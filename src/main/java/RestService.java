import controllers.LoginController;
import controllers.RegisterController;
import dbal.login.LoginRepo;
import dbal.register.RegisterRepo;
import handlers.login.ILoginHandler;
import handlers.login.LoginHandler;
import handlers.register.IRegisterHandler;
import handlers.register.RegisterHandler;
import logging.Logger;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class RestService {
    public static void main(String[] args) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        Server jettyServer = new Server();

        int secureport = 8080;
        int port = 8081;

        //http
        HttpConfiguration http = new HttpConfiguration();
        http.setSecureScheme("https");
        http.setSecurePort(secureport);

        //http connector
        ServerConnector httpconnector = new ServerConnector(jettyServer, new HttpConnectionFactory(http));
        httpconnector.setIdleTimeout(30000);
        httpconnector.setPort(port);

        //https config
        HttpConfiguration https = new HttpConfiguration(http);
        https.addCustomizer(new SecureRequestCustomizer());

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(RestService.class.getResource("/keystore.jks").toExternalForm());
        sslContextFactory.setKeyStorePassword("123456");
        sslContextFactory.setKeyManagerPassword("123456");

        ServerConnector sslConnector = new ServerConnector(jettyServer,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(https));
        sslConnector.setPort(secureport);

        jettyServer.setConnectors(new Connector[]{sslConnector, httpconnector});
        //region Origin header
        FilterHolder cors = context.addFilter(CrossOriginFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");
        //endregion

        jettyServer.setHandler(context);
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Creating and setting handlers
        ILoginHandler loginHandler = new LoginHandler(new LoginRepo());
        LoginController.setHandler(loginHandler);

        IRegisterHandler registerHandler = new RegisterHandler(new RegisterRepo());
        RegisterController.setHandler(registerHandler);

        // Tells the Jersey Servlet which REST service/class to load
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "controllers");
        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
            Logger.getInstance().log(e);
            try {
                jettyServer.stop();
            } catch (Exception ex) {
                Logger.getInstance().log(ex);
            }
            jettyServer.destroy();
        }
    }
}
