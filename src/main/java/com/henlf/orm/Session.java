package com.henlf.orm;

public interface Session {

    <T> T getMapper(Class<T> type);
}
