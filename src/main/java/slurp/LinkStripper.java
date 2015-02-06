package slurp;

public class LinkStripper {

    public String stripUrl(String url) {
        String[] split = url.split("/");
        String stripped = split[0] + "//" + split[1] + split[2];
       return stripped;
    }

}
