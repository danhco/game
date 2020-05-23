package com.data.dangtuan.utils.constants;

/**
 * This class provides String constants for swagger .
 */
public class SwaggerConstants {

  private SwaggerConstants() {

  }

  public static final String SEPARATOR = "/";
  public static final String PATH_REGEX = "/*.*";
  public static final String API_VERSION = AppConstants.API_VERSION;
  public static final String BASE_PACKAGE = "com.data.dangtuan";
  public static final String COMPANY_NAME = "DangTuan";
  public static final String EMAIL = "tuan193@gmail.com";
  public static final String ENDPOINTS_FOR_SWAGGER = SEPARATOR + AppConstants.API_VERSION + ".*";
  public static final String LICENSE_STR = "License";
  public static final String REPORTS_SERVICE_TITLE = "Export Command Service";
  public static final String REPORTS_SERVICE_DESC = "Export Command APIs.";
  public static final String WEBSITE = "https://www.linkedin.com/in/tuan-nguyen-dang-b97b8366/";
  public static final String SWAGGER_REDIRECT_URL = "redirect:/swagger-ui.html";

  public static final String ACCESS_TOKEN_HEADER_NAME = "x-access-token";
  public static final String ACCESS_TOKEN_HEADER = "header";

  public static final String AUTHORIZATION_SCOPE = "global";
}
