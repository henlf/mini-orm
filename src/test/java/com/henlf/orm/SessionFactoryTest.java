package com.henlf.orm;

import com.henlf.mapper.TestMapper;
import com.henlf.orm.config.SessionFactory;
import org.junit.Assert;
import org.junit.Test;

public class SessionFactoryTest {
    @Test
    public void test_getSession() {
        SessionFactory factory = new SessionFactory("config.properties");
        Session session = factory.getSession();
        Assert.assertNotNull(session);
    }

    @Test
    public void test_getMapper() {
        SessionFactory factory = new SessionFactory("config.properties");
        Session session = factory.getSession();
        TestMapper testMapper = session.getMapper(TestMapper.class);
        testMapper.selectAll();
    }
}
