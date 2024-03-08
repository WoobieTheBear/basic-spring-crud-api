package ch.black.gravel.controllers;

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
        SignupFormDTO user = new SignupFormDTO();
        model.addAttribute("user", new SignupFormDTO());
        return "signup/form";
    }

    @PostMapping("/processSignup")
    public String processSignup(
			@Valid @ModelAttribute("user") SignupFormDTO signupDTO,
			BindingResult result,
			HttpSession session, Model theModel
    ) {

		String userName = signupDTO.getName();
		
        // form validation
		if (result.hasErrors()){
			return "signup/form";
		}

		// check the database if user already exists
        AuthEntity existing = entityService.findByName(userName);
        if (existing != null){
        	theModel.addAttribute("webUser", new SignupFormDTO());
			theModel.addAttribute("registrationError", "User name already exists.");

        	return "signup/form";
        }
        
        // create user account and store in the databse
        entityService.save(signupDTO);
        
		// place user in the web http session for later use
		session.setAttribute("user", signupDTO);

        return "signup/success";
    }

}
