// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.jsp.xml;

import org.jdom.Element;

// Referenced classes of package donreba.ice.jsp.xmler:
//            CommonXMLer

public class HtmlXMLer extends CommonXMLer
{

    public HtmlXMLer()
    {
        createStructure();
    }

    public String generateUniqueID(String s, String s1)
    {
        return "[" + s1 + "," + s + "]";
    }

    public Element getElementByUniqueID(String s)
    {
        Element element = getElementById(s);
        return element;
    }

    public void setHtml()
    {
        Element element = new Element(HTML_NAME);
        setRoot(element, HTML_ID);
    }

    public void setTag(String s, String s1, String s2)
    {
        Element element = new Element(s);
        try
        {
            setElement(element, s1, s2);
        }
        catch (Exception exception)
        {
            outString(exception.getMessage());
        }
    }

    public void moveTagAfter(String s, String s1, String s2)
    {
        Element element = getElementById(s);
        Element element1 = element.getParent();
        element1.removeContent(element);
        ids.remove(s);
        try
        {
            setElementAfter(element, s, s1, s2);
        }
        catch (Exception exception)
        {
            outString(exception.getMessage());
        }
    }

    public void moveTagBefore(String s, String s1, String s2)
    {
        Element element = getElementById(s);
        Element element1 = element.getParent();
        element1.removeContent(element);
        ids.remove(s);
        try
        {
            setElementBefore(element, s, s1, s2);
        }
        catch (Exception exception)
        {
            outString(exception.getMessage());
        }
    }

    public void setHead()
    {
        setTag(HEAD_NAME, HEAD_ID, HTML_ID);
    }

    public void setTitle()
    {
        setTag(TITLE_NAME, TITLE_ID, HEAD_ID);
    }

    public void setBody()
    {
        setTag(BODY_NAME, BODY_ID, HTML_ID);
    }

    public void createStructure()
    {
        setHtml();
        setHead();
        setTitle();
        setBody();
    }

    public static String HTML_NAME = "html";
    public static String HEAD_NAME = "head";
    public static String TITLE_NAME = "title";
    public static String BODY_NAME = "body";
    public static String TABLE_NAME = "table";
    public static String ROW_NAME = "tr";
    public static String CELL_NAME = "td";
    public static String FORM_NAME = "form";
    public static String HREF_NAME = "a";
    public static String INPUT_NAME = "input";
    public static String IMAGE_NAME = "img";
    public static String LINK_NAME = "link";
    public static String SCRIPT_NAME = "script";
    public static String LAYER_NAME = "div";
    public static String HTML_ID = "html";
    public static String HEAD_ID = "head";
    public static String TITLE_ID = "title";
    public static String BODY_ID = "body";

}
