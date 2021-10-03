package com.example.exercicio3loginfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exercicio3loginfirebase.databinding.ActivityResetBinding
import com.google.firebase.auth.FirebaseAuth

class ResetActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityResetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        binding = ActivityResetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance();

        binding.signInAppCompatButtonReset.setOnClickListener {
            val mEmail = binding.emailEditText.text.toString()

            when {
                mEmail.isEmpty()  -> {
                    Toast.makeText(baseContext, "Favor informar o e-mail", Toast.LENGTH_SHORT).show()
                } else -> {
                reset(mEmail)
            }
            }

        }

    }

    private fun reset(mEmail : String) {
        auth.sendPasswordResetEmail(mEmail)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "sendPasswordResetEmail:success")
                    Toast.makeText(
                        this@ResetActivity,
                        "Recuperação de acesso iniciada. Email enviado.",
                        Toast.LENGTH_SHORT
                    ).show()
                    reload()
                } else {
                    Toast.makeText(
                        this@ResetActivity,
                        "Falhou! Tente novamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun reload() {
        val intent = Intent (this, LoginActivity::class.java)
        this.startActivity(intent)

    }
}
