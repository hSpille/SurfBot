package slurp;

import java.util.ArrayList;
import java.util.HashMap;

public class LinkStorage {

    private LinkStripper lStrip = new LinkStripper();
    HashMap<String, String> myCrappyStorage = new HashMap<String, String>();
    HashMap<String, String> visitedLinks = new HashMap<String, String>();

    public ArrayList<String> speicherNeueLinks(ArrayList<String> links) {
        ArrayList<String> neue = new ArrayList<String>();
        for (String link : links) {
            String plainHttpHost = this.lStrip.stripUrl(link);
            if (!this.myCrappyStorage.containsKey(plainHttpHost)) {
                this.myCrappyStorage.put(plainHttpHost, plainHttpHost);
                neue.add(plainHttpHost);
            }
        }
        return neue;
    }

    public void storeVisited(String link) {
        this.visitedLinks.put(link, link);
        System.out.println("Total Links Visited: " + this.visitedLinks.size() + " Total Found: " + this.myCrappyStorage.size());
    }
}
