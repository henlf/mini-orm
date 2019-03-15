package com.henlf.orm;

import com.henlf.orm.config.SessionFactory;
import org.junit.Test;

public class SessionFactoryTest {
    @Test
    public void test() {
        SessionFactory factory = new SessionFactory("config.properties");
        Session session = factory.getSession();
    }

}
