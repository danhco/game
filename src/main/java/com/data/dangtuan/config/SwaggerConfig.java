package com.data.dangtuan.config;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.data.dangtuan.utils.constants.SwaggerConstants;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API document configuration.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(SwaggerConstants.BASE_PACKAGE))
        .paths(PathSelectors.regex(SwaggerConstants.ENDPOINTS_FOR_SWAGGER))
        .build().apiInfo(apiInformation())
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()));
  }

  private ApiInfo apiInformation() {
    final Contact contact = new Contact(SwaggerConstants.COMPANY_NAME, SwaggerConstants.WEBSITE,
        SwaggerConstants.EMAIL);

    return new ApiInfoBuilder().title(SwaggerConstants.REPORTS_SERVICE_TITLE)
        .description(SwaggerConstants.REPORTS_SERVICE_DESC)
        .version(SwaggerConstants.API_VERSION)
        .contact(contact)
        .license(SwaggerConstants.LICENSE_STR).build();
  }

  @Bean
  public SecurityConfiguration security() {
    return SecurityConfigurationBuilder.builder()
        .clientId(null)
        .clientSecret(null)
        .scopeSeparator(null)
        .useBasicAuthenticationWithAccessCodeGrant(true)
        .build();
  }

  private ApiKey apiKey() {
    return new ApiKey(AUTHORIZATION, SwaggerConstants.ACCESS_TOKEN_HEADER_NAME,
        SwaggerConstants.ACCESS_TOKEN_HEADER);
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(PathSelectors.regex(SwaggerConstants.PATH_REGEX))
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    final AuthorizationScope authorizationScope = new AuthorizationScope(
        SwaggerConstants.AUTHORIZATION_SCOPE, SwaggerConstants.AUTHORIZATION_SCOPE);
    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference(AUTHORIZATION, authorizationScopes));
  }
}
