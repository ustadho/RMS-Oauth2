Feature: Application Login

@WebTest
Scenario: Homepage default login
Given User is on login user page
When User login into application with "admin" and password "password"
Then Home page is populated
