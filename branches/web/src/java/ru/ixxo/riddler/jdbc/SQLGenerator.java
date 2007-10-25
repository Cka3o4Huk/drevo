// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.jdbc;


public class SQLGenerator
{

    public SQLGenerator()
    {
    }

    public static String generateSQLbyObjectType(String s)
    {
        return "select name as obj_type_name, obj_type_id from obj_types where obj_type_id in (" + s + ")";
    }

    public static String generateSQLbyParentType(String s)
    {
        return "select name as obj_type_name, obj_type_id from obj_types where parent_id in (" + s + ")";
    }

    public static String generateSQLforAttrsbyObjectType(String s)
    {
        return "select * from obj_attrs where obj_type_id in (" + s + ")";
    }
}
