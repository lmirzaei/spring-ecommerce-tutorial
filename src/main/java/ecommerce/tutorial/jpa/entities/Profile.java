package ecommerce.tutorial.jpa.entities;

import com.sun.istack.internal.NotNull;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ecommerce.tutorial.enums.Gender;

@Entity
public class Profile
{
    @Id
    private long id;

    @OneToOne
    @JoinColumn
    @MapsId
    private Seller seller;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String website;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    private String address;

    private String emailAddress;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
