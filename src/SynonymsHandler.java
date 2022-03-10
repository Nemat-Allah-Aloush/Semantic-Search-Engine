import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.dictionary.Dictionary;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class SynonymsHandler {
    private static Dictionary dictionary = null;
    public static void init(){
        if(dictionary==null){
            try {
                String propsFile = "src\\extjwnl\\src\\main\\resources\\net\\sf\\extjwnl\\file_properties.xml";
                dictionary = Dictionary.getInstance(new FileInputStream(propsFile));
            } catch (FileNotFoundException | JWNLException ex) {
                Logger.getLogger(SynonymsHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static String getIndexedWord(POS pos, String w){
        init();
        String lemmas="";
        try {
            IndexWord indexWord = dictionary.lookupIndexWord(pos, w);
              if(indexWord !=null)
                        {
                             List<Synset> synsets = indexWord.getSenses();
                             if (!synsets.isEmpty())
                            {
                                ArrayList<String> al = new ArrayList<>();
                                // add elements to al, including duplicates
                                HashSet hs = new HashSet();
                                for (int i = 0; i < synsets.size(); i++)
                                {
                                List<Word> wordForms = synsets.get(i).getWords();
                                 
                                for (int j = 0; j < wordForms.size(); j++)
                                 {
                                   // String managedString=wordForms.get(j).getLemma().replace(" "," +");//replaces all occurrences of ' ' to '+'  
                                    if(!wordForms.get(j).getLemma().contains(" "))
                                    {al.add(wordForms.get(j).getLemma());}
                                 }
                                 //removing duplicates
                                hs.addAll(al);
                                al.clear();
                                al.addAll(hs);
                                }
                                for (int i = 0; i < al.size(); i++) {
                                 lemmas=lemmas+" "+(al.get(i));}
                            }
                        }
            return lemmas;
        } catch (JWNLException ex) {
            Logger.getLogger(SynonymsHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
