package entity;

import java.util.HashMap;
import java.util.List;

public class User {
    private final String name;
    private final String password;
    private final String email;
    private List<Listing> listings;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String get_username() {
        return name;
    }

    public String get_email() { return  email; }

    public String get_password() { return password; }


    public void add_listing(Listing listing) {
        listings.add(listing);
    }

    public void delete_listing(Listing listing) {
        listings.remove(listing);
    }

    public List<Listing> get_listings() {
        return listings;
    }

    @Override
    public String toString() {
        return name;
    }
}
