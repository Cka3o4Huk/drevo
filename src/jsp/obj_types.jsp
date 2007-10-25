<%@page import="donreba.ice.jsp.*,
				donreba.ice.jsp.xmler.*,
				ru.ixxo.riddler.common.*,
				org.jdom.*"%>
<%@ page import="ru.ixxo.riddler.jsp.Sample" %>
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
    long startTime = System.currentTimeMillis();
    Sample pg = new Sample();
    pg.setContext(pageContext);
    pg.execute();
    long allTime = System.currentTimeMillis() - startTime;
    Logger.log("All Time: " + allTime + " milliseconds");
%>