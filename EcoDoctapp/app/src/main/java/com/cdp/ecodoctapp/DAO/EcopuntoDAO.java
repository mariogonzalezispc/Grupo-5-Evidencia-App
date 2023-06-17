package com.cdp.ecodoctapp.DAO;

import com.cdp.ecodoctapp.entity.Ecopunto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EcopuntoDAO {
    private DatabaseReference databaseReference;

    public EcopuntoDAO() {
        // Obtén la referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference("ecopuntos");
    }

    public void crearEcopunto(Ecopunto ecopunto) {
        // Genera un nuevo ID único para el ecopunto
        String ecopuntoId = databaseReference.push().getKey();

        // Guarda el ecopunto en la base de datos usando el ID generado
        databaseReference.child(ecopuntoId).setValue(ecopunto);
    }

    public void actualizarEcopunto(String ecopuntoId, Ecopunto ecopunto) {
        // Actualiza el ecopunto en la base de datos utilizando el ID proporcionado
        databaseReference.child(ecopuntoId).setValue(ecopunto);
    }

    public void eliminarEcopunto(String ecopuntoId) {
        // Elimina el ecopunto de la base de datos utilizando el ID proporcionado
        databaseReference.child(ecopuntoId).removeValue();
    }

    public DatabaseReference obtenerReferenciaEcopuntos() {
        // Devuelve la referencia a la lista de ecopuntos en la base de datos
        return databaseReference;
    }
}