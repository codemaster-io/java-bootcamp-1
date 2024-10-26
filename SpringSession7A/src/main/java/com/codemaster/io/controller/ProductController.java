package com.codemaster.io.controller;

import com.codemaster.io.models.Product;
import com.codemaster.io.models.dto.*;
import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;
    private SearchService searchService;

    public ProductController(ProductService productService, SearchService searchService) {
        this.productService = productService;
        this.searchService = searchService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public AllProductsResponse allProducts() {
        System.out.println("All Products.");
        List<Product> products = productService.getAllProducts();
        AllProductsResponse response = AllProductsResponse.builder()
                .products(products)
                .build();
        return response;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public AddProductResponse addProduct(@RequestBody AddProductRequest req,
                                         Principal principal) {
        Product product = Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .addedByUserEmail(principal.getName())
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
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
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
        boolean success = productService.deleteProduct(productId);
        DeleteResponse response = DeleteResponse.builder()
                .success(success)
                .build();
        return response;
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public Product getProduct(@PathVariable long productId) {
        System.out.println("productId = " + productId);
        return productService.getProduct(productId);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public SearchResponse searchProducts(@RequestParam(required = true) String query) {
        System.out.println("query = " + query);
        List<Product> products = searchService.search(query);
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setProducts(products);
        return searchResponse;
    }

}
