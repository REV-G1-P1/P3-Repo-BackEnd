package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.LoanStatus;
import com.revature.models.MortgageApplication;
import com.revature.models.User;
import com.revature.repositories.MortgageApplicationRepository;
import com.revature.repositories.UserRepository;

@Service
@Transactional
public class MortgageApplicationService {
	
	@Autowired
	private MortgageApplicationRepository mortgageApplicationRepository;
	
	@Autowired
    private UserRepository userRepository;

	public void createMortgage(MortgageApplication mortgage, User user) {
        int randomId = (int) ((Math.random() * (99999999 - 11111111)) + 11111111);
        mortgage.setApplicationId(randomId);
		mortgage.setStatus(LoanStatus.PENDING);
        mortgage.setFirstName(user.getFirstName());
        mortgage.setLastName(user.getLastName());
        mortgage.setSsn(user.getSSN());
		List<MortgageApplication> applications = user.getMortgageApplication();
		applications.add(mortgage);
		user.setMortgageApplication(applications);
		userRepository.save(user);
	}
	
	public void createMortgage(MortgageApplication mortgage) {
        int randomId = (int) ((Math.random() * (99999999 - 11111111)) + 11111111);
        mortgage.setApplicationId(randomId);
		mortgage.setStatus(LoanStatus.PENDING);
		mortgageApplicationRepository.save(mortgage);
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
	
	public List<MortgageApplication> findAllApplications() {
        return mortgageApplicationRepository.findAll();
    }

}
