package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

public abstract class AbstractUserServiceWithCacheTest extends AbstractUserServiceTest{
    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void clearCache() throws Exception {
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
