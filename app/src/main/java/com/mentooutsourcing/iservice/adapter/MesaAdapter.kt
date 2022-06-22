package com.mentooutsourcing.iservice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mentooutsourcing.iservice.R
import com.mentooutsourcing.iservice.databinding.MesaItemBinding
import com.mentooutsourcing.iservice.model.mesa.Mesa

class MesaAdapter(private val onItemClicked: (Mesa) -> Unit):
                        RecyclerView.Adapter<MesaAdapter.ItemViewHolder>() {

    private var listaMesas = mutableListOf<Mesa>()

    fun setCurrentList(mesa: List<Mesa>) {
        this.listaMesas = mesa.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MesaItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val mesa = listaMesas[position]
        holder.bind(mesa, onItemClicked)
    }

    override fun getItemCount(): Int {
        return listaMesas.size
    }

    class ItemViewHolder(val binding: MesaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Mesa, onItemClicked: (Mesa) -> Unit) {
            binding.valorComanda.text = item.valorcomanda.toString()
            binding.imagemMesa.setImageResource(R.drawable.mesajantar)
            binding.numeroMesa.text = item.numeromesa

            if (item.ocupada == 0) {
                binding.situacaoMesa.text = "Livre"
            } else binding.situacaoMesa.text = "Ocupada"
            binding.imagemMesa.setOnClickListener{
                onItemClicked(item)
            }
        }
    }
}