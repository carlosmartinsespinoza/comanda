package com.iservice.refrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mentooutsourcing.iservice.databinding.ItemComandaBinding
import com.mentooutsourcing.iservice.model.produto.Produto


class ProdutoAdapter(private val onItemClicked: (Produto) -> Unit):
                                RecyclerView.Adapter<ProdutoAdapter.MainViewHolder>() {

    private var listaProdutos = mutableListOf<Produto>()

    fun setCurrentList(produtos: List<Produto>) {

        this.listaProdutos = produtos.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemComandaBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val produto = listaProdutos[position]
        holder.bind(produto, onItemClicked)
    }

    override fun getItemCount(): Int {
        return listaProdutos.size
    }
    class MainViewHolder(val binding: ItemComandaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Produto, onItemClicked: (Produto) -> Unit) {
            binding.nomeProduto.text = item.nome
            binding.descricaoProduto.text = item.descricao
            binding.precoProduto.text = item.preco.toString()

            itemView.setOnClickListener{
                onItemClicked(item)
            }
        }
    }
}

