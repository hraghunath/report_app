package com.raghunath.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.raghunath.entity.CitizenPlan;

public interface CitizenPlanRepository extends JpaRepository<CitizenPlan, Integer> {
	
	@Query("select distinct(planName) from CitizenPlan")
	public List<String> getPlanName();
	@Query("select distinct(planStatus) from CitizenPlan")
	public List<String> getPlanStatus();
	
	//here we write some custome query //sql querry//not nativ sql query
	//to pre-populate the data in the jsp page we write the custoem query in jsp page 
	
	

}
