import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.document.Document;
public class Management {

    //Index_Dir_Path: the path where to save the indexes, a path inside the project folder
    public  static String Index_Dir_Path = "src\\Indexes";
    //File_Dir_Path: the path of the file to be indexed.It is given a default value "Inside the project" , in case the user didn't choose a file
    public  static String File_Dir_Path = ".\\bbc_rss_feed.xml";
    //Index the parsed BCC Documents and write them by using IndexWriter from 'LuceneWriteIndex' class
    public static void allDocumentsParsed () throws IOException
     {
        try (IndexWriter writer = LuceneWriteIndex.createWriter(Index_Dir_Path)) {
            List<Document> documents = new ArrayList<>();
            RssFeedParser parser=new RssFeedParser();
            parser.parse(File_Dir_Path);
            List<RssFeedDocument> ressFDocs=parser.getDocuments();
            for (RssFeedDocument ressFDoc : ressFDocs)
            {
                RssFeedDocument temp;
                temp = DocumentAnalyzer.AnalyzeDocument(ressFDoc);
                Document t = LuceneWriteIndex.createDocument(temp);
                documents.add(t);
            }
            writer.deleteAll();
            writer.addDocuments(documents);
            writer.commit();
        }
     }
     // manage the string query the user entered, so we can parse it and search in the parsed documents
     public static String manageQuery(String query)
     {
         //replace the words 
         String managedString=query.replace("and ","+");//replaces all occurrences of 'and' to '+'  
         managedString=managedString.replace("or "," ");//replaces all occurrences of 'or' to ' ' 
         managedString=managedString.replace("not ","-");//replaces all occurrences of 'not' to '-' 
        managedString="+"+managedString;
       // Properties props ;
        //props= new Properties();
        //props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref,depparse,natlog,openie");
        //StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // Initialize an Annotation with some text to be annotated. The text is the argument to the constructor.
        //Annotation annotation;
        //annotation = new Annotation(managedString);
        // run all the selected Annotators on this text
        //pipeline.annotate(annotation);
        //List<CoreLabel> tokens = annotation.get(CoreAnnotations.TokensAnnotation.class);
        //List<CoreLabel> stemms = annotation.get(CoreAnnotations.LemmaAnnotation.class);
        return managedString;
     }
     
}
