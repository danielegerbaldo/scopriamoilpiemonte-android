package com.example.guitaass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guitaass.sindaco.home.SindacoHome;
import com.example.guitaass.utente.home.UtenteHome;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registrati = findViewById(R.id.registrati);
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Registrazione.class);
                startActivity(intent);
            }
        });

        Button accedi = findViewById(R.id.accedi);
        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nome = findViewById(R.id.name);
                EditText password = findViewById(R.id.password);
                /*Log.d("Login Activity", "nome = " + nome.getText());
                Log.d("Login Activity", "nome = " + password.getText());
                Log.d("Login Activity", "nome.getText().equals(\"sindaco\") = " + nome.getText().equals("sindaco") );
                Log.d("Login Activity", "password.getText().equals(\"sindaco\") = " + password.getText().equals("sindaco"));*/
                if(nome.getText().toString().equals("sindaco") && password.getText().toString().equals("sindaco")){
                    Intent intent = new Intent(v.getContext(), SindacoHome.class);
                    intent.putExtra("Username", "sindaco");
                    intent.putExtra("Email", "sindaco@gmail.com");
                    startActivity(intent);
                }else if(nome.getText().toString().equals("utente") && password.getText().toString().equals("utente")){
                    Intent intent = new Intent(v.getContext(), UtenteHome.class);
                    intent.putExtra("Username", "utente");
                    intent.putExtra("Email", "utente@gmail.com");
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Utente sconosciuto!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}