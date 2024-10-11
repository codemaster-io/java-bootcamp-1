package com.codemaster.io;

import com.codemaster.io.litespring.ApplicationContext;
import com.codemaster.io.litespring.LiteSpringApplication;
import com.codemaster.io.litespring.annotation.PackageScan;
import com.codemaster.io.models.Product;
import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.SearchService;

import java.util.List;


@PackageScan(scanPackages = {"com.codemaster.io"})
public class MainApplication {

    public static void main(String[] args) throws Exception {

        ApplicationContext applicationContext = LiteSpringApplication.run(MainApplication.class);

//
//        ProductService productService = (ProductService) applicationContext.getBean(ProductService.class);
//        SearchService searchService = (SearchService) applicationContext.getBean("SearchService");
//
//        Product product1 = new Product();
//        product1.setName("iPhone 14");
//        productService.addProduct(product1);
//
//        Product product2 = new Product();
//        product2.setName("iPhone 16");
//        productService.addProduct(product2);
//
//        List<Product> productList = searchService.search("iphone");
//        for(Product product : productList) {
//            System.out.println("product = " + product);
//        }

    }
}


