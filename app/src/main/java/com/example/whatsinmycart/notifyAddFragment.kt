package com.example.whatsinmycart

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.whatsinmycart.databinding.FragmentNotifyAddBinding
import java.util.Calendar

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

        binding.dateSelector.setOnClickListener {
            val calendar = Calendar.getInstance()   // 캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                if(month+1 < 10) {
                    binding.addDate.text = "${year}-0${month + 1}-${dayOfMonth}"
                } else {
                    binding.addDate.text = "${year}-${month + 1}-${dayOfMonth}"
                }
            }
            DatePickerDialog(requireActivity(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
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