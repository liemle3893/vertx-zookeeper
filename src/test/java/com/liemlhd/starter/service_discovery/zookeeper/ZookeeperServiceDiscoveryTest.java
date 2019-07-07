package com.liemlhd.starter.service_discovery.zookeeper;

import com.google.common.base.Strings;
import com.liemlhd.starter.service_discovery.ServiceInfo;
import com.liemlhd.starter.service_discovery.ServiceStatus;
import com.liemlhd.starter.service_discovery.SearchCriteria;
import io.vertx.core.Vertx;
import org.apache.curator.test.TestingServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZookeeperServiceDiscoveryTest {

  static TestingServer testingServer;
  private ZookeeperServiceDiscovery serviceDiscovery;

  ZookeeperServiceDiscoveryTest() {
  }


  @BeforeEach
  public void setupClass() throws Exception {
    testingServer = new TestingServer(true);
    this.serviceDiscovery = new ZookeeperServiceDiscovery(Vertx.vertx(), createConfig());

  }


  @AfterEach
  public void teardownClass() throws IOException {
    testingServer.stop();
  }

  public ZkConfig createConfig() {
    ZkConfig config = new ZkConfig();
    config.setZookeeperHosts(testingServer.getConnectString());
    return config;
  }

  public ServiceInfo createServiceInfo() {
    ServiceInfo serviceInfo = new ServiceInfo(() -> "test");
    ServiceInfo.Address address = new ServiceInfo.Address();
    address.setPort(12345);
    address.setHost("localhost");
    serviceInfo.setAddress(address);
    serviceInfo.setName("test");
    return serviceInfo;
  }

  @Test
  public void testRegisterService_success() throws ExecutionException, InterruptedException {
    ServiceInfo serviceInfo = createServiceInfo();
    CompletableFuture<ServiceInfo> registerPromise = serviceDiscovery.register(serviceInfo);
    ServiceInfo registeredServiceInfo = registerPromise.get();
    assertFalse(Strings.isNullOrEmpty(registeredServiceInfo.getId()));
  }

  @Test
  public void testDeregisterService_success() throws ExecutionException, InterruptedException {
    // Given
    ServiceInfo serviceInfo = createServiceInfo();
    CompletableFuture<ServiceInfo> registerPromise = serviceDiscovery.register(serviceInfo);
    ServiceInfo registeredServiceInfo = registerPromise.get();
    assertFalse(Strings.isNullOrEmpty(registeredServiceInfo.getId()));
    // When
    serviceDiscovery.deregister(registeredServiceInfo.getId());
    // Then
    SearchCriteria searchCriteria = SearchCriteria.create().and(SearchCriteria.status(ServiceStatus.UP));
    CompletableFuture<List<ServiceInfo>> listCompletableFuture = serviceDiscovery.find(searchCriteria);
    List<ServiceInfo> serviceInfos = listCompletableFuture.get();
    assertTrue(serviceInfos.isEmpty());
  }

  @Test
  public void testSearchService_success() throws Exception {
    // Given
    ServiceInfo serviceInfo = createServiceInfo();
    CompletableFuture<ServiceInfo> registerPromise = serviceDiscovery.register(serviceInfo);
    ServiceInfo registeredServiceInfo = registerPromise.get();
    assertFalse(Strings.isNullOrEmpty(registeredServiceInfo.getId()));
    SearchCriteria searchCriteria = SearchCriteria.create().and(SearchCriteria.status(ServiceStatus.UP));
    // When
    CompletableFuture<List<ServiceInfo>> listCompletableFuture = serviceDiscovery.find(searchCriteria);
    // Then
    List<ServiceInfo> serviceInfos = listCompletableFuture.get();
    assertFalse(serviceInfos.isEmpty());
  }

}
