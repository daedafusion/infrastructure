package com.daedafusion.iconfactory.services;

import com.daedafusion.iconfactory.framework.exceptions.ObjectNotFoundException;
import com.daedafusion.iconfactory.framework.exceptions.StorageException;
import com.daedafusion.iconfactory.services.exceptions.ServiceException;
import com.daedafusion.iconfactory.services.exceptions.UnauthorizedException;
import com.daedafusion.sf.ServiceFramework;
import com.daedafusion.sf.ServiceFrameworkFactory;
import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.entities.IconRule;
import com.daedafusion.iconfactory.entities.OrnamentSymbol;
import com.daedafusion.iconfactory.framework.IconProcessor;
import com.daedafusion.iconfactory.framework.IconRulesStorage;
import com.daedafusion.iconfactory.framework.IconStorage;
import com.daedafusion.security.authorization.Authorization;
import com.daedafusion.security.bindings.SubjectUtil;
import com.daedafusion.security.common.Context;
import com.daedafusion.security.common.impl.DefaultContext;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mphilpot on 7/22/14.
 */
@Path("customize")
public class CustomizationService
{
    private static final Logger log = Logger.getLogger(CustomizationService.class);

    @GET
    @Path("ornaments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrnamentSymbol> getOrnamentSymbols()
    {
        try
        {
            ServiceFramework framework = ServiceFrameworkFactory.getInstance().getFramework();

            IconProcessor iconProcessor = framework.getService(IconProcessor.class);

            return iconProcessor.getOrnamentSymbols();
        }
        catch (StorageException e)
        {
            log.error("", e);
            throw new ServiceException();
        }
    }

    @GET
    @Path("icons")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Icon> getAvailableIcons(@HeaderParam("x-icon-domain") String domain)
    {
        try
        {
            ServiceFramework framework = ServiceFrameworkFactory.getInstance().getFramework();

            Authorization auth = framework.getService(Authorization.class);
            IconStorage iconStorage = framework.getService(IconStorage.class);

            Context context = new DefaultContext();
            context.addContext("domain", domain);

            if(auth.isAuthorized(SubjectUtil.getSubject(), URI.create("icons"), "GET", context))
            {
                return iconStorage.getIcons(domain);
            }
            else
            {
                throw new UnauthorizedException();
            }
        }
        catch (StorageException e)
        {
            log.error("", e);
            throw new ServiceException();
        }
    }

    @GET
    @Path("rules")
    @Produces(MediaType.APPLICATION_JSON)
    public List<IconRule> getRules(@HeaderParam("x-icon-domain") String domain)
    {
        try
        {
            ServiceFramework framework = ServiceFrameworkFactory.getInstance().getFramework();

            Authorization auth = framework.getService(Authorization.class);
            IconRulesStorage rulesStorage = framework.getService(IconRulesStorage.class);

            Context context = new DefaultContext();
            context.addContext("domain", domain);

            if(auth.isAuthorized(SubjectUtil.getSubject(), URI.create("icon:rules"), "GET", context))
            {
                List<IconRule> rules = rulesStorage.getRules(domain);

                Map<String, IconRule> map = new HashMap<>();

                for(IconRule rule : rules)
                {
                    if(map.containsKey(rule.getRdfType()))
                    {
                        // merge
                        map.get(rule.getRdfType()).getModifiers().addAll(rule.getModifiers());
                    }
                    else
                    {
                        map.put(rule.getRdfType(), rule);
                    }
                }

                return new ArrayList<IconRule>(map.values());
            }
            else
            {
                throw new UnauthorizedException();
            }
        }
        catch (StorageException e)
        {
            log.error("", e);
            throw new ServiceException();
        }
    }

    @POST
    @Path("rules")
    @Consumes(MediaType.APPLICATION_JSON)
    public void setRule(IconRule rule)
    {
        try
        {
            ServiceFramework framework = ServiceFrameworkFactory.getInstance().getFramework();

            Authorization auth = framework.getService(Authorization.class);
            IconRulesStorage rulesStorage = framework.getService(IconRulesStorage.class);

            Context context = new DefaultContext();
            context.addContext("rule.domain", rule.getDomain());

            if(auth.isAuthorized(SubjectUtil.getSubject(), URI.create("icons:rules"), "POST", context))
            {
                rulesStorage.setRule(rule);
            }
            else
            {
                throw new UnauthorizedException();
            }
        }
        catch (StorageException e)
        {
            log.error("", e);
            throw new ServiceException();
        }
    }

    @POST
    @Path("icons")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Icon uploadIcon(Icon icon)
    {
        try
        {
            ServiceFramework framework = ServiceFrameworkFactory.getInstance().getFramework();

            Authorization auth = framework.getService(Authorization.class);
            IconStorage iconStorage = framework.getService(IconStorage.class);

            Context context = new DefaultContext();
            context.addContext("icon.domain", icon.getDomain());

            if(auth.isAuthorized(SubjectUtil.getSubject(), URI.create("icons"), "POST", context))
            {
                return iconStorage.addIcon(icon);
            }
            else
            {
                throw new UnauthorizedException();
            }
        }
        catch (StorageException e)
        {
            log.error("", e);
            throw new ServiceException();
        }
    }

    @DELETE
    @Path("icon/{id}")
    public void deleteIcon(@HeaderParam("x-icon-domain") String domain,
                           @PathParam("id") Long id)
    {
        try
        {
            ServiceFramework framework = ServiceFrameworkFactory.getInstance().getFramework();

            Authorization auth = framework.getService(Authorization.class);
            IconStorage iconStorage = framework.getService(IconStorage.class);

            Icon icon = iconStorage.getIcon(id);

            Context context = new DefaultContext();
            context.addContext("domain", domain);
            context.addContext("icon.domain", icon.getDomain());

            if(auth.isAuthorized(SubjectUtil.getSubject(), URI.create("icons"), "DELETE", context))
            {
                iconStorage.deleteIcon(id);
            }
            else
            {
                throw new UnauthorizedException();
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

    @DELETE
    @Path("rule/{id}")
    public void deleteIconRule(@HeaderParam("x-icon-domain") String domain,
                               @PathParam("id") Long id)
    {
        try
        {
            ServiceFramework framework = ServiceFrameworkFactory.getInstance().getFramework();

            Authorization auth = framework.getService(Authorization.class);
            IconRulesStorage rulesStorage = framework.getService(IconRulesStorage.class);

            IconRule rule = rulesStorage.getRule(id);

            Context context = new DefaultContext();
            context.addContext("domain", domain);
            context.addContext("rule.domain", rule.getDomain());

            if(auth.isAuthorized(SubjectUtil.getSubject(), URI.create("icons:rules"), "DELETE", context))
            {
                rulesStorage.deleteRule(id);
            }
            else
            {
                throw new UnauthorizedException();
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


}
