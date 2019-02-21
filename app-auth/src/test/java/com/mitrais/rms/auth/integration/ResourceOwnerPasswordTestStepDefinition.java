package com.mitrais.rms.auth.integration;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@RunWith(Cucumber.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ResourceOwnerPasswordTestStepDefinition {
    private final String clientId = "adminapp";
    private final String clientSecret = "password";
    private final String tokenUrl = "oauth/token";
    OAuth2AccessToken accessToken;
    OAuth2RefreshToken refreshToken;
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup(){
        params.set("grant_type", "password");
        params.set("scope", "role_admin");
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        headers.set(AUTHORIZATION, getAuthorization());
    }

    @Given("^User or application know the auth url$")
    public void user_or_application_know_the_auth_url() throws Throwable {
        System.out.println("Oauth token url: "+tokenUrl);
    }

    @When("^User login into application with \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void user_login_into_application_with_something_and_password_something(String username, String pass) throws Throwable {
        params.set("grant_type", "password");
        params.set("scope", "role_admin");
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        headers.set(AUTHORIZATION, getAuthorization());
        params.set("username", username);
        params.set("password", pass);
        HttpEntity<Object> request = new HttpEntity<>(params, headers);

        accessToken =  restTemplate.postForObject("/oauth/token", request, OAuth2AccessToken.class);
        refreshToken = accessToken.getRefreshToken();
    }

    @Then("^AuthServer return OAuth2AccessToken object$")
    public void authserver_return_oauth2accesstoken_object() throws Throwable {
        System.out.println("The token returned: "+accessToken.getValue());
        System.out.println("Refresh token: "+accessToken.getRefreshToken().getValue());
        assertThat(accessToken.getValue(), is(notNullValue()));
    }

    @Given("Already have a refresh_token")
    public void already_have_a_refresh_token() throws Throwable{
        user_login_into_application_with_something_and_password_something("admin", "password");
        assertThat(accessToken.getRefreshToken().getValue(), is(notNullValue()));
    }

    @When("post refresh_token to exchange with access_token")
    public void post_refresh_token_to_exchange_with_access_token() throws Throwable {
        params.set("grant_type", "refresh_token");
        params.set("refresh_token", accessToken.getRefreshToken().getValue());
        user_login_into_application_with_something_and_password_something("admin", "password");
        System.out.println("The token exchange from ref_refresh_token : "+accessToken.getValue());
        assertThat(accessToken.getValue(), is(notNullValue()));
    }

    private OAuth2AccessToken exchangeCredentialsForAccessToken(
            String tokenEndpoint, String username, String password, String scope){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "password");
        params.set("username", username);
        params.set("password", password);
        params.set("scope", scope);

        return postForAccessToken(tokenEndpoint, params);
    }

    private OAuth2AccessToken exchangeRefreshTokenForAccessToken(String tokenEndpoint, String refreshToken)
    {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "refresh_token");
        params.set("refresh_token", refreshToken);

        return postForAccessToken(tokenEndpoint, params);
    }

    private OAuth2AccessToken postForAccessToken(String tokenEndpoint, MultiValueMap<String, String> params)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        headers.set(AUTHORIZATION, getAuthorization());

        HttpEntity<Object> request = new HttpEntity<>(params, headers);
        return restTemplate.postForObject(tokenEndpoint, request, OAuth2AccessToken.class);
    }

    private String getAuthorization()
    {
        return httpBasic(clientId, clientSecret)
                .postProcessRequest(new MockHttpServletRequest())
                .getHeader(AUTHORIZATION);
    }
}
