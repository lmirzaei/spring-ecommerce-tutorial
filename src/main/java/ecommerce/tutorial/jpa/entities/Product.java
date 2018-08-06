package ecommerce.tutorial.jpa.entities;

import com.sun.istack.internal.NotNull;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    private String description;

    private float price;

    @ElementCollection
    @CollectionTable(name = "Product_Images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_URL")
    private List<String> images;

    @ManyToOne(targetEntity = Seller.class)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;


    @ManyToMany(mappedBy = "products")
    private List<Category> fallIntoCategories;

    public Product()
    {
    }

    public Product(String name, String description, float price, List<String> images, Seller seller, List<Category> fallIntoCategories)
    {
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
        this.seller = seller;
        this.fallIntoCategories = fallIntoCategories;
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

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public List<String> getImages()
    {
        return images;
    }

    public void setImages(List<String> images)
    {
        this.images = images;
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

