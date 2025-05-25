package com.example.whatsinmycart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsinmycart.databinding.ActivityNotifyBinding
import com.example.whatsinmycart.databinding.NotifyItemBinding

class NotifyActivity : AppCompatActivity() {

    data class CardModel(
        val notifyItem: String,
        val notifyDate: String
    )

    lateinit var binding: ActivityNotifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "알림"

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.navigationIcon?.setTint(Color.WHITE)

        val dataList = arrayListOf<CardModel>()

        dataList.add(CardModel("칫솔", "3월 8일"))
        dataList.add(CardModel("영양제", "6월 23일"))
        dataList.add(CardModel("치약", "8월 17일"))

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = notifyAdapter(dataList)
        binding.recyclerView.adapter = adapter

        // 리스트 아이템 클릭 이벤트
        adapter.itemClickListener = object: notifyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val dialog = notifyModiFragment()
                dialog.show(supportFragmentManager, "notifyModiFragment")
            }
        }

        binding.addBtn.setOnClickListener {
            val dialog = notifyAddFragment()

            /*dialog.setButtonClickListener(object: notifyAddFragment.OnButtonClickListener{
                // addBtn 이벤트 설정
            })*/

            dialog.show(supportFragmentManager, "notifyAddFragment")
        }
    }
}

class notifyAdapter(val dataList: ArrayList<NotifyActivity.CardModel>):
    RecyclerView.Adapter<notifyAdapter.Holder>() {

    inner class Holder(val binding: NotifyItemBinding): RecyclerView.ViewHolder(binding.root) {
        val notifyItem = binding.notifyItem
        val notifyDate = binding.notifyDate
    }

    // 클릭 이벤트 처리
    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notifyAdapter.Holder {
        val binding = NotifyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: notifyAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(position)
        }
        holder.notifyItem.text = "  " + dataList[position].notifyItem
        holder.notifyDate.text = dataList[position].notifyDate + " 구입 예정"
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}