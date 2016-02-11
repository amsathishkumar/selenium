@pkg
Feature: package

Background: SD details


@pkg @pkg1 @BROWSER_NOT_NEEDED @SAT
Scenario Outline: package  
Given SD details
Given Frame the Url for "Service Manager"
|baseURL        | URI       |URIParameters   |QueryParam                                                        |
|<<pkg.baseurl>>|<<pkg.uri>>|<PKGNAME>?      |sourceSystem=abc&tracingToken=abcd456&controllerId=<CONTROLLERID> |
And Generate json with below data
|controllerId            |packageName|segments                           |limitedDuration|
| <CONTROLLERID>         | <PKGNAME>       |["HBO_East_Regular","ESPN_Regular"]| {"effectiveTime": "2015-03-24 00:00:00T+00:00","duration": 120} }|
And Make a "POST" call with the below headers
|Content-type    |Accept          |
|application/json|application/json|

And Verify the reponse status code has "200" with below response "json" value
|controllerId            |packageName| Store   |
| <CONTROLLERID>         | <PKGNAME> | response|

Examples:
|SOURCEID|SOURCENAME|superCasId|accessCriteria                      |CONTROLLERID|PKGNAME|
|12004 |Auto89_793| 0E110000 |0E11000000000000090201AF020101D77E9C  | taj          | pkg |