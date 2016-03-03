@SSOConfiguration 
Feature: Single Sign On WUI 
Background: 
	Given Navigate to VCS "<<vcs.url>>" 
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>" 
	And Click on "login" button 

	
@Ttv9629605c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed 
Scenario: SSO WUI_Verify all the SSO Configuration Details fields entered valid values are saved to DB  and there is no service configured to publish sso configuration
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
    Then Verify critical alert message "saved successfully but unable to publish configuration!!!"
	And Click on critical alert message OK button
	And WUI wait for "1" seconds
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,SSOSTATUSURL,ISACTIVE,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|SSOSTATUSURL     |ISACTIVE|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |<<vcs.port>>|     https    |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 120000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       |rs/ssosrvc/status| Y	    | 7200000              |

 @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity  
Scenario: SSO WUI_Verify all the SSO Configuration Details fields entered valid values are saved to DB  and there is no service configured to publish sso configuration
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
	|<<vcs.ip>>|<<vcs.port>>|ssoadmin|dv3group|2|120|true|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
    Then Verify critical alert message "saved successfully but unable to publish configuration!!!"
	And Click on critical alert message OK button
	And WUI wait for "1" seconds
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,SSOSTATUSURL,ISACTIVE,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|SSOSTATUSURL     |ISACTIVE|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |<<vcs.port>>|     https    |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 120000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       |rs/ssosrvc/status| Y	    | 7200000              |


@Ttv9629604c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed 
Scenario: SSO WUI_Verify all the SSO Configuration Details fields entered valid values are saved to DB  and published to register service successfully
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
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,SSOSTATUSURL,ISACTIVE,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|SSOSTATUSURL     |ISACTIVE|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |<<vcs.port>>      |      https   |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 120000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       |rs/ssosrvc/status| Y	    | 7200000              |


@Ttv9629529c @SSOConfigurationRALLY2 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @DB 
Scenario: verify sso configuration tables creation after successful VCS console deployment  
	Then Excute the query "select table_name from user_tables where table_name='SSO_CONFIG'"
	And Verify the below db values "is matched"
	|TABLE_NAME|
	|SSO_CONFIG|
    And Excute the query "select table_name from user_tables where table_name='SSO_TOKEN'"
	And Verify the below db values "is matched"
	|TABLE_NAME|
	|SSO_TOKEN|
	And Excute the query "select table_name from user_tables where table_name='NETWORK_ELEMENT_ACCESS'"
	And Verify the below db values "is matched"
	|TABLE_NAME|
	|NETWORK_ELEMENT_ACCESS|
	
	
@Ttv9629530c @SSOConfigurationRALLY3 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @DB
Scenario: verify SSO_CONFIG table fields   
	Then Excute the query "select t1.column_name || '-'|| substr(data_type||'('||data_length||')', 0, 20) as column_details from all_tab_columns t1, all_col_comments t2 where t1.table_name = t2.table_name and t1.column_name = t2.column_name and t1.table_name = 'SSO_CONFIG' ORDER BY COLUMN_ID"
	And Verify the below db values "is matched"
	|COLUMN_DETAILS                   |
    |ID-NUMBER(22)                   |
	|USERNAME-VARCHAR2(32)            |
	|PASSWORD-VARCHAR2(255)           |
	|SERVERPROTOCOL-VARCHAR2(6)       |
	|SERVERHOST-VARCHAR2(256)         |
	|SERVERPORT-NUMBER(22)            |
	|CONTEXT-VARCHAR2(64)             |
	|AUDITSERVICEURL-VARCHAR2(256)    |
	|ROLESRETRIEVALURL-VARCHAR2(256)  |
	|AUTHENTICATEURL-VARCHAR2(256)    |
	|AUTHORIZATIONURL-VARCHAR2(256)   |
	|LOGOUTURL-VARCHAR2(256)          |
	|RETRIEVETOKENURL-VARCHAR2(256)   |
	|CACHEINTERVAL-NUMBER(22)         |
	|GOTOURLPARAMNAME-VARCHAR2(32)    |
	|TOKENPARAMNAME-VARCHAR2(32)      |
	|HOSTCOOKIEPARAMNAME-VARCHAR2(32) |
	|APPIDPARAMNAME-VARCHAR2(32)      |
	|LOGINREDIRECTURL-VARCHAR2(256)   |
	|LOGOUTREDIRECTURL-VARCHAR2(256)  |
	|SSOSTATUSURL-VARCHAR2(256)       |
	|ISACTIVE-VARCHAR2(1)             |
	|TOKENMAXINACTIVITYTIME-NUMBER(22)|
	
	
@Ttv9629531c @SSOConfigurationRALLY4 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @DB
Scenario: verify SSO_TOKEN table fields   
	Then Excute the query "select t1.column_name || '-'|| substr(data_type||'('||data_length||')', 0, 20) as column_details from all_tab_columns t1, all_col_comments t2 where t1.table_name = t2.table_name and t1.column_name = t2.column_name and t1.table_name = 'SSO_TOKEN' ORDER BY COLUMN_ID"
	And Verify the below db values "is matched"
	|COLUMN_DETAILS                   |
    |ID-NUMBER(22)                    |
    |TOKEN-VARCHAR2(64)               |
    |MASTERTOKEN-VARCHAR2(64)         |
    |ONETIMETOKEN-VARCHAR2(64)        |
    |APPID-VARCHAR2(64)               |
    |SESSIONID-VARCHAR2(64)           |
    |USERNAME-VARCHAR2(32)            |
    |CREATED_TIMESTAMP-NUMBER(22)     |
    |LAST_ACCESS_TIMESTAMP-NUMBER(22) |
    |USERDETAILS-CLOB(4000)           |
    
    
@Ttv9629532c @SSOConfigurationRALLY5 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @DB
Scenario: verify NETWORK_ELEMENT_ACCESS table fields   
	Then Excute the query "select t1.column_name || '-'|| substr(data_type||'('||data_length||')', 0, 20) as column_details from all_tab_columns t1, all_col_comments t2 where t1.table_name = t2.table_name and t1.column_name = t2.column_name and t1.table_name = 'NETWORK_ELEMENT_ACCESS' ORDER BY COLUMN_ID"
	And Verify the below db values "is matched"
	|COLUMN_DETAILS          |
	|ID-NUMBER(22)           |
	|USERID-VARCHAR2(64)     |
	|NEID-VARCHAR2(256)      |
	|NETYPE_ID-VARCHAR2(256) |
	
@Ttv9629597c @SSOConfigurationRALLY6 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed 
Scenario: SSO WUI_Verify SSO configuration page are able to load with proper page title and panel Description
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And Validate the breadcrumb on console equals "/ ... / User Administration / SSO Configuration"
	And Verify panel titles "Configure SSO Details" are displayed
	
@Ttv9629598c @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed
Scenario: SSO WUI_Verify the SSO Configuration Details fields
Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|
	|<<vcs.ip>>|<<vcs.port>>|ssoadmin|dv3group|2|120|
	And Verify mandatory fields "Server Host,User Name,User Password,Token Maximum Inactive Interval (in minutes)"

@Ttv9629599c @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @tip @Completed @demo
Scenario: SSO WUI_Verify the SSO Configuration Details fields tool tip
Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "2" seconds
	And Verify tooltip for Server Host field
	And Hover over the tooltip icon and verify tooltip content 
	|Server Port :|User Name|User Password|Cache Interval (in minutes) :|Token Maximum Inactive Interval (in minutes)|Enable :|
	|Only includes range from 1-65535. If not specified, default port 443 for HTTPS protocol is assumed.|Composed of any characters except space with length no longer than 32 and no less than 6.|Composed of any characters except space with length no longer than 32 and no less than 6.|Cache Interval used by IDP and remote client to determine how often token information is updated in memory.Only allow values from range 0(mins) - 120(mins).|Maximum Time User can remain inactive. Only allow values from range 2(mins) - 120(mins).|Enable SSO.|
	
@Ttv9629602c @vcsrestart @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed
Scenario: SSO WUI_Verify all the SSO Configuration Details fields default entries in DB and same is displayed in web user interface 
	And Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Excute the query "select SERVERHOST,SERVERPORT,USERNAME, CACHEINTERVAL,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Get values from UI
	And Verify DB match
	


@Ttv9629603c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed 
Scenario: SSO WUI - Verify all the SSO Configuration Details fields entered valid values are saved to DB  and there is issue publishing configuration
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
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
    Then Verify critical alert message "saved successfully but unable to publish configuration!!!"
	And Click on critical alert message OK button
	And WUI wait for "1" seconds
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |<<vcs.port>>|     https    |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 120000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       		 | 7200000              |

	
	 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed
Scenario: SSO WUI - Verify toaster message , when are is error in loading sso configuration
	Given Click on the Toggle menu icon 
	And Stop oracle db and verify message
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	Then Verify critical alert message "Database is not reachable currently, Please try after some time"
	And Click on critical alert message OK button
	And WUI wait for "1" seconds
	
@Ttv9629607c @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed
Scenario: SSO WUI - Verify toaster when there is any error saving SSO Configuration
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
	And Stop oracle db and verify message
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	 Then Verify critical alert message "Database is not reachable currently, Please try after some time"
	And Click on critical alert message OK button
	And WUI wait for "1" seconds
	
@Ttv9629609c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @test1
Scenario Outline: SSO WUI_Verify Server Host field with invalid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig Server host "<ip>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Disabled |
	|Reset Button | Enabled |
	And Verify Widget error message "The host name is not valid."
	And Excute the query "select SERVERHOST from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is not matched"
	|SERVERHOST|
	|<ip>|
	Examples:
	|ip|
	|-10.78.203.118|
	|10.78.203.118-|
	|.10.78.203118|
	|10.78.203118.|
	|-10.-78.-203.-118|
	|10-.78-.203-.118-|
	|substringsubstringsubstringsubstringsubstringsubstringsubstrings.78.203.118|
	|substringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsubstringsub|
	
@Ttv9629611c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @mani @test1
Scenario Outline: SSO WUI_Verify Server Port field with invalid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig Server port "<port>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Disabled |
	|Reset Button | Enabled |
	And Verify Widget error message "Server Port is not valid."
	And Excute the query "select SERVERPORT from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is not matched"
	|SERVERPORT|
	|<port>|
	Examples:
	|port|
	|0|
	|65536|
	|-ABCD%^!|

@Ttv9629612c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @pending @mani @test1
Scenario: SSO WUI_Verify Server Port field with default values
Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
	|<<vcs.ip>>||ssoadmin|dv3group|2|120|true|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
    Then Verify critical alert message "saved successfully but unable to publish configuration!!!"
	And Click on critical alert message OK button
	And WUI wait for "1" seconds
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,SSOSTATUSURL,ISACTIVE,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|SSOSTATUSURL     |ISACTIVE|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |443       |      https   |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 120000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       |rs/ssosrvc/status| Y	    | 7200000              |


@Ttv9629614c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed  @test 
Scenario Outline:SSO WUI_Verify User Name field with invalid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig User name "<username>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Disabled |
	|Reset Button | Enabled |
	And Verify Widget error message "User Name is not valid."
	And Excute the query "select USERNAME from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is not matched"
	|USERNAME|
	|<username>|
	Examples:
	|username|
	|ssoa dmin|
	|ssoadm i|
	
	

@Ttv9629616c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @mani @test1
Scenario Outline:SSO WUI_Verify User Password field with invalid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig user password "<password>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Disabled |
	|Reset Button | Enabled |
	And Verify Widget error message "The password is not valid."
	And Excute the query "select PASSWORD from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is not matched"
	|PASSWORD|
	|<password>|
	Examples:
	|password|
	|ssoadmi n|
	|s amdin|	


@Ttv9629618c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @mani @test1
Scenario Outline:SSO WUI_Verify Cache Interval field with invalid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig Cache Interval "<cacheinterval>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Disabled |
	|Reset Button | Enabled |
	And Verify Widget error message "The cache interval is invalid."
	And Excute the query "select CACHEINTERVAL from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is not matched"
	|CACHEINTERVAL|
	|<cacheinterval_db>|
	Examples:
	|cacheinterval|cacheinterval_db|
	|-1|-60000|
	|121|7260000|

@Ttv9629620c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @manis @test1
Scenario Outline:SSO WUI_Verify Token Maximum Inactive Interval field with invalid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig Token Maximum Inactive Interval "<tokenmax>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Disabled |
	|Reset Button | Enabled |
	And Verify Widget error message "The token maximum inactive interval is invalid."
	And Excute the query "select TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is not matched"
	|TOKENMAXINACTIVITYTIME|
	|<tokenmax_db>|
	Examples:
	|tokenmax|tokenmax_db|
	|-1|-60000|
	|121|7260000|
	|1|60000|
	
@Ttv9629621c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @mani @test1
Scenario: SSO WUI_Verify sso Enabled field is able to select the check box
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
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |<<vcs.port>>      |      https   |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 120000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       | 7200000              |
	
@Ttv9629622c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @mani @test1
Scenario: SSO WUI_Verify sso Enabled field is able to unselect the check box
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
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,SSOSTATUSURL,ISACTIVE,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|SSOSTATUSURL     |ISACTIVE|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |<<vcs.port>>      |      https   |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 120000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       |rs/ssosrvc/status| N	    | 7200000              |


@Ttv9629608c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @manis @test1
Scenario Outline: SSO WUI_Verify Server Host field with valid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig Server host "<ip>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select SERVERHOST from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is matched"
	|SERVERHOST|
	|<ip>|
	Examples:
	|ip|
	|10.78.203.118|
	|whataj-_.007|
	|10.78.203.substringsubstringsubstringsubstringsubstringsubstringsubstring|
	|substringsubstringsubstringsubstringsubstringsubstringsubstring|
	|123456789012345678901234567890123456789012345678901234567890123|
	|substringsubstringsubstringsubstringsubstringsubstringsubstring.substringsubstringsubstringsubstringsubstringsubstringsubstring.substringsubstringsubstringsubstringsubstringsubstringsubstring.substringsubstringsubstringsubstringsubstringsubstringsubstring|
	
@Ttv9629610c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @manis @test1
Scenario Outline: SSO WUI_Verify Server Port field with valid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig Server port "<port>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
 	And Verify any widget warning message is "not displayed"
 	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select SERVERPORT from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is matched"
	|SERVERPORT|
	|<port>|
	Examples:
	|port|
	|1|
	|65535|
	|22220|

@Precheck @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity  @test1
Scenario: SSO WUI_Verify User Name field with valid values - Precheck
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig Cache Interval "2"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
    Then Verify critical alert message "saved successfully but unable to publish configuration!!!"
	And Click on critical alert message OK button
	And WUI wait for "1" seconds
	And Excute the query "select CACHEINTERVAL from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is matched"
	|CACHEINTERVAL|
	|120000|
	

@Ttv9629613c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @test
Scenario Outline:SSO WUI_Verify User Name field with valid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig Cache Interval "2"
	When Enter ssoconfig User name "<username>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
And Excute the query "select USERNAME from SSO_CONFIG where CACHEINTERVAL='120000'"
	Then Verify the below db values "is matched"
	|USERNAME|
	|<user_name>|
	Examples:
	|username|user_name|
	|ssoadminssoadminssoadminssoadmin|ssoadminssoadminssoadminssoadmin|
	|ssoadmi|ssoadmi|
	|ssoadminssoadminssoadminssoadmins|ssoadminssoadminssoadminssoadmin|
	|123456|123456|
	|12345678901234567890123456789012|12345678901234567890123456789012|
	|123456789012345678901234567890123|12345678901234567890123456789012|
	|12345*|12345*|
	|!@#$%^&*()!@#$%^&*()!@#$%^&*()!@!|!@#$%^&*()!@#$%^&*()!@#$%^&*()!@|
	|ssoadmin|ssoadmin|
	
	

@Ttv9629615c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @test1
Scenario Outline:SSO WUI_Verify User Password field with valid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig user password "<password>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select PASSWORD from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is matched"
	|PASSWORD|
	|<password_db>|
	Examples:
	|password|password_db|
	|ssoadminssoadminssoadminssoadmin|Cin5oQyZGWd9BJ7WC52PjmW9ABQw3jb7BMkaqa07rgv5K3DoYrXkZw==|
	|ssoadm|T7+QjihruCA=|
	|ssoadminssoadminssoadminssoadmins|Cin5oQyZGWd9BJ7WC52PjmW9ABQw3jb7BMkaqa07rgv5K3DoYrXkZw==|
	|123456|SgxCgtgGfvE=|
	|12345678901234567890123456789012|IAiijtQKZm+8gEgPOKD3CwRtS+rkObOVzZm4m+EQ3uveHsqzsN8UsQ==|
	|123456789012345678901234567890123|IAiijtQKZm+8gEgPOKD3CwRtS+rkObOVzZm4m+EQ3uveHsqzsN8UsQ==|
	|12345*|UhSXpJxrZKs=|
	|!@#$%^&*()!@#$%^&*()!@#$%^&*()!@!|OV0Gc75TfeJbMcRZXLFaxfQrgNDpQLkTn/Cppix2hjvFhc43lhrV3w==|
	|dv3group|Dw6MxQ67z2rSTeDgD9jAkQ==|	


@Ttv9629617c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @manis @test1
Scenario Outline:SSO WUI_Verify Cache Interval field with valid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig Cache Interval "<cacheinterval>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select CACHEINTERVAL from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is matched"
	|CACHEINTERVAL|
	|<cacheinterval_db>|
	Examples:
	|cacheinterval|cacheinterval_db|
	|1|60000|
	|120|7200000|
	|0|0|

@Ttv9629619c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed  @test1
Scenario Outline:SSO WUI_Verify Token Maximum Inactive Interval field with valid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter ssoconfig Token Maximum Inactive Interval "<tokenmax>"
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is matched"
	|TOKENMAXINACTIVITYTIME|
	|<tokenmax_db>|
	Examples:
	|tokenmax|tokenmax_db|
	|2|120000|
	|120|7200000|
	|55|3300000|
	
@Ttv9629600c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @mani @test
Scenario: SSO WUI_Verify SSO configuration fields with mandatory icon
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Verify mandatory fields "Server Host,User Name,User Password,Token Maximum Inactive Interval (in minutes)"

@Ttv9629601c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @mani @test
Scenario: SSO WUI_Verify SSO configuration page buttons	
Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	
	
@Ttv9629623c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @mani @test
Scenario: SSO WUI_verify the sso login user  has logged out after specified amount of idea time from the UI
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|
	|<<vcs.ip>>|<<vcs.port>>|ssoadmin|dv3group|2|2|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
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
	And Excute the query "select TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is matched"
	|TOKENMAXINACTIVITYTIME|
	|120000|
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
	|APPID|
	|CMC  |
	And WUI wait for "121" seconds
	And Validate logout message
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='|
	And Verify the below db values "is empty"
	|APPID|
	|CMC  |  
	

@Ttv9629624c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @mani @test 
Scenario: UI - verify the sso login user  has not been logged out after specified amount of time when active action on the UI
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|
	|<<vcs.ip>>|<<vcs.port>>|ssoadmin|dv3group|2|2|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And Read the cookie variable "CMC_SSO_COOKIE"
	And WUI wait for "2" seconds	
	And Excute the query "select TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	Then Verify the below db values "is matched"
	|TOKENMAXINACTIVITYTIME|
	|120000|
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
	|APPID|
	|CMC  |
	And Click Settings button
	And Click logout button
	And Validate logout message
	And Enter user name "<<vcs.login.username>>" 
	And Enter password "<<vcs.login.password>>" 
	And Click on "login" button 
	And WUI wait for "60" seconds
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "100" seconds
	And Read the cookie variable "CMC_SSO_COOKIE"
	And Execute query with following parameters
		 |token         |query|
		 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where TOKEN='| 
	And Verify the below db values "is matched"
	|APPID|
	|CMC  |
	And Click Settings button
	And Click logout button
	And Validate logout message
	
@Ttv9688751c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed @test-demo
Scenario: SSO WUI_Verify proper error messages and invalid icon are thrown for all fields when they are left blank
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	And Clear the text box "Server Host"
	And Clear the text box "User Name"
	And Clear the text box "Password"
	And Clear the text box "Max Inactive"	
	And WUI wait for "5" seconds
	And Click the WebElement "Server Host"
	And Validate invalid icons for corresponding fields "Server Host"
	And Validate tooltip content "This value is required." on entering invalid data
	#And Verify Widget error message "This value is required."
	And Click the WebElement "User Name"
		And Validate invalid icons for corresponding fields "User Name"
	And Validate tooltip content "This value is required." on entering invalid data
	And Click the WebElement "Password"
	And Validate invalid icons for corresponding fields "User Password"	
	And Validate tooltip content "This value is required." on entering invalid data
	And Click the WebElement "Max Inactive"
	And Validate invalid icons for corresponding fields "Token Maximum Inactive Interval (in minutes)"
	#And WUI wait for "2" seconds
	And Validate tooltip content "This value is required." on entering invalid data

@Ttv9688535c @reset @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed 
Scenario: SSO WUI_Verify Reset button functionality

	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|
	|<<vcs.ip>>|6605|ssoadmin|dv3group|2|120|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,SSOSTATUSURL,ISACTIVE,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|SSOSTATUSURL     |ISACTIVE|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |<<vcs.port>>      |      https    |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 120000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       |rs/ssosrvc/status| Y	    | 7200000              |
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
	|10.78.217.118|5524|ssoadmin1|1dv3group|21|20|false|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Reset" button
	And Excute the query "select SERVERHOST,SERVERPORT,USERNAME, CACHEINTERVAL,TOKENMAXINACTIVITYTIME,ISACTIVE from SSO_CONFIG where USERNAME='ssoadmin'"
	And Get values from UI
	And Verify DB match
	
@Ttv9688740c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed 
Scenario: SSO WUI_Verify SSO Configuration Details for mandatory fields are saved to DB on entering valid values

	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|
	|<<vcs.ip>>|118|ssoadmin|dv3group|5|120|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |118      |      https   |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 300000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       | 7200000              |
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|
	|<<vcs.ip>>||ssoadmin|dv3group||120|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |118      |      https   |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 300000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       | 7200000              |
	
	
@Ttv9688743c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed 
Scenario: SSO WUI_Verify SSO Configuration Details on entering all fields without entering optional field Server Port are getting saved to DB on entering valid values

	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|
	|<<vcs.ip>>|118|ssoadmin|dv3group|5|120|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |118      |      https   |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 300000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       | 7200000              |
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
	|<<vcs.ip>>||ssoadmin|dv3group|2|120|true|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,SSOSTATUSURL,ISACTIVE,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|SSOSTATUSURL     |ISACTIVE|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |118      |      https   |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 120000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       |rs/ssosrvc/status| Y	    | 7200000              |
	

@Ttv9629612c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed 
Scenario: Verify Server Port field has default values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|
	|<<vcs.ip>>||ssoadmin|dv3group|5|120|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |443      |      https   |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 300000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       | 7200000              |
	
@Ttv9688748c @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity @Completed 
Scenario: SSO WUI_Verify SSO Configuration Details on entering all fields without entering optional field Cache Interval are getting saved to DB on entering valid values
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|
	|<<vcs.ip>>|118|ssoadmin|dv3group|5|120|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |118      |      https   |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 300000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       | 7200000              |
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
	|<<vcs.ip>>|118|ssoadmin|dv3group||120|true|
	And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |
	And Verify any widget warning message is "not displayed"
	And Click on ssoconfig "Save" button
	And WUI wait for "2" seconds
	Then Verify success toaster
	And Verify title "SSO Configuration" for "SSO" toaster
	And Verify content "saved and published successfully." for "SSO" toaster
	And Excute the query "select ID,USERNAME,SERVERPORT,SERVERPROTOCOL,SERVERHOST,CONTEXT,AUDITSERVICEURL,ROLESRETRIEVALURL,AUTHENTICATEURL,AUTHORIZATIONURL,LOGOUTURL,RETRIEVETOKENURL   ,CACHEINTERVAL,GOTOURLPARAMNAME,TOKENPARAMNAME,HOSTCOOKIEPARAMNAME,APPIDPARAMNAME,LOGINREDIRECTURL,LOGOUTREDIRECTURL,SSOSTATUSURL,ISACTIVE,TOKENMAXINACTIVITYTIME from SSO_CONFIG where USERNAME='ssoadmin'"
	And Verify the below db values "is matched"
	|ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|SSOSTATUSURL     |ISACTIVE|TOKENMAXINACTIVITYTIME|
	| 1|ssoadmin |118      |      https   |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 300000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       |rs/ssosrvc/status| Y	    | 7200000              |


	
@test- @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity  
Scenario: SSO WUI_Verify all the SSO Configuration Details fields entered valid values are saved to DB  and there is no service configured to publish sso configuration
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	

@demo @SSOConfigurationRALLY1 @SSOConfiguration @Regression,Sanity @SSOConfigurationSanity  
Scenario: SSO WUI_Verify all the SSO Configuration Details fields entered valid values are saved to DB  and there is no service configured to publish sso configuration
	Given Click on the Toggle menu icon 
	And Click on "Console Admin" main menu option 
	And Click on "SSO Configuration" sub menu option
	And WUI wait for "1" seconds
	When Enter all sso fields with valid values
	|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|
	|<<vcs.ip>>|<<vcs.port>>|ssoadmin|dv3group|2|120|
	And Click on ssoconfig "Save" button
	
@demoo-db
Scenario: test
And Excute the query "select TO_CHAR(VALUE) from WCSPREFERENCE where INSTANCE_VERSION!=0"


	