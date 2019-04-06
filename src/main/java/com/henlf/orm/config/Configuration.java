package com.henlf.orm.config;

import com.henlf.orm.JDBCProperties;
import com.henlf.orm.core.MapperInterface;
import com.henlf.orm.core.MapperMethod;
import com.henlf.orm.core.MapperProxy;
import com.henlf.orm.util.ClazzUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author firewoodMan
 */
public class Configuration {
    private JDBCProperties jdbcProperties = new JDBCProperties();
    private Map<Class<?>, MapperInterface> mapperInterfaceMap = new ConcurrentHashMap<>();

    public void addMapper(Class<?> clazz, MapperInterface mapperInterface) {
        this.mapperInterfaceMap.put(clazz, mapperInterface);
    }

    public <T> T getMapper(Class<T> type) {
        MapperInterface mapperInterface = mapperInterfaceMap.get(type);
        return mapperInterface.newInstance(new MapperProxy(mapperInterface));
    }

    public void setJdbcProperties(JDBCProperties jdbcProperties) {
        this.jdbcProperties = jdbcProperties;
    }

    public JDBCProperties getJdbcProperties() {
        return jdbcProperties;
    }
}
