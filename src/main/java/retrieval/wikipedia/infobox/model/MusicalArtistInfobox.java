package retrieval.wikipedia.infobox.model;

import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * A JavaBean describing a musical artist's infobox, as stored by Wikipedia, on
 * the musical artist page, conveniently storing only the information required.
 *
 * @author Alejandro Tellez G. <java.util.fck@hotmail.com>
 */
public class MusicalArtistInfobox {
    /*
     * General fields used for bands and persons alike.
     */

    /**
     * The title of the article, as used on OpenSearch and Query
     */
    private String wikiArticleTitle;
    private String name;
    private String image;
    private String caption;
    private String alias;
    private String origin;
    private LinkedList<Genre> genres;
    /**
     * Years this musical artist has been active. Reconsider changing datatype.
     */
    private String yearsActive;
    private String label;
    /**
     * Website of this musical artist. Reconsider changing datatype, as this may
     * have multiple values, and each URL may have a description.
     */
    private String website;
    /**
     * The associated acts to this musical artist. Reconsider changing datatype
     * to something like LinkedList.
     */
    private String associatedActs;
    /*
     * ************
     * End of general fields. Start of specific fields, whether it's a band or a
     * person.
     *
     */
    //If the musical artist is a person
    private String birthName;
    private String birthDate;
    private String birthPlace;
    private String deathDate;
    private String deathPlace;
    private String instrument;
    private String notableInstruments;
    private String occupation;
    //If the musical artist is a band
    private String currentMembers;
    private String pastMembers;

    public void importGenericInfobox(GenericInfobox gi) {
        //If it is a musical artist
        if (gi.getFirst().indexOf("musical artist") != -1) {
            //Set title
            this.setWikiArticleTitle(gi.getTitle());
            //For each of the lines on the infobox
            for (String s : gi) {
                //If the current line is an attribute
                if (s.indexOf("=") != -1) {
                    //If that attribute is the name
                    if (s.indexOf("| name") != -1) {
                        this.setName(s);
                    }
                    if (s.indexOf("| image") != -1) {
                        this.setImage(s);
                    }
                    if (s.indexOf("| caption") != -1) {
                        this.setCaption(s);
                    }
                    if (s.indexOf("| genre") != -1) {
                        this.setGenre(s);
                    }

                }
            }
        }
    }

    public void setWikiArticleTitle(String wikiArticleTitle) {

        this.wikiArticleTitle = wikiArticleTitle;
    }

    private void setName(String name) {
        if (name.indexOf("=") + 2 < name.length()) {
            name = name.substring(name.indexOf("=") + 2);
            this.name = name;
        }
    }

    private void setImage(String image) {
        if (image.indexOf("=") + 2 < image.length()) {
            image = image.substring(image.indexOf("=") + 2);
            this.image = image;
        }
    }

    private void setCaption(String caption) {
        if (caption.indexOf("=") + 2 < caption.length()) {
            caption = caption.substring(caption.indexOf("=") + 2);
            this.caption = caption;
        }
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setAssociatedActs(String associatedActs) {
        if (associatedActs.indexOf("=") + 2 < associatedActs.length()) {
            associatedActs = associatedActs.substring(caption.indexOf("=") + 2);
            this.associatedActs = associatedActs;
        }
    }

    public void setGenre(String genres) {
        genres = genres.substring(genres.indexOf("=") + 2);
        StringTokenizer st = new StringTokenizer(genres, ",");
        this.genres = new LinkedList<Genre>();
        while (st.hasMoreTokens()) {
            try {
                String genre = st.nextToken();
                if (genre.indexOf("]") != -1 && genre.lastIndexOf("[") != -1) {
                    Genre g = new Genre();
                    if (genre.lastIndexOf("[") != -1 && genre.lastIndexOf("]") != -1) {
                        genre = genre.substring(genre.lastIndexOf("[") + 1, genre.indexOf("]"));
                        if (genre.indexOf("|") != -1) {
                            String title = genre.substring(0, genre.indexOf("|"));
                            genre = genre.substring(genre.indexOf("|") + 1);
                            g.setWikiArticleTitle(title);
                        }
                        g.setGenre(genre);
                        this.genres.add(g);
                    }
                }
            }
            catch (Exception e) {
            }
        }
        //Not visible on server implem
        System.out.println("Printing genres: ");

        for (Genre g : this.genres) {
            System.out.println("Genre: " + g.getWikiArticleTitle()
                    + "|" + g.getGenre());
        }
        //this.genre = genre;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setYearsActive(String yearsActive) {
        this.yearsActive = yearsActive;
    }

    public String getWikiArticleTitle() {
        return wikiArticleTitle;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getCaption() {
        return caption;
    }

    public String getAlias() {
        return alias;
    }

    public String getOrigin() {
        return origin;
    }

    public String getLabel() {
        return label;
    }

    public String getWebsite() {
        return website;
    }

    public String getBirthName() {
        return birthName;
    }

    public LinkedList<Genre> getGenres() {
        return genres;
    }
}
