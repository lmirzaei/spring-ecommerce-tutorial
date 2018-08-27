package ecommerce.tutorial.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sellers")
@TypeAlias(value = "Seller")
public class Seller
{
    @Id
    private String id;

    @Indexed(unique = true)
    private String accountId;

    private Profile profile;

    public Seller()
    {
    }

    public Seller(String accountId, Profile profile)
    {
        this.accountId = accountId;
        this.profile = profile;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
    }

    @Override
    public String toString()
    {
        if (profile == null)
        {
            return super.toString();
        }
        else
        {
            return profile.getFirstName() + " " + profile.getLastName();
        }
    }
}
