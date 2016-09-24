package co.flock.flockathon.jetty;
/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/12/13
 * Time: 1:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main  {

    private JettyServer server;

    public Main() {
        server = new JettyServer(8070);
    }

    public static void main(String... anArgs) throws Exception {
        new Main().start();
    }

    public void start() throws Exception {
        server.start();
        server.join();
    }
}

