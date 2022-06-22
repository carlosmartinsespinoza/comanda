package com.mentooutsourcing.iservice.fragment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mentooutsourcing.iservice.adapter.MesaAdapter
import com.mentooutsourcing.iservice.databinding.FragmentMesaBinding
import com.mentooutsourcing.iservice.model.mesa.MesaRepository
import com.mentooutsourcing.iservice.model.mesa.MesaViewModel
import com.mentooutsourcing.iservice.model.mesa.MesaViewModelFactory
import com.mentooutsourcing.iservice.network.RetrofitService

class mesaFragment : Fragment() {

    private lateinit var binding: FragmentMesaBinding
    private lateinit var viewModel: MesaViewModel
    private val adapter = MesaAdapter() {
        abreAtendimento(it.idmesa,it.idcomanda,it.numeromesa,it.valorcomanda!!.toString())
    }
    private val retrofit =  RetrofitService.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMesaBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this,
                        MesaViewModelFactory(MesaRepository(retrofit)))
                            .get(MesaViewModel::class.java)
        binding.recyclerviewmesas.adapter = adapter
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.listaMesas.observe(this, Observer {
            adapter.setCurrentList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.leMesas()
    }
    private fun abreAtendimento(idmesa: Int,idcomanda: Int,numeromesa: String,valorcomanda: String) {
        val action = mesaFragmentDirections
                .actionMesaFragmentToAtendimentoFragment(idmesa,idcomanda,numeromesa,valorcomanda
        )
        findNavController().navigate(action)
    }
}