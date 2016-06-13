package com.daedafusion.iconfactory.framework.impl;

import com.daedafusion.sf.AbstractService;
import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.framework.IconStorage;
import com.daedafusion.iconfactory.framework.providers.IconStorageProvider;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by mphilpot on 7/23/14.
 */
public class IconStorageImpl extends AbstractService<IconStorageProvider> implements IconStorage
{
    private static final Logger log = Logger.getLogger(IconStorageImpl.class);

    @Override
    public List<Icon> getIcons(String domain)
    {
        return getSingleProvider().getIcons(domain);
    }

    @Override
    public Icon addIcon(Icon icon)
    {
        return getSingleProvider().addIcon(icon);
    }

    @Override
    public Icon getIcon(Long id)
    {
        return getSingleProvider().getIcon(id);
    }

    @Override
    public void deleteIcon(Long id)
    {
        getSingleProvider().deleteIcon(id);
    }

    @Override
    public Class getProviderInterface()
    {
        return IconStorageProvider.class;
    }
}
