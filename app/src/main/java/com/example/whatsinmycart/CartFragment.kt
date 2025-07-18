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

    private var itemName:  String? = null
    private var itemIndex: Int? = null

    companion object {
        fun newInstance(text: String, index: Int): addFragment {
            val fragment = addFragment()
            val args = Bundle()
            args.putString("text", text)
            args.putInt("index", index)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val datas = mutableListOf<String>()

        binding.cartview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CartAdapter(datas)
        binding.cartview.adapter = adapter

        // 추가 버튼 클릭
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

            override fun onEditClick(position: Int, item: String) {
                val dialog = addFragment.newInstance(item, position)
                dialog.show(parentFragmentManager, "editItem")
            }

            override fun onDeleteClick(position: Int) {
                datas.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }

        // FragmentResultListener 등록
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

        return binding.root
    }

}

class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

class CartAdapter(val datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onEditClick(position: Int, item: String)
        fun onDeleteClick(position: Int)
    }
    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CartViewHolder(CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as CartViewHolder).binding

        binding.itemData.text = datas[position]

        // 체크박스 클릭 시 buyFragment 호출
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                itemClickListener?.onItemClick(position)
                binding.checkbox.isChecked = false
            }
        }

        // 길게 누르면 수정/삭제 다이얼로그 표시
        binding.itemData.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("작업 선택")
                .setItems(arrayOf("수정", "삭제")) { _, which ->
                    when (which) {
                        0 -> itemClickListener?.onEditClick(position, datas[position])
                        1 -> itemClickListener?.onDeleteClick(position)
                    }
                }
                .show()
            true // 롱클릭 이벤트 소비
        }
    }

}