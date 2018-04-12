package ru.yandex.market.github.pr.stats.config.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.yandex.market.github.pr.stats.config.mvc.WebMvcConfig;

/**
 * @author fbokovikov
 */
@Configuration
public class JettyServerConfig implements ApplicationContextAware {

    private ApplicationContext rootContext;

    @Value("${jetty.maxThreads}")
    private int jettyMaxThreads;

    @Value("${jetty.minThreads}")
    private int jettyMinThreds;

    @Value("${http.port}")
    private int httpPort;

    @Value("${http.host}")
    private String httpHost;

    @Bean
    public Handler mvcHandler() {
        ServletHandler handler = new ServletHandler();
        WebApplicationContext wac = createWebApplicationContext();

        handler.addServletWithMapping(createDispatcherServlet(wac), "/*");

        ContextHandler context = new ContextHandler(handler, "/");
        context.addEventListener(new ContextLoaderListener(wac));
        context.setContextPath("/");
        return handler;
    }

    @Bean
    public ThreadPool threadPool() {
        return new QueuedThreadPool(jettyMaxThreads, jettyMinThreds);
    }

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public Server server() {
        Server server = new Server(threadPool());
        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setHost(httpHost);
        serverConnector.setPort(httpPort);
        server.setConnectors(new ServerConnector[] {serverConnector});
        server.setHandler(mvcHandler());
        return server;
    }

    protected ServletHolder createDispatcherServlet(WebApplicationContext wac) {
        ServletHolder holder = new ServletHolder(new DispatcherServlet(wac));
        holder.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        holder.setName("dispatcherServlet");
        holder.setDisplayName("dispatcherServlet");
        return holder;
    }

    protected WebApplicationContext createWebApplicationContext() {
        AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
        wac.setParent(rootContext);
        wac.register(WebMvcConfig.class);
        wac.setNamespace("parent-spring-mvc");
        return wac;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.rootContext = applicationContext;
    }
}
