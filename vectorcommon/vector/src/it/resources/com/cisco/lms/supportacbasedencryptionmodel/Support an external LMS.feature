@SupportanexternalLMS
Feature: Support an external LMS

@LMS_test  @Stage2_1 @Ttv10748281c @Sanity @Completed-Demo @DEPLOY-VIDEO-SOURCE-INSTANCE
Scenario Outline: DEPLOY-VIDEO-SOURCE-INSTANCE - CREATE and Verify Source Detail in ESN   
# Delete and Create source in PKPSm 
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
# PCB DB Validation only 
And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
# Validate in AC MAnager
And verfiy the "AC Manager" db for Validation 
|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
# Delete and Create Source in Service Manager 
Given Load settings of "Service Manager"
And Frame the rest URL using the below parameters 
|ip			   	 |port		      |mandatoryparam	  		         |tracingToken	 |sourceSystem	 |controllerId	 |
|<<svcemgr.ip>>  |<<svcemgr.port>>|ServicesManager/sources/<SOURCEID>      |<<tracing.token>>|<<source.system>>|<<CONTROLLERID>>|
And Make a "DELETE" rest call using above details
And Get Store value "<<r3_version>>"
And Construct the request body using "sourcepayload.json" and update below parameters
|sourceId|sourceName|
|<SOURCEID>  |<SOURCENAME>  |
And Add below headers for the rest query
|Content-Type|
|<<r3_version>>|
And Make a "POST" rest call using above details
And Verify rest response Status Code details
 |Status Code|Status Code Action|
 |201|Created|
 # ESN validation
#Given Load property from "com.cisco.esn.esn"
Given Load settings of "ESN"
And I inject EC password to firefox
And I navigate to ESN url
And I click on "ESN" Mega Menu
And I click on "Source" link from megamenu 
And I filter the "Source UI" by "Source ID" 
And I enter the source value "<SOURCEID>"
And I click on "Show" button in "source" UI
And WUI wait for "3" seconds 
And I select the checkbox corresponding to "<SOURCEID>" 
And I click on "View" button in "source" UI 
And WUI wait for "3" seconds 
And Validate the following details
	|Source ID|
	|<SOURCEID>     |
And verfiy the "ESN" db for Validation
|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION |                                 
|  sourceident   |pdsourceident  |sourcename='<SOURCENAME>'     | sourceident=<SOURCEID>   |                                 
And Print the Scenario
	

Examples:
|SOURCEID|SOURCENAME|superCasId|accessCriteria                      |
|11621 |SAutosource301| 0E110000 |0E11000000000000090201AF020101D77E9C|