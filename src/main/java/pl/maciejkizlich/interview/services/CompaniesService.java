package pl.maciejkizlich.interview.services;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.Company;

public interface CompaniesService {

	Company getCompanyById(long companyId);
	
	Collection<Company> findAll(); 

}
