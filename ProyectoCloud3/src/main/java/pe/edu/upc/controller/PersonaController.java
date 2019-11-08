package pe.edu.upc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class PersonaController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder2;

	@Bean
	public BCryptPasswordEncoder passwordEncoder2() {
		return new BCryptPasswordEncoder();
	}

	public BCryptPasswordEncoder getPasswordEncoder2() {
		return passwordEncoder2;
	}

	public void setPasswordEncoder2(BCryptPasswordEncoder passwordEncoder2) {
		this.passwordEncoder2 = passwordEncoder2;
	}
	
	

}
