package io.boodskap.iot.spi.storage.es;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.tinylog.Logger;

import io.boodskap.iot.StorageException;
import io.boodskap.iot.ThreadContext;
import io.boodskap.iot.model.IDomainObject;
import io.boodskap.iot.model.IStorageObject;
import io.boodskap.iot.util.PebbleUtil;

@SuppressWarnings("unchecked")
public class Elastic{

	private static final Elastic instance = new Elastic();

	private static final Set<String> JSON_FIELDS = new HashSet<>();
	private static final Set<String> SKIPPED_FIELDS = new HashSet<>();
	private final Map<Class<? extends IStorageObject>, String> INDEXES = new HashMap<>();
	private final Map<String, List<String>> KEYS = new HashMap<>();


	static {
		JSON_FIELDS.add("extraProperties");

		SKIPPED_FIELDS.add("fields");
	}

	private Elastic() {
	}

	public static final Elastic get() {
		return instance;
	}

	private RestClient es;

	private JSONObject index;

	protected static ESConfig config;

	public static boolean isSearchSupported() {
		return true;
	}

	public static String getVendorInfo() {
		return "elastic.co";
	}

	public static String getVersion() {
		return "7.15.x";
	}

	public static ESConfig config() {
		return config;
	}

	public RestClient getClient() {
		return es;
	}

	public static class EntityIterator<T extends IStorageObject> implements io.boodskap.iot.dao.EntityIterator<T>{

		private final Class<T> clazz;
		private final EntityConverter<T> converter;
		private final ResultSet iter;
		private boolean moved;

		public EntityIterator(Class<T> clazz, EntityConverter<T> converter, ResultSet iter) {
			this.clazz = clazz;
			this.converter = converter;
			this.iter = iter;
		}

		@Override
		public boolean hasNext() {
			try {
				moved = iter.next();
				if(!moved) {
					ESSession.get().close();
				}
				return moved;
			}catch(Exception ex) {
				throw new StorageException(ex);
			}
		}

		@Override
		public T next() {
			try {
				if(!moved) {
					moved=iter.next();
					if(!moved) {
						ESSession.get().close();
					}
				}
				moved = false;

				if(null == converter) {
					T o = clazz.getConstructor().newInstance();
					return o;
				}

				return converter.convert(iter);
			} catch (Exception e) {
				throw new StorageException(e);
			}
		}

	}

	public static interface EntityConverter<T extends IStorageObject>{
		public T convert(ResultSet rs);
	}

	private void init() {

		try {

			if(null == config.getIndex()) {
				config.setIndex(new ESIndex());
			}

			this.index = new JSONObject();
			this.index.put("number_of_shards", config.getIndex().getNumberOfSshards());
			this.index.put("number_of_replicas", config.getIndex().getNumberOfReplicas());

			final Properties tables = new Properties();
			tables.load(Elastic.class.getResourceAsStream("/elastic/mapping.properties"));
			Map<Object, Object> map = new HashMap<>(tables);
			
			int temIndex = 0;
			for(Map.Entry<Object, Object> me : map.entrySet()) {
				
				Logger.debug("Executing {} / {} Entity: {} Key: {}", ++temIndex, map.size(), me.getKey().toString().substring(27), me.getValue());
				final String cname = me.getKey().toString();
				final String val = me.getValue().toString();
				final String index = val.substring(0, val.indexOf(":"));

				Class<?> clazz = Class.forName(cname);

				createIndex((Class<? extends IStorageObject>) clazz, index);
			}

		}catch(Exception ex) {
			Logger.error(ex);
		}
	}

	protected void init(ESConfig cfg) throws Exception {

		if(null != es) return; 

		Elastic.config = cfg;

		Logger.info("Initializing elastic search subsystem....");

		List<HttpHost> hcs = new ArrayList<>();
		List<Header> bhs = new ArrayList<>();

		cfg.getHosts().forEach(h -> {
			HttpHost hh = new HttpHost(h.getHost(), h.getPort(), h.getScheme());
			hcs.add(hh);
		});

		HttpHost[] hl = new HttpHost[hcs.size()];
		hcs.toArray(hl);

		if(null != config.getHeaders()) {

			config.getHeaders().forEach((k,v) -> {
				BasicHeader hh = new BasicHeader(k, v);
				bhs.add(hh);				
			});

		}

		RestClientBuilder builder = RestClient.builder(hl);

		if(!bhs.isEmpty()) {

			Header[] hs = new Header[bhs.size()];
			bhs.toArray(hs);

			builder.setDefaultHeaders(hs);
		}

		if(null != config.getPathPrefix()) {
			builder.setPathPrefix(config.getPathPrefix());
		}

		instance.es = builder.build();

		init();

		Logger.info("Elastic search subsystem inited.");
	}

	public static void close() throws StorageException {

		try {

			if(null != instance.es) {
				instance.es.close();
				instance.es = null;
			}

		}catch(Exception ex) {
			throw new StorageException(ex);
		}
	}

	public static void setJsonType(Request r) {
		RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
		builder.addHeader("Content-Type", "application/json");
		r.setOptions(builder);
	}

	public static Field getField(Class<?> clazz, String name) {
		Field field = null;
		while (clazz != null && field == null) {
			try {
				field = clazz.getDeclaredField(name);
			} catch (Exception e) {
			}
			clazz = clazz.getSuperclass();
		}
		return field;
	}

	public boolean hasIndex(String index) throws StorageException {
		try {

			Request req = new Request("HEAD", index);
			Response res = es.performRequest(req);

			EntityUtils.consume(res.getEntity());

			return res.getStatusLine().getStatusCode() == HttpStatus.SC_OK ;

		}catch(Exception ex) {
			throw new StorageException(ex);
		}		
	}

	public void deleteIndex(String index) throws StorageException {
		try {
			Request req = new Request("DELETE", index);
			Response res = es.performRequest(req);
			EntityUtils.consume(res.getEntity());
		}catch(Exception ex) {
			throw new StorageException(ex);
		}		
	}

	public void flushIndex(String index) throws StorageException {
		try {
			Request req = new Request("POST", String.format("%s/_flush", index));
			Response res = es.performRequest(req);
			EntityUtils.consume(res.getEntity());

			req = new Request("POST", String.format("%s/_refresh", index));
			res = es.performRequest(req);
			EntityUtils.consume(res.getEntity());
		}catch(Exception ex) {
			throw new StorageException(ex);
		}		
	}

	public void createIndex(Class<? extends IStorageObject> entityClass, String indexPath) throws StorageException {

		if(hasIndex(indexPath)) {
			INDEXES.put(entityClass, indexPath);
			return;
		}

		try {
			String json = new String(Files.readAllBytes(Paths.get(Elastic.class.getResource(String.format("/indexes/%s.json", indexPath)).toURI())));

			JSONObject config = new JSONObject(json);
			JSONObject settings = new JSONObject();

			settings.put("index", index);
			config.put("settings", settings);

			Request r = new Request("PUT", indexPath);
			r.setJsonEntity(config.toString());

			Logger.trace(String.format("creating index %s \n%s", indexPath, config.toString(4)));

			es.performRequest(r);

			INDEXES.put(entityClass, indexPath);

		}catch(Exception ex) {
			throw new StorageException(ex);
		}		

	}

	public String getTable(Class<? extends IStorageObject> clazz) throws StorageException {

		String table = INDEXES.get(clazz);

		if(null == table) {
			throw new StorageException("**DEVELOPER** add related resourcs to resources/Elastic folder. Table mapping not found for %s", clazz.getName());
		}

		return table;
	}

	public void save(IStorageObject entity) throws StorageException{
		try {

			final String table = getTable(entity.getClass());
			final List<String> keys = KEYS.get(table);			
			final Map<String, Object> map = ThreadContext.mapper.convertValue(entity, HashMap.class);
			final List<Object> values = new ArrayList<>();
			final List<String> columns = new ArrayList<>();			

			keys.forEach(k -> {
				columns.add(k);
				values.add(map.get(k));
			});

			for(Map.Entry<String, Object> me : map.entrySet()) {
				if(!keys.contains(me.getKey()) && !SKIPPED_FIELDS.contains(me.getKey())) {
					columns.add(me.getKey());
					values.add(me.getValue());
				}
			}

			Iterator<String> kiter = columns.iterator();
			final StringBuilder qsb  = new StringBuilder();

			qsb.append("MERGE INTO ")
			.append(table)
			.append(" (")
			;

			while(kiter.hasNext()) {
				qsb.append("\"").append(kiter.next()).append("\"");
				if(kiter.hasNext()) {
					qsb.append(",");
				}
			}

			qsb.append(") VALUES(");

			kiter = columns.iterator();

			while(kiter.hasNext()) {

				qsb.append("?");

				kiter.next();
				//final String name = kiter.next();

				//if(JSON_FIELDS.contains(name)) {
				//	qsb.append(" FORMAT JSON");
				//}

				if(kiter.hasNext()) {
					qsb.append(",");
				}
			}

			qsb.append(")");

			final ESSession s = ESSession.get();

			try {

				final PreparedStatement ps = s.prepare(qsb.toString());

				int i=1;
				kiter = columns.iterator();
				while(kiter.hasNext()) {
					final String fname = kiter.next();
					Field f = getField(entity.getClass(), fname);
					f.setAccessible(true);

					final Object value = f.get(entity);

					if (null != value && value.getClass().isEnum()){
						Enum<?> e = (Enum<?>) value;
						ps.setObject(i++, e.name());
					}else if(null != value && value instanceof Collection<?>) {
						Collection<?> col = (Collection<?>) value;
						Object[] vals = new Object[col.size()];
						col.toArray(vals);
						ps.setObject(i++, vals);
					}else {
						ps.setObject(i++, value);
					}
				}

				ps.execute();

			}finally {
				s.close();
			}

		}catch(StorageException sx) {
			throw sx;

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

	}

	public <T extends IStorageObject> EntityIterator<T> load(Class<T> clazz, String domainKey, EntityConverter<T> converter) throws StorageException{
		return load(clazz, domainKey, null);
	}

	public <T extends IStorageObject> EntityIterator<T> load(Class<T> clazz, EntityConverter<T> converter) throws StorageException{
		return load(clazz, (EntityConverter<T>) null);
	}

	public void delete(Class<? extends IStorageObject> clazz) throws StorageException{

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Map<String, Object> targs = new HashMap<>();

			String query = PebbleUtil.mergeNamedTemplate("match-all", targs);

			Request r = new Request("POST", String.format("%s/_delete_by_query", index));
			r.setJsonEntity(query);

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			EntityUtils.consume(res.getEntity());

		}catch(Exception ex) {
			throw new StorageException(ex);
		}
		flushIndex(index);
	}

	public void delete(Class<? extends IStorageObject> clazz, String id) throws StorageException {

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {
			Logger.trace(String.format("deleting %s %s/%s", clazz.getSimpleName(), index, id));;

			Request r = new Request("DELETE", String.format("%s/_doc/%s", index, id));

			Response res = es.performRequest(r);

			EntityUtils.consume(res.getEntity());

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

		flushIndex(index);
	}

	public <T extends IStorageObject> List<T> search(Class<T> clazz, Set<String> domainKeys, String templateId, Map<String, Object> args) throws StorageException {
		String query;
		try {
			query = PebbleUtil.mergeNamedTemplate(templateId, args);
		} catch (Exception e) {
			throw new StorageException(e);
		}

		return search(clazz, domainKeys, query);
	}

	public <T extends IStorageObject> List<T> search(Class<T> clazz, Set<String> domainKeys, String query) throws StorageException {

		List<T> list = new ArrayList<>();

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Request r = new Request("POST", String.format("%s/_search", index));
			r.setJsonEntity(query);

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			String json =  EntityUtils.toString(res.getEntity());

			JSONObject result = new JSONObject(json);

			JSONArray hits = result.getJSONObject("hits").optJSONArray("hits");

			if(null != hits && hits.length() > 0) {

				for(int i=0;i<hits.length();i++) {

					JSONObject source = hits.getJSONObject(i).getJSONObject("_source");

					T value = ThreadContext.jsonToObject(source, clazz);

					if(!domainKeys.isEmpty() && value instanceof IDomainObject && config.isStrictSearch() && !domainKeys.contains(((IDomainObject)value).getDomainKey())) {
						throw new StorageException("Access denied, you can only search these domains %s", domainKeys);
					}

					list.add(value);
				}
			}

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

		return list;
	}

	public <T extends IStorageObject> String scroll(Class<T> clazz, List<T> outResult, Set<String> domainKeys, String templateId, Map<String, Object> args, int pageSize) throws StorageException {
		String query;
		try {
			query = PebbleUtil.mergeNamedTemplate(templateId, args);
		} catch (Exception e) {
			throw new StorageException(e);
		}

		return scroll(clazz, outResult, domainKeys, query, pageSize);
	}

	public <T extends IStorageObject> String scroll(Class<T> clazz, List<T> list, Set<String> domainKeys, String query, int pageSize) throws StorageException {

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Request r = new Request("POST", String.format("%s/_search?scroll=10m&size=" + pageSize, index));
			r.setJsonEntity(query);

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			String json =  EntityUtils.toString(res.getEntity());

			JSONObject result = new JSONObject(json);

			JSONArray hits = result.getJSONObject("hits").optJSONArray("hits");

			if(null != hits && hits.length() > 0) {

				for(int i=0;i<hits.length();i++) {

					JSONObject source = hits.getJSONObject(i).getJSONObject("_source");

					T value = ThreadContext.jsonToObject(source, clazz);

					if(!domainKeys.isEmpty() && value instanceof IDomainObject && config.isStrictSearch() && !domainKeys.contains(((IDomainObject)value).getDomainKey())) {
						throw new StorageException("Access denied, you can only search these domains %s", domainKeys);
					}

					list.add(value);
				}
			}

			return result.getString("_scroll_id");

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

	}

	public <T extends IStorageObject> List<T> scroll(Class<T> clazz, Set<String> domainKeys, String scrollid) throws StorageException {

		List<T> list = new ArrayList<>();

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Request r = new Request("GET", "/_search/scroll");

			Elastic.setJsonType(r);

			r.setJsonEntity(String.format("{\"scroll\":\"10m\", \"scroll_id\": \"%s\"}", scrollid));

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			String json =  EntityUtils.toString(res.getEntity());

			JSONObject result = new JSONObject(json);

			JSONArray hits = result.getJSONObject("hits").optJSONArray("hits");

			if(null != hits && hits.length() > 0) {

				for(int i=0;i<hits.length();i++) {

					JSONObject source = hits.getJSONObject(i).getJSONObject("_source");

					T value = ThreadContext.jsonToObject(source, clazz);

					if(!domainKeys.isEmpty() && value instanceof IDomainObject && config.isStrictSearch() && !domainKeys.contains(((IDomainObject)value).getDomainKey())) {
						throw new StorageException("Access denied, you can only search these domains %s", domainKeys);
					}

					list.add(value);
				}
			}

			return list;

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

	}

	public  <T extends IStorageObject> long count(Class<T> clazz) {

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Request r = new Request("GET", String.format("%s/_count", index));

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			String json =  EntityUtils.toString(res.getEntity());

			JSONObject result = new JSONObject(json);

			return result.getLong("count");

		}catch(Exception ex) {
			throw new StorageException(ex);
		}
	}

	public  <T extends IStorageObject> long count(Class<T> clazz, String domainKey) {

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Map<String, Object> targs = new HashMap<>();
			targs.put("dkey", domainKey);

			String query = PebbleUtil.mergeNamedTemplate("match-domain", targs);

			return countByQuery(clazz, query);

		}catch(Exception ex) {
			throw new StorageException(ex);
		}
	}

	public  <T extends IStorageObject> long count(Class<T> clazz, String domainKey, String field, Object fieldValue) {

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Map<String, Object> targs = new HashMap<>();
			targs.put("dkey", domainKey);
			targs.put(field, fieldValue);

			String query = PebbleUtil.mergeNamedTemplate("match-domain-and", targs);

			return countByQuery(clazz, query);

		}catch(Exception ex) {
			throw new StorageException(ex);
		}
	}

	public  <T extends IStorageObject> long countByQuery(Class<T> clazz, String query) {

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Request r = new Request("POST", String.format("%s/_count", index));
			r.setJsonEntity(query);

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			String json =  EntityUtils.toString(res.getEntity());

			JSONObject result = new JSONObject(json);

			return result.getLong("count");

		}catch(Exception ex) {
			throw new StorageException(ex);
		}
	}

	public  <T extends IStorageObject> void purge(Class<T> clazz) {

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Map<String, Object> targs = new HashMap<>();

			String query = PebbleUtil.mergeNamedTemplate("match-all", targs);

			Request r = new Request("POST", String.format("%s/_delete_by_query", index));
			r.setJsonEntity(query);

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			EntityUtils.consume(res.getEntity());

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

		flushIndex(index);
	}

	public  <T extends IStorageObject> void purge(Class<T> clazz, String domainKey) {

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Map<String, Object> targs = new HashMap<>();
			targs.put("dkey", domainKey);

			String query = PebbleUtil.mergeNamedTemplate("match-domain", targs);

			Request r = new Request("POST", String.format("%s/_delete_by_query", index));
			r.setJsonEntity(query);

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			EntityUtils.consume(res.getEntity());

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

		flushIndex(index);
	}

	/**
	 * @author ganapathi
	 * @since 5.0-0.00
	 */
	public JSONObject deleteByQuery(Class<? extends IStorageObject> clazz, String query) throws StorageException{

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Request r = new Request("POST", String.format("%s/_delete_by_query", index));
			r.setJsonEntity(query);

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			String json =  EntityUtils.toString(res.getEntity());

			JSONObject result = new JSONObject(json);

			int affected = result.getInt("count");

			result.put("affected", affected);

			return result;

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

	}

	/**
	 * @author ganapathi
	 * @since 5.0-0.00
	 */
	public JSONObject deleteByQuery(Class<? extends IStorageObject> clazz, String domainKey, String query) throws StorageException{

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Request r = new Request("DELETE", String.format("%s/_doc/%s", index, domainKey));
			r.setJsonEntity(query);

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			String json =  EntityUtils.toString(res.getEntity());

			JSONObject result = new JSONObject(json);

			int affected = result.getInt("count");

			result.put("affected", affected);

			return result;

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

	}

	/**
	 * @author ganapathi
	 * @since 5.0-0.00
	 */
	public JSONObject updateByQuery(Class<? extends IStorageObject> clazz, String query) throws StorageException{

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Request r = new Request("POST", String.format("%s/_update_by_query", index));
			r.setJsonEntity(query);

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			String json =  EntityUtils.toString(res.getEntity());

			JSONObject result = new JSONObject(json);

			int affected = result.getInt("count");

			result.put("affected", affected);

			return result;

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

	}

	/**
	 * @author ganapathi
	 * @since 5.0-0.00
	 */
	public JSONObject updateByQuery(Class<? extends IStorageObject> clazz, String domainKey, String query) throws StorageException{

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Request r = new Request("POST", String.format("%s/_update_by_query/%s", index, domainKey));
			r.setJsonEntity(query);

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			String json =  EntityUtils.toString(res.getEntity());

			JSONObject result = new JSONObject(json);

			int affected = result.getInt("count");

			result.put("affected", affected);

			return result;

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

	}

	/**
	 * @author ganapathi
	 * @since 5.0-0.00
	 */
	public JSONObject updateByQuery(Class<? extends IStorageObject> clazz, String domainKey, String what, String how) throws StorageException{

		final String index = INDEXES.get(clazz);

		if(null == index) {
			throw new StorageException(String.format("Index for entity: %s not configured", clazz.getSimpleName()));
		}

		try {

			Map<String, Object> targs = new HashMap<>();
			targs.put("dkey", domainKey);
			targs.put(what, how);

			String query = PebbleUtil.mergeNamedTemplate("match-domain-and", targs);

			Request r = new Request("POST", String.format("%s/_update_by_query", index));
			r.setJsonEntity(query);

			Response res = es.performRequest(r);

			if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) {
				throw new StorageException(res.getStatusLine().getReasonPhrase());
			}

			String json =  EntityUtils.toString(res.getEntity());

			JSONObject result = new JSONObject(json);

			int affected = result.getInt("count");

			result.put("affected", affected);

			return result;

		}catch(Exception ex) {
			throw new StorageException(ex);
		}

	}



}
