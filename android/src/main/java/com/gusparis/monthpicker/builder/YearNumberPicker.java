package com.gusparis.monthpicker.builder;

import android.os.Build;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
class YearNumberPicker extends MonthYearNumberPicker {

  private static final int DEFAULT_SIZE = 204;

  @Override
  YearNumberPicker onScrollListener(MonthYearScrollListener scrollListener) {
    yearPicker.setOnScrollListener(scrollListener);
    return this;
  }

  @Override
  YearNumberPicker build() {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(props.value());
    int year = calendar.get(Calendar.YEAR);
    yearPicker.setMinValue(year - DEFAULT_SIZE);
    yearPicker.setMaxValue(year + DEFAULT_SIZE);
    yearPicker.setValue(year);
    return this;
  }

  @Override
  synchronized void setValue() {
    int year = yearPicker.getValue();
    int value = year;

    if (Objects.nonNull(props.minimumValue())) {
      Calendar minCalendar = new GregorianCalendar();
      minCalendar.setTime(props.minimumValue());
      if (year < minCalendar.get(Calendar.YEAR)) {
        value = minCalendar.get(Calendar.YEAR);
      }
    }
    if (Objects.nonNull(props.maximumValue())) {
      Calendar maxCalendar = new GregorianCalendar();
      maxCalendar.setTime(props.maximumValue());
      if (year > maxCalendar.get(Calendar.YEAR)) {
        value = maxCalendar.get(Calendar.YEAR);
      }
    }
    yearPicker.setValue(value);
  }

  @Override
  int getValue() {
    return yearPicker.getValue();
  }
}
