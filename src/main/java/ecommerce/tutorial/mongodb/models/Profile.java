package ecommerce.tutorial.mongodb.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import ecommerce.tutorial.enums.Gender;


public class Profile
{

    private String firstName;

    private String lastName;

    private String website;

    //@Temporal(value = TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthday;

    private String address;

    private String emailAddress;

    @Enumerated(EnumType.STRING)
    private Gender gender;


}
