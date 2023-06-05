package com.cdp.ecodoctapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cdp.ecodoctapp.entity.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executor;

public class UserRepository {

    // Se crea Repositorio para toda interacion con la base de datos.
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dref = db.getReference(UserEntity.class.getSimpleName());

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // autenticacion con firebase
    public void register (String name, String lastname, String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                createUSer(name, lastname, email, id);
            }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    throw new RuntimeException(e);

                }
            });

    }
// insert de user en bd
    private void createUSer (String name, String lastname, String email,String id){
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
                    Log.d("CREATE_USER", e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public FirebaseUser getCurrentUser (){
       FirebaseUser user = mAuth.getCurrentUser();
       if (user !=null) {
           Log.d("USER_CONTEXT", user.getEmail());
       }
        return user;

    }

    public void login (String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("LOGIN", "ERROR" + task.getResult().toString());
                           throw new RuntimeException("Error al loguearse");

                        }
                    }

                });
    }

    public void logout (){
        FirebaseAuth.getInstance().signOut();
        Log.d("LOGOUT", "¡se deslogeo!");
    }

}
