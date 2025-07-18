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
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsinmycart.databinding.CartItemBinding
import com.example.whatsinmycart.databinding.FragmentCartBinding
import javax.sql.DataSource

class CartFragment : DialogFragment() {

    private lateinit var binding: FragmentCartBinding
    private val datas = mutableListOf<String>()
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupListeners()
        observeAddItemResult()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(datas).apply {
            itemClickListener = object : CartAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    buyFragment().show(parentFragmentManager, "buyFragment")
                }

                override fun onEditClick(position: Int, item: String) {
                    addFragment.newInstance(item, position)
                        .show(parentFragmentManager, "editItem")
                }

                override fun onDeleteClick(position: Int) {
                    datas.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            }
        }

        binding.cartview.layoutManager = LinearLayoutManager(requireContext())
        binding.cartview.adapter = adapter
    }

    private fun setupListeners() {
        binding.plusButton.setOnClickListener {
            addFragment().show(parentFragmentManager, "addFragment")
        }
    }

    private fun observeAddItemResult() {
        parentFragmentManager.setFragmentResultListener("add_item_request", viewLifecycleOwner) { _, bundle ->
            val index = bundle.getInt("item_index", -1)
            bundle.getString("item_key")?.let { newItem ->
                if (index >= 0) {
                    datas[index] = newItem
                    adapter.notifyItemChanged(index)
                } else {
                    datas.add(newItem)
                    adapter.notifyItemInserted(datas.size - 1)
                }
            }
        }
    }
}


class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

class CartAdapter(private val datas: MutableList<String>) : RecyclerView.Adapter<CartViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onEditClick(position: Int, item: String)
        fun onDeleteClick(position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val binding = holder.binding
        val item = datas[position]

        binding.itemData.text = item

        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                itemClickListener?.onItemClick(position)
                binding.checkbox.isChecked = false
            }
        }

        binding.itemData.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("작업 선택")
                .setItems(arrayOf("수정", "삭제")) { _, which ->
                    when (which) {
                        0 -> itemClickListener?.onEditClick(position, item)
                        1 -> itemClickListener?.onDeleteClick(position)
                    }
                }.show()
            true
        }
    }
}