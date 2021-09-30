package com.example.exercicio3loginfirebase.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class CadastroPedidos(val mesa: Int?=null,
                           val nomeAtendente: String?=null,
                           val nomePedido: String?=null,
                           val descricaoPedido: String?=null,
                           @Exclude
                           val key: String?= null
): Serializable
