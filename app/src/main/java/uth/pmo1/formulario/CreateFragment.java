package uth.pmo1.formulario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateFragment extends DialogFragment {

    Button btnguardar, btncancelar;
    EditText nombre, apellido, edad;
    private FirebaseFirestore firestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        firestore = FirebaseFirestore.getInstance();

        btnguardar = view.findViewById(R.id.btnguardar);
        btncancelar = view.findViewById(R.id.btncancelar);
        nombre = view.findViewById(R.id.txtnombre);
        apellido = view.findViewById(R.id.txtapellido);
        edad = view.findViewById(R.id.txtedad);

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String alumnombre = nombre.getText().toString().trim();
                String alumapellido = apellido.getText().toString().trim();
                String alumedad = edad.getText().toString().trim();

                if (alumnombre.isEmpty() && alumapellido.isEmpty() && alumedad.isEmpty()){
                    Toast.makeText(getContext() ,"Ingrese los datos!", Toast.LENGTH_SHORT).show();
                }else{
                    postAlum(alumnombre,alumapellido,alumedad);
                }
            }
        });

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre.setText("");
                apellido.setText("");
                edad.setText("");
                nombre.requestFocus();
            }
        });

        return view;
    }

    private void postAlum(String alumnombre, String alumapellido, String alumedad) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", alumnombre);
        map.put("apellido", alumapellido);
        map.put("edad", alumedad);

        firestore.collection("alumno").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Creado exitosamente!", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al ingresar!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}