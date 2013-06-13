package com.example.test;


//import antlr.collections.List;
import info.bliki.api.Page;
import info.bliki.api.User;
import info.bliki.wiki.model.WikiModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alejandro
 */
public class WikipediaRenderPageTest extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {        
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        
        String[] listOfTitleStrings = {"Black Sabbath"};
        User user = new User("", "", "http://en.wikipedia.org/w/api.php");
        user.login();
        List<Page> listOfPages = user.queryContent(listOfTitleStrings);
        for (Page page : listOfPages) {
            WikiModel wikiModel = new WikiModel("${image}", "${title}");
            
            String html = wikiModel.render(page.toString());
            out.println(html);
        }      
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        out.println("printing parameter names: <br>");
        List<String> names = Collections.list(
                (Enumeration<String>) request.getParameterNames());

        for (String name : names) {
            out.println(name + "<br>");
        }

        for (String name : names) {
            out.println("Values for name: " + name + "<br>");
            String[] values = request.getParameterValues(name);
            for (String value : values) {
                out.println(value + "<br>");
            }
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
