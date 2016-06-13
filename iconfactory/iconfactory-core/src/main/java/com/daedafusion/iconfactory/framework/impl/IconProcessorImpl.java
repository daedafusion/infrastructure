package com.daedafusion.iconfactory.framework.impl;

import com.daedafusion.sf.AbstractService;
import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.entities.Modifier;
import com.daedafusion.iconfactory.entities.OrnamentSymbol;
import com.daedafusion.iconfactory.framework.IconProcessor;
import com.daedafusion.iconfactory.framework.providers.IconProcessorProvider;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by mphilpot on 7/23/14.
 */
public class IconProcessorImpl extends AbstractService<IconProcessorProvider> implements IconProcessor
{
    private static final Logger log = Logger.getLogger(IconProcessorImpl.class);

    @Override
    public byte[] generateCompositeIcon(Icon icon, Modifier modifier, Integer size) throws IOException
    {
        return getSingleProvider().generateCompositeIcon(icon, modifier, size);
    }

    @Override
    public List<OrnamentSymbol> getOrnamentSymbols()
    {
        return getSingleProvider().getOrnamentSymbols();
    }

    @Override
    public Class getProviderInterface()
    {
        return IconProcessorProvider.class;
    }
}
