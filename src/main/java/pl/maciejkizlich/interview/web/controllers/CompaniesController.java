package pl.maciejkizlich.interview.web.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.maciejkizlich.interview.persistence.model.Company;
import pl.maciejkizlich.interview.services.CompaniesService;

@Controller
@RequestMapping("/company")
public class CompaniesController {

    @Autowired
    private CompaniesService companiesService;
    
    @RequestMapping(value = "/get/{companyId}", method = RequestMethod.GET)
    public String showCompanyDetails(@PathVariable(value = "companyId") long companyId, ModelMap model) {

    	Company company = companiesService.getCompanyById(companyId);
    	
    	model.put("company", company);
    	
    	return "company/companyDetails";
    }
    
    @RequestMapping(value="/allCompanies", method = RequestMethod.GET)
    public String showAllQuestions(Map<String, Object> model) {
        Collection<Company> companies = companiesService.findAll();
        model.put("companies", companies);

        return "company/showAllCompanies"; //jsp page
    }
    
    
}
