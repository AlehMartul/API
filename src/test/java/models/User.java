package models;

import aquality.selenium.browser.AqualityServices;

public class User {

    private String name;
    private String username;
    private String email;
    private String address;
    private String phone;
    private String website;
    private String company;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setAllFields(User user, String name, String username,
                             String email, String address, String phone, String website, String company) {
        AqualityServices.getLogger().info("Setting all fields");
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setAddress(address);
        user.setPhone(phone);
        user.setWebsite(website);
        user.setCompany(company);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode() + username.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        models.User user1 = (models.User) obj;
        return (name == user1.name || (name != null && name.equals(user1.getName())))
                && (username == user1.username || (username != null && username.equals(user1.getUsername()))) &&
                (email == user1.email || (email != null && email.equals(user1.getEmail()))) &&
                (address == user1.address || (address != null && address.equals(user1.getAddress()))) &&
                (phone == user1.phone || (phone != null && phone.equals(user1.getPhone()))) &&
                (website == user1.website || (website != null && website.equals(user1.getWebsite()))) &&
                (company == user1.company || (company != null && company.equals(user1.getCompany())));
    }
}


