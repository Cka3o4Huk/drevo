// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.jsp;

import ru.ixxo.riddler.jsp.xml.HtmlXMLer;
import org.jdom.Document;
import org.jdom.Element;

// Referenced classes of package donreba.ice.jsp:
//            CommonSite

public class SimpleUI extends CommonSite
{

    public SimpleUI()
    {
    }

    public void generateTable(String s, Document document)
    {
    }

    public void createTable(String s, String s1, String s2)
    {
        xmler.setTag(HtmlXMLer.TABLE_NAME, s, s2);
        xmler.getElementById(s).setAttribute("class", s1);
        xmler.getElementById(s).setText(" ");
    }

    public void createForm(String s, String s1, String s2, String s3)
    {
        xmler.setTag(HtmlXMLer.FORM_NAME, s, s3);
        xmler.getElementById(s).setAttribute("action", s1);
        xmler.getElementById(s).setAttribute("method", s2);
        xmler.getElementById(s).setText(" ");
    }

    public void createHRef(String s, String s1, String s2)
    {
        xmler.setTag(HtmlXMLer.HREF_NAME, s, s2);
        xmler.getElementById(s).setAttribute("href", s1);
        xmler.getElementById(s).setText(" ");
    }

    public void createInput(String s, String s1, String s2, String s3)
    {
        xmler.setTag(HtmlXMLer.INPUT_NAME, s, s3);
        xmler.getElementById(s).setAttribute("name", s2);
        xmler.getElementById(s).setAttribute("type", s1);
    }

    public void createScriptExternal(String s, String s1, String s2)
    {
        createScript(s, s1, HtmlXMLer.HTML_ID);
        xmler.getElementById(s).setAttribute("src", s2);
        xmler.getElementById(s).setText(" ");
    }

    public void createScript(String s, String s1, String s2)
    {
        xmler.setTag(HtmlXMLer.SCRIPT_NAME, s, s2);
        xmler.getElementById(s).setAttribute("language", s1);
        xmler.getElementById(s).setText(" ");
    }

    public void createLayer(String s, String s1, String s2)
    {
        xmler.setTag(HtmlXMLer.LAYER_NAME, s, s2);
        xmler.getElementById(s).setAttribute("class", s1);
    }

    public void createRow(String s, String s1)
    {
        xmler.setTag(HtmlXMLer.ROW_NAME, s, s1);
        xmler.getElementById(s).setText(" ");
    }

    public void createCell(String s, String s1)
    {
        xmler.setTag(HtmlXMLer.CELL_NAME, s, s1);
        xmler.getElementById(s).setText(" ");
    }

    public void createCCS(String s, String s1)
    {
        xmler.setTag(HtmlXMLer.LINK_NAME, s, HtmlXMLer.HTML_ID);
        xmler.getElementById(s).setAttribute("rel", "stylesheet");
        xmler.getElementById(s).setAttribute("href", s1);
        xmler.getElementById(s).setAttribute("type", "text/css");
    }

    public void createImage(String s, String s1, String s2)
    {
        xmler.setTag(HtmlXMLer.IMAGE_NAME, s, s1);
        xmler.getElementById(s).setAttribute("src", s2);
    }

    public void out(String s)
    {
        String s1 = currentElement.getText() + s;
        currentElement.setText(s1);
    }

    public void init()
    {
        currentElement = xmler.getElementById(HtmlXMLer.BODY_ID);
    }

    public void prepare()
    {
        setTitle(title);
        createCCS("main_ccs", ccs);
        xmler.moveTagBefore("main_ccs", HtmlXMLer.HTML_ID, HtmlXMLer.BODY_ID);
        createScriptExternal("main_script", "javascript", defscr);
        xmler.moveTagBefore("main_script", HtmlXMLer.HTML_ID, HtmlXMLer.BODY_ID);
        xmler.getElementById("main_script").setText(" ");
    }

    public void printContent()
    {
    }

    public void parseParameters()
    {
    }

    public void execute()
    {
        init();
        parseParameters();
        prepare();
        printContent();
        super.execute();
    }

    public static String HTML_SPACE = "\240";
    public static String title = "Ice Project";
    public static String ccs = "ccs/main.css";
    public static String defscr = "scripts/main.js";
    public static Element currentElement = null;

}
