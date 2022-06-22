package com.mentooutsourcing.iservice.model.comanda

import com.google.gson.annotations.SerializedName

data class ComandaItem(
    @SerializedName("custnumber") val clienteId: Int,
    @SerializedName("linenumber") val comandaItemId: Int,
    @SerializedName("mesaid") val mesaId: Int,
    @SerializedName("orderedby") val origemComanda: Int,
    @SerializedName("ordernumber") val comandaId: Int,
    @SerializedName("price") val valorProduto: Double,
    @SerializedName("productdescription") val descricaoProduto: String,
    @SerializedName("productname") val nomeProduto: String,
    @SerializedName("productnumber") val produtoId: Int,
    @SerializedName("quantityordered") val quantidaProduto: Int = 1,
    @SerializedName("totalcost") val totalItem: Double
    )
