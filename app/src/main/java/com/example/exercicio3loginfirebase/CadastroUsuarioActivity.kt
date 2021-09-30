package com.example.exercicio3loginfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exercicio3loginfirebase.databinding.ActivityCadastroUsuarioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CadastroUsuarioActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityCadastroUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        //Login
        binding.signInAppCompatButtonCadastrarUsuario.setOnClickListener {
            val mEmail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()

            when {
                mEmail.isEmpty() || mPassword.isEmpty() -> {
                Toast.makeText(baseContext, "Informe o usuário e a senha para cadastro.", Toast.LENGTH_SHORT).show()
            } else -> {
                cadastroUsuario(mEmail, mPassword)
            }
            }
        }
    }

    private fun cadastroUsuario(email : String , password : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createInWithEmail:success")
                    reload()
                } else {
                    Log.w("TAG", "createInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Erro ao criar usuário", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload() {
        val intent = Intent (this, MainActivity::class.java)
        this.startActivity(intent)

    }
}