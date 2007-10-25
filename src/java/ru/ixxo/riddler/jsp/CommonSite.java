// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) space 

package ru.ixxo.riddler.jsp;

import ru.ixxo.riddler.common.Logger;
import ru.ixxo.riddler.jdbc.DBUtils;
import ru.ixxo.riddler.jsp.xml.HtmlXMLer;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import org.jdom.Element;

public class CommonSite
{
    private class ContextProvider extends PageContext
    {

        public void initialize(Servlet servlet, ServletRequest servletrequest, ServletResponse servletresponse, String s, boolean flag, int i, boolean flag1)
            throws IOException, IllegalStateException, IllegalArgumentException
        {
            pgcn.initialize(servlet, servletrequest, servletresponse, s, flag, i, flag1);
        }

        public void release()
        {
            pgcn.release();
        }

        public HttpSession getSession()
        {
            return pgcn.getSession();
        }

        public Object getPage()
        {
            return pgcn.getPage();
        }

        public ServletRequest getRequest()
        {
            return pgcn.getRequest();
        }

        public ServletResponse getResponse()
        {
            return pgcn.getResponse();
        }

        public Exception getException()
        {
            return pgcn.getException();
        }

        public ServletConfig getServletConfig()
        {
            return pgcn.getServletConfig();
        }

        public ServletContext getServletContext()
        {
            return pgcn.getServletContext();
        }

        public void forward(String s)
            throws ServletException, IOException
        {
            pgcn.forward(s);
        }

        public void include(String s)
            throws ServletException, IOException
        {
            pgcn.include(s);
        }

        public void include(String s, boolean flag)
            throws ServletException, IOException
        {
            pgcn.include(s, flag);
        }

        public void handlePageException(Exception exception)
            throws ServletException, IOException
        {
            pgcn.handlePageException(exception);
        }

        public void handlePageException(Throwable throwable)
            throws ServletException, IOException
        {
            pgcn.handlePageException(throwable);
        }

        public void setAttribute(String s, Object obj)
        {
            pgcn.setAttribute(s, obj);
        }

        public void setAttribute(String s, Object obj, int i)
        {
            pgcn.setAttribute(s, obj, i);
        }

        public Object getAttribute(String s)
        {
            return pgcn.getAttribute(s);
        }

        public Object getAttribute(String s, int i)
        {
            return pgcn.getAttribute(s, i);
        }

        public Object findAttribute(String s)
        {
            return pgcn.findAttribute(s);
        }

        public void removeAttribute(String s)
        {
            removeAttribute(s);
        }

        public void removeAttribute(String s, int i)
        {
            pgcn.removeAttribute(s, i);
        }

        public int getAttributesScope(String s)
        {
            return pgcn.getAttributesScope(s);
        }

        public Enumeration getAttributeNamesInScope(int i)
        {
            return pgcn.getAttributeNamesInScope(i);
        }

        public JspWriter getOut()
        {
            return pgcn.getOut();
        }

        public ExpressionEvaluator getExpressionEvaluator()
        {
            return pgcn.getExpressionEvaluator();
        }

        public VariableResolver getVariableResolver()
        {
            return pgcn.getVariableResolver();
        }

        public PageContext pgcn;

        public ContextProvider(PageContext pagecontext)
        {
            pgcn = pagecontext;
        }
    }


    public CommonSite()
    {
        xmler = new HtmlXMLer();
    }

    public void setContext(PageContext pagecontext)
    {
        long l = System.currentTimeMillis();
        cntx = new ContextProvider(pagecontext);
        xmler.initWriter(cntx.getOut());
        Logger.ctx = cntx.getServletContext();
        request = cntx.getRequest();
        long l1 = System.currentTimeMillis() - l;
        initDB();
        long l2 = System.currentTimeMillis() - l;
        Logger.log("Set Context Times: " + l1 + " " + l2);
    }

    public void initDB()
    {
        DBUtils.createConnectionFromPool("jdbc/postgres");
    }

    public void setTitle(String s)
    {
        Element element = xmler.getElementById(HtmlXMLer.TITLE_ID);
        element.setText(s);
    }

    public void execute()
    {
        xmler.outXML();
    }

    protected ContextProvider cntx;
    public ServletRequest request;
    public HtmlXMLer xmler;
}
