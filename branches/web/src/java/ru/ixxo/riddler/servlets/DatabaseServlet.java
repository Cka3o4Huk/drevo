// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.servlets;

import ru.ixxo.riddler.common.Logger;
import ru.ixxo.riddler.common.Settings;
import ru.ixxo.riddler.jdbc.DBUtils;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package donreba.ice.servlets:
//            CommonServlet

public class DatabaseServlet extends CommonServlet
{

    public DatabaseServlet()
    {
    }

    protected Connection connectDB()
    {
        Settings settings = new Settings();
        settings.initSettings(generalSt.getSetting("DBSettings"));
        dbname = settings.getSetting("DataBaseName");
        user = settings.getSetting("User");
        pass = settings.getSetting("Password");
        type = settings.getSetting("Type");
        conn = DBUtils.createConnection(DBUtils.generateUrl(dbname, "", DBUtils.getProviderByType(type)), user, pass, DBUtils.getProviderByType(type));
        if (conn == null)
            out.println("Failed connection");
        if (conn != null)
        {
            out.println("Success connection");
            try
            {
                conn.close();
            }
            catch (SQLException sqlexception)
            {
                Logger.log("Can't close connection: " + sqlexception.getMessage(), "Warning");
            }
        }
        return conn;
    }

    protected void disconnectDB()
    {
    }

    protected void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
    {
        initServlet(httpservletrequest, httpservletresponse);
        connectDB();
    }

    protected String dbname;
    protected String user;
    protected String pass;
    protected String type;
    protected Settings dbsettings;
    Connection conn;
    private static final long serialVersionUID = 1L;
}
