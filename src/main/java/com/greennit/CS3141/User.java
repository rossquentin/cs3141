package com.greennit.CS3141;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
/**
 * This user class allows one to create posts, edit or delete existing posts, as well as create a new subgreenit.
 * The user can also logout, change password, or delete their account with this class
 */
public class User{
    @Id
    @Column(name = "username")
    private String username;
    private int permission;
    private String email;
    private String pass;
    private int karma;

    public User(){
    }

    public User(String username, String pass, String email){
        this.username = username;
        this.pass = pass;
        this.email = email;
        karma = 0;
        permission = 1;
    }

    public void createPost(){
        Post post = new Post();
        post.setAuthor(username);
        post.setLikes(0);
        post.setContent("placeholder");
        post.setCreation_date(new Timestamp(System.currentTimeMillis()));
        post.setHost_thread(-1);
    }

    public void editPost(Post post){
        post.setCreation_date(new Timestamp(System.currentTimeMillis()));
        post.setContent("placeholder");
    }

    public void deletePost(Post post) {
        post.setContent(null);
        post.setCreation_date(null);
        post.setHost_thread(-1);
        post.setAuthor(null);
        post.setLikes(-1);
    }

    public void createSubgreenit(String name){
        Subgreennit sub = new Subgreennit();
        sub.setAuthor(username);
        sub.setContent("placeholder");
        sub.setCreation_date(new Timestamp(System.currentTimeMillis()));
        sub.setTitle(name);
    }

    /**
     * Changes the users password
     * @param password - the new password they want to change it to
     * @return - Simple string saying it is complete
     */
    public String changePassword(String password){
        pass = password;
        //database.query("update users set password=" + password + " where username=" + username + ";", false, null);
        return "Password Change Complete";
    }

    /**
     * Performs a query to delete a user from the database
     */
    public void deleteAccount(){
        logout();
        //database.query("delete from users where username=" + username + ";", false, null);
    }

    public void logout(){

    }


    /*
    Getters and Setters for global variables
     */
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
    public void setPermission(int permission){
        this.permission = permission;
    }
    public int getPermission(){
        return permission;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    public void setPass(String pass){
        this.pass = pass;
    }
    public String getPass(){
        return pass;
    }
    public void setKarma(int karma){
        this.karma = karma;
    }
    public int getKarma(){
        return karma;
    }


}
