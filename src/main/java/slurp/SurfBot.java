package slurp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SurfBot implements Runnable {

    HttpHost proxy = new HttpHost("someproxy.some.so", 8080);
    CredentialsProvider credsProvider = new BasicCredentialsProvider();
    private String startpoint = null;
    private CloseableHttpClient httpclient;
    private String urlToSurf;
    private SurfBotListener listener;
    private Boolean usePRoxy;

    public SurfBot(String urlToSurf, SurfBotListener listener, Boolean useProxy) {
        this.urlToSurf = urlToSurf;
        this.listener = listener;
        this.usePRoxy = useProxy;
    }

    public String requestPageContent() throws IOException {
        if(this.usePRoxy){
            this.credsProvider.setCredentials(new AuthScope("someproxy.some.so", 8080), new UsernamePasswordCredentials("pw", "user"));
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(this.proxy);
            this.httpclient = HttpClients.custom().setRoutePlanner(routePlanner).setDefaultCredentialsProvider(this.credsProvider).build();
        }else{
           this.httpclient = HttpClients.createDefault();
        }
        HttpGet httpget = new HttpGet(this.urlToSurf);

        CloseableHttpResponse response = this.httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();

        String contentString = EntityUtils.toString(entity);
        content.close();
        response.close();
        // this.httpclient.close();
        return contentString;
    }

    public ArrayList<String> findLinks(String content) {
        ArrayList<String> toReturn = new ArrayList<String>();
        Document doc = Jsoup.parse(String.valueOf(content));
        Elements links = doc.select("a[href]");
        for (Element element : links) {
            String mayBeLink = element.attr("abs:href");
            if (mayBeLink.isEmpty()) {
                continue;
            }
            toReturn.add(mayBeLink);
        }
        return toReturn;
    }

    public void run() {
        String requestPageContent;
        try {
            requestPageContent = this.requestPageContent();
            this.listener.doneSurfing(this.findLinks(requestPageContent),this.urlToSurf);
        } catch (IOException cause) {
            System.out.println("Exception fuer URL:" + this.urlToSurf);
        }
    }

}
