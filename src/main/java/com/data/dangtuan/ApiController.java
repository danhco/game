package com.data.dangtuan;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api")
@Controller
public class ApiController {

  @Autowired
  WordCount wordCount;

  @Autowired
  private ReaderCSV readerCSV;


  @RequestMapping("wordcount")
  public ResponseEntity<List<Count>> words() {
    return new ResponseEntity<>(wordCount.count(), HttpStatus.OK);
  }


  @RequestMapping("readcsv")
  public ResponseEntity<String> readcsv() throws IOException {
    return new ResponseEntity<>(readerCSV.runAnalyzeBasicDataSource(), HttpStatus.OK);
  }
}
