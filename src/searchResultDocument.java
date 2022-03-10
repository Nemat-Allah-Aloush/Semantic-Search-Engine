/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class searchResultDocument {
    private String title;
    private String Content;
    private double score;
    public searchResultDocument(String _title,String _Content,double _Score)
    {
        this.title=_title;
        this.Content=_Content;
        this.score=_Score;
    }
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return this.Content;
	}
	
	public void setcontent(String content) {
		this.Content = content;
	}
	
	public double getScore() {
		return this.score;
	}

	public void setPubDate(double score) {
		this.score = score;
	}
}
