package com.gusparis.monthpicker.builder;

import android.os.Build;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
class MonthNumberPicker extends MonthYearNumberPicker {

  private final String[] MONTHS = new DateFormatSymbols().getShortMonths();

  @Override
  MonthNumberPicker onScrollListener(MonthYearScrollListener scrollListener) {
    monthPicker.setOnScrollListener(scrollListener);
    return this;
  }

  @Override
  MonthNumberPicker build() {
    monthPicker.setMinValue(0);
    monthPicker.setMaxValue(11);
    monthPicker.setDisplayedValues(MONTHS);
    monthPicker.setWrapSelectorWheel(false);
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(props.value());
    monthPicker.setValue(calendar.get(Calendar.MONTH));
    return this;
  }

  @Override
  synchronized void setValue() {
    int month = monthPicker.getValue();
    int year = yearPicker.getValue();
    int value = month;
    if (Objects.nonNull(props.minimumValue())) {
      Calendar minCalendar = new GregorianCalendar();
      minCalendar.setTime(props.minimumValue());
      if (year == minCalendar.get(Calendar.YEAR) && month < minCalendar.get(Calendar.MONTH)) {
        value = minCalendar.get(Calendar.MONTH);
      }
    }
    if (Objects.nonNull(props.maximumValue())) {
      Calendar maxCalendar = new GregorianCalendar();
      maxCalendar.setTime(props.maximumValue());
      if (year == maxCalendar.get(Calendar.YEAR) && month > maxCalendar.get(Calendar.MONTH)) {
        value = maxCalendar.get(Calendar.MONTH);
      }
    }
    monthPicker.setValue(value);
  }

  @Override
  int getValue() {
    return monthPicker.getValue();
  }
}
