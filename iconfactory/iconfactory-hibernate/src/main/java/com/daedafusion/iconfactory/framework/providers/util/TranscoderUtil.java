package com.daedafusion.iconfactory.framework.providers.util;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by mphilpot on 7/23/14.
 */
public class TranscoderUtil
{
    private static final Logger log = Logger.getLogger(TranscoderUtil.class);

    public static void toPNG(InputStream in, int size, OutputStream out) throws TranscoderException
    {
        PNGTranscoder pngTranscoder = new PNGTranscoder();

        pngTranscoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) size);

        TranscoderInput input = new TranscoderInput(in);

        TranscoderOutput output = new TranscoderOutput(out);

        pngTranscoder.transcode(input, output);
    }

    public static void toPNG(Document doc, int size, OutputStream out) throws TranscoderException
    {
        PNGTranscoder pngTranscoder = new PNGTranscoder();

        pngTranscoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) size);

        TranscoderInput input = new TranscoderInput(doc);

        TranscoderOutput output = new TranscoderOutput(out);

        pngTranscoder.transcode(input, output);
    }
}
