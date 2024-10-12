package com.codemaster.io.controller;


import com.codemaster.io.litespring.annotation.*;
import com.codemaster.io.litespring.context.UserContext;
import com.codemaster.io.litespring.enums.MethodType;
import com.codemaster.io.models.Product;
import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.AddProductRequest;
import com.codemaster.io.models.dto.AddProductResponse;
import com.codemaster.io.service.ProductService;


@Component
@RestController
public class ProductController {
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

    @RequestMapping(url = "/api/products/{id}", type = MethodType.GET)
    public Product getProduct(@PathVariable("id") String id) {
        return productService.getProduct(id);
    }

}
