package cache;

import model.Service;

import java.lang.reflect.Proxy;

public class CacheProxy {
    private String filePath;

    public CacheProxy() {
    }

    public CacheProxy(String filePath) {
        this.filePath = filePath;
    }

    public Service cache(Service service) {
        ClassLoader classLoader = service.getClass().getClassLoader();
        Class<?>[] interfaces = service.getClass().getInterfaces();
        ServiceInvocationHandler serviceInvocationHandler = new ServiceInvocationHandler(service, filePath);
        return (Service) Proxy.newProxyInstance(classLoader, interfaces, serviceInvocationHandler);
    }
}
