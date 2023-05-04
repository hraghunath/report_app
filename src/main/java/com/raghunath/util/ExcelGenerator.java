package com.raghunath.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.raghunath.entity.CitizenPlan;

@Component
public class ExcelGenerator {
	
	public void generate(HttpServletResponse response,List<CitizenPlan> records) throws Exception {
		//Workbook workbook=new XSSFWorkbook();
		Workbook workbook=new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("plans_data");
			Row row = sheet.createRow(0);
			row.createCell(0).setCellValue("ID");
			row.createCell(1).setCellValue("Citizen Name");
			row.createCell(2).setCellValue("Plan Name");
			row.createCell(3).setCellValue("Plan Status");
			row.createCell(4).setCellValue("Plan Start Date");
			row.createCell(5).setCellValue("Plan End Date");
			
			row.createCell(6).setCellValue("Benefit Amt");
			
			int dataRowIndex=1;
			for(CitizenPlan plan: records) {
				Row dataRow = sheet.createRow(dataRowIndex);
				dataRow.createCell(0).setCellValue(plan.getCitizenId());
				dataRow.createCell(1).setCellValue(plan.getCitizenName());
				dataRow.createCell(2).setCellValue(plan.getPlanName());
				dataRow.createCell(3).setCellValue(plan.getPlanStatus());
				if(null!=plan.getPlanStartDate()) {
					dataRow.createCell(4).setCellValue(plan.getPlanStartDate()+"");

				}else {
					dataRow.createCell(4).setCellValue("N/A");

				}
				
				if(null!=plan.getPlanEndDate()) {
					dataRow.createCell(5).setCellValue(plan.getPlanEndDate()+"");

				}else {
					dataRow.createCell(5).setCellValue("N/A");

				}
				
				if(null !=plan.getBenefitAmt()) {
					dataRow.createCell(6).setCellValue(plan.getBenefitAmt());
				}else {
					dataRow.createCell(6).setCellValue("N/A");

				}
				
				dataRowIndex++;

			}
			//how to write data to the file this 
			//this is only for write the data into the excel file 
//			FileOutputStream fos=new FileOutputStream(new File("plans.xls"));
//			workbook.write(fos);
//			workbook.close();
			//this is for both download and write the data into the excelll file
			//to store the file into the server output file
			ServletOutputStream outputStream=response.getOutputStream();
			workbook.write(outputStream);
			workbook.close();
	}
	

}
