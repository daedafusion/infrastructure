package com.daedafusion.iconfactory.framework.providers.daos.impl;

import com.daedafusion.hibernate.dao.AbstractDAO;
import com.daedafusion.iconfactory.entities.OrnamentSymbol;
import com.daedafusion.iconfactory.framework.providers.daos.DAOFactory;
import com.daedafusion.iconfactory.framework.providers.daos.IconDAO;
import com.daedafusion.iconfactory.framework.providers.daos.IconRuleDAO;
import com.daedafusion.iconfactory.framework.providers.daos.OrnamentSymbolDAO;
import org.apache.log4j.Logger;

/**
 * Created by mphilpot on 7/23/14.
 */
public class HibernateDAOFactory extends DAOFactory
{
    private static final Logger log = Logger.getLogger(HibernateDAOFactory.class);

    @Override
    public IconDAO getIconDAO()
    {
        return (IconDAO) instantiateDAO(IconDAOHibernate.class);
    }

    @Override
    public IconRuleDAO getIconRuleDAO()
    {
        return (IconRuleDAO) instantiateDAO(IconRuleDAOHibernate.class);
    }

    @Override
    public OrnamentSymbolDAO getOrnamentSymbolDAO()
    {
        return (OrnamentSymbolDAO) instantiateDAO(OrnamentSymbolDAOHibernate.class);
    }

    public static class OrnamentSymbolDAOHibernate extends AbstractDAO<OrnamentSymbol, Long> implements OrnamentSymbolDAO {}
}
