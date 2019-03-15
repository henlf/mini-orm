package com.henlf.orm.core;

import com.henlf.orm.Session;
import com.henlf.orm.config.Configuration;

public class DefaultSession implements Session {
    private Configuration configuration;

    public DefaultSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type);
    }
}
