package com.hiep.tuto;
import java.nio.file.Files;

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

// Extend HttpServlet class
public class Hello extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://hopcaquehuong.db.9404273.hostedresource.com/hopcaquehuong";
    static final String USER = "*****";
    static final String PASS = "*****";

    public Hello() {
        logInfo("The servlet is constructed");
    }

    public static void logInfo(String msg) {
        try {
            String timeLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            msg = timeLog +" - "+ msg+"\n";
            Files.write(Paths.get("/home/duong/tmp/log/webapp.txt"), msg.getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e) {
        }
    }

    public void init() throws ServletException
    {
        logInfo("The servlet is intitialized");
    }

    public ArrayList<String> GetFullNames() {
        ArrayList<String> resu = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT firstname, lastname, id FROM booking");
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");

                resu.add(firstname+" "+lastname);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            return resu;
        }
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        logInfo("Calling DoGet");
        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        ArrayList<String> everybody = GetFullNames();
        out.println("<html><body>");
        out.println("<h1>VERSION 1.4</h1>");
        for (String name: everybody) {
            out.println("<div>"+name+"</div>");
        }
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


    public void destroy()
    {
        logInfo("The servlet is destroyed");
    }
}
