package pl.maciejkizlich.interview.services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.maciejkizlich.interview.persistence.dao.CompaniesRepository;
import pl.maciejkizlich.interview.persistence.model.Company;

@Service
@Transactional
public class CompaniesServiceImpl implements CompaniesService{

	@Autowired
	private CompaniesRepository companiesRepository;
	
	@Override
	public Company getCompanyById(long companyId) {
		return companiesRepository.getCompanyById(companyId);
	}

	@Override
	public Collection<Company> findAll() {
		return companiesRepository.findAll();
	}
}
