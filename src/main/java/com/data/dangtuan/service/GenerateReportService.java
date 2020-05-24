package com.data.dangtuan.service;

import com.data.dangtuan.dto.CustomerNotification;
import java.util.List;

public interface GenerateReportService {

  /**
   * Get top of customer
   *
   * @param top number customer
   * @return customer
   */
  List<CustomerNotification> getTopNCustomers(final Long top);
}
