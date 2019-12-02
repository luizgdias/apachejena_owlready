from owlready2 import *
import yake, os

#######################################################################################
# Para o correto funcionamento deste script, deve existir um arquivo ontologia-prov.owl 
# vazio no diretório /ontologia
#######################################################################################
# os.system("rm -rf ontologia")
# os.system("mkdir ontologia")
# os.system("touch ontologia/ontologia-prov.owl")
# os.system("cat ontologia-prov.owl")
onto = get_ontology('ontologia/ontologia-prov.owl').load()

def create_ontology(ontology):
    ###################################################################### 
    # definindo classes, subclasses e propriedades
    ###################################################################### 
    print("| **** Accessing .owl file...")
    with ontology:
        class Entity(Thing):
            namespace = ontology

        class Agent(Thing):
            namespace = ontology

        class Activity(Thing):
            namespace = ontology
        
        # onto.save(file = "ontologia/ontologia-prov.owl")
        
        class OriginalFile(Entity):
            pass

        class Metadata(Entity):
            pass

        class OilWell(Entity):
            pass

        class Table(Entity):
            pass

        class Chart(Entity):
            pass

        class Image(Entity):
            pass
        
        class wasDerivedFrom(ObjectProperty):
            domain   = [Entity]
            range    = [Entity]

        class wasGeneratedBy(ObjectProperty):
            domain   = [Entity]
            range    = [Activity]
        
        class used(ObjectProperty):
            domain   = [Activity]
            range    = [Entity]
        
        class wasAttributedTo(ObjectProperty):
            domain   = [Entity]
            range    = [Agent]
        
        class wasAssociatedWith(ObjectProperty):
            domain   = [Activity]
            range    = [Agent]
        
        class hasMetadata(ObjectProperty):
            domain   = [Entity]
            range    = [Metadata]

    ontology.save(file = "ontologia/ontologia-prov.owl")
    print("| **** Creating classes and properties: Done")
    return(ontology)

def create_instances_and_links(onto):  
###################################################################### 
# Criando instancias (individuos) na ontologia
####################################################################### 
    print("| **** Creating instances and links: Done")
    agent0 = onto.Agent("pdf-extractor-v2")
    activity0 = onto.Activity("extract-data")
    activity0.wasAssociatedWith = [agent0]

    originalfile1                   = onto.OriginalFile("file1.pdf")
    originalfile1.wasAttributedTo   = [agent0]

    table1                  = onto.Table("table11.jpg")
    table1.wasDerivedFrom   = [originalfile1]

    originalfile2                   = onto.OriginalFile("file2.pdf")
    originalfile2.wasAttributedTo   = [agent0]

    table2                  = onto.Table("table21.jpg")
    table2.wasDerivedFrom   = [originalfile2]


    originalfile3                   = onto.OriginalFile("file3.pdf")
    originalfile3.wasAttributedTo   = [agent0]

    table3                  = onto.Table("table31.jpg")
    table3.wasDerivedFrom   = [originalfile3]


    originalfile4                   = onto.OriginalFile("file4.pdf")
    originalfile4.wasAttributedTo   = [agent0]

    table4 = onto.Table("table41.jpg")
    image1 = onto.Image("img42.jpg")
    table4.wasDerivedFrom = [originalfile4]
    image1.wasDerivedFrom = [originalfile4]


    originalfile5 = onto.OriginalFile("file5.pdf")
    originalfile5.wasAttributedTo = [agent0]

    table5 = onto.Table("table51.jpg")
    image2 = onto.Image("img52.jpg")
    chart1 = onto.Chart("chart53.jpg")
    table6 = onto.Table("table54.jpg")
    table5.wasDerivedFrom = [originalfile5]
    image2.wasDerivedFrom = [originalfile5]
    chart1.wasDerivedFrom = [originalfile5]
    table6.wasDerivedFrom = [originalfile5]

    ###################################################################### 
    # Definindo um exemplo de metadado
    ###################################################################### 

    text = "Exemplo de poco de petróleo desativado localizado em Rio_de_Janeiro"
    keywords = add_metadata(text)
    ####################################################################### 
    # Definindo metadados
    ####################################################################### 

    metadata1 = onto.Metadata(keywords[0][1])
    metadata2 = onto.Metadata(keywords[1][1])
    metadata3 = onto.Metadata(keywords[2][1])
    metadata4 = onto.Metadata(keywords[3][1])

    ####################################################################### 
    # atribuindo metadados a uma instância tipo table
    #######################################################################

    table6.hasMetadata = [metadata1]
    table6.hasMetadata.append(metadata2)
    table6.hasMetadata.append(metadata3)
    table6.hasMetadata.append(metadata4)

    onto.save(file = "ontologia/ontologia-prov.owl")
    
    

def add_metadata(text):
    print("| **** Extracting metadata from text: Done")
    text = text
    language = "pt"
    max_ngram_size = 1
    deduplication_thresold = 0.9
    deduplication_algo = 'seqm'
    windowSize = 1
    numOfKeywords = 4

    ####################################################################### 
    # Extraindo quatro keywords do conteudo de metadados
    #######################################################################

    custom_kw_extractor = yake.KeywordExtractor(    lan=language, n=max_ngram_size, 
                                                    dedupLim=deduplication_thresold, 
                                                    dedupFunc=deduplication_algo, 
                                                    windowsSize=windowSize, 
                                                    top=numOfKeywords, 
                                                    features=None
                                                )
    keywords = custom_kw_extractor.extract_keywords(text)
    # print(len(keywords))
    # print(keywords)
    print("| **** Adding Metadata to instances: Done")
    return(keywords)

onto = create_ontology(onto)
create_instances_and_links(onto)