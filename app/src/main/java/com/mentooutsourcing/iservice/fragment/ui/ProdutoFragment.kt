package com.mentooutsourcing.iservice.fragment.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iservice.refrofit.ProdutoAdapter
import com.mentooutsourcing.iservice.R
import com.mentooutsourcing.iservice.databinding.FragmentProdutoBinding
import com.mentooutsourcing.iservice.model.comanda.ComandaItem
import com.mentooutsourcing.iservice.model.produto.Produto
import com.mentooutsourcing.iservice.model.produto.ProdutoRepository
import com.mentooutsourcing.iservice.model.produto.ProdutoViewModel
import com.mentooutsourcing.iservice.model.produto.ProdutoViewModelFactory
import com.mentooutsourcing.iservice.network.RetrofitService

class ProdutoFragment : Fragment() {

    private val args: ProdutoFragmentArgs by navArgs()
    private lateinit var viewModel:
                            ProdutoViewModel
    private val retrofit = RetrofitService.getInstance()

    private val adapter = ProdutoAdapter{
                        incluirItemAtendimento(it)
    }

    private lateinit var binding: FragmentProdutoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProdutoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this@ProdutoFragment,
                       ProdutoViewModelFactory(
                         ProdutoRepository(retrofit))).get(
                               ProdutoViewModel::class.java)
        binding.numeroMesa.text = getString(R.string.numero_da_mesa,args.numeromesa)

        binding.floatingButtonBack.setOnClickListener {
            val action = ProdutoFragmentDirections
                            .actionProdutoFragmentToAtendimentoFragment(
                                    args.idmesa,args.idcomanda,
                                        args.numeromesa,"0"
                            )
            findNavController().navigate(action)
        }

        binding.recyclerviewproduto.adapter = adapter
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.listaProdutos.observe(this, Observer { produtos ->
            adapter.setCurrentList(produtos)
        })
        viewModel.mensagemIncluirItemComanda.observe(this, Observer {
            Toast.makeText(this.activity,"Item Incluído",Toast.LENGTH_SHORT).show()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.leProdutos()
    }
    private fun incluirItemAtendimento(item: Produto) {
        if (item.descricao.isNullOrEmpty()) {
                item.descricao = item.nome
        }
        val item = ComandaItem(clienteId = 0,
                               comandaId = args.idcomanda,
                               comandaItemId = 0,
                               produtoId = item.idproduto,
                               quantidaProduto = 1,
                               valorProduto = item.preco.toDouble(),
                               totalItem = item.preco.toDouble(),
                               nomeProduto = item.nome,
                               descricaoProduto = item.descricao,
                               mesaId = args.idmesa,
                               origemComanda = 0
                               )
        viewModel.incluirProdutoComanda(item,{})
    }
}