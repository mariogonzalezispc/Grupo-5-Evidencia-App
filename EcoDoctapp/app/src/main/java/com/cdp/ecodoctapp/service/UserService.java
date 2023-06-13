package com.cdp.ecodoctapp.service;

import android.util.Log;

import com.cdp.ecodoctapp.entity.Message;
import com.cdp.ecodoctapp.repository.UserRepository;

public class UserService {

    // Se crea serivcio para realizar control y logica de negocio de User
    private UserRepository userRepository = new UserRepository();

    public Message register(String name, String lastname, String email, String password){

        // se valida que los datos no sean nulos
        if (validData(name,lastname,email, password)){
            return new Message("Complete todos los datos son obligatorios");
        }
        Message message = new Message();
        // se llama al repository para generar user,
        try {
            userRepository.register(name, lastname, email, password);
            message.setMessage("Se creo el usuario exitosamente");
        } catch (Exception e){
            message.setMessage("No se guardo los datos correctamente");
        }

        return message;
    }

    private boolean validData (String name, String lastname, String email, String password){
        return name.isEmpty()|| lastname.isEmpty()||email.isEmpty()||password.isEmpty();
    }

    public Message login (String mail,String password){

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
}
