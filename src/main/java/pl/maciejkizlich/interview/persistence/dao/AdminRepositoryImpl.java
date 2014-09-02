package pl.maciejkizlich.interview.persistence.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pl.maciejkizlich.interview.persistence.model.AsyncJobError;
import pl.maciejkizlich.interview.persistence.model.LibraryConfig;

@Repository
public class AdminRepositoryImpl implements AdminRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<LibraryConfig> getLibraryConfig() {
		Query q = em.createQuery("SELECT config FROM LibraryConfig config");
		return q.getResultList();
	}

	@Override
	public void saveConfig(Set<LibraryConfig> configs) {
		for (LibraryConfig config : configs) {
			em.merge(config);
		}
	}

	@Override
	public LibraryConfig getConfigByName(String key) {
		Query q = em
				.createQuery(
						"SELECT config FROM LibraryConfig config WHERE config.key = :key")
				.setParameter("key", key).setMaxResults(1);
		return JpaResultHelper.getSingleResultOrNull(q);
		
	}

	@Override
	public void saveAsyncJobError(AsyncJobError error) {
		em.merge(error);
	}
}
