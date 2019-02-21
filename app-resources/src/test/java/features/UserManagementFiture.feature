Feature: User Management
  In order to testing User management data with CRUD Operation

Scenario: Getting all user data from Database
  Given User or application have got access_token
  When User or apllication accessing  an url find_all with valid "acess_token"
  Then Resource server provide data in form of a List
