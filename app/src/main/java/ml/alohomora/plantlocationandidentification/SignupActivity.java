package ml.alohomora.plantlocationandidentification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class SignupActivity extends AppCompatActivity {

    EditText signupemail, signuppassword;
    Button signup, toLoginActivity;
    FirebaseDatabase fb;
    DatabaseReference dr;
    FirebaseDatabase firebaseDatabaseSync;
    DatabaseReference databaseReferenceSync;
    CheckBox tocheckpriviledgeuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupemail = (EditText) findViewById(R.id.editTextsignupemail);
        signuppassword = (EditText) findViewById(R.id.editTextsignuppassword);
        signup = (Button) findViewById(R.id.buttonSignupSubmit);
        toLoginActivity = (Button) findViewById(R.id.buttonSignupMovetoLoginActivity);
        tocheckpriviledgeuser=(CheckBox)findViewById(R.id.checkBoxtocheckpriviledgeuser);
        fb = FirebaseDatabase.getInstance();

        Log.d("Firebase","Hopefully added to database");

        firebaseDatabaseSync = FirebaseDatabase.getInstance();
        databaseReferenceSync = firebaseDatabaseSync.getReference();
        databaseReferenceSync.keepSynced(true);

        //signup button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fb = FirebaseDatabase.getInstance();
                dr = fb.getReference().child("user");
                final String email = signupemail.getText().toString();
                final String password = signuppassword.getText().toString();
                Pattern pattern1 = Pattern.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

                Matcher matcher1 = pattern1.matcher(email);

                if (!matcher1.matches()) {
                    //show your message if not matches with email pattern


                    Toast.makeText(getApplicationContext(), "enter valid email", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6)

                {
                    Toast.makeText(getApplicationContext(), "minimum 6 characters for password", Toast.LENGTH_SHORT).show();
                }
                else
                {


                    final ArrayList<User> userlist = new ArrayList<>();
                    dr.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {

                                //userList.add(d.getValue(User.class))
                                userlist.add(d.getValue(User.class));
                            }
                            Boolean flag = false;
                            for (User u : userlist)
                            {
                                if (u.getEmail().toLowerCase().equals(email.toLowerCase()) )
                                {
                                    flag = true;
                                    break;
                                }



                            }
                            if (flag == true)
                            {
                                Toast.makeText(getApplicationContext(), "Already registerd please login", Toast.LENGTH_SHORT).show();


                            }
                            if(flag == false)
                            {

                                User user = new User(0,0,email,password,5,tocheckpriviledgeuser.isChecked(),new ArrayList());
                                dr.push().setValue(user);
                                Toast.makeText(getApplicationContext(), "sign in successful", Toast.LENGTH_SHORT).show();
                                //next activity
                                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }


                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("Database error",databaseError.toString());
                        }


                    });
                }
            }
        });

        toLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}