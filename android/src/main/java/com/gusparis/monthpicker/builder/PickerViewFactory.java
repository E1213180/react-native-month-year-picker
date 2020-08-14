package com.gusparis.monthpicker.builder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import com.gusparis.monthpicker.R;
import com.gusparis.monthpicker.RNMonthPickerDialog;
import com.gusparis.monthpicker.adapter.RNMonthPickerProps;

import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

public class PickerViewFactory {

  private static final int DARK_VIEW = R.layout.picker_list_view_dark;
  private static final int LIGHT_VIEW = R.layout.picker_list_view;
  private RNMonthPickerProps props;
  private RNMonthPickerDialog rnMonthPickerDialog;

  public PickerViewFactory(RNMonthPickerProps props, RNMonthPickerDialog rnMonthPickerDialog) {
    this.props = props;
    this.rnMonthPickerDialog = rnMonthPickerDialog;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public AlertDialog build() {
    FragmentActivity fragmentActivity = Objects.requireNonNull(rnMonthPickerDialog.getActivity());
    int uiMode = fragmentActivity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    int dialogStyle = R.style.LightStyle;
    int contentViewStyle = R.layout.picker_list_view;
    boolean darkMode = uiMode == Configuration.UI_MODE_NIGHT_YES && props.enableAutoDarkMode();
    final int buttonFG = darkMode ? Color.parseColor("#FFFFFF") : Color.parseColor("#1F1B24");
    final int buttonBG = darkMode ? Color.parseColor("#1F1B24") : Color.parseColor("#FFFFFF");

    if (darkMode) {
      dialogStyle = R.style.DarkStyle;
      contentViewStyle = R.layout.picker_list_view_dark;
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(rnMonthPickerDialog.getActivity(), dialogStyle);
    LayoutInflater inflater = fragmentActivity.getLayoutInflater();
    View contentView = inflater.inflate(contentViewStyle, null);

    MonthYearScrollListener scrollListener = new MonthYearScrollListener();
    final MonthYearNumberPicker monthPicker = new MonthNumberPicker()
        .view(contentView)
        .props(props)
        .onScrollListener(scrollListener)
        .build();
    final MonthYearNumberPicker yearPicker = new YearNumberPicker()
        .view(contentView)
        .props(props)
        .onScrollListener(scrollListener)
        .build();

    scrollListener.addObserver(monthPicker);
    scrollListener.addObserver(yearPicker);

    builder.setView(contentView)
        .setPositiveButton(props.okButton(), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            props.onChange(yearPicker.getValue(), monthPicker.getValue());
            rnMonthPickerDialog.getDialog().cancel();
          }
        })
        .setNegativeButton(props.cancelButton(), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            rnMonthPickerDialog.getDialog().cancel();
          }
        });

    final AlertDialog dialog = builder.create();
    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
      @Override
      public void onShow(DialogInterface arg0) {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(buttonFG);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(buttonFG);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(buttonBG);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(buttonBG);
      }
    });

    return dialog;
  }
}
