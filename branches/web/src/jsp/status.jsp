<%@page import="donreba.ice.jsp.*,
				donreba.ice.jsp.xmler.*,
				ru.ixxo.riddler.common.*,
				org.jdom.*"%>
<%@ page import="ru.ixxo.riddler.jsp.SketchSite" %>
<%!
    public class MySite extends SketchSite {
        public void init() {
            super.init();
            str_currentPath = "Status";
            str_currentURL = "status.jsp";
        }

        public void execute() {
            super.execute();
        }

        public void printContent() {
            super.printContent();
            mainSheet.setText(HTML_SPACE + "Status of project: Build [16 oct 2005]");
        }
    }
%>
<%
MySite pg = new MySite();
pg.setContext(pageContext);
pg.execute();
%>