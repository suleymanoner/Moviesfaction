package com.example.moviesfaction.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesfaction.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    ImageView signupTitleImage;
    TextView nameTitleText;
    EditText nameEditText;
    TextView surnameTitleText;
    EditText surnameEditText;
    TextView emailTitleTXT;
    EditText emailEditTXT;
    TextView passwordTitleTXT;
    EditText passwordEditTXT;
    Button signupButton2;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupTitleImage = findViewById(R.id.signupTitleImage);
        nameTitleText = findViewById(R.id.nameTitleText);
        nameEditText = findViewById(R.id.nameEditText);
        surnameTitleText = findViewById(R.id.surnameTitleText);
        surnameEditText = findViewById(R.id.surnameEditText);
        emailTitleTXT = findViewById(R.id.emailTitleTXT);
        emailEditTXT = findViewById(R.id.emailEditTXT);
        passwordTitleTXT = findViewById(R.id.passwordTitleTXT);
        passwordEditTXT = findViewById(R.id.passwordEditTXT);
        signupButton2 = findViewById(R.id.signupButton2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = firebaseFirestore.getInstance();

    }

    public void signupClick2(View view){

        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String email = emailEditTXT.getText().toString().trim();
        String password = passwordEditTXT.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()){
            Toast.makeText(this, "Please fill in all the fields!", Toast.LENGTH_SHORT).show();
        }
        else{
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SignUpActivity.this, "User created!", Toast.LENGTH_LONG).show();
                    userID = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("name",name);
                    user.put("email",email);
                    user.put("surname",surname);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "User fields created! This is : " + userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "Failure! : " + e.toString());
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                    startActivity(intent);
                    finish();                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}