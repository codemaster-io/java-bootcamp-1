package io.codemaster.demo.design_patterns.builder;

public class Product {
    private String name;
    private String id;
    private String imageUrl;

    // Private constructor to restrict object creation through Builder only
    private Product(ProductBuilder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.imageUrl = builder.imageUrl;
    }

    public static class ProductBuilder {
        private String name;
        private String id;
        private String imageUrl;


        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ProductBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
