package com.bbsitter.bbsitter.Login;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
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
import com.bbsitter.bbsitter.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {



    private String email = "";
    private String password = "";
    private String tipo;


    private TextInputLayout editTextEmail, editTextPassword;

    private Button btnLogin;
    private SignInButton btnGoogle;
    private TextView etCrearCuenta, etCambiarPass;

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    private static final String TAG = "Login";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    private final ProgressBarInicioSesion progressBarInicioSesion = new ProgressBarInicioSesion(Login.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
                    validarCampos();
                }

            }
        });

        //Logueamos con Google. Inicia los elementos necesarios para poder iniciar sesión con Google.

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


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

    //Comprobamos los datos de login
    private boolean validarCampos() {

        Boolean validar = true;
        email = editTextEmail.getEditText().getText().toString().trim();
        password = editTextPassword.getEditText().getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Debes rellenar el usuario");
            validar = false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Debes rellenar la contraseña");
            validar = false;
        }


        if(!email.isEmpty()){
            editTextEmail.setError(null);
        }
        if(!password.isEmpty()){
            editTextEmail.setError(null);
        }

        return validar;
    }

    //Logueamos un usuario con Email y contraseña
    private void logearUsuario() {


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
                                                                }
                                                                else if (tipo.equals("canguro"))
                                                                {
                                                                    Intent maincanguro = new Intent(getApplicationContext(), MainActivityCanguro.class);
                                                                    startActivity(maincanguro);
                                                                }



                                                            }
                                                        }, 3000);



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
                            Toast.makeText(Login.this, "El usuario no existe o credenciales erróneas",
                                    Toast.LENGTH_SHORT).show();


                        }

                    }
                });

    }

    /*Inicio de sesión con Google.*/
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                updateUI(null);
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Se loguea", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {

                            updateUI(null);
                            Toast.makeText(Login.this, "Ni caso", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    /*private void handleSingInResult(Task<GoogleSignInAccount> completedTask)
    {

        try {

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Logín hecho", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e)
        {
            Toast.makeText(this, "Logín mal", Toast.LENGTH_SHORT).show();
        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount account)
    {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Pasa FirebaseGoogleAuth", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        }
                        else{
                            Toast.makeText(Login.this, "Ha fallado en FirebaseGoogleAuth", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/



    private void updateUI(FirebaseUser fUser)
    {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        /*GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account != null)
        {
            String nombre = account.getDisplayName();
            String apellido = account.getGivenName();
            String FamilyName = account.getFamilyName();
            String email = account.getEmail();
            String id = account.getId();
            Uri photo = account.getPhotoUrl();

            Toast.makeText(this, nombre+ " "+ apellido + FamilyName+ " "+ email + id+ " "+ photo , Toast.LENGTH_SHORT).show();
        }*/
    }

}
