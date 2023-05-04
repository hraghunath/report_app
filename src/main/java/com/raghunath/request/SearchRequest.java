package com.raghunath.request;

import java.time.LocalDate;

import lombok.Data;

//search request is a form binding class
//what is the meaning of form binding
//when user select the value and click on the searech bottom ,
//i should be able to capture the data in the form of object
//for capturing the form data 
@Data
public class SearchRequest {
	
	private String planName;
	private String planStatus;
	private String gender;
	private String startDate;
	private String endDate;

}
