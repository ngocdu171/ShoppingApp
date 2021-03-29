package din19sp.group10.shoppingapp;

public class Product {
    public String name, brand, description, numReview, price, rating, status;

    public Product() {

    }

    public Product(String name, String brand, String description, String numReview, String price, String rating, String status) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.numReview = numReview;
        this.price = price;
        this.rating = rating;
        this.status= status;
    }
}
