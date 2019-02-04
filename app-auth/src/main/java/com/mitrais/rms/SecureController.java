package com.mitrais.rms;

import java.util.Date;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecureController {
	@ResponseBody
	@GetMapping("sekarang")
	public ModelMap halo() {
		return new ModelMap().addAttribute("sekarang", new Date());
	}
	@ResponseBody
	@GetMapping("hallo-friend")
	public String haloFriend() {
		return "Hallo Fiend";
	}

	@PreAuthorize("hasAuthority('USER_VIEW')")
	@GetMapping("/protected")
	public void protectedPage(Model model) {
	}

	@GetMapping("/home")
	public void homePage() {
	}
	
	@ResponseBody
	@PreAuthorize("hasAuthority('USER_VIEW')")
	@GetMapping("/current-user")
	public Object getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
