package com.data.dangtuan.service;

import org.apache.spark.sql.Dataset;

public interface GenerateReportService {

  /**
   * Get top of customer
   *
   * @param top number customer
   * @return customer
   */
  Dataset<String> getTopNCustomers(final Long top);
}
