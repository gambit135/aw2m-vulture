package com.example.test;

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
import retrieval.wikipedia.infobox.model.MusicalArtistInfobox;
import retrieval.wikipedia.util.ProcessRawFacebook;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class MultipleValues extends HttpServlet {

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

        out.println("<BR>");
        out.println("<BR>Select your favorite music:");
        out.println("<BR><FORM METHOD=POST>");
        out.println("<BR><INPUT TYPE=CHECKBOX "
                + "NAME=favoriteMusic VALUE=Rock>Rock");
        out.println("<BR><INPUT TYPE=CHECKBOX "
                + "NAME=favoriteMusic VALUE=Jazz>Jazz");
        out.println("<BR><INPUT TYPE=CHECKBOX "
                + "NAME=favoriteMusic VALUE=Classical>Classical");
        out.println("<BR><INPUT TYPE=CHECKBOX "
                + "NAME=favoriteMusic VALUE=Country>Country");
        out.println("<BR><INPUT TYPE=SUBMIT VALUE=Submit>");

        out.println("<BR>Select your favorite Food:");
        out.println("<BR><INPUT TYPE=CHECKBOX "
                + "NAME=favoriteFood VALUE=Eggs>Eggs");
        out.println("<BR><INPUT TYPE=CHECKBOX "
                + "NAME=favoriteFood VALUE=Ham>Ham");


        out.println("</FORM>");
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

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        //Session management
        HttpSession session = request.getSession();
        HashSet<String> musicalLikes = new HashSet<String>();

        //session.setAttribute("musicalLikes", out);

        //Get list of names
        List<String> names = Collections.list(
                (Enumeration<String>) request.getParameterNames());

        out.println("<ul>");
        //For each name on the list
        try {
            out.println("<h1> " + getServletContext().getRealPath("/") + "</h1><br>");
        }
        catch (NullPointerException npe) {
            out.println(npe.getMessage());
            out.println("<br>");
        }
        for (String name : names) {
            out.println("<li> Values for name: " + name + "</li>");

            //Get list of values for each name
            String[] values = request.getParameterValues(name);
            out.println("Values' size: " + values.length);
            /*
             * The name 'userData' is the only one with more than one value
             * (Facebook user ID and user name), so a distinction must be done,
             * between this and the rest.
             */
            //For each value of current name
            if (!name.equals("userData")) {
                for (String value : values) { //values[0]
                    /*
                     * Get the first and only value for this name, as it is the
                     * String representation of the current musical like
                     */
                    out.println(value);
                    musicalLikes.add(value);
                    /*
                     * N.B. Get the String representation of current name/value for
                     * processing.
                     */
                }
            }
            else {
                for (String value : values) {
                    out.println("User Data!!! \n" + value);
                }
            }
        }
        out.println("<hr>");
        out.println("</ul>");
        /* In the end, process the resulting set with values as music likes
         */
        HashSet<MusicalArtistInfobox> infoboxes =
                new ProcessRawFacebook().processRaw(musicalLikes);
        session.setAttribute("infoboxes", infoboxes);
        session.setAttribute("test","test!!!");
//        response.sendRedirect("/musicalArtist/persistSessionAttribute");
        response.sendRedirect("/PrintMusicalInfoboxes");

    }
    /*
     * String[] values = request.getParameterValues("favoriteMusic"); if
     * (values != null) { int length = values.length; out.println("You have
     * selected: "); for (int i = 0; i < length; i++) { out.println("<BR>" +
     * values[i]); } }
     */
    //processRequest(request, response);
}
