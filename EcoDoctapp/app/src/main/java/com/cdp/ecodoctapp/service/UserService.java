package com.cdp.ecodoctapp.service;

import android.util.Log;

import com.cdp.ecodoctapp.entity.Message;
import com.cdp.ecodoctapp.entity.UserEntity;
import com.cdp.ecodoctapp.repository.UserRepository;
import com.google.firebase.firestore.auth.User;

public class UserService {

    // Se crea serivcio para realizar control y logica de negocio de User
    private UserRepository userRepository = new UserRepository();

    public Message register(String name, String lastname, String email, String password,String password2){

        // se valida que los datos no sean nulos
        if (validData(name,lastname,email, password,password2)){
            return new Message("Complete todos los datos son obligatorios",false);
        }
        if (!password.equals(password2)){
            return new Message("Las contraseñas no coinciden",false);
        }

        Message message = new Message();
        // se llama al repository para generar user,
        try {
            userRepository.register(name, lastname, email, password);
            message.setMessage("Se creo el usuario exitosamente");
            message.setOK(true);
        } catch (Exception e){
            message.setMessage("No se guardo los datos correctamente");
            message.setOK(false);
        }

        return message;
    }

    private boolean validData (String name, String lastname, String email, String password,String password2){
        return name.isEmpty()|| lastname.isEmpty()||email.isEmpty()||password.isEmpty()||password2.isEmpty();
    }


    public  Message login (String mail,String password){

        Message message = new Message();
        if (userRepository.getCurrentUser()!= null){
            message = createMessage("Ya se encuentra logueado",false);

              return message;

        }

        try {
            userRepository.login(mail, password);
            message = createMessage("Ingreso con exito",true);
        } catch (Exception e){
            message = createMessage("Error al loguearse: "+ e,false);
            Log.d("Error Login",e.getMessage(),e);

        }

        return message;
    }

    public Message logout () {
        Message message = new Message();
        userRepository.logout();
        if (userRepository.getCurrentUser()!=null){
            message = createMessage("Error al desloguearse",false);
        }else {
            message = createMessage("Deslogueo exitosos", true);
        }
        return message;
    }

    private Message createMessage(String msg,boolean isOk){
        Message message = new Message();
        message.setMessage(msg);
        message.setOK(isOk);
        return message;
    }

    public boolean isLogged(){
        return userRepository.getCurrentUser()!=null && userRepository.getCurrentUser().getEmail()!=null;
    }

    public String getLoggedUser(){
        Log.d("USER",userRepository.getCurrentUser().getEmail().toString());

        userRepository.getCurrentUser().getDisplayName();
        return userRepository.getCurrentUser().getEmail();
    }

  public void getUsuario() throws InterruptedException {
      userRepository.traerUsuario();


  }

  public UserEntity getUser(){
   return userRepository.user[0];
  }

}
