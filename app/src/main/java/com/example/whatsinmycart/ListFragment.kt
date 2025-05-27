package com.example.whatsinmycart

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.whatsinmycart.CalendarDecorators.dayDecorator
import com.example.whatsinmycart.databinding.FragmentListBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.Calendar

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
//    private val viewModel: CalendarViewModel by viewModels()

    // 데코레이터 변수를 나중에 초기화
    private lateinit var dayDecorator: DayViewDecorator
    private lateinit var todayDecorator: DayViewDecorator
    private lateinit var selectedMonthDecorator: DayViewDecorator
    private lateinit var sundayDecorator: DayViewDecorator
    private lateinit var saturdayDecorator: DayViewDecorator


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        /*// 달력 출력
        val calendarView = binding.calendarView
        calendarView.setTitleFormatter(
            MonthArrayTitleFormatter(getResources().getTextArray(
                R.array.custom_months))
        )

        calendarView.setWeekDayFormatter(ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)))*/

        initView()
//        initViewModel()

        return binding.root
    }

    private fun initView() = with(binding) {
        with(calendarView) {
            // 데코레이터 초기화
            dayDecorator = CalendarDecorators.dayDecorator(requireContext())
            todayDecorator = CalendarDecorators.todayDecorator(requireContext())
            sundayDecorator = CalendarDecorators.sundayDecorator()
            saturdayDecorator = CalendarDecorators.saturdayDecorator()
            selectedMonthDecorator = CalendarDecorators.selectedMonthDecorator(
                requireContext(),
                CalendarDay.today().month
            )

            // 캘린더뷰에 데코레이터 추가
            addDecorators(
                dayDecorator,
                todayDecorator,
                sundayDecorator,
                saturdayDecorator,
                selectedMonthDecorator
            )

            // 월 변경 리스너 설정
            setOnMonthChangedListener { widget, date ->
                // 캘린더 위젯에서 현재 선택된 날짜를 모두 선택 해제
                widget.clearSelection()
                // 캘린더 위젯에 적용된 모든 데코레이터 제거
                removeDecorators()
                // 데코레이터 제거 후 위젯 다시 그리기
                invalidateDecorators()
                // 새로운 월에 해당되는 데코레이터를 생성하여 selectedMonthDecorator에 할당
                selectedMonthDecorator =
                    CalendarDecorators.selectedMonthDecorator(requireContext(), date.month)
                // 새로 생성한 데코레이터를 캘린더 위젯에 추가
                addDecorators(
                    dayDecorator,
                    todayDecorator,
                    sundayDecorator,
                    saturdayDecorator,
                    selectedMonthDecorator
                )
                // 현재 월의 첫 번째 날을 나타내는 CalendarDay 객체 생성
                val clickedDay = CalendarDay.from(date.year, date.month, 1)
                // 캘린더 위젯에서 clickedDay를 선택하도록 지정
                widget.setDateSelected(clickedDay, true)
                /*// 변경된 일에 해당하는 일정 목록을 필터링하고 업데이트
                viewModel.filterScheduleListByDate(date.toLocalDate())
                // 변경된 월에 해당하는 일정 목록을 필터링하고 업데이트
                viewModel.filterDatabyMonth(date.toLocalDate())*/
            }

            // 요일 텍스트 포매터 설정
            setWeekDayFormatter(ArrayWeekDayFormatter(resources.getTextArray(R.array.custom_weekdays)))
            // 헤더 텍스트 모양 설정
            setHeaderTextAppearance(R.style.CalendarWidgetHeader)
            // 범위 선택 리스너 설정
            setOnRangeSelectedListener { widget, dates -> }
            /*// 날짜 변경 리스너 설정
            setOnDateChangedListener { widget, date, selected ->
                val localDate = date.toLocalDate()
                viewModel.filterScheduleListByDate(localDate)
            }*/
        }
    }

    /*private fun initViewModel() {
        viewModel.apply
    }*/

}

// MaterialCalendarView를 사용하기 위해 데코레이터 생성
object CalendarDecorators {

    // 날짜를 표시하는 데 사용되는 요소를 정의하기 위한 함수
    fun dayDecorator(context: Context): DayViewDecorator {
        return object : DayViewDecorator {
            private val drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector)

            override fun shouldDecorate(day: CalendarDay?): Boolean = true
            override fun decorate(view: DayViewFacade) {
                view.setSelectionDrawable(drawable!!)
            }
        }
    }

    // 현재 날짜를 다른 날짜와 구별하기 위해 스타일, 색상 적용
    fun todayDecorator(context: Context): DayViewDecorator {
        return object : DayViewDecorator {
            private val backgroundDrawable =
                ContextCompat.getDrawable(context, R.drawable.calendar_circle_today)
            private val today = CalendarDay.today()

            override fun shouldDecorate(day: CalendarDay?): Boolean = day == today
            override fun decorate(view: DayViewFacade?) {
                view?.apply {
                    setBackgroundDrawable(backgroundDrawable!!)
                    addSpan(
                        ForegroundColorSpan (
                            ContextCompat.getColor(
                                context,
                                R.color.selected_date_color
                            )
                        )
                    )
                }
            }
        }
    }

    // 현재 선택된 날 이외의 다른 달의 날짜의 모양을 변경
    fun selectedMonthDecorator(context: Context, selectedMonth: Int): DayViewDecorator {
        return object: DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay): Boolean = day.month != selectedMonth
            override fun decorate(view: DayViewFacade) {
                view.addSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            context,
                            R.color.enabled_date_color
                        )
                    )
                )
            }
        }
    }

    // 일요일을 담당하는 데코레이터
    fun sundayDecorator(): DayViewDecorator {
        return object: DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay): Boolean {
                val calendar = Calendar.getInstance()
                calendar.set(day.year, day.month-1, day.day)
                return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
            }

            override fun decorate(view: DayViewFacade) {
                view.addSpan(ForegroundColorSpan(Color.BLACK))
            }
        }
    }

    // 토요일을 담당하는 데코레이터
    fun saturdayDecorator() : DayViewDecorator {
        return object : DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay): Boolean {
                val calendar = Calendar.getInstance()
                calendar.set(day.year, day.month-1, day.day)
                return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
            }

            override fun decorate(view: DayViewFacade) {
                view.addSpan(ForegroundColorSpan(Color.BLACK))
            }
        }
    }

    /*// 이벤트가 있는 날짜를 표시하는 데코레이터를 생성하기 위한 함수
    fun eventDecorator(context: Context, receiptList: List<ReceiptModel>): DayViewDecorator {
        return object: DayViewDecorator {
            private val eventDates = HashSet<CalendarDay>()

            init {
                // 스케쥴 목록에서 이벤트가 있는 날짜를 파싱해 이벤트 날짜 목록에 추가
            }

            override fun shouldDecorate(day: CalendarDay?): Boolean {
                return eventDates.contains(day)
            }

            override fun decorate(view: DayViewFacade?) {
                view.addSpan(DotSpan(10F, ContextCompat.getColor(context, R.color.lavender)))
            }
        }
    }*/
}