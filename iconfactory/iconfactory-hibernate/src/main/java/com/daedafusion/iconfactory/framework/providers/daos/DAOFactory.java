package com.daedafusion.iconfactory.framework.providers.daos;

import com.daedafusion.hibernate.dao.AbstractDAO;
import com.daedafusion.iconfactory.framework.providers.daos.impl.HibernateDAOFactory;
import org.apache.log4j.Logger;

/**
 * Created by mphilpot on 7/23/14.
 */
public abstract class DAOFactory
{
    private static final Logger log = Logger.getLogger(DAOFactory.class);

    public static final Class HIBERNATE = HibernateDAOFactory.class;

    public static DAOFactory instance()
    {
        try
        {
            return (DAOFactory) HIBERNATE.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new RuntimeException("Error creating DAOFactory impl", e);
        }
    }

    protected AbstractDAO instantiateDAO(Class daoClass)
    {
        try
        {
            return (AbstractDAO)daoClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new RuntimeException("Can not instantiate DAO", e);
        }
    }

    public abstract IconDAO getIconDAO();
    public abstract IconRuleDAO getIconRuleDAO();
    public abstract OrnamentSymbolDAO getOrnamentSymbolDAO();
}
