package pl.maciejkizlich.interview.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.maciejkizlich.interview.persistence.model.LibraryConfig;

public interface AdminService {

	public List<LibraryConfig> getLibraryConfig();

	public void saveConfig(HashMap<String, String> config);
	
	public Map<String, Class<? extends Object>> getCronJobs();

	public void executeJob(String jobName);
	
}