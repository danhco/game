package com.data.dangtuan.service.impl;

import com.data.dangtuan.service.GenerateReportService;
import com.data.dangtuan.utils.Utils;
import com.data.dangtuan.utils.constants.AppConstants;
import java.util.Objects;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GenerateReportServiceImpl implements GenerateReportService {

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

  @Autowired
  private SparkSession spark;

  public Dataset<String> getTopNCustomers(final Long top) {
    Dataset<Row> jdbcDF2 = spark.read()
        .jdbc(sparkURL, sparkSchema, Utils.initConnection(sparkUser, sparkPass));

    jdbcDF2.createOrReplaceTempView("customer");
    final String limit = Objects.nonNull(top) ? "limit " + top : ";";
    Dataset<Row> teenagersDF = spark
        .sql("SELECT * FROM customer " + limit);

    Encoder<String> stringEncoder = Encoders.STRING();
    Dataset<String> teenagerNamesByIndexDF = teenagersDF.map(
        (MapFunction<Row, String>) row -> "Name: " + row.getString(0),
        stringEncoder);

    teenagerNamesByIndexDF.show();

    return teenagerNamesByIndexDF.toJSON();
  }

  public void writeCustomer(final Dataset<Row> jdbcDF, final String schemaTablename) {
    jdbcDF.write()
        .jdbc(sparkURL, schemaTablename,
            Utils.initConnection(sparkUser, sparkPass));
  }

  public Dataset<Row> getReaderSchemaRuntime(final String sparkSchema) {
    return spark.read()
        .format(AppConstants.JDBC)
        .option(AppConstants.URL, sparkURL)
        .option(AppConstants.DBTABLE, sparkSchema)
        .option(AppConstants.USER_NAME, sparkUser)
        .option(AppConstants.PASS_WORD, sparkPass)
        .load();
  }

}
