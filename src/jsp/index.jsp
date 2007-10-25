<%@page import="donreba.ice.jsp.*,
				donreba.ice.jsp.xmler.*,
				org.jdom.*"%>
<%@ page import="ru.ixxo.riddler.jsp.SketchSite" %>
<%!
    public class MySite extends SketchSite {
        public void execute() {
            super.execute();
        }
    }
%>
<%
MySite pg = new MySite();
pg.setContext(pageContext);
pg.execute();
%>