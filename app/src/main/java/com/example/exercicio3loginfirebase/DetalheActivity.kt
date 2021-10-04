package com.example.exercicio3loginfirebase

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exercicio3loginfirebase.model.CadastroPedidos
import com.orhanobut.hawk.Hawk

class DetalheActivity : AppCompatActivity(){
    companion object {

        const val RESULT_EDIT = 1
        const val RESULT_DELETE = 2
        const val RESULT_PRINT = 3
    }

    private lateinit var btPrint: BtPrint

    private lateinit var textoMesa: EditText
    private lateinit var textoNomeAtendente: EditText
    private lateinit var textoNomePedido: EditText
    private lateinit var textoDescricaoPedido: EditText
    private lateinit var keyFirebase: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)
        val intent = intent
        val Pedido = intent.getSerializableExtra("cadastro") as CadastroPedidos
        textoMesa = findViewById<EditText>(R.id.textoMesaDetalhe).apply {
            setText(Pedido.mesa.toString())
        }
        textoNomeAtendente = findViewById<EditText>(R.id.textoNomeAtendenteDetalhe).apply {
            setText(Pedido.nomeAtendente)
        }
        textoNomePedido = findViewById<EditText>(R.id.textoNomePedidoDetalhe).apply {
            setText(Pedido.nomePedido)
        }
        textoDescricaoPedido = findViewById<EditText>(R.id.textoDescricaoPedidoDetalhe).apply {
            setText(Pedido.descricaoPedido)
        }
        keyFirebase = findViewById<TextView>(R.id.key).apply {
            text = Pedido.key.toString()
        }


    }

    fun editarProduto(v: View?) {
        val Pedido = CadastroPedidos(
            textoMesa.text.toString().toInt(),
            textoNomeAtendente.text.toString(),
            textoNomePedido.text.toString(),
            textoDescricaoPedido.text.toString(),
            keyFirebase.text.toString()
        )
        val data = Intent()
        data.putExtra("cadastro", Pedido)
        setResult(RESULT_EDIT, data)
        finish()
    }

    fun excluirProduto(v: View?) {
        val Pedido = CadastroPedidos(
            textoMesa.text.toString().toInt(),
            textoNomeAtendente.text.toString(),
            textoNomePedido.text.toString(),
            textoDescricaoPedido.text.toString(),
            keyFirebase.text.toString()
        )
        val data = Intent()
        data.putExtra("cadastro", Pedido)
        setResult(RESULT_DELETE, data)
        finish()
    }

    fun voltar(v: View?) {
        finish()
    }
}