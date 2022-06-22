package com.mentooutsourcing.iservice.model.mesa

import com.google.gson.annotations.SerializedName

data class Mesa (
    @SerializedName("idmesa") val idmesa: Int,
    @SerializedName("quantidadelugares") val quantidadelugares: Int,
    @SerializedName("numeromesa") val numeromesa: String,
    @SerializedName("idcomanda") val idcomanda: Int,
    @SerializedName("ocupada") var ocupada: Int,
    @SerializedName("valorcomanda") val valorcomanda: String
)