package ecommerce.tutorial.jpa.entities;

import com.sun.istack.internal.NotNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sellers")
public class SellerEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String accountId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "seller")
    private ProfileEntity profile;

    public SellerEntity()
    {
    }

    public SellerEntity(String accountId)
    {
        this.accountId = accountId;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
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

    public ProfileEntity getProfile()
    {
        return profile;
    }

    public void setProfile(ProfileEntity profile)
    {
        this.profile = profile;
    }
}

