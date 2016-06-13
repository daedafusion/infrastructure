package com.daedafusion.iconfactory.framework.providers;

import com.daedafusion.iconfactory.framework.providers.util.TranscoderUtil;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.w3c.dom.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by mphilpot on 7/23/14.
 */
public class BatikDriver
{
    private static final Logger log = Logger.getLogger(BatikDriver.class);

    @Test
    public void transcode() throws IOException, TranscoderException
    {
        InputStream in = BatikDriver.class.getClassLoader().getResourceAsStream("question37.svg");

        FileOutputStream out = new FileOutputStream(new File("/tmp/test.png"));

        TranscoderUtil.toPNG(in, 128, out);

        out.close();
    }

    @Test
    public void compositeTest() throws TranscoderException, IOException
    {
        System.setProperty("java.awt.headless", "true");

        long a = System.currentTimeMillis();

        InputStream iconIn = BatikDriver.class.getClassLoader().getResourceAsStream("icon_twitter.svg");
        InputStream ornamentIn = BatikDriver.class.getClassLoader().getResourceAsStream("ornament_warning.svg");

        ByteArrayOutputStream iconBaos = new ByteArrayOutputStream();
        ByteArrayOutputStream ornamentBaos = new ByteArrayOutputStream();

        TranscoderUtil.toPNG(iconIn, 32, iconBaos);
        TranscoderUtil.toPNG(ornamentIn, 16, ornamentBaos);

        ByteArrayInputStream iconBais = new ByteArrayInputStream(iconBaos.toByteArray());
        ByteArrayInputStream ornamentBais = new ByteArrayInputStream(ornamentBaos.toByteArray());

        BufferedImage iconImage = ImageIO.read(iconBais);
        BufferedImage ornamentImage = ImageIO.read(ornamentBais);

        //Try coloring the ornament red
        int width = ornamentImage.getWidth();
        int height = ornamentImage.getHeight();

        for(int xx = 0; xx < width; xx++)
        {
            for(int yy = 0; yy < height; yy++)
            {
                Color originalColor = new Color(ornamentImage.getRGB(xx, yy), true);
                if(originalColor.getAlpha() != 0)
                {
                    Color newColor = new Color(255, 0, 0, originalColor.getAlpha());
                    ornamentImage.setRGB(xx, yy, newColor.getRGB());
                }
            }
        }

        BufferedImage finalIcon = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = finalIcon.createGraphics();

        graphics.drawImage(iconImage, 16, 16, null);
        graphics.drawImage(ornamentImage, 0, 0, null);

        ImageIO.write(finalIcon, "png", new FileOutputStream(new File("test.png")));

        long b = System.currentTimeMillis();

        System.out.println(b-a);
    }

//    @Test
//    public void superimpose() throws IOException, TranscoderException
//    {
//        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
//        String parser = XMLResourceDescriptor.getXMLParserClassName();
//        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
//
//        Document icon = factory.createSVGDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, BatikDriver.class.getClassLoader().getResourceAsStream("icon_twitter.svg"));
//        Document ornament = factory.createSVGDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, BatikDriver.class.getClassLoader().getResourceAsStream("ornament_warning.svg"));
//
//        Node e = icon.importNode(ornament.getDocumentElement(), true);
//
//        Node attX = e.getOwnerDocument().createAttribute("x");
//        attX.setNodeValue("0");
//        e.getAttributes().setNamedItem(attX);
//        Node attY = e.getOwnerDocument().createAttribute("y");
//        attY.setNodeValue("0");
//        e.getAttributes().setNamedItem(attY);
//
//        Node width = e.getOwnerDocument().createAttribute("width");
//        width.setNodeValue("16");
//        e.getAttributes().setNamedItem(width);
//        Node height = e.getOwnerDocument().createAttribute("height");
//        height.setNodeValue("16");
//        e.getAttributes().setNamedItem(height);
//
//        icon.getDocumentElement().appendChild(e);
//
//        FileOutputStream out = new FileOutputStream(new File("test.png"));
//
//        TranscoderUtil.toPNG(icon, 64, out);
//
//        out.close();
//    }
//
//    @Test
//    public void moreAdvanced() throws IOException, TranscoderException
//    {
//        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
//        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
//        Document wrapper = impl.createDocument(svgNS, "svg", null);
//
//        Element svgRoot = wrapper.getDocumentElement();
//
//        svgRoot.setAttributeNS(null, "width", "64");
//        svgRoot.setAttributeNS(null, "height", "64");
//
//        String parser = XMLResourceDescriptor.getXMLParserClassName();
//        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
//
//        Document icon = factory.createSVGDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, BatikDriver.class.getClassLoader().getResourceAsStream("icon_twitter.svg"));
//        Document ornament = factory.createSVGDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, BatikDriver.class.getClassLoader().getResourceAsStream("ornament_warning.svg"));
//
//        icon.getDocumentElement().getChildNodes();
//
//        Node iconImport = wrapper.importNode(icon.getDocumentElement(), true);
//        Node ornamentImport = wrapper.importNode(ornament.getDocumentElement(), true);
//
//        setAttribute(iconImport, "x", "16");
//        setAttribute(iconImport, "y", "16");
//        setAttribute(iconImport, "width", "32");
//        setAttribute(iconImport, "height", "32");
//
//        wrapper.getDocumentElement().appendChild(iconImport);
//
//        setAttribute(ornamentImport, "x", "0");
//        setAttribute(ornamentImport, "y", "0");
//        setAttribute(ornamentImport, "width", "16");
//        setAttribute(ornamentImport, "height", "16");
//
//        wrapper.getDocumentElement().appendChild(ornamentImport);
//
//        OutputFormat format = new OutputFormat(wrapper);
//        StringWriter writer = new StringWriter();
//        XMLSerializer serial = new XMLSerializer(writer, format);
//        serial.serialize(wrapper);
//
//        System.out.println(writer.toString());
//
//        FileOutputStream out = new FileOutputStream(new File("test.png"));
//
//        TranscoderUtil.toPNG(icon, 64, out);
//
//        out.close();
//    }
//
//    @Test
//    public void boundingBox() throws IOException
//    {
//        String parser = XMLResourceDescriptor.getXMLParserClassName();
//        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
//
//        Document icon = factory.createSVGDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, BatikDriver.class.getClassLoader().getResourceAsStream("icon_twitter.svg"));
//
//        GVTBuilder builder = new GVTBuilder();
//        BridgeContext context = new BridgeContext(new UserAgentAdapter());
//
//        GraphicsNode gvtRoot = builder.build(context, icon);
//
//        System.out.println("width = " + Math.ceil(gvtRoot.getSensitiveBounds().getWidth()));
//        System.out.println("height = " + Math.ceil(gvtRoot.getSensitiveBounds().getHeight()));
//    }
//
//    @Test
//    public void compose() throws IOException
//    {
//        String parser = XMLResourceDescriptor.getXMLParserClassName();
//        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
//
//        Document icon = factory.createSVGDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, BatikDriver.class.getClassLoader().getResourceAsStream("icon_twitter.svg"));
//        Document ornament = factory.createSVGDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, BatikDriver.class.getClassLoader().getResourceAsStream("ornament_warning.svg"));
//
//        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
//        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
//        Document wrapper = impl.createDocument(svgNS, "svg", null);
//
//        Element svgRoot = wrapper.getDocumentElement();
//
//        svgRoot.setAttributeNS(null, "width", "64");
//        svgRoot.setAttributeNS(null, "height", "64");
//
//
//    }
//
//    private double getMaxDimension(Document doc)
//    {
//        GVTBuilder builder = new GVTBuilder();
//        BridgeContext context = new BridgeContext(new UserAgentAdapter());
//
//        GraphicsNode gvtRoot = builder.build(context, doc);
//
//        return Math.max(Math.ceil(gvtRoot.getSensitiveBounds().getWidth()),
//                Math.ceil(gvtRoot.getSensitiveBounds().getHeight()));
//    }
//
//    private void setAttribute(Node node, String attribute, String value)
//    {
//        Node attr = node.getOwnerDocument().createAttribute(attribute);
//        attr.setNodeValue(value);
//        node.getAttributes().setNamedItem(attr);
//    }
}
