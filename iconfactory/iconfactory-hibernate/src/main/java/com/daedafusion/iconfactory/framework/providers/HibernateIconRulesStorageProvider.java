package com.daedafusion.iconfactory.framework.providers;

import com.daedafusion.hibernate.Transaction;
import com.daedafusion.hibernate.TransactionFactory;
import com.daedafusion.hibernate.listener.HibernateSessionFilter;
import com.daedafusion.iconfactory.framework.exceptions.ObjectNotFoundException;
import com.daedafusion.iconfactory.framework.exceptions.StorageException;
import com.daedafusion.sf.AbstractProvider;
import com.daedafusion.sf.LifecycleListener;
import com.daedafusion.iconfactory.entities.IconRule;
import com.daedafusion.iconfactory.framework.providers.daos.DAOFactory;
import com.daedafusion.iconfactory.framework.providers.daos.IconRuleDAO;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import java.util.List;

/**
 * Created by mphilpot on 7/23/14.
 */
public class HibernateIconRulesStorageProvider extends AbstractProvider implements IconRulesStorageProvider
{
    private static final Logger log = Logger.getLogger(HibernateIconRulesStorageProvider.class);

    public HibernateIconRulesStorageProvider()
    {
        addLifecycleListener(new LifecycleListener()
        {
            @Override
            public void init()
            {
                getServiceRegistry().addExternalResource(HibernateSessionFilter.class.getName(), new HibernateSessionFilter());
            }

            @Override
            public void start()
            {

            }

            @Override
            public void stop()
            {

            }

            @Override
            public void teardown()
            {

            }
        });
    }

    @Override
    public List<IconRule> getRules(String domain)
    {
        IconRuleDAO dao = DAOFactory.instance().getIconRuleDAO();

        Transaction tm = TransactionFactory.getInstance().get();

        try
        {
            tm.begin();

            List<IconRule> list = dao.findByDomain(domain);

            tm.commit();

            return list;
        }
        catch(HibernateException e)
        {
            throw new StorageException(e);
        }
        finally
        {
            if(tm.isActive())
                tm.rollback();
        }
    }

    @Override
    public void setRule(IconRule rule)
    {
        IconRuleDAO dao = DAOFactory.instance().getIconRuleDAO();

        Transaction tm = TransactionFactory.getInstance().get();

        try
        {
            tm.begin();

            dao.makePersistent(rule);

            tm.commit();
        }
        catch(HibernateException e)
        {
            throw new StorageException(e);
        }
        finally
        {
            if(tm.isActive())
                tm.rollback();
        }

    }

    @Override
    public IconRule getRule(Long id)
    {
        IconRuleDAO dao = DAOFactory.instance().getIconRuleDAO();

        Transaction tm = TransactionFactory.getInstance().get();

        try
        {
            tm.begin();

            IconRule rule = dao.findById(id);

            rule = tm.materialize(rule);

            tm.commit();

            return rule;
        }
        catch(org.hibernate.ObjectNotFoundException e)
        {
            throw new ObjectNotFoundException();
        }
        catch(HibernateException e)
        {
            throw new StorageException(e);
        }
        finally
        {
            if(tm.isActive())
                tm.rollback();
        }
    }

    @Override
    public void deleteRule(Long id)
    {
        IconRuleDAO dao = DAOFactory.instance().getIconRuleDAO();

        Transaction tm = TransactionFactory.getInstance().get();

        try
        {
            tm.begin();

            IconRule rule = dao.findById(id);

            if (rule != null)
            {
                dao.makeTransient(rule);
            }

            tm.commit();
        }
        catch(org.hibernate.ObjectNotFoundException e)
        {
            throw new ObjectNotFoundException();
        }
        catch(HibernateException e)
        {
            throw new StorageException(e);
        }
        finally
        {
            if(tm.isActive())
                tm.rollback();
        }
    }
}
