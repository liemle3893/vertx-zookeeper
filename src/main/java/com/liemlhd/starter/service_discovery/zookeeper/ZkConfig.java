package com.liemlhd.starter.service_discovery.zookeeper;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "zookeeperHosts",
  "sessionTimeout",
  "connectTimeout",
  "rootPath",
  "retry"
})
public class ZkConfig {

  @JsonProperty("connection")
  private String zookeeperHosts;
  @JsonProperty("sessionTimeout")
  private int sessionTimeout;
  @JsonProperty("connectTimeout")
  private int connectTimeout;
  @JsonProperty("rootPath")
  private String rootPath;
  @JsonProperty("retry")
  private Retry retry;

  @JsonProperty("zookeeperHosts")
  public String getZookeeperHosts() {
    return zookeeperHosts;
  }

  @JsonProperty("zookeeperHosts")
  public void setZookeeperHosts(String zookeeperHosts) {
    this.zookeeperHosts = zookeeperHosts;
  }

  @JsonProperty("sessionTimeout")
  public int getSessionTimeout() {
    return sessionTimeout;
  }

  @JsonProperty("sessionTimeout")
  public void setSessionTimeout(int sessionTimeout) {
    this.sessionTimeout = sessionTimeout;
  }

  @JsonProperty("connectTimeout")
  public int getConnectTimeout() {
    return connectTimeout;
  }

  @JsonProperty("connectTimeout")
  public void setConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  @JsonProperty("rootPath")
  public String getRootPath() {
    return rootPath;
  }

  @JsonProperty("rootPath")
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @JsonProperty("retry")
  public Retry getRetry() {
    return retry;
  }

  @JsonProperty("retry")
  public void setRetry(Retry retry) {
    this.retry = retry;
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof ZkConfig)) return false;
    final ZkConfig other = (ZkConfig) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$zookeeperHosts = this.getZookeeperHosts();
    final Object other$zookeeperHosts = other.getZookeeperHosts();
    if (this$zookeeperHosts == null ? other$zookeeperHosts != null : !this$zookeeperHosts.equals(other$zookeeperHosts))
      return false;
    if (this.getSessionTimeout() != other.getSessionTimeout()) return false;
    if (this.getConnectTimeout() != other.getConnectTimeout()) return false;
    final Object this$rootPath = this.getRootPath();
    final Object other$rootPath = other.getRootPath();
    if (this$rootPath == null ? other$rootPath != null : !this$rootPath.equals(other$rootPath)) return false;
    final Object this$retry = this.getRetry();
    final Object other$retry = other.getRetry();
    if (this$retry == null ? other$retry != null : !this$retry.equals(other$retry)) return false;
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof ZkConfig;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $zookeeperHosts = this.getZookeeperHosts();
    result = result * PRIME + ($zookeeperHosts == null ? 43 : $zookeeperHosts.hashCode());
    result = result * PRIME + this.getSessionTimeout();
    result = result * PRIME + this.getConnectTimeout();
    final Object $rootPath = this.getRootPath();
    result = result * PRIME + ($rootPath == null ? 43 : $rootPath.hashCode());
    final Object $retry = this.getRetry();
    result = result * PRIME + ($retry == null ? 43 : $retry.hashCode());
    return result;
  }

  public String toString() {
    return "ZkConfig(zookeeperHosts=" + this.getZookeeperHosts() + ", sessionTimeout=" + this.getSessionTimeout() + ", connectTimeout=" + this.getConnectTimeout() + ", rootPath=" + this.getRootPath() + ", retry=" + this.getRetry() + ")";
  }

  static @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonPropertyOrder({
    "initialSleepTime",
    "intervalTimes",
    "maxTimes"
  })
  public class Retry {

    @JsonProperty("initialSleepTime")
    private int initialSleepTime;
    @JsonProperty("intervalTimes")
    private int intervalTimes;
    @JsonProperty("maxTimes")
    private int maxTimes;

    @JsonProperty("initialSleepTime")
    public int getInitialSleepTime() {
      return initialSleepTime;
    }

    @JsonProperty("initialSleepTime")
    public void setInitialSleepTime(int initialSleepTime) {
      this.initialSleepTime = initialSleepTime;
    }

    @JsonProperty("intervalTimes")
    public int getIntervalTimes() {
      return intervalTimes;
    }

    @JsonProperty("intervalTimes")
    public void setIntervalTimes(int intervalTimes) {
      this.intervalTimes = intervalTimes;
    }

    @JsonProperty("maxTimes")
    public int getMaxTimes() {
      return maxTimes;
    }

    @JsonProperty("maxTimes")
    public void setMaxTimes(int maxTimes) {
      this.maxTimes = maxTimes;
    }

    public boolean equals(final Object o) {
      if (o == this) return true;
      if (!(o instanceof Retry)) return false;
      final Retry other = (Retry) o;
      if (!other.canEqual((Object) this)) return false;
      if (this.getInitialSleepTime() != other.getInitialSleepTime()) return false;
      if (this.getIntervalTimes() != other.getIntervalTimes()) return false;
      if (this.getMaxTimes() != other.getMaxTimes()) return false;
      return true;
    }

    protected boolean canEqual(final Object other) {
      return other instanceof Retry;
    }

    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = result * PRIME + this.getInitialSleepTime();
      result = result * PRIME + this.getIntervalTimes();
      result = result * PRIME + this.getMaxTimes();
      return result;
    }

    public String toString() {
      return "ZkConfig.Retry(initialSleepTime=" + this.getInitialSleepTime() + ", intervalTimes=" + this.getIntervalTimes() + ", maxTimes=" + this.getMaxTimes() + ")";
    }
  }

}
