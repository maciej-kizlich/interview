package pl.maciejkizlich.interview.persistence.dao;

import java.util.List;
import java.util.Set;

import pl.maciejkizlich.interview.persistence.model.AsyncJobError;
import pl.maciejkizlich.interview.persistence.model.LibraryConfig;

public interface AdminRepository {

	public List<LibraryConfig> getLibraryConfig();
	
	public void saveConfig(Set<LibraryConfig> configs);
	
	public LibraryConfig getConfigByName(String key);
	
	public void saveAsyncJobError(AsyncJobError error);
	
}
