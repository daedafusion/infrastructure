package com.daedafusion.iconfactory.framework.providers;

import com.daedafusion.hibernate.Transaction;
import com.daedafusion.hibernate.TransactionFactory;
import com.daedafusion.sf.AbstractProvider;
import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.entities.Modifier;
import com.daedafusion.iconfactory.entities.OrnamentSymbol;
import com.daedafusion.iconfactory.framework.providers.daos.DAOFactory;
import com.daedafusion.iconfactory.framework.providers.daos.OrnamentSymbolDAO;
import com.daedafusion.iconfactory.framework.providers.util.TranscoderUtil;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mphilpot on 7/23/14.
 */
public class BatikIconProcessorProvider extends AbstractProvider implements IconProcessorProvider
{
    private static final Logger log = Logger.getLogger(BatikIconProcessorProvider.class);

    @Override
    public byte[] generateCompositeIcon(Icon icon, Modifier modifier, Integer size) throws IOException
    {
        ByteArrayInputStream iconBais = new ByteArrayInputStream(icon.getIcon());

        if(icon.getMimetype().equals("image/svg+xml") || icon.getMimetype().equals("image/svg"))
        {
            // Transcode
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try
            {
                TranscoderUtil.toPNG(iconBais, size, baos);
            }
            catch (TranscoderException e)
            {
                throw new IOException(e);
            }
            iconBais = new ByteArrayInputStream(baos.toByteArray());
        }
        else
        {
            throw new RuntimeException(String.format("Unsupported mimetype :: %s", icon.getMimetype()));
        }

        BufferedImage iconImage = ImageIO.read(iconBais);

        BufferedImage finalIcon = new BufferedImage(size+(16*2), size+(16*2), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = finalIcon.createGraphics();

        graphics.drawImage(iconImage, 16, 16, null);

        // TODO process ornaments

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ImageIO.write(finalIcon, "png", out);

        graphics.dispose();

        return out.toByteArray();
    }

    @Override
    public List<OrnamentSymbol> getOrnamentSymbols()
    {
        OrnamentSymbolDAO dao = DAOFactory.instance().getOrnamentSymbolDAO();

        Transaction tm = TransactionFactory.getInstance().get();

        tm.begin();

        Collection<OrnamentSymbol> list = dao.findAll();

        for(OrnamentSymbol os : list)
        {
            os = tm.materialize(os);
        }

        tm.commit();

        return new ArrayList<OrnamentSymbol>(list);
    }
}
