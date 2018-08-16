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

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import ecommerce.tutorial.jpa.entities.CategoryEntity;
import ecommerce.tutorial.jpa.repositories.CategoryJpaRepository;
import ecommerce.tutorial.jpa.repositories.ProductJpaRepository;
import ecommerce.tutorial.jpa.repositories.SellerJpaRepository;
import ecommerce.tutorial.mongodb.models.Category;
import ecommerce.tutorial.mongodb.models.Product;
import ecommerce.tutorial.mongodb.repositories.CategoryRepository;
import ecommerce.tutorial.mongodb.repositories.ProductRepository;
import ecommerce.tutorial.mongodb.repositories.SellerRepository;

@RestController
@RequestMapping(path = "/category")
public class CategoryService
{
    private MongoOperations mongoOperation = new MongoTemplate(new MongoClient(), "local");//TODO: How to know databaseName?

    @Autowired
    private CategoryRepository _categoryMongoRepository;
    @Autowired
    private ProductRepository _productMongoReposirory;
    @Autowired
    private SellerRepository _sellerMongoRepository;

    @Autowired
    private CategoryJpaRepository _categoryJpaRepository;
    @Autowired
    ProductJpaRepository _productJpaRepository;
    @Autowired
    SellerJpaRepository _sellerJpaRepository;

    //--------------------------------------Retrieve (a) Categories-----------------------------------------------------
    @GetMapping(path = "/mongo")
    public ResponseEntity<Category> getCategoryFromMongoDB(@RequestParam(value = "name") String name)
    {
        Category categoryMongo = _categoryMongoRepository.findByName(name);
        if (categoryMongo != null)
        {
            return new ResponseEntity<Category>(categoryMongo, HttpStatus.OK);
        }
        System.out.println("There isn't any Category in Mongo database with name: " + name);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/all/mongo")
    public List<Category> getAllCategoriesFromMongoDB()
    {
        return _categoryMongoRepository.findAll();
    }

    @GetMapping(path = "/mysql")
    public ResponseEntity<?> getCategoryFromMysql(@RequestParam(value = "name") String name)
    {
        List<CategoryEntity> categoryEntityList = _categoryJpaRepository.findAllByName(name);
        if (!categoryEntityList.isEmpty())
        {
            return new ResponseEntity<List<CategoryEntity>>(categoryEntityList, HttpStatus.OK);
        }
        System.out.println("There isn't any Category in MySQL database with name: " + name);

        return new ResponseEntity<String>(new StringBuilder("There isn't any Category in MySQL database with name: ").append(name).toString(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/all/mysql")
    public List<CategoryEntity> getAllCategoriesFromMysql()
    {
        return _categoryJpaRepository.findAll();
    }

    //--------------------------------------Create a Category-----------------------------------------------------------
    @PostMapping(path = "/mysql")
    public Object addNewCategoryInMysql(@RequestParam(value = "name") String name)
    {
        if (name == null || name.trim().isEmpty())
        {
            return HttpStatus.BAD_REQUEST;
        }
        CategoryEntity createdCategoryEntity = new CategoryEntity(name.trim());
        createdCategoryEntity = _categoryJpaRepository.save(createdCategoryEntity);
        System.out.println("A new Category created in MySQL database with id: " + createdCategoryEntity.getId() + "  and name: " + createdCategoryEntity.getName());
        return createdCategoryEntity;
    }


    @PostMapping(path = "/mongo")
    public ResponseEntity<Category> addNewCategoryInMongoDB(@Valid @RequestBody Category category)
    {
        if (category == null || category.getName() == null || category.getName().trim().isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Category createdCategory = _categoryMongoRepository.save(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.OK);
    }


    //--------------------------------------Update a Category-----------------------------------------------------------
    @PutMapping(path = "/mongo")
    public ResponseEntity<Category> updateCategoryInMongoDB(@Valid @RequestBody Category category)
    {
        if (category == null || category.getId() == null || category.getName() == null || category.getName().trim().isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Category categoryDatabase = _categoryMongoRepository.findById(category.getId()).orElse(null);
        if (categoryDatabase == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //Update the name of the category in Database using mongoOperation.updateFirst
        Update updateCat = new Update();
        updateCat.set("name", category.getName());
        Query queryCat = new Query(Criteria.where("_id").is(category.getId()));
        UpdateResult updateResult = mongoOperation.updateFirst(queryCat, updateCat, Category.class);


        //Update all of the products which are in this category.
        Query where = new Query();
        where.addCriteria(Criteria.where("fallIntoCategories._id").is(categoryDatabase.getId()));
        Update update = new Update().set("fallIntoCategories.$.name", category.getName());
        UpdateResult updateResultA = mongoOperation.updateMulti(where, update, Product.class);

        return new ResponseEntity<>(_categoryMongoRepository.findById(category.getId()).get(), HttpStatus.OK);
    }


    @PutMapping(path = "/mysql")
    public ResponseEntity<String> updateCategoryInMysql(@Valid @RequestBody CategoryEntity category)
    {
        if (category == null)
        {
            return new ResponseEntity<>("Your request is null!", HttpStatus.BAD_REQUEST);
        }
        try
        {
            CategoryEntity categoryEntity = _categoryJpaRepository.getOne(category.getId());
            categoryEntity.setName(category.getName());
            _categoryJpaRepository.save(categoryEntity);
            return new ResponseEntity<>("The category updated", HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>("This category does not exists", HttpStatus.NOT_FOUND);
        }
    }
}
