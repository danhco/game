package com.data.dangtuan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  private String accountName;
  private String emailId;
  private String fullName;
  private String gender;
  private String birthday;
  private String telephone;
  private String email;

}
