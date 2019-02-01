package com.ustadho.oauth;

import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Controller
public class AppAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppAuthApplication.class, args);
	}
        
        @ResponseBody
        @GetMapping("sekarang")
        public ModelMap halo(){
            return new ModelMap().addAttribute("sekarang", new Date());
        }
        
        @GetMapping("/protected")
        public void protectedPage(Model model){
        }
        
        @GetMapping("/home")
        public void homePage(){}
        
}

