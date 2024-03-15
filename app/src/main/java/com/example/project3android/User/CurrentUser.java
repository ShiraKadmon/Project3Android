package com.example.project3android.User;

public class CurrentUser {

    private final static CurrentUser INSTANCE = new CurrentUser();
    private User currentUser;
    private String jwtToken;
<<<<<<< HEAD
=======
    private String id;
>>>>>>> 0381a4e1e19d3bc6963c28a000b00ae8f105a84e

    public static CurrentUser getInstance() {
        return INSTANCE;
    }

    private CurrentUser() { }

    public String getJwtToken() {
        return jwtToken;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public void setCurrentUser(String firstName, String lastName, String username, String password, String profileImage) {
        this.currentUser = new User(firstName, lastName, username, password, profileImage);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
<<<<<<< HEAD
=======

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
>>>>>>> 0381a4e1e19d3bc6963c28a000b00ae8f105a84e
}
