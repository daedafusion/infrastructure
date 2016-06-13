package com.daedafusion.iconfactory;

import com.daedafusion.client.AbstractClient;
import com.daedafusion.client.exceptions.NotFoundException;
import com.daedafusion.client.exceptions.ServiceErrorException;
import com.daedafusion.client.exceptions.UnauthorizedException;
import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.entities.IconRule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by mphilpot on 10/9/14.
 */
public class IconFactoryClient extends AbstractClient
{
    private static final Logger log = Logger.getLogger(IconFactoryClient.class);

    private ObjectMapper mapper;

    public IconFactoryClient()
    {
        this(null, null);
    }

    public IconFactoryClient(String url)
    {
        this(url, null);
    }

    public IconFactoryClient(String url, CloseableHttpClient client)
    {
        super("iconfactory", url, client);
        mapper = new ObjectMapper();
    }

    public byte[] getIcon(String domain, Long id) throws URISyntaxException, UnauthorizedException, IOException, ServiceErrorException
    {
        URI uri = new URIBuilder(baseUrl).setPath(String.format("/runtime/%d", id)).build();

        HttpGet get  = new HttpGet(uri);

        get.addHeader("x-icon-domain", domain);
        get.addHeader(AUTH, getAuthToken());
        get.addHeader(ACCEPT, "image/png");

        try(CloseableHttpResponse response = client.execute(get))
        {
            throwForStatus(response.getStatusLine());

            return EntityUtils.toByteArray(response.getEntity());
        }
        catch (IOException e)
        {
            throw new ServiceErrorException(e.getMessage(), e);
        }
    }

    public Icon uploadIcon(Icon icon) throws URISyntaxException, UnauthorizedException, IOException, ServiceErrorException
    {
        URI uri = new URIBuilder(baseUrl).setPath("/customize/icons").build();

        HttpPost post = new HttpPost(uri);

        post.addHeader(AUTH, getAuthToken());
        post.addHeader(ACCEPT, APPLICATION_JSON);

        post.setEntity(new StringEntity(mapper.writeValueAsString(icon), ContentType.APPLICATION_JSON));

        try(CloseableHttpResponse response = client.execute(post))
        {
            throwForStatus(response.getStatusLine());

            return mapper.readValue(EntityUtils.toString(response.getEntity()), Icon.class);
        }
        catch (IOException e)
        {
            throw new ServiceErrorException(e.getMessage(), e);
        }
    }

    public List<Icon> getAvailableIcons(String domain) throws URISyntaxException, UnauthorizedException, IOException, ServiceErrorException
    {
        URI uri = new URIBuilder(baseUrl).setPath("/customize/icons").build();

        HttpGet get  = new HttpGet(uri);

        get.addHeader("x-icon-domain", domain);
        get.addHeader(AUTH, getAuthToken());
        get.addHeader(ACCEPT, APPLICATION_JSON);

        try(CloseableHttpResponse response = client.execute(get))
        {
            throwForStatus(response.getStatusLine());

            return mapper.readValue(EntityUtils.toString(response.getEntity()), new TypeReference<List<Icon>>(){});
        }
        catch (IOException e)
        {
            throw new ServiceErrorException(e.getMessage(), e);
        }
    }

    public List<IconRule> getIconRules(String domain) throws URISyntaxException, UnauthorizedException, IOException, ServiceErrorException
    {
        URI uri = new URIBuilder(baseUrl).setPath("/customize/rules").build();

        HttpGet get  = new HttpGet(uri);

        get.addHeader("x-icon-domain", domain);
        get.addHeader(AUTH, getAuthToken());
        get.addHeader(ACCEPT, APPLICATION_JSON);

        try(CloseableHttpResponse response = client.execute(get))
        {
            throwForStatus(response.getStatusLine());

            return mapper.readValue(EntityUtils.toString(response.getEntity()), new TypeReference<List<IconRule>>(){});
        }
        catch (IOException e)
        {
            throw new ServiceErrorException(e.getMessage(), e);
        }
    }

    public void setRule(IconRule rule) throws URISyntaxException, UnauthorizedException, IOException, ServiceErrorException
    {
        URI uri = new URIBuilder(baseUrl).setPath("/customize/rules").build();

        HttpPost post = new HttpPost(uri);

        post.addHeader(AUTH, getAuthToken());

        post.setEntity(new StringEntity(mapper.writeValueAsString(rule), ContentType.APPLICATION_JSON));

        try(CloseableHttpResponse response = client.execute(post))
        {
            throwForStatus(response.getStatusLine());
        }
        catch (IOException e)
        {
            throw new ServiceErrorException(e.getMessage(), e);
        }
    }

    public void deleteRule(Long id) throws URISyntaxException, UnauthorizedException, IOException, ServiceErrorException
    {
        URI uri = new URIBuilder(baseUrl).setPath(String.format("/customize/rule/%d", id.longValue())).build();

        HttpDelete del = new HttpDelete(uri);

        del.addHeader(AUTH, getAuthToken());

        try(CloseableHttpResponse response = client.execute(del))
        {
            throwForStatus(response.getStatusLine());
        }
        catch (IOException e)
        {
            throw new ServiceErrorException(e.getMessage(), e);
        }
    }

    public void deleteIcon(Long id) throws URISyntaxException, UnauthorizedException, IOException, ServiceErrorException
    {
        URI uri = new URIBuilder(baseUrl).setPath(String.format("/customize/icon/%d", id.longValue())).build();

        HttpDelete del = new HttpDelete(uri);

        del.addHeader(AUTH, getAuthToken());

        try(CloseableHttpResponse response = client.execute(del))
        {
            throwForStatus(response.getStatusLine());
        }
        catch (IOException e)
        {
            throw new ServiceErrorException(e.getMessage(), e);
        }
    }

    private void throwForStatusNFE(StatusLine statusLine) throws NotFoundException, ServiceErrorException, UnauthorizedException
    {
        if(statusLine.getStatusCode() >= 300)
        {
            if(statusLine.getStatusCode() == 404)
            {
                throw new NotFoundException("Partition not found");
            }
            else if(statusLine.getStatusCode() == 401)
            {
                throw new UnauthorizedException(statusLine.getReasonPhrase());
            }
            else
            {
                throw new ServiceErrorException(statusLine.getReasonPhrase());
            }
        }
    }

    private void throwForStatus(StatusLine statusLine) throws ServiceErrorException, UnauthorizedException
    {
        if(statusLine.getStatusCode() >= 300)
        {
            if(statusLine.getStatusCode() == 401)
            {
                throw new UnauthorizedException(statusLine.getReasonPhrase());
            }
            else
            {
                throw new ServiceErrorException(statusLine.getReasonPhrase());
            }
        }
    }
}
