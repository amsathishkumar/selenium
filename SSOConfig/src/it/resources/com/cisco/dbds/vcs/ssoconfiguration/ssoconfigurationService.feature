@SSOConfigurationService
Feature: Single Sign On Service
Background: 
	Given Navigate to VCS "<<vcs.url>>" 
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>" 
	And Click on "login" button 

@Ttv9629533c @SSOConfigurationServiceRally1 @SSOConfigurationService @Regression,Sanity @SSOConfigurationServiceSanity @Completed   @test 
Scenario: verify rest message USER_ID=<USER_ID> using Token Authentication Service
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHENTUCATEURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |
	And Add the below parameter values to URL
		 |token             |appid|
		 |<<CMC_SSO_COOKIE>>|CMC  | 	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "USER_ID=root"		
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	| Y	     | 
	
		
@Ttv9629534c @SSOConfigurationServiceRally2 @SSOConfigurationService @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: verify the rest message ERROR=REQUIRED_ATTRIBUTE_appid_MISSING using Token Authentication Service
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHENTUCATEURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |
	And Add the below parameter values to URL
         |token|
		 |11111|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_appid_MISSING"		
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	
    
    
    
@Ttv9629535c @SSOConfigurationServiceRally3 @SSOConfigurationService @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message ERROR=REQUIRED_ATTRIBUTE_TOKEN_MISSING using Token Authentication Service 
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHENTUCATEURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |
	And Add the below parameter values to URL
		 |appid|
		 |CMC  | 
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_token_MISSING"   	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 
    
    
    

    	
@Ttv9629536c @SSOConfigurationServiceRally5 @SSOConfigurationService @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message USER_ID=<USER_ID> using all Token Authentication Service parameters  
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHENTUCATEURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |
	And Add the below parameter values to URL
		 |token             |appid|ip         |url |componentName|
		 |<<CMC_SSO_COOKIE>>|CMC  |10.45.45.45|*****|vvv          | 	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "USER_ID=root"	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	
    	
    
@Ttv9629537c @SSOConfigurationServiceRally5 @SSOConfigurationService @Regression,Sanity @SSOConfigurationServiceSanity @Completed  
Scenario: Verify the rest message ERROR=REQUIRED_ATTRIBUTE_TOKEN_MISSING using Token Authentication Service for all mandatory parameters missing  
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHENTUCATEURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_token_MISSING"	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	

 @Ttv9629538c @SSOConfigurationServiceRally4 @SSOConfigurationService @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message ERROR=INVALID_TOKEN  using Token Authentication Service  
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHENTUCATEURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |
	And Add the below parameter values to URL
		 |token|appid|
		 |11111|CMC  | 
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN"	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 
    	
    
     
@Ttv9629539c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message ERROR=INVALID_APPID using Token Authentication Service
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHENTUCATEURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |
	And Add the below parameter values to URL
		 |token             |appid|
		 |<<CMC_SSO_COOKIE>>|CM  |        	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN"	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 
 
 
    
@Ttv9629540c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message ERROR=INVALID_TOKEN using Token Authentication Service for all parameters as invalid
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHENTUCATEURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |
	And Add the below parameter values to URL
		 |token |appid|
		 |1111	|CM  |        	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN" 
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 
   	
@Ttv9629541c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message ERROR=SSO_DISABLED using Token Authentication Service 
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |false |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHENTUCATEURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |
	And Add the below parameter values to URL
		 |token                 |appid|
		 |1234	|CMC  |        	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SSO_DISABLED"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|N	     |
   
    
 @Ttv9629543c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed  
Scenario: Verify the rest message ROLES=<ROLE1,ROLE2> using Roles Retrieval Service
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |ROLESURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/roles |
	And Add the below parameter values to URL
		 |token             |appid|
		 |<<CMC_SSO_COOKIE>>|CMC  | 	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
	And Excute the query "select NEID || '_'|| NETYPE_ID || ',' from NETWORK_ELEMENT_ACCESS FIELDS where USERID='root'"
    Then Verify response Status code as "200"
    And Verify response Message as "ROLES=Root"		
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	| Y	     | 
	
		
@Ttv9629544c @SSOConfigurationServiceRally2 @SSOConfigurationService @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: verify the rest message ERROR=REQUIRED_ATTRIBUTE_appid_MISSING using Roles Retrieval Service
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |ROLESURL   |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/roles |
	And Add the below parameter values to URL
         |token|
		 |11111|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_appid_MISSING"		
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	
    
    
    
@Ttv9629545c @SSOConfigurationServiceRally3 @SSOConfigurationService @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message ERROR=REQUIRED_ATTRIBUTE_TOKEN_MISSING using Roles Retrieval Service 
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |ROLESURL   |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/roles |
	And Add the below parameter values to URL
		 |appid|
		 |CMC  | 
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_token_MISSING"   	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 
    
    	
@Ttv9629546c @SSOConfigurationServiceRally5 @SSOConfigurationService @Regression,Sanity @SSOConfigurationServiceSanity @Completed 
Scenario: Verify the rest message ROLES=<ROLE1,ROLE2>  using all Roles Retrieval Service parameters  
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Excute the query "select NEID || '_'|| NETYPE_ID || ',' from NETWORK_ELEMENT_ACCESS FIELDS where USERID='root'"
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |ROLESURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/roles |
	And Add the below parameter values to URL
		 |token             |appid|ip         |url |componentName|
		 |<<CMC_SSO_COOKIE>>|CMC  |10.78.203.118|taj|vvv          | 	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ROLES=Root"	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	
    	
    
@Ttv9629547c @SSOConfigurationServiceRally5 @SSOConfigurationService @Regression,Sanity @SSOConfigurationServiceSanity @Completed  
Scenario: Verify the rest message ERROR=REQUIRED_ATTRIBUTE_TOKEN_MISSING using Roles Retrieval Service for all mandatory parameters missing  
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |ROLESURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/roles |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_token_MISSING"	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	

 @Ttv9629548c @SSOConfigurationServiceRally4 @SSOConfigurationService @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message ERROR=INVALID_TOKEN  using Roles Retrieval Service  
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |ROLESURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/roles |
	And Add the below parameter values to URL
		 |token|appid|
		 |11111|CMC  | 
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN"	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 

  
@Ttv9629549c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message ERROR=INVALID_APPID using Roles Retrieval Service
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |ROLESURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/roles |
	And Add the below parameter values to URL
		 |token             |appid|
		 |<<CMC_SSO_COOKIE>>|CM  |        	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN"	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |   
  
 @Ttv9629550c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message ERROR=INVALID_TOKEN  with Roles Retrieval Service when all mandatory parameters are invalid
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |ROLESURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/roles |
	And Add the below parameter values to URL
		 |token|appid|
		 |11111|CM  |        	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN"	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 
	
 @Ttv9629551c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message ERROR=SSO_DISABLED  using Roles Retrieval Service
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |false  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |ROLESURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/roles |
	And Add the below parameter values to URL
		 |token				|appid|
		 |1234|CMC  |     	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SSO_DISABLED"	
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|N	     |


	
 @Ttv9629553c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed 
Scenario: Verify the rest message SUCCESS using Logout Service
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |LOGOUTURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/logout |
	And Add the below parameter values to URL
		 |token				|appid|
		 |<<CMC_SSO_COOKIE>>|CMC  |     	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "SUCCESS"	
 	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='|
	And Verify the below db values "is empty"
	|APPID|
	|CMC  |  
	And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	

@Ttv9629554c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Logout Service Verify the rest message ERROR=REQUIRED_ATTRIBUTE_appid_MISSING, when SSO enable UI field is enabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |LOGOUTURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/logout |
	And Add the below parameter values to URL
		 |token				|
		 |<<CMC_SSO_COOKIE>>|    	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_appid_MISSING"	
   And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	|  
	And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	
	
 @Ttv9629555c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Logout Service Verify the rest message ERROR=REQUIRED_ATTRIBUTE_TOKEN_MISSING, when SSO enable UI field is enabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |LOGOUTURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/logout |
	And Add the below parameter values to URL
		|appid|
		|CMC  |     	
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_token_MISSING"	
   And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	|  
	And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |


 @Ttv9629556c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Logout Service  Verify the rest message ERROR=REQUIRED_ATTRIBUTE_TOKEN_MISSING, when SSO enable UI field is enabled when all mandatory parameters are missing
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |LOGOUTURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/logout |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_token_MISSING"	
    And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	|  
	And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |

  @Ttv9629557c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   @testcasemodification 
Scenario: Logout Service All the service should be able to roles through restful with all valid mandatory parameters and optional parameters, when SSO enable UI field is enabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |LOGOUTURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/logout |
	And Add the below parameter values to URL
		 |token             |appid|ip         |url  |componentName|
		 |<<CMC_SSO_COOKIE>>|CMC  |10.78.203.118|taj|vvv          | 
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "SUCCESS"	
    And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is empty"
		|APPID	|
		|CMC	| 
	And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
   
@Ttv9629558c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Logout  Service Verify the rest message ERROR=INVALID_TOKEN, when SSO enable UI field is enabled with invalid token and valid appid
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |LOGOUTURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/logout |
	And Add the below parameter values to URL
		 |token|appid|
		 |11111|CMC  |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN"	
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
   	And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
   
 
@Ttv9629559c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Logout  Service Verify the rest message ERROR= INVALID_APPID, when SSO enable UI field is enabled with valid token and invalid appid
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |LOGOUTURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/logout |
	And Add the below parameter values to URL
		 |token             |appid|
		 |<<CMC_SSO_COOKIE>>|CM  |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN"	
  	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	|   
	And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |

@Ttv9629560c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Logout  Service Verify the rest message ERROR= INVALID_TOKEN, when SSO enable UI field is enabled with invalid token and invalid appid
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |LOGOUTURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/logout |
	And Add the below parameter values to URL
		 |token|appid|
		 |11111|CM  |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN"	
  	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	|   
	And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	
@Ttv9629561c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Logout Service Verify the rest message ERROR= SSO_DISABLED through restful with only and all valid mandatory parameters, when SSO enable UI field is disabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |false  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |LOGOUTURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/logout |
	And Add the below parameter values to URL
		|token              |appid|
		|1234|CMC  |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SSO_DISABLED"	
    And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	|
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|N	     | 

    
@Ttv9629563c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message SUCCESS with Audit Service parameters
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Add the below parameter values to URL
		 |auditType|token              |ip |url|operationResult|appid|
		 |XMP_AUDIT       |<<CMC_SSO_COOKIE>> |10.78.203.118|taj|0|CMC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "SUCCESS"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	
	
 @Ttv9629564c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message SSO_DISABLED with Audit Service parameters
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |false  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Add the below parameter values to URL
		 |auditType|token              |ip |url|operationResult|appid|
		 |XMP_AUDIT|<<CMC_SSO_COOKIE>> |10.78.203.118|taj|0|CMC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SSO_DISABLED"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|N	     | 	
	

@Ttv9629566c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message INVALID_TOKEN with Audit Service parameters
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Add the below parameter values to URL
		 |auditType|token              |ip |url|operationResult|appid|
		 |XMP_AUDIT       |1111111111111111111|10.78.203.118|taj|0|CMC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	
	   
@Ttv9629567c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message REQUIRED_ATTRIBUTE_appid_MISSING with Audit Service parameters
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Add the below parameter values to URL
		 |auditType|token              |ip |url|operationResult|
		 |XMP_AUDIT       |<<CMC_SSO_COOKIE>> |10.78.203.118|taj|0|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_appid_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	

@Ttv9629568c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message REQUIRED_ATTRIBUTE_token_MISSING with Audit Service parameters
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Add the below parameter values to URL
		 |auditType|ip |url|operationResult|appid|
		 |XMP_AUDIT       |10.78.203.118|taj|0|CMC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_token_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	


@Ttv9629569c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message REQUIRED_ATTRIBUTE_ip_MISSING with Audit Service parameters
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Add the below parameter values to URL
		 |auditType|token              |url|operationResult|appid|
		 |XMP_AUDIT       |<<CMC_SSO_COOKIE>> |taj|0|CMC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_ip_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	
	
@Ttv9629570c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message REQUIRED_ATTRIBUTE_AuditType_MISSING with Audit Service parameters
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Add the below parameter values to URL
		|token              |ip |url|operationResult|appid|
		|<<CMC_SSO_COOKIE>> |10.78.203.118|taj|0|CMC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_AuditType_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 
	
@Ttv9629571c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message REQUIRED_ATTRIBUTE_url_MISSING with Audit Service parameters
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Add the below parameter values to URL
		 |auditType|token              |ip |operationResult|appid|
		 |XMP_AUDIT       |<<CMC_SSO_COOKIE>> |10.78.203.118|0|CMC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_url_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	
	 
@Ttv9629572c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message REQUIRED_ATTRIBUTE_operationResult_MISSING with Audit Service parameters
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Add the below parameter values to URL
		 |auditType|token              |ip |url|appid|
		 |XMP_AUDIT       |<<CMC_SSO_COOKIE>> |10.78.203.118|taj|CMC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_operationResult_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 

	
	 @Ttv9629573c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   @testcasemodification
Scenario: Verify the rest message operationResult_DATA_IS_INVALID with Audit Service parameters
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Add the below parameter values to URL
		 |auditType|token              |ip |url|operationResult|appid|
		 |XMP_AUDIT       |<<CMC_SSO_COOKIE>> |10.78.203.118|taj|0!1|CMC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=operationResult_DATA_IS_INVALID"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	


@Ttv9629574c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Verify the rest message INVALID_TOKEN with all Audit Service parameters as invalid  
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Add the below parameter values to URL
		 |auditType|token              |ip |url|operationResult|appid|
		 |XMP_AUDIT!       |111111111111111111 |10.78.203.118|taj|0!|CMC!|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=operationResult_DATA_IS_INVALID"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 	
	
	
@SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Deletedcase   
Scenario: Verify the rest message REQUIRED_ATTRIBUTE_token_MISSING with all Audit Service parameters missing  

	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUDITURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/audit  |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_token_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     | 
	
		
	
@Ttv9629575c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Application Token Retrieval Service: All the service should be able to obtain application token through restful with all valid mandatory parameters, when SSO enable UI field is enabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	And Update query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|update SSO_TOKEN set ONETIMETOKEN='1234' where TOKEN='| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |RETRIEVETOKURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/getToken  |
	And Add the below parameter values to URL
		|token              |appid|
		|1234	       	    |CMC |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "CMC_SSO_COOKIE"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	  |
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where ONETIMETOKEN is NULL and TOKEN='| 
	And Verify the below db values "is matched"
	|APPID|
	|CMC  |
	
@Ttv9629576c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Application Token Retrieval Service: Verify the rest message ERROR= INVALID_TOKEN_OR_APPID through restful with all mandatory parameters with invalid appid value when SSO enable UI field is enabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |RETRIEVETOKURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/getToken  |
	And Add the below parameter values to URL
		|token              |appid|
		|1234 |CM |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN_OR_APPID"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where ONETIMETOKEN is NULL and TOKEN='| 
	And Verify the below db values "is matched"
	|APPID|
	|CMC  |
	
	
	
	
@Ttv9629577c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Application Token Retrieval Service: Verify the rest message ERROR= INVALID_TOKEN_OR_APPID through restful with all mandatory parameters with invalid token value when SSO enable UI field is enabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |RETRIEVETOKURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/getToken  |
	And Add the below parameter values to URL
		|token     |appid|
		|1111111111|CMC |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN_OR_APPID"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where ONETIMETOKEN is NULL and TOKEN='| 
	And Verify the below db values "is matched"
	|APPID|
	|CMC  |

@Ttv9629578c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Application Token Retrieval Service: Verify the rest message ERROR= INVALID_TOKEN_OR_APPID through restful with all mandatory parameters with invalid token value and invalid appid when SSO enable UI field is enabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |RETRIEVETOKURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/getToken  |
	And Add the below parameter values to URL
		|token              |appid|
		|111111111111111111 |CM |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN_OR_APPID"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where ONETIMETOKEN is NULL and TOKEN='| 
	And Verify the below db values "is matched"
	|APPID|
	|CMC  |

@Ttv9629579c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Application Token Retrieval Service: Verify the rest message ERROR=REQUIRED_ATTRIBUTE_appid_MISSING  through restful with one valid mandatory parameters token, when SSO enable UI field is enabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |RETRIEVETOKURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/getToken  |
	And Add the below parameter values to URL
		|token              |
		|1234 |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_appid_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where ONETIMETOKEN is NULL and TOKEN='| 
	And Verify the below db values "is matched"
	|APPID|
	|CMC  |
	
@Ttv9629580c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Application Token Retrieval Service: Verify the rest message ERROR=REQUIRED_ATTRIBUTE_token_MISSING through restful with one valid mandatory parameters appid, when SSO enable UI field is enabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |RETRIEVETOKURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/getToken  |
	And Add the below parameter values to URL
		|appid|
		|CMC |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_token_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where ONETIMETOKEN is NULL and TOKEN='| 
	And Verify the below db values "is matched"
	|APPID|
	|CMC  |
	
@Ttv9629581c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   @testcasemodification
Scenario: Application Token Retrieval Service: Verify the rest message ERROR=REQUIRED_ATTRIBUTE_token_MISSING through restful with all invalid mandatory parameters, when SSO enable UI field is enabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |RETRIEVETOKURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/getToken  |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_token_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where ONETIMETOKEN is NULL and TOKEN='| 
	And Verify the below db values "is matched"
	|APPID|
	|CMC  |
	
@Ttv9629582c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed   
Scenario: Application Token Retrieval Service: Verify the rest message Status: ERROR=SSO_DISABLED  through restful with all valid mandatory parameters, when SSO enabled UI field is disabled
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |false  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |RETRIEVETOKURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/getToken  |
	And Add the below parameter values to URL
		|token              |appid|
		|<<CMC_SSO_COOKIE>> |CMC |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SSO_DISABLED"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|N	     |
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where ONETIMETOKEN is NULL and TOKEN='| 
	And Verify the below db values "is matched"
	|APPID|
	|CMC  |
	

@Ttv9629584c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed 
Scenario: Token Authorization Service: verify EC network element access user status  through restful with all valid mandatory parameters, when SSO enable UI field is enabled and user assigned with network element	
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "5" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	And Click on the Toggle menu icon 
	And Click on "Control Plane" main menu option 
	And Click on "ECS Dashboard" sub menu option
	And Click on ECS Dashboard tab "Network Element Management"
	And Click on ECS Dashboard tab "Network Element Access Management"
	And Select ECS Dashboard user "root"
	And "Enable" ECS Dashboard ne "marinaec (EC)"
	And WUI wait for "2" seconds
	And Click on Network Element Save button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "Network Element Management" for "Network Element" toaster
	And Verify content "Network Element Access Saved Successfully!!!" for "Network Element" toaster
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHORIZEURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authorized  |
	And Add the below parameter values to URL
		|token              |appid|neId|netype_id|
		|<<CMC_SSO_COOKIE>> |CMC  |marinaec|EC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "true"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Excute the query "select NEID from NETWORK_ELEMENT_ACCESS where USERID='root'"
	And Verify the below db values "is matched"
	|NEID|
	|marinaec|
	
@Ttv9629585c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed 
Scenario: Token Authorization Service: verify EC network element access user status  through restful with all valid mandatory parameters, when SSO enable UI field is disabled and user assigned with network element	
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |false  |
	And Click on ssoconfig "Save" button
	And WUI wait for "5" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	And Click on the Toggle menu icon 
	And Click on "Control Plane" main menu option 
	And Click on "ECS Dashboard" sub menu option
	And Click on ECS Dashboard tab "Network Element Management"
	And Click on ECS Dashboard tab "Network Element Access Management"
	And Select ECS Dashboard user "root"
	And "Enable" ECS Dashboard ne "marinaec (EC)"
	And WUI wait for "2" seconds
	And Click on Network Element Save button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "Network Element Management" for "Network Element" toaster
	And Verify content "Network Element Access Saved Successfully!!!" for "Network Element" toaster
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHORIZEURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authorized  |
	And Add the below parameter values to URL
		|token              |appid|neId|netype_id|
		|<<CMC_SSO_COOKIE>> |CMC  |marinaec|EC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=SSO_DISABLED"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|N	     |
	And Excute the query "select NEID from NETWORK_ELEMENT_ACCESS where USERID='root'"
	And Verify the below db values "is matched"
	|NEID|
	|marinaec|


@Ttv9629587c  @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed 
Scenario: Token Authorization Service: verify EC network element access user status through restful with all valid mandatory parameters, when SSO enable UI field is enabled and user is not assigned with network element
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "5" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	And Click on the Toggle menu icon 
	And Click on "Control Plane" main menu option 
	And Click on "ECS Dashboard" sub menu option
	And Click on ECS Dashboard tab "Network Element Management"
	And Click on ECS Dashboard tab "Network Element Access Management"
	And Select ECS Dashboard user "root"
	And "Disable" ECS Dashboard ne "marinaec (EC)"
	And Click on Network Element Save button	
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "Network Element Management" for "Network Element" toaster
	And Verify content "Network Element Access Saved Successfully!!!" for "Network Element" toaster
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHORIZEURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authorized  |
	And Add the below parameter values to URL
		|token              |appid|neId|netype_id|
		|<<CMC_SSO_COOKIE>> |CMC  |marinaec|EC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "false"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |



@Ttv9629588c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed 
Scenario: Token Authorization Service: verify EC network element access user status  through restful with all valid mandatory parameters with invalid token,when SSO enable UI field is enabled and user assigned with network element
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "5" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	And Click on the Toggle menu icon 
	And Click on "Control Plane" main menu option 
	And Click on "ECS Dashboard" sub menu option
	And Click on ECS Dashboard tab "Network Element Management"
	And Click on ECS Dashboard tab "Network Element Access Management"
	And Select ECS Dashboard user "root"
	And "Enable" ECS Dashboard ne "marinaec (EC)"
	And Click on Network Element Save button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "Network Element Management" for "Network Element" toaster
	And Verify content "Network Element Access Saved Successfully!!!" for "Network Element" toaster
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHORIZEURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authorized  |
	And Add the below parameter values to URL
		|token              |appid|neId|netype_id|
		|12345 |CMC  |marinaec|EC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Excute the query "select NEID from NETWORK_ELEMENT_ACCESS where USERID='root'"
	And Verify the below db values "is matched"
	|NEID|
	|marinaec|
	

@Ttv9629589c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed 
Scenario: Token Authorization Service: verify EC network element access user status  through restful with all valid mandatory parameters with invalid appid,when SSO enable UI field is enabled and user assigned with network element	
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "5" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	And Click on the Toggle menu icon 
	And Click on "Control Plane" main menu option 
	And Click on "ECS Dashboard" sub menu option
	And Click on ECS Dashboard tab "Network Element Management"
	And Click on ECS Dashboard tab "Network Element Access Management"
	And Select ECS Dashboard user "root"
	And "Enable" ECS Dashboard ne "marinaec (EC)"
	And Click on Network Element Save button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "Network Element Management" for "Network Element" toaster
	And Verify content "Network Element Access Saved Successfully!!!" for "Network Element" toaster
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHORIZEURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authorized  |
	And Add the below parameter values to URL
		|token              |appid|neId|netype_id|
		|<<CMC_SSO_COOKIE>> |CMC!	  |marinaec|EC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=INVALID_TOKEN"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Excute the query "select NEID from NETWORK_ELEMENT_ACCESS where USERID='root'"
	And Verify the below db values "is matched"
	|NEID|
	|marinaec|

@demoooo
Scenario: test
And "Enable" ECS Dashboard ne "marinaec (EC)"

	

@Ttv9629590c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed 
Scenario: Token Authorization Service: verify EC network element access user status  through restful with all valid mandatory parameters excluding token, when SSO enable UI field is enabled and user assigned with network element	
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "5" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	And Click on the Toggle menu icon 
	And Click on "Control Plane" main menu option 
	And Click on "ECS Dashboard" sub menu option
	And Click on ECS Dashboard tab "Network Element Management"
	And Click on ECS Dashboard tab "Network Element Access Management"
	And Select ECS Dashboard user "root"
	And "Enable" ECS Dashboard ne "marinaec (EC)"
	And Click on Network Element Save button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "Network Element Management" for "Network Element" toaster
	And Verify content "Network Element Access Saved Successfully!!!" for "Network Element" toaster
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHORIZEURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authorized  |
	And Add the below parameter values to URL
		|appid|neId|netype_id|
		|CMC  |marinaec|EC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_token_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Excute the query "select NEID from NETWORK_ELEMENT_ACCESS where USERID='root'"
	And Verify the below db values "is matched"
	|NEID|
	|marinaec|


@Ttv9629591c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed 
Scenario: Token Authorization Service: verify EC network element access user status  through restful with all valid mandatory parameters excluding appid, when SSO enable UI field is enabled and user assigned with network element	
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "5" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	And Click on the Toggle menu icon 
	And Click on "Control Plane" main menu option 
	And Click on "ECS Dashboard" sub menu option
	And Click on ECS Dashboard tab "Network Element Management"
	And Click on ECS Dashboard tab "Network Element Access Management"
	And Select ECS Dashboard user "root"
	And "Enable" ECS Dashboard ne "marinaec (EC)"
	And Click on Network Element Save button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "Network Element Management" for "Network Element" toaster
	And Verify content "Network Element Access Saved Successfully!!!" for "Network Element" toaster
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHORIZEURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authorized  |
	And Add the below parameter values to URL
		|token              |neId|netype_id|
		|<<CMC_SSO_COOKIE>> |marinaec|EC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_appid_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Excute the query "select NEID from NETWORK_ELEMENT_ACCESS where USERID='root'"
	And Verify the below db values "is matched"
	|NEID|
	|marinaec|
	
	

@Ttv9629592c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed 
Scenario: Token Authorization Service: verify EC network element access user status  through restful with all valid mandatory parameters excluding neid,when SSO enable UI field is enabled and user assigned with network element	
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "5" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	And Click on the Toggle menu icon 
	And Click on "Control Plane" main menu option 
	And Click on "ECS Dashboard" sub menu option
	And Click on ECS Dashboard tab "Network Element Management"
	And Click on ECS Dashboard tab "Network Element Access Management"
	And Select ECS Dashboard user "root"
	And "Enable" ECS Dashboard ne "marinaec (EC)"
	And Click on Network Element Save button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "Network Element Management" for "Network Element" toaster
	And Verify content "Network Element Access Saved Successfully!!!" for "Network Element" toaster
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHORIZEURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authorized  |
	And Add the below parameter values to URL
		|token              |appid|netype_id|
		|<<CMC_SSO_COOKIE>> |CMC|EC|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_neId_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Excute the query "select NEID from NETWORK_ELEMENT_ACCESS where USERID='root'"
	And Verify the below db values "is matched"
	|NEID|
	|marinaec|
	
	

@Ttv9629593c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Completed 
	Scenario: Token Authorization Service: verify EC network element access user status  through restful with all valid mandatory parameters excluding netype_id, when SSO enable UI field is enabled and user assigned with network element	
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "5" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
		|APPID	|
		|CMC	| 
	And Click on the Toggle menu icon 
	And Click on "Control Plane" main menu option 
	And Click on "ECS Dashboard" sub menu option
	And Click on ECS Dashboard tab "Network Element Management"
	And Click on ECS Dashboard tab "Network Element Access Management"
	And Select ECS Dashboard user "root"
	And "Enable" ECS Dashboard ne "marinaec (EC)"
	And Click on Network Element Save button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "Network Element Management" for "Network Element" toaster
	And Verify content "Network Element Access Saved Successfully!!!" for "Network Element" toaster
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHORIZEURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authorized  |
	And Add the below parameter values to URL
		|token              |appid|neId|
		|<<CMC_SSO_COOKIE>> |CMC	  |marinaec|
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "ERROR=REQUIRED_ATTRIBUTE_netype_id_MISSING"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	And Excute the query "select NEID from NETWORK_ELEMENT_ACCESS where USERID='root'"
	And Verify the below db values "is matched"
	|NEID|
	|marinaec|
			
	
@Ttv9629594c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Invalid
Scenario: SSO Status Service - SSO Status should be obtainable through restful interface when Enable check box is Unchecked
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |false  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |STATUSURL        |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/status  |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "500"
    And Verify response Message as "ERROR=SSO_DISABLED"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|N	     |
	
@Ttv9629595c @SSOConfigurationServiceRally6 @SSOConfigurationService @sso @Regression,Sanity @SSOConfigurationServiceSanity @Invalid 
Scenario: SSO Status Service -  SSO Status should be obtainable through restful interface when Enable check box is checked
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Enter all sso fields with valid values
		 |ServerHost   |ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
		 |<<vcs.ip>>|<<vcs.port>>      |ssoadmin|dv3group    |2            |120                         |true  |
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	And Click Settings button
	And Click logout button
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>"
	And Click on "login" button
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	When Frame a URL with "https" with below server details
		 |server    |port        |context    |STATUSURL           |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/status  |
	And Make a rest call with basic authentication
		 |Username|Password|
		 |ssoadmin|dv3group|
    Then Verify response Status code as "200"
    And Verify response Message as "SUCCESS"
    And Excute the query "select ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ISACTIVE|
	|Y	     |
	

