package slurp;

import java.io.IOException;

public class startup {

    public static void main(String[] args) throws IOException {

        StartSlurp s = new StartSlurp(getThreads(args), getProxyArg(args));
        s.startSurfingTheWeb("http://www.wikipedia.de");
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException cause) {
                // TODO Auto-generated catch block
                cause.printStackTrace();
            }
        }

    }

    private static Boolean getProxyArg(String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args.length == 1) {
            return parseProxyArg(args, 0);
        }
        if (args.length == 2) {
            return parseProxyArg(args, 1);
        }
        return false;
    }

    /**
     * @param args
     * @return
     * @author hspille
     * @param pos
     * @since  05.02.2015 - 1.0.0
     */
    private static Boolean parseProxyArg(String[] args, int pos) {
        String arg1 = args[pos];
        try {
            Boolean val = Boolean.parseBoolean(arg1);
            return val;
        } catch (Exception e) {
            // ist kein bool
            return false;
        }
    }

    /**
     * @param args
     * @return
     * @author hspille
     * @since 05.02.2015 - 1.0.0
     */
    private static int getThreads(String[] args) {
        if(args.length == 0){
            return 2;
        }
        if(args.length == 1 || args.length == 2){
            try{
                int threads = Integer.parseInt(args[0]);
                return threads;
            } catch (Exception e){
                //kein int
                return 2;
            }

        }
        return 2;
    }
}
