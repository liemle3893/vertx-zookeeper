= Starter

A thin, synchronous wrapper on vertx' Zookeeper Backend service discovery. Can be plug into any JVM based project.

```groovy
repositories {
	maven {
		url  "https://dl.bintray.com/liemle3893/Personal"
	}
}
dependencies {
  ...
  compile 'com.liemlhd.starter:vertx-zookeeper-starter:1.0'
  ...
}
```

Example usage:

```java
public class App {

    public static void main(String[] args) throws Exception {
        ServiceDiscovery discovery = new ZookeeperServiceDiscovery(Vertx.vertx(), createConfig());
        
        
        // Service was create with at service type.
        /**
        * @see {@link com.liemlhd.starter.service_discovery.ServiceType}
        * */
        ServiceInfo serviceInfo = new ServiceInfo(() -> "test");
        ServiceInfo.Address address = new ServiceInfo.Address();
        address.setHost("localhost");
        address.setPort(12345);
        serviceInfo.setName("test");
        serviceInfo.setAddress(address);
        // Register it
        Future<ServiceInfo> promise = discovery.register(serviceInfo);
        ServiceInfo s = promise.get();
        System.out.println("registered service info: " + s);
        
        // Do something with your service.
        
        // Search for it
        SearchCriteria searchCriteria = SearchCriteria.name("test");
        Future<List<ServiceInfo>> services = discovery.find( searchCriteria );
        System.out.println("services = " + services);
        // ...
        // Do something with your services.
          
        // Clean up
        // Your service will be clean after it shutdown anyway
        // but there a may be some delay, so for god's sake, DIY!
        discovery.deregister(serviceInfo.getId()); // We do use serviceInfo.getId, so dont forget to saved your registered service info. 
    }

    public static ZkConfig createConfig() {
        ZkConfig config = new ZkConfig();
        config.setZookeeperHosts("localhost:2181");
        return config;
    }

}
```


