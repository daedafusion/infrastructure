package com.daedafusion.iconfactory.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * Created by mphilpot on 7/22/14.
 */
@Entity
@Table(name = "icon_ornament")
public class Ornament
{
    private static final Logger log = Logger.getLogger(Ornament.class);

    public enum Location { N, S, E, W, NE, SE, NW, SW }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "l")
    private Location location;

    @Column
    @JsonProperty(value = "c")
    private String   color; // web form "#aabbcc"

    @Column
    @JsonProperty(value = "s")
    private Long     symbolId;

    public Ornament()
    {

    }

    public Ornament(Location location, String color, Long symbolId)
    {
        this.location = location;
        this.color = color;
        this.symbolId = symbolId;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public Long getSymbolId()
    {
        return symbolId;
    }

    public void setSymbolId(Long symbolId)
    {
        this.symbolId = symbolId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Ornament)) return false;

        Ornament ornament = (Ornament) o;

        if (color != null ? !color.equals(ornament.color) : ornament.color != null) return false;
        if (location != ornament.location) return false;
        if (symbolId != null ? !symbolId.equals(ornament.symbolId) : ornament.symbolId != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (symbolId != null ? symbolId.hashCode() : 0);
        return result;
    }
}
