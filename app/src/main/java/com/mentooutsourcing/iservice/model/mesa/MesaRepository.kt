package com.mentooutsourcing.iservice.model.mesa

import com.mentooutsourcing.iservice.network.RetrofitService

class MesaRepository(private val retrofit: RetrofitService) {

    fun leMesas() = retrofit.getMesas()

    fun updateMesa(mesaId: String,mesa: Mesa)
                    = retrofit.leMesa(mesaId)
}