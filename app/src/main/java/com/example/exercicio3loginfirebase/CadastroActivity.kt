package com.example.exercicio3loginfirebase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.exercicio3loginfirebase.model.CadastroPedidos

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    fun cadastrarPedido(view: View?) {
        val textoMesaPedido = findViewById(R.id.textoMesaCadastro) as EditText
        val textoNomeAtendente = findViewById(R.id.textoNomeAtendenteCadastro) as EditText
        val textoNomePedido = findViewById(R.id.textoNomePedidoCadastro) as EditText
        val textoDescricaoPedido = findViewById(R.id.textoDescricaoPedidoCadastro) as EditText

        val mesa = textoMesaPedido.text.toString().toInt()
        val nomePedido = textoNomePedido.text.toString()
        val nomeAtendente = textoNomeAtendente.text.toString()
        val descricaoPedido = textoDescricaoPedido.text.toString()
        val cadastro = CadastroPedidos(mesa, nomeAtendente, nomePedido, descricaoPedido)
        val it = Intent().apply {
            putExtra("cadastro", cadastro)
        }
        setResult(Activity.RESULT_OK, it)

        finish()
    }

    fun cancelarCadastro(view: View?) {
        finish()
    }
}