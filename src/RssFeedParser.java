import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RssFeedParser extends DefaultHandler {
	
	private final List<RssFeedDocument> docs;
	private final DateFormat formatter;
	private boolean item;
	private boolean title;
	private boolean description;
	private boolean pubDate;
	private RssFeedDocument currentDoc;
	
	public RssFeedParser() {
		this.docs = new LinkedList<RssFeedDocument>();
		
		this.formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
		
		this.item = false;
		this.title = false;
		this.description = false;
		this.pubDate = false;
	}
	
	// parses the RSS feed in the given URI
	public void parse(String uri) {
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(uri, this);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	// returns the documents of the RSS feed as a list
	public List<RssFeedDocument> getDocuments() {
		return this.docs;
	}
	
	
	// methods for the SAX parser below
	
        @Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
            switch (qName) {
                case "item":
                    this.item = true;
                    this.currentDoc = new RssFeedDocument();
                    break;
                case "title":
                    this.title = true;
                    break;
                case "description":
                    this.description = true;
                    break;
                case "pubDate":
                    this.pubDate = true;
                    break;
                default:
                    break;
            }
	}
	
        @Override
	public void endElement(String uri, String localName, String qName)  {
            switch (qName) {
                case "item":
                    this.item = false;
                    if (this.currentDoc.getTitle() != null)
                        docs.add(this.currentDoc);
                    break;
                case "title":
                    this.title = false;
                    break;
                case "description":
                    this.description = false;
                    break;
                case "pubDate":
                    this.pubDate = false;
                    break;
                default:
                    break;
            }
	}
	
        @Override
	public void characters(char[] ch, int start, int length) {
		String text = "";
		for (int i=0; i<length; i++)
			text += ch[start+i];
		if (this.item) {
			if (this.title)
				this.currentDoc.setTitle(text);
			else if (this.description)
				this.currentDoc.setDescription(text);
			else if (this.pubDate) {
				try {
					this.currentDoc.setPubDate(this.formatter.parse(text));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}