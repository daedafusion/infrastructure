package com.daedafusion.iconfactory.entities;

import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * Created by mphilpot on 7/22/14.
 */
@Entity
@Table(name = "icon_modifier_conditions")
public class ModifierCondition
{
    private static final Logger log = Logger.getLogger(ModifierCondition.class);

    public enum Operator { EQUALS, CONTAINS, REGEX_MATCHES }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "icon_id")
    private Long iconId;

    @Column
    private String dataPropertyUri;

    @Column
    @Enumerated(EnumType.STRING)
    private Operator operator;

    @Column
    private String value;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "modifier_condition_modifier",
        joinColumns = @JoinColumn(name = "condition_id"),
        inverseJoinColumns = @JoinColumn(name = "modifier_id")
    )
    private Modifier modifier;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDataPropertyUri()
    {
        return dataPropertyUri;
    }

    public void setDataPropertyUri(String dataPropertyUri)
    {
        this.dataPropertyUri = dataPropertyUri;
    }

    public Operator getOperator()
    {
        return operator;
    }

    public void setOperator(Operator operator)
    {
        this.operator = operator;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public Long getIconId()
    {
        return iconId;
    }

    public void setIconId(Long iconId)
    {
        this.iconId = iconId;
    }

    public Modifier getModifier()
    {
        return modifier;
    }

    public void setModifier(Modifier modifier)
    {
        this.modifier = modifier;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModifierCondition that = (ModifierCondition) o;

        if (dataPropertyUri != null ? !dataPropertyUri.equals(that.dataPropertyUri) : that.dataPropertyUri != null)
            return false;
        if (iconId != null ? !iconId.equals(that.iconId) : that.iconId != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (operator != that.operator) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = iconId != null ? iconId.hashCode() : 0;
        result = 31 * result + (dataPropertyUri != null ? dataPropertyUri.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        return result;
    }
}
