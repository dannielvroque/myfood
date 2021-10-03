package com.example.exercicio3loginfirebase

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercicio3loginfirebase.adapter.CadastroAdapter
import com.example.exercicio3loginfirebase.model.CadastroPedidos
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList
import com.google.firebase.firestore.ktx.firestore

class MainActivity : AppCompatActivity(), CadastroAdapter.OnItemClickListener {

    private val REQ_CADASTRO = 1;
    private val REQ_DETALHE = 2;
    private var listaPedidos: ArrayList<CadastroPedidos> = ArrayList()
    private var posicaoAlterar = -1

    private lateinit var recyclerView: RecyclerView
    private lateinit var view: CadastroAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        view = CadastroAdapter(listaPedidos)
        view.onItemClickListener = this

        db.collection("cadastro").addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("Firestore Error", error.message.toString())
            }

            for (dc: DocumentChange in value?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {

                    var cadastroPedido = CadastroPedidos(
                        dc.document.toObject(CadastroPedidos::class.java).mesa,
                        dc.document.toObject(CadastroPedidos::class.java).nomeAtendente,
                        dc.document.toObject(CadastroPedidos::class.java).nomePedido,
                        dc.document.toObject(CadastroPedidos::class.java).descricaoPedido,
                        dc.document.id
                    )
                    listaPedidos.add(cadastroPedido)
                }
            }

            view.notifyDataSetChanged()
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = view
        }

    }

    override fun onItemClicked(view: View, position: Int) {
        val it = Intent(this, DetalheActivity::class.java)
        this.posicaoAlterar = position
        val cadastroPedidos = listaPedidos.get(position)
        it.putExtra("cadastro", cadastroPedidos)
        @Suppress("DEPRECATION")
        this.startActivityForResult(it, REQ_DETALHE)
    }

    fun abrirPedidos(view: View) {
        val it = Intent(this, CadastroActivity::class.java)
        @Suppress("DEPRECATION")
        this.startActivityForResult(it, REQ_CADASTRO)
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CADASTRO) {
            if (resultCode == Activity.RESULT_OK) {
                val cadastroPedido = data?.getSerializableExtra("cadastro") as CadastroPedidos

                // Add a new document with a generated ID
                db.collection("cadastro")
                    .add(cadastroPedido)
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot added")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

                view.notifyDataSetChanged()
                Toast.makeText(this, "Cadastro realizada com sucesso!", Toast.LENGTH_SHORT)
                    .show()
            }
        } else if (requestCode == REQ_DETALHE) {
            if (resultCode == DetalheActivity.RESULT_EDIT) {
                val cadastroPedido = data?.getSerializableExtra("cadastro") as CadastroPedidos
                listaPedidos.set(this.posicaoAlterar, cadastroPedido)

                // atualizar no banco firestore

                db.collection("cadastro").document(cadastroPedido.key.toString())
                    .update("mesa",cadastroPedido.mesa?.toInt(), "nomeAtendente", cadastroPedido.nomeAtendente.toString(),"nomePedido", cadastroPedido.nomePedido.toString(), "descricaoPedido" ,cadastroPedido.descricaoPedido.toString()
                    )
                    .addOnSuccessListener { document ->
                        Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "DocumentSnapshot added")
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
                        Log.w("Firebase", "Error adidin documen", e)
                    }
                view.notifyDataSetChanged()
            } else if (resultCode == DetalheActivity.RESULT_DELETE) {
                val cadastroPedido = data?.getSerializableExtra("cadastro") as CadastroPedidos
                listaPedidos.removeAt(this.posicaoAlterar)

                db.collection("cadastro").document(cadastroPedido.key.toString())
                    .delete()
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Deletado com sucesso", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "Registro deletado com sucesso!")
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
                        Log.w("Firebase", "Error deleting document", e)
                    }

                view.notifyDataSetChanged()
            }
        }
    }

    fun logout(view: View){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Deseja sair?")
        builder.setPositiveButton("Sim"){dialog, which ->
            Toast.makeText(this,"Usuário deslogou.",Toast.LENGTH_SHORT).show()
            Firebase.auth.signOut()
            val intent = Intent (this, LoginActivity::class.java)
            this.startActivity(intent)
        }
        builder.setNegativeButton("Não"){dialog,which ->
            Toast.makeText(this,"Permaneceu na main",Toast.LENGTH_SHORT).show()
            return@setNegativeButton
        }

        builder.setNeutralButton("Cancelar"){_,_ ->
            Toast.makeText(this,"Você cancelou a ação.",Toast.LENGTH_SHORT).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}