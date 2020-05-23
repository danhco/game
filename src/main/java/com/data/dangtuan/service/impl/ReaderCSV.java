package com.data.dangtuan.service.impl;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.unix_timestamp;

import com.data.dangtuan.utils.Utils;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ReaderCSV {

  @Autowired
  private SparkSession sparkSession;

  @Autowired
  private ApplicationContext applicationContext;

  @Value("${spark.jdbc.url}")
  private String sparkURL;

  @Value("${spark.schema}")
  private String sparkSchema;

  @Value("${spark.user}")
  private String sparkUser;

  @Value("${spark.password}")
  private String sparkPass;

  @Value("${spark.jdbc}")
  private String sparkReaderURL;

  @Value("${file.path}")
  private String filePath;


  public String runAnalyzeBasicDataSource() throws IOException {
    String status = "File imported : ";
    try {
      Resource[] resources = applicationContext.getResources(filePath);
      for (int i = 0; i < resources.length; i++) {
        if (resources[i].getFile().exists()) {
          String absolutePath = resources[i].getFile().getAbsolutePath();
          processDataSet(absolutePath);
          status = status + ", " + absolutePath;
        }
      }
    } catch (Exception e) {
      log.error("Error : " + ExceptionUtils.getStackTrace(e));
      return "Error!";
    }
    return status;
  }


  public void insertDataThroughJdbcDataset(Dataset<Row> jdbcDF2) {
    jdbcDF2.write().mode(SaveMode.Append)
        .jdbc(sparkURL, sparkSchema, Utils.initConnection(sparkUser, sparkPass));
  }


  public Dataset<Row> readJdbcDataset() {
    Dataset<Row> jdbcDF2 = sparkSession.read().jdbc(
        sparkReaderURL,
        sparkSchema,
        Utils.initConnection(sparkUser, sparkPass));
    return jdbcDF2;
  }


  private void processDataSet(String absolutePathFile) {
    try {
      Dataset<Row> peopleDFCsv = sparkSession.read().format("csv").option("sep", ",")
          .option("inferSchema", "true").option("header", "true").load(
              absolutePathFile).filter("email LIKE '%@gmail.com%' OR email LIKE '%@hotmail%'")
          .select(
              "accountname",
              "email",
              "fullname",
              "gender",
              "birthday",
              "telephone",
              "address",
              "email2").withColumn("birthday",
              unix_timestamp(col("birthday"), "yyyy-MM-dd HH:mm:ss").cast("timestamp"));
      peopleDFCsv.show();
      insertDataThroughJdbcDataset(peopleDFCsv);
    } catch (Exception e) {
      log.error("File : " + absolutePathFile + "  Error : {}" + ExceptionUtils.getStackTrace(e));
    }
  }

}
