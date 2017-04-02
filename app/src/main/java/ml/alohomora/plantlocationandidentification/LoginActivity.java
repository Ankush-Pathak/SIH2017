package ml.alohomora.plantlocationandidentification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class LoginActivity extends AppCompatActivity {

    EditText emaillogin, passwordlogin;
    Button loginSubmit, tosignupactivity;
    ArrayList<User> userlist;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean loginFlag;
    User user;
    long session;

    //calligraphy additions
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getApplicationContext().getSharedPreferences("logednUser", MODE_PRIVATE);
        loginFlag = sharedPreferences.getBoolean("loginFlag",false);




        session=sharedPreferences.getLong("session",0);
        emaillogin = (EditText) findViewById(R.id.editTextLoginEnterEmail);
        passwordlogin = (EditText) findViewById(R.id.editTextLoginEnterPassword);
        loginSubmit = (Button) findViewById(R.id.buttonLoginSubmit);
        tosignupactivity = (Button) findViewById(R.id.buttonLoginMoveToSignUpActivity);
        FirebaseDatabase fb;

        DatabaseReference dr;

        fb = FirebaseDatabase.getInstance();
        dr = fb.getReference().child("user");
        userlist = new ArrayList<>();
        //firebase

        if(loginFlag && System.currentTimeMillis()-session<172800000 )
        {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this,"We have directly logged you in.",Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(System.currentTimeMillis()-session>172800000&& session!=0)
        {
            Toast.makeText(LoginActivity.this,"session expired ,log in again",Toast.LENGTH_SHORT).show();
        }
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    userlist.add(d.getValue(User.class));
                    Log.d("user",d.child("privilege").toString());
                }
                Log.d("Userlist size",userlist.size() + "");

                //login button
                loginSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String email = emaillogin.getText().toString();
                        final String password = passwordlogin.getText().toString();
                        //User user = new User();
                        loginFlag = false;


                        // get editor to edit in file
                        editor = sharedPreferences.edit();

                        //to check validity of email address
                        Pattern pattern1 = Pattern.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");
                        Matcher matcher1 = pattern1.matcher(email);

                        if (!matcher1.matches()) {
                            //show your message if not matches with email pattern
                            Toast.makeText(getApplicationContext(), "enter valid email", Toast.LENGTH_SHORT).show();
                        } else if (password.length() < 6)
                        {
                            Toast.makeText(getApplicationContext(), "minimum 6 characters for password", Toast.LENGTH_SHORT).show();
                        } else {
                            for (User u : userlist) {
                                if (u.getEmail().toLowerCase().equals(email.toLowerCase()) && u.getPassword().toLowerCase().equals(password.toLowerCase())) {
                                    loginFlag = true;
                                    user = u;
                                    break;
                                    }
                                }





                        }
                        if (loginFlag == true) {
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putString("level",String.valueOf(user.getLevel()) );
                            editor.putString("score", String.valueOf(user.getScore()));
                            editor.putBoolean("loginFlag",loginFlag);
                            editor.putLong("session",System.currentTimeMillis());
                            editor.putBoolean("privilege",user.privilege);

                            editor.commit();
                            //next activity
                            Log.d("user",user.getPrivilege()+"");
                            Log.d("email",user.getEmail() + "");
                            Log.d("password",user.getPassword() +"");
                            Log.d("level" ,user.getLevel() + "");
                            Log.d("score",user.getScore() + "");
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(loginFlag==false)
                        {

                            Toast.makeText(getApplicationContext(), "login unsuccessful wrong password/signup first", Toast.LENGTH_SHORT).show();

                        }
                    }


                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        tosignupactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }


}
