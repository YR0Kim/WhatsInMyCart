package com.example.whatsinmycart

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsinmycart.databinding.CartItemBinding
import com.example.whatsinmycart.databinding.FragmentCartBinding
import javax.sql.DataSource

class CartFragment : DialogFragment() {

    private var itemName:  String? = null
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val datas = mutableListOf<String>()

        for (i in 1 .. 10) {
            datas.add("살 것 $i")
        }
        binding.cartview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CartAdapter(datas)
        binding.cartview.adapter = adapter


        binding.plusButton.setOnClickListener {
            val dialog = addFragment()
            dialog.show(requireActivity().supportFragmentManager, "addFragment")
        }

        // 아이템 클릭 리스너

        adapter.itemClickListener = object : CartAdapter.OnItemClickListener {

            override fun onItemClick(position: Int) {

                val dialog = buyFragment()

                dialog.show(requireActivity().supportFragmentManager, "buyFragment")
            }


        }

        return binding.root
    }

}

class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

class CartAdapter(val datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

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
            itemClickListener?.onItemClick(position)
        }
    }

}