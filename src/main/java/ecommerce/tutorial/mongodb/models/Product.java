package ecommerce.tutorial.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Product
{

    @Id
    private long id;

    private String name;

    private String description;

    private List<String> image_URLs;
}
