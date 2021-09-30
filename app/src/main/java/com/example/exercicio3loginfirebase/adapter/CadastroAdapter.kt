package com.example.exercicio3loginfirebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exercicio3loginfirebase.R
import com.example.exercicio3loginfirebase.model.CadastroPedidos

class CadastroAdapter(private var listaPedidos: ArrayList<CadastroPedidos>) : RecyclerView.Adapter<CadastroAdapter.MyViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textoMesa: TextView
        var textoNomeAtendente: TextView
        var textoNomePedido: TextView
        var textoDescricaoPedido: TextView

        init {
            textoMesa = view.findViewById(R.id.textoMesaItem)
            textoNomeAtendente = view.findViewById(R.id.textoNomeAtendenteItem)
            textoNomePedido = view.findViewById(R.id.textoNomePedidoItem)
            textoDescricaoPedido = view.findViewById(R.id.textoDescricaoPedidoItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CadastroAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_pedido, parent, false) as View
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textoMesa.text = listaPedidos.get(position).mesa.toString()
        holder.textoNomeAtendente.text = listaPedidos.get(position).nomeAtendente
        holder.textoNomePedido.text = listaPedidos.get(position).nomePedido
        holder.textoDescricaoPedido.text = listaPedidos.get(position).descricaoPedido
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClicked(
                holder.itemView,
                position
            )
        }
    }

    override fun getItemCount() = listaPedidos.size

    interface OnItemClickListener {
        fun onItemClicked(view: View, position: Int)
    }
}
