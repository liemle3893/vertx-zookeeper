package com.liemlhd.starter.service_discovery;

import io.vertx.servicediscovery.Record;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class AbstractServiceDiscovery implements ServiceDiscovery {

  @Override
  public final CompletableFuture<List<ServiceInfo>> find(ServiceInfo filter) {
    return find0(Optional.ofNullable(filter).map(f -> f.createRecord()).orElse(null));
  }

  @Override
  public final CompletableFuture<List<ServiceInfo>> find(SearchCriteria searchCriteria) {
    return find0(null)
      .thenApply(l -> l.stream()
        .filter(r -> searchCriteria.test(r))
        .collect(Collectors.toList())
      );
  }

  @Override
  public final CompletableFuture<ServiceInfo> register(ServiceInfo serviceInfo) {
    Objects.requireNonNull(serviceInfo).validate();
    return register0(serviceInfo.createRecord());
  }

  @Override
  public final void deregister(String serviceId) {
    deregister0(serviceId);
  }

  protected abstract CompletableFuture<List<ServiceInfo>> find0(Record filter);


  protected abstract CompletableFuture<ServiceInfo> register0(Record record);

  protected abstract void deregister0(String serviceId);

}
