/*
 * 
 */
package com.sat.dbds.vcs.common;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.cisco.dbds.utils.cucumber.Hooks;
import com.cisco.dbds.utils.logging.LogHandler;
import com.cisco.dbds.utils.selenium.Autowait;
import com.cisco.dbds.utils.selenium.Identifier;
import com.cisco.dbds.utils.selenium.SeleniumUtilities;
import com.cisco.dbds.utils.validation.Validate;
import com.sat.dbds.vcs.commonpagefactory.CommonPagefactory;
import com.sat.dbds.vcs.dbconnection.DBconnection;
import com.sat.dbds.vcs.ssoconfig.ssoconfigStepDef;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
// TODO: Auto-generated Javadoc
//import com.cisco.dbds.vcs.commonpagefactory.CommonPagefactory;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonStepDef.
 */
public class CommonStepDef {

	/** The commonpf. */
	private CommonPagefactory commonpf;

	/** The queryvalues. */
	private Map<String, List<Object>> queryvalues = null;

	/** The queryvalues1. */
	public static List<Map<String, String>> queryvalues1 = null;

	/** The db. */
	public static DBconnection db;
	
	/** The sso. */
	public ssoconfigStepDef sso;

	/**
	 * Instantiates a new common step def.
	 */
	public CommonStepDef() {
		commonpf = PageFactory.initElements(SeleniumUtilities.getDriver(),
				CommonPagefactory.class);
	}

	/**
	 * Wui_wait_for_seconds.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>Given WUI wait for "5" seconds </code>
	 * </pre>
	 * 
	 * @param argSec
	 *            the arg sec
	 */
	@When("^WUI wait for \"(.*?)\" seconds$")
	public void wui_wait_for_seconds(String argSec) {
		LogHandler.info("wui_wait_for_seconds(String argSec)" + argSec);
		try {
			int sec = Integer.parseInt(argSec);
			SeleniumUtilities.wait(sec, Autowait.vcswui);
		} catch (Exception e) {
			LogHandler.warn("In-valid WUI wait");
			Assert.assertTrue("In-valid WUI wait", false);
		}

	}

	/**
	 * Verify_critical_alert_message.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Verify critical alert message "sssss"</code>
	 * </pre>
	 * 
	 * @param cmessage
	 *            the cmessage
	 */
	@Given("^Verify critical alert message \"(.*?)\"$")
	public void verify_critical_alert_message(String cmessage) {
		LogHandler.info("void verify_critical_alert_message(String cmessage)"
				+ cmessage);
		try {

			String actualmessage = commonpf.WE_ALERT_CRITICAL_MESSAGE.getText();
			if (!actualmessage.equals(cmessage)) {
				LogHandler.warn("Actual critical alert message "
						+ actualmessage + "not matche with Excpected message "
						+ cmessage);
				Assert.assertTrue("Actual critical alert message "
						+ actualmessage + "not matche with Excpected message "
						+ cmessage, false);
			}

		} catch (WebDriverException | NullPointerException e) {
			LogHandler.warn("Page Object WE_ALERT_CRITICAL_MESSAGE  mismatch");
			Assert.assertTrue("Page Object WE_ALERT_CRITICAL_MESSAGE mismatch",
					false);
		}

	}

	/**
	 * Click_on_critical_alert_message_ok_button.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>Click on critical alert message OK button</code>
	 * </pre>
	 */
	@Given("^Click on critical alert message OK button$")
	public void click_on_critical_alert_message_ok_button() {
		LogHandler.info("click_on_critical_alert_message_ok_button()");
		try {
			commonpf.WE_PRIME_ALERT_SELECTOR.click();
		} catch (WebDriverException | NullPointerException e) {
			LogHandler
					.warn("Page Object getCRITICALALERTOKBUTTON or PRIME_ALERT_SELECTOR  mismatch");
			Assert.assertTrue(
					"Page Object getCRITICALALERTOKBUTTON or PRIME_ALERT_SELECTOR  mismatch",
					false);
		}
	}

	/**
	 * Excute_the_query.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Excute the query "sssss"</code>
	 * </pre>
	 * 
	 * @param query
	 *            the query
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws SQLException
	 *             the SQL exception
	 */
	@Given("^Excute the query \"(.*?)\"$")
    public void excute_the_query(String query) throws SQLException {
          LogHandler.info("void excute_the_query(String query)");
          String dbtype=System.getProperty("Dbtype");
          System.out.print("The DB Type is:" +dbtype);
          if (dbtype.matches("mysql"))
          {	
        	 // query=query.toLowerCase();
        	  if (query.contains("user_tables"))
        		  query = query.replace("user_tables", "INFORMATION_SCHEMA.TABLES");
        	 // query = query
          }
          System.out.print("The query is:" +query);
          
        			  db = new DBconnection();
          queryvalues1 = db.dbselect(query);
          LogHandler.info("DB result" + queryvalues1.toString());
        //  }
    }
          
    @Given("^Update the db query \"(.*?)\"$")
    public void Execute_the_delete_query(String query) {
          db = new DBconnection();
          db.dbupdate(query);
    }

          


	/**
	 * Update_query.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Update query with following parameters
	 * 	 |token         |query|
	 *    |<<CMC_SSO_COOKIE>>|update SSO_TOKEN set ONETIMETOKEN='1234' where TOKEN='| 
	 * 	</code>
	 * </pre>
	 * 
	 * @param paramdetails
	 *            the paramdetails
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws SQLException
	 *             the SQL exception
	 */
	@Given("^Update query with following parameters$")
	public void update_query(DataTable paramdetails) {
		LogHandler.info("update_query(DataTable paramdetails)");
		db = new DBconnection();

		String cookie = null;
		LogHandler.info("update_query(DataTable paramdetails)");
		List<Map<String, String>> paramdetailsvalues = paramdetails.asMaps(
				String.class, String.class);

		for (String prmvalue : paramdetails.topCells()) {
			String val = paramdetailsvalues.get(0).get(prmvalue);

			if (val.startsWith("<<") && val.endsWith(">>")) {

				cookie = Validate.readsystemvariable(val);
			} else {
				val = val.concat(cookie);
				val = val.concat("'");
				db.dbupdate(val);
			}

		}

	}

	/**
	 * Execute_query.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Execute query with following parameters
	 * 	 |token         |query|
	 * 	 |<<CMC_SSO_COOKIE>>|select APPID from SSO_TOKEN where ONETIMETOKEN is NULL and TOKEN='|</code>
	 * </pre>
	 * 
	 * @param paramdetails
	 *            the paramdetails
	 * @throws SQLException 
	 */
	@Given("^Execute query with following parameters$")
	public void execute_query(DataTable paramdetails) throws SQLException {
		LogHandler.info("execute_query(DataTable paramdetails)");
		db = new DBconnection();

		String cookie = null;
		LogHandler.info("execute_query(DataTable paramdetails)");
		List<Map<String, String>> paramdetailsvalues = paramdetails.asMaps(
				String.class, String.class);

		for (String prmvalue : paramdetails.topCells()) {
			String val = paramdetailsvalues.get(0).get(prmvalue);

			if (val.startsWith("<<") && val.endsWith(">>")) {
				cookie = Validate.readsystemvariable(val);
			} else {
				val = val.concat(cookie);
				val = val.concat("'");
				db = new DBconnection();
				queryvalues1 = db.dbselect(val);
				LogHandler.info("DB result" + queryvalues1.toString());
			}
		}
	}

	/**
	 * Delete_entries_from_table.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Delete entries from table "sssss"</code>
	 * </pre>
	 * 
	 * @param table
	 *            the table
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws SQLException
	 *             the SQL exception
	 */
	@Given("^Delete entries from table \"(.*?)\"$")
	public void delete_entries_from_table(String table) {
		db = new DBconnection();
		String query = "delete from ";
		query += table;
		db.dbupdate(query);

	}

	/**
	 * Verify_the_below_db_values.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Verify the below db values "is matched"
	 * |ID|USERNAME |SERVERPORT|SERVERPROTOCOL|SERVERHOST   |CONTEXT    |AUDITSERVICEURL |ROLESRETRIEVALURL|AUTHENTICATEURL        |AUTHORIZATIONURL     | LOGOUTURL        |RETRIEVETOKENURL   |CACHEINTERVAL|GOTOURLPARAMNAME | TOKENPARAMNAME     |HOSTCOOKIEPARAMNAME|APPIDPARAMNAME        |LOGINREDIRECTURL| LOGOUTREDIRECTURL|SSOSTATUSURL     |ISACTIVE|TOKENMAXINACTIVITYTIME|
	 * | 1|ssoadmin |<<vcs.port>>|     https    |<<vcs.ip>>|/vcsconsole|rs/ssosrvc/audit|rs/ssosrvc/roles |rs/ssosrvc/authenticate|rs/ssosrvc/authorized| rs/ssosrvc/logout|rs/ssosrvc/getToken| 120000      |gotoURL          |cisco.dbds.sso.token|CMC_SSO_COOKIE     | cisco.dbds.sso.appid |login.jsp       | logout.jsp       |rs/ssosrvc/status| Y	    | 7200000              |
	 * </code>
	 * </pre>
	 * 
	 * @param flag
	 *            the flag
	 * @param value
	 *            the value
	 */
	@Then("^Verify the below db values \"(.*?)\"$")
	public void verify_the_below_db_values(String flag, DataTable value) {
		LogHandler
				.info("verify_the_below_db_values(String flag, DataTable value)");
		List<Map<String, String>> stvalue = value.asMaps(String.class,
				String.class);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list = Validate.readsystemvariable(value);

		switch (flag) {
		case "is matched":
			db.testdatacomparedbdata(list, queryvalues1);
			break;
		case "is not matched":
			db.testdatacomparedbdata_notpresent(stvalue, queryvalues1);
			break;
		case "is empty":
			if (!queryvalues1.isEmpty())
				Assert.assertTrue("DB verification failed", false);

			break;
		default:
			LogHandler
					.warn("is matched/is not matched allowed for DB verification");
			Assert.assertTrue(
					"is matched/is not matched allowed for DB verification",
					false);
			break;
		}

	}

	/**
	 * Verify_ widget_error_message.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Verify Widget error message "sssss"</code>
	 * </pre>
	 * 
	 * @param errmessage
	 *            the errmessage
	 */
	@Then("^Verify Widget error message \"(.*?)\"$")
	public void Verify_Widget_error_message(String errmessage) {

		System.out.println("error message " + errmessage
				+ commonpf.WE_ERRORMESSSAGE_CSS.getText());
		String actualmessage = commonpf.WE_ERRORMESSSAGE_CSS.getText();
		Assert.assertTrue("Actual widget error message " + actualmessage
				+ "matches with Excpected message " + errmessage,
				errmessage.equals(actualmessage));
	}

	/**
	 * Verify_mandatory_fields.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>And Verify mandatory fields "ssss"</code>
	 * </pre>
	 * 
	 * @param fields
	 *            the fields
	 */
	@Then("^Verify mandatory fields \"(.*?)\"$")
	public void Verify_mandatory_fields(List<String> fields) {
		LogHandler.info("Verify_mandatory_fields(String fields[])");
		try {
			for (String flag : fields) {
				boolean condition = SeleniumUtilities.findElement(
						Identifier.XPATH,
						String.format(commonpf.MANDATORY_FIELD, flag))
						.isDisplayed();
				if (!condition) {
					LogHandler.warn("Mandatory icon not present for field"
							+ flag);
					Assert.assertTrue("Mandatory icon not present for field"
							+ flag, false);
				}
			}
		} catch (WebDriverException e) {
			LogHandler.warn("Page Object MANDATORY_FIELD mismatch");
			Assert.assertTrue("Page Object MANDATORY_FIELD mismatch", false);
		} catch (Exception e) {
			LogHandler.warn("SeleniumUtilities.findElement() Exception");
			Assert.assertTrue("SeleniumUtilities.findElement() Exception",
					false);
		}
	}

	/**
	 * Verify_tool_tip.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>	And Verify SSO tool tip
	 * |Server Host|Server Port|User Name|User Password|Cache Interval (in minutes)|Token Maximum Inactive Interval (in minutes)|Enable|
	 * |Only includes number,letter,"-","_" and ".". Can´t start or end with "-",".". The length should be 1-255. The substring separated by "." can´t start or end with "-" and length should be 1~63.|Only includes range from 1-65535. If not specified, default port 443 for HTTPS protocol is assumed.|Composed of any characters except space with length no longer than 32 and no less than 6.|Composed of any characters except space with length no longer than 32 and no less than 6.|Cache Interval used by IDP and remote client to determine how often token information is updated in memory. Only allow values from range 0(mins) - 120(mins).|Maximum Time User can remain inactive. Only allow values from range 2(mins) - 120(mins).|Enable SSO|
	 * </code>
	 * </pre>
	 * 
	 * @param table
	 *            the table
	 */
	@Then("^Verify SSO tool tip$")
	public void Verify_tool_tip(DataTable table) {

		LogHandler.info("Verify_tool_tip(String fields[])");

		boolean condition;
		LogHandler.info("enter_all_fields_with_valid_values(DataTable arg1)");
		List<Map<String, String>> map = table
				.asMaps(String.class, String.class);
			try {
				WebElement we;
		//	for (String field : table.topCells()) {
				we = null;
				Actions action = new Actions(SeleniumUtilities.getDriver());
				
			//	action.moveToElement(mypasswordpf.IMG_ID2_ID_WE);
			//	String tool = "//label[contains(text(),'%s')]/../..//img";
			//	we = SeleniumUtilities.findElement(Identifier.XPATH, String.format("//label[contains(text(),'%s')]/../..//img", field));
			//	Actions action = new Actions(SeleniumUtilities.getDriver());
				action.moveToElement(commonpf.WE_SHOST);
				System.out.println(commonpf.WE_XPATH_TOOLTIP_TEXT.getText());
				/*
				condition = false;
				if (field.equals("Server Host")) {
					{
						String van = "Only includes number,letter,\"-\",\"_\" and \".\". Can´t start or end with \"-\",\".\". The length should be 1-255. The substring separated by \".\" can´t start or end with \"-\" and length should be 1~63.";
						
						String id = SeleniumUtilities.findElement(Identifier.XPATH,
								String.format(commonpf.TOOLTIPID_XPATH_WE, field))
								.getAttribute("id").trim();
						 SeleniumUtilities.findElement(Identifier.XPATH,
									String.format(commonpf.TOOLTIPID_XPATH_WE, field)).click();
						System.out.println("vantom"+id);
						System.out.println(SeleniumUtilities.findElement(Identifier.XPATH, String.format(commonpf.TOOLTIP_CONTENT_XPATH_WE, id)).getText().trim());
					//	if ((SeleniumUtilities.findElement(Identifier.XPATH, String.format(commonpf.TOOLTIP_CONTENT_XPATH_WE, id)).getText().trim()).equals(van))
							condition = true;

						}
					}
				 else {
					 String id = SeleniumUtilities.findElement(Identifier.XPATH,
								String.format(commonpf.TOOLTIPID_XPATH_WE, field))
								.getAttribute("id").trim();
					 SeleniumUtilities.findElement(Identifier.XPATH,
								String.format(commonpf.TOOLTIPID_XPATH_WE, field)).click();
					 System.out.println("vantom1"+map.get(0).get(field));
					 System.out.println("vantom"+id);
					 System.out.println("bool"+SeleniumUtilities.findElement(Identifier.XPATH, commonpf.TOOLTIP_CONTENT1_XPATH_WE).isDisplayed());
					 System.out.println("vantomhi"+SeleniumUtilities.findElement(Identifier.XPATH, commonpf.TOOLTIP_CONTENT1_XPATH_WE).getText());
						if ((SeleniumUtilities.findElement(Identifier.XPATH, String.format(commonpf.TOOLTIP_CONTENT_XPATH_WE, id)).getText().trim()).equals(map.get(0).get(field)))
							condition = true;
						
					}
				
				if (condition == false) {
					Assert.assertTrue(
							"Tool tip verification fails for" + field, false);
				}*/
			//}
		} catch (WebDriverException e) {
			LogHandler.warn("Page Object commonpf.TOOL_TIP mismatch");
			Assert.assertTrue("Page Object commonpf.TOOL_TIP mismatch", false);
		} catch (Exception e) {
			LogHandler.warn("SeleniumUtilities.findElement() Exception");
			Assert.assertTrue("SeleniumUtilities.findElement() Exception",
					false);
		}
	}

	/**
	 * Verify_any_sso_configuration_warning_message_is.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>AndVerify any widget warning message is "ssss"</code>
	 * </pre>
	 * 
	 * @param status
	 *            the status
	 */
	@When("^Verify any widget warning message is \"(.*?)\"$")
	public void verify_any_sso_configuration_warning_message_is(String status) {
		try {
			if ("not displayed".equals(status)) {
				if (SeleniumUtilities
						.isElementDisplayed(commonpf.WE_ERRORMESSSAGE_CSS))
					Assert.assertTrue("Warning message is displayed"
							+ commonpf.WE_ERRORMESSSAGE_CSS.getText(), false);
			}
			if ("displayed".equals(status)) {
				if (!SeleniumUtilities
						.isElementDisplayed(commonpf.WE_ERRORMESSSAGE_CSS))
					Assert.assertTrue("Warning message is not displayed", false);
			}
		} catch (NoSuchElementException e) {
			LogHandler.info("Page object not found for widget status" + status);
			// LogHandler.warn( "Page Object WE_ERRORMESSSAGE_CSS mismatch");

		} catch (WebDriverException e) {
			LogHandler.warn("Page Object WE_ERRORMESSSAGE_CSS mismatch");
			Assert.assertTrue("Page Object WE_ERRORMESSSAGE_CSS mismatch",
					false);
		}
	}

	/**
	 * Verify_the_page_title.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Verify the "sso config" page title has "SSO Configuration Details" </code>
	 * </pre>
	 * 
	 * @param page
	 *            the page
	 * @param title
	 *            the title
	 */
	@Then("^Verify the \"(.*?)\" page title has \"(.*?)\"$")
	public void verify_the_page_title(String page, String title) {
		LogHandler.info("void vrify_the_page_titke(String page, String title)"
				+ page + " , " + title);
		try {
			LogHandler.info("Title for the page :" + page + " is "
					+ commonpf.WE_PAGETITLE_CSS.getText());
			Assert.assertTrue("Title for the page :" + page + " is "
					+ commonpf.WE_PAGETITLE_CSS.getText() + " but should be "
					+ title, title.equals(commonpf.WE_PAGETITLE_CSS.getText()));
		} catch (WebDriverException e) {
			LogHandler.warn("Page Object WE_PAGETITLE_CSS mismatch");
			Assert.assertTrue("Page Object WE_PAGETITLE_CSS mismatch", false);
		}

	}

	/**
	 * Verify_the_panel_title.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Verify the "sso config" panel title has "Configure SSO Details" </code>
	 * </pre>
	 * 
	 * @param page
	 *            the page
	 * @param title
	 *            the title
	 */
	@Then("^Verify the \"(.*?)\" panel title has \"(.*?)\"$")
	public void verify_the_panel_title(String page, String title) {
		LogHandler.info("void vrify_the_page_titke(String page, String title)"
				+ page + " , " + title);
		try {
			LogHandler.info("Panel Title for the page :" + page + " is "
					+ commonpf.WE_PAGEPANEL_CSS.getText());
			Assert.assertTrue("Panel Title for the page :" + page + " is "
					+ commonpf.WE_PAGEPANEL_CSS.getText() + " but should be "
					+ title, title.equals(commonpf.WE_PAGEPANEL_CSS.getText()));
		} catch (WebDriverException e) {
			LogHandler.warn("Page Object WE_PAGEPANEL_CSS mismatch");
			Assert.assertTrue("Page Object WE_PAGEPANEL_CSS mismatch", false);
		}

	}

	/**
	 * Validate_logout_message.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Validate logout message</code>
	 * </pre>
	 */
	@Then("^Validate logout message$")
	public void validate_logout_message() {
		LogHandler.info("validate_logout_message()");
		try {
			if (!SeleniumUtilities
					.isElementDisplayed(commonpf.WE_LOGOUT_MESSAGE))
				Assert.assertTrue("Warning message is not displayed", false);
			else
				LogHandler.info("Clicked logout button");
		} catch (WebDriverException e) {
			LogHandler.warn("Page Object WE_LOGOUT_MESSAGE mismatch");
			Assert.assertTrue("Page Object WE_LOGOUT_MESSAGE mismatch", false);
		}

	}

	/**
	 * Click_settings_button.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Click Settings button</code>
	 * </pre>
	 */
	@Then("^Click Settings button$")
	public void click_settings_button() {
		LogHandler.info("click_settings_button()");
		try {
			SeleniumUtilities.click(commonpf.WE_SETTINGS_BUTTON);
		} catch (WebDriverException e) {
			LogHandler.warn("Page Object WE_SETTINGS_BUTTON mismatch");
			Assert.assertTrue("Page Object WE_SETTINGS_BUTTON mismatch", false);
		}
	}

	/**
	 * Click_logout_button.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Click logout button</code>
	 * </pre>
	 */
	@Then("^Click logout button$")
	public void click_logout_button() {
		LogHandler.info("click_logout_button()");
		try {
			SeleniumUtilities.click(commonpf.WE_LOGOUT_BUTTON);
		} catch (WebDriverException e) {
			LogHandler.warn("Page Object WE_LOGOUT_BUTTON mismatch");
			Assert.assertTrue("Page Object WE_LOGOUT_BUTTON mismatch", false);
		}

	}

	/**
	 * Validate_invalid_icon.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And Validate invalid icon</code>
	 * </pre>
	 */
	@Given("^Validate invalid icon$")
	public void validate_invalid_icon() {
		LogHandler.info("validate_invalid_icon");
		try {
			boolean flag = commonpf.WE_INVALID_ICON.isDisplayed();
			if (!flag) {
				LogHandler.warn("Page Object WE_INVALID_ICON mismatch");
				Assert.assertTrue("Page Object WE_INVALID_ICON mismatch", false);
			}
		} catch (WebDriverException e) {
			LogHandler.warn("Page Object WE_INVALID_ICON mismatch");
			Assert.assertTrue("Page Object WE_INVALID_ICON mismatch", false);
		}

	}

	/**
	 * Frame_response_for_role.
	 * 
	 * <pre>
	 * <b>Gherkin</b>
	 *     <code>And frame_response_for_role</code>
	 * </pre>
	 * 
	 * @param val
	 *            the val
	 * @return the string
	 */
	@Given("^frame_response_for_role$")
	public static String frame_response_for_role(String val) {
		String answer = "ROLES=Root,";
		if (!queryvalues1.isEmpty()) {
			for (Map<String, String> dbdatavalue : queryvalues1) {
				answer = answer.concat(dbdatavalue.values().toString());
			}

			String var = answer.substring(0, answer.length() - 2);
			val = var.replaceAll("[^a-zA-Z,_=]", "");
			Hooks.scenario.write(val);
			LogHandler.info("ROLES_EXPECTED_RESPONSE" + val);
			return val;
		} else {
			return "ROLES=Root";
		}
	}

}
