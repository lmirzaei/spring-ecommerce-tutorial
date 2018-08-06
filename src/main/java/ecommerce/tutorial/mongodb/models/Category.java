package ecommerce.tutorial.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "categories")
@TypeAlias(value = "Category")
public class Category
{
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @DBRef(lazy = true)
    private List<Product> productsOfCategory;

    public Category()
    {
    }

    public Category(String name)
    {
        this.name = name;
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

    public List<Product> getProductsOfCategory()
    {
        return productsOfCategory;
    }

    public void setProductsOfCategory(List<Product> productsOfCategory)
    {
        this.productsOfCategory = productsOfCategory;
    }
}
