package com.example.moviesfaction.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesfaction.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountActivity extends AppCompatActivity {

    ImageView accountTitleImage;
    TextView nameTitle;
    TextView surnameTitle;
    TextView emailTitle;
    TextView nameEmptyText;
    TextView surnameEmptyText;
    TextView emailEmptyText;
    TextView resetPassword;
    TextView signOut;
    ImageView listImage;
    ImageView searchImage;
    ImageView accountImage;
    ImageView homeImage;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        accountTitleImage = findViewById(R.id.accountTitleImage);
        nameTitle = findViewById(R.id.nameTitleText2);
        surnameTitle = findViewById(R.id.surnameTitleText2);
        emailTitle = findViewById(R.id.emailTitleTXT);
        nameEmptyText = findViewById(R.id.nameEmptyTXT);
        surnameEmptyText = findViewById(R.id.surnameEmptyTXT);
        emailEmptyText = findViewById(R.id.emailEmptyTXT);
        resetPassword = findViewById(R.id.resetPasswordTitleTXT);
        signOut = findViewById(R.id.signOutTitleTXT);
        listImage = findViewById(R.id.userListImageView2);
        searchImage = findViewById(R.id.searchImageView2);
        accountImage = findViewById(R.id.accountImageView2);
        homeImage = findViewById(R.id.homeListImageView2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        loadAccountDetails();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this,FeedActivity.class);
                startActivity(intent);
            }
        });

    }


    public void loadAccountDetails(){

        String userID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()){
                    String name = documentSnapshot.getString("name");
                    String surname = documentSnapshot.getString("surname");
                    String email = documentSnapshot.getString("email");
                    nameEmptyText.setText(name);
                    surnameEmptyText.setText(surname);
                    emailEmptyText.setText(email);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AccountActivity.this, "There is no document here!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void resetPassword(){

        EditText resetMail = new EditText(AccountActivity.this);
        AlertDialog.Builder resetPass = new AlertDialog.Builder(AccountActivity.this);
        resetPass.setTitle("Reset Password");
        resetPass.setMessage("Enter your email.");
        resetPass.setView(resetMail);

        resetPass.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(resetMail.getText().toString().isEmpty()){
                    Toast.makeText(AccountActivity.this, "Please enter your email!", Toast.LENGTH_SHORT).show();
                } else{
                    String email = resetMail.getText().toString();
                    firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AccountActivity.this, "Reset link sent!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AccountActivity.this, "Link not sent : " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        resetPass.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        resetPass.create().show();
    }

    public void signOut(){

        AlertDialog.Builder signOutAlert = new AlertDialog.Builder(AccountActivity.this);
        signOutAlert.setTitle("Sign Out");
        signOutAlert.setMessage("Are you sure?");

        signOutAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.signOut();
                Intent intent = new Intent(AccountActivity.this,FirstActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signOutAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        signOutAlert.create().show();
    }

}