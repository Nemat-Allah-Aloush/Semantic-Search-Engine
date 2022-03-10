
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.lucene.analysis.core.StopAnalyzer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class DocumentAnalyzer {

    private static StanfordCoreNLP pipeline;
    public static RssFeedDocument AnalyzeDocument(RssFeedDocument documentText)
    {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
       
        pipeline = new StanfordCoreNLP(props);
        String lemmas_title=lemmatize( documentText.getTitle() );
        String lemmas_description=lemmatize( documentText.getDescription() );
        documentText.setDescription(lemmas_description);
        documentText.setTitle(lemmas_title);
        return documentText;
    }
    public static String lemmatize(String documentText )
    {
        String lemmas="";
Set<?> stopWords = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);

        // run all Annotators on this text
        pipeline.annotate(document);

        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word except stopwords into the list of lemmas
                 if (!stopWords.contains(token.word().toLowerCase())) 
                     {
                         lemmas =lemmas +" "+(token.get(LemmaAnnotation.class));
                     }
                     
                 }
        }

        return lemmas;
    } 
    
}
