package com.daedafusion.iconfactory.framework.providers.daos.impl;

import com.daedafusion.hibernate.dao.AbstractDAO;
import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.framework.providers.daos.IconDAO;
import org.apache.log4j.Logger;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by mphilpot on 10/9/14.
 */
public class IconDAOHibernate extends AbstractDAO<Icon, Long> implements IconDAO
{
    private static final Logger log = Logger.getLogger(IconDAOHibernate.class);

    public List<Icon> listByDomain(String domain)
    {
        Query q = getSession().createQuery("from Icon i where i.domain = :domain or i.domain is null")
                .setString("domain", domain);
        return q.list();
    }
}
