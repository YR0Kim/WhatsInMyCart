package com.example.whatsinmycart

import android.R.attr.data
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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