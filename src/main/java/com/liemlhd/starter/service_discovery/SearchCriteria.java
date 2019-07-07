package com.liemlhd.starter.service_discovery;

import java.util.Objects;
import java.util.Optional;

@FunctionalInterface
public interface SearchCriteria {

  static SearchCriteria create() {
    return _any -> true;
  }

  static SearchCriteria status(ServiceStatus status) {
    return si -> Optional.ofNullable(si).map(s -> s.getStatus()).filter(s -> s.equals(status)).isPresent();
  }

  static SearchCriteria name(String serviceName) {
    return si -> Optional.ofNullable(si).map(s -> s.getName())
      .filter(n -> n.equalsIgnoreCase(serviceName))
      .isPresent();
  }

  static SearchCriteria type(IServiceType serviceType) {
    return si -> Optional.ofNullable(si).map(s -> s.getServiceType())
      .map(t -> t.getType())
      .filter(t -> t.equalsIgnoreCase(Optional.ofNullable(serviceType).map(s -> s.getType()).orElse(null)))
      .isPresent();
  }

  static SearchCriteria datacenter(String dc) {
    return si -> Optional.ofNullable(si).map(s -> s.getAddress()).map(a -> a.getDatacenter())
      .filter(_dc -> _dc.equalsIgnoreCase(dc)).isPresent();
  }

  static SearchCriteria host(String host) {
    return si -> Optional.ofNullable(si).map(s -> s.getAddress()).map(a -> a.getHost())
      .filter(h -> h.equalsIgnoreCase(host)).isPresent();
  }

  static SearchCriteria port(int port) {
    return si -> si.getAddress() != null && si.getAddress().getPort() == port;
  }


  boolean test(ServiceInfo t);


  default SearchCriteria and(SearchCriteria other) {
    Objects.requireNonNull(other);
    return (t) -> test(t) && other.test(t);
  }


  default SearchCriteria negate() {
    return (t) -> !test(t);
  }


  default SearchCriteria or(SearchCriteria other) {
    Objects.requireNonNull(other);
    return (t) -> test(t) || other.test(t);
  }

  static SearchCriteria isEqual(Object targetRef) {
    return (null == targetRef)
      ? Objects::isNull
      : object -> targetRef.equals(object);
  }
}
