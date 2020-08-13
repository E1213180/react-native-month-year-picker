import invariant from 'invariant';

import RNMonthPickerDialogModule from './RNMonthPickerDialogModule';
import { ACTION_DATE_SET, ACTION_DISMISSED } from './utils';

const MonthPicker = ({
  value,
  onChange,
  minimumDate,
  maximumDate,
  okButton = 'OK',
  cancelButton = 'Cancel',
  enableAutoDarkMode = true,
}) => {
  invariant(value, 'value prop is required!');

  const getLongFromDate = (selectedValue) => selectedValue.valueOf();

  RNMonthPickerDialogModule.open({
    value: getLongFromDate(value),
    minimumDate: getLongFromDate(minimumDate),
    maximumDate: getLongFromDate(maximumDate),
    okButton,
    cancelButton,
    enableAutoDarkMode,
  }).then(
    function resolve({ action, year, month }) {
      let date;
      switch (action) {
        case ACTION_DATE_SET:
          date = new Date(year, month);
          break;
        case ACTION_DISMISSED:
        default:
          date = undefined;
      }
      onChange(action, date);
    },
    function reject(error) {
      throw error;
    },
  );

  return null;
};

export default MonthPicker;
