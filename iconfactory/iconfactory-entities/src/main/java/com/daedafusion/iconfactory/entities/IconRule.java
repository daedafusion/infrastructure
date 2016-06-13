package com.daedafusion.iconfactory.entities;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphilpot on 7/22/14.
 */
@Entity
@Table(name = "icon_rules", indexes = {
        @Index(name="rules_domain_index", columnList = "domain")
})
public class IconRule
{
    private static final Logger log = Logger.getLogger(IconRule.class);

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column
    private String domain;

    @Column
    private String rdfType;

    @Column
    private String iconDataProperty; // If set, the UI should use the data property as the image (TODO how to handle mimetype)

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "rule_id")
    private List<ModifierCondition> modifiers;

    public IconRule()
    {
        modifiers = new ArrayList<>();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public String getRdfType()
    {
        return rdfType;
    }

    public void setRdfType(String rdfType)
    {
        this.rdfType = rdfType;
    }

    public String getIconDataProperty()
    {
        return iconDataProperty;
    }

    public void setIconDataProperty(String iconDataProperty)
    {
        this.iconDataProperty = iconDataProperty;
    }

    public List<ModifierCondition> getModifiers()
    {
        return modifiers;
    }

    public void setModifiers(List<ModifierCondition> modifiers)
    {
        this.modifiers = modifiers;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof IconRule)) return false;

        IconRule iconRule = (IconRule) o;

        if (domain != null ? !domain.equals(iconRule.domain) : iconRule.domain != null) return false;
        if (iconDataProperty != null ? !iconDataProperty.equals(iconRule.iconDataProperty) : iconRule.iconDataProperty != null)
            return false;
        if (modifiers != null ? !modifiers.equals(iconRule.modifiers) : iconRule.modifiers != null) return false;
        if (rdfType != null ? !rdfType.equals(iconRule.rdfType) : iconRule.rdfType != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = domain != null ? domain.hashCode() : 0;
        result = 31 * result + (rdfType != null ? rdfType.hashCode() : 0);
        result = 31 * result + (iconDataProperty != null ? iconDataProperty.hashCode() : 0);
        result = 31 * result + (modifiers != null ? modifiers.hashCode() : 0);
        return result;
    }
}
