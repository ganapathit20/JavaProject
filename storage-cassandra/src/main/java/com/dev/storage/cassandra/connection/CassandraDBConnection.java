package com.dev.storage.cassandra.connection;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraDBConnection {

	private Cluster cluster;
	
	private Session session;
	
	private static CassandraDBConnection conObj = new CassandraDBConnection();
	
	public static CassandraDBConnection getInstance() {
		return conObj;
	}
	
	public Session getSession() {
		return this.session;
	}

	public CassandraDBConnection(){};
	
	public void DBconnection(String seeds, int port) {

		this.cluster=Cluster.builder().addContactPoint(seeds)
				.withPort(port).build();

		final Metadata metaData = cluster.getMetadata();

		for (final Host host : metaData.getAllHosts()) {
			System.out.println("Driver version : " + host.getCassandraVersion());
		}

		this.session=cluster.connect();
	}

	public void close() {
		cluster.close();
	}

}
