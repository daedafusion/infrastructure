package com.daedafusion.iconfactory.framework.providers;

import com.daedafusion.sf.AbstractProvider;
import com.daedafusion.sf.LifecycleListener;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import org.apache.log4j.Logger;

/**
 * Created by mphilpot on 7/23/14.
 */
public class EHCacheProvider extends AbstractProvider implements IconCacheProvider
{
    private static final Logger log = Logger.getLogger(EHCacheProvider.class);

    private Ehcache cache;

    public EHCacheProvider()
    {
        addLifecycleListener(new LifecycleListener()
        {
            @Override
            public void init()
            {
                CacheConfiguration config = new CacheConfiguration();
                config.setMaxBytesLocalHeap(getProperty("cacheSize", "100M"));
                config.persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.NONE));
                config.setMemoryStoreEvictionPolicy("LRU");
                config.eternal(true);
                config.setName("icon-cache");

                cache = new net.sf.ehcache.Cache(config);
                CacheManager.getInstance().addCache(cache);
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
                cache.dispose();
            }
        });
    }

    @Override
    public byte[] getIcon(String key)
    {
        Element e = cache.get(key);

        if(e == null)
            return null;

        return (byte[]) e.getObjectValue();
    }

    @Override
    public void putIcon(String key, byte[] png)
    {
        cache.put(new Element(key, png));
    }
}
