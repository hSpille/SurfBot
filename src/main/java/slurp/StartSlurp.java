package slurp;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartSlurp implements SurfBotListener {

    private LinkStorage linkStorage;
    ExecutorService executor;
    Boolean useProxy;

    public StartSlurp(int threads, Boolean useProxy) {
        System.out.println("Using " + threads + " Threads!");
        String out = useProxy  ? "Using Proxy" : "Not Using proxy";
        System.out.println(out);
        this.executor = Executors.newFixedThreadPool(threads);
        this.useProxy = useProxy;
    }

    public void startSurfingTheWeb(String startUrl) {
        this.linkStorage = new LinkStorage();
        SurfBot s = new SurfBot(startUrl, this, this.useProxy);
        this.executor.submit(s);

    }

    public void doneSurfing(ArrayList<String> linksFound, String myVisitedUrl) {
        for (String pageLink : this.linkStorage.speicherNeueLinks(linksFound)) {
            SurfBot s = new SurfBot(pageLink, this, this.useProxy);
            this.executor.submit(s);
        }
    }
}
