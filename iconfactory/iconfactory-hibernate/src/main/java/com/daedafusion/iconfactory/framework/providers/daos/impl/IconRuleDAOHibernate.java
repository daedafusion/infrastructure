package com.daedafusion.iconfactory.framework.providers.daos.impl;

import com.daedafusion.hibernate.dao.AbstractDAO;
import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.entities.IconRule;
import com.daedafusion.iconfactory.framework.providers.daos.IconDAO;
import com.daedafusion.iconfactory.framework.providers.daos.IconRuleDAO;
import org.apache.log4j.Logger;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by mphilpot on 10/9/14.
 */
public class IconRuleDAOHibernate extends AbstractDAO<IconRule, Long> implements IconRuleDAO
{
    private static final Logger log = Logger.getLogger(IconRuleDAOHibernate.class);

    @Override
    public List<IconRule> findByDomain(String domain)
    {
        Query q = getSession().createQuery("from IconRule r where r.domain = :domain or r.domain is null")
                .setString("domain", domain);
        return q.list();
    }
}
