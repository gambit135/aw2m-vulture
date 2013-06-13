package com.example.controller;

import com.example.model.MusicalArtist;
import com.example.service.MusicalArtistService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import retrieval.wikipedia.infobox.model.MusicalArtistInfobox;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com> @date 26/11/2012 -
 * 04:09:13 AM
 */
@Controller
public class MusicalArtistController {

    @Autowired
    private MusicalArtistService musicalArtistService;

    @RequestMapping("/")
    public String listMusicalArtist(Map<String, Object> map) {
        map.put("musicalArtist", new MusicalArtist());
        map.put("musicalArtistList", musicalArtistService.listMusicalArtist());

        return "musicalArtist";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addMusicalArtist(@ModelAttribute("musicalArtist") MusicalArtist musicalArtist, BindingResult result) {

        musicalArtistService.addMusicalArtist(musicalArtist);

        return "redirect:/musicalArtist/";
    }

    @RequestMapping("/delete/{musicalArtistName}")
    public String deleteMusicalArtist(@PathVariable("musicalArtistName") String musicalArtistName) {

        musicalArtistService.removeMusicalArtist(musicalArtistName);

        return "redirect:/musicalArtist/";
    }

    @RequestMapping("/persistSessionAttribute")
    public String showSessionAttribute(HttpServletRequest request) {
        /*
         * try to persist each of the objects on the session Attribute
         */
        String artists = "Artists: \n";
        String errors = "Errors: \n";

        HttpSession session = request.getSession();

        if (session.isNew()) {
            errors.concat("Session is new \n");
        }
        HashSet set = (HashSet<MusicalArtistInfobox>) session.getAttribute("infoboxes");
        String test = "test D: " + (String) session.getAttribute("test");
        if (set != null) {
            test = test + set.size() + " is the set size";
        }
        else {
            test = test + "NO INFOBOXES on session!!!";
        }

        for (MusicalArtistInfobox mai : (HashSet<MusicalArtistInfobox>) session.getAttribute("infoboxes")) {
            try {
                MusicalArtist ma = new MusicalArtist();
                artists = artists + mai.getName();
                artists = artists + ("\n");
                ma.setName(mai.getName());
                ma.setImageurl(mai.getImage());
                ma.setImagecaption(mai.getCaption());
                musicalArtistService.addMusicalArtist(ma);
            }
            catch (Exception e) {
                errors = errors + "\n";
                errors = errors + e.getLocalizedMessage();
            }
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("test", test);
        map.put("artists", artists);
        map.put("errors", errors);
        session.setAttribute("map", map);
        return "showPersistenceResults";
    }
    /*
     * @RequestMapping("/PrintMusicalInfoboxes") public String
     * showSessionAttribute(HttpServletRequest request) {
     *
     * }
     */
}
