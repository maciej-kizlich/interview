package pl.maciejkizlich.interview.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.maciejkizlich.interview.persistence.model.LibraryConfig;
import pl.maciejkizlich.interview.services.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = "/showSettings", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String showSettings(Map<String, Object> model) {

		prepareConfigModel(model);

		return "admin/showSettings";
	}
    
	@RequestMapping(value = "/editSettings", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String editSettings(Map<String, Object> model) {

		prepareConfigModel(model);

		return "admin/editSettings";
	}

	private void prepareConfigModel(Map<String, Object> model) {
		List<LibraryConfig> libraryConfig = adminService.getLibraryConfig();
		model.put("configs", libraryConfig);
	}
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveSettings(@RequestParam HashMap<String, String> config) {
    	
      adminService.saveConfig(config);
    	
      return "redirect:/admin/showSettings";
    }
    
    @RequestMapping(value = "/showCronJobs", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showCronJobs(Map<String, Object> model) {
    	
      Map<String, Class<? extends Object>> cronJobs = adminService.getCronJobs();
    	
      model.put("jobs", cronJobs);

      return "admin/showCronJobs";
    }
    
    @RequestMapping(value = "/execute{jobName}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String executeJob(@RequestParam(value = "jobName") String jobName) {
    	
    	adminService.executeJob(jobName);
    	
      return "redirect:/admin/showCronJobs";
    }
}