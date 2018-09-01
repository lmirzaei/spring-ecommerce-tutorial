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
import ecommerce.tutorial.mongodb.models.Category;
import ecommerce.tutorial.mongodb.models.Product;
import ecommerce.tutorial.mongodb.repositories.CategoryRepository;

@RestController
@RequestMapping(path = "/category")
public class CategoryService
{
    private MongoOperations mongoOperation = new MongoTemplate(new MongoClient(), "local");
    @Autowired
    private CategoryRepository _categoryMongoRepository;
    @Autowired
    private CategoryJpaRepository _categoryJpaRepository;


    //----------Retrieve (a) Categories---------------
    @GetMapping(path = "/mongo")
    public ResponseEntity<Category> getCategoryFromMongoDB(@RequestParam(value = "name") String name)
    {
        Category categoryMongo = _categoryMongoRepository.findByName(name);
        if (categoryMongo != null)
        {
            return new ResponseEntity<Category>(categoryMongo, HttpStatus.OK);
        }
        System.out.println("There isn't any Category in Mongodb database with name: " + name);
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


    //----------Create a Category---------------
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


    //----------Update a Category---------------
    @PutMapping(path = "/mongo")
    public ResponseEntity<?> updateCategoryInMongoDB(@Valid @RequestBody Category category)
    {
        if (category == null || category.getId() == null || category.getName() == null || category.getName().trim().isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Category categoryInDatabase = _categoryMongoRepository.findById(category.getId()).orElse(null);
        if (categoryInDatabase == null)
        {
            return new ResponseEntity<>("This category doesn't exists in MongoDB.", HttpStatus.NOT_FOUND);
        }

        //Update the name of the category in MongoDB Database using mongoOperation.updateFirst
        Update updateCat = new Update();
        updateCat.set("name", category.getName());
        Query queryCat = new Query(Criteria.where("_id").is(category.getId()));
        UpdateResult updateResult = mongoOperation.updateFirst(queryCat, updateCat, Category.class);
        if (updateResult.getModifiedCount() == 1)
        {
            //After updating a category, all of the products which are in this category must be updated manually. The mapping framework doesn't support cascade update!
            Query where = new Query();
            where.addCriteria(Criteria.where("fallIntoCategories._id").is(categoryInDatabase.getId()));
            Update update = new Update().set("fallIntoCategories.$.name", category.getName());
            updateResult = mongoOperation.updateMulti(where, update, Product.class);
            return new ResponseEntity<>(_categoryMongoRepository.findById(category.getId()).get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
