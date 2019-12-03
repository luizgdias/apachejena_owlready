# Instruções sobre o Apache Jena

Este hands-on tem a finalidade de conceituar e demonstrar o funcionamento do Apache Jena, bem como instruir na realização de buscas semânticas.

### O que é o Apache Jena?
O Apache Jena é um framework Java de código aberto para a criação de aplicações semânticas e dados conectados. O framework é composto por 
diversas APIs que interagem em conjunto para processar dados em RDF (Resource Description Framework, é uma família de especificações
da W3C originalmente planejada como um modelo de dados para metadados). O processo de importação da biblioteca no eclipse é simplificado, 
e pode ser acessado por [este](https://jena.apache.org/tutorials/using_jena_with_eclipse.html) link.
 
O Jena suporta linguagens de consulta (como SPARQL) e mecanismos de inferência (como raciocinadores lógicos). Neste hands-on será 
demonstrado como utilizar os dois métodos. Primeiramente é necessário ter em mente que os dados inferidos necessitam estar em arquivos 
de extensão RDF ou OWL. Neste exemplo foi utilizada uma ontologia PROV extendida, composta por nove classes e nove propriedades:

Classes:
- Thing
  - Activity
  - Agent
  - Entity
    - Chart
    - Image
    - Metadata
    - OriginalFile
    - Table
    
Propriedades de Objetos:
- derived (extendida)
- hasMetadata (extendida)
- hasSimilar (extendida)
- isMetadata (extendida)
- used
- wasASsociatedWith
- wasAttributedTo
- wasDerivedFrom
- wasGeneratedBy

Foram criados cinco métodos na classe main:
- loadOntology(): que carrega a ontologia já criada, com as classes e indivíduos instanciados e relacionados;
- showClassesOrSub(model, uri, instance): exibe as informações de uma classe ou subclasse;
- showMetadataInformation(model, uri, metadata): exibe informações de um metadado específico;
- showOriginalFileInformation(model, uri, originalFile): exibe informações de um indivíduo do tipo originalFile;

###Carregando a ontologia
O primeiro passo para interagir com a ontologia é instanciar um modelo que carregará o vocabulário do domínio. Para isso Jena disponibiliza o tipo OntModel:
```
OntModel <modelName> = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
```
 O argumento recebido na instancia, fiz respeito a especificação do modelo, que leva em consideração o schema, os tipos de inferência e linguagens, maiores informações podem ser encontradas [aqui](https://jena.apache.org/documentation/javadoc/jena/org/apache/jena/ontology/OntModelSpec.html).
 
 Os próximos passos são relativos a declarar o diretório da ontologia e ler o modelo criado respectivamente:
 ```
 String ontology = "file:/dir/file.owl";
 model.read(ontology, "OWL/XML");
 ```
 
