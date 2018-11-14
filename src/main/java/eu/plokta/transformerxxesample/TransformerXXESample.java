/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.plokta.transformerxxesample;

import java.io.InputStream;
import java.security.CodeSource;
import java.text.MessageFormat;
import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author plokta
 */
public class TransformerXXESample {

        public Transformer getTransformer(String impl, Boolean secPro, String extStyle, String extEnt) {
        InputStream is = getClass().getResourceAsStream("/stylesheet.xsl");
        StreamSource xslStreamSource = new StreamSource(is);
        TransformerFactory factory;
        
        if (impl == null)
            // use whatever factory is provided by javax.xml.transform.TransformerFactory
            factory = TransformerFactory.newInstance();
        else
            factory = TransformerFactory.newInstance(impl, null);
        
        System.out.println(getJaxpImplementationInfo("Used TransformerFactory: ",factory.getClass()));
        try {
            if (secPro)
                factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            if (extStyle != null )
                factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, extStyle);
            if (extEnt != null)
                factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, extEnt);

            Transformer transformer = factory.newTransformer(xslStreamSource);
            return transformer;
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

        // source: https://stackoverflow.com/a/1804281
        private static String getJaxpImplementationInfo(String componentName, Class componentClass) {
        CodeSource source = componentClass.getProtectionDomain().getCodeSource();
        return MessageFormat.format(
                "{0} implementation: {1} loaded from: {2}",
                componentName, componentClass.getName(),
                source == null ? "Java Runtime" : source.getLocation());
    }

}
