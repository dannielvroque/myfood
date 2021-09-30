package com.example.exercicio3loginfirebase.model

import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class CadastroUsuario(val emailUsuario: String?=null,
                           val senhaUsuario: String?=null
): Serializable