// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.jsp.xml;

import ru.ixxo.riddler.common.Logger;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

public class CommonXMLer
{
    public void initWriter(Writer writer)
    {
        out = writer;
    }

    public void outString(String s)
    {
        try
        {
            out.write(s);
        }
        catch (IOException ioexception) { }
    }

    public CommonXMLer()
    {
        ids = new HashMap();
    }

    public CommonXMLer(Element element, String s)
    {
        setRoot(element, s);
    }

    public Element getRoot()
    {
        if (xml == null)
            return null;
        else
            return xml.getRootElement();
    }

    public void setRoot(Element element, String s)
    {
        if (element != null)
        {
            if (xml == null)
                xml = new Document(element);
            else
                xml.setRootElement(element);
            ids.put(s, element);
        }
    }

    public void setElementAfter(Element element, String s, String s1, String s2)
        throws Exception
    {
        setElementByOrder(element, s, s1, s2, AFTER_ELEMENT);
    }

    public void setElementBefore(Element element, String s, String s1, String s2)
        throws Exception
    {
        setElementByOrder(element, s, s1, s2, BEFORE_ELEMENT);
    }

    public void setElement(Element element, String s, String s1)
        throws Exception
    {
        setElementByOrder(element, s, s1, "", "");
    }

    public void setElementByOrder(Element element, String s, String s1, String s2, String s3)
        throws Exception
    {
        if (xml == null)
            throw new Exception("XML isn't exist. Please create ");
        if (ids.containsKey(s))
            throw new Exception("This ID is exist in xml. Please use other id: " + s);
        Element element1 = getElementById(s1);
        Element element2 = getElementById(s2);
        if (element1 == null)
            element1 = getRoot();
        List list = element1.getChildren();
        int i = list.indexOf(element2);
        if (s3.compareTo(AFTER_ELEMENT) == 0)
        {
            list.add(i + 1, element);
            element1.removeChildren();
            element1.setChildren(list);
        } else
        if (s3.compareTo(BEFORE_ELEMENT) == 0)
        {
            list.add(i, element);
            element1.removeChildren();
            element1.setChildren(list);
        } else
        {
            element1.addContent(element);
        }
        ids.put(s, element);
    }

    public Element getElementById(String s)
    {
        return (Element)ids.get(s);
    }

    public void outXML()
    {
        XMLOutputter xmloutputter = new XMLOutputter();
        try
        {
            xmloutputter.output(xml, out);
            if (Logger.isLogging())
                Logger.logXML(xml, xmloutputter);
        }
        catch (IOException ioexception)
        {
            Logger.log("Can't execute: out XML!!!" + ioexception.getMessage(), "Error");
        }
    }

    public static String BEFORE_ELEMENT = "before";
    public static String AFTER_ELEMENT = "after";
    public static String attr_id_name = "id";
    public Writer out;
    public Document xml;
    protected HashMap ids;

}
