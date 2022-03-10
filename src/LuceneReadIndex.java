
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneReadIndex 
{
    //Function to search for the query in the : Title, description, Both.
	public static List<searchResultDocument> searchByTitle(String text, IndexSearcher searcher) throws Exception
	{
             RssFeedParser parser=new RssFeedParser();
            parser.parse(Management.File_Dir_Path);
            List<RssFeedDocument> ressFDocs=parser.getDocuments();
	List<searchResultDocument> results=new LinkedList<>();
			
            QueryParser qp ;
            qp = new QueryParser("Title", new StandardAnalyzer());
            int index;
            index = -1;
            Query idQuery = qp.parse(text);
            TopDocs topDocs = searcher.search(idQuery, 10);
            for (ScoreDoc sd : topDocs.scoreDocs) 
            {
		Document d = searcher.doc(sd.doc);
                index=parseInt(d.get("Id"));
                
		results.add(new searchResultDocument(ressFDocs.get(index).getTitle(),ressFDocs.get(index).getDescription(),sd.score));
	}
            return results;
	}

	public static List<searchResultDocument> searchByDescription(String text, IndexSearcher searcher) throws Exception
	{
             RssFeedParser parser=new RssFeedParser();
            parser.parse(Management.File_Dir_Path);
            List<RssFeedDocument> ressFDocs=parser.getDocuments();
	List<searchResultDocument> results=new LinkedList<>();
			
            QueryParser qp ;
            qp = new QueryParser("Description", new StandardAnalyzer());
            int index=-1;
            Query idQuery = qp.parse(text);
            TopDocs topDocs = searcher.search(idQuery, 10);
            for (ScoreDoc sd : topDocs.scoreDocs) 
            {
		Document d = searcher.doc(sd.doc);
                index=parseInt(d.get("Id"));
                
		results.add(new searchResultDocument(ressFDocs.get(index).getTitle(),ressFDocs.get(index).getDescription(),sd.score));
	}
            return results;
	}
public static List<searchResultDocument> searchByBoth(String text, IndexSearcher searcher) throws Exception
	{
            text=QueryAnalyzer.AnalyzeQuery(text);
            //get searching results by title
	List<searchResultDocument> results_title=searchByTitle(text, searcher);
            //get searching results by Description
	List<searchResultDocument> results_Desc=searchByDescription(text, searcher);
            //Combine the two lists without duplicating the items
       List<searchResultDocument> combinedList ;
       combinedList=results_title;
       boolean status;
       for (searchResultDocument tempD :results_Desc)
       {
           status=false;
           for (searchResultDocument tempT : results_title)
           {if(tempD.getTitle().equals(tempT.getTitle()))
               {
                   status=true;
                   break;}
           }
           if (status ==false)
               combinedList.add(tempD);
       }
       //sort according to the Score
       combinedList.sort(Comparator.comparing(searchResultDocument::getScore).reversed());
        return combinedList;
	}
//Create the searcher to be used ^_^
        public static IndexSearcher createSearcher(String Index_Dir_Path) throws IOException {
		Directory dir = FSDirectory.open(Paths.get(Index_Dir_Path));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
                searcher.setSimilarity(new ClassicSimilarity());
                return searcher;
	}
}

