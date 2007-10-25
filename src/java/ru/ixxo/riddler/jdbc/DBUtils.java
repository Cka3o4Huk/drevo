// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.jdbc;

import ru.ixxo.riddler.common.Helper;
import ru.ixxo.riddler.common.Logger;
import java.sql.*;
import java.util.HashMap;
import javax.naming.*;
import javax.sql.DataSource;

public class DBUtils
{

    public DBUtils()
    {
    }

    protected static void initClasses()
    {
        classes.put(HSQLDB, "org.hsqldb.jdbcDriver");
        classes.put(InstantDB, "org.enhydra.instantdb.jdbc.idbDriver");
        classes.put(PostGreSDB, "org.postgresql.Driver");
    }

    protected static void initDBProviders()
    {
        providers.put("HSQLDB", HSQLDB);
        providers.put("InstantDB", InstantDB);
        providers.put("PostGresql", PostGreSDB);
    }

    public static String getProviderByType(String s)
    {
        return (String)providers.get(s);
    }

    public static String generateUrl(String s, String s1, String s2)
    {
        return "jdbc:" + s2 + ":" + Helper.getDBDir() + "/" + s;
    }

    public static Connection createConnection(String s, String s1, String s2, String s3)
    {
        String s4;
        initClasses();
        if ((s4 = (String)classes.get(s3)) == null)
            return null;
        Connection connection;
        
        try{
        	Class.forName(s4);
        	connection = DriverManager.getConnection(s, s1, s2);
        	Logger.log("Successful connection to " + s);
        	currConn = connection;
        	return connection;
        }
        catch(ClassNotFoundException obj){
        	Logger.log("Can't found class: " + s4, "Error");
        	Logger.log("Exception: " + ((ClassNotFoundException) (obj)).getMessage(), "Error");
        	return null;
        }
        catch(SQLException obj){
        	Logger.log("Can't connect to database: " + s, "Error");
            Logger.log("Exception: " + ((SQLException) (obj)).getMessage(), "Error");        	
        	return null;
        }
    }

    public static Connection createConnectionFromPool(String s)
    {
        Connection connection;
        try{
        	InitialContext initialcontext = new InitialContext();
        	Context context = (Context)initialcontext.lookup("java:/comp/env");
        	DataSource datasource = (DataSource)context.lookup(s);
        	connection = datasource.getConnection();
        	currConn = connection;
        	return connection;
    	}catch(SQLException obj){	
    		Logger.log("Can't connect to database: " + s, "Error");
        	Logger.log("Exception: " + ((SQLException) (obj)).getMessage(), "Error");
        	return null;
    	}catch(NamingException obj){
    		Logger.log("Can't connect to database: " + s, "Error");
        	Logger.log("Exception: " + ((NamingException) (obj)).getMessage(), "Error");
        	return null;
    	}
    }

    public static ResultSet sqlQueryRun(String s)
    {
        try{
        	if (currConn == null)
            	return null;
        	ResultSet resultset;
        	Statement statement = currConn.createStatement();
        	resultset = statement.executeQuery(s);
        	return resultset;
    	}catch(SQLException sqlexception){
    		Logger.log("sqlQueryRun: Can't run query: " + s, "Error");
    		Logger.log("Exception: " + sqlexception.getMessage(), "Error");
    		return null;
    	}
    }

    public static int sqlUpdateRun(String s)
    {
        try{
        	if (currConn == null)
        		return -3;
        	int i;
        	Statement statement = currConn.createStatement();
        	i = statement.executeUpdate(s);
        	return i;
        }
        catch(SQLException sqlexception){
        	Logger.log("sqlQueryRun: Can't run query: " + s, "Error");
        	Logger.log("Exception: " + sqlexception.getMessage(), "Error");
        	return -3;
        }
    }

    public static void closeConnection()
    {
        try
        {
            currConn.close();
        }
        catch (SQLException sqlexception)
        {
            Logger.log("Close connection error");
        }
    }

    protected static final String PostGreSDBCl = "org.postgresql.Driver";
    protected static final String HSQLDBClass = "org.hsqldb.jdbcDriver";
    protected static final String InstantDBCl = "org.enhydra.instantdb.jdbc.idbDriver";
    public static String HSQLDB = "hsqldb";
    public static String InstantDB = "idb";
    public static String PostGreSDB = "postgresql";
    protected static HashMap classes = new HashMap();
    protected static HashMap providers = new HashMap();
    protected static Connection currConn = null;

}
