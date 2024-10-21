package com.codemaster.io.controller;

import com.codemaster.io.models.Product;
import com.codemaster.io.models.dto.*;
import com.codemaster.io.service.ACLService;
import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;
    private SearchService searchService;

    @Autowired
    private ACLService aclService;

    public ProductController(ProductService productService, SearchService searchService) {
        this.productService = productService;
        this.searchService = searchService;
    }

    @GetMapping
    public AllProductsResponse allProducts() {
        List<Product> products = productService.getAllProducts();
        AllProductsResponse response = AllProductsResponse.builder()
                .products(products)
                .build();
        return response;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public AddProductResponse addProduct(@RequestBody AddProductRequest req, Principal principal) {
        System.out.println("principal = " + principal);

        Product product = Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .createdByUserEmail(principal.getName())
                .build();
        Product responseProduct = productService.addProduct(product);
        AddProductResponse response = AddProductResponse.builder()
                .product(responseProduct)
                .success(responseProduct != null)
                .build();
        System.out.println("responseProduct = " + responseProduct);
        return response;
    }

    @PutMapping()
    public UpdateProductResponse updateProduct(@RequestBody UpdateProductRequest request) {
        Product product = Product.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .build();
        Product updateProduct = productService.updateProduct(product);
        UpdateProductResponse response = UpdateProductResponse.builder()
                .product(updateProduct)
                .success(updateProduct != null)
                .build();
        return response;
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("@ACLService.hasPermitToDelete(#productId)")
    public DeleteResponse deleteProduct(@PathVariable long productId) {
        try {
            boolean success = productService.deleteProduct(productId);
            DeleteResponse response = DeleteResponse.builder()
                    .success(success)
                    .build();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{productId}")
    @PreAuthorize("isAuthenticated()")
    public Product getProduct(@PathVariable long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping("/search")
    public SearchResponse searchProducts(@RequestParam(required = true) String query) {
        System.out.println("query = " + query);
        List<Product> products = searchService.search(query);
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setProducts(products);
        return searchResponse;
    }

}
