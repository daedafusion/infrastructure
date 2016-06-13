package com.daedafusion.iconfactory.framework.impl;

import com.daedafusion.sf.AbstractService;
import com.daedafusion.iconfactory.framework.IconCache;
import com.daedafusion.iconfactory.framework.providers.IconCacheProvider;
import org.apache.log4j.Logger;

/**
 * Created by mphilpot on 7/23/14.
 */
public class IconCacheImpl extends AbstractService<IconCacheProvider> implements IconCache
{
    private static final Logger log = Logger.getLogger(IconCacheImpl.class);

    @Override
    public byte[] getIcon(String key)
    {
        return getSingleProvider().getIcon(key);
    }

    @Override
    public void putIcon(String key, byte[] png)
    {
        getSingleProvider().putIcon(key, png);
    }

    @Override
    public Class getProviderInterface()
    {
        return IconCacheProvider.class;
    }
}
