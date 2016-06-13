package com.daedafusion.iconfactory.entities;

import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * Created by mphilpot on 7/22/14.
 */
@Entity
@Table(name = "icons", indexes = {
        @Index(name="icon_domain_index", columnList = "domain")
})
public class Icon
{
    private static final Logger log = Logger.getLogger(Icon.class);

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long   id;

    @Column
    private String domain; // null if global

    @Column
    private String name;

    @Column
    private String mimetype;

    @Column
    @Lob
    private byte[] icon;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMimetype()
    {
        return mimetype;
    }

    public void setMimetype(String mimetype)
    {
        this.mimetype = mimetype;
    }

    public byte[] getIcon()
    {
        return icon;
    }

    public void setIcon(byte[] icon)
    {
        this.icon = icon;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Icon icon = (Icon) o;

        if (domain != null ? !domain.equals(icon.domain) : icon.domain != null) return false;
        if (name != null ? !name.equals(icon.name) : icon.name != null) return false;
        return !(mimetype != null ? !mimetype.equals(icon.mimetype) : icon.mimetype != null);

    }

    @Override
    public int hashCode()
    {
        int result = domain != null ? domain.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mimetype != null ? mimetype.hashCode() : 0);
        return result;
    }
}
