package com.example.whatsinmycart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsinmycart.databinding.FragmentListBinding
import com.example.whatsinmycart.databinding.ListItemBinding

class ListFragment : Fragment() {

    data class CardModel(
        val shopping_date: String,
        val shopping_data1: String,
        val shopping_data2: String,
        val total_size: String
    )

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentListBinding.inflate(inflater, container, false)

        val itemList = mutableListOf<CardModel>()

        itemList.add(CardModel("  3월 18일", "소면", "우유", "외 5건"))
        itemList.add(CardModel("  3월 19일", "칫솔", "치약", "외 1건"))
        itemList.add(CardModel("  3월 20일", "과자", "샤워볼", "외 3건"))
        itemList.add(CardModel("  3월 24일", "음료수", "컵라면", "외 7건"))
        itemList.add(CardModel("  3월 31일", "커피", "디저트", "외 1건"))

        binding.listview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ListviewAdapter(itemList)
        binding.listview.adapter = adapter

        return binding.root
    }
}



class ListviewAdapter(val items: MutableList<ListFragment.CardModel>) : RecyclerView.Adapter<ListviewAdapter.CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListviewAdapter.CardViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CardViewHolder(v)
    }

    override fun onBindViewHolder(holder: ListviewAdapter.CardViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(items: ListFragment.CardModel) {
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
