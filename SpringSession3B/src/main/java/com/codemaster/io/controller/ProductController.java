package com.codemaster.io.controller;


import com.codemaster.io.litespring.annotation.*;
import com.codemaster.io.models.Product;
import com.codemaster.io.models.dto.AddProductRequest;
import com.codemaster.io.models.dto.AddProductResponse;
import com.codemaster.io.models.dto.SearchResponse;
import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.SearchService;

import java.util.List;

@Component
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private SearchService searchService;

    @PostMapping("/api/products")
    @ResponseBody
    public AddProductResponse addProduct(@RequestBody AddProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());

        String id = productService.addProduct(product);

        AddProductResponse addProductResponse = new AddProductResponse();
        addProductResponse.setId(id);

        return addProductResponse;
    }

    @GetMapping("/api/products/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable("id") String id) {
        return productService.getProduct(id);
    }

    @GetMapping("/api/products/{id}")
    @ResponseBody
    public SearchResponse search(@RequestParam("query") String query) {
        List<Product> productList = searchService.search(query);
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setProducts(productList);
        return searchResponse;
    }
}
