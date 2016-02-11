@PKCUISupport
Feature: PKCUI Support

Background: SD details
@LMS_test2  @Stage2_14 @Completed @Ttv10765674c @CreateavideoproductforNon-DTAapplicableSourcewithunlimitedsegmentsverifythesameinthesourceandsegmentlistpageinPKCWUI
Scenario Outline: Verify sources are listed and segments associated with these sources.
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|HD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

#SegmentCreate
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId|segmentName|sourceId|status|segmentDuration|blackOut|fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE|PERMITTED|SET|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|

# PCB DB Validation only 
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_SPOTLIGHT,BLACKOUT_X_CENTROID    |SEGMENT |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_SPOTLIGHT=0,BLACKOUT_X_CENTROID=20|
#AC Manager Validation
And verfiy the "AC Manager" db for Validation 
|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
#VCS Console 
Given Load property from "com.cisco.vcs.vcs"
Given Navigate to VCS "<<vcs.url>>" 
And Enter user name "<<vcs.login.username>>" 
And Enter password "<<vcs.login.password>>" 
And Click on "login" button 
And WUI wait for "2" seconds
Given Click on the Toggle icon
And WUI wait for "2" seconds
And Click on "Control Plane" mainmenu option
And Click on "Source list" submenu option
And WUI wait for "5" seconds
#And Scroll down to load data from all pages on console
And Select radio button corresponding to record "<SOURCEID>" in table
And Validate all entries of a table row having unique value "<SOURCEID>"
|DTA Applicable  |SC Id |SCC Mode |DLNA  |
|false           |-     |INIT     | false|
And WUI wait for "5" seconds
And Click the prime-button "Segments" present in console
And WUI wait for "3" seconds
#And Validate all entries of a table row having unique value "<SegmentName>"
#|Controller ID|Source ID|Segment name|Status|

#|<<CONTROLLERID>>|<SOURCEID>|<SegmentName>|ACTIVE|
And Print the Scenario


Examples:

|SOURCEID|SOURCENAME  |superCasId|accessCriteria                        |PKGNAME|SegmentName|
|12930   |SAutoSour930| 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg1|AutoSeg930|

@LMS_test2  @Stage2_14 
Scenario Outline: Verify sources are listed and segments associated with these sources.
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|HD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

#SegmentCreate
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId|segmentName|sourceId|status|segmentDuration|blackOut|fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE|PERMITTED|SET|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|

# PCB DB Validation only 
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_SPOTLIGHT,BLACKOUT_X_CENTROID    |SEGMENT |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_SPOTLIGHT=0,BLACKOUT_X_CENTROID=20|
#AC Manager Validation
And verfiy the "AC Manager" db for Validation 
|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
#VCS Console 
Given Load property from "com.cisco.vcs.vcs"
Given Navigate to VCS "<<vcs.url>>" 
And Enter user name "<<vcs.login.username>>" 
And Enter password "<<vcs.login.password>>" 
And Click on "login" button 
And WUI wait for "2" seconds
Given Click on the Toggle icon
And WUI wait for "2" seconds
And Click on "Control Plane" mainmenu option
And Click on "Source list" submenu option
And WUI wait for "5" seconds
Given Click on "<SOURCEID>" radio button
And Click the prime-button "Segments" present in console
And Print the Scenario


Examples:
|SOURCEID|SOURCENAME  |superCasId|accessCriteria                        |PKGNAME|SegmentName|baseUrl                    |
|12630   |SAutoSour630| 0E110000 |0E11000000000000090201AF020101D77E9C |Auto_pkg1|AutoSeg99|https://10.78.206.142:8443/|

@LMS_test2  @Stage2_14  @Completed-stage
Scenario Outline: Verify sources are listed and segments associated with these sources.
Given Load settings of "PK_PSM" 
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_SPOTLIGHT,BLACKOUT_X_CENTROID    |SEGMENT |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_SPOTLIGHT=0,BLACKOUT_X_CENTROID=20|
And I navigate to DTACS url
And I navigate to "DTACS"

Examples:
|SOURCEID|SOURCENAME  |superCasId|accessCriteria                        |CONTROLLERID  |PKGNAME|SegmentName|baseUrl                    |
|12630   |SAutoSour630| 0E110000 |0E11000000000000090201AF020101D77E9C  | taj          |Auto_pkg1|AutoSeg99|https://10.78.206.142:8443/|

@Stage2_14  @BROWSER_NOT_NEEDED  @Completed-Mongo-db
Scenario Outline:
Given Load settings of "AC Manager" 
And Delete below tables in "AC Manager" db
#And verfiy the "AC Manager" db for Validation 
|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
| superCasId    |all_acs   |superCasId=<superCasId> AND acRefs="0001"  | txcount=0                                     |superCasId|


And Print the Scenario


Examples:
|SOURCEID|SOURCENAME  |superCasId|accessCriteria                        |PKGNAME|SegmentName|baseUrl                    |
|12639   |SAutoSour639| 0E110000 |0E11000000000000090201AF020101D77E9C |Auto_pkg1|AutoSeg99|https://10.78.206.142:8443/|
