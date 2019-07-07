package com.liemlhd.starter.service_discovery;

public enum ServiceType implements IServiceType {
  HTTP {
    @Override
    public String getType() {
      return "http";
    }
  },
  HTTP_THRIFT {
    @Override
    public String getType() {
      return "http-thrift";
    }
  },
  BINARY_THRIFT {
    @Override
    public String getType() {
      return "framed-thrift";
    }
  },
  GRPC {
    @Override
    public String getType() {
      return "stream-grpc";
    }
  }
}
