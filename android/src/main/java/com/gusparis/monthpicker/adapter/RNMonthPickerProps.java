package com.gusparis.monthpicker.adapter;

import java.util.Date;

public interface RNMonthPickerProps {

  Date value();

  Date minimumValue();

  Date maximumValue();

  String okButton();

  String cancelButton();

  Boolean enableAutoDarkMode();

  void onChange(int year, int month);

  void onChange();
}
