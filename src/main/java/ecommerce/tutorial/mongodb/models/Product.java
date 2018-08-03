package ecommerce.tutorial.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "products")
public class Product
{
    @Id
    private long id;

    private String name;

    private String description;

    private List<String> image_URLs;

    @DBRef
    private Seller seller;

    private List<Category> fallIntoCategories;

    public Product()
    {
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
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

    public List<Category> getFallIntoCategories()
    {
        return fallIntoCategories;
    }

    public void setFallIntoCategories(List<Category> fallIntoCategories)
    {
        this.fallIntoCategories = fallIntoCategories;
    }
}

