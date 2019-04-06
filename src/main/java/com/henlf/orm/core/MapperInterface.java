package com.henlf.orm.core;

import com.henlf.orm.annotation.parse.AnnotationParser;

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

    /**
     * 获取 MapperMethod
     * @param method
     * @return
     */
    public MapperMethod cachedMapperMethod(Method method) {
        MapperMethod mapperMethod = methodCache.get(method);
        if (null == mapperMethod) {
            AnnotationParser annotationParser = new AnnotationParser(mapperInterface);
            mapperMethod = annotationParser.parseAnnotation(method);
            methodCache.put(method, mapperMethod);
        }

        return mapperMethod;
    }

    public Map<Method, MapperMethod> getMethodCache() {
        return methodCache;
    }

    public Class<?> getMapperInterface() {
        return mapperInterface;
    }
}
