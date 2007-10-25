// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.common;

import java.text.MessageFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;

public class Logger
{

    public Logger()
    {
    }

    public static void enableLogging()
    {
        flags = true;
    }

    public static void disableLogging()
    {
        flags = false;
    }

    public static boolean isLogging()
    {
        return flags;
    }

    public static void log(String s)
    {
        log(s, "Info");
    }

    public static void log(String s, String s1)
    {
        Object aobj[] = {
            new Date()
        };
        String s2 = MessageFormat.format("[{0,date,full} {0,time,full}] ", aobj);
        s2 = s2 + "[" + s1 + "] " + s;
        ctx.log(s2);
    }

    public static void logXML(Document document, XMLOutputter xmloutputter)
    {
        ctx.log(xmloutputter.outputString(document));
    }

    protected static boolean flags = false;
    public static final String Error = "Error";
    public static final String Info = "Info";
    public static final String Warning = "Warning";
    public static ServletContext ctx = null;

}
