@Login
Feature: Single Sign On WUI 

@TtvLogin1 @LoginRALLY1 @Login @Sanity @LoginSanity @NON-TIMS-Completed
Scenario: VCS login 
	Given Navigate to VCS "<<vcs.url>>" 
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>" 
	And Click on "login" button


@Ttv10159888c @LoginRALLY! @VCSLoginWUI  @Sanity @Completed
Scenario: VCS Login WUI : VCS console Login button operation with valid user name and password
  Given Navigate to VCS "<<vcs.url>>"
  Then Enter user name "<<vcs.login.username>>"
  Then Enter password "<<vcs.login.password>>"
  Then Click on "login" button
  Then Click Settings button
  And WUI wait for "2" seconds
  And Validate VCS Settings Dropdown Values-Log Out
  And Validate VCS Settings Dropdown Value-Help
  And Validate VCS Settings Dropdown-Help-Content
  Then Validate VCS_Settings_Dropdown_Logged_in as "<<vcs.login.username>>"
  #And Excute the Database query "select distinct USERNAME from AUDITLOG where IPADDRESS = '10.143.32.180' AND USERNAME = '<USERNAME>'"



@Ttv10159891c @LoginRALLY! @VCSLoginWUI  @Sanity @Completed
Scenario: VCS Login WUI : VCS console user logout operation
  Given Navigate to VCS "<<vcs.url>>"
  Then Enter user name "<<vcs.login.username>>"
  Then Enter password "<<vcs.login.password>>"
  Then Click on "login" button
  Then Validate the Home Screen Header
  Then Validate the Logged in user name

  Then Click Settings button
  And WUI wait for "2" seconds
  When Click on Log-out
  And WUI wait for "2" seconds
  Then Validate Log-out Screen Message as "You have been successfully logged out."
  Then Validate Home Page is displayed.

@Ttv10159892c @LoginRALLY! @VCSLoginWUI  @Sanity @Completed
Scenario: VCS Login WUI : Login with default user name and password
  Given Navigate to VCS "<<vcs.url>>"
  Then Enter user name "<<vcs.login.username>>"
  Then Enter password "<<vcs.login.password>>"
  Then Click on "login" button
  And WUI wait for "2" seconds
  Then Validate the Home Screen Header
  Then Validate the Logged in user name
  Then Click Settings button
  And WUI wait for "2" seconds
  Then Click on Log-out
  Then Enter user name "<<vcs.login.username>>"
  Then Enter password "<<vcs.login.password>>"
  Then Click on "login" button
  And WUI wait for "2" seconds
  Then Validate the Home Screen Header
  And WUI wait for "2" seconds
  Then Validate the Logged in user name


@Ttv10166546c @LoginRALLY! @VCSLoginWUI  @Sanity @Completed
Scenario: VCS Login WUI : VCS console home page details
  Given Navigate to VCS "<<vcs.url>>"
  Then Enter user name "<<vcs.login.username>>"
  Then Enter password "<<vcs.login.password>>"
  Then Click on "login" button
  And WUI wait for "2" seconds
  Then Validate the Home Screen Header
  And WUI wait for "2" seconds
  Then  Validate the Page Title as " Welcome to Videoscape Control Suite."



@Ttv10159886c @LoginRALLY! @VCSLoginWUI @Regression @Completed
 Scenario: VCS Login WUI : VCS console Login button status
    Given Navigate to VCS "<<vcs.url>>"
    Then Enter user name "<<vcs.login.username>>"
    And Enter password "<<vcs.login.password>>"
    Then WUI wait for "2" seconds
    Then Verify Button Status
      |  Widget     | Status  |
      |Log In Button | Enabled |
    Then Clear the Username/Password text box "User Name"
    Then Clear the Username/Password text box "Password"
    And WUI wait for "2" seconds
    Then Verify Button Status
      |  Widget     | Status  |
      |Log In Button  | Disabled |
    Then Validate the mask of "Username" Field
    Then Validate the mask of "Passowrd" Field


@Ttv10159889c @LoginRALLY! @VCSLoginWUI @Regression @Completed-user
Scenario: VCS Login WUI : VCS console Login button operation with valid user name and invalid password
    Given Navigate to VCS "<<vcs.url>>" 
	Then Enter user name "<<vcs.login.username>>" 
	Then  Enter password "<<vcs.login.password>>" 
	Then Click on "login" button
    Then Click on the Toggle menu icon 
	Then Click on "Console Admin" main menu option 
	Then Click on "Users, Roles & AAA" sub menu option
	And WUI wait for "1" seconds
	Then Verify User and Accounts Tab is present and click it
	Then Click on Add User button
	And WUI wait for "5" seconds
	Then Enter the following details for Adding User
	|Username|New Password|Confirm Password|
	|AutoUser1|Public@123|Public@123| 
	Then Select checkboxes corresponding to user groups "Admin,Super Users"
	Then Click the specified widget in Add User Page "Save"
	And WUI wait for "2" seconds

    #Given Navigate to VCS "<<vcs.url>>"
    #And Enter user name "AutoUser1"
    #And Enter password "Admin"
    #And Click on "login" button
    #And WUI wait for "2" seconds
    #And Verify Login "Error" Message.
    #And WUI wait for "2" seconds
    #And Enter password "Admin"
    #And Click on "login" button
    #And WUI wait for "2" seconds
    #And Enter password "Admin"
    #And Click on "login" button
    #And WUI wait for "2" seconds
    #And Verify Login "Locked" Message.

  @Ttv10159890c @LoginRALLY! @VCSLoginWUI @Regression @Completed
    Scenario: VCS Login WUI : VCS console Login button operation with invalid user name and invalid password
    Given Navigate to VCS "<<vcs.url>>"
    Then Enter user name "%^&*ABCDEFGHIJKLMNOPQRSTUVWXYZ%^&*ABCDEFGHIJKLMNOPQRSTUVWXYZ%^&*ABCDEFGHIJKLMNOPQRSTUVWXYZ%^&*ABCDEFGHIJKLMNOPQRSTUVWXYZ%^&*ABCDEFGHIJKLMNOPQRSTUVWXYZ%^&*ABCDEFGHIJKLMNOPQRSTUVWXYZ%^&*ABCDEFGHIJKLMNOPQRSTUVWXYZ%^&*ABCDEFGHIJKLMNOPQRSTUVWXYZ%^&*ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    Then  Enter password "!@@@Admin"
    Then  Click on "login" button
    Then  WUI wait for "2" seconds
    Then Verify Login "Error" Message.


  @Ttv10166548c @LoginRALLY! @VCSLoginWUI @Regression @Completed
    Scenario: VCS Login WUI : VCS console home page Bread Crumb
    Given Navigate to VCS "<<vcs.url>>"
    And Enter user name "<<vcs.login.username>>"
    And Enter password "<<vcs.login.password>>"
    And Click on "login" button
    Then I assert if the breadcrumb displayed is "Home icon" by comparing the image "homeicon_original.png"
    Then I verify the "Separator icon " by comparing the image "separatoricon_original.png"
    Then I verify the "Home" text
    Then I verify the "favourite icon" by star icon "favouriteicon_original.png"

    @Ttv10166549c  @LoginRALLY! @VCSLoginWUI @Regression @Completed
    Scenario: VCS Login WUI : VCS console Breadcrumb favorite icon operation
      Given Navigate to VCS "<<vcs.url>>"
      And Enter user name "<<vcs.login.username>>"
      And Enter password "<<vcs.login.password>>"
      And Click on "login" button
      Then I verify the "favourite icon" by star icon "favouriteicon_original.png"

    @Ttv10166547c @LoginRALLY! @VCSLoginWUI @Regression @Completed
    Scenario: VCS Login WUI : VCS console main menu
      Given Navigate to VCS "<<vcs.url>>"
      And Enter user name "<<vcs.login.username>>"
      And Enter password "<<vcs.login.password>>"
      And Click on "login" button
      And WUI wait for "2" seconds
      And Validate Home Page is displayed.
      And I check "Cisco logo"
      Then I verify the "Videoscape Control Suite Console" text

    @Ttv10166546c @LoginRALLY! @VCSLoginWUI @Regression @Completed
    Scenario: VCS Login WUI : VCS console home page details
      Given Navigate to VCS "<<vcs.url>>"
      And Enter user name "<<vcs.login.username>>"
      And Enter password "<<vcs.login.password>>"
      And Click on "login" button
      And WUI wait for "2" seconds
      Then I verify the "Videoscape Control Suite Console" text
      Then I Verrify "Main Menu"


    @Ttv10166556c @LoginRALLY! @VCSLoginWUI @Regression @Completed
      Scenario: VCS Login WUI : VCS console global search on the main menu
        Given Navigate to VCS "<<vcs.url>>"
        Then Enter user name "<<vcs.login.username>>"
        Then  Enter password "<<vcs.login.password>>"
        Then  Click on "login" button
        And WUI wait for "2" seconds
        Then I validate "global search" in main menu

    @Ttv10166551c @LoginRALLY! @VCSLoginWUI @Regression @Completed
      Scenario: VCS Login WUI : VCS console Toggle menu Pin option
        Given Navigate to VCS "<<vcs.url>>"
        Then Enter user name "<<vcs.login.username>>"
        Then  Enter password "<<vcs.login.password>>"
        Then  Click on "login" button
        Then Click on the Toggle menu icon
        And WUI wait for "2" seconds
        Then Validate the "Pin" button
        Then Click on "Pin"
        And WUI wait for "2" seconds
        Then Click on "UnPin"
        #And Validate the "UnPin" button
        
    @Ttv10166552c @LoginRALLY! @VCSLoginWUI @Regression @Completed
      Scenario: VCS Login WUI : VCS console Toggle menu Navigation option
      	Given Navigate to VCS "<<vcs.url>>"
      	Then Enter user name "<<vcs.login.username>>"
      	Then  Enter password "<<vcs.login.password>>"
      	Then Click on "login" button
     	Then  Click on the Toggle menu icon
       	Then  Verify Navigation Button
       	Then Validate for Console Admin Menu
     	
        
    @Ttv10166558c @LoginRALLY! @VCSLoginWUI @Sanity @Completed
    Scenario: VCS Login WUI : VCS console help content  under user setting option
        Given Navigate to VCS "<<vcs.url>>"
        Then Enter user name "<<vcs.login.username>>"
        Then Enter password "<<vcs.login.password>>"
        Then Click on "login" button
        Then Click Settings button
        Then Click VCS Settings-Help-Content
        Then Focus Help Window
     
     @Ttv10166557c @LoginRALLY! @VCSLoginWUI @Sanity  @Completed
      Scenario: VCS Login WUI : VCS console help content  under user setting option
        Given Navigate to VCS "<<vcs.url>>"
        Then Enter user name "<<vcs.login.username>>"
        Then Enter password "<<vcs.login.password>>"
        Then Click on "login" button
        Then Click Settings button
        Then Validate "Log-out" Position
        Then Validate "Help_Content" Position 
        
   
        
        
   @Ttv10166550c @LoginRALLY! @VCSLoginWUI @Regression @In-Progress
   Scenario: VCS Login WUI : VCS console Toggle menu details
     Given Navigate to VCS "<<vcs.url>>"
        Then Enter user name "<<vcs.login.username>>"
        Then Enter password "<<vcs.login.password>>"
        Then Click on "login" button
        
   
    @Ttv10166553c @LoginRALLY! @VCSLoginWUI @Regression @test-anusha-pend
     Scenario: VCS Login WUI : VCS console Toggle menu Console Admin options
     Given Navigate to VCS "<<vcs.url>>"
        Then Enter user name "<<vcs.login.username>>"
        Then Enter password "<<vcs.login.password>>"
        Then Click on "login" button
        Given Click on the Toggle menu icon
        Then Validate for Console Admin Menu
       
        
        
        
  @Ttv10166553c @LoginRALLY! @VCSLoginWUI @Regression @test-anusha-pend
         Scenario: VCS Login WUI : VCS console Toggle menu Console Admin options
      	Given Navigate to VCS "<<vcs.url>>"
      	Then Enter user name "<<vcs.login.username>>"
      	Then Enter password "<<vcs.login.password>>"
      	Then Click on "login" button
     	Then Click on the Toggle menu icon
     	Then Validate for Console Admin Menu
     	
     	
     	
  @Ttv10166555c @LoginRALLY! @VCSLoginWUI @Regression @Completed
  Scenario: VCS Login WUI : VCS console Toggle menu favorites button operation
  Given Navigate to VCS "<<vcs.url>>"
      	Then Enter user name "<<vcs.login.username>>"
      	Then Enter password "<<vcs.login.password>>"
      	Then Click on "login" button
    	Then Click on "Favorite" 
    	Then Click on the Toggle menu icon
    	Then Click on "Console Admin" main menu option 
		Then Click on "SSO Configuration" sub menu option
		Then Click on "Favorite" 
       	Then  Click on the Toggle menu icon
      	Then Click on "Favorite_Toggle" 
      	Then WUI wait for "2" seconds
      	Then Validate Favorite Screen "#pageId=home_page_dashboard_pageId"
      	Then Validate Favorite Screen "#pageId=sso_config_page"
      	Then Click on "Favorite_Validate_Remove"
      	
      	
      	
      	
      	
      	
        
    
        
        
     
    
    
     
    
        
        
        
        
     
     
        
    
    
        
        
      
      


     



