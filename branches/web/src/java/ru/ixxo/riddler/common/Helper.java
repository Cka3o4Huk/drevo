// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.common;

import java.util.HashMap;
import javax.servlet.ServletContext;

public class Helper
{

    public Helper()
    {
    }

    public static void initDir(ServletContext servletcontext)
    {
        setRootDir("");
        setCnfDir(servletcontext.getRealPath("/"));
        setDBDir(servletcontext.getRealPath("/"));
    }

    public static String getRootDir()
    {
        return rootDir;
    }

    public static void setRootDir(String s)
    {
        rootDir = s;
    }

    public static String getDBDir()
    {
        return dbDir;
    }

    public static void setDBDir(String s)
    {
        dbDir = s;
    }

    public static void setCnfDir(String s)
    {
        cnfDir = s;
    }

    public static String getCnfDir()
    {
        return cnfDir;
    }

    protected static HashMap dirs;
    protected static String rootDir;
    protected static String dbDir;
    protected static String cnfDir;
}
