package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.LoanStatus;
import com.revature.models.MortgageApplication;
import com.revature.repositories.MortgageApplicationRepository;

@Service
@Transactional
public class MortgageApplicationService {
	
	@Autowired
	private MortgageApplicationRepository mortgageApplicationRepository;

	public MortgageApplication createMortgage(MortgageApplication mortgage) {
		mortgage.setStatus(LoanStatus.PENDING);
		return mortgageApplicationRepository.save(mortgage);
	}
	
	public MortgageApplication approveDenyMortgage(Integer applicationId, String status) {
		Optional<MortgageApplication> tempOptionalMortgageApplication = mortgageApplicationRepository.findById(applicationId);
		MortgageApplication tempMortgageApplication = new MortgageApplication();
		if(tempOptionalMortgageApplication.isPresent()) {
			tempMortgageApplication = tempOptionalMortgageApplication.get();
			
			if(status.toUpperCase().equals("APPROVED")) tempMortgageApplication.setStatus(LoanStatus.APPROVED);
			if(status.toUpperCase().equals("DENIED")) tempMortgageApplication.setStatus(LoanStatus.DENIED);
				
			return mortgageApplicationRepository.save(tempMortgageApplication);
		}
		return null;
	}

	public MortgageApplication findMortgageByApplicationId(Integer applicationId) {
		Optional<MortgageApplication> tempMortgageApplication = mortgageApplicationRepository.findById(applicationId);
		if(tempMortgageApplication.isPresent()) {
			return tempMortgageApplication.get();
		}
		return null;
	}

}
