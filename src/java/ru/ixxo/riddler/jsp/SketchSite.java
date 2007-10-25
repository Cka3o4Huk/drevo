// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.jsp;

import ru.ixxo.riddler.common.Logger;
import ru.ixxo.riddler.jsp.xml.HtmlXMLer;
import org.jdom.Element;

// Referenced classes of package donreba.ice.jsp:
//            SimpleUI

public class SketchSite extends SimpleUI
{

    public SketchSite()
    {
    }

    public void init()
    {
        createMenu = "initMenu();";
        str_currentURL = "index.jsp";
        str_currentPath = "Root";
    }

    public void printContent()
    {
        basicInit();
        basicInitSettings();
        basicInitAvailableControls();
        basicInitCurrentPath();
        basicInitMenu();
        basicInitTopMenu();
    }

    public void basicInit()
    {
        basicInitBody();
        basicInitRootTable();
        basicInitLogo();
        basicInitMiddleLevel();
        basicInitLeftMenu();
        basicInitMainSheet();
    }

    protected void basicInitBody()
    {
        body = xmler.getElementById(HtmlXMLer.BODY_ID);
        body.setAttribute("id", "body");
        body.setAttribute("topmargin", "0");
        body.setAttribute("leftmargin", "0");
        body.setAttribute("rightmargin", "0");
        body.setAttribute("bottommargin", "0");
    }

    protected void basicInitRootTable()
    {
        createTable("root_table", "root", HtmlXMLer.BODY_ID);
        rootTable = xmler.getElementById("root_table");
        rootTable.setAttribute("class", "root");
        rootTable.setAttribute("cellSpacing", "0");
        rootTable.setAttribute("cellPadding", "0");
    }

    protected void basicInitLogo()
    {
        createRow("root_high_level", "root_table");
        createCell("logo_cell", "root_high_level");
        xmler.getElementById("logo_cell").setAttribute("colspan", "2");
        xmler.getElementById("logo_cell").setAttribute("class", "root");
        createImage("logo_image", "logo_cell", "img/elite/snow&fon.jpg");
        xmler.getElementById("logo_image").setAttribute("width", "75");
        xmler.getElementById("logo_image").setAttribute("height", "75");
        createImage("logo2_image", "logo_cell", "img/logo2.gif");
        createCell("toptoolbar_cell", "root_high_level");
        xmler.getElementById("toptoolbar_cell").setAttribute("class", "root");
        xmler.getElementById("toptoolbar_cell").setAttribute("id", "TopToolBar");
        xmler.getElementById("toptoolbar_cell").setAttribute("valign", "top");
        xmler.getElementById("toptoolbar_cell").setAttribute("align", "right");
    }

    protected void basicInitMiddleLevel()
    {
        createRow("middle_level", "root_table");
        createRow("middle2_level", "root_table");
        createCell("middle_cell", "middle_level");
        xmler.getElementById("middle_cell").setAttribute("colspan", "3");
        xmler.getElementById("middle_cell").setAttribute("background", "img/bg_hdr_menu_t.gif");
        xmler.getElementById("middle_cell").setAttribute("height", "1");
        createCell("middle2_cell", "middle2_level");
        xmler.getElementById("middle2_cell").setAttribute("colspan", "3");
        xmler.getElementById("middle2_cell").setAttribute("background", "img/bg_hdr_menu.gif");
        xmler.getElementById("middle2_cell").setText(HTML_SPACE + "Welcome, Guest!");
        createRow("middle3_level", "root_table");
        createCell("middle3_cell", "middle3_level");
        xmler.getElementById("middle3_cell").setAttribute("colspan", "3");
        xmler.getElementById("middle3_cell").setAttribute("nowrap", "");
        xmler.getElementById("middle3_cell").setText(" ");
        createRow("middle4_level", "root_table");
        createCell("middle4_cell", "middle4_level");
        xmler.getElementById("middle4_cell").setAttribute("colspan", "3");
        xmler.getElementById("middle4_cell").setAttribute("nowrap", "");
        xmler.getElementById("middle4_cell").setText(HTML_SPACE + "Something");
        createRow("middle5_level", "root_table");
        createCell("middle5_cell", "middle5_level");
        xmler.getElementById("middle5_cell").setAttribute("colspan", "3");
        xmler.getElementById("middle5_cell").setAttribute("background", "img/bg_hdr_menu_t.gif");
        xmler.getElementById("middle5_cell").setAttribute("height", "1");
        greeting = xmler.getElementById("middle2_cell");
        controls = xmler.getElementById("middle4_cell");
        currentPath = xmler.getElementById("middle3_cell");
    }

    protected void basicInitLeftMenu()
    {
        createRow("main_level", "root_table");
        createCell("left_menu_cell", "main_level");
        xmler.getElementById("left_menu_cell").setAttribute("id", "LeftMenu");
        xmler.getElementById("left_menu_cell").setAttribute("width", "14%");
        createTable("left_menu_cell_table", "menu", "left_menu_cell");
        xmler.getElementById("left_menu_cell_table").setAttribute("width", "100%");
        xmler.getElementById("left_menu_cell_table").setText("Control's menu:");
        for (int i = 1; i < 7; i++)
        {
            createRow("leftcontrol_" + i, "left_menu_cell_table");
            createCell("leftcell_" + i, "leftcontrol_" + i);
            createHRef("leftcell_href_" + i, "#", "leftcell_" + i);
            xmler.getElementById("leftcell_href_" + i).setAttribute("class", "menu");
            xmler.getElementById("leftcell_href_" + i).setText("Sample Control " + i);
        }

        leftMenu = xmler.getElementById("left_menu_cell_table");
    }

    protected void basicInitMainSheet()
    {
        createCell("mainSheet_cell", "main_level");
        mainSheet = xmler.getElementById("mainSheet_cell");
        xmler.getElementById("mainSheet_cell").setAttribute("id", "MainSheet");
        xmler.getElementById("mainSheet_cell").setAttribute("colspan", "2");
    }

    protected void basicInitMenu()
    {
        createScript("createMenu_script", "javascript", HtmlXMLer.BODY_ID);
        xmler.getElementById("createMenu_script").setText(createMenu);
    }

    protected void basicInitSettings()
    {
        xmler.getElementById("left_menu_cell").setAttribute("class", "leftmenu");
        xmler.getElementById("left_menu_cell").setAttribute("height", "100%");
        xmler.getElementById("left_menu_cell").setAttribute("valign", "top");
        if (rootTable != null)
            rootTable.setAttribute("height", "100%");
        else
            Logger.log("rootTable is NULL");
        xmler.getElementById("logo_cell").setAttribute("height", "75");
        xmler.getElementById("middle_cell").setAttribute("height", "1");
        xmler.getElementById("middle_cell").setAttribute("height", "1");
        xmler.getElementById("middle2_cell").setAttribute("height", "18");
        xmler.getElementById("middle3_cell").setAttribute("height", "18");
        xmler.getElementById("middle4_cell").setAttribute("height", "18");
        xmler.getElementById("middle5_cell").setAttribute("height", "1");
        if (mainSheet != null)
            mainSheet.setAttribute("valign", "top");
        else
            Logger.log("mainSheet is NULL");
        if (currentPath != null)
            currentPath.setText(HTML_SPACE + "Current Path: >");
        else
            Logger.log("currentPath is NULL");
        createHRef("curpath_href", str_currentURL, "middle3_cell");
        xmler.getElementById("curpath_href").setText(str_currentPath);
        xmler.getElementById("curpath_href").setAttribute("class", "menu");
        if (greeting != null)
            greeting.setText(HTML_SPACE + "Welcome, My Friend!!!");
        else
            Logger.log("greeting is NULL");
        if (controls != null)
            controls.setText(HTML_SPACE + "Available Controls: n/a");
        else
            Logger.log("controls is NULL");
    }

    public void basicInitAvailableControls()
    {
    }

    public void basicInitCurrentPath()
    {
    }

    public void basicInitTopMenu()
    {
    }

    public Element body;
    public Element rootTable;
    public Element leftMenu;
    public Element mainSheet;
    public Element greeting;
    public Element controls;
    public Element currentPath;
    public String str_currentPath;
    public String str_currentURL;
    protected String createMenu;
}
