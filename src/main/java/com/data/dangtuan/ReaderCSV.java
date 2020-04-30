package com.data.dangtuan;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.unix_timestamp;

import java.io.IOException;
import java.util.Properties;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ReaderCSV {

  private Logger log = Logger.getLogger(ReaderCSV.class);

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


  public String runAnalyzeBasicDataSource() throws IOException {
    String status = "File imported : ";
    try {
      Resource[] resources = applicationContext.getResources(
          "file:/Users/tuan.nguyen.oddle/Documents/vnp1/data/*");
      for (int i = 0; i < resources.length; i++) {
        if (resources[i].getFile().exists()) {
          String absolutePath = resources[i].getFile().getAbsolutePath();
          processDataSet(absolutePath);
          status = status + ", " + absolutePath;
        }
      }
      // String realPath = "classpath:/000000_0.csv";
      // Resource res = resourceLoader.getResource(realPath);
      // Dataset<Row> peopleDFCsv = sparkSession.read().format("csv").option("sep", ",").option("inferSchema", "true").option("header", "true").load(
      // res.getFile().getAbsolutePath()).filter("email LIKE '%@gmail.com%' OR email LIKE '%@hotmail%'").select(
      // "accountname",
      // "email",
      // "fullname",
      // "gender",
      // "birthday",
      // "telephone",
      // "address",
      // "email2");
      // peopleDFCsv.show();
      // insertDataThroughJdbcDataset(peopleDFCsv);
    } catch (Exception e) {
      log.error("Error : " + ExceptionUtils.getStackTrace(e));
      return "Error!";
    }
    return status;
  }


  public void insertDataThroughJdbcDataset(Dataset<Row> jdbcDF2) {
    jdbcDF2.write().mode(SaveMode.Append)
        .jdbc(sparkURL, sparkSchema, initConnection(sparkUser, sparkPass));
  }


  public Dataset<Row> readJdbcDataset() {
    Dataset<Row> jdbcDF2 = sparkSession.read().jdbc(
        sparkReaderURL,
        sparkSchema,
        initConnection(sparkUser, sparkPass));
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


  private Properties initConnection(String userName, String password) {
    Properties connectionProperties = new Properties();
    connectionProperties.put("user", userName);
    connectionProperties.put("password", password);
    return connectionProperties;
  }
}
