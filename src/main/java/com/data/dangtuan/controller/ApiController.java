package com.data.dangtuan.controller;

import com.data.dangtuan.dto.Count;
import com.data.dangtuan.service.GenerateReportService;
import com.data.dangtuan.service.impl.ReaderCSV;
import com.data.dangtuan.service.impl.WordCount;
import com.data.dangtuan.utils.constants.AppConstants;
import java.io.IOException;
import java.util.List;
import org.apache.spark.sql.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(AppConstants.BASE_PATH)
@Controller
public class ApiController {

  @Autowired
  WordCount wordCount;

  @Autowired
  private ReaderCSV readerCSV;

  @Autowired
  private GenerateReportService generateReportService;


  @GetMapping(value = AppConstants.WORLD_COUNTER_API)
  public ResponseEntity<List<Count>> words() {
    return new ResponseEntity<>(wordCount.count(), HttpStatus.OK);
  }


  @GetMapping(value = AppConstants.READ_CSV_API)
  public ResponseEntity<String> readCSV() throws IOException {
    return new ResponseEntity<>(readerCSV.runAnalyzeBasicDataSource(), HttpStatus.OK);
  }

  @GetMapping(value = AppConstants.GET_TOP_API)
  public ResponseEntity<Dataset<String>> getTopN(@RequestParam final Long limit) {
    return new ResponseEntity<>(generateReportService.getTopNCustomers(limit), HttpStatus.OK);
  }
}
