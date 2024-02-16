package com.bernardosoler.tfd

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bernardosoler.tfd.databinding.ActivityMain2Binding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC,
    GOOGLE

}
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityMain2Binding.inflate(layoutInflater)
        val bundle = intent.extras
        val email=bundle?.getString("email")
        val provider=bundle?.getString("provider")
        val photo=bundle?.getString("photo")
        val name=bundle?.getString("name")
        setContentView(binding.root)
        setup(binding,email?:"",provider?:"",photo?:"",name?:"")
        //GUARDADO DE DATOS
        val pref =getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        pref.putString("email",email)
        pref.putString("provider",provider)
        pref.putString("photo",photo)
        pref.putString("name",name)
        pref.apply()




    }
    private fun setup(binding: ActivityMain2Binding,email:String,provider :String,photo:String,name:String){
        binding.emailTextView.text = email
        binding.providerTextView.text = provider
        binding.nameText.text=name
        Glide.with(this).load(photo).into(binding.profile)
        binding.logOutButton1.setOnClickListener{
            val pref =getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            pref.clear()
            pref.apply()
            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }

    }

}