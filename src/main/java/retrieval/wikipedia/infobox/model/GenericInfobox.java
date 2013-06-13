package retrieval.wikipedia.infobox.model;

import java.util.LinkedList;

/**
 * Abstracts lines of information of a wiki infobox as a LinkedList of String
 * objects, where each line is a textual line of the infobox, as retrieved by
 * raw processing of the wiki article. This class only provides methods for
 * printing it's values to the standard output, printing also it's size; aside
 * that, it behaves as a normal LinkedList of String objects.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 4/09/2012 - 10:37:44 PM
 */
public class GenericInfobox extends LinkedList<String> {

    /**
     * The title of the article that contains this infobox, as used by
     * OpenSearch and Query.
     */
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        System.out.println("List size: " + this.size());
        int c = 1;
        for (String s : this) {
            System.out.println(c++ + ". " + s);
        }
        return "";
    }
}
