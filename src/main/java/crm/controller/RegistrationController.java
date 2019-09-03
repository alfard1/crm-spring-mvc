package crm.controller;

import crm.service.UserService;
import crm.user.CrmUser;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
    private final UserService userService;
	public RegistrationController(UserService userService) {
		this.userService = userService;
	}
	private Logger logger = Logger.getLogger(getClass().getName());

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		theModel.addAttribute("crmUser", new CrmUser());
		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
				@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, 
				BindingResult theBindingResult, 
				Model theModel) {
						
		String userName = theCrmUser.getUserName();
		logger.info("Processing registration form for: " + userName);
		
		// form validation (rules are here: CrmUser.java)
		if (theBindingResult.hasErrors()) {
			logger.warning("User name/password can not be empty.");		
			return "registration-form";	
		}

		// check the database if user already exists
        crm.entity.User existing = userService.findByUserName(userName);
        System.out.println("### >>>> existing = " + existing);
        if (existing != null){
        	theModel.addAttribute("crmUser", new CrmUser());
			theModel.addAttribute("registrationError", "User name already exists.");
			logger.warning("User name already exists.");
        	return "registration-form";
        }

		// create user account    
        userService.save(theCrmUser);
        
        logger.info("Successfully created user: " + userName);     
        return "registration-confirmation";		
	}
}
