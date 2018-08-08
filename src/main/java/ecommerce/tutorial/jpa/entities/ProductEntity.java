package ecommerce.tutorial.jpa.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class ProductEntity
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
    @Size(min = 1)
    @NotNull
    private List<String> images;

    @ManyToOne(targetEntity = SellerEntity.class)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    @NotNull
    private SellerEntity seller;


    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    @Size(min = 1)
    @NotNull
    private Set<CategoryEntity> fallIntoCategories;

    public ProductEntity()
    {
    }

    public ProductEntity(String name, String description, float price, List<String> images, SellerEntity seller, HashSet<CategoryEntity> fallIntoCategories)
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

    public SellerEntity getSeller()
    {
        return seller;
    }

    public void setSeller(SellerEntity seller)
    {
        this.seller = seller;
    }

    public Set<CategoryEntity> getFallIntoCategories()
    {
        return fallIntoCategories;
    }

    public void setFallIntoCategories(HashSet<CategoryEntity> fallIntoCategories)
    {
        this.fallIntoCategories = fallIntoCategories;
    }
}

