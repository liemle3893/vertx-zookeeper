package com.liemlhd.starter.service_discovery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.Status;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceInfo {
  private final IServiceType serviceType;
  private String name;
  private Address address;
  private String id;
  private JsonObject metadata = new JsonObject();
  private ServiceStatus status;

  public ServiceInfo(IServiceType serviceType) {
    this.serviceType = serviceType;
  }

  public void validate() {
    Objects.requireNonNull(serviceType);
    Objects.requireNonNull(name);
    Objects.requireNonNull(address).validate();
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMetadata(JsonObject metadata) {
    this.metadata = metadata;
  }

  public void setStatus(ServiceStatus status) {
    this.status = status;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public IServiceType getServiceType() {
    return serviceType;
  }

  Record createRecord() {
    Record record = new Record();
    Optional.ofNullable(getName()).ifPresent(record::setName);
    Optional.ofNullable(getServiceType()).ifPresent(s -> record.setType(s.getType()));
    Optional.ofNullable(getId()).ifPresent(record::setRegistration);
    Optional.ofNullable(getAddress()).map(addr -> JsonObject.mapFrom(addr))
      .ifPresent(record::setLocation);
    Optional.ofNullable(getStatus()).ifPresent(s -> record.setStatus(Status.valueOf(s.name())));
    Optional.ofNullable(getMetadata()).ifPresent(record::setMetadata);
    return record;
  }

  public static ServiceInfo from(Record r) {
    if (r == null) {
      return null;
    }
    ServiceInfo serviceInfo = new ServiceInfo(() -> r.getType());
    Optional.ofNullable(r.getRegistration()).ifPresent(id -> serviceInfo.id = id);
    Optional.ofNullable(r.getLocation()).map(json -> json.mapTo(Address.class))
      .ifPresent(addr -> serviceInfo.address = addr);
    Optional.ofNullable(r.getMetadata()).ifPresent(m -> serviceInfo.metadata = m);
    Optional.ofNullable(r.getName()).ifPresent(n -> serviceInfo.name = n);
    Optional.ofNullable(r.getStatus()).map(s -> s.name())
      .ifPresent(n -> serviceInfo.status = ServiceStatus.valueOf(n));
    return serviceInfo;
  }

  public String getName() {
    return this.name;
  }

  public Address getAddress() {
    return this.address;
  }

  public String getId() {
    return this.id;
  }

  public JsonObject getMetadata() {
    return this.metadata;
  }

  public ServiceStatus getStatus() {
    return this.status;
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof ServiceInfo)) return false;
    final ServiceInfo other = (ServiceInfo) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$serviceType = this.getServiceType();
    final Object other$serviceType = other.getServiceType();
    if (this$serviceType == null ? other$serviceType != null : !this$serviceType.equals(other$serviceType))
      return false;
    final Object this$name = this.getName();
    final Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    final Object this$address = this.getAddress();
    final Object other$address = other.getAddress();
    if (this$address == null ? other$address != null : !this$address.equals(other$address)) return false;
    final Object this$id = this.getId();
    final Object other$id = other.getId();
    if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
    final Object this$metadata = this.getMetadata();
    final Object other$metadata = other.getMetadata();
    if (this$metadata == null ? other$metadata != null : !this$metadata.equals(other$metadata)) return false;
    final Object this$status = this.getStatus();
    final Object other$status = other.getStatus();
    if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof ServiceInfo;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $serviceType = this.getServiceType();
    result = result * PRIME + ($serviceType == null ? 43 : $serviceType.hashCode());
    final Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    final Object $address = this.getAddress();
    result = result * PRIME + ($address == null ? 43 : $address.hashCode());
    final Object $id = this.getId();
    result = result * PRIME + ($id == null ? 43 : $id.hashCode());
    final Object $metadata = this.getMetadata();
    result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
    final Object $status = this.getStatus();
    result = result * PRIME + ($status == null ? 43 : $status.hashCode());
    return result;
  }

  public String toString() {
    return "ServiceInfo(serviceType=" + Optional.ofNullable(this.getServiceType()).map(i -> i.getType()).orElse("<unknown>") + ", name=" + this.getName() + ", address=" + this.getAddress() + ", id=" + this.getId() + ", metadata=" + this.getMetadata() + ", status=" + this.getStatus() + ")";
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Address {
    private String datacenter = "dc1";
    private String host;
    private int port;

    public Address() {
    }

    public void validate() {
      Objects.requireNonNull(host);
      if (port < 1) {
        throw new IllegalArgumentException("port must be positive number. " + port);
      }
    }

    public String getDatacenter() {
      return this.datacenter;
    }

    public String getHost() {
      return this.host;
    }

    public int getPort() {
      return this.port;
    }

    public void setDatacenter(String datacenter) {
      this.datacenter = datacenter;
    }

    public void setHost(String host) {
      this.host = host;
    }

    public void setPort(int port) {
      this.port = port;
    }

    public boolean equals(final Object o) {
      if (o == this) return true;
      if (!(o instanceof Address)) return false;
      final Address other = (Address) o;
      if (!other.canEqual((Object) this)) return false;
      final Object this$datacenter = this.getDatacenter();
      final Object other$datacenter = other.getDatacenter();
      if (this$datacenter == null ? other$datacenter != null : !this$datacenter.equals(other$datacenter))
        return false;
      final Object this$host = this.getHost();
      final Object other$host = other.getHost();
      if (this$host == null ? other$host != null : !this$host.equals(other$host)) return false;
      if (this.getPort() != other.getPort()) return false;
      return true;
    }

    protected boolean canEqual(final Object other) {
      return other instanceof Address;
    }

    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final Object $datacenter = this.getDatacenter();
      result = result * PRIME + ($datacenter == null ? 43 : $datacenter.hashCode());
      final Object $host = this.getHost();
      result = result * PRIME + ($host == null ? 43 : $host.hashCode());
      result = result * PRIME + this.getPort();
      return result;
    }

    public String toString() {
      return "ServiceInfo.Address(datacenter=" + this.getDatacenter() + ", host=" + this.getHost() + ", port=" + this.getPort() + ")";
    }
  }

}
