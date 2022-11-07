package com.tcs.eas.api.tools.common.step;

import com.stanfy.gsonxml.XmlParserCreator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * 
 * @author 44745
 *
 */
public class XmlPullParserCreator implements XmlParserCreator {

    @Override
    public XmlPullParser createParser() {
        try {
            return XmlPullParserFactory.newInstance().newPullParser();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
