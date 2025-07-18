package com.example.whatsinmycart

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.whatsinmycart.databinding.FragmentAddBinding

class addFragment : DialogFragment() {
    private lateinit var binding: FragmentAddBinding

    private var itemText: String? = null
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        itemText = arguments?.getString("text")
        itemIndex = arguments?.getInt("index")

        // 배경을 흰색으로 설정
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        // EditText에 기존 텍스트 설정
        binding.addItem.setText(itemText)

        // '등록' 버튼 클릭
        binding.submitBtn.setOnClickListener {
            //val itemName = binding.addItem.text.toString()
            val itemName = binding.addItem.text.toString().trim()   // 사용자가 입력한 값

            if (itemName.isNotEmpty()){
                // FragmentResult로 값 전달
                val result = Bundle().apply {
                    putString("item_key", itemName)
                    putInt("item_index", itemIndex ?: -1)
                }

                parentFragmentManager.setFragmentResult("add_item_request", result)
            }

            dismiss()

        }

        // '취소' 버튼 클릭
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}