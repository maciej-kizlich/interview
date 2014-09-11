package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.Company;

public interface CompaniesRepository {

	Company getCompanyById(long id);
	
	Company getCompanyByName(String name);
	
	Collection<Company> findAll();
	
	void saveOrUpdate(Company company);
	
}
