package com.swarawan.try1.ui.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import com.swarawan.corelibrary.base.CoreActivity
import com.swarawan.corelibrary.extensions.safeDispose
import com.swarawan.try1.R
import com.swarawan.try1.RosidFoodApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : CoreActivity() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var loadSubject: BehaviorSubject<Calendar> = BehaviorSubject.create()
    private var currentCalendar: Calendar = Calendar.getInstance()
    private val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    companion object {
        private const val DEBOUNCE_INTERVAL: Long = 500
    }

    override fun initInjection() {
        (application as RosidFoodApplication).defaultComponent.inject(this@MainActivity)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        imageArrowLeft.setOnClickListener { selectedDate(-1) }
        imageArrowRight.setOnClickListener { selectedDate(1) }
        textDate.setOnClickListener { showPickerDialog() }
    }

    override fun setLayout() {
        setContentView(R.layout.activity_main)
    }

    private fun selectedCalendarDate(year: Int, month: Int, dayOfMonth: Int) {
        currentCalendar.set(year, month, dayOfMonth)
        textDate.text = format.format(currentCalendar.time)
        loadData()
    }

    private fun selectedDate(amount: Int) {
        currentCalendar.add(Calendar.DATE, amount)
        textDate.text = format.format(currentCalendar.time)
        loadSubject.onNext(currentCalendar)
    }

    private fun showPickerDialog() {
        val year = currentCalendar.get(Calendar.YEAR)
        val month = currentCalendar.get(Calendar.MONTH)
        val day = currentCalendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(this@MainActivity, R.style.DatePickerStyle, { _, y, m, d ->
            selectedCalendarDate(y, m, d)
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun loadData() {

    }

    private fun subscribeLoadDate() {
        compositeDisposable.let {
            it.clear()
            it.add(loadSubject.debounce(DEBOUNCE_INTERVAL, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe { loadData() })
        }
    }


    override fun onDestroy() {
        compositeDisposable.safeDispose()
        super.onDestroy()
    }
}
