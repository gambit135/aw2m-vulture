package alignmenttest;

/*
 * $Id: Skeleton.java 1404 2010-03-31 08:53:09Z euzenat $
 *
 * Copyright (C) INRIA, 2006-2008, 2010
 *
 * Modifications to the initial code base are copyright of their respective
 * authors, or their employers as appropriate. Authorship of the modifications
 * may be determined from the ChangeLog placed at the end of this file.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
// Alignment API classes
import fr.inrialpes.exmo.align.impl.BasicParameters;
import fr.inrialpes.exmo.align.impl.method.EditDistNameAlignment;
import fr.inrialpes.exmo.align.impl.renderer.RDFRendererVisitor;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Properties;
import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.AlignmentVisitor;

/**
 * The Skeleton of code for embeding the alignment API
 *
 * Takes two files as arguments and align them.
 */
public class Skeleton {

    public static void main(String[] args) {
        URI onto1 = null;
        URI onto2 = null;
        Properties params = new BasicParameters();

        System.out.println("Skeleton.java");
        System.out.println("This is a test");
        try {

            // Loading ontologies
            //onto1 = new URI("http://dl.dropbox.com/u/20278793/ontology/myMusicOntology.owl");
            onto1 = new URI("http://dl.dropbox.com/u/20278793/ontology/WebRadioStations.owl");
            //onto1 = new URI("E://Users\\Administrador\\Documents\\Dropbox\\Public\\ontology\\WebRadioStations.owl");
            //onto2 = new URI("E://Users\\Administrador\\Documents\\Dropbox\\Public\\ontology\\Groove.owl");
            onto2 = new URI("http://dl.dropbox.com/u/20278793/ontology/Groove/Groove.owl");
            //onto2 = new URI("http://dl.dropbox.com/u/20278793/ontology/musicOntology2.owl");
	    /*
             * if (args.length >= 2) { onto1 = new URI( args[0] ); onto2 = new
             * URI( args[1] ); } else { System.err.println("Need two arguments
             * to proceed"); return ; }
             */

            // Aligning
            AlignmentProcess a1;
            //a1 = new StringDistAlignment();
            a1 = new EditDistNameAlignment();
            //a1 = new ClassStructAlignment();
            //a1 = new SMOANameAlignment();
            //a1 = new NameAndPropertyAlignment();
            //a1 = new NameEqAlignment();
            //a1 = new StrucSubsDistAlignment();
            //a1 = new JWINLAlignment();
            a1.init(onto1, onto2);
            
            a1.align((Alignment) null, params);

            // Outputing
            PrintWriter writer = new PrintWriter(
                    new BufferedWriter(
                    new OutputStreamWriter(System.out, "UTF-8")), true);
            
            AlignmentVisitor renderer = new RDFRendererVisitor(writer);
            
            a1.render(renderer);
            //writer.flush();
            writer.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        };
    }
}
