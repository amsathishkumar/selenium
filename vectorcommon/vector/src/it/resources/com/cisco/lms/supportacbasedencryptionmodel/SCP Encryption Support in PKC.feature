@SCPEncryptionSupportinPKC
Feature: SCP Encryption Support in PKC

@LMS_test @Ttv10748278c @Regression  @BROWSER_NOT_NEEDED @Completed @MakeanexistingSourceasDTANOTApplicable
Scenario Outline: Modify an video source to DTA Applicable with duplicate Source Name
Given Load settings of "PK_PSM" 
#Precheck source with dta applicable as true
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |AutoSource810     |ACTIVE|SD         |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId    | Store   |
| <<CONTROLLERID>>       | <SOURCEID> | response|
#precheck create a source with dta applicable as false 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |AutoSource810     |ACTIVE|SD         |<SOURCENAME_CHANGE>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
#Actual Test case
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID_CHANGE>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |sourceId      |sourceDescription          |status|contentType            |sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID_CHANGE>   |<modifiedDescription>      |ACTIVE|<modifiedFlag>         |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "PUT" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "409" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>        | <SOURCEID_CHANGE> | response|



Examples:
|SOURCEID|SOURCENAME|superCasId|accessCriteria                      |SOURCEID_CHANGE|SOURCENAME_CHANGE|
|12810 |SAutosource810| 0E110000 |0E11000000000000090201AF020101D77E9C|12815|SAutosource815_CH|



@LMS_test @Ttv10748277c @Sanity  @BROWSER_NOT_NEEDED @Completed @MakeanexistingSourceasDTANOTApplicable
Scenario Outline: Modify a DTA Applicable Video Source to Non-DTA Applicable.
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And WUI wait for "3" seconds
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>     |ACTIVE|HD     |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID,DESCRIPTION,HD_FLAG,IS_DTA_SOURCE,HIGH_VALUE_CONTENT    |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>,DESCRIPTION=<SOURCENAME>,HD_FLAG=1,IS_DTA_SOURCE=1,HIGH_VALUE_CONTENT=0|AC_REF|
And verfiy the "PCB" db for Validation
|FIELDLIST                                    |TABLENAME            |CONDITION               | VERIFICATION                                             |STORE |
| CA_DOMAIN_ID,SCCMODE                 |sccsrc               |SOURCE_ID = '<SOURCEID>'|CA_DOMAIN_ID=<<LMS>>, SCCMODE=INIT                    |SCID|

And verfiy the "PCB" db for Validation
|FIELDLIST                                    |TABLENAME            |CONDITION               | VERIFICATION                              |
| EXPIRYDATE,SCIDSTATUS, VERSION              |SCID                 |scid = '<<tSCID>>'      |SCIDSTATUS=1, VERSION=2                    |


And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>     |ACTIVE|HD        |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Print the Scenario

And Make a "PUT" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "200" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID,DESCRIPTION,IS_DTA_SOURCE     |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>,DESCRIPTION=<SOURCENAME>,IS_DTA_SOURCE=0|AC_REF|
And Print the Scenario
Examples:
|SOURCEID|SOURCENAME|superCasId|accessCriteria                      |
|12829 |SAutosource829| 0E110000 |0E11000000000000090201AF020101D77E9C|


@LMS_test @Ttv10748273c @Regression  @BROWSER_NOT_NEEDED @Completed @MakeanexistingSourceasDTAApplicable
Scenario Outline: Modify an video source to DTA Applicable with duplicate Source Name
Given Load settings of "PK_PSM" 
#Precheck source with dta applicable as true
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>     |ACTIVE|SD         |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
#precheck create a source with dta applicable as false 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>     |ACTIVE|SD         |<SOURCENAME_CHANGE>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
#Actual Test case
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID_CHANGE>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |sourceId      |sourceDescription          |status|contentType            |sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID_CHANGE>   |<modifiedDescription>      |ACTIVE|<modifiedFlag>         |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "PUT" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "409" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>        | <SOURCEID_CHANGE> | response|



Examples:
|SOURCEID|SOURCENAME|superCasId|accessCriteria                      |SOURCEID_CHANGE|SOURCENAME_CHANGE|
|12703 |SAutosource703| 0E110000 |0E11000000000000090201AF020101D77E9C|12704|SAutosource703_CH|


@LMS_test @Ttv10748272c @Sanity  @BROWSER_NOT_NEEDED @Completed @MakeanexistingSourceasDTAApplicable
Scenario Outline: Modify an Video Source to DTA Applicable
Given Load settings of "PK_PSM" 
Given Frame the Url for "PK_PSM"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<psmBaseurl>>|<<psm.sources.uri>>|<SOURCEID>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And WUI wait for "3" seconds
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>     |ACTIVE|HD     |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":false,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID,DESCRIPTION,HD_FLAG,IS_DTA_SOURCE,HIGH_VALUE_CONTENT    |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>,DESCRIPTION=<SOURCENAME>,HD_FLAG=1,IS_DTA_SOURCE=0,HIGH_VALUE_CONTENT=0|AC_REF|

And Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
And Generate json with below data
|controllerId  |sourceId      |sourceDescription|status|contentType|sourceName    |sourceSecurities              |sccSourceParams                                                 |extAcRefs                                   |acRef|sdvStatus|
|<<CONTROLLERID>>| <SOURCEID>   |<SOURCENAME>     |ACTIVE|HD        |<SOURCENAME>|[{"securityMode":"ENCRYPTED"}]|{"dtaApplicable":true,"sccMode":"INIT","isDLNA":false,"scid":3}|[{"casSystemId":2345,"casSubSystemId":1234}]|{"isUsed":false}|None|
And Make a "PUT" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Verify the reponse status code has "200" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
And verfiy the "PCB" db for Validation
|FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
| SOURCE_ID,DESCRIPTION,IS_DTA_SOURCE     |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>,DESCRIPTION=<SOURCENAME>,IS_DTA_SOURCE=1|AC_REF|
And Print the Scenario
Examples:
|SOURCEID|SOURCENAME|superCasId|accessCriteria                      |
|12829 |SAutosource829| 0E110000 |0E11000000000000090201AF020101D77E9C|



