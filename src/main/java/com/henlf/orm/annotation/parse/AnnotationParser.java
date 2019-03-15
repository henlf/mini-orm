package com.henlf.orm.annotation.parse;

import com.henlf.orm.annotation.Delete;
import com.henlf.orm.annotation.Insert;
import com.henlf.orm.annotation.Select;
import com.henlf.orm.annotation.Update;
import com.henlf.orm.constants.SqlType;
import com.henlf.orm.core.MapperMethod;
import com.henlf.orm.core.SqlCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AnnotationParser {
    private static final Logger LOG = LoggerFactory.getLogger(AnnotationParser.class);

    private Class<?> clazz;
    private Set<Class<? extends Annotation>> annotationTypes = new HashSet<>();

    public AnnotationParser(Class<?> clazz) {
        this.clazz = clazz;

        annotationTypes.add(Delete.class);
        annotationTypes.add(Select.class);
        annotationTypes.add(Insert.class);
        annotationTypes.add(Update.class);
    }

    /**
     * 解析方法上的注解
     */
    public MapperMethod parseAnnotation(Method method) {
        Class<? extends Annotation> sqlAnnotationType = chooseAnnotationType(method);
        try {
            if (null != sqlAnnotationType) {
                Method[] methods = sqlAnnotationType.getMethods();

                Annotation annotation = method.getAnnotation(sqlAnnotationType);
                String sql = (String)sqlAnnotationType.getMethod("value").invoke(annotation);

                String statementId = clazz + "." + method.getName();
                SqlCommand sqlCommand = new SqlCommand(statementId, getSqlType(method), sql);

                return new MapperMethod(method, clazz, sqlCommand);
            }
        } catch (Exception e) {
            LOG.error("不能解析 SQL 注解的 value 方法", e);
        }

        return null;
    }

    private Class<? extends Annotation> chooseAnnotationType(Method method) {
        for (Class<? extends Annotation> type : annotationTypes) {
            Annotation annotation = method.getAnnotation(type);
            if (null != annotation) {
                return type;
            }
        }

        return null;
    }

    private SqlType getSqlType(Method method) {
        Class<? extends Annotation> sqlAnnotationType = chooseAnnotationType(method);
        if (null == sqlAnnotationType) {
            return null;
        }

        return SqlType.valueOf(sqlAnnotationType.getSimpleName().toUpperCase(Locale.ENGLISH));
    }
}
