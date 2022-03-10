import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class LuceneWriteIndex
{
    public static int id=0;
    //create Lucene Document for the parsed document 
    public static Document createDocument(RssFeedDocument document) throws IOException 
     {
        Document doc = new Document();
        doc.add(new TextField("Id",Integer.toString(id), Field.Store.YES));
        doc.add(new TextField("Title", document.getTitle(), Field.Store.YES));
        doc.add(new TextField("Description", document.getDescription(), Field.Store.YES));
        doc.add(new StringField("Hour", DateTools.dateToString(document.getPubDate(),DateTools.Resolution.HOUR) , Field.Store.YES));
      id++;
        return doc;
    }
	public static IndexWriter createWriter(String Index_Dir_Path) throws IOException 
	{
		FSDirectory dir = FSDirectory.open(Paths.get(Index_Dir_Path));
		IndexWriterConfig config = new IndexWriterConfig();
		IndexWriter writer = new IndexWriter(dir, config);
		return writer;
	}
}
