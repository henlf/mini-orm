package com.henlf.orm;

public interface Session extends Cloneable {

    <T> T getMapper(Class<T> type);
}
