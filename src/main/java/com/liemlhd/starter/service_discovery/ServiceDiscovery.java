package com.liemlhd.starter.service_discovery;


import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ServiceDiscovery {
  CompletableFuture<List<ServiceInfo>> find(ServiceInfo filter);

  CompletableFuture<List<ServiceInfo>> find(SearchCriteria searchCriteria);

  CompletableFuture<ServiceInfo> register(ServiceInfo serviceInfo);

  void deregister(String serviceId);
}
