package com.daedafusion.iconfactory.services;

import com.daedafusion.iconfactory.framework.exceptions.ObjectNotFoundException;
import com.daedafusion.iconfactory.framework.exceptions.StorageException;
import com.daedafusion.iconfactory.services.exceptions.ServiceException;
import com.daedafusion.sf.ServiceFramework;
import com.daedafusion.sf.ServiceFrameworkFactory;
import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.entities.Modifier;
import com.daedafusion.iconfactory.framework.IconCache;
import com.daedafusion.iconfactory.framework.IconProcessor;
import com.daedafusion.iconfactory.framework.IconStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by mphilpot on 7/22/14.
 */
@Path("runtime")
public class RuntimeService
{
    private static final Logger log = Logger.getLogger(RuntimeService.class);

    @GET
    @Path("{id}")
    @Produces("image/svg+xml")
    public Response getIconSvg(@PathParam("id") Long iconId,
                               @HeaderParam("x-icon-modifier") String modifier,
                               @HeaderParam("x-icon-size") @DefaultValue("32") Integer size)
    {
        try
        {
            ServiceFramework framework = ServiceFrameworkFactory.getInstance().getFramework();

            IconCache cache = framework.getService(IconCache.class);
            IconProcessor processor = framework.getService(IconProcessor.class);
            IconStorage iconStorage = framework.getService(IconStorage.class);

            StringBuilder buffer = new StringBuilder();
            buffer.append("svg").append(iconId).append(modifier).append(size);

            byte[] svg = cache.getIcon(buffer.toString());

            if(svg == null)
            {
                ObjectMapper mapper = new ObjectMapper();

//                Modifier mod = null;
//
//                if(modifier != null)
//                {
//                    mod = mapper.readValue(modifier, Modifier.class);
//                }

                Icon icon = iconStorage.getIcon(iconId);

//                png = processor.generateCompositeIcon(icon, mod, size);
                svg = icon.getIcon();

                cache.putIcon(buffer.toString(), svg);

                return Response.ok(svg).build();
            }
            else
            {
                return Response.ok(svg).build();
            }
        }
        catch (StorageException e)
        {
            log.error("", e);
            throw new ServiceException();
        }
        catch (ObjectNotFoundException e)
        {
            log.error("", e);
            throw new NotFoundException();
        }
    }

    @GET
    @Path("{id}")
    @Produces("image/png")
    public Response getIcon(@PathParam("id") Long iconId,
                            @HeaderParam("x-icon-modifier") String modifier,
                            @HeaderParam("x-icon-size") @DefaultValue("32") Integer size)
    {
        try
        {
            ServiceFramework framework = ServiceFrameworkFactory.getInstance().getFramework();

            IconCache cache = framework.getService(IconCache.class);
            IconProcessor processor = framework.getService(IconProcessor.class);
            IconStorage iconStorage = framework.getService(IconStorage.class);

            StringBuilder buffer = new StringBuilder();
            buffer.append(iconId).append(modifier).append(size);

            byte[] png = cache.getIcon(buffer.toString());

            if(png == null)
            {
                ObjectMapper mapper = new ObjectMapper();

                Modifier mod = null;

                if(modifier != null)
                {
                    mod = mapper.readValue(modifier, Modifier.class);
                }

                Icon icon = iconStorage.getIcon(iconId);

                png = processor.generateCompositeIcon(icon, mod, size);

                cache.putIcon(buffer.toString(), png);

                return Response.ok(png).build();
            }
            else
            {
                return Response.ok(png).build();
            }
        }
        catch (IOException | StorageException e)
        {
            log.error("", e);
            throw new ServiceException();
        }
        catch (ObjectNotFoundException e)
        {
            log.error("", e);
            throw new NotFoundException();
        }
    }
}
