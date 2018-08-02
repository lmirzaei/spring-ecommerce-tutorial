package ecommerce.tutorial.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Seller
{
   @Id
    private long id;

   @Indexed (unique = true) //@Indexed annotation tells the mapping framework to call createIndex(…) on that property of your document, making searches faster.
   private String accountId;

   private Profile profile;

}
