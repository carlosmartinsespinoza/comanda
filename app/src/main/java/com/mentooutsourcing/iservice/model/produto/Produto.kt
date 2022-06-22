package com.mentooutsourcing.iservice.model.produto

data class Produto(
    val idproduto: Int,
    val produtosku: String,
    val nome: String,
    val preco: Double,
    val quantidadeemestoque: String,
    val quantidadeajustada: String,
    val custo: Double,
    var descricao: String
)
