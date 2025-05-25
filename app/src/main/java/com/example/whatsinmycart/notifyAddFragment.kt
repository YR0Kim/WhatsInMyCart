package com.example.whatsinmycart

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.whatsinmycart.databinding.FragmentNotifyAddBinding

class notifyAddFragment : DialogFragment() {

    lateinit var binding: FragmentNotifyAddBinding
    private var _binding: FragmentNotifyAddBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotifyAddBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        binding.addBtn.setOnClickListener {
            buttonClickListener.onAddBtnClicked()
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnButtonClickListener {
        fun onAddBtnClicked()
    }

    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }

    private lateinit var buttonClickListener: OnButtonClickListener
}