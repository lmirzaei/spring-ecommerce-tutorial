package ecommerce.tutorial.controllers;

import com.mongodb.MongoClient;
import com.mongodb.client.result.UpdateResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import ecommerce.tutorial.jpa.entities.CategoryEntity;
import ecommerce.tutorial.jpa.entities.ProductEntity;
import ecommerce.tutorial.jpa.entities.SellerEntity;
import ecommerce.tutorial.jpa.repositories.CategoryJpaRepository;
import ecommerce.tutorial.jpa.repositories.ProductJpaRepository;
import ecommerce.tutorial.jpa.repositories.SellerJpaRepository;
import ecommerce.tutorial.mongodb.models.Category;
import ecommerce.tutorial.mongodb.models.EmbeddedCategory;
import ecommerce.tutorial.mongodb.models.Product;
import ecommerce.tutorial.mongodb.repositories.CategoryRepository;
import ecommerce.tutorial.mongodb.repositories.ProductRepository;
import ecommerce.tutorial.mongodb.repositories.SellerRepository;

@RestController
@RequestMapping(path = "/product")
public class ProductService
{
    private MongoOperations mongoOperations = new MongoTemplate(new MongoClient(), "local");
    @Autowired
    private ProductRepository _productMongoRepository;
    @Autowired
    private SellerRepository _sellerMongoRepository;
    @Autowired
    private CategoryRepository _categoryMongoRepository;
    @Autowired
    private ProductJpaRepository _productJpaRepository;
    @Autowired
    private SellerJpaRepository _sellerJpaRepository;
    @Autowired
    private CategoryJpaRepository _categoryJpaRepository;


    //----------Retrieve Products----------------
    @GetMapping(path = "/mongo")
    public ResponseEntity<Product> getProductFromMongoDB(@RequestParam(value = "name") String name)
    {
        Product productMongo = _productMongoRepository.findByName(name);
        if (productMongo != null)
        {
            return new ResponseEntity<>(productMongo, HttpStatus.OK);
        }
        System.out.println("There isn't any Product in Mongodb database with name: " + name);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/mysql")
    public ResponseEntity<ProductEntity> getProductFromMysql(@RequestParam(value = "name") String name)
    {
        ProductEntity product = _productJpaRepository.findByName(name);
        if (product != null)
        {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        System.out.println("There isn't any Product in MySQL database with name: " + name);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/all/mongo")
    public List<Product> getAllProductsFromMongoDB()
    {
        return _productMongoRepository.findAll();
    }

    @GetMapping(path = "/all/mysql")
    public List<ProductEntity> getAllProductsFromMysql()
    {
        return _productJpaRepository.findAll();
    }


    //----------Create a Product-----------------
    @PostMapping(path = "/mysql")
    public Object addNewProductInMysql(@RequestBody ProductEntity product)
    {
        if (product.getName() == null || product.getName().trim().isEmpty())
        {
            return HttpStatus.BAD_REQUEST;
        }

        SellerEntity seller;
        try
        {
            seller = _sellerJpaRepository.getOne(product.getSeller().getId());
        }
        catch (EntityNotFoundException e)
        {
            return HttpStatus.BAD_REQUEST;
        }

        HashSet<CategoryEntity> categories = new HashSet<>();
        try
        {
            for (CategoryEntity categoryEntity : product.getFallIntoCategories())
            {
                categories.add(_categoryJpaRepository.getOne(categoryEntity.getId()));
            }
        }
        catch (EntityNotFoundException e)
        {
            return HttpStatus.BAD_REQUEST;
        }

        if (seller != null && !categories.isEmpty())
        {
            ProductEntity createdProductEntity = new ProductEntity(product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getImages(),
                    seller,
                    categories);
            createdProductEntity = _productJpaRepository.save(createdProductEntity);
            System.out.println("A new Product created in MySQL database with id: " + createdProductEntity.getId() + "  and name: " + createdProductEntity.getName());
            return createdProductEntity;
        }
        else
        {
            return HttpStatus.BAD_REQUEST;
        }
    }


    //----------Update a Product-----------------
    @PutMapping(path = "/mongo")
    public ResponseEntity<String> updateProductInMongoDB(@Valid @RequestBody Product product)
    {
        Product productInDatabase = _productMongoRepository.findById(product.getId()).orElse(null);
        if (productInDatabase == null)
        {
            return new ResponseEntity<>("This seller doesn't exists in MongoDB.", HttpStatus.NOT_FOUND);
        }

        //Update the product by setting each property of this product in a update query.
        Update update = new Update();
        update.set("name", product.getName());
        update.set("description", product.getDescription());
        update.set("price", product.getPrice());
        update.set("image_URLs", product.getImage_URLs());
        HashSet<Category> categories = new HashSet<>();
        try
        {
            for (EmbeddedCategory category : product.getFallIntoCategories())
            {
                Category categoryNew = _categoryMongoRepository.findById(category.getId()).orElseThrow(EntityNotFoundException::new);
                categories.add(categoryNew);
            }
            update.set("fallIntoCategories", categories);
            Query query = new Query(Criteria.where("_id").is(product.getId()));
            UpdateResult updateResult = mongoOperations.updateFirst(query, update, Product.class);
            if (updateResult.getModifiedCount() == 1)
            {
                productInDatabase = _productMongoRepository.findByName(product.getName());
                return new ResponseEntity<>("The product updated", HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>("One of the categories which the product falls into, doesn't exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/mysql")
    public ResponseEntity<String> updateProductInMysql(@Valid @RequestBody ProductEntity product)
    {
        ProductEntity productEntity;
        SellerEntity sellerEntity;
        try
        {
            productEntity = _productJpaRepository.getOne(product.getId());
            System.out.println("The product " + productEntity.getName() + " with id " + productEntity.getId() + " is updating...");
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>("This product does not exists in MySQL database.", HttpStatus.NOT_FOUND);
        }
        try
        {
            sellerEntity = _sellerJpaRepository.getOne(product.getSeller().getId());
            System.out.println("The seller of this product is: " + sellerEntity.toString());
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>("The seller does not exists", HttpStatus.NOT_FOUND);
        }
            HashSet<CategoryEntity> categories = new HashSet<>();
            for (CategoryEntity categoryEntity : product.getFallIntoCategories())
            {
                _categoryJpaRepository.findById(categoryEntity.getId()).ifPresent(categories::add);
            }
            if (!categories.isEmpty())
            {
                productEntity.setName(product.getName());
                productEntity.setDescription(product.getDescription());
                productEntity.setPrice(product.getPrice());
                productEntity.setImages(product.getImages());
                productEntity.setSeller(sellerEntity);
                productEntity.setFallIntoCategories(categories);
                _productJpaRepository.save(productEntity);
                return new ResponseEntity<>("The product updated", HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>("The product must belongs to at least one category!", HttpStatus.BAD_REQUEST);
            }
    }
}
