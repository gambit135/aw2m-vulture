/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aw2m.remote.endpoint;

import aw2m.common.stats.Statistic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class DeserializeEndpointServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeserializeEndpoint Mark II</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeserializeEndpoint Mark II at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>GET METHOD @ Servlet DeserializeEndpoint Mark II</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>This is the GET METHOD @ Servlet DeserializeEndpoint Mark II </h1>");
            out.println("</body>");
            out.println("</html>");
        }
        finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Do something with the received parameters.


        response.setContentType("text/html;charset=UTF-8");

        //The updated serialized data must be printed
        PrintWriter out = response.getWriter();

        Date today = new Date();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>POST METHOD @ Servlet DeserializeEndpoint Mark II  </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>This is the POST METHOD @ Servlet DeserializeEndpoint Mark II </h1>");
            out.println("<h2>Server time: " + today + "</h2>");

            out.println("<ul>");

            //Session management

            //Validate if there exists already a session on the server

            //I STILL DON'T KNOW WHY THIS MIGHT BE USEFUL
            /*Session might be useful for storing TERRAIN data: as the map.
             * But this will only be benefical when using 
             * not predeployed -custom- maps.             
             */


            /*It might also help to deal with IA, 
             * assuming the AI can have continuity*/

            /*But it must be noted that NOT always TWO consecutive turns 
             * are played by the AI on the cloud.*/

            /*If continuity is to be considered: 
             * the variables and aspects that may ensure it must be passed as
             * parameters, via the serialization process 
             * and unto the HTTP REQUEST*/

            boolean create;
            HttpSession session = request.getSession(create = false);

            //There was no session created before
            //use session.isNew()
            if (session == null) {
                session = request.getSession(create = true);
            }

            //Total size of String, in bytes
            int totalSize = 0;
            //Total overhead of String size, in bytes
            int overhead = 0;
            //Get list of names
            List<String> names = Collections.list(
                    (Enumeration<String>) request.getParameterNames());

            //For each name on the list
            for (String name : names) {
                out.println("<li> Values for name: <b>" + name + "</b></li>");
                //Get list of values for each name
                String[] values = request.getParameterValues(name);
                out.println("Values' size: " + values.length);
                out.println("<ul>");
                for (String value : values) {
                    out.println("<li>Value: " + value);
                    int oh = Statistic.calculateByteSizeOfStringOverhead(value);
                    int size = Statistic.calculateByteSizeofString(value);
                    out.println("<br>String size (bytes): " + size);
                    out.println("<br>String size overhead (bytes): " + oh);
                    out.println("</li>");
                    overhead += oh;
                    totalSize += size;
                }
                out.println("</ul>");
            }
            //End of HTML
            out.println("</ul>");

            out.println("<br>");
            out.println("<h3>Total String size (bytes): " + totalSize + "</h3>");
            out.println("<h3>Total String overhead (bytes): " + overhead + "</h3>");
            out.println("</body>");
            out.println("</html>");
        }
        finally {
            out.close();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
