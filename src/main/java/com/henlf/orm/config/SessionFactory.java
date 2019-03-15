package com.henlf.orm.config;

import com.henlf.orm.JDBCProperties;
import com.henlf.orm.Session;
import com.henlf.orm.annotation.parse.AnnotationParser;
import com.henlf.orm.core.DefaultSession;
import com.henlf.orm.core.MapperInterface;
import com.henlf.orm.core.MapperMethod;
import com.henlf.orm.util.ClazzUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SessionFactory {
    private Configuration configuration = new Configuration();

    public SessionFactory(String configLocation) {
        parse(configLocation);
    }

    private void parse(String location) {
        if (null == location || location.trim().length() == 0) {
            throw new IllegalArgumentException("can't find config location");
        }

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location);
        Properties properties = new Properties();
        try {
            properties.load(is);

            JDBCProperties jdbcProperties = new JDBCProperties();
            jdbcProperties.setUrl(properties.getProperty("jdbc.url", null));
            jdbcProperties.setDriver(properties.getProperty("jdbc.driver", null));
            jdbcProperties.setUsername(properties.getProperty("jdbc.username", null));
            jdbcProperties.setPassword(properties.getProperty("jdbc.password", null));
            configuration.setJdbcProperties(jdbcProperties);

            parseMapper(properties.getProperty("mapper.location", null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析指定包下所有 Mapper 接口
     * @param mapperLocation 包名
     */
    private void parseMapper(String mapperLocation) {
        if (null == mapperLocation || mapperLocation.trim().length() == 0) {
            throw new IllegalArgumentException("can't find mapper location");
        }

        try {
            Set<Class<?>> clazzSet = ClazzUtils.getClassSet(mapperLocation);
            clazzSet.forEach(this::parseAnnotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseAnnotation(Class<?> clazz) {
        Map<Method, MapperMethod> methodCache = new HashMap<>();

        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            AnnotationParser parser = new AnnotationParser(clazz);
            MapperMethod mapperMethod = parser.parseAnnotation(method);

            methodCache.put(method, mapperMethod);

            addMapper(clazz, methodCache);
        }
    }

    private void addMapper(Class<?> clazz, Map<Method, MapperMethod> methodCache) {
        MapperInterface mapperInterface = new MapperInterface(clazz, methodCache);
        this.configuration.addMapper(clazz, mapperInterface);
    }

    public Session getSession() {
        return new DefaultSession(configuration);
    }
}
