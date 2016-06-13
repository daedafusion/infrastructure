package com.daedafusion.iconfactory.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by mphilpot on 3/31/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JsonTest
{
    private static final Logger log = Logger.getLogger(JsonTest.class);

    private ObjectMapper mapper = new ObjectMapper();

    private void assertAllFieldsSet(Object o)
    {
        try
        {
            for(PropertyDescriptor pd : Introspector.getBeanInfo(o.getClass()).getPropertyDescriptors())
            {
                assertThat(pd.getReadMethod().getName(), pd.getReadMethod().invoke(o), is(notNullValue()));
            }
        }
        catch(Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void jsonIcon() throws IOException
    {
        Icon x = new Icon();
        x.setDomain("a");
        x.setIcon(new byte[1]);
        x.setId(123L);
        x.setMimetype("text/null");
        x.setName("test");

        assertAllFieldsSet(x);

        String s = mapper.writeValueAsString(x);

        Icon y = mapper.readValue(s, Icon.class);

        assertThat(x, is(y));
        //assertThat(y.getIcon(), is(nullValue()));
        assertThat(x.hashCode(), is(y.hashCode()));
    }

    @Test
    public void jsonIconRule() throws IOException
    {
        IconRule x = new IconRule();
        x.setId(123L);
        x.setDomain("a");
        x.setIconDataProperty("http://");
        x.setRdfType("http://abc");
        x.getModifiers().add(new ModifierCondition());

        assertAllFieldsSet(x);

        String s = mapper.writeValueAsString(x);

        IconRule y = mapper.readValue(s, IconRule.class);

        assertThat(x, is(y));
        assertThat(x.hashCode(), is(y.hashCode()));
    }

    @Test
    public void jsonModifier() throws IOException
    {
        Modifier x = new Modifier();
        x.setId(123L);
        x.setHaloColor("#000");
        x.getOrnaments().add(new Ornament());

        assertAllFieldsSet(x);

        String s = mapper.writeValueAsString(x);

        Modifier y = mapper.readValue(s, Modifier.class);

        assertThat(x, is(y));
        assertThat(x.hashCode(), is(y.hashCode()));
    }

    @Test
    public void jsonModifierCondition() throws IOException
    {
        ModifierCondition x = new ModifierCondition();
        x.setId(123L);
        x.setValue("abc");
        x.setOperator(ModifierCondition.Operator.EQUALS);
        x.setDataPropertyUri("http://");
        x.setIconId(12L);
        x.setModifier(new Modifier());

        assertAllFieldsSet(x);

        String s = mapper.writeValueAsString(x);

        ModifierCondition y = mapper.readValue(s, ModifierCondition.class);

        assertThat(x, is(y));
        assertThat(x.hashCode(), is(y.hashCode()));
    }

    @Test
    public void jsonOrnament() throws IOException
    {
        Ornament x = new Ornament();
        x.setId(123L);
        x.setColor("#000");
        x.setLocation(Ornament.Location.N);
        x.setSymbolId(12L);

        assertAllFieldsSet(x);

        String s = mapper.writeValueAsString(x);

        Ornament y = mapper.readValue(s, Ornament.class);

        assertThat(x, is(y));
        assertThat(x.hashCode(), is(y.hashCode()));
    }

    @Test
    public void jsonOnramentSymbol() throws IOException
    {
        OrnamentSymbol x = new OrnamentSymbol();
        x.setId(123L);
        x.setExample("asdf");
        x.setName("test");
        x.setSvg("svg");

        assertAllFieldsSet(x);

        String s = mapper.writeValueAsString(x);

        OrnamentSymbol y = mapper.readValue(s, OrnamentSymbol.class);

        assertThat(x, is(y));
        assertThat(x.hashCode(), is(y.hashCode()));
    }
}
