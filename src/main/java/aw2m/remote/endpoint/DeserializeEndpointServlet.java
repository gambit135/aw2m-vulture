/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aw2m.remote.endpoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>POST METHOD @ Servlet DeserializeEndpoint Mark II</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>This is the POST METHOD @ Servlet DeserializeEndpoint Mark II </h1>");

            out.println("<ul>");

            //Session management
            HttpSession session = request.getSession();
            HashSet<String> musicalLikes = new HashSet<String>();

            //Get list of names
            List<String> names = Collections.list(
                    (Enumeration<String>) request.getParameterNames());
            
            //For each name on the list
            for (String name : names) {
                out.println("<li> Values for name: " + name + "</li>");
                //Get list of values for each name
                String[] values = request.getParameterValues(name);
                out.println("Values' size: " + values.length);
                out.println("<ul>");
                for (String value : values) {
                    out.println("<li>Value: " + value + " </li>");
                }
                out.println("</ul>");
            }
            //End of HTML
            out.println("</ul>");
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
