package retrieval.wikipedia.infobox.model;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com> @date 5/09/2012 -
 * 02:25:17 AM
 */
public class Genre {

    private String genre;
    private String wikiArticleTitle;

    public Genre() {
        this.genre = "";
        this.wikiArticleTitle = "";
    }

    public Genre(String genre) {
        this.genre = genre;
        //When this is empty, the genre itself is the title on Wikipedia
        this.wikiArticleTitle="";
    }
    public Genre(String genre, String wikiArticleTitle) {
        this.genre = genre;
        this.wikiArticleTitle = wikiArticleTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getWikiArticleTitle() {
        return wikiArticleTitle;
    }

    public void setWikiArticleTitle(String wikiArticleTitle) {
        this.wikiArticleTitle = wikiArticleTitle;
    }
}
