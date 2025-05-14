package com.example.whatsinmycart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsinmycart.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    data class CardModel(
        val shopping_date: String,
        val shopping_data1: String,
        val shopping_data2: String,
        val total_size: String
    )

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val itemList = mutableListOf<CardModel>()

        binding.searchview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SearchviewAdapter(itemList)
        binding.searchview.adapter = adapter

        return binding.root
    }
}

class SearchviewAdapter(val items: MutableList<SearchFragment.CardModel>) : RecyclerView.Adapter<SearchviewAdapter.CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchviewAdapter.CardViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CardViewHolder(v)
    }

    override fun onBindViewHolder(holder: SearchviewAdapter.CardViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(items: SearchFragment.CardModel) {
            val shopping_date = itemView.findViewById<TextView>(R.id.shopping_date)
            val shopping_data1 = itemView.findViewById<TextView>(R.id.shopping_data1)
            val shopping_data2 = itemView.findViewById<TextView>(R.id.shopping_data2)
            val total_size = itemView.findViewById<TextView>(R.id.total_size)

            shopping_date.text = items.shopping_date
            shopping_data1.text = items.shopping_data1
            shopping_data2.text = items.shopping_data2
            total_size.text = items.total_size
        }
    }
}