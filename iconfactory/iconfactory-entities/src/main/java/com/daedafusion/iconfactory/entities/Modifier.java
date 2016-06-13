package com.daedafusion.iconfactory.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphilpot on 7/22/14.
 */
@Entity
@Table(name = "icon_modifier")
public class Modifier
{
    private static final Logger log = Logger.getLogger(Modifier.class);

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "modifier_id")
    @JsonProperty(value = "o")
    private List<Ornament> ornaments;

    @Column
    @JsonProperty(value = "h")
    private String         haloColor;

    public Modifier()
    {
        ornaments = new ArrayList<>();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public List<Ornament> getOrnaments()
    {
        return ornaments;
    }

    public void setOrnaments(List<Ornament> ornaments)
    {
        this.ornaments = ornaments;
    }

    public String getHaloColor()
    {
        return haloColor;
    }

    public void setHaloColor(String haloColor)
    {
        this.haloColor = haloColor;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Modifier modifier = (Modifier) o;

        if (ornaments != null ? !ornaments.equals(modifier.ornaments) : modifier.ornaments != null) return false;
        return !(haloColor != null ? !haloColor.equals(modifier.haloColor) : modifier.haloColor != null);

    }

    @Override
    public int hashCode()
    {
        int result = ornaments != null ? ornaments.hashCode() : 0;
        result = 31 * result + (haloColor != null ? haloColor.hashCode() : 0);
        return result;
    }
}
