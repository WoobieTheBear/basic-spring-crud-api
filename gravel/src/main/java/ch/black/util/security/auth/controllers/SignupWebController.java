package ch.black.util.security.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.black.util.security.auth.dtos.SignupFormDTO;
import ch.black.util.security.auth.entities.AuthEntity;
import ch.black.util.security.auth.services.AuthEntityService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class SignupWebController {

    private AuthEntityService entityService;

	@Autowired
	public SignupWebController(AuthEntityService userService) {
		this.entityService = userService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new SignupFormDTO());
        return "security/signup-form";
    }

    @PostMapping("/processSignup")
    public String processSignup(
			@Valid @ModelAttribute("user") SignupFormDTO signupDTO,
			BindingResult result,
			HttpSession session, Model model
    ) {

		String userName = signupDTO.getName();
		
        // form validation
		if (result.hasErrors()){
			return "security/signup-form";
		}

		// check the database if user already exists
        AuthEntity existing = entityService.findByName(userName);
        if (existing != null){
        	model.addAttribute("user", new SignupFormDTO());
			model.addAttribute("duplicateError", "User name already exists.");

        	return "security/signup-form";
        }
        
        // create user account and store in the databse
        entityService.save(signupDTO);
        
		// place user in the web http session for later use
		session.setAttribute("user", signupDTO);

        return "security/signup-success";
    }

}
