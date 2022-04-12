package com.projekat.projekat_mrs_isa.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @SequenceGenerator(name = "usersSeqGenV1",sequenceName = "usersSeqV1",initialValue = 1,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSeqGenV1")
    private Long id;

    @Column(name="email",unique = true,nullable = false)
    private String email;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="firstName",nullable = false)
    private String firstName;

    @Column(name="lastName",nullable = false)
    private String lastName;

    @Column(name="address",nullable = false)
    private String address;

    @Column(name="city",nullable = false)
    private String city;

    @Column(name="country",nullable = false)
    private String country;

    @Column(name="phoneNum",nullable = false)
    private String phoneNum;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public User() {

    }

    public User( String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNum) {

        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phoneNum = phoneNum;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
