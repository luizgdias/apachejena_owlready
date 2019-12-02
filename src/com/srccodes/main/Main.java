package com.srccodes.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.management.Query;
import org.apache.http.util.TextUtils;
import org.apache.jena.atlas.lib.ProgressMonitor;
import static org.apache.jena.enhanced.BuiltinPersonalities.model;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import static org.apache.jena.query.ResultSetFormatter.out;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.util.FileManager;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NsIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RSIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import static org.apache.jena.riot.system.StreamRDFLib.writer;
import static org.apache.jena.sparql.engine.http.Service.base;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDFS;
import static org.apache.jena.vocabulary.VOID.NS;
import static org.apache.jena.vocabulary.VOID.properties;

//Caso dê erro relacionado ao arquivo log4j.properties que por acaso nao exista no projeto: https://stackoverflow.com/questions/5081316/where-is-the-correct-location-to-put-log4j-properties-in-an-eclipse-project
//o arquivo deve ser criado e configurado como no tutorial

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Apache Jena **");
		try {
			createOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createOntology() throws IOException {
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		OntModel model2 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		String ontology = "file:/home/lmdc/Documentos/Luiz_Gustavo/owlready2/ontologia/ontologia-prov.rdf";
		String ontology2 = "file:/home/lmdc/Documentos/Luiz_Gustavo/owlready2/ontologia/ontologia-prov.rdf";
        model.read(ontology, "OWL/XML");
        model2.read(ontology2, "RDF/XML");
        
        /*Termos utilizados na busca semântica*/
        String uri = "http://www.semanticweb.org/lmdc/ontologies/2019/10/untitled-ontology-38#";
        String instance = "table54.jpg";
        String metadata = "desativado";
        String originalFile = "file1.pdf";
        
        /*Funções semânticas: Buscar subclasses de uma classe, Informações sobre uma instância, e informações de um metadado*/
        showClassesOrSub(model, "<http://www.semanticweb.org/lmdc/ontologies/2019/10/untitled-ontology-38#Entity>");
        showTermInformation(model, uri, instance);
        showMetadataInformation(model2, uri, metadata);
        showOriginalFileInformation(model, uri, originalFile);
	}
	
	private static void showClassesOrSub(OntModel model, String termo) {
        System.out.println("Showing Classe *"+termo+"* Subclasses: *");
        String queryString
        = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "
        + "PREFIX edam: <http://edamontology.org/>  "
        + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        + "PREFIX xml:<http://www.w3.org/XML/1998/namespace>"
        + "select ?subclasses "
        + "where { ?subclasses rdfs:subClassOf "+ termo +" . } LIMIT 200"
        + "\n ";
       
        org.apache.jena.query.Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        System.out.println("SPARQL results for subclasses related to: "+termo);
        ResultSetFormatter.out(System.out, results, query);
	}
	
	private static void showTermInformation(OntModel model2, String uri, String instance) {
		// create the base model
		Model schema = FileManager.get().loadModel("/home/lmdc/Documentos/Luiz_Gustavo/owlready2/ontologia/ontologia-prov.owl");
		Model data = FileManager.get().loadModel("/home/lmdc/Documentos/Luiz_Gustavo/owlready2/ontologia/ontologia-prov.owl");
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
		Resource r = infmodel.getResource(uri+instance);
		System.out.println("Data about instance: "+instance);
		printStatements(infmodel, r, null, null);
		
		
	}
	
	private static void showMetadataInformation(OntModel model2, String uri, String metadata) {
		// create the base model
		Model schema = FileManager.get().loadModel("/home/lmdc/Documentos/Luiz_Gustavo/owlready2/ontologia/ontologia-prov.owl");
		Model data = FileManager.get().loadModel("/home/lmdc/Documentos/Luiz_Gustavo/owlready2/ontologia/ontologia-prov.owl");
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
		Resource r = infmodel.getResource(uri+metadata);
		
		String property_name = "isMetadata";

		Property p = model2.createObjectProperty( uri+property_name );
		System.out.println("Data about instance: "+metadata);
		printStatements(infmodel, r, p , null);
			
	}
	
	private static void showOriginalFileInformation(OntModel model2, String uri, String original_file) {
		// create the base model
		Model schema = FileManager.get().loadModel("/home/lmdc/Documentos/Luiz_Gustavo/owlready2/ontologia/ontologia-prov.owl");
		Model data = FileManager.get().loadModel("/home/lmdc/Documentos/Luiz_Gustavo/owlready2/ontologia/ontologia-prov.owl");
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
		Resource r = infmodel.getResource(uri + original_file);
		
		String property_name = "derived";

		Property p = model2.createObjectProperty( uri+property_name );
		System.out.println("Data about original file: "+ original_file);
		printStatements(infmodel, r, p , null);
			
	}
	
	/*Função que printa informações de termos*/
	public static void printStatements(Model m, Resource s, Property p, Resource o) {
	    for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
	        Statement stmt = i.nextStatement();
	        System.out.println(" - " + PrintUtil.print(stmt.asTriple()));
	    }
	}

}
