package com.liemlhd.starter.service_discovery.zookeeper;

import com.liemlhd.starter.service_discovery.AbstractServiceDiscovery;
import com.liemlhd.starter.service_discovery.ServiceInfo;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ZookeeperServiceDiscovery extends AbstractServiceDiscovery {

  private static final Logger log = LoggerFactory.getLogger(ZookeeperServiceDiscovery.class);

  private final io.vertx.servicediscovery.ServiceDiscovery discovery;
  private final Vertx vertx;

  public ZookeeperServiceDiscovery(Vertx vertx, ZkConfig config) {
    ServiceDiscoveryOptions options = new ServiceDiscoveryOptions()
      .setBackendConfiguration(JsonObject.mapFrom(config));
    this.vertx = vertx;
    this.discovery = io.vertx.servicediscovery.ServiceDiscovery.create(vertx, options);
  }

  @Override
  protected CompletableFuture<List<ServiceInfo>> find0(Record filter) {
    io.vertx.core.Future<List<ServiceInfo>> f = io.vertx.core.Future.future();
    Function<Record, Boolean> _filter;
    if (filter == null) {
      _filter = r -> true;
    } else {
      _filter = r -> r.match(JsonObject.mapFrom(filter));
    }
    vertx.executeBlocking(h -> {
      discovery.getRecords(_filter, ar -> {
        if (ar.succeeded()) {
          List<Record> result = ar.result();
          if (result != null) {
            List<ServiceInfo> serviceInfos = result.stream()
              .map(r -> ServiceInfo.from(r))
              .collect(Collectors.toList());
            h.complete(serviceInfos);
          } else {
            h.complete(Collections.emptyList());
          }
        } else {
          h.fail(ar.cause());
        }
      });
    }, ar -> {
      if (ar.succeeded()) {
        f.complete((List<ServiceInfo>) ar.result());
      } else {
        f.fail(ar.cause());
      }
    });
    return CompletableFuture.supplyAsync(() -> {
      while (!f.isComplete()) {

      }
      return f.result();
    });
  }

  @Override
  protected CompletableFuture<ServiceInfo> register0(Record record) {
    io.vertx.core.Future<ServiceInfo> f = io.vertx.core.Future.future();
    vertx.executeBlocking(h -> {
      discovery.publish(record, ar -> {
        if (ar.succeeded()) {
          Record result = ar.result();
          if (result != null) {
            h.complete(ServiceInfo.from(record));
          } else {
            h.complete();
          }
        } else {
          h.fail(ar.cause());
        }
      });
    }, ar -> {
      if (ar.succeeded()) {
        f.complete((ServiceInfo) ar.result());
      } else {
        f.fail(ar.cause());
      }
    });
    return CompletableFuture.supplyAsync(() -> {
      while (!f.isComplete()) {
      }
      return f.result();
    });
  }

  @Override
  protected void deregister0(String serviceId) {
    io.vertx.core.Future<ServiceInfo> f = io.vertx.core.Future.future();
    vertx.executeBlocking(h -> {
      discovery.unpublish(serviceId, ar -> {
        if (ar.succeeded()) {
          log.info("Deregister success. " + serviceId);
        } else {
          log.error("Fail to deregister. {}", new Object[]{serviceId, ar.cause()});
        }
        f.complete();
      });
    }, ar -> {
    });
    while (!f.isComplete()) {
      // Wait for completed
    }
  }


}
