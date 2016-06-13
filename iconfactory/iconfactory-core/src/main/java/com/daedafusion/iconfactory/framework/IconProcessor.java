package com.daedafusion.iconfactory.framework;

import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.entities.Modifier;
import com.daedafusion.iconfactory.entities.OrnamentSymbol;

import java.io.IOException;
import java.util.List;

/**
 * Created by mphilpot on 7/22/14.
 */
public interface IconProcessor
{
    byte[] generateCompositeIcon(Icon icon, Modifier modifier, Integer size) throws IOException;

    List<OrnamentSymbol> getOrnamentSymbols();

}
