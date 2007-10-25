// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.common;

import java.io.*;
import java.nio.channels.Channels;
import java.util.*;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

// Referenced classes of package donreba.ice.common:
//            ISettings, Helper, Logger

public class Settings
    implements ISettings
{

    public Settings()
    {
        zerolevel = null;
        fname = "";
    }

    public void loadSettings()
        throws JDOMException, IOException
    {
        if (fname == null || fname.compareTo("") == 0)
            return;
        SAXBuilder saxbuilder = new SAXBuilder();
        document = saxbuilder.build(Helper.getCnfDir() + fname);
        Element element = document.getRootElement();
        List list = element.getChildren();
        Element element1;
        for (Iterator iterator = list.iterator(); iterator.hasNext(); zerolevel.put(element1.getName(), element1.getText()))
            element1 = (Element)iterator.next();

    }

    public void saveSettings(String s)
        throws IOException
    {
        Element element = document.getRootElement();
        List list = element.getChildren();
        Element element1;
        for (Iterator iterator = list.iterator(); iterator.hasNext(); element.removeChild(element1.getName()))
            element1 = (Element)iterator.next();

        String as[] = getAllKeys();
        for (int i = 0; i < as.length; i++)
        {
            Element element2 = new Element(as[i]);
            element2.setText(getSetting(as[i]));
            element.addContent(element2);
        }

        File file = new File(Helper.getCnfDir() + s);
        java.nio.channels.FileChannel filechannel = (new RandomAccessFile(file, "rw")).getChannel();
        java.io.OutputStream outputstream = Channels.newOutputStream(filechannel);
        XMLOutputter xmloutputter = new XMLOutputter();
        xmloutputter.output(document, outputstream);
    }

    public void saveSettings()
        throws IOException
    {
        saveSettings(fname);
    }

    public void initSettings(String s)
    {
        Logger.log("initSettings");
        zerolevel = new HashMap();
        fname = s;
        try
        {
            loadSettings();
        }
        catch (Exception exception)
        {
            Logger.log("Settings: Can't load setting from file = " + s, "Error");
        }
    }

    public String getSetting(String s)
    {
        if (zerolevel.containsKey(s))
            return (String)zerolevel.get(s);
        else
            return null;
    }

    public void setSetting(String s, String s1)
    {
        if (zerolevel.containsKey(s))
            zerolevel.remove(s);
        zerolevel.put(s, s1);
    }

    public String[] getAllKeys()
    {
        Set set = zerolevel.keySet();
        String as[] = (String[])set.toArray();
        return as;
    }

    public void removeSetting(String s)
    {
        zerolevel.remove(s);
    }

    protected HashMap zerolevel;
    protected Document document;
    protected String fname;
}
