package com.example.whatsinmycart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsinmycart.databinding.CartItemBinding
import com.example.whatsinmycart.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val datas = mutableListOf<String>()

        for (i in 1 .. 50) {
            datas.add("살 것 $i")
        }
        binding.cartview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CartAdapter(datas)
        binding.cartview.adapter = adapter

        binding.plusButton.setOnClickListener {
            datas.add("new data")
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

}

class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

class CartAdapter(val datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CartViewHolder(CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as CartViewHolder).binding

        binding.itemData.text = datas[position]
        binding.itemData.setOnClickListener {
            Log.d("kkang", "Item Click: $position")
        }
    }

}