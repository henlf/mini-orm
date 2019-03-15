package com.henlf.orm.core;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class MapperInterface {
    private Class<?> mapperInterface;
    private Map<Method, MapperMethod> methodCache;

    public MapperInterface(Class<?> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.mapperInterface = mapperInterface;
       this.methodCache = methodCache;
    }

    @SuppressWarnings("unchecked")
    public <T> T newInstance(MapperProxy mapperProxy) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{mapperInterface}, mapperProxy);
    }
}
