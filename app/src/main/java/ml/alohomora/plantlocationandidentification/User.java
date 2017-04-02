package ml.alohomora.plantlocationandidentification;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ankush on 3/29/2017.
 */

public class User implements Serializable {

    int score;
    int level;
    String email;
    String password;
    Boolean privilege;
    ArrayList<String> contributTo;

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

    public Boolean getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Boolean privilege) {
        this.privilege = privilege;
    }

    public ArrayList<String> getContributTo() {
        return contributTo;
    }

    public void setContributTo(ArrayList<String> contributTo) {
        this.contributTo = contributTo;
    }

    public User() {
        this.score = -1;
        this.level = -1;
        this.email = null;
        this.password = null;
        this.privilege = false;
        this.contributTo = new ArrayList<>();
    }

    public User(int score, int level, String email, String password, Boolean privilege, ArrayList<String> contributTo) {

        this.score = score;
        this.level = level;
        this.email = email;
        this.password = password;
        this.privilege = privilege;
        this.contributTo = contributTo;
    }
}