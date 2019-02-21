package com.mitrais.rms.auth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ResourceOwnerPasswordTest {
    private final String clientId = "adminapp";
    private final String clientSecret = "password";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void canRefreshAccessTokenWithGrantedScope()
    {
        OAuth2AccessToken accessToken = exchangeCredentialsForAccessToken("/oauth/token", "admin", "password",
                "role_admin");

        OAuth2AccessToken refreshedAccessToken = exchangeRefreshTokenForAccessToken("/oauth/token",
                accessToken.getRefreshToken().getValue());
        System.out.println("refreshedAccToken.getValue()"+refreshedAccessToken.getValue());
        assertThat(refreshedAccessToken.getValue(), is(notNullValue()));
    }

    private OAuth2AccessToken exchangeCredentialsForAccessToken(String tokenEndpoint, String username, String password,
                                                                String scope)
    {
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
