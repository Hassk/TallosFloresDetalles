package com.bernardosoler.tfd

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.bernardosoler.tfd.Activity.PrincipalActivity
import com.bernardosoler.tfd.databinding.ActivityMainBinding
import com.bernardosoler.tfd.ui.gallery.Model.Usuario
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private  val GOOGLE_SIGN_IN =100
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.Base_Theme_TFD)
        super.onCreate(savedInstanceState)
        val binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup(binding)
        session(binding)


    }
    private fun session(binding: ActivityMainBinding){
        val pref =getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email =pref.getString("email",null)
        val photo=pref.getString("photo",null)
        val provider= pref.getString("provider",null)
        val name=pref.getString("name",null)
        if (email != null && provider !=null){
            binding.authLayout.visibility = View.INVISIBLE
            showHome(email,ProviderType.valueOf(provider),photo.toString(),name.toString())

        }

    }
    override fun onStart() {
        super.onStart()
        val layout1 = findViewById<LinearLayout>(R.id.authLayout)
        layout1.visibility =View.VISIBLE

    }
    private fun setup(binding: ActivityMainBinding){

    binding.btnAcceder.setOnClickListener{
     if (binding.email.text.isNotEmpty() && binding.password.text.isNotEmpty()){

         FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.email.text.toString(),binding.password.text.toString()).addOnCompleteListener{
                if (it.isSuccessful){



                }
         }

     }

    }

    binding.btnRegistrar.setOnClickListener{


    }

    binding.btnGoogle.setOnClickListener{
        //configuracion
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        //cliente
        val googleClient = GoogleSignIn.getClient(this,googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent,GOOGLE_SIGN_IN)


    }


    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("se ha producido un error autenticando el usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
    private fun showHome(email :String,provider: ProviderType,photo :String,name:String){
        val intent = Intent(this,PrincipalActivity ::class.java).apply {
            putExtra( "email",email)
            putExtra("provider",provider.name)
            putExtra("photo",photo)
            putExtra("name",name)



        }
        startActivity(intent)


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN ){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account =task.getResult(ApiException ::class.java)
                if(account != null) {
                    val credencial = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credencial).addOnCompleteListener {
                        if(it.isSuccessful){
                            showHome(account.email?:"", ProviderType.GOOGLE,account.photoUrl.toString(),account.displayName.toString())

                        }else{

                            showAlert()
                        }



                    }

                }
            }catch (e : ApiException){
                showAlert()
            }

        }
    }


}