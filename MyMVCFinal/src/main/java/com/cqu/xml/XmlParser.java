package com.cqu.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

//用于读取xml中需要扫描的包
public class XmlParser {
    public static String getBasePackage(String xml){

        SAXReader saxReader = new SAXReader();
        InputStream inputStream = XmlParser.class.getClassLoader().getResourceAsStream(xml);
        try {
            //xml文档对象
            Document read = saxReader.read(inputStream);
            Element rootElement = read.getRootElement();
            Element element = rootElement.element("component-scan");
            Attribute attribute = element.attribute("base-package");
            String text = attribute.getText();
            return text;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }
}
