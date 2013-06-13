package populationTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.*;
/*
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
*/
/**
 * /**
 *
 * @author Alejandro Tellez G. <java.util.fck@hotmail.com>
 */

public class PopulationTest1 {

    public static void main(String[] args) throws Exception{

        try {
            // Get hold of an ontology manager
            //OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
          
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            
            // Let's load an ontology from the web
            IRI iri = IRI.create("https://dl.dropbox.com/u/20278793/ontology/Groove/Groove.owl");
            OWLOntology grooveOntology = manager.loadOntologyFromOntologyDocument(iri);
            
            //Lineas de prueba
            System.out.println("Loaded ontology: " + grooveOntology);
            //Lugar de donde fue leida
            IRI documentIRI = manager.getOntologyDocumentIRI(grooveOntology);
            System.out.println("    from: " + documentIRI);

            System.out.println("Loaded ontology: " + grooveOntology);
            
            OWLDataFactory dataFactory = manager.getOWLDataFactory();
            
            OWLClass musicalArtist = 
                    dataFactory.getOWLClass(IRI.create("http://dl.dropbox.com/u/20278793/ontology/Groove.owl#MusicalArtist"));
           OWLIndividual i = dataFactory.getOWLNamedIndividual(IRI.create("http://dl.dropbox.com/u/20278793/ontology/Groove.owl#Bodom"));
           
           OWLAxiom axiom = dataFactory.getOWLClassAssertionAxiom(musicalArtist, i);
           manager.addAxiom(grooveOntology, axiom);
            
    //           manager.saveOntology(grooveOntology);
           File file = new File("/tmp/local.owl");
            manager.saveOntology(grooveOntology, IRI.create(file.toURI()));
           //manager.saveOntology(grooveOntology, documentIRI);
           
            
        }
        catch (OWLOntologyCreationIOException e) {
            // IOExceptions during loading get wrapped in an OWLOntologyCreationIOException
            IOException ioException = e.getCause();
            if (ioException instanceof FileNotFoundException) {
                System.out.println("Could not load ontology. File not found: " + ioException.getMessage());
            }
            else if (ioException instanceof UnknownHostException) {
                System.out.println("Could not load ontology. Unknown host: " + ioException.getMessage());
            }
            else {
                System.out.println("Could not load ontology: " + ioException.getClass().getSimpleName() + " " + ioException.getMessage());
            }
        }
        catch (UnparsableOntologyException e) {
            // If there was a problem loading an ontology because there are syntax errors in the document (file) that
            // represents the ontology then an UnparsableOntologyException is thrown
            System.out.println("Could not parse the ontology: " + e.getMessage());
            // A map of errors can be obtained from the exception
            Map<OWLParser, OWLParserException> exceptions = e.getExceptions();
            // The map describes which parsers were tried and what the errors were
            for (OWLParser parser : exceptions.keySet()) {
                System.out.println("Tried to parse the ontology with the " + parser.getClass().getSimpleName() + " parser");
                System.out.println("Failed because: " + exceptions.get(parser).getMessage());
            }
        }
        catch (UnloadableImportException e) {
            // If our ontology contains imports and one or more of the imports could not be loaded then an
            // UnloadableImportException will be thrown (depending on the missing imports handling policy)
            System.out.println("Could not load import: " + e.getImportsDeclaration());
            // The reason for this is specified and an OWLOntologyCreationException
            OWLOntologyCreationException cause = e.getOntologyCreationException();
            System.out.println("Reason: " + cause.getMessage());
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not load ontology: " + e.getMessage());
        }
        /*catch(Exception e){
            System.out.println(e.getMessage());
        }*/
    }
}
