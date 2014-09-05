package pl.maciejkizlich.interview.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.maciejkizlich.interview.persistence.dao.AdminRepository;
import pl.maciejkizlich.interview.persistence.model.LibraryConfig;
import pl.maciejkizlich.interview.scheduler.CronJobsExecutor;
import pl.maciejkizlich.interview.spring.postprocessors.CronJobsBeanPostProcessor;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
    private AdminRepository adminRepository;

	@Override
	public List<LibraryConfig> getLibraryConfig() {

		List<LibraryConfig> libraryConfig = adminRepository.getLibraryConfig();

		return libraryConfig;
	}

	@Override
	@Transactional
	public void saveConfig(HashMap<String, String> config) {
		
		Set<LibraryConfig> configs = new HashSet<LibraryConfig>();

		for(Entry<String, String> entry : config.entrySet()){
			
			LibraryConfig con = new LibraryConfig();
			con.setKey(entry.getKey());
			con.setValue(entry.getValue());

			configs.add(con);
		}
		
		adminRepository.saveConfig(configs);
		
	}
	
	@Override
	public Map<String, Class<? extends Object>> getCronJobs() {
		return CronJobsBeanPostProcessor.getJobBeansDefinitions();
	}

	@Override
	public void executeJob(String jobName) {
		
		CronJobsExecutor executor = new CronJobsExecutor();
		executor.execute(jobName);
		
	}
}
