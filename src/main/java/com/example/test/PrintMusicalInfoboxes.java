/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import retrieval.wikipedia.infobox.model.Genre;
import retrieval.wikipedia.infobox.model.MusicalArtistInfobox;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class PrintMusicalInfoboxes extends HttpServlet {

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
            out.println("<title>Servlet PrintMusicalInfoboxes</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PrintMusicalInfoboxes at " + request.getContextPath() + "</h1>");
            out.println("<ul>");
            //Get Session
            HttpSession session = request.getSession();

            if (!session.isNew()) {
                if (session.getAttribute("infoboxes") != null) {
                    //Print infoboxes
                    for (MusicalArtistInfobox mai : (HashSet<MusicalArtistInfobox>) session.getAttribute("infoboxes")) {
                        out.println("<li>");
                        out.println("Artist name: " + mai.getName());
                        out.println("<br>");
                        out.println("Artist article: " + mai.getWikiArticleTitle());
                        out.println("<br>");
                        out.println("Artist image URL: " + mai.getImage());
                        out.println("<br>");
                        out.println("Artist image caption: " + mai.getCaption());
                        out.println("<br>");
                        out.println("Artist Genres: ");
                        for (Genre genre : mai.getGenres()) {
                            out.println("Genre: " + genre.getGenre());
                            out.println("<br>");
                            out.println("Genre wikiArticle: " + genre.getWikiArticleTitle());
                            out.println("<br>");
                        }
                        out.println("</li>");
                    }
                    out.println("");
                }
            }
            out.println("</ul>");

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
        processRequest(request, response);
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
        processRequest(request, response);
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
