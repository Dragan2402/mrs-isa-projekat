package com.projekat.projekat_mrs_isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Where(clause = "deleted = false")
public abstract class User implements UserDetails {
    @Id
    @SequenceGenerator(name = "usersSeqGen",sequenceName = "usersSeq",initialValue = 1,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSeqGen")
    private Long id;

    @Column(name = "username",unique = true,nullable = false)
    private String username;

    @Column(name="email",unique = true,nullable = false)
    private String email;

    @JsonIgnore
    @Column(name="password",nullable = false)
    private String password;

    @Column(name="picture",nullable = false)
    private String picture;

    @Column(name="first_name",nullable = false)
    private String firstName;

    @Column(name="last_name",nullable = false)
    private String lastName;

    @Column(name="address",nullable = false)
    private String address;

    @Column(name="city",nullable = false)
    private String city;

    @Column(name="country",nullable = false)
    private String country;

    @Column(name="phone_num",nullable = false)
    private String phoneNum;

    @Column(name="rating",nullable = false)
    private Double rating;

    @Column(name = "reviews_number",nullable = false)
    private Integer reviewsNumber;

    @Column(name = "type",nullable = false)
    private AccountType type;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "rentingOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Review> reviews= new HashSet<>();

    @OneToMany(mappedBy = "submitter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Request> requests= new HashSet<>();

    @OneToMany(mappedBy = "complainant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Complaint> complaintsSent= new HashSet<>();

    @OneToMany(mappedBy = "respondent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Complaint> complaintsRecieved= new HashSet<>();

    @Column(name = "penalties", nullable = false)
    private Integer penalties;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public User() {}

    public User( String email,String username,String password, String picture, String firstName, String lastName, String address, String city,
                 String country, String phoneNum) {
        this.email = email;
        this.username=username;
        this.setPassword(password);
        this.password = password;
        this.picture = picture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.rating=0.0;
        this.reviewsNumber=0;
        this.penalties=0;
        this.phoneNum = phoneNum;
        this.verified=false;
        this.enabled=true;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addReview(Review review){
        reviews.add(review);
        double ratingSum= this.rating*this.reviewsNumber;
        this.reviewsNumber++;
        this.rating=(ratingSum+review.getRating()) / this.reviewsNumber;
        review.setRentingOwner(this);
    }

    public void removeReview(Review review){
        reviews.remove(review);
        review.setRentingOwner(null);
    }

    public void addRequest(Request request){
        requests.add(request);
        request.setSubmitter(this);
    }

    public void removeRequest(Request request){
        requests.remove(request);
        request.setSubmitter(null);
    }

    public void sendComplaint(Complaint complaint){
        complaintsSent.add(complaint);
        complaint.setComplainant(this);
    }

    public void removeSentComplaint(Complaint complaint){
        complaintsSent.remove(complaint);
        complaint.setComplainant(null);
    }

    public void recieveComplaint(Complaint complaint){
        complaintsRecieved.add(complaint);
        complaint.setRespondent(this);
    }

    public void removeRecievedComplaint(Complaint complaint){
        complaintsRecieved.remove(complaint);
        complaint.setRespondent(null);
    }

    public Integer getPenalties() {
        return penalties;
    }

    public void setPenalties(Integer penalties) {
        this.penalties = penalties;
    }

    public void addPenalty(){
        if(this.penalties<3){
            this.penalties++;
        }
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
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
                ", is verified"+verified+
                '}';
    }

    public void update(UserDTO userDTO)
    {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.address = userDTO.getAddress();
        this.city = userDTO.getCity();
        this.country = userDTO.getCountry();
        this.phoneNum = userDTO.getPhoneNum();
    }

}
