package com.daedafusion.iconfactory;

import com.daedafusion.iconfactory.services.CustomizationService;
import com.daedafusion.iconfactory.services.RuntimeService;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by mphilpot on 7/22/14.
 */
public class Application extends ResourceConfig
{
    private static final Logger log = Logger.getLogger(Application.class);

    public Application()
    {
        super(
                RuntimeService.class,
                CustomizationService.class,
                JacksonJsonProvider.class
        );
    }
}
