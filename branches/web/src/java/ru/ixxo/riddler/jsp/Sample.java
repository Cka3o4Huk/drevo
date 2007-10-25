package ru.ixxo.riddler.jsp;

import ru.ixxo.riddler.common.HTMLHelper;
import ru.ixxo.riddler.common.Logger;
import ru.ixxo.riddler.jdbc.DBUtils;
import ru.ixxo.riddler.jdbc.SQLGenerator;
import java.sql.*;
import java.util.ArrayList;
import org.jdom.Element;

// Referenced classes of package donreba.ice.jsp:
//            SketchSite

public class Sample extends SketchSite{
	
    protected final String ROOT_TYPE_ID = "1";
    protected final String SQL_SEPARATOR = "_";
    protected final String OBJ_TYPE_TABLE = "obj_type";
    protected final String NAME_COLUMN = "name";
    protected final String ID_COLUMN = "id";
    public String obj_type_id;
    protected Element sheet;
    protected final String SHEET_ID = "sheet_obj_type";
    protected final String TABLE_ID = "obj_type_table";
    protected final String CHILD_TABLE_ID = "child_obj_type_table";
    protected final String ATTR_TABLE_ID = "attr_table";

    public Sample()
    {
    }

    public void init()
    {
        super.init();
        str_currentPath = "Object Type Schema";
        str_currentURL = "obj_types.jsp";
    }

    public void parseParameters()
    {
        obj_type_id = request.getParameter("obj_type_id");
        if (obj_type_id == null)
            obj_type_id = "";
    }

    public void execute()
    {
        long l = System.currentTimeMillis();
        super.execute();
        Logger.log("Execute Time: " + (System.currentTimeMillis() - l));
        DBUtils.closeConnection();
    }

    public void printContent()
    {
        super.printContent();
        outObjectType(obj_type_id);
    }

    public void outFormat(String s, ResultSet resultset, ResultSetMetaData resultsetmetadata, int i)
        throws SQLException
    {
        String s1 = resultsetmetadata.getColumnName(i);
        String s2 = s1.substring(0, s1.lastIndexOf("_"));
        String s3 = s1.substring(s1.lastIndexOf("_") + 1);
        if (s2.compareToIgnoreCase("obj_type") == 0 && s3.compareToIgnoreCase("name") == 0)
        {
            ArrayList arraylist = new ArrayList();
            arraylist.add("obj_type_id=" + resultset.getString(resultset.findColumn("obj_type_id")));
            createHRef(s + "_href_obj_types_name_" + i, HTMLHelper.createHyperRef("obj_types.jsp", arraylist), s);
            xmler.getElementById(s + "_href_obj_types_name_" + i).setText(resultset.getString(i));
            return;
        } else
        {
            xmler.getElementById(s).setText(resultset.getString(i));
            return;
        }
    }

    public void outRStoTable(ResultSet resultset, String s, String s1)
        throws SQLException
    {
        ResultSetMetaData resultsetmetadata = resultset.getMetaData();
        String s2 = s1 + "_title";
        String s3 = s1 + "_row_";
        String s4 = s1 + "_cell_";
        int i = resultsetmetadata.getColumnCount();
        int j = 1;
        createTable(s1, "", s);
        Element element = xmler.getElementById(s1);
        element.setAttribute("cellSpacing", "1");
        element.setAttribute("cellPadding", "1");
        element.setAttribute("border", "1");
        createRow(s2, s1);
        for (int k = 1; k <= i; k++)
        {
            createCell(s4 + resultsetmetadata.getColumnName(k) + "title", s2);
            xmler.getElementById(s4 + resultsetmetadata.getColumnName(k) + "title").setText(resultsetmetadata.getColumnName(k));
        }

        while (resultset.next()) 
        {
            createRow(s3 + j, s1);
            for (int l = 1; l <= i; l++)
            {
                createCell(s4 + resultsetmetadata.getColumnName(l) + j, s3 + j);
                outFormat(s4 + resultsetmetadata.getColumnName(l) + j, resultset, resultsetmetadata, l);
            }

            j++;
        }
    }

    public void outRStoTableWithTitle(ResultSet resultset, int i, String s, String s1, String s2)
        throws SQLException
    {
        createRow(s1 + "_row_title_" + i, s1);
        createCell(s1 + "_cell_title_" + i, s1 + "_row_title_" + i);
        xmler.getElementById(s1 + "_cell_title_" + i).setText(s);
        createRow(s1 + "_row_" + i, s1);
        createCell(s1 + "_cell_" + i, s1 + "_row_" + i);
        outRStoTable(resultset, s1 + "_cell_" + i, s2);
    }

    public void outObjectType(String s)
    {
        String s1;
        String s2;
        String s3;
        
        if (s.compareTo("") == 0)
        {
            outObjectType("1");
            return;
        }
        
        createTable("sheet_obj_type", "", "mainSheet_cell");
        Element element = xmler.getElementById("sheet_obj_type");
        element.setAttribute("cellSpacing", "0");
        element.setAttribute("cellPadding", "0");
        
        s1 = SQLGenerator.generateSQLbyObjectType(s);
        s2 = SQLGenerator.generateSQLbyParentType(s);
        s3 = SQLGenerator.generateSQLforAttrsbyObjectType(s);
        
        ResultSet resultset;
        ResultSet resultset1;
        ResultSet resultset2;
        
        resultset = DBUtils.sqlQueryRun(s1);
        resultset1 = DBUtils.sqlQueryRun(s2);
        resultset2 = DBUtils.sqlQueryRun(s3);
        
        if (resultset == null || resultset1 == null || resultset2 == null)
        {
            mainSheet.setText("Error: ResultSet is null");
            return;
        }
        try
        {
            outRStoTableWithTitle(resultset, 1, "Object Type", "sheet_obj_type", "obj_type_table");
            outRStoTableWithTitle(resultset1, 2, "Children", "sheet_obj_type", "child_obj_type_table");
            outRStoTableWithTitle(resultset2, 3, "Attributes", "sheet_obj_type", "attr_table");
        }
        catch (SQLException sqlexception)
        {
            mainSheet.setText("SQLException in outObjectType: " + sqlexception.getLocalizedMessage());
            sqlexception.printStackTrace();
        }
        return;
    }
}
