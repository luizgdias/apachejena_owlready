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
