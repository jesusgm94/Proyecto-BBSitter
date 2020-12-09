package com.bbsitter.bbsitter.OpcionesMenuCanguro.Perfil;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bbsitter.bbsitter.Login.Login;
import com.bbsitter.bbsitter.Main.MainActivityCanguro;
import com.bbsitter.bbsitter.OpcionesMenu.Anuncios.AnunciosFragment;
import com.bbsitter.bbsitter.OpcionesMenu.Perfil.EditarPerfilFragment;
import com.bbsitter.bbsitter.OpcionesMenu.Perfil.EditarPerfilViewModel;
import com.bbsitter.bbsitter.Perfiles.CrearPerfilCanguro;
import com.bbsitter.bbsitter.Perfiles.ProgressBarCrearPerfil;
import com.bbsitter.bbsitter.ProgressBarCargando;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarPerfilCanguroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarPerfilCanguroFragment extends Fragment {

    private EditarPerfilCanguroViewModel mViewModel;


    private final String KEY_API_GOOGLE = "AIzaSyCAq5pFIif49ezgqjq4x6ZEaFMyuGXnCH0";

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;
    private StorageReference storageReference;

    private String uid;


    private CircleImageView foto;
    private TextInputLayout nombre, apellidos, fechaNacimiento, direccion, telefono, descripcion;
    private TextInputEditText etNombre, etApellidos, etDireccion, etFechaNacimiento, etDescripcion, etTelefono;
    private TextView precioHora;
    private Slider sliderPrecio;
    private Button btnModificarPerfilCanguro, btnELiminarPerfilCanguro;

    private Uri uriFoto;
    private String urlFotoPerfil ="";

    private LatLng latLng;

    private int edadCanguro;

    private double precioHoraCanguro;
    private RadioGroup radioGroupSexo;
    private RadioButton rbMasculino, rbFemenino, rbMenos6Meses, rb6a12meses, rb1a2años, rb2a6años, rbMas6años ;

    /*
    private CheckBox checkbox_6a12meses, checkbox_1a3años,checkbox_3a6años, checkbox_mas6años;
    private CheckBox checkBoxEspañol, checkBoxIngles,checkBoxFrances, checkBoxAleman, checkBoxOtros;
    private CheckBox checkBoxNoFumador, checkBoxCarnetConducir,checkBoxPrimerosAuxilios, checkBoxCocinar, checkBoxDeberes;
    */

    // Para crear los checkbox y podamos  marcarlos y enviar a la base de datos, aquellos que esten mrcados
    ListView listViewPluses, listViewPreferencias, listViewIdiomas;
    ArrayAdapter adapterPrefrencias;
    ArrayAdapter<String> adapterPulses;
    ArrayAdapter<String> adapterIdiomas;
    String [] arrayPreferencias = {"De 6 a 12 meses", "De 1 a 3 años", "De 3 a 6 años","Más de 6 años"};
    String [] arrayPluses = {"No soy fumador", "Carnet de conducir", "Sé primeros auxilios","Puedo cocinar", "Ayudo con los deberes"};
    String [] arrayIdiomas = {"Español", "Inglés", "Francés","Alemán", "Otros"};


    public static EditarPerfilCanguroFragment newInstance() {
        return new EditarPerfilCanguroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_perfil_canguro, container, false);

        // FIREBASE
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        // Asignacion de variables
        foto = view.findViewById(R.id.imageEditarCanguro);
        nombre = view.findViewById(R.id.nombreEditarCanguro_edit_text);
        apellidos = view.findViewById(R.id.apellidosEditarCanguro_edit_text);
        telefono = view.findViewById(R.id.telefonoEditarPerfilCanguro_edit_text);
        descripcion = view.findViewById(R.id.descripcion_edit_textEditarCanguro);
        fechaNacimiento = view.findViewById(R.id.FechaNacimientoEditarCanguro_edit_textEditar);
        etNombre = view.findViewById(R.id.etNombreEditarCanguro);
        etApellidos = view.findViewById(R.id.etApellidosEditarCanguro);
        etDireccion = view.findViewById(R.id.etDireccionEditarCanguro);
        etTelefono = view.findViewById(R.id.etTelefonoEditarCanguro);
        etFechaNacimiento = view.findViewById(R.id.etFechaNacimientoEditarCanguro);
        etDescripcion = view.findViewById(R.id.etDescripcionEditarCanguro);
        precioHora = view.findViewById(R.id.textViewEditarCanguroPrecioHora);
        direccion = view.findViewById(R.id.direccion_edit_textEditarCanguro);

        // RADIO BUTTONS SEXO
        radioGroupSexo = view.findViewById(R.id.radioGroupSexoEditarCanguro);
        rbMasculino = view.findViewById(R.id.radio_button_MasculinoEditarCanguro);
        rbFemenino = view.findViewById(R.id.radio_button_FemeninoEditarCanguro);

        // RADIO BUTTONS EXPERIENCIA
        rbMenos6Meses = view.findViewById(R.id.radio_button_Menos6mesesEditarCanguro);
        rb1a2años = view.findViewById(R.id.radio_button_1a2añosEditarCanguro);
        rb6a12meses = view.findViewById(R.id.radio_button_6a12mesesEditarCanguro);
        rb2a6años = view.findViewById(R.id.radio_button_2a5añosEditarCanguro);
        rbMas6años = view.findViewById(R.id.radio_button_Mas6añosEditarCanguro);

        // ListView Checkboxs PREFERENCIAS
        listViewPreferencias = view.findViewById(R.id.listViewPrefrenciaEdadEditar);
        adapterPrefrencias = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice, arrayPreferencias);
        listViewPreferencias.setAdapter(adapterPrefrencias);


        // ListView Checkboxs PLUSES
        listViewPluses = view.findViewById(R.id.listViewPlusesEditar);
        adapterPulses = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice, arrayPluses);
        listViewPluses.setAdapter(adapterPulses);
        listViewPluses.setBackgroundColor(Color.GRAY);

        // ListView Checkboxs IDIOMAS
        listViewIdiomas = view.findViewById(R.id.listViewIdiomasEditarCanguro);
        adapterIdiomas = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice, arrayIdiomas);
        listViewIdiomas.setAdapter(adapterIdiomas);
        listViewIdiomas.setBackgroundColor(Color.GRAY);

        // SLIDER
        sliderPrecio = view.findViewById(R.id.slider_precio_horaEditarCanguro);

        // BOTON CREAR CANGURO
        btnModificarPerfilCanguro = view.findViewById(R.id.btnModificarPerfilCanguro);
        btnELiminarPerfilCanguro = view.findViewById(R.id.btnEliminarPerfilCanguro);

        // Campo Precio/hora. SLIDER precio de la hora, mostramos el precio que va eligiendo el usuario
        sliderPrecio.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                //String precio = String.valueOf(value);
                precioHora.setText(value + " €");
                precioHoraCanguro = value;
            }
        });

        //////////////////////////////////////////////////////////////////////////////

        // PROGRESS BAR con animacion Lottie
        final ProgressBarCargando progressBarCargando = new ProgressBarCargando(getActivity());

        cargarDatosPerfilCanguro();

        // Campo Fecha Nacimiento
        establecerFechaNacimiento();

        // Campo Direccion
        establecerAutocompletadoDireccion();

        // Poner foto
        foto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        // BOTON ELIMINAR PERFIL CANGURO
        btnELiminarPerfilCanguro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            MaterialAlertDialogBuilder builder =new MaterialAlertDialogBuilder(getContext(), R.style.MyMaterialAlertDialog);
                            builder.setTitle("Eliminar mi cuenta");
                            builder.setMessage("Atención: Esta acción es irreversible. ¿Estás seguro/a de que quieres eliminar tu cuenta?");
                            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    //ELIMINAMOS CANGURO

                                    bbdd.collection("canguros")
                                            .document(uid)
                                            .delete();


                                    //ELIMINAMOS USUARIO
                                    bbdd.collection("usuario")
                                            .document(uid)
                                            .delete();


                                    startActivity(new Intent(getContext(), Login.class));
                                    getActivity().finish();
                                }
                            });
                            builder.show();


                        } else {

                        }
                    }
                });
            }
        });

        // BOTON MODIFICAR PERFIL CANGURO
        btnModificarPerfilCanguro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Si todos los campos estan rellenados correctamente, validación a true
                if(validacionCampos()) {

                    //String fechaCreacionPerfil = ;
                    Random randomRating = new Random();
                    int rating  = randomRating.nextInt(6)+1;


                    final String uid = mAuth.getCurrentUser().getUid();
                    final String urlFoto = "";
                    String nombreCanguro = etNombre.getText().toString().trim();
                    String apellidosCanguro = etApellidos.getText().toString().trim();
                    String fechaNacimiento = etFechaNacimiento.getText().toString().trim();
                    String direccion = etDireccion.getText().toString().trim();
                    String descripcion = etDescripcion.getText().toString().trim();
                    String telefono = etTelefono.getText().toString().trim();
                    String sexo = obtenerSexo();
                    String experiencia = obtenerExperiencia();
                    double precio = precioHoraCanguro;


                    // LOCALIZACION
                    // Obtener coordenadas de direccion

                    double longitudLoc = latLng.longitude;
                    double latitudLoc = latLng.latitude;

                    // Crear MAPA COORDENADAS para meterlo en la localizacion del Canguro
                    Map<String, Double> mapLoc = new HashMap<>();
                    mapLoc.put("Latitud", latitudLoc);
                    mapLoc.put("Longitud", longitudLoc);


                    // ListView CHECKBOX PREFERENCIAS
                    Map<String, Boolean> mapPrefEdades = new HashMap<>();
                    for (int cont = 0; cont < listViewPreferencias.getCount(); cont++) {
                        if (listViewPreferencias.isItemChecked(cont)) {
                            String textoItemSeleccionado = listViewPreferencias.getItemAtPosition(cont).toString();
                            mapPrefEdades.put(textoItemSeleccionado, true);
                        }
                    }


                    // ListView CHECKBOX PLUSES
                    Map<String, Boolean> mapPluses = new HashMap<>();
                    for (int cont = 0; cont < listViewPluses.getCount(); cont++) {
                        if (listViewPluses.isItemChecked(cont)) {
                            String textoItemSeleccionado = listViewPluses.getItemAtPosition(cont).toString();
                            mapPluses.put(textoItemSeleccionado, true);
                        }
                    }

                    // ListView CHECKBOX IDIOMAS
                    Map<String, Boolean> mapIdiomas = new HashMap<>();
                    for (int cont = 0; cont < listViewIdiomas.getCount(); cont++) {
                        if (listViewIdiomas.isItemChecked(cont)) {
                            String textoItemSeleccionado = listViewIdiomas.getItemAtPosition(cont).toString();
                            mapIdiomas.put(textoItemSeleccionado, true);
                        }
                    }

                    // Escribir datos en FIRESTORE
                    /*Creamos un mapa para meter los datos de las familias*/
                    Map<String, Object> mapCanguro = new HashMap<>();

                    //mapCanguro.put("Id Canguro", idCanguro);
                    mapCanguro.put("uid", uid);
                    mapCanguro.put("nombre", nombreCanguro);
                    mapCanguro.put("apellidos", apellidosCanguro);
                    mapCanguro.put("fechaNacimiento", fechaNacimiento);
                    mapCanguro.put("edad", edadCanguro);
                    mapCanguro.put("sexo", sexo);
                    mapCanguro.put("telefono", telefono);
                    mapCanguro.put("direccion", direccion);
                    mapCanguro.put("localizacion", mapLoc);  // Mapa localizacion Canguro
                    mapCanguro.put("experiencia", experiencia);
                    mapCanguro.put("precioHora", precio);
                    mapCanguro.put("descripcion", descripcion);
                    mapCanguro.put("latitud", latitudLoc);
                    mapCanguro.put("longitud", longitudLoc);
                    mapCanguro.put("preferenciaEdades", mapPrefEdades);  // Mapa Prefrencia Edades Canguro
                    mapCanguro.put("pluses", mapPluses);  // Mapa Pluses Canguro
                    mapCanguro.put("idiomas", mapIdiomas);  // Mapa Idiomas Canguro
                    mapCanguro.put("rating", rating);

                    /*Introducimos el canguro nuevo dentro de la BBDD*/
                    bbdd.collection("canguros")
                            .document(uid)
                            .set(mapCanguro);

                    /*Mapa para de datos para actualizar el usuario*/
                    Map<String, Object> userUpdate = new HashMap<>();
                    userUpdate.put("perfil", true);
                    userUpdate.put("tipo", "canguro");



                    // METER FOTO DEL PERFIL CANGURO
                    final StorageReference rutaArchivo = storageReference.child("img_Canguros").child(uid +".jpg");

                    if(uriFoto != null)
                    {
                        /*Metemos la foto en Storage*/
                        rutaArchivo.putFile(uriFoto).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                rutaArchivo.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final Uri downloadUrl = uri;
                                        urlFotoPerfil = downloadUrl.toString();
                                        //urlFoto = urlFotoPerfil.toString();

                                        // Creamos un mapa para actualizar el perifl del usuario*/
                                        Map<String, Object> userUpdateImg = new HashMap<>();
                                        userUpdateImg.put("img", urlFotoPerfil);

                                        // Actualizamos el perfil del usuario para que no vuelva a la pantalla de creacion de perfil*/
                                        bbdd.collection("canguros").document(uid).set(userUpdateImg, SetOptions.merge());


                                    }
                                } ).addOnFailureListener( new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {

                                        Toast.makeText(getContext(), "Ha fallado", Toast.LENGTH_SHORT).show();
                                    }
                                } );


                            }
                        } );
                    }
                    else

                    {
                        //Creamos un mapa para actualizar la imagen del perfil
                        Map<String, Object> userUpdateImg = new HashMap<>();
                        userUpdateImg.put("img", "https://firebasestorage.googleapis.com/v0/b/bbsitter-61bd3.appspot.com/o/img_BBSitter%2Ffotoperfil.jpg?alt=media&token=a76cfc60-0edb-480c-953d-f0925fab2941");

                        bbdd.collection("canguros").document(uid).set(userUpdateImg, SetOptions.merge());
                    }



                    /*Actualizamos el perfil del usuario para que no vuelva a la pantalla de creacion de perfil*/
                    bbdd.collection("usuarios")
                            .document(uid)
                            .set(userUpdate, SetOptions.merge());

                    // Creamos PROGGRES BAR para que el usuario sepa que su perfil Canguro se está actualizando)
                    progressBarCargando.StarProgressBar();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBarCargando.finishProgressBar();

                            MiPerfilCanguroFragment miPerfilCanguroFragment = new MiPerfilCanguroFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.nav_host_fragment_canguro, miPerfilCanguroFragment)
                                    .addToBackStack(null)
                                    .commit();

                        }
                    }, 2000);

                }else{
                    Toasty.error(getContext(),"Hay campos a revisar", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditarPerfilCanguroViewModel.class);
        // TODO: Use the ViewModel
    }

    private void cargarDatosPerfilCanguro()
    {
        uid = mAuth.getCurrentUser().getUid();


        bbdd.collection("canguros")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Recogemos los datos de la base de datos
                                String nombre =  document.get("nombre").toString();
                                String apellidos = document.get("apellidos").toString();
                                String imagenCanguro = document.get("img").toString();
                                String direccionCanguro = document.get("direccion").toString();
                                String telefono = document.get("telefono").toString();
                                String descripcionCanguro = document.get("descripcion").toString();
                                String precioHoraCanguro = document.get("precioHora").toString();
                                Float precio = Float.parseFloat(precioHoraCanguro);

                                String fechaNaciminiento = document.get("fechaNacimiento").toString();
                                String experienciaCanguro = document.get("experiencia").toString();


                                //Cargar la imagen
                                Picasso.get().load(imagenCanguro).into(foto);

                                // Cargar los datos en los campos
                                etNombre.setText(nombre);
                                etApellidos.setText(apellidos);
                                etFechaNacimiento.setText(fechaNaciminiento);
                                etDireccion.setText("");
                                etDescripcion.setText(descripcionCanguro);
                                sliderPrecio.setValue(precio);
                                etTelefono.setText(telefono);

                            }
                        } else {

                        }
                    }
                });

    }
    private String obtenerExperiencia() {

        String experiencia ="";    // EXPERIENCIA: Elección de experiencia

        if(rbMenos6Meses.isChecked()){
            experiencia = rbMenos6Meses.getText().toString();
        } else if(rb6a12meses.isChecked()){
            experiencia = rbMenos6Meses.getText().toString();
        } else if(rb1a2años.isChecked()){
            experiencia = rb1a2años.getText().toString();
        } else if(rb2a6años.isChecked()){
            experiencia = rb2a6años.getText().toString();
        } else if(rbMas6años.isChecked()){
            experiencia = rbMas6años.getText().toString();
        }

        return experiencia;

    }

    private String obtenerSexo() {

        String sexo;    // SEXO: Elección de femenino o masculino
        if(rbMasculino.isChecked()){
            sexo = rbMasculino.getText().toString();
        }
        else{
            sexo = rbFemenino.getText().toString();
        }
        return sexo;
    }

    private boolean validacionCampos() {

        Boolean validar = true;

        String urlFoto = urlFotoPerfil.toString();
        String nombreCanguro = etNombre.getText().toString().trim();
        String apellidosCanguro = etApellidos.getText().toString().trim();
        String fechaNacimientoCanguro = etFechaNacimiento.getText().toString().trim();
        String direccionCanguro = etDireccion.getText().toString().trim();
        String descripcionCanguro = etDescripcion.getText().toString().trim();
        String telefonoCanguro = etTelefono.getText().toString().trim();
        String sexo = obtenerSexo();
        String experiencia = obtenerExperiencia();
        double precio = precioHoraCanguro;

        if(nombreCanguro.isEmpty()){
            nombre.setError("Debes de rellenar tu nombre");
            validar = false;
        }
        if (apellidosCanguro.isEmpty()){
            apellidos.setError("Debes de rellenar el campo");
            validar = false;
        }
        if (fechaNacimientoCanguro.isEmpty()){
            fechaNacimiento.setError("Debes de rellenar tu fecha de nacimiento");
            validar = false;
        }
        if (direccionCanguro.isEmpty()){
            direccion.setError("Debes de rellenar con una direccion");
            validar = false;
        }
        if (descripcionCanguro.isEmpty()){
            descripcion.setError("Debes de rellenar la descripcion");
            validar = false;
        }
        if (telefonoCanguro.isEmpty()){
            telefono.setError("Debes de rellenar el teléfono");
            validar = false;
        }

        if(!nombreCanguro.isEmpty()){
            nombre.setError(null);
        }
        if (!apellidosCanguro.isEmpty()){
            apellidos.setError(null);
        }
        if (!fechaNacimientoCanguro.isEmpty()){
            fechaNacimiento.setError(null);
        }
        if (!direccionCanguro.isEmpty()){
            direccion.setError(null);
        }
        if (!descripcionCanguro.isEmpty()){
            descripcion.setError(null);
        }
        if (!telefonoCanguro.isEmpty()){
            telefono.setError(null);
        }





        return validar;
    }

    // FECHA NACIMIENTO
    private void establecerFechaNacimiento() {

        etFechaNacimiento.setFocusable(false);
        etFechaNacimiento.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        Calendar calendarResultado = Calendar.getInstance();
                        calendarResultado.set(Calendar.YEAR, year);
                        calendarResultado.set(Calendar.MONTH, month);
                        calendarResultado.set(Calendar.DAY_OF_MONTH, day);

                        // OBTENEMOS EL AÑO DE NACIMIENTO (Recogido del metodo onDateSet) y el año Actual, obtenido de Calendar
                        int añoNacimiento = year;
                        int añoActual = calendar.get(Calendar.YEAR);

                        // CALCULAMOS LA EDAD
                        edadCanguro = añoActual - añoNacimiento;

                        // FORMATEAMOS LA FECHA PARA COLOCAR EN EL EDIT TEXT
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date dateFechaNacimiento = calendarResultado.getTime();
                        String fechaNacimiento = simpleDateFormat.format(dateFechaNacimiento);

                        etFechaNacimiento.setText(fechaNacimiento);

                    }
                }, calendar.get(Calendar.YEAR) - 18, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }

    private void cargarImagen() {

        Intent intentCargarFoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intentCargarFoto.setType("image/");

        startActivityForResult(intentCargarFoto.createChooser(intentCargarFoto, "Seleccione una foto"),200);

    }

    /*Buscamos la dirrecion con la API de Google*/
    private void establecerAutocompletadoDireccion() {

        // Inicializamos Google.PLACES para autocomplete la direccion
        if (!Places.isInitialized()) {
            Places.initialize(getContext(), KEY_API_GOOGLE);
        }

        etDireccion.setFocusable(false);
        etDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Inicializamos la lista de lugares
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                // Creamos un intent que nos mostrará el autocompletado de direcciones
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(getContext());

                // Start activity
                startActivityForResult(intent, 100);
            }
        });
    }

    /* Resultado de autocompletado Direccion*/
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Resultado de Activity para direccion
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {

                // Instanciamos un PLACE, el cual lo obtendremos del autocomplete
                Place place = Autocomplete.getPlaceFromIntent(data);

                String address = place.getAddress();

                try {

                    etDireccion.setText(address);
                    latLng = place.getLatLng();
                }
                catch(Exception e)
                {
                    Toasty.error(getContext(), "Debes rellenar la dirección", Toasty.LENGTH_LONG).show();
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getContext(), "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {

            }
        }

        // Resultado de obtener foto del movil
        else if (requestCode == 200){
            if (resultCode == RESULT_OK) {

                uriFoto= data.getData();

                foto.setImageURI(uriFoto);


            } else if(resultCode == RESULT_CANCELED){
            }
        }
    }
}