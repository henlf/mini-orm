package com.henlf.reflect;

import com.henlf.mapper.TestMapper;
import com.henlf.orm.util.ReturnTypeResolver;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ReturnTypeTest {
    @Test
    public void test_returnType() {
        Class<TestMapper> clazz = TestMapper.class;
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            Type type = ReturnTypeResolver.resolveReturnType(method, clazz);
            System.out.println(method.getName() + "\t" + type);
        }
    }
}
