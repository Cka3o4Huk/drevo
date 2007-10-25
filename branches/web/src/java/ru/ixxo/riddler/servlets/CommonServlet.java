// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.servlets;

import ru.ixxo.riddler.common.*;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class CommonServlet extends HttpServlet
{

    public CommonServlet()
    {
    }

    protected void initServlet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
    {
        Helper.initDir(getServletContext());
        Logger.ctx = getServletContext();
        try
        {
            out = httpservletresponse.getWriter();
        }
        catch (IOException ioexception)
        {
            Logger.log("InitServlet: Error on getWriter()", "Error");
        }
        generalSt = new Settings();
        generalSt.initSettings("config/main.xml");
    }

    protected void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws IOException
    {
        initServlet(httpservletrequest, httpservletresponse);
        String s;
        String s1;
        if ((s = httpservletrequest.getParameter("name")) == null)
            s1 = "sample.xml";
        else
            s1 = s;
        out.println("<html><title>Welcome</title><body>");
        out.println("<form method = \"get\">");
        out.println("Please type XML file name:<input type = \"text\" name =  \"name\" value = \"" + s1 + "\"/>");
        out.println("<input type = \"submit\" value = \"Submit\"/>");
        out.println("</form>");
        out.println("<br>" + System.getProperty("user.dir") + "<br>" + getServletContext().getRealPath("\\"));
        if (s != null)
        {
            out.println("File consist:<br>");
            Settings settings = new Settings();
            settings.initSettings(s);
        }
        out.println("</body></html>");
    }

    private static final long serialVersionUID = 1L;
    protected PrintWriter out;
    protected Settings generalSt;
}
