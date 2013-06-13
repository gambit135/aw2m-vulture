/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alignmenttest;

import fr.inrialpes.exmo.align.impl.BasicParameters;
import fr.inrialpes.exmo.align.impl.method.StringDistAlignment;
import fr.inrialpes.exmo.align.impl.renderer.HTMLRendererVisitor;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb. owl.align.AlignmentVisitor;

/**
 *
 * @author Alejandro Tellez G. <java.util.fck@hotmail.com>
 */
public class AlignmentTestServlet extends HttpServlet {

    /**
     * Performs the test alignment to see if two things can be done:
     *
     * 1. Executing the alignment on the cloud.
     *
     * 2. Write the output for such alignment on a servlet via the response
     * writer.
     *
     * @param pw The Printwriter associated with a HttpResponse object.
     */
    protected void alignTest(PrintWriter pw) {

        URI onto1 = null;
        URI onto2 = null;
        AlignmentProcess a1;
        Properties params = new BasicParameters();
        try {
            onto1 = new URI("http://dl.dropbox.com/u/20278793/ontology/WebRadioStations.owl");
            onto2 = new URI("http://dl.dropbox.com/u/20278793/ontology/Groove/Groove.owl");

            // Aligning
            a1 = new StringDistAlignment();
            //a1 = new SMOANameAlignment();
            a1.init(onto1, onto2);
            a1.align((Alignment) null, params);
            // Outputing
            //AlignmentVisitor renderer = new RDFRendererVisitor(pw);
            AlignmentVisitor renderer = new HTMLRendererVisitor(pw);
            a1.render(renderer);
        }
        catch (Exception e) {
            e.printStackTrace();
        };
    }

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
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReasonerTestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("TEST!!<br/><br/>");

            //Calling for the test methods that executes the alignment
            this.alignTest(out);

            out.println("<h1>Servlet AlignmentTestServlet at " + request.getContextPath() + "</h1>");
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
