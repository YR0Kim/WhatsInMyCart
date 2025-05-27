package com.example.whatsinmycart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.data.Entry
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsinmycart.databinding.FragmentSearchBinding
import com.github.mikephil.charting.data.LineDataSet

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

    private fun initLineChart() {
        val data = listOf(
            Entry(1f, 4.67f),
            Entry(2f, 5f),
            Entry(3f, 4.8f),
            Entry(4f, 5.9f)
        )

        val dataSet = LineDataSet(data, "")
        val lineColor = resources.getColor(R.color.lavender)

        /*dataSet.apply {
            valueTextSize = 12F
            valueTextColor = resources.getColor(R.color.black, null)
            valueFormatter = DataFormatter()

        }*/

    }

    /*private fun setOnQueryTextListener() {
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener{

            // 검색 버튼 입력 시 호출, 검색 버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            // 텍스트 입력, 수정 시 호출
            fun onQeuryTextChange(newText: String?): Boolean {
                updateItemCurrntFragment(newText)
                return false
            }
            })
    }*/
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