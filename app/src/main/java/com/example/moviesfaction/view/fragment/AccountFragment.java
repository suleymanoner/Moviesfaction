package com.example.moviesfaction.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.moviesfaction.R;
import com.example.moviesfaction.view.activity.FirstActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountFragment extends Fragment {

    ImageView accountTitleImage;
    TextView nameTitle;
    TextView surnameTitle;
    TextView emailTitle;
    TextView nameEmptyText;
    TextView surnameEmptyText;
    TextView emailEmptyText;
    TextView resetPassword;
    TextView signOut;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_account,container,false);

        accountTitleImage = v.findViewById(R.id.accountTitleImage);
        nameTitle = v.findViewById(R.id.nameTitleText2);
        surnameTitle = v.findViewById(R.id.surnameTitleText2);
        emailTitle = v.findViewById(R.id.emailTitleTXT);
        nameEmptyText = v.findViewById(R.id.nameEmptyTXT);
        surnameEmptyText = v.findViewById(R.id.surnameEmptyTXT);
        emailEmptyText = v.findViewById(R.id.emailEmptyTXT);
        resetPassword = v.findViewById(R.id.resetPasswordTitleTXT);
        signOut = v.findViewById(R.id.signOutTitleTXT);

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




        return v;
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
                Toast.makeText(getContext(), "There is no document here!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void resetPassword(){

        EditText resetMail = new EditText(getContext());
        resetMail.setText(emailEmptyText.getText().toString());
        AlertDialog.Builder resetPass = new AlertDialog.Builder(getContext());
        resetPass.setTitle("Reset Password");
        resetPass.setMessage("Enter your email.");
        resetPass.setView(resetMail);

        resetPass.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(resetMail.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please enter your email!", Toast.LENGTH_SHORT).show();
                } else{
                    String email = resetMail.getText().toString();
                    firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Reset link sent!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Link not sent : " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
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

        AlertDialog.Builder signOutAlert = new AlertDialog.Builder(getContext());
        signOutAlert.setTitle("Sign Out");
        signOutAlert.setMessage("Are you sure?");

        signOutAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getContext(), FirstActivity.class);
                startActivity(intent);
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
