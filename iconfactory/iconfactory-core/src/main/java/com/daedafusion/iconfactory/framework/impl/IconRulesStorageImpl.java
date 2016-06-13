package com.daedafusion.iconfactory.framework.impl;

import com.daedafusion.sf.AbstractService;
import com.daedafusion.iconfactory.entities.IconRule;
import com.daedafusion.iconfactory.framework.IconRulesStorage;
import com.daedafusion.iconfactory.framework.providers.IconRulesStorageProvider;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by mphilpot on 7/23/14.
 */
public class IconRulesStorageImpl extends AbstractService<IconRulesStorageProvider> implements IconRulesStorage
{
    private static final Logger log = Logger.getLogger(IconRulesStorageImpl.class);

    @Override
    public List<IconRule> getRules(String domain)
    {
        return getSingleProvider().getRules(domain);
    }

    @Override
    public void setRule(IconRule rule)
    {
        getSingleProvider().setRule(rule);
    }

    @Override
    public IconRule getRule(Long id)
    {
        return getSingleProvider().getRule(id);
    }

    @Override
    public void deleteRule(Long id)
    {
        getSingleProvider().deleteRule(id);
    }

    @Override
    public Class getProviderInterface()
    {
        return IconRulesStorageProvider.class;
    }
}
