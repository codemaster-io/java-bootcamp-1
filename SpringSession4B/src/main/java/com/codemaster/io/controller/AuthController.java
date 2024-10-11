package com.codemaster.io.controller;

import com.codemaster.io.litespring.MethodType;
import com.codemaster.io.litespring.annotation.Autowired;
import com.codemaster.io.litespring.annotation.RequestBody;
import com.codemaster.io.litespring.annotation.RequestMapping;
import com.codemaster.io.models.Product;
import com.codemaster.io.models.dto.AddProductRequest;
import com.codemaster.io.models.dto.AddProductResponse;
import com.codemaster.io.service.ProductService;

public class AuthController {
    @Autowired
    private ProductService productService;

    @RequestMapping(url = "/api/products", type = MethodType.POST)
    public AddProductResponse addProduct(@RequestBody AddProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());

        String id = productService.addProduct(product);

        AddProductResponse addProductResponse = new AddProductResponse();
        addProductResponse.setId(id);

        return addProductResponse;
    }
}
