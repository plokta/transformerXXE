/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.plokta.transformerxxesample;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 *
 * @author plokta
 */
public class TransformerXXESampleTest {

    String xmlString = "<test>Hello World</test>";
    StreamSource src = new StreamSource(new StringReader(xmlString));

    //@Ignore
    @Test
    public void testXalanTransformer() throws TransformerException {
        StringWriter writer = new StringWriter();
        Result res = new StreamResult(writer);
        TransformerXXESample txs = new TransformerXXESample();
        Transformer transformer = txs.getTransformer("org.apache.xalan.processor.TransformerFactoryImpl",true, null, null);
        assertNotNull(transformer);
        transformer.transform(src, res);
        String result = writer.toString();
        assertFalse(result, result.contains("XXE"));
    }

    //@Ignore
    @Test
    public void testSaxonTransformer() throws TransformerException {
        StringWriter writer = new StringWriter();
        Result res = new StreamResult(writer);
        TransformerXXESample txs = new TransformerXXESample();
        Transformer transformer = txs.getTransformer("net.sf.saxon.TransformerFactoryImpl",true, null, null);
        assertNotNull(transformer);
        transformer.transform(src, res);
        String result = writer.toString();
        assertFalse(result, result.contains("XXE"));
    }

    // "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl" (which is
    // the default TF afaik) does indeed restrict external stylesheet access
    // when FEATURE_SECURE_PROCESSING is set. expect an exception here
    @Test(expected = java.lang.Exception.class)
    public void testInternalTransformer() throws TransformerException {
        StringWriter writer = new StringWriter();
        Result res = new StreamResult(writer);
        TransformerXXESample txs = new TransformerXXESample();
        Transformer transformer = txs.getTransformer("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl", true, null, null);
        assertNotNull(transformer);

        // throws:
        // 'java.lang.Exception: Could not read stylesheet target 'extStylesheet.xsl',
        // because 'https' access is not allowed due to restriction set by the accessExternalStylesheet property.'
        transformer.transform(src, res);
        String result = writer.toString();
        assertFalse(result, result.contains("XXE"));
    }

    @Test
    public void testClasspathTransformer() throws TransformerException {
        StringWriter writer = new StringWriter();
        Result res = new StreamResult(writer);
        TransformerXXESample txs = new TransformerXXESample();
        Transformer transformer = txs.getTransformer(null, true, null, null);
        assertNotNull(transformer);
        transformer.transform(src, res);
        String result = writer.toString();
        assertFalse(result, result.contains("XXE"));
    }
}
