@SupportaACbasedencryptionmodel
Feature: Support a AC based encryption model


@LMS_test @Ttv10748270c @Regression  @BROWSER_NOT_NEEDED @Completed @Video_source_creation
Scenario Outline: Create a video source with duplicate Source name
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And WUI wait for "3" seconds
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>     |ACTIVE|SD         |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
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
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |sourceId               |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                        |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID_CHANGE>   |<SOURCENAME>    |ACTIVE|SD          |<SOURCENAME>  |[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "409" with below response "json" value
|controllerId             |sourceId           | Store   |
| <<CONTROLLERID>>        | <SOURCEID_CHANGE> | response|
And Print the Scenario
Examples:
|SOURCEID|SOURCENAME    |superCasId|accessCriteria                      |SOURCEID_CHANGE|
|12093  |SAutosource093| 0E110000 |0E11000000000000090201AF020101D77E9C|12801          |


@LMS_test @Ttv10748267c @Sanity  @BROWSER_NOT_NEEDED @Completed @Video_source_creation
Scenario Outline: Create a video source with DTA Applicable as true
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And WUI wait for "3" seconds
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>     |ACTIVE|HD        |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
# PCB DB Validation only 
And verfiy the "PCB" db for Validation
|FIELDLIST                                          |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID,DESCRIPTION, HIGH_VALUE_CONTENT,HD_FLAG |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>,DESCRIPTION=<SOURCENAME>,HIGH_VALUE_CONTENT=0,HD_FLAG=1|AC_REF|

And verfiy the "PCB" db for Validation
|FIELDLIST            |TABLENAME            |CONDITION               | VERIFICATION                             |STORE |
| CA_DOMAIN_ID,SCCMODE|sccsrc               |SOURCE_ID = '<SOURCEID>'|CA_DOMAIN_ID=<<CA_DOMAIN_ID>>,SCCMODE=0 |SCID|

And verfiy the "PCB" db for Validation
|FIELDLIST           |TABLENAME            |CONDITION               | VERIFICATION          |
|SCIDSTATUS, VERSION |SCID                 |scid = '<<tSCID>>'      |SCIDSTATUS=0,VERSION=1 |
And Print the Scenario
Examples:
|SOURCEID|SOURCENAME|superCasId  |accessCriteria                      |
|12092 |SAutosource092| 0E110000 |0E11000000000000090201AF020101D77E9C|






@LMS_test  @Ttv10748268c @Regression  @BROWSER_NOT_NEEDED @Completed @Video_source_creation
Scenario Outline: Create an video source with DTA Applicable as true having duplicate source ID
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And WUI wait for "3" seconds
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>     |ACTIVE|SD         |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
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
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>     |ACTIVE|SD         |<SOURCENAME_CHANGE>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "409" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>        | <SOURCEID> | response|
And Print the Scenario

Examples:
|SOURCEID|SOURCENAME|superCasId|accessCriteria                      |SOURCENAME_CHANGE|
|12091 |SAutosource91| 0E110000 |0E11000000000000090201AF020101D77E9C|SAutosource91_CH|

@LMS_test @Ttv10748291c @Regression  @BROWSER_NOT_NEEDED @Completed @RETIRE-VIDEO-SOURCE-INSTANCE
Scenario Outline: Delete a Video source which is not available in PKC DB
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And WUI wait for "3" seconds
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>    |ACTIVE|SD         |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And WUI wait for "3" seconds
And Make a "DELETE" call with no headers
And WUI wait for "3" seconds
And Make a "DELETE" call with no headers
And Verify the reponse status code has "404" with below response "json" value
|appErrorString            | Store   |
| "Requested Source record with SourceId : <SOURCEID> not found."       | response|


Examples:
|SOURCEID|SOURCENAME|superCasId|accessCriteria                      |
|12822 |SAutosource822| 0E110000 |0E11000000000000090201AF020101D77E9C|




@LMS_test @Ttv10748287c @Regression  @BROWSER_NOT_NEEDED @Completed
Scenario Outline: Query a video source which is not present in PKC DB
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And WUI wait for "3" seconds
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>    |ACTIVE|SD         |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And WUI wait for "3" seconds
And Make a "DELETE" call with no headers
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "GET" call with no headers
And Verify the reponse status code has "404" with below response "json" value
|appErrorString            |sourceId| Store   |
| "Source ID Not Found"        | <SOURCEID> | response|
And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  txcount=0   |


Examples:
|SOURCEID|SOURCENAME|superCasId|accessCriteria                      |
|12820 |SAutosource820| 0E110000 |0E11000000000000090201AF020101D77E9C|


@LMS_test @Ttv10748288c @Regression  @BROWSER_NOT_NEEDED @Completed
Scenario Outline: Query a video source in system with invalid Controller ID
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And WUI wait for "3" seconds
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>    |ACTIVE|SD         |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And WUI wait for "3" seconds
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerIds=<CONTROLLERID_ERROR> |
And Make a "GET" call with no headers
And Verify the reponse status code has "400" with below response "json" value
|appErrorString            |sourceId| Store   |
| "EC with name [<CONTROLLERID_ERROR>] not found."        | <SOURCEID> | response|

Examples:
|SOURCEID|SOURCENAME|superCasId|accessCriteria                      |CONTROLLERID_ERROR|
|12821 |SAutosource821| 0E110000 |0E11000000000000090201AF020101D77E9C|taj123|


@Stage2_TC8  @Ttv10748294c   @Completed @DEPLOY-VIDEO-PRODUCT
Scenario Outline: CREATE-VIDEO-PRODUCT- Create a video product for Non-DTA applicable Source with unlimited segments
# Delete and Create of source,segment and package in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status  |segmentDuration                                                       |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |

Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME    |SegmentName|
|12006   |SAutoSour006 | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg006|AutoSeg006 |

@Stage2_TC9  @Ttv10748295c @Completed @DEPLOY-VIDEO-PRODUCT
Scenario Outline: CREATE-VIDEO-PRODUCT- Modify a video product for Non- DTA applicable source with unlimited segments
# Delete and Create of source,segment and package in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |

And Generate json with below data
|controllerId  |segmentName  |sourceId  |status  |segmentDuration                                                       |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}  |NONE       |NEVER         |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                                               |TABLENAME|CONDITION                   | VERIFICATION                                                                                                                |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT,COPYPROTECT_LEVEL    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1,COPYPROTECT_LEVEL=3|


#SegmentUpdate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |

And Generate json with below data
|controllerId  |segmentName  |sourceId  |status  |segmentDuration                                                       |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}  |NONE       |PERMITTED     |SET    |
When Make a "PUT" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "200" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                                               |TABLENAME|CONDITION                   | VERIFICATION                                                                                                                |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT,COPYPROTECT_LEVEL    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1,COPYPROTECT_LEVEL=0|

Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME    |SegmentName|
|12007   |SAutoSour007 | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg007|AutoSeg007 |





@Stage2_TC10  @Ttv10748297c @Completed @Completed3 @RETIRE-VIDEO-PRODUCT
Scenario Outline: RETIRE-VIDEO-PRODUCT- Delete a video Product of Non-DTA applicable source
# Delete and Create of source,segment and package in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status  |segmentDuration                                                       |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
Then Verify the reponse status code has "200" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>       | <SegmentName> | response|
And verfiy the "PCB" db for Validation 
|FIELDLIST          |TABLENAME            |CONDITION                      | VERIFICATION         |
| SEGMENT_NAME      |SEGMENT              |SEGMENT_NAME  = '<SegmentName>'|  txcount=0           |



Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME    |SegmentName|
|12074 |SAutoSour074 | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg748 |AutoSeg748 |

@Stage2_TC14  @Ttv10748296c @Completed23 @RETIRE-VIDEO-PRODUCT
Scenario Outline: CREATE-VIDEO-PRODUCT-Create a video product with invalid Controller ID

# Delete and Create of source and segment in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Load settings of "PK_PSM"
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status|segmentDuration                                                     |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<InValidCONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "400" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|


Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |InValidCONTROLLERID       |PKGNAME   |SegmentName|
|12658   |SAutoSour758 | 0E110000 |0E11000000000000090201AF020101D77E9C  | taj1                     |Auto_pkg58|AutoSeg658 |


@Stage2_TC2   @Ttv10748299c @Completed @RETIRE-VIDEO-PRODUCT
Scenario Outline: RETIRE-VIDEO-PRODUCT-Delete a Video product which is not available in PKC DB
# Delete and Create of source and segment in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Load settings of "PK_PSM"
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status|segmentDuration                                                     |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                                   |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?  |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
Then Verify the reponse status code has "200" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
And Make a "DELETE" call with no headers
And Verify the reponse status code has "404" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>       | <SOURCEID> | response|

Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME  |SegmentName|
|12690   |SAutoSour724 | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg90|AutoSeg690|

@Stage2_TC3   @Ttv10748300c @Completed @RETIRE-VIDEO-PRODUCT
Scenario Outline: RETIRE-VIDEO-PRODUCT-Delete a video product which is part of video package
# Delete and Create of source,segment and package in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.package.uri>>|<PKGNAME>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>>  |
And Make a "DELETE" call with no headers
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status|segmentDuration                                                     |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |
#Create a package
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.package.uri>>|<PKGNAME>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>>  |
And Generate json with below data
|controllerId    |pkPackageEid  |segments              |subPackages|dtaApplicable|deletable|status|packageName|packageDescription|packageType |
|<<CONTROLLERID>>| 13           |["<SegmentName>"]     |[]         |false        |true     |ACTIVE|<PKGNAME>  |pacakgeDesc       |SUBSCRIPTION|
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>       | <PKGNAME>     | response|
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
Then Verify the reponse status code has "404" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>       | <SegmentName> | response|


Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME   |SegmentName|
|12090   |SAutoSour90 | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg90|AutoSeg90 |



@Stage2_TC1  @Ttv10748298c @Completed @RETIRE-VIDEO-PRODUCT
Scenario Outline: RETIRE-VIDEO-PRODUCT-Delete a video product in system with invalid Controller ID
# Delete and Create of source and segment in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Load settings of "PK_PSM"
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status|segmentDuration                                                     |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                                   |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<InValidCONTROLLERID> |
When Make a "DELETE" call with no headers
Then Verify the reponse status code has "404" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |InValidCONTROLLERID       |PKGNAME   |SegmentName|
|12650   |SAutoSour750 | 0E110000 |0E11000000000000090201AF020101D77E9C  | taj1                     |Auto_pkg50|AutoSeg650 |


@Stage2_TC5  @Ttv10748302c @Completed @RETIRE-VIDEO-PRODUCT
Scenario Outline: QUERY-VIDEO-PRODUCT-Query a video product in system with invalid Controller ID
# Delete and Create of source and segment in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Load settings of "PK_PSM"
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status|segmentDuration                                                     |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |
Given Load settings of "PK_PSM"
 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                                    |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerIds=<InValidCONTROLLERID> |
When Make a "GET" call with no headers
Then Verify the reponse status code has "500" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME  |SegmentName|InValidCONTROLLERID |
|12683   |SAutoSour7   | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg1|Auto623Seg |taj1                |



@Stage2_TC4   @Ttv10748301c @Completed @QUERY-VIDEO-PRODUCT
Scenario Outline: QUERY-VIDEO-PRODUCT-Query a video product which is not present in PKC DB.
# Delete and Create of source and segment in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Load settings of "PK_PSM"
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And I wait for "5" sec
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status|segmentDuration                                                     |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                                   |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?  |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
Then Verify the reponse status code has "200" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                                   |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?  |sourceSystem=abc&tracingToken=abcd456&controllerIds=<<CONTROLLERID>>         |
And Make a "GET" call with no headers
And Verify the reponse status code has "404" with below response "json" value
|controllerId            |sourceId    | Store   |
| <<CONTROLLERID>>       | <SOURCEID> | response|

Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME  |SegmentName|
|12693   |SAutoSour729 | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg9|AutoSeg620 |

@Stage2_TC11  @Ttv10748303c @Completed @QUERY-VIDEO-PRODUCT
Scenario Outline: QUERY-VIDEO-PRODUCT- Query a video product by Segment Name.
# Delete and Create of source,segment and package in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status  |segmentDuration                                                       |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerIds=<<CONTROLLERID>> |
And Make a "GET" call with no headers
Then Verify the reponse status code has "200" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>       | <SegmentName> | response|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |



Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME    |SegmentName|
|12009   |SAutoSour009 | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg009|AutoSeg009 |



@Stage2_TC12  @Ttv10748304c @Completed @CreateModifyVideoProduct
Scenario Outline: CREATE-VIDEO-PRODUCT- Create an video product for DTA Applicable Source with unlimited segment
# Delete and Create of source,segment and package in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status  |segmentDuration                                                       |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |

Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME    |SegmentName|
|12089  |SAutoSour089 | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg089|AutoSeg089 |

@Stage2_TC13  @Ttv10748307c   @Completed @CreateModifyVideoProduct
Scenario Outline: CREATE-VIDEO-PRODUCT- Modify a video product for DTA Applicable Source with unlimited segment
# Delete and Create of source,segment and package in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |

And Generate json with below data
|controllerId  |segmentName  |sourceId  |status  |segmentDuration                                                       |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}  |NONE       |NEVER         |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                                               |TABLENAME|CONDITION                   | VERIFICATION                                                                                                                |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT,COPYPROTECT_LEVEL    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1,COPYPROTECT_LEVEL=3|


#SegmentUpdate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |

And Generate json with below data
|controllerId  |segmentName  |sourceId  |status  |segmentDuration                                                       |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}  |NONE       |PERMITTED     |SET    |
When Make a "PUT" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "200" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                                               |TABLENAME|CONDITION                   | VERIFICATION                                                                                                                |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT,COPYPROTECT_LEVEL    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1,COPYPROTECT_LEVEL=0|

Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME    |SegmentName|
|12088   |SAutoSour088 | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg088|AutoSeg088 |


@Stage2_TC6  @Ttv10748308c @Completed @DeleteVideoProduct
Scenario Outline: RETIRE-VIDEO-PRODUCT- Delete an video product of DTA Applicable source which is part of Video Package
# Delete and Create of source,segment and package in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.package.uri>>|<PKGNAME>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>>  |
And Make a "DELETE" call with no headers
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status|segmentDuration                                                     |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |
#Create a package
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.package.uri>>|<PKGNAME>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>>  |
And Generate json with below data
|controllerId    |pkPackageEid  |segments              |subPackages|dtaApplicable|deletable|status|packageName|packageDescription|packageType |
|<<CONTROLLERID>>| 13           |["<SegmentName>"]     |[]         |false        |true     |ACTIVE|<PKGNAME>  |pacakgeDesc       |SUBSCRIPTION|
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>       | <PKGNAME>     | response|
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
Then Verify the reponse status code has "404" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>       | <SegmentName> | response|


Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME    |SegmentName|
|12087   |SAutoSour087 | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg087|AutoSeg08773 |


@Stage2_TC7  @Ttv10748309c  @Completed  @DeleteVideoProduct
Scenario Outline: RETIRE-VIDEO-PRODUCT- Delete an video product of DTA Applicable source which is not part of Video Package
# Delete and Create of source,segment and package in PKPSm 
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And WUI wait for "9" seconds
And Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>> |<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
When Make a "DELETE" call with no headers
And WUI wait for "9" seconds
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |HBO Channel      |ACTIVE|SD         |SAutosource620|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|

#SegmentCreate
Given Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |segmentName  |sourceId  |status  |segmentDuration                                                       |blackOut                                                                   |fingerPrint|copyProtection|citFlag|
|<<CONTROLLERID>>|<SegmentName>|<SOURCEID>|ACTIVE|{"startTime":null,"limitedDuration":{"length":0,"repeatDuration":0}}|{"blackoutControlType":"NONE","centroidX": 20,"centroidY": 20,"radius": 20}|NONE       |PERMITTED     |SET    |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>         | <SegmentName> | response|
## Validate in AC MAnager
#And verfiy the "AC Manager" db for Validation 
#|FIELDLIST       |TABLENAME |CONDITION                                          | VERIFICATION                                  |STORE     |
#|  superCasId    |all_acs   |superCasId=<superCasId> AND acRefs=<<tAC_REF>>     | txcount=0                                     |superCasId|
And verfiy the "PCB" db for Validation 
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID      |source               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>|AC_REF|
And verfiy the "PCB" db for Validation 
|FIELDLIST                                                                             |TABLENAME|CONDITION                   | VERIFICATION                                                                                                      |
| SOURCE_ID,ADMIN_STATE,BLACKOUT_RADIUS,BLACKOUT_X_CENTROID,BLACKOUT_Y_CENTROID,CIT    |SEGMENT  |SEGMENT_NAME='<SegmentName>'|SOURCE_ID=<SOURCEID>,ADMIN_STATE=1,BLACKOUT_RADIUS=20,BLACKOUT_X_CENTROID=20,BLACKOUT_Y_CENTROID=20,CIT=1    |
Given Load settings of "PK_PSM" 
And Frame the Url
|baseURL                     | URI               |URIParameters   |QueryParam                                                           |
|<<psmBaseurl>>              |<<psm.segment.uri>>|<SegmentName>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And WUI wait for "9" seconds
Then Verify the reponse status code has "200" with below response "json" value
|controllerId            |packageName    | Store   |
| <<CONTROLLERID>>       | <SegmentName> | response|
And verfiy the "PCB" db for Validation 
|FIELDLIST          |TABLENAME            |CONDITION                     | VERIFICATION         |
| SEGMENT_NAME      |SEGMENT              |SEGMENT_NAME  ='<SegmentName>'|  txcount=0           |



Examples:
|SOURCEID|SOURCENAME   |superCasId|accessCriteria                        |PKGNAME    |SegmentName|
|12086   |SAutoSour086 | 0E110000 |0E11000000000000090201AF020101D77E9C  |Auto_pkg086|AutoSeg086 |



