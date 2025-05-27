package com.example.whatsinmycart

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.whatsinmycart.databinding.FragmentBuyBinding
import java.util.*

class buyFragment : DialogFragment() {

    lateinit var binding: FragmentBuyBinding

    data class Item(
        var category: String? = "",
        var itemName: String? = "",
        var itemQuantity: Int? = null,
        var itemAmount: Int? = null,
        var itemDate: Date? = null,
        var outwill: Boolean? = false
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuyBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        val spinner = binding.categoryCombobox
        this.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.categories,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }



        binding.dateSelector.setOnClickListener {
            val calendar = Calendar.getInstance()   // 캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                binding.dateView.text = "${year}-${month+1}-${dayOfMonth}"
            }
            DatePickerDialog(requireActivity(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.submitBtn.setOnClickListener {
            dismiss()
        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

}

class SpinnerHandler(tv:TextView) : AdapterView.OnItemSelectedListener {
    var textview: TextView = tv
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(parent != null) {
            textview?.text = parent.getItemIdAtPosition(position).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}