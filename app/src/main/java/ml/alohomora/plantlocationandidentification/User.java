package ml.alohomora.plantlocationandidentification;

import java.io.Serializable;

/**
 * Created by Ankush on 3/29/2017.
 */

public class User implements Serializable{

    int score;
    int level;
    String email;
    String password;
    public User(int score, int level, String email, String password) {
        this.score = score;
        this.level = level;
        this.email = email;
        this.password = password;
    }

    public User() {
        score = level = 0;
        email = password = null;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
}
