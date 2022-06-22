package com.mentooutsourcing.iservice.fragment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mentooutsourcing.iservice.R
import com.mentooutsourcing.iservice.adapter.ComandaItemAdapter
import com.mentooutsourcing.iservice.adapter.MesaAdapter
import com.mentooutsourcing.iservice.databinding.FragmentAtendimentoBinding
import com.mentooutsourcing.iservice.model.comanda.ComandaItemRepository
import com.mentooutsourcing.iservice.model.comanda.ComandaItemViewModel
import com.mentooutsourcing.iservice.model.comanda.ComandaItemViewModelFactory
import com.mentooutsourcing.iservice.model.mesa.Mesa
import com.mentooutsourcing.iservice.model.mesa.MesaRepository
import com.mentooutsourcing.iservice.model.mesa.MesaViewModel
import com.mentooutsourcing.iservice.model.mesa.MesaViewModelFactory
import com.mentooutsourcing.iservice.network.RetrofitService

class AtendimentoFragment : Fragment() {

    private lateinit var binding: FragmentAtendimentoBinding
    private val args: AtendimentoFragmentArgs by navArgs()
    private lateinit var viewModelItens: ComandaItemViewModel
    private val itemAdapter = ComandaItemAdapter(){

    }
    private val mesaAdapter= MesaAdapter() {

    }
    private val retrofit = RetrofitService.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      // Inflate the layout for this fragment
      binding = FragmentAtendimentoBinding.inflate(inflater, container, false)

      viewModelItens = ViewModelProvider(this,
                          ComandaItemViewModelFactory(
                             ComandaItemRepository(retrofit)))
                              .get(ComandaItemViewModel::class.java)

      binding.setLifecycleOwner(this@AtendimentoFragment)

      binding.recyclerviewitens.adapter = itemAdapter

      binding.recyclerviewmesa.adapter = mesaAdapter

      binding.floatingButtonAdicionaItens.setOnClickListener {
          adicionaItens(args.idmesa,args.idcomanda,args.numeromesa)
      }

      binding.floatingActionButtonRetornaMesas.setOnClickListener {
          findNavController().navigate(R.id.action_atendimentoFragment_to_mesaFragment)
      }

      return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModelItens.listaComandaItem.observe(this,{
            itemAdapter.setCurrentList(it)
        })
        viewModelItens.mesa.observe(this, Observer {
            mesaAdapter.setCurrentList(it)
        })
    }

    override fun onResume() {
        super.onResume()

        val idcomanda = getString(R.string.comandaItemCriteria,args.idcomanda)
        viewModelItens.leComandaItem(idcomanda)

        val idmesa = getString(R.string.mesaIdCriteria,args.idmesa)
        viewModelItens.leDadosMesa(idmesa)

   }
    private fun adicionaItens(idmesa: Int, idcomanda: Int?,numeromesa: String) {
        val action = AtendimentoFragmentDirections
                        .actionAtendimentoFragmentToProdutoFragment(idmesa,
                            idcomanda!!,numeromesa
        )
        findNavController().navigate(action)
    }
}