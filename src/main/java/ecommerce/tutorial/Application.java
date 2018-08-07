package ecommerce.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import ecommerce.tutorial.enums.Gender;
import ecommerce.tutorial.jpa.entities.CategoryEntity;
import ecommerce.tutorial.jpa.entities.ProductEntity;
import ecommerce.tutorial.jpa.entities.ProfileEntity;
import ecommerce.tutorial.jpa.entities.SellerEntity;
import ecommerce.tutorial.jpa.repositories.CategoryJpaRepository;
import ecommerce.tutorial.jpa.repositories.ProductJpaRepository;
import ecommerce.tutorial.jpa.repositories.SellerJpaRepository;
import ecommerce.tutorial.mongodb.models.Category;
import ecommerce.tutorial.mongodb.models.Product;
import ecommerce.tutorial.mongodb.models.Profile;
import ecommerce.tutorial.mongodb.models.Seller;
import ecommerce.tutorial.mongodb.repositories.CategoryRepository;
import ecommerce.tutorial.mongodb.repositories.ProductRepository;
import ecommerce.tutorial.mongodb.repositories.SellerRepository;


@EnableJpaRepositories(basePackages = "ecommerce.tutorial.jpa.repositories")
@EnableMongoRepositories(basePackages = "ecommerce.tutorial.mongodb.repositories")
@SpringBootApplication
public class Application implements CommandLineRunner
{
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


    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception
    {
        _categoryJpaRepository.deleteAll();
        _productJpaRepository.deleteAll();
        _sellerJpaRepository.deleteAll();

        //--------------Create two sellers-----------------------------------------
        SellerEntity judySellerEntity = new SellerEntity("google account id for Judy is 3243523567678000000");
        ProfileEntity judyProfileEntity = new ProfileEntity(judySellerEntity, "Judy", "Adams", Gender.Female);
        judySellerEntity = _sellerJpaRepository.save(judySellerEntity);

        SellerEntity michaelSellerEntity = new SellerEntity("google account id for Michael is 9443129034679800011");
        ProfileEntity michaelProfileEntity = new ProfileEntity(michaelSellerEntity, "Michael", "Martin", Gender.Male);
        michaelSellerEntity = _sellerJpaRepository.save(michaelSellerEntity);


        //--------------Create 4 different categories and save them--------------------
        CategoryEntity artCategory = new CategoryEntity("Art");
        CategoryEntity wallDecorCategory = new CategoryEntity("Wall Decor");
        CategoryEntity babyCategoryEntity = new CategoryEntity("Baby");
        CategoryEntity toysCategoryEntity = new CategoryEntity("Toys");

        artCategory = _categoryJpaRepository.save(artCategory);
        wallDecorCategory = _categoryJpaRepository.save(wallDecorCategory);
        babyCategoryEntity = _categoryJpaRepository.save(babyCategoryEntity);
        toysCategoryEntity = _categoryJpaRepository.save(toysCategoryEntity);


        //--------------Create a product in wall decor and art categories--------------
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://images-na.ssl-images-amazon.com/images/I/81-%2BW4McA%2BL._SL1200_.jpg");
        imageUrls.add("https://images-na.ssl-images-amazon.com/images/I/811WCp%2BnIaL._SL1500_.jpg");
        ProductEntity pictureProductEntity = new ProductEntity("Framed Canvas Wall Art",
                "Canvas Prints describe Purple Trees, with High Definition Giclee Print On Thick High Quality Canvas",
                42.34f, imageUrls, michaelSellerEntity, new HashSet<>(Arrays.asList(artCategory, wallDecorCategory)));
        pictureProductEntity = _productJpaRepository.save(pictureProductEntity);


        imageUrls.clear();
        //--------------Create a product in toys and baby categories------------------
        imageUrls.add("https://images-na.ssl-images-amazon.com/images/I/81-Fd8LV-7L._SX522_.jpg");
        imageUrls.add("https://images-na.ssl-images-amazon.com/images/I/91zq0dI0tBL._SL1500_.jpg");
        ProductEntity dollProductEntity = new ProductEntity("Teddy Bear",
                "Ramon tan teddy with realistic paw pad accents and heart shaped tummy",
                24.25f, imageUrls, judySellerEntity, new HashSet<>(Arrays.asList(babyCategoryEntity, toysCategoryEntity)));
        dollProductEntity = _productJpaRepository.save(dollProductEntity);


        _categoryMongoRepository.deleteAll();
        _sellerMongoRepository.deleteAll();
        _productMongoReposirory.deleteAll();


        //--------------Create a seller-----------------------------------------------
        Profile leilaProfileMongo = new Profile("Leila", "Mongo Seller", Gender.Female);
        Seller leilaSellerMongo = new Seller("account id = 23456789", leilaProfileMongo);
        _sellerMongoRepository.save(leilaSellerMongo);


        //--------------Create three different categories------------------------------
        Category furnitureCategory = new Category("Furniture");
        Category handmadeCategory = new Category("Handmade");
        furnitureCategory = _categoryMongoRepository.save(furnitureCategory);
        handmadeCategory = _categoryMongoRepository.save(handmadeCategory);
        Category woodCategory = new Category();
        woodCategory.setName("Wood");
        woodCategory = _categoryMongoRepository.save(woodCategory);


        //--------------Create a product in three different categories------------------
        HashSet<Category> categoryList = new HashSet<>(Arrays.asList(furnitureCategory, handmadeCategory, woodCategory));
        Product leilaDeskProduct = new Product("A Wooden Desk", "This is a comfortable made by Leila!", 249.99f, leilaSellerMongo, categoryList);
        leilaDeskProduct = _productMongoReposirory.save(leilaDeskProduct);

        System.out.println("__________________________________________________________________");
        System.out.println("Test Mongo repository");
        System.out.println("Find a seller by first name");
        _sellerMongoRepository.findByFirstName("Leila").forEach(System.out::println);
        System.out.println("__________________________________________________________________");

    }

}
