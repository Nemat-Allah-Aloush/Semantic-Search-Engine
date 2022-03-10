
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import net.sf.extjwnl.data.POS;
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
public class QueryAnalyzer {

    private static StanfordCoreNLP pipeline;
    public static String AnalyzeQuery(String queryString)
    {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner");

        pipeline = new StanfordCoreNLP(props);
        String lemmas_title=lemmatize( queryString );
        return lemmas_title;
    }
    public static String lemmatize(String documentText )
    {
        Set<?> stopWords = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
 
        String lemmas="";

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);

        // run all Annotators on this text
        pipeline.annotate(document);

        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                String temp=null;
                // Retrieve and add the lemma for each word except stopwords into the list of lemmas
               if (!stopWords.contains(token.word().toLowerCase())) 
                     {
                     //    Set<Class<?>> tokenAnnotations = token.keySet();
                         //token.get(CoreAnnotations.StackedNamedEntityTagAnnotation.class);
                         if(token.get(CoreAnnotations.StackedNamedEntityTagAnnotation.class)==null)
                                 //!tokenAnnotations.contains(NamedEntityTagAnnotation.class))
                         {
                             
                             String pos = token.get(PartOfSpeechAnnotation.class);
                             if( pos.contains("NN"))
                             {
                                 temp= temp+" "+SynonymsHandler.getIndexedWord(POS.NOUN, token.get(LemmaAnnotation.class));
                             }
                              if ( pos.contains("VB"))
                             {
                                 temp= temp+" "+SynonymsHandler.getIndexedWord(POS.VERB, token.get(LemmaAnnotation.class));
                             }
                             else if( pos.contains("RB"))
                             {
                                 temp=temp+" "+ SynonymsHandler.getIndexedWord(POS.ADVERB, token.get(LemmaAnnotation.class));
                             }
                             else if(  pos.contains("JJ"))
                             {
                                 temp= temp+" "+SynonymsHandler.getIndexedWord(POS.ADJECTIVE, token.get(LemmaAnnotation.class));
                             }
                             
                         }
                         lemmas =lemmas +" "+(token.get(LemmaAnnotation.class));
                         if (temp!=null) lemmas=lemmas+temp;
                    }
                 
             } 
         }return lemmas;
}}
