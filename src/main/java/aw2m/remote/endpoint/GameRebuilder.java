package aw2m.remote.endpoint;

import aw2m.common.core.GameInstance;
import aw2m.common.core.Player;
import aw2m.common.serialize.Deserialize;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * This class rebuilds the game as it was sent by the mobile device and wrapped
 * unto the request sent to the DeserializeEndpointServlet.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 16/08/2013 - 02:38:28 AM
 */
public class GameRebuilder {

    /**
     * A reference to a HTTPServletRequest, where the game data is embedded.
     */
    HttpServletRequest request;
    GameInstance rebuiltGame;

    public GameRebuilder(HttpServletRequest request) {
        this.request = request;
    }

    public void getParametersFromRequest() {
        byte mapChosen = 0;
        boolean isPredefined ;

        //First, check if is predefined

        //Get list of names
        List<String> names = Collections.list(
                (Enumeration<String>) request.getParameterNames());
        
        //Search for isPredefined value
        for(String s : names){
            if(s.equals("isPredefined")){
                if(request.getParameterValues(s)[0].equals("true")){
                    isPredefined = true;
                }
                else{
                    isPredefined  = false;
                }
            }
            if (s.equals("mapChosen")){
                mapChosen = Byte.parseByte(request.getParameterValues(s)[0]);
            }
        }

        //Steps to rebuild a game: 
        //1. Create a new Deserialize object
        //
        //2. Deserialize players
        //   Create a Player array and assign it to the 
        //   return value of d.deserializePlayers(String)
        //
        //3. Deserialize terrain
        //  if(isNOTpredeployed, i.e. the terrain is on a String)
        //      Create a GridCell[][] and assign it to the return
        //      value of d.deserializeTerrain(String)
        //
        //  else:
        //      The predefined map must be loaded from the catalog:
        //      Only the terrain part should be taken into account
        //      The 

        Deserialize d = new Deserialize();
        //First deserialize Players
        Player[] players = d.deserializePlayers(request.getParameter("players"));


        //Deserialize map and terrain
        if (request.getParameter("isPredefined").equals("true")) {
            //Is Predefined - Load map from catalog
        }
        else {
        }
    }

    /**
     * Plays the rebuilt game.
     */
    public void play() {
    }
}
