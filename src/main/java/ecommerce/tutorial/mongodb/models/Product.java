package ecommerce.tutorial.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "products")
@TypeAlias("Product")
public class Product
{
    @Id
    private String id;

    private String name;

    private String description;

    private float price;

    private List<String> image_URLs = new ArrayList<>();

    @DBRef
    private Seller seller;

    private Set<Category> fallIntoCategories = new HashSet<>();

    public Product()
    {
    }

    public Product(String name, String description, float price, Seller seller, HashSet<Category> fallIntoCategories)
    {
        this.name = name;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.fallIntoCategories = fallIntoCategories;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public List<String> getImage_URLs()
    {
        return image_URLs;
    }

    public void setImage_URLs(List<String> image_URLs)
    {
        this.image_URLs = image_URLs;
    }

    public Seller getSeller()
    {
        return seller;
    }

    public void setSeller(Seller seller)
    {
        this.seller = seller;
    }

    public Set<Category> getFallIntoCategories()
    {
        return fallIntoCategories;
    }

    public void setFallIntoCategories(HashSet<Category> fallIntoCategories)
    {
        this.fallIntoCategories = fallIntoCategories;
    }
}

