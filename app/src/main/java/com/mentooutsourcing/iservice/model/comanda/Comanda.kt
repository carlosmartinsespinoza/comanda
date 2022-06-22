package com.mentooutsourcing.iservice.model.comanda


data class Comanda (
    val comandaId: Long,
    val dataComanda: Long,
    val valorComanda: Double,
    val mesaId: Long,
    val nomeCliente: String,
    val situacaoComanda: Int = 0 //0 - Em aberto, 1 - fechado
)