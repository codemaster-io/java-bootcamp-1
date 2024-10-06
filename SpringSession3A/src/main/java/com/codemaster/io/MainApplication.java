package com.codemaster.io;

import com.codemaster.io.litespring.ApplicationFactory;
import com.codemaster.io.litespring.annotation.PackageScan;
import com.codemaster.io.models.Product;
import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.SearchService;

import java.util.List;


@PackageScan({"com.codemaster.io"})
public class MainApplication {

    public static void main(String[] args) throws Exception {

        ApplicationFactory.start(MainApplication.class);
        ProductService productService = (ProductService) ApplicationFactory.getBean(ProductService.class);
        SearchService searchService = (SearchService) ApplicationFactory.getBean(SearchService.class);

        Product product1 = new Product();
        product1.setName("iPhone 14");
        productService.addProduct(product1);

        Product product2 = new Product();
        product2.setName("iPhone 16");
        productService.addProduct(product2);

        List<Product> productList = searchService.search("iphone");
        for(Product product : productList) {
            System.out.println("product = " + product);
        }

    }
}


