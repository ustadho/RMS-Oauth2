package com.mitrais.rms.resource.integration.cucumber.stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTestStepDefinition extends StepDefs {
    private final String clientId = "adminapp";
    private final String clientSecret = "password";
    private final String tokenUrl = "oauth/token";
    String accessToken;
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception{
        accessToken = obtainAccessToken("admin", "password");
    }

    @Given("User or application have got access_token")
    public void user_or_application_have_got_access_token() throws Exception {
        if(accessToken ==null){
            accessToken = obtainAccessToken("admin", "password");
        }
        assertNotNull(accessToken);
    }

    @When("User or apllication accessing  an url find_all with valid {string}")
    public void user_or_apllication_accessing_an_url_find_all_with_valid(String string) throws Exception {
        actions = mockMvc.perform(get("/api/setting/users/"+1)
                .header("Authorization", "Bearer "+accessToken)
                .accept(MediaType.APPLICATION_JSON));

    }

    @Then("Resource server provide data in form of a List")
    public void resource_server_provide_data_in_form_of_a_List() throws Exception{
        actions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
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
