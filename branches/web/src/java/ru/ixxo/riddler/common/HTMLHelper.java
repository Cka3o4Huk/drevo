// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.common;

import java.util.Collection;
import java.util.Iterator;

public class HTMLHelper
{

    public HTMLHelper()
    {
    }

    public static String createHyperRef(String s, Collection collection)
    {
        String s1 = s + "?";
        for (Iterator iterator = collection.iterator(); iterator.hasNext();)
            s1 = s1 + (String)iterator.next();

        return s1;
    }
}
