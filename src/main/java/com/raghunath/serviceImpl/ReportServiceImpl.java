package com.raghunath.serviceImpl;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.raghunath.entity.CitizenPlan;
import com.raghunath.repository.CitizenPlanRepository;
import com.raghunath.request.SearchRequest;
import com.raghunath.service.ReportService;
import com.raghunath.util.ExcelGenerator;
import com.raghunath.util.PdfGenerator;

@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private CitizenPlanRepository planRepo;
	
	@Autowired
	private ExcelGenerator excelGenerator;
	
	@Autowired
	private PdfGenerator pdfGenerator;
	
	@Override
	public List<String> getPlanNames() {
		
		List<String> planNames = planRepo.getPlanName();
		return planNames;
	}

	@Override
	public List<String> getPlanStatuses() {
		
		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {
		//here service method is not applaying the filter
		//it just retriving all the record 
		//so here we implement dynamic query
		//by using query by example
		//form data is present in request object 
		//for example i need entity to copy the objec from binding object to entity object
		//so we need to create entity object
		/*here request object contain empty value 
		 and entity object contain null value
		 */
		CitizenPlan entity=new CitizenPlan();
		//then copy the object form binding object to entity object we used.
		
		//now we can not append empty value to the query because we put the condtion
		//if the binding object is not null and not empty then only that value shuld be 
		//loaded into the entity object.
		if(null!=request.getPlanName() && !"".equals(request.getPlanName())){
			entity.setPlanName(request.getPlanName());
		}
		if(null!=request.getGender() && !"".equals(request.getGender())){
			entity.setGender(request.getGender());;
		}
		if(null!=request.getPlanStatus() && !"".equals(request.getPlanStatus())){
			entity.setPlanStatus(request.getPlanStatus());;
		}
		if(null !=request.getStartDate() && !"".equals(request.getStartDate())) {
			String startDate=request.getStartDate();
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			//convert String to localDate
			LocalDate localDate = LocalDate.parse(startDate,formater);
			entity.setPlanStartDate(localDate);
		}
		if(null !=request.getEndDate() && !"".equals(request.getEndDate())) {
			String endDate=request.getEndDate();
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			//convert String to localDate
			LocalDate localDate = LocalDate.parse(endDate,formater);
			entity.setPlanEndDate(localDate);
		}
		
		//req""uest is the source object and entity is the target object.
		Example<CitizenPlan> filterData = Example.of(entity);
		
		return planRepo.findAll(filterData);
	}

	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception {
		
		List<CitizenPlan> plans = planRepo.findAll();
		excelGenerator.generate(response,plans);
		return true;
	}

	

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {
		List<CitizenPlan> plans = planRepo.findAll();
		pdfGenerator.generate(response, plans);
		return true;
	}

	
	


}
