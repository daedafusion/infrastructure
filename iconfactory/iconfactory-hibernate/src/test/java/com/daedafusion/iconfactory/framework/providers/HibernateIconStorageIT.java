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
public class HibernateIconStorageIT
{
    private static final Logger log = Logger.getLogger(HibernateIconStorageIT.class);
    private final HibernateIconStorageProvider storage;

    public HibernateIconStorageIT()
    {
        this.storage = new HibernateIconStorageProvider();
    }

    @Test(expected = ObjectNotFoundException.class)
    public void test0getNotFound()
    {
        storage.getIcon(0L);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void test0deleteNotFound()
    {
        storage.deleteIcon(0L);
    }
}
