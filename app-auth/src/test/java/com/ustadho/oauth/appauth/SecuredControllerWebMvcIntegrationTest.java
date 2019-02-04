package com.ustadho.oauth.appauth;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.net.ssl.SSLEngineResult.Status;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mitrais.rms.SecureController;

@RunWith(SpringJUnit4ClassRunner.class)
public class SecuredControllerWebMvcIntegrationTest {
	private MockMvc mockMvc;
	
	@InjectMocks
    private SecureController resource;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(resource)
                .build();
    }
    
	@WithMockUser(value = "spring")
	@Test
	public void givenAuthRequestOnPrivateServuce_shouldSucceedWith200() throws Exception {
		 mockMvc.perform(get("/hallo-friend"))
         .andExpect(status().isOk())
         .andExpect(content().string("Hallo Fiend"));
	}
	
	@WithMockUser(username = "admin", password = "password")
	@Test
	public void givenAuthServiceOn_shouldSuccessWith200() throws Exception{
		mockMvc.perform(get("/current-user").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
}
