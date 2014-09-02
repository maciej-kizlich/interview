package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.maciejkizlich.interview.persistence.model.Authority;
import pl.maciejkizlich.interview.persistence.model.User;


@Repository
public class UserRepositoryImpl extends AbstractModelRepository<User> implements UserRepository {

	@Autowired
	AdminRepository adminRepository;
	
    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public User findUserByLogin(String login) {
        Query query = em.createQuery("SELECT u FROM Users u WHERE u.login = :login");
        query.setParameter("login", login);
        return JpaResultHelper.getSingleResultOrNull(query);
    }


    @Override
    public Collection<Authority> findAuthorities(Collection<String> authorities) {
        Query query = em.createQuery("SELECT auth FROM Authority auth WHERE auth.authority IN :authorities");

        query.setParameter("authorities", authorities);
        List<Authority> result = query.getResultList();

        return result;
    }

	@Override
	public Collection<User> findUsersByIds(Collection<Long> jobDatasource) {
	      Query query = em.createQuery("SELECT user FROM User user WHERE user.id IN :ids");

	        query.setParameter("ids", jobDatasource);
	        List<User> result = query.getResultList();

	        return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> findUsersWithOvertimeOrders() {
		
		Integer numberOfDays = Integer.valueOf(adminRepository.getConfigByName("block.user.reservation.days.after").getValue());
		DateTime dateInFuture = timeProvider.getCurrentTime().minusDays(numberOfDays);
		
		Query q = em.createQuery("SELECT user FROM BookOrder o WHERE o.expectedReturnDate <= :date and o.status = 'BORROWED'").setParameter("date", dateInFuture.toDate());
		
		return q.getResultList();
	}
}