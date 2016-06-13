package com.daedafusion.iconfactory.framework.providers;

import com.daedafusion.hibernate.Transaction;
import com.daedafusion.hibernate.TransactionFactory;
import com.daedafusion.hibernate.listener.HibernateSessionFilter;
import com.daedafusion.iconfactory.framework.exceptions.ObjectNotFoundException;
import com.daedafusion.iconfactory.framework.exceptions.StorageException;
import com.daedafusion.sf.AbstractProvider;
import com.daedafusion.sf.LifecycleListener;
import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.framework.providers.daos.DAOFactory;
import com.daedafusion.iconfactory.framework.providers.daos.IconDAO;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by mphilpot on 7/23/14.
 */
public class HibernateIconStorageProvider extends AbstractProvider implements IconStorageProvider
{
    private static final Logger log = Logger.getLogger(HibernateIconStorageProvider.class);

    public HibernateIconStorageProvider()
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
    public List<Icon> getIcons(String domain)
    {
        IconDAO dao = DAOFactory.instance().getIconDAO();

        Transaction tm = TransactionFactory.getInstance().get();

        try
        {
            tm.begin();

            List<Icon> list = dao.listByDomain(domain);

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
    public Icon addIcon(Icon icon)
    {
        IconDAO dao = DAOFactory.instance().getIconDAO();

        Transaction tm = TransactionFactory.getInstance().get();

        try
        {
            tm.begin();

            icon = dao.makePersistent(icon);

            tm.commit();

            return icon;
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
    public Icon getIcon(Long id)
    {
        IconDAO dao = DAOFactory.instance().getIconDAO();

        Transaction tm = TransactionFactory.getInstance().get();

        try
        {
            tm.begin();

            Icon icon = dao.findById(id);

            if (icon == null)
            {
                // Return default icon
                icon = new Icon();
                icon.setName("default");
                icon.setMimetype("image/svg");

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try
                {
                    IOUtils.copy(HibernateIconStorageProvider.class.getClassLoader().getResourceAsStream("question37.svg"), baos);
                    icon.setIcon(baos.toByteArray());
                }
                catch (IOException e)
                {
                    log.error("", e);
                }
            }
            else
            {
                icon = tm.materialize(icon);
            }

            tm.commit();

            return icon;
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
    public void deleteIcon(Long id)
    {
        IconDAO dao = DAOFactory.instance().getIconDAO();

        Transaction tm = TransactionFactory.getInstance().get();

        try
        {
            tm.begin();

            Icon icon = dao.findById(id);

            if (icon != null)
            {
                dao.makeTransient(icon);
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
