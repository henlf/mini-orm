package com.henlf.orm.core;

import com.henlf.orm.util.ReturnTypeResolver;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public class MapperMethod {
    private SqlCommand sqlCommand;
    private MethodSignature methodSignature;

    public MapperMethod(Method method, Class<?> mapperInterface, SqlCommand sqlCommand) {
        this.sqlCommand = sqlCommand;
        this.methodSignature = new MethodSignature(method, mapperInterface);
    }

    public SqlCommand getSqlCommand() {
        return sqlCommand;
    }

    public void setSqlCommand(SqlCommand sqlCommand) {
        this.sqlCommand = sqlCommand;
    }

    public MethodSignature getMethodSignature() {
        return methodSignature;
    }

    public void setMethodSignature(MethodSignature methodSignature) {
        this.methodSignature = methodSignature;
    }

    public static class MethodSignature {
        private boolean returnsMany;
        private Class<?> returnType;
        private boolean returnsVoid;

        public MethodSignature(Method method, Class<?> clazz) {
            Type resolvedReturnType = ReturnTypeResolver.resolveReturnType(method, clazz);

            if (resolvedReturnType instanceof Class<?>) {
                this.returnType = (Class<?>) resolvedReturnType;
            } else if (resolvedReturnType instanceof ParameterizedType) {
                this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
            } else {
                this.returnType = method.getReturnType();
            }
            this.returnsVoid = void.class.equals(this.returnType);
            this.returnsMany = isCollection(this.returnType) || this.returnType.isArray();
        }

        private  <T> boolean isCollection(Class<T> type) {
            return Collection.class.isAssignableFrom(type);
        }

        public boolean isReturnsMany() {
            return returnsMany;
        }

        public void setReturnsMany(boolean returnsMany) {
            this.returnsMany = returnsMany;
        }

        public Class<?> getReturnType() {
            return returnType;
        }

        public void setReturnType(Class<?> returnType) {
            this.returnType = returnType;
        }

        public boolean isReturnsVoid() {
            return returnsVoid;
        }

        public void setReturnsVoid(boolean returnsVoid) {
            this.returnsVoid = returnsVoid;
        }
    }
}
