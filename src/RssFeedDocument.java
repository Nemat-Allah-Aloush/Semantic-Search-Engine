import java.util.Date;

public class RssFeedDocument {
       // private int Id;
	private String title;
	private String description;
	private Date pubDate;
	
	public RssFeedDocument() {
		this(null, null, null);
	}
	
	public RssFeedDocument(String title, String description, Date pubDate) {
		//this.Id=i;
                this.title = title;
		this.description = description;
		this.pubDate = pubDate;
	}
/*
        public String getId() {
		return Integer.toString(Id);
	}

	public void setTitle(int i) {
		this.Id = i;
	}*/
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
        @Override
	public String toString() {
		return "Title: "+title+"\n description: "+description+"\n publication date: "+pubDate;
	}
}