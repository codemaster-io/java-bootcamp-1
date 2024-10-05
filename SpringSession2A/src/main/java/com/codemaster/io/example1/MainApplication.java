package com.codemaster.io.example1;

import com.codemaster.io.example1.controller.ProductController;
import com.codemaster.io.example1.controller.ProductServlet;
import com.codemaster.io.example1.controller.SearchServlet;
import com.codemaster.io.example1.repository.ProductRepository;
import com.codemaster.io.example1.service.ProductService;
import com.codemaster.io.example1.service.SearchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class MainApplication {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        tomcat.setBaseDir("temp"); // Specify the base directory for temporary files

        Connector conn = new Connector();
        conn.setPort(8080);
        tomcat.setConnector(conn);

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);

        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        SearchService searchService = new SearchService(productService);

        ProductController productController = new ProductController(productService, searchService);
        ProductServlet productServlet = new ProductServlet(productController);
        SearchServlet searchServlet = new SearchServlet(productController);

        tomcat.addServlet(contextPath, "WelcomeServlet", getWelcomeServlet());
        context.addServletMappingDecoded("/", "WelcomeServlet");


        tomcat.addServlet(contextPath, "ProductServlet", productServlet);
        context.addServletMappingDecoded("/products/*", "ProductServlet");


        tomcat.addServlet(contextPath, "SearchServlet", searchServlet);
        context.addServletMappingDecoded("/products/search", "SearchServlet");

        tomcat.start();
        tomcat.getServer().await();

    }

    public static HttpServlet getWelcomeServlet() {
        return new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
//                resp.setContentType("text/html");
                PrintWriter writer = resp.getWriter();

                writer.println("<html><title>Welcome</title><body>");
                writer.println("<h1>Hello world!</h1>");
                writer.println("</body></html>");
            }
        };
    }
}


