package com.daedafusion.iconfactory;

import com.daedafusion.aniketos.AniketosClient;
import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.entities.IconRule;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created by mphilpot on 10/9/14.
 */
public class IconFactoryIntegrationDriver
{
    private static final Logger log = Logger.getLogger(IconFactoryIntegrationDriver.class);

    @Test
    public void listAvailable() throws Exception
    {
        System.setProperty("etcdHost", "192.168.59.103");

        AniketosClient ac = new AniketosClient();
        String token = ac.authenticate("test@test.com", "test").getToken();

        IconFactoryClient client = new IconFactoryClient();
        client.setAuthToken(token);

        List<Icon> list = client.getAvailableIcons("test.com");

        for(Icon i : list)
        {
            log.info(i.getName());
        }
    }

    @Test
    public void listRules() throws Exception
    {
        System.setProperty("etcdHost", "192.168.59.103");

        AniketosClient ac = new AniketosClient();
        String token = ac.authenticate("test@test.com", "test").getToken();

        IconFactoryClient client = new IconFactoryClient();
        client.setAuthToken(token);

        List<IconRule> list = client.getIconRules("test.com");

        for(IconRule i : list)
        {
            log.info(String.format("%s -> %d", i.getRdfType(), i.getModifiers().get(0).getIconId()));
        }
    }

    @Test
    public void getIcon() throws Exception
    {
        System.setProperty("etcdHost", "192.168.59.103");

        AniketosClient ac = new AniketosClient();
        String token = ac.authenticate("test@test.com", "test").getToken();

        IconFactoryClient client = new IconFactoryClient();
        client.setAuthToken(token);

        byte[] icon = client.getIcon("test.com", 2L);

        FileUtils.writeByteArrayToFile(new File("/tmp/test.png"), icon);
    }
}
