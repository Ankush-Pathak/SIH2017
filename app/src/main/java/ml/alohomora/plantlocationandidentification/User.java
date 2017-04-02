package ml.alohomora.plantlocationandidentification;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ankush on 3/29/2017.
 */

public class User implements Serializable{

    int score;
    int level;
    int maxscoreforlevel;
    String email;
    String password;
    Boolean isuserpriviledge;
    ArrayList contributto;

    public User(int score, int level, String email, String password, int maxscoreforlevel, Boolean isuserprivilegde,ArrayList contributedto ) {
        this.score = score;
        this.level = level;
        this.email = email;
        this.password = password;
        this.maxscoreforlevel=maxscoreforlevel;
       this.isuserpriviledge=isuserprivilegde;
        this.contributto=new ArrayList();
    }

    public User() {
        score = level = 0;
        maxscoreforlevel = 0;
        email = password = null;
        isuserpriviledge=false;
        contributto=new ArrayList();
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

    public int getmaxScore()
    {
        if(level > 0) { maxscoreforlevel = level * 10; }
        else          { maxscoreforlevel=5; }

        return maxscoreforlevel;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsuserpriviledge() {
        return isuserpriviledge;
    }

    public void setIsuserpriviledge(Boolean isuserpriviledge) {
        this.isuserpriviledge = isuserpriviledge;
    }

    public ArrayList getContributedto() {
        return contributto;
    }

    public void setContributedto(ArrayList contributedto) {
        this.contributto = contributedto;
    }
}
