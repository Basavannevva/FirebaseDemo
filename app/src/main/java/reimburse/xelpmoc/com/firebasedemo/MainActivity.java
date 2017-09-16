package reimburse.xelpmoc.com.firebasedemo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Bind(R.id.editEmail)
    EditText editEmail;
    @Bind(R.id.editPAssword)
    EditText editPAssword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        //Firebase check the user is login or not
        getCurrentUSer();
        getUserInformation();
    }

    @OnClick(R.id.buttonSubmit)
    void SubmitDetails()
    {
        signInExistingUSer(editEmail.getText().toString(),editPAssword.getText().toString());
    }

    @OnClick(R.id.textSignUp)
    void SignUp()
    {
        createNewFirebaseUSer(editEmail.getText().toString(),editPAssword.getText().toString());
    }

    /**
     * Check the user is sign in or not
     */
    public void getCurrentUSer() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Toast.makeText(MainActivity.this, "USer IS Signid", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("FireBAseDemo", "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    public void createNewFirebaseUSer(String email,String password)
    {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("FireBAseDemo", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "User has been Created, please login",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void signInExistingUSer(String email,String password)
    {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("FireBaseDemo", "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {


                            Toast.makeText(MainActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Intent i=new Intent(MainActivity.this,FirebaseExample.class);
                            startActivity(i);
                        }
                    }
                });
    }

    public void getUserInformation()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String uid = user.getUid();
        }
    }

}
