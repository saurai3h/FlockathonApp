package co.flock.flockathon.jetty;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/12/13
 * Time: 1:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class JettyServer
{
    // TODO: You should configure this appropriately for your environment
    private static final String LOG_PATH = "./var/logs/access/yyyy_mm_dd.request.log";

    private static final String WEB_XML = "WEB-INF/web.xml";
    private static final String CLASS_ONLY_AVAILABLE_IN_IDE = "co.flock.flockathon.jetty.IDE";
    private static final String PROJECT_RELATIVE_PATH_TO_WEBAPP = "src/main/webapp";

    public static interface WebContext
    {
        public File getWarPath();
        public String getContextPath();
    }


    private Server server;
    private int port;
    private String bindInterface;

    public JettyServer(int aPort)
    {
        this(aPort, null);
    }

    public JettyServer(int aPort, String aBindInterface)
    {
        port = aPort;
        bindInterface = aBindInterface;
    }

    public void start() throws Exception
    {

        server = new Server(createThreadPool());

        server.addConnector(createConnector());
        server.setHandler(createHandlers());
        server.setStopAtShutdown(true);

        server.start();
    }

    public void join() throws InterruptedException
    {
        server.join();
    }

    public void stop() throws Exception
    {
        server.stop();
    }

    private ServerConnector createConnector()   {
        HttpConfiguration httpConfiguration = new HttpConfiguration();
        httpConfiguration.setOutputBufferSize(65536);

        ServerConnector connector = new ServerConnector(server, new HttpConnectionFactory(httpConfiguration));
        connector.setPort(port);
        connector.setHost(bindInterface);
        connector.setIdleTimeout(30000);
        return connector;
    }

    private ThreadPool createThreadPool()
    {

        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setName("jetty-threads");
        threadPool.setMinThreads(10);
        threadPool.setMaxThreads(1000);
        threadPool.setDetailedDump(true);
        return threadPool;
    }


    private HandlerCollection createHandlers()
    {
        WebAppContext _ctx = new WebAppContext();
        _ctx.setContextPath("/");

        if(isRunningInShadedJar())
        {
            _ctx.setWar(getShadedWarUrl());
        }
        else
        {
            _ctx.setWar(PROJECT_RELATIVE_PATH_TO_WEBAPP);
        }

        List<Handler> _handlers = new ArrayList<Handler>();

        _handlers.add(_ctx);
        _handlers.add(new DefaultHandler());

        HandlerList _contexts = new HandlerList();
        _contexts.setHandlers(_handlers.toArray(new Handler[0]));

        RequestLogHandler _log = new RequestLogHandler();
        _log.setRequestLog(createRequestLog());

        HandlerCollection _result = new HandlerCollection();
        _result.setHandlers(new Handler[] {_contexts, _log});

        return _result;
    }

    private RequestLog createRequestLog()
    {
        NCSARequestLog _log = new NCSARequestLog();

        File _logPath = new File(LOG_PATH);
        _logPath.getParentFile().mkdirs();

        _log.setFilename(_logPath.getPath());
        _log.setRetainDays(90);
        _log.setExtended(false);
        _log.setAppend(true);
        _log.setLogTimeZone("GMT");
        _log.setLogLatency(true);
        return _log;
    }

//---------------------------
// Discover the war path
//---------------------------

    private boolean isRunningInShadedJar()
    {

        try
        {
            Class.forName(CLASS_ONLY_AVAILABLE_IN_IDE);
            return false;
        }
        catch(ClassNotFoundException anExc)
        {
            return true;
        }
    }

    private URL getResource(String aResource)
    {
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource(".").toString());
        return Thread.currentThread()
                .getContextClassLoader()
                .getResource(aResource);
    }

    private String getShadedWarUrl()
    {
//        System.out.println(getResource(".").toString());
        String _urlStr = getResource(WEB_XML).toString();
        // Strip off "WEB-INF/web.xml"
        return _urlStr.substring(0, _urlStr.length() - WEB_XML.length());
//        return "./FlockathonApp.war";
    }
}