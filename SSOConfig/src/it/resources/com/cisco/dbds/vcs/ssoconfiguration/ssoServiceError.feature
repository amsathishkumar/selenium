@SSOServiceError
Feature: SSO Service Error Restcalls


@Prechecksdf @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @Sumathi
Scenario: SSO WUI_Verify all the SSO Configuration Details fields entered valid values are saved to DB  and there is no service configured to publish sso configuration
	Given Navigate to VCS "<<vcs.url>>" 
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>" 
	And Click on "login" button 
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|
	|<<vcs.ip>>|<<vcs.port>>|ssoadmin|dv3group|2|120|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	
@test123 @demooo @Ttv9629542c @Completed @Sumathi
Scenario: Verify the rest message ERROR=SERVICE_ERROR using Token Authentication Service
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |STATUSURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |
	And Add the below parameter values to URL
		|token |appid|
		|12345 |CMC |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SERVICE_ERROR"

@test123 @BROWSER_NOT_NEEDED @Ttv9629552c @Completed @Sumathi
Scenario: Verify the rest message ERROR=SERVICE_ERROR Roles Retrieval Service
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |STATUSURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/roles |
	And Add the below parameter values to URL
		|token |appid|
		|12345 |CMC |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SERVICE_ERROR"
    
 @test123 @BROWSER_NOT_NEEDED @Ttv9629562c @Completed @Sumathi
Scenario: Logout Service: Verify the rest message ERROR=SERVICE_ERROR through restful with
only and all valid mandatory parameters
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |STATUSURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/logout |
	And Add the below parameter values to URL
		|token |appid|
		|12345 |CMC |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SERVICE_ERROR"
 
 @test123 @BROWSER_NOT_NEEDED @Ttv9629565c @Completed @Sumathi
Scenario: Verify the rest message ERROR=SERVICE_ERROR with Audit Service parameters
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |STATUSURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit |
	And Add the below parameter values to URL
		 |auditType|token              |ip |url|operationResult|appid|
		 |XMP_AUDIT|12345 |10.78.203.118|taj|0|CMC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SERVICE_ERROR"
  
   @test123 @BROWSER_NOT_NEEDED @Ttv9629583c @Completed @Sumathi
Scenario: Application Token Retrieval Service: Verify the rest message ERROR=SERVICE_ERROR
 through restful with all valid mandatory parameters
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |STATUSURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/getToken |
	And Add the below parameter values to URL
		|token |appid|
		|12345 |CMC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SERVICE_ERROR" 
    
 @test123 @demooo @BROWSER_NOT_NEEDED @Ttv9629586c @Completed @Sumathi
Scenario: Verify  ERROR=SERVICE_ERROR message using rest call using Token Authorization Service
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |STATUSURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authorized |
	And Add the below parameter values to URL
		|token |appid|neId|netype_id|
		|12345 |CMC|marinaec|EC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SERVICE_ERROR" 

