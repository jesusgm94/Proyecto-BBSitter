package com.bbsitter.bbsitter.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bbsitter.bbsitter.Main.MainActivity;
import com.bbsitter.bbsitter.Main.MainActivityCanguro;
import com.bbsitter.bbsitter.Perfiles.PerfilFamiliaActivity;
import com.bbsitter.bbsitter.R;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {


    private static final String TAG = "datos";
    private String email = "";
    private String password = "";
    private String tipo;
    private static final int RC_SIGN_IN = 9001;

    private TextInputLayout editTextEmail, editTextPassword;

    private Button btnLogin;
    private SignInButton btnGoogle;
    private TextView etCrearCuenta, etCambiarPass;

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    private GoogleSignInClient mGoogleSingInClient;

    private final ProgressBarInicioSesion progressBarInicioSesion = new ProgressBarInicioSesion(Login.this);

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

                            String uid = mAuth.getCurrentUser().getUid();

                            bbdd.collection("usuarios")
                                    .whereEqualTo("uid", uid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Boolean perfil = ((Boolean) document.get("perfil"));
                                                    tipo = document.get("tipo").toString();

                                                    if (perfil == true) {

                                                        // Creamos PROGRESS BAR para que el usuario sepa que su perfil se está creando)
                                                        progressBarInicioSesion.StarProgressBar();
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                progressBarInicioSesion.finishProgressBar();

                                                                if(tipo.equals("familia"))
                                                                {
                                                                    //Aqui abrimos la actividad main
                                                                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                                                    startActivity(main);
                                                                    finish();
                                                                }
                                                                else if (tipo.equals("canguro"))
                                                                {
                                                                    Intent maincanguro = new Intent(getApplicationContext(), MainActivityCanguro.class);
                                                                    startActivity(maincanguro);
                                                                    finish();
                                                                }



                                                            }
                                                        }, 2000);



                                                    } else {

                                                        Intent crearPerfil = new Intent(getApplicationContext(), ElegirquePerfilCrear.class);
                                                        startActivity(crearPerfil);
                                                        finish();



                                                    }

                                                }
                                            } else {

                                                Toasty.error(Login.this, "Error" + getApplicationContext(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

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
