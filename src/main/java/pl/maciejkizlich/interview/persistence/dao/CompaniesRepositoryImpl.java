package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pl.maciejkizlich.interview.persistence.model.Company;

@Repository
public class CompaniesRepositoryImpl extends AbstractModelRepository<Company> implements CompaniesRepository {

	protected CompaniesRepositoryImpl() {
		super(Company.class);
	}

	@Override
	public Company getCompanyById(long id) {
		return findById(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Company> findAll() {
        Query q = em.createQuery("SELECT q FROM Company q");
        return q.getResultList();
	}

	@Override
	public Company getCompanyByName(String name) {
		 Query q = em.createQuery("SELECT q FROM Company q where q.name = :companyName");
		 q.setParameter("companyName", name);
		 q.setMaxResults(1);
		 return JpaResultHelper.getSingleResultOrNull(q);
	}
	
	public void saveOrUpdate(Company company){
		if(null == company.getId()){
			em.persist(company);
		} else {
			em.merge(company);
		}
	}
}
