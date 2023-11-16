package com.example.org.template.excel.service;

import com.example.org.template.excel.model.Organisation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExcelService {

    private static final Logger log = LoggerFactory.getLogger(ExcelService.class);

    public List<Map<String, String>> parseExcel(MultipartFile file) throws Exception {
        Scanner sc;
        if (!Objects.nonNull(file) && file.getName().contains(".xlsx")) {

        }
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            int visibleIndex = 0;
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                if (!workbook.isSheetVeryHidden(i)) {
                    visibleIndex = i;
                    break;
                }
            }

            Sheet sheet = workbook.getSheetAt(visibleIndex);
            if (Objects.isNull(sheet)) {

            }
            int totalRows = sheet.getLastRowNum() + 1;
            log.info("Total no of rows in excel are : [{}]", totalRows);
            Row headerRow = sheet.getRow(0);

            if (Objects.isNull(headerRow)) {
                throw new Exception("Headers can't be kept empty");
            }

            if (sheet.getPhysicalNumberOfRows() < 2) {
                throw new Exception("Empty Excel is uploaded");
            }
            HashMap<String, Integer> map = new HashMap<>();
            log.info("##### Reading Headers #######");
            short minColIndex = headerRow.getFirstCellNum();
            short maxColIndex = headerRow.getLastCellNum();
            DataFormatter formatter = new DataFormatter();
            List<String> headers = new ArrayList<>();
            for (short colIx = minColIndex; colIx < maxColIndex; colIx++) {
                Cell cell = headerRow.getCell(colIx);
                if (cell != null) {
                    log.info("##### Header : Name:{}, Column Index:{} #######", formatter.formatCellValue(cell).trim(), cell.getColumnIndex());
                    String header = formatter.formatCellValue(cell);
                    map.put(header, cell.getColumnIndex());
                    headers.add(header);
                }
            }
            List<Map<String, String>> result = new ArrayList<>();
            for (int i = 1; i < totalRows; i++) {

                Row dataRow = sheet.getRow(i);
                HashMap<String, String> rowMap = new HashMap<>();
                headers.stream().forEach(header -> {
                    int index = map.get(header);
                    if (Objects.nonNull(dataRow.getCell(index))) {
                        rowMap.put(header, String.valueOf(dataRow.getCell(index)));
                    }

                });
                result.add(rowMap);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void downloadTemplate(HttpServletResponse response, Organisation organisation) throws IOException {
        log.info("Creating empty Excel ");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("template");
        Row headerRow = sheet.createRow(0);
        List<String> columns = Arrays.asList(organisation.getTemplateCol().split("\\^"));
        AtomicInteger index = new AtomicInteger(0);
        columns.stream().forEach(col -> {
            headerRow.createCell(index.getAndIncrement()).setCellValue(col);
        });
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template_"+System.currentTimeMillis()+".xlsx");
        response.setContentType("application/vnd.ms-excel");
        workbook.write(response.getOutputStream());
        workbook.close();
            
    }
}
