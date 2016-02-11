@LMS
Feature: UDTA Code Download in PKC 

@LMS_test  @Ttv10721489c @Sanity @Completed
Scenario Outline: Download Groups Definition - Create a group definition with ShowInCVT as True   
# Delete and Create source in PKPSm 
Given Delete below tables in "DTACS" db
|TABLENAME       |CONDITION           |
|download_group  |group_id='<GROUPID>'|
Given Load settings of "SCB"
#Delete group ID in DTACS DB also 
And Frame the Url for "SCB"
|baseURL        | URI                   |URIParameters   |QueryParam                                                        |
|<<scbBaseurl>>|<<scb.GroupManager.uri>>|<GROUPID>?      |controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |groupId      |groupName        |showInCVTAssociationList|systemDefaultGroup|userDefinedDefaultGroup    |
|<<CONTROLLERID>>| <GROUPID>   |<GroupName>      |true                    |false             | false                     |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
# PCB DB Validation only 
And verfiy the "PCB" db for Validation
|FIELDLIST                                   |TABLENAME            |CONDITION              | VERIFICATION     |
|MOD_VERSION,GROUP_ID,GROUP_NAME,MOD_VERSION |DOWNLOAD_GROUP       | GROUP_ID=<GROUPID>  |MOD_VERSION=<initialmodVersion>,GROUP_ID=<GROUPID>,GROUP_NAME=<GroupName>,MOD_VERSION=<initialmodVersion>|
Then verfiy the "DTACS" db for Validation
|FIELDLIST       |TABLENAME       |CONDITION           | VERIFICATION      |
|synced_version  |download_group  |group_id='<GROUPID>'|  synced_version=1 |
When Load settings of "DTACS"
And I navigate to DTACS url
And I navigate to "DTACS"
And I select the "CVT Association"
Then I validate the group "<GROUPID>" is shown in CVT Association
And I add CVT-Association "No-CVT"
And I click on "Add" button
And I validate the group "<GROUPID>" is shown in add CVT Association
And I click on "Cancel" button
And I click on first checkbox
And I click on "Edit" button
And I validate the group "<GROUPID>" is shown in edit CVT Association
Examples:
|GROUPID|GroupName|initialmodVersion|ca_domain_id|
|4      |Group4   |1                |1           |


@LMS_test  @Ttv10721491c @Sanity @Completed 
Scenario Outline: Download Groups Definition - Create a group definition with ShowInCVT as false   
# Delete and Create source in PKPSm 
Given Delete below tables in "DTACS" db
|TABLENAME       |CONDITION           |
|download_group  |group_id='<GROUPID>'|
Given Load settings of "SCB"
#Delete group ID in DTACS DB also 
And Frame the Url for "SCB"
|baseURL        | URI                   |URIParameters   |QueryParam                                                        |
|<<scbBaseurl>>|<<scb.GroupManager.uri>>|<GROUPID>?      |controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |groupId      |groupName        |showInCVTAssociationList|systemDefaultGroup|userDefinedDefaultGroup    |
|<<CONTROLLERID>>| <GROUPID>   |<GroupName>      |true                    |false             | false                     |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
# PCB DB Validation only 
And verfiy the "PCB" db for Validation
|FIELDLIST                                   |TABLENAME            |CONDITION              | VERIFICATION     |
|MOD_VERSION,GROUP_ID,GROUP_NAME,MOD_VERSION |DOWNLOAD_GROUP       | GROUP_ID=<GROUPID>  |MOD_VERSION=<initialmodVersion>,GROUP_ID=<GROUPID>,GROUP_NAME=<GroupName>,MOD_VERSION=<initialmodVersion>|
Then verfiy the "DTACS" db for Validation
|FIELDLIST       |TABLENAME       |CONDITION           | VERIFICATION      |
|synced_version  |download_group  |group_id='<GROUPID>'|  synced_version=1 |
When Load settings of "DTACS"
And I navigate to DTACS url
And I navigate to "DTACS"
And I select the "CVT Association"
Then I validate the group "<GROUPID>" is not shown in CVT Association
Examples:
|GROUPID|GroupName|initialmodVersion|ca_domain_id|
|4      |Group4   |1                |1           |



@LMS_test  @Ttv10721495c @Sanity @Completed  
Scenario Outline: Download Groups Definition - Add download groupid '0' in Download groups   
Given Load settings of "SCB"
#Delete group ID in DTACS DB also 
And Frame the Url for "SCB"
|baseURL        | URI                   |URIParameters   |QueryParam                                                        |
|<<scbBaseurl>>|<<scb.GroupManager.uri>>|<GROUPID>?      |controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |groupId      |groupName        |showInCVTAssociationList|systemDefaultGroup|userDefinedDefaultGroup    |
|<<CONTROLLERID>>| <GROUPID>   |<GroupName>      |true                    |false             | false                     |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "400" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
Examples:
|GROUPID|GroupName|initialmodVersion|ca_domain_id|
|0      |Group00  | 1                |1           |




@LMS_test  @Ttv10721497c @Sanity @Completed 
Scenario Outline: Download Groups Definition - Validating maximum length for download  group name field   
# Delete and Create source in PKPSm 
Given Delete below tables in "DTACS" db
|TABLENAME       |CONDITION           |
|download_group  |group_id='<GROUPID>'|
Given Load settings of "SCB"
#Delete group ID in DTACS DB also 
And Frame the Url for "SCB"
|baseURL        | URI                   |URIParameters   |QueryParam                                                        |
|<<scbBaseurl>>|<<scb.GroupManager.uri>>|<GROUPID>?      |controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |groupId      |groupName        |showInCVTAssociationList|systemDefaultGroup|userDefinedDefaultGroup    |
|<<CONTROLLERID>>| <GROUPID>   |<GroupName>      |true                    |false             | false                     |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
# PCB DB Validation only 
And verfiy the "PCB" db for Validation
|FIELDLIST                                   |TABLENAME            |CONDITION              | VERIFICATION     |
|MOD_VERSION,GROUP_ID,GROUP_NAME,MOD_VERSION |DOWNLOAD_GROUP       | GROUP_ID=<GROUPID>  |MOD_VERSION=<initialmodVersion>,GROUP_ID=<GROUPID>,GROUP_NAME=<GroupName>,MOD_VERSION=<initialmodVersion>|
Then verfiy the "DTACS" db for Validation
|FIELDLIST       |TABLENAME       |CONDITION           | VERIFICATION      |
|synced_version  |download_group  |group_id='<GROUPID>'|  synced_version=1 |
When Load settings of "DTACS"
And I navigate to DTACS url
And I navigate to "DTACS"
And I select the "CVT Association"
Then I validate the group "<GROUPID>" is shown in CVT Association
And I add CVT-Association "No-CVT"
And I click on "Add" button
And I validate the group "<GROUPID>" is shown in add CVT Association
And I click on "Cancel" button
And I click on first checkbox
And I click on "Edit" button
And I validate the group "<GROUPID>" is shown in edit CVT Association
Examples:
|GROUPID|GroupName                     |initialmodVersion|ca_domain_id|
|4      |123456789123456789123456789123|1                |1           |


@LMS_test  @Ttv10721498c @Sanity @Completed  
Scenario Outline: Download Groups Definition - Validating error message is triggered for creating a group ID with duplicate Group name.   
# Delete and Create source in PKPSm 
Given Delete below tables in "DTACS" db
|TABLENAME       |CONDITION           |
|download_group  |group_id='<GROUPID>'|
Given Load settings of "SCB"
#Delete group ID in DTACS DB also 
And Frame the Url for "SCB"
|baseURL        | URI                   |URIParameters   |QueryParam                                                        |
|<<scbBaseurl>>|<<scb.GroupManager.uri>>|<GROUPID>?      |controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |groupId      |groupName        |showInCVTAssociationList|systemDefaultGroup|userDefinedDefaultGroup    |
|<<CONTROLLERID>>| <GROUPID>   |<GroupName>      |true                    |false             | false                     |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |groupID     | Store   |
| <<CONTROLLERID>>         | <GROUPID>  | response|
When Delete below tables in "DTACS" db
|TABLENAME       |CONDITION           |
|download_group  |group_id='<GROUPID1>'|
And Load settings of "SCB"
#Delete group ID in DTACS DB also 
And Frame the Url for "SCB"
|baseURL        | URI                   |URIParameters   |QueryParam                                                        |
|<<scbBaseurl>>|<<scb.GroupManager.uri>>|<GROUPID1>?      |controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |groupId      |groupName        |showInCVTAssociationList|systemDefaultGroup|userDefinedDefaultGroup    |
|<<CONTROLLERID>>| <GROUPID1>   |<GroupName>      |true                    |false             | false                     |
And Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "400" with below response "json" value
|controllerId            |groupID     | Store   |
| <<CONTROLLERID>>         | <GROUPID1> | response|
Examples:
|GROUPID|GroupName|initialmodVersion|ca_domain_id|GROUPID1|
|4      |Group4   |1                |1           |   1    |


@LMS_test  @Ttv10721492c @Sanity @Completed  
Scenario Outline: Download Groups Definition - Modify a download group definition   
# Delete and Create source in PKPSm 
Given Delete below tables in "DTACS" db
|TABLENAME       |CONDITION           |
|download_group  |group_id='<GROUPID>'|
Given Load settings of "SCB"
#Delete group ID in DTACS DB also 
And Frame the Url for "SCB"
|baseURL        | URI                   |URIParameters   |QueryParam                                                        |
|<<scbBaseurl>>|<<scb.GroupManager.uri>>|<GROUPID>?      |controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |groupId      |groupName        |showInCVTAssociationList|systemDefaultGroup|userDefinedDefaultGroup    |
|<<CONTROLLERID>>| <GROUPID>   |<GroupName>      |true                    |false             | false                     |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "201" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|
# PCB DB Validation only 
And verfiy the "PCB" db for Validation
|FIELDLIST                                   |TABLENAME            |CONDITION              | VERIFICATION     |
|MOD_VERSION,GROUP_ID,GROUP_NAME,MOD_VERSION |DOWNLOAD_GROUP       | GROUP_ID=<GROUPID>  |MOD_VERSION=<initialmodVersion>,GROUP_ID=<GROUPID>,GROUP_NAME=<GroupName>,MOD_VERSION=<initialmodVersion>|
Then verfiy the "DTACS" db for Validation
|FIELDLIST       |TABLENAME       |CONDITION           | VERIFICATION      |
|synced_version  |download_group  |group_id='<GROUPID>'|  synced_version=1 |
When Load settings of "DTACS"
And I navigate to DTACS url
And I navigate to "DTACS"
And I select the "CVT Association"
Then I validate the group "<GROUPID>" is shown in CVT Association
And I add CVT-Association "No-CVT"
And I click on "Add" button
And I validate the group "<GROUPID>" is shown in add CVT Association
And I click on "Cancel" button
And I click on first checkbox
And I click on "Edit" button
And I validate the group "<GROUPID>" is shown in edit CVT Association
Given Load settings of "SCB"
#Delete group ID in DTACS DB also 
And Frame the Url for "SCB"
|baseURL        | URI                   |URIParameters   |QueryParam                                                        |
|<<scbBaseurl>>|<<scb.GroupManager.uri>>|<GROUPID>?      |controllerId=<<CONTROLLERID>> |
And Generate json with below data
|controllerId  |groupId      |groupName           |showInCVTAssociationList|systemDefaultGroup|userDefinedDefaultGroup    |
|<<CONTROLLERID>>| <GROUPID>   |<NewGroupName>      |false                   |false             | false                     |
When Make a "PUT" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
Then Verify the reponse status code has "200" with below response "json" value
|controllerId            |sourceId| Store   |
| <<CONTROLLERID>>         | <SOURCEID> | response|

# PCB DB Validation only 
And verfiy the "PCB" db for Validation
|FIELDLIST                       |TABLENAME            |CONDITION              | VERIFICATION     |
|MOD_VERSION,GROUP_ID,GROUP_NAME |DOWNLOAD_GROUP       | GROUP_ID=<GROUPID>    |MOD_VERSION=<changedModVersion>,GROUP_ID=<GROUPID>,GROUP_NAME=<NewGroupName>|
Then verfiy the "DTACS" db for Validation
|FIELDLIST       |TABLENAME       |CONDITION           | VERIFICATION      |
|synced_version  |download_group  |group_id='<GROUPID>'|  synced_version=2 |
When Load settings of "DTACS"
And I navigate to DTACS url
And I navigate to "DTACS"
And I select the "CVT Association"
Then I validate the group "<GROUPID>" is not shown in CVT Association
And I add CVT-Association "No-CVT"
And I click on "Add" button
And I validate the group "<GROUPID>" is shown in add CVT Association
And I click on "Cancel" button
And I click on first checkbox
And I click on "Edit" button
And I validate the group "<GROUPID>" is shown in edit CVT Association
Examples:
|GROUPID|GroupName|initialmodVersion|ca_domain_id|NewGroupName|changedModVersion|
|4      |Group4   |1                |1           |TestGroup   |2                |


@LMS_test  @Ttv10721493c @Sanity @Completed
Scenario Outline: Download Groups Definition- Delete a download group ID
Given Delete below tables in "DTACS" db
|TABLENAME       |CONDITION           |
|download_group  |group_id='<GROUPID>'|
Given Load settings of "SCB"
#Delete group ID in DTACS DB also 
And Frame the Url for "SCB"
|baseURL        | URI                   |URIParameters   |QueryParam                                                        |
|<<scbBaseurl>>|<<scb.GroupManager.uri>>|<GROUPID>?      |controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
And Generate json with below data
|controllerId  |groupId      |groupName        |showInCVTAssociationList|systemDefaultGroup|userDefinedDefaultGroup    |
|<<CONTROLLERID>>| <GROUPID>   |<GroupName>      |true                    |false             | false                     |
When Make a "POST" call with the below headers
|Content-Type    |Basic			   |
|application/json|restful/conductor|
And Make a "DELETE" call with no headers
Then Verify the reponse status code has "200" with below response "json" value
|controllerId            |groupId|groupName| Store   |
| <<CONTROLLERID>>         | <GROUPID> |GroupName| response|
# PCB DB Validation only 
And verfiy the "PCB" db for Validation
|FIELDLIST                                   |TABLENAME            |CONDITION              | VERIFICATION     |
|GROUP_ID |DOWNLOAD_GROUP       | GROUP_ID=<GROUPID>  |txcount=0 |
Then verfiy the "DTACS" db for Validation
|FIELDLIST       |TABLENAME       |CONDITION           | VERIFICATION      |
|group_id  |download_group  |group_id='<GROUPID>'|  txcount=0 |
When Load settings of "DTACS"
And I navigate to DTACS url
And I navigate to "DTACS"
And I select the "CVT Association"
And I validate the group "<GROUPID>" is not shown in CVT Association
Examples:
|GROUPID|GroupName|initialmodVersion|ca_domain_id|
|10     |Group10   |1                |1           |


@LMS_test  @Ttv10721496c @Sanity @BROWSER_NOT_NEEDED @Completed
Scenario Outline: Download Groups Definition-Deleting the default groupID from Download Groups
Given Load settings of "SCB"
And Frame the Url for "SCB"
|baseURL        | URI                   |URIParameters   |QueryParam                                                        |
|<<scbBaseurl>>|<<scb.GroupManager.uri>>|<GROUPID>?      |controllerId=<<CONTROLLERID>> |
And Make a "DELETE" call with no headers
Then Verify the reponse status code has "400" with below response "json" value
|appErrorString           | Store   |
| <Error>         |  response|
# PCB DB Validation only 
And verfiy the "PCB" db for Validation
|FIELDLIST                                   |TABLENAME            |CONDITION              | VERIFICATION     |
|GROUP_ID |DOWNLOAD_GROUP       | GROUP_ID=<GROUPID> and CA_DOMAIN_ID=<ca_domain_id>  |GROUP_ID=<GROUPID> |
Examples:
|GROUPID|GroupName     |initialmodVersion|ca_domain_id|Error|
|0     |Default-Group  |1                |1           |"Operation Failed: Group Id (0) is a System Default Group. Create /"|



