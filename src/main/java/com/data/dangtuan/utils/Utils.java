package com.data.dangtuan.utils;

import com.data.dangtuan.utils.constants.AppConstants;
import java.util.Properties;

public class Utils {

  public static Properties initConnection(final String userName, final String password) {
    Properties connectionProperties = new Properties();
    connectionProperties.put(AppConstants.USER_NAME, userName);
    connectionProperties.put(AppConstants.PASS_WORD, password);
    return connectionProperties;
  }
}
