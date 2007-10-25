// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.common;

import java.io.IOException;
import org.jdom.JDOMException;

public interface ISettings
{

    public abstract void loadSettings()
        throws JDOMException, IOException;

    public abstract void saveSettings(String s)
        throws IOException;

    public abstract void saveSettings()
        throws IOException;

    public abstract void initSettings(String s);

    public abstract String getSetting(String s);

    public abstract void setSetting(String s, String s1);

    public abstract String[] getAllKeys();

    public abstract void removeSetting(String s);
}
