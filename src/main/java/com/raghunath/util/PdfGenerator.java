package com.raghunath.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.lowagie.text.Document;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.raghunath.entity.CitizenPlan;

@Component
public class PdfGenerator {
	
	public void generate(HttpServletResponse response,	List<CitizenPlan> plans) throws Exception {
		Document document=new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		  // Creating font
	    // Setting font style and size
	    Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
	    fontTiltle.setSize(20);
	    // Creating paragraph
	    Paragraph paragraph1 = new Paragraph("Citizen Plans", fontTiltle);
	    // Aligning the paragraph in the document
	    paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
	    // Adding the created paragraph in the document
	    document.add(paragraph1);		
		
						
		PdfPTable table=new PdfPTable(6);
		table.setSpacingBefore(5);
		table.addCell("Id");
		table.addCell("Citizen Name");
		table.addCell("Plan Name");
		table.addCell("Plan Status");
		table.addCell("Start Date");
		table.addCell("End Date");

	 	
		
		for(CitizenPlan plan:plans) {
			table.addCell(String.valueOf(plan.getCitizenId()));
			table.addCell(plan.getCitizenName());
			table.addCell(plan.getPlanName());
			table.addCell(plan.getPlanStatus());
			if(null!=plan.getPlanStartDate()) {
				table.addCell(plan.getPlanStartDate()+"");

			}else {
				table.addCell("N/A");

			}
			
			if(null!=plan.getPlanEndDate()) {
				table.addCell(plan.getPlanEndDate()+"");

			}else {
				table.addCell("N/A");

			}
			
		}



		document.add(table);
		document.close();
	}

}
