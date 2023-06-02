package com.cdp.ecodoctapp.service;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cdp.ecodoctapp.MainActivity;
import com.cdp.ecodoctapp.RegisterActivity;
import com.cdp.ecodoctapp.entity.Message;
import com.cdp.ecodoctapp.entity.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dref = db.getReference(UserEntity.class.getSimpleName());

    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public Message create (String name, String lastname, String email, String password){

        // se valida que los datos no sean nulos
        if (validData(name,lastname,email, password)){
            return new Message("Complete todos los datos son obligatorios");
        }
        // guarda al usuario en la base de datas

        try {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();

                    dref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserEntity user = new UserEntity(id,email,name,lastname);
                            dref.push().setValue(user);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            try {
                                throw new Exception("No se guardo los datos correctamente");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });


            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                      throw new RuntimeException(e);

            }
        });
        } catch (Exception e){
            return new Message("No se guardo los datos correctamente");
        }

        return new Message("Se creo el usuario exitosamente");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private boolean validData (String name, String lastname, String email, String password){
        return name.isEmpty()|| lastname.isEmpty()||email.isEmpty()||password.isEmpty();
    }
}
