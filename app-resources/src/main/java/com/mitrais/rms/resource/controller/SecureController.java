package com.mitrais.rms.resource.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecureController {
	private static final String STATE = "state";
	
	@ResponseBody
	@GetMapping("sekarang")
	public ModelMap halo() {
		return new ModelMap().addAttribute("sekarang", new Date());
	}
	@ResponseBody
	@GetMapping("hello")
	public String haloFriend() {
		return "Hello";
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
	
	@RequestMapping("/api/state/new")
	@ResponseBody
    public Map<String, Object> newState(HttpSession session){
        Map<String, Object> hasil = new HashMap<String, Object>();
        hasil.put("sukses", Boolean.TRUE);
        
        String state = UUID.randomUUID().toString();
        hasil.put(STATE, state);
        session.setAttribute(STATE, state);
        
        return hasil;
    }
    
	@ResponseBody
    @RequestMapping("/api/state/verify")
    public Map<String, Object> verifyState(HttpSession session){
        Map<String, Object> hasil = new HashMap<String, Object>();
        hasil.put("sukses", Boolean.TRUE);
        
        String state = (String) session.getAttribute(STATE);
        hasil.put(STATE, state);
        session.removeAttribute(STATE);
        
        return hasil;
    }
}
