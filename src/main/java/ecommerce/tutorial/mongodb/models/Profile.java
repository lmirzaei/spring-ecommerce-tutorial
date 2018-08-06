package ecommerce.tutorial.mongodb.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import ecommerce.tutorial.enums.Gender;

public class Profile
{
    private String firstName;

    private String lastName;

    private String website;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthday;

    private String address;

    private String emailAddress;

    private Gender gender;

    public Profile()
    {
    }

    public Profile(String firstName, String lastName, Gender gender)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }
}
