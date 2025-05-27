package com.example.whatsinmycart

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.whatsinmycart.databinding.FragmentNotifyModiBinding
import java.util.Calendar

class notifyModiFragment : DialogFragment() {

    lateinit var binding: FragmentNotifyModiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotifyModiBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        binding.dateSelector.setOnClickListener {
            val calendar = Calendar.getInstance()   // 캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                if(month+1 < 10) {
                    binding.modifyDate.text = "${year}-0${month + 1}-${dayOfMonth}"
                } else {
                    binding.modifyDate.text = "${year}-${month + 1}-${dayOfMonth}"
                }
            }
            DatePickerDialog(requireActivity(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.modifyBtn.setOnClickListener {
            dismiss()
        }

        binding.deleteBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}