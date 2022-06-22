package com.mentooutsourcing.iservice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mentooutsourcing.iservice.databinding.ItemComandaBinding
import com.mentooutsourcing.iservice.model.comanda.ComandaItem

class ComandaItemAdapter(private val onItemClicked: (ComandaItem) -> Unit) : RecyclerView.Adapter<MainViewHolder>() {

    private var listaProdutos = mutableListOf<ComandaItem>()

    fun setCurrentList(produtos: List<ComandaItem>) {

        this.listaProdutos = produtos.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemComandaBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = listaProdutos[position]
        holder.bind(item, onItemClicked)
    }

    override fun getItemCount(): Int {
        return listaProdutos.size
    }
}

class MainViewHolder(val binding: ItemComandaBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ComandaItem, onItemClicked: (ComandaItem) -> Unit) {

        binding.nomeProduto.text = item.nomeProduto
        binding.descricaoProduto.text = item.descricaoProduto
        binding.precoProduto.text = item.valorProduto.toString()
    }

}