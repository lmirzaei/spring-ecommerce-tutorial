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
public class Seller
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String accountId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "profile")
    private Profile profile;
}
