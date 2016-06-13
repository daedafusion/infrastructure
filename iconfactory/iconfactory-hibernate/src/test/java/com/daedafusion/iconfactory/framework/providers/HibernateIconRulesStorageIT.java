package com.daedafusion.iconfactory.framework.providers;

import com.daedafusion.iconfactory.framework.exceptions.ObjectNotFoundException;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by mphilpot on 3/31/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HibernateIconRulesStorageIT
{
    private static final Logger log = Logger.getLogger(HibernateIconRulesStorageIT.class);
    private final HibernateIconRulesStorageProvider storage;

    public HibernateIconRulesStorageIT()
    {
        this.storage = new HibernateIconRulesStorageProvider();
    }

    @Test(expected = ObjectNotFoundException.class)
    public void test0getNotFound()
    {
        storage.getRule(0L);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void test0deleteNotFound()
    {
        storage.deleteRule(0L);
    }
}
