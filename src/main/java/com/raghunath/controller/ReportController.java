package com.raghunath.controller;



import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.raghunath.entity.CitizenPlan;
import com.raghunath.request.SearchRequest;
import com.raghunath.service.ReportService;

@Controller
public class ReportController {
	
	@Autowired
	private ReportService service;
	
	@GetMapping("/pdf")
	public void pdfExport(HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		//i want to send the excel file into the brower in the response object thatw why 
		//i am taking the response object as a parameter
		response.addHeader("Content-Disposition", "attachment;filename=plans.pdf");
		//to give the information to the broser so that we can download the excel we are setting the header like this 

		service.exportPdf(response);
	}
	
	@GetMapping("/excel")
	public void excelExport(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		//i want to send the excel file into the brower in the response object thatw why 
		//i am taking the response object as a parameter
		response.addHeader("Content-Disposition", "attachment;filename=plans.xls");
		//to give the information to the broser so that we can download the excel we are setting the header like this 
		service.exportExcel(response);
	}
	
	@PostMapping("/searched")
	public String handleSearh(SearchRequest search ,Model model) {
		System.out.println(search);
		
		List<CitizenPlan> plans = service.search(search);
		model.addAttribute("plans", plans);
		model.addAttribute("search", search);
		//whatever the form data i am getting in the object same object i am settig back 
		//to the UI 
		//Whenever PostMethod  is availabe i am sending binding data back to the form
		//because i am sending binding object back to the UI
		init(model);
		return "index";
	}
	
	@GetMapping("/")
	public String indexPages(Model model) {
//		SearchRequest searchObj=new SearchRequest();
//		model.addAttribute("search", searchObj);
//		List<String> planNames = service.getPlanNames();
//		List<String> planStatuses = service.getPlanStatuses();
//		model.addAttribute("names", planStatuses);
//		model.addAttribute("status", planNames);
		
		model.addAttribute("search",new SearchRequest());

		init(model);
		
		return "index";
	}

	private void init(Model model) {
		//model.addAttribute("search",new SearchRequest());

		model.addAttribute("names", service.getPlanNames());
		model.addAttribute("status", service.getPlanStatuses());
	}

}
