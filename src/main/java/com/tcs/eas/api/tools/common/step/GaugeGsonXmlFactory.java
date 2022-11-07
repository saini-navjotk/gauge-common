package com.tcs.eas.api.tools.common.step;

import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

public final class GaugeGsonXmlFactory {

    private static XmlParserCreator parserCreator = new XmlPullParserCreator();

    public static GsonXml getGsonXml() {
        return new GsonXmlBuilder()
                .setXmlParserCreator(parserCreator)
                .create();
    }

}
