package com.example.test;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class MultipleValues2 extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            /*
             * TODO output your page here out.println("<html>");
             * out.println("<head>"); out.println("<title>Servlet
             * MultipleValues</title>"); out.println("</head>");
             * out.println("<body>"); out.println("<h1>Servlet MultipleValues at
             * " + request.getContextPath () + "</h1>"); out.println("</body>");
             * out.println("</html>");
             */
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


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<TITLE>Obtaining Multi-Value Parameters</TITLE>");
        out.println("</HEAD>");
        out.println("<BODY>");

        //Call the java class that will execute a HTTP request via POST method.
        out.println(MainSendPostRequest.getBodyfromPostResponseToRequest());

        out.println("</BODY>");
        out.println("</HTML>");
        //processRequest(request, response);
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
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        /*
         PrintWriter out = response.getWriter();
         response.setContentType("text/html");

         //Session management
         HttpSession session = request.getSession();
         HashSet<String> musicalLikes = new HashSet<String>();

         //Get list of names
         List<String> names = Collections.list(
         (Enumeration<String>) request.getParameterNames());

         out.println("<ul>");
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
         out.println("</ul>");
         */
        /*
         * String[] values = request.getParameterValues("favoriteMusic"); if
         * (values != null) { int length = values.length; out.println("You have
         * selected: "); for (int i = 0; i < length; i++) { out.println("<BR>" +
         * values[i]); } }
         */
        //processRequest(request, response);
    }
}