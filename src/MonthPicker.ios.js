import React from 'react';
import {
  View,
  StyleSheet,
  Dimensions,
  Text,
  NativeModules,
} from 'react-native';
import invariant from 'invariant';

import RNMonthPickerView from './RNMonthPickerNativeComponent';
import { ACTION_DATE_SET, ACTION_DISMISSED, useTheme } from './utils';

const { width } = Dimensions.get('screen');

const styles = StyleSheet.create({
  pickerContainer: { height: 200, minWidth: 315 },
});

class MonthPicker extends React.PureComponent {
  state = {
    currentDate: this.props.value,
  };

  getLongFromDate = (selectedValue) => selectedValue.valueOf();

  onValueChange = (event) => {
    const date = event.nativeEvent.newDate;
    this.setState({ currentDate: date });
  };

  render() {
    const {
      value,
      minimumDate,
      maximumDate,
      enableAutoDarkMode = true,
    } = this.props;
    invariant(value, 'value prop is required!');

    return (
      <View style={styles.pickerContainer}>
        <RNMonthPickerView
          style={styles.picker}
          onChange={this.onValueChange}
          value={this.getLongFromDate(value)}
          minimumDate={this.getLongFromDate(minimumDate)}
          maximumDate={this.getLongFromDate(maximumDate)}
          enableAutoDarkMode={enableAutoDarkMode}
        />
      </View>
    );
  }
}

export default MonthPicker;
