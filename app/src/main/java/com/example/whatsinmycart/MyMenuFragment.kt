package com.example.whatsinmycart

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import com.example.whatsinmycart.databinding.FragmentMyMenuBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class MyMenuFragment : Fragment() {

    lateinit var binding: FragmentMyMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyMenuBinding.inflate(inflater, container, false)
        binding.pieChart.setUsePercentValues(true)

        // 월 설정 버튼으로 넘버피커 다이얼로그 출력
        binding.selectMonthBtn.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext()).create()
            val edialog: LayoutInflater = LayoutInflater.from(requireContext())
            val dView: View = edialog.inflate(R.layout.dialog_datepicker, null)

            val year: NumberPicker = dView.findViewById(R.id.year_picker)
            val month: NumberPicker = dView.findViewById(R.id.month_picker)
            val saveBtn: Button = dView.findViewById(R.id.saveBtn_np)
            val cancelBtn: Button = dView.findViewById(R.id.cancelBtn_np)

            // 순환 설정
            year.wrapSelectorWheel = false
            month.wrapSelectorWheel = true

            // editText 설정 해제
            year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            // year 범위 설정
            year.minValue = 2024
            year.maxValue = 2040

            // month 범위 설정
            month.minValue = 1
            month.maxValue = 12

            // 입력 버튼 클릭 이벤트
            saveBtn.setOnClickListener {

                if(month.value < 10) {
                    binding.selectMonth.text = (year.value).toString() + " - 0" + (month.value).toString()
                } else {
                    binding.selectMonth.text = (year.value).toString() + " - " + (month.value).toString()
                }

                dialog.dismiss()
                dialog.cancel()
            }

            // 취소 버튼 클릭 이벤트
            cancelBtn.setOnClickListener {
                dialog.dismiss()
                dialog.cancel()
            }

            dialog.setView(dView)
            dialog.create()
            dialog.show()
        }

        // 파이 차트 설정
        val dataList = ArrayList<PieEntry>()

        var tempTotalCategory1 = 145000f
        var tempTotalCategory2 = 25000f
        var tempTotalCategory3 = 5000f
        var tempTotalCategory4 = 100000f
        var tempTotalCategory5 = 125000f
        var tempTotalCategory6 = 35000f

        dataList.add(PieEntry(tempTotalCategory1, "식료품"))
        dataList.add(PieEntry(tempTotalCategory2, "간식"))
        dataList.add(PieEntry(tempTotalCategory3, "의약품"))
        dataList.add(PieEntry(tempTotalCategory4, "취미용품"))
        dataList.add(PieEntry(tempTotalCategory5, "생활용품"))
        dataList.add(PieEntry(tempTotalCategory6, "기타"))

        val dataSet = PieDataSet(dataList, "")
        with(dataSet) {
            sliceSpace = 3f
            setColors(*ColorTemplate.PASTEL_COLORS)
        } // 섹션 별 색상 설정

        // pieChart 안에 들어간 텍스트 크기
        dataSet.valueTextSize = 16F

        // pieChart 안에 들어간 value 값 표기 지우기
        dataSet.setDrawValues(false)

        // 데이터 설정 값 삽입
        var piedata = PieData(dataSet)

        binding.run {
            pieChart.apply {
                data = piedata
                description.isEnabled = false // 차트 설명 비활성화
                legend.isEnabled = false // 하단 설명 비활성화
                isRotationEnabled = false // 차트 회전 비활성화
                isDrawHoleEnabled = true // 도넛 형태의 파이차트(원 형태는 false로)
                setEntryLabelColor(Color.BLACK) // label 색상
                setTouchEnabled(false) // 터치 비활성화

                // 애니메이션 설정 생략
            }
        }

        return binding.root
    }
}