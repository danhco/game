package com.data.dangtuan.controller;

import com.data.dangtuan.utils.constants.AppConstants;
import com.data.dangtuan.utils.constants.SwaggerConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller class handles swagger ui request mapping
 */
@Controller
public class SwaggerController {

  @RequestMapping(value = AppConstants.SEPARATOR, method = RequestMethod.GET)
  public String swagger() {
    return SwaggerConstants.SWAGGER_REDIRECT_URL;
  }
}
