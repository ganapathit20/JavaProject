package io.boodskap.iot.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import io.boodskap.iot.BootstrapBase;
import io.boodskap.iot.StorageException;
import io.boodskap.iot.TestConfig;
import io.boodskap.iot.model.IClusterMachine;

@ExtendWith({BootstrapBase.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClusterMachineTests {
	
	private static TestConfig config;
	
	static {
		config = BootstrapBase.getConfig();
	}
	
	@Order(1)
	@Test
	public void createClusterMachine() throws StorageException {
		
		for(int d=0; d<config.getDomains().size(); d++) {

			final String domainKey = config.getDomains().get(d);

			for (int j=0; j<config.getEntities(); j++) {

				final String targetDomainKey = String.format("targetDomainKey%d", j+1);

				for(int k=0; j<config.getSubEntities(); k++) {

					final String clusterId = String.format("clusterId%d", k+1);

					final String machineId = String.format("machineId%d", k+1);

					final IClusterMachine cl = IClusterMachine.create(domainKey,targetDomainKey,clusterId,machineId);

					
					cl.save();
				}
			}
		}
	}

	@Order(2)
	@Test
	public void getClusterMachine()	throws StorageException {
		
		final String domainKey = config.getDomains().get(0);
		final String targetDomainKey = "targetDomainKey1";
		final String clusterId = "clusterId1";
		final String machineId = "machineId1";

		IClusterMachine cl = IClusterMachine.get(domainKey, targetDomainKey, clusterId, machineId);

		assertNotNull(cl, String.format("Unable to find Cluster Machines %s.%s.%s.%s", domainKey, targetDomainKey, clusterId, machineId));
	}
	
	@Order(3)
	@Test
	public void loadAllClusterMachine() throws StorageException {
		
		final AtomicInteger count = new AtomicInteger(0);

		IClusterMachine.load().forEachRemaining(i -> {
			count.incrementAndGet();
		});

		int total = config.getDomains().size() * config.getEntities() * config.getSubEntities();

		assertEquals(total, count.get(), String.format("Expected %d Cluster Machine, found: %d", total, count.get()));
	}

	@Order(4)
	@Test
	public void countAllClusterMachine() throws StorageException {
		
		final long count = IClusterMachine.count();

		long total = config.getDomains().size() * config.getEntities() * config.getSubEntities();

		assertEquals(total, count, String.format("Expected %d , found: %d", total, count));
	}

	@Order(5)
	@Test
	public void countClusterMachineByDomain() throws StorageException {
		
		final String domainKey = config.getDomains().get(0);

		final long count = IClusterMachine.count(domainKey);

		long total = config.getEntities() * config.getSubEntities();

		assertEquals(total, count, String.format("Expected %d , found: %d", total, count));
	}

	@Order(6)
	@Test
	public void countClusterMachineByTrgtDomain() throws StorageException {
		
		final String domainKey = config.getDomains().get(1);

		final String targetDomainKey = "targetDomainKey2";

		final long count = IClusterMachine.count(domainKey, targetDomainKey);

		long total = config.getEntities()/4 * config.getSubEntities();

		assertEquals(total, count, String.format("Expected %d , found: %d", total, count));
	}

	@Order(7)
	@Test
	public void countClusterMachineByClusterId() throws StorageException {
		
		final String domainKey = config.getDomains().get(1);

		final String targetDomainKey = "targetDomainKey2";

		final String clusterId = "clusterId1";

		final long count = IClusterMachine.count(domainKey, targetDomainKey, clusterId);

		long total = config.getEntities()/4;

		assertEquals(total, count, String.format("Expected %d , found: %d", total, count));
	}

	@Order(8)
	@Test
	public void countAllCpuCores() throws StorageException {
		
		final long count = IClusterMachine.count();

		long total = config.getDomains().size() * config.getEntities() * config.getSubEntities();

		assertEquals(total, count, String.format("Expected %d , found: %d", total, count));
	}

	@Order(9)
	@Test
	public void countCpuCoresByTrgtDomain() throws StorageException {
	}

	@Order(10)
	@Test
	public void countCpuCoresByDomain() throws StorageException {
	}

	@Order(11)
	@Test
	public void countCpuCoresByClusterId() throws StorageException {
	}

	@Order(12)
	@Test
	public void touchClusterMachine()	throws StorageException {
		
	}

	@Order(13)
	@Test
	public void listAllClusterMachine() throws StorageException {
		
		final String domainKey = config.getDomains().get(1);

		final String targetDomainKey = "targetDomainKey2";
		
		final String clusterId = "clusterId2";

		final int total = config.getEntities()/2;

		Collection<IClusterMachine> listCameras = IClusterMachine.list(domainKey, targetDomainKey, clusterId, 0, total);

		assertEquals(total, listCameras.size(),
				String.format("Expected %d types, found: %d", total, listCameras.size()));
	}

	@Order(14)
	@Test
	public void listNextClusterMachine() throws StorageException {
		
		final String domainKey = config.getDomains().get(0);
		final String targetDomainKey = "targetDomainKey1";
		final String clusterId1 = "clusterId1";
		final String machineId = "machineId1";
		final int total = config.getEntities()/2;

		Collection<IClusterMachine> list = IClusterMachine.listNext(domainKey, targetDomainKey, clusterId1, machineId, 1, total);

		assertEquals(total, list.size(), String.format("Expected %d device , found: %d", total, list.size()));
	}

	@Order(15)
	@Test
	public void searchAllClusterMachine() throws StorageException {
	}

	@Order(16)
	@Test
	public void deleteByDkeyTargetDkeyCidMid() throws StorageException {

		final String domainKey = config.getDomains().get(0);

		final String targetDomainKey = "targetDomainKey1";

		final String clusterId = "clusterId1";

		final String machineId = "machineId1";

		IClusterMachine.delete(domainKey, targetDomainKey, clusterId, machineId);

		final long count = IClusterMachine.count();

		final long total = 119;

		assertEquals(total, count, String.format("Expected %d Cluster Machine after deletion, found: %d", total, count));
	}

	@Order(17)
	@Test
	public void deleteByDkeyTargetDkeyCid() throws StorageException {

		final String domainKey = config.getDomains().get(0);

		final String targetDomainKey = "targetDomainKey1";

		final String clusterId = "clusterId1";

		IClusterMachine.delete(domainKey, targetDomainKey, clusterId);

		final long count = IClusterMachine.count();

		final long total = 119;

		assertEquals(total, count, String.format("Expected %d Cluster Machine after deletion, found: %d", total, count));
	}

	@Order(18)
	@Test
	public void deleteByDkeyTargetDkey() throws StorageException {

		final String domainKey = config.getDomains().get(0);

		final String targetDomainKey = "targetDomainKey1";

		IClusterMachine.delete(domainKey, targetDomainKey);

		final long count = IClusterMachine.count();

		final long total = 109;

		assertEquals(total, count, String.format("Expected %d Cluster Machine after deletion, found: %d", total, count));
	}

	@Order(19)
	@Test
	public void deleteDkey() throws StorageException {

		final String domainKey = config.getDomains().get(0);

		IClusterMachine.delete(domainKey);

		final long count = IClusterMachine.count();

		final long total = 80;

		assertEquals(total, count, String.format("Expected %d Cluster Machine after deletion, found: %d", total, count));
	}
	
	@Order(20)
	@Test
	public void deleteAll() throws StorageException {

		IClusterMachine.delete();

		final long count = IClusterMachine.count();

		final long total = 0;

		assertEquals(total, count, String.format("Expected %d Cluster Machine after purge, found: %d", total, count));
	}

}
