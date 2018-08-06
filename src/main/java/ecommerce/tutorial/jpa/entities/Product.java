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
}
