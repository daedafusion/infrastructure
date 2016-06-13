package com.daedafusion.iconfactory.entities;

import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * Created by mphilpot on 7/22/14.
 */
@Entity
@Table(name = "icon_symbol")
public class OrnamentSymbol
{
    private static final Logger log = Logger.getLogger(OrnamentSymbol.class);

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long   id;

    @Column
    private String name;

    @Column
    @Lob
    private String svg;

    @Column
    @Lob
    private String example; // b64 encoded png

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

    public String getSvg()
    {
        return svg;
    }

    public void setSvg(String svg)
    {
        this.svg = svg;
    }

    public String getExample()
    {
        return example;
    }

    public void setExample(String example)
    {
        this.example = example;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof OrnamentSymbol)) return false;

        OrnamentSymbol that = (OrnamentSymbol) o;

        if (example != null ? !example.equals(that.example) : that.example != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (svg != null ? !svg.equals(that.svg) : that.svg != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (svg != null ? svg.hashCode() : 0);
        result = 31 * result + (example != null ? example.hashCode() : 0);
        return result;
    }
}
