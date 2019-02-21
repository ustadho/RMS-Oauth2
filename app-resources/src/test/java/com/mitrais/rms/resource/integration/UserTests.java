package com.mitrais.rms.resource.integration;

import com.mitrais.rms.resource.domain.User;
import com.mitrais.rms.resource.repository.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Test
    public void test() throws Exception {
        String accessToken = obtainAccessToken("admin", "password");
        System.out.println("access_token: "+accessToken);
        MvcResult result = mockMvc.perform(get("/api/setting/users/"+1)
        .header("Authorization", "Bearer "+accessToken)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
                .andReturn();

        String str = result.getResponse().getContentAsString();
        assertTrue(str.contains("\"id\" : 1"));
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);
        params.add("scope", "role_admin");

        String base64ClientCredentials = new String(Base64.encodeBase64("adminapp:password".getBytes()));

        ResultActions result  =
                mockMvc.perform(post("/oauth/token")
                .params(params)
                .header("Authorization", "Basic "+base64ClientCredentials)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk());

        String resultString = result.andReturn()
                .getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }


}
