package com.codemaster.io.controller;


import com.codemaster.io.litespring.annotation.*;
import com.codemaster.io.litespring.enums.MethodType;
import com.codemaster.io.models.Product;
import com.codemaster.io.models.dto.SearchResponse;
import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.SearchService;

import java.util.List;

@Component
@RestController
public class SearchController {
    @Autowired
    private ProductService productService;

    @Autowired
    private SearchService searchService;

    @RequestMapping(url = "/api/search", type = MethodType.GET)
    public SearchResponse search(@RequestParam("query") String query) {
        List<Product> productList = searchService.search(query);
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setProducts(productList);
        return searchResponse;
    }
}
