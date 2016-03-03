@VCScommon
Feature: VCS common 


@TtvVCScommon1 @VCScommonRALLY1 @VCScommon @Sanity
@VCScommonSanity @UNCompleted 
Scenario: VCS common  
	Given WUI wait for "5" seconds 
	And Verify critical alert message "sssss" 
    And Verify Widget error message "The host name is not valid." 
    And Verify any widget warning message is "displayed"
    And Verify the "sso config" page title has "SSO Configuration Details"
	