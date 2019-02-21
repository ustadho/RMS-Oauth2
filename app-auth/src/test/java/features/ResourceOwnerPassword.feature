Feature: Application Login
  In order to testing authentication and authorization process on auth-server
  With some Flow Grant Type

@LoginTest
Scenario: Request Access Token for Resource Owner Password Grant
  Given User or application know the auth url
  When User login into application with "admin" and password "password"
  Then AuthServer return OAuth2AccessToken object

Scenario: Exchange refresh_token for access_token
  Given Already have a refresh_token
  When post refresh_token to exchange with access_token
  Then AuthServer return OAuth2AccessToken object