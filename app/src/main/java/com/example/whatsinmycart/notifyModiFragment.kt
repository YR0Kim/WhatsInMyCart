package com.example.whatsinmycart

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.whatsinmycart.databinding.FragmentNotifyModiBinding

class notifyModiFragment : DialogFragment() {

    lateinit var binding: FragmentNotifyModiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotifyModiBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        binding.modifyBtn.setOnClickListener {
            dismiss()
        }

        binding.deleteBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}