package com.bbsitter.bbsitter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
/*import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;*/

public class Login extends AppCompatActivity {


    private String email = "";
    private String password = "";
    private static final int RC_SIGN_IN = 9001;

    private TextInputLayout editTextEmail, editTextPassword;

    private Button btnLogin;

    private SignInButton btnGoogle;

    private TextView etCrearCuenta, etCambiarPass;

    private FirebaseAuth mAuth;
    //private DatabaseReference mDatabase;
    private FirebaseFirestore bbdd;

    private GoogleSignInClient mGoogleSingInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextPassword = findViewById(R.id.password_text_input);
        editTextEmail = findViewById(R.id.email_edit_text);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnGoogle = (SignInButton) findViewById(R.id.sign_in_button);
        btnGoogle.setSize(SignInButton.SIZE_STANDARD);
        etCrearCuenta = (TextView) findViewById(R.id.etCrearCuenta);
        etCambiarPass = (TextView) findViewById(R.id.etCambiarPass);


        mAuth = FirebaseAuth.getInstance();
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        bbdd = FirebaseFirestore.getInstance();



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = editTextEmail.getEditText().getText().toString().trim();
                password = editTextPassword.getEditText().getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {


                    logearUsuario();

                } else {
                    validarEmail();
                    validarPassword();
                    //Toast.makeText(Login.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                }

            }
        });

        loginGoogle();

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();
            }
        });


        etCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent crearUsuario = new Intent(getApplicationContext(), RegistroUsuarioActivity.class);
                startActivity(crearUsuario);

            }

        });

        etCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cambiarPass = new Intent(getApplicationContext(), CambiarPassActivity.class);
                startActivity(cambiarPass);
            }
        });


    }

    /*Comprobar que el usuario esta conectado al iniciar app*/
    /*@Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/

    @Override
    protected void onRestart() {
        super.onRestart();
        editTextEmail.setError(null);
        editTextPassword.setError(null);

    }

    private boolean validarEmail() {

        email = editTextEmail.getEditText().getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Debes rellenar el campo");
            return false;
        } else {
            editTextEmail.setError(null);
            return true;
        }

    }

    private boolean validarPassword() {

        password = editTextPassword.getEditText().getText().toString().trim();

        if (password.isEmpty()) {
            editTextPassword.setError("Debes rellenar el campo");
            return false;
        } else {
            editTextEmail.setError(null);
            return true;
        }

    }

    private void logearUsuario(/*String idToken*/) {


        //comprobamos que el email y la contraseña estan en la base de datos
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //Si la tarea es satisfactoria

                            //Metemos en la app al usuario
                            FirebaseUser user = mAuth.getCurrentUser();


                            /********** REALTIME DATABASE ********/

                            /*mDatabase.child("Usuarios").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Boolean perfil = (Boolean) snapshot.child("perfil").getValue();

                                        if (perfil == true) {
                                            //Aqui abrimos la actividad principal
                                            Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(main);
                                        } else {

                                            MaterialAlertDialogBuilder builder =
                                                    new MaterialAlertDialogBuilder(Login.this);

                                            builder.setTitle("Crear Perfil");
                                            builder.setMessage("Antes de entrar necesitas crear tu perfil!");
                                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //Aqui abrimos la actividad perfil
                                                    Intent crearPerfil = new Intent(getApplicationContext(), ElegirquePerfilCrear.class);
                                                    startActivity(crearPerfil);
                                                }
                                            });
                                            builder.show();

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });*/


                        } else {
                            //Si no existe ese usuario en la base de datos no inicia sesion
                            Toast.makeText(Login.this, "El usuario no existe.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    /*Inicia los elementos necesarios para poder iniciar sesión con Google.*/
    private void loginGoogle()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSingInClient = GoogleSignIn.getClient(this,gso);
    }

    /*Inicio de sesión con Google.*/
    private void signIn()
    {
        Intent googleIntent = mGoogleSingInClient.getSignInIntent();
        startActivityForResult(googleIntent, RC_SIGN_IN);
    }

    /*Se ejecuta cuando termina la actividad iniciada (Ventana de Google Sing In)*/
    /*FALTA IMPLEMENTAR*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                //logearUsuario(account.getIdToken());

            } catch (ApiException e) {

            }
        }
    }



}
