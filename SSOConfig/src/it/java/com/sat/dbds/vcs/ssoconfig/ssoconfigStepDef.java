/*
 * 
 */
package com.sat.dbds.vcs.ssoconfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.cisco.dbds.utils.cucumber.Hooks;
import com.cisco.dbds.utils.logging.LogHandler;
import com.cisco.dbds.utils.restapi.RestfulIFCommonFunctions;
import com.cisco.dbds.utils.selenium.Identifier;
import com.cisco.dbds.utils.selenium.SeleniumUtilities;
import com.cisco.dbds.utils.validation.Validate;
import com.sat.dbds.vcs.common.CommonStepDef;
import com.sat.dbds.vcs.dbconnection.DBconnection;
import com.sat.dbds.vcs.ssoconfig.pagefactory.SSOconfigPagefactory;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
//import com.cisco.dbds.vcs.ssoconfig.pagefactory.ssotoast;



// TODO: Auto-generated Javadoc
/**
 * The Class ssoconfigStepDef.
 */
public class ssoconfigStepDef {

	/** The ssoconfigpf. */
	private SSOconfigPagefactory ssoconfigpf;

	/** The commonpf. */
	public CommonStepDef commonpf;


	/** The runtype. */
	public String runtype;

	/** The ssoarray. */
	public JSONObject ssoarray;

	/** The ssoarrayvalue. */
	public JSONObject ssoarrayvalue;
	/** The request url. */
	String requestURL;

	/** The response. */
	HttpResponse response;


	/** The client. */
	public HttpClient client = HttpClientBuilder.create().build();

	/** The value. */
	public static List<Map<String, String>> value = new ArrayList<>();

	/**
	 * Instantiates a new ssoconfig step def.
	 */
	public ssoconfigStepDef() {
		runtype = System.getProperty("run.type");
		LogHandler.info( "Run Type:"+runtype);
		if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))
			ssoconfigpf = PageFactory.initElements(SeleniumUtilities.getDriver(),SSOconfigPagefactory.class);
		else
		{
		
			ssoarray = new JSONObject();
			ssoarrayvalue = new JSONObject();
		}

	}


	/**
	 *<pre>
	 *<b>Gherkin</b>
	 *<code>When Enter all sso fields with valid values
	 *|ServerHost|ServerPort|UserName|UserPassword|CacheInterval|TokenMaximumInactiveInterval|Enable|
	 *|<<vcs.ip>>|<<vcs.port>>|ssoadmin|dv3group|2|120|true|</code>	
	 * </pre>
	 * @param ssotable the ssotable
	 */
	@When("^Enter all sso fields with valid values$")	 
	public void enter_all_sso_fields_with_valid_values(DataTable ssotable){
		LogHandler.info( "enter_all_fields_with_valid_values(DataTable arg1)");
		List<Map<String, String>> lst = ssotable.asMaps(String.class,
				String.class);
		for (Map<String, String> ssotablevalue : lst) 
		{

			enter_ssoconfig_server_host(ssotablevalue.get("ServerHost"));
			enter_ssoconfig_server_port(ssotablevalue.get("ServerPort"));
			enter_ssoconfig_user_name(ssotablevalue.get("UserName"));
			enter_ssoconfig_user_password(ssotablevalue.get("UserPassword"));
			enter_ssoconfig_cache_interval(ssotablevalue.get("CacheInterval"));
			enter_ssoconfig_token_maximum_inactive_intervall(ssotablevalue.get("TokenMaximumInactiveInterval"));
			/*if (ssotablevalue.get("Enable").equalsIgnoreCase("true"))
			{
				enable_ssoconfig_enable();	
			}else if (ssotablevalue.get("Enable").equalsIgnoreCase("false"))
			{
				enable_ssoconfig_disable();
			}	*/	
		}
	}
	@Given("^Verify tooltip for Server Host field$")
	public void verify_tooltip_for_server_host()
	{
		LogHandler.info("verify_tooltip_for_server_host()");
		try
		{
			String tooltip = "Only includes number,letter,\"-\",\"_\" and \".\". Can't start or end with \"-\",\".\".The length should be 1-255. The substring separated by \".\" can't start or end with \"-\" and length should be 1~63.";
			Actions action = new Actions(SeleniumUtilities.getDriver());
			action.moveToElement(SeleniumUtilities.findElement(Identifier.XPATH, ".//label[normalize-space(text())='Server Host']/../..//img")).perform();
			SeleniumUtilities.wait(2);
			String actual_text =SeleniumUtilities.findElement(Identifier.XPATH, ".//*[contains(@class,'dijitTooltip dijitTooltipRight')]//*[@class='dijitTooltipContainer dijitTooltipContents']").getText().trim();
			actual_text = actual_text.replaceAll("\\r|\\n", "");
			actual_text = actual_text.trim();
			//System.out.println(actual_text);
			Assert.assertTrue("Actual tooltip "+actual_text+"does not match with expected tooltip "+tooltip, actual_text.equals(tooltip));
			SeleniumUtilities.wait(4);
			
		}
		catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			Assert.assertTrue("Page Object TOOLTIP_FOR_SERVER_HOST not found on VCS Console", false);
		}
	}


	/**
	 * Enter_ssoconfig_server_host.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Enter ssoconfig Server host "hostname"</code>	
	 * </pre>
	 * @param serverhost the serverhost
	 */
	@Given("^Enter ssoconfig Server host \"(.*?)\"$")
	public void enter_ssoconfig_server_host(String serverhost) {
		LogHandler.info( "enter_ssoconfig_server_host(String serverhost)");
		if (serverhost.startsWith("<<") && serverhost.endsWith(">>") )
		{
			//serverhost=CommonStepDef.read_system_variable(serverhost);
			serverhost=Validate.readsystemvariable(serverhost);
		}


		Hooks.scenario.write(serverhost);
		LogHandler.info("SERVERHOST"+serverhost);
		try
		{

			if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))	
			{
				ssoconfigpf.WE_ID_SERVER_HOST.clear();
				ssoconfigpf.WE_ID_SERVER_HOST.sendKeys(serverhost);	
			}
			else
			{

				ssoarrayvalue.put("serverhost",serverhost);
				System.out.println("sso json"+ssoarrayvalue);
			}
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object SERVER_HOST  mismatch");
			Assert.assertTrue("Page Object SERVER_HOST mismatch",false);
		}	

	}


	/**
	 * Enter_ssoconfig_server_port.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Enter ssoconfig Server port "6605"</code>	
	 * </pre>
	 * @param serverport the serverport
	 */
	@Given("^Enter ssoconfig Server port \"(.*?)\"$")
	public void enter_ssoconfig_server_port(String serverport) {
		LogHandler.info( "enter_ssoconfig_server_port(String serverport)");

		if (serverport.startsWith("<<") && serverport.endsWith(">>") )
		{
			//serverport=CommonStepDef.read_system_variable(serverport);
			serverport=Validate.readsystemvariable(serverport);

		}


		Hooks.scenario.write(serverport);
		LogHandler.info("SERVERPORT"+serverport);
		try
		{
			if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))	
			{
				ssoconfigpf.WE_ID_SERVER_PORT.clear();
				ssoconfigpf.WE_ID_SERVER_PORT.sendKeys(serverport);	
			}
			else
			{
				ssoarrayvalue.put("serverport",serverport);
				System.out.println("sso json"+ssoarrayvalue);

			}
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object SERVER_PORT mismatch");
			Assert.assertTrue("Page Object SERVER_PORT mismatch",false);
		}	
	}

	/**
	 * Enter_ssoconfig_user_name.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Enter ssoconfig User name "ssoadmin"</code>	
	 * </pre>
	 * @param username the username
	 */
	@Given("^Enter ssoconfig User name \"(.*?)\"$")
	public void enter_ssoconfig_user_name(String username) {
		LogHandler.info( "void enter_ssoconfig_user_name(String username)");
		try
		{
			if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))	
			{
				ssoconfigpf.WE_ID_USER_NAME.clear();
				ssoconfigpf.WE_ID_USER_NAME.sendKeys(username);		
			}
			else
			{
				ssoarrayvalue.put("username",username);
				System.out.println("sso json"+ssoarrayvalue);
			}
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object USER_NAME mismatch");
			Assert.assertTrue("Page Object USER_NAME mismatch",false);
		}	
	}


	/**
	 * Enter_ssoconfig_user_password.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Enter ssoconfig user password "ssoadmin"</code>	
	 * </pre>
	 * @param password the password
	 */
	@Given("^Enter ssoconfig user password \"(.*?)\"$")
	public void enter_ssoconfig_user_password(String password) {
		LogHandler.info( "void enter_ssoconfig_user_password(String password)");
		try
		{
			if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))	
			{
				ssoconfigpf.WE_ID_PASSWORD.clear();
				ssoconfigpf.WE_ID_PASSWORD.sendKeys(password);	
			}
			else
			{
				ssoarrayvalue.put("password",password);
				System.out.println("sso json"+ssoarrayvalue);
			}
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object PASSWORD mismatch");
			Assert.assertTrue("Page Object PASSWORD mismatch",false);
		}	
	}

	/**
	 * Enter_ssoconfig_cache_interval.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Enter ssoconfig Cache Interval "2"</code>	
	 * </pre>
	 * @param cacheint the cacheint
	 */
	@Given("^Enter ssoconfig Cache Interval \"(.*?)\"$")
	public void enter_ssoconfig_cache_interval(String cacheint) {
		LogHandler.info( "void enter_ssoconfig_cache_interval(String cacheint)");
		try
		{
			if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))	
			{
				ssoconfigpf.WE_ID_CACHE_INT.clear();
				ssoconfigpf.WE_ID_CACHE_INT.sendKeys(cacheint);		
			}
			else
			{
				ssoarrayvalue.put("cacheinterval",cacheint);
				System.out.println("sso json"+ssoarrayvalue);
			}

		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object CACHE_INT mismatch");
			Assert.assertTrue("Page Object CACHE_INT mismatch",false);
		}	
	}

	/**
	 * Enter_ssoconfig_token_maximum_inactive_intervall.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Enter ssoconfig Token Maximum Inactive Interval "120"</code>	
	 * </pre>
	 * @param MAX_INACT_TIME the max inact time
	 */
	@Given("^Enter ssoconfig Token Maximum Inactive Interval \"(.*?)\"$")
	public void enter_ssoconfig_token_maximum_inactive_intervall(String MAX_INACT_TIME) {
		LogHandler.info( "void enter_ssoconfig_token_maximum_inactive_intervall(String MAX_INACT_TIME)");
		try
		{
			if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))	
			{
				ssoconfigpf.WE_ID_MAX_INACT_TIME.clear();
				ssoconfigpf.WE_ID_MAX_INACT_TIME.sendKeys(MAX_INACT_TIME);		
			}
			else
			{
				ssoarrayvalue.put("tokenmaxinactivitytime",MAX_INACT_TIME);
				System.out.println("sso json"+ssoarrayvalue);
			}
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object MAX_INACT_TIME mismatch");
			Assert.assertTrue("Page Object MAX_INACT_TIME mismatch",false);
		}	
	}

	/**
	 * Enable_ssoconfig_enable.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Enable ssoconfig Enable</code>	
	 * </pre>
	 */
	@Given("^Enable ssoconfig Enable $")
	public void enable_ssoconfig_enable() {
		LogHandler.info( "void enable_ssoconfig_enable()");
		try
		{
			if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))	
			{
				if ("false".equals(ssoconfigpf.WE_ID_IS_ACTIVE.getAttribute("aria-checked")))
					ssoconfigpf.WE_ID_IS_ACTIVE.click();   

			}
			else
			{
				ssoarrayvalue.put("isactive","Y");
				System.out.println("sso json"+ssoarrayvalue);
			}
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object IS_ACTIVE mismatch");
			Assert.assertTrue("Page Object IS_ACTIVE mismatch",false);
		}	
	}

	/**
	 * Enable_ssoconfig_disable.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Enable ssoconfig Disable </code>	
	 * </pre>
	 */
	@Given("^Enable ssoconfig Disable $")
	public void enable_ssoconfig_disable() {
		LogHandler.info( "void enable_ssoconfig_disable()");
		try
		{
			if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))	
			{
				if ("true".equals(ssoconfigpf.WE_ID_IS_ACTIVE.getAttribute("aria-checked")))
					ssoconfigpf.WE_ID_IS_ACTIVE.click();   
			}
			else
			{
				ssoarrayvalue.put("isactive","N");
				System.out.println("sso json"+ssoarrayvalue);
			}

		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object IS_ACTIVE mismatch");
			Assert.assertTrue("Page Object IS_ACTIVE mismatch",false);
		}	
	}


	/**
	 * Click_on_ssoconfig_button.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Click on ssoconfig "Save" button</code>	
	 * </pre>
	 * @param ssoconfigbutton the ssoconfigbutton
	 * @throws Exception the exception
	 */
	@When("^Click on ssoconfig \"(.*?)\" button$")
	public void click_on_ssoconfig_button(String ssoconfigbutton) throws Exception{

		LogHandler.info( "click_on_ssoconfig_button(String ssoconfigbutton)"+ssoconfigbutton);
		switch (ssoconfigbutton.toLowerCase()) {
		case "save":
			try
			{
				if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))	
				{
					ssoconfigpf.WE_ID_SAVE_BTN.click(); 
					LogHandler.info("Clicked " + ssoconfigbutton+" button");
				}
				else
				{
					ssoarray.put("ssoconfig", ssoarrayvalue);
					LogHandler.info("ssoconfigjsonsathish"+ssoarray);

					//satcook(ssoarray); 
					ssosave(ssoarray); 

				}

			}
			catch (WebDriverException | NullPointerException e)
			{			
				LogHandler.warn( "Page Object SAVE_BTN mismatch");
				Assert.assertTrue("Page Object SAVE_BTN mismatch",false);
			}
			break;
		case "reset":
			try
			{
				ssoconfigpf.WE_ID_RESET_BTN.click();   
			}
			catch (WebDriverException | NullPointerException e)
			{			
				LogHandler.warn( "Page Object RESET_BTN mismatch");
				Assert.assertTrue("Page Object RESET_BTN mismatch",false);
			}
			break;
		default:
			LogHandler.warn( "No button named: "+ssoconfigbutton);
			Assert.assertTrue("No button named: "+ssoconfigbutton,false);
			break;
		}

	}






	/**
	 * Verify_the_below_widgets_details.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And verify the below widgets details
	|  Widget     | Status  |
	|Save Button  | Enabled |
	|Reset Button | Enabled |</code>	
	 * </pre>
	 * @param widget the widget
	 */
	@When("^verify the below widgets details$")
	public void verify_the_below_widgets_details(DataTable widget) {
		LogHandler.info( "enter_all_fields_with_valid_values(DataTable arg1)");
		List<Map<String, String>> widgetlst = widget.asMaps(String.class,
				String.class);
		for (Map<String, String> widgetvalue : widgetlst)		
		{
			String wname=widgetvalue.get("Widget");			
			switch (wname.toLowerCase()) {
			case "save button":
				savebuttonstatus(widgetvalue.get("Status"));
				break;
			case "reset button":
				resetbuttonstatus(widgetvalue.get("Status"));
				break;
			default:
				LogHandler.warn( "No Widget named: "+wname);
				Assert.assertTrue("No Widget named: "+wname,false);
				break;
			}


		}
	}



	/**
	 * Savebuttonstatus.
	 *
	 * @param status the status
	 */
	public void savebuttonstatus(String status) {
		LogHandler.info( "savebuttonstatus(String status)");
		try
		{			
			if (SeleniumUtilities.isElementDisplayed(ssoconfigpf.WE_ID_SAVE_BTN))
			{
				boolean b =Boolean.parseBoolean(ssoconfigpf.WE_ID_SAVE_BTN.getAttribute("aria-disabled"));
				if ("Enabled".equals(status))
				{

					Assert.assertTrue( "Save button is not enabled"+ssoconfigpf.WE_ID_SAVE_BTN.getAttribute("aria-disabled"),!b);
				}
				if ("Disabled".equals(status))
					Assert.assertTrue( "Save button is not disabled"+ssoconfigpf.WE_ID_SAVE_BTN.getAttribute("aria-disabled"),b);		    		
			}
			else
				Assert.assertTrue("SAVE BUTTON is not visble ",false);			

		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object WE_ID_SAVE_BTN mismatch");
			Assert.assertTrue("Page Object WE_ID_SAVE_BTN mismatch",false);
		}	
	}

	/**
	 * Resetbuttonstatus.
	 * 
	 * @param status the status
	 */
	public void resetbuttonstatus(String status) {
		LogHandler.info( "resetbuttonstatus(String status)");
		try
		{			
			if (SeleniumUtilities.isElementDisplayed(ssoconfigpf.WE_ID_RESET_BTN))
			{
				boolean b =Boolean.parseBoolean(ssoconfigpf.WE_ID_RESET_BTN.getAttribute("aria-disabled"));
				//boolean b =ssoconfigpf.WE_ID_RESET_BTN.isEnabled();

				if ("Enabled".equals(status))
					Assert.assertTrue( "Reset button is not enabled"+ssoconfigpf.WE_ID_RESET_BTN.getAttribute("aria-disabled"),!b);
				if ("Disabled".equals(status))
					Assert.assertTrue( "Reset button is not disabled"+ssoconfigpf.WE_ID_RESET_BTN.getAttribute("aria-disabled"),b);		    		
			}
			else
				Assert.assertTrue("Reset button is not visble ",false);			

		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object WE_ID_RESET_BTN mismatch");
			Assert.assertTrue("Page Object WE_ID_RESET_BTN mismatch",false);
		}	
	}

	/**
	 * Verify_success_toaster.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>Then Verify success toaster</code>	
	 * </pre>
	 */
	@Given("^Verify success toaster$")
	public void verify_success_toaster() {
		LogHandler.info(" verify_success_toaster");
		try
		{
			if (!SeleniumUtilities.isElementDisplayed(ssoconfigpf.WE_CSS_TOASTER))
				Assert.assertTrue("Toaster WE_CSS_TOASTER not thrown",false);
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object WE_CSS_TOASTER mismatch");
			Assert.assertTrue("Page Object WE_CSS_TOASTER mismatch",false);
		}
	}

	/**
	 * Verify_toaster_title.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>And Verify title "SSO Configuration" for "SSO" toaster</code>	
	 * </pre>
	 * @param title the title
	 * @param toaster the toaster
	 */
	@Given("^Verify title \"(.*?)\" for \"(.*?)\" toaster$")
	public void verify_toaster_title(String title, String toaster) {
		LogHandler.info("verify_toaster_title(String title)");
		try
		{
			String var = null;

			if(toaster.contains("SSO"))
				var="ssotoast";
			else if (toaster.contains("Network"))
				var="nemgmttoast";
			if (!SeleniumUtilities.findElement(Identifier.XPATH,String.format(ssoconfigpf.WE_XPATH_TOASTER_TITLE, var,title)).isDisplayed())
				Assert.assertTrue("Toaster WE_XPATH_SSOTOASTER_TITLE title mismatch",false);
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object WE_XPATH_SSOTOASTER_TITLE mismatch");
			Assert.assertTrue("Page Object WE_XPATH_SSOTOASTER_TITLE mismatch",false);
		}

		catch (Exception e){
			LogHandler.warn( "SeleniumUtilities.findElement() Exception");
			Assert.assertTrue("SeleniumUtilities.findElement() Exception",false);
		}
	}

	/**
	 * Verify_toaster_content.
	 * <pre>
	 * <b>Gherkin</b>
	 * <code>And Verify content "saved and published successfully." for "SSO" toaster</code>	
	 * </pre>

	 * @param content the content
	 * @param toaster the toaster
	 */
	@Given("^Verify content \"(.*?)\" for \"(.*?)\" toaster$")
	public void verify_toaster_content(String content, String toaster) {
		LogHandler.info("verify_toaster_content(String content)");
		try
		{
			String var = null;
			if(toaster.contains("SSO"))
				var="ssotoast";
			else if (toaster.contains("Network"))
				var="nemgmttoast";
			if (!SeleniumUtilities.findElement(Identifier.XPATH,String.format(ssoconfigpf.WE_XPATH_TOASTER_BODY, var, content)).isDisplayed())
				Assert.assertTrue("Toaster WE_XPATH_SSOTOASTER_BODY content mismatch",false);	
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object WE_XPATH_SSOTOASTER_BODY mismatch");
			Assert.assertTrue("Page Object WE_XPATH_SSOTOASTER_BODY mismatch",false);
		}

		catch (Exception e){
			LogHandler.warn( "SeleniumUtilities.findElement() Exception");
			Assert.assertTrue("SeleniumUtilities.findElement() Exception",false);
		}
	}



	/**
	 * Click_on_ecs_dashboard_tab.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>When Click on ECS Dashboard tab "String"</code>	
	 * </pre>
	 * @param tab the tab
	 */
	@Given("^Click on ECS Dashboard tab \"(.*?)\"$")
	public void click_on_ecs_dashboard_tab(String tab) {
		LogHandler.info("click_on_ecs_dashboard_tab(String tab)");
		try
		{
			SeleniumUtilities.findElement(
					Identifier.XPATH,
					String.format(ssoconfigpf.WE_XPATH_ECSDASHBOARD_TAB, tab)).click();
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object WE_XPATH_ECSDASHBOARD_TAB mismatch");
			Assert.assertTrue("Page Object WE_XPATH_ECSDASHBOARD_TAB mismatch",false);
		}
		catch (Exception e){
			LogHandler.warn( "SeleniumUtilities.findElement() Exception");
			Assert.assertTrue("SeleniumUtilities.findElement() Exception",false);
		}
	}


	/**
	 * Select_ecs_dashboard_user.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>When Select ECS Dashboard user "ecname"</code>	
	 * </pre>
	 * @param user the user
	 */
	@Given("^Select ECS Dashboard user \"(.*?)\"$")
	public void select_ecs_dashboard_user(String user) {
		LogHandler.info("click_ecs_dashboard_user(String user)");
		try
		{

			SeleniumUtilities.findElement(Identifier.XPATH,ssoconfigpf.WE_XPATH_ECSDASHBOARD_DROPDOWN).click();
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object WE_XPATH_ECSDASHBOARD_DROPDOWN mismatch");
			Assert.assertTrue("Page Object WE_XPATH_ECSDASHBOARD_DROPDOWN mismatch",false);
		}
		catch (Exception e){
			LogHandler.warn( "SeleniumUtilities.findElement() Exception");
			Assert.assertTrue("SeleniumUtilities.findElement() Exception",false);
		}
		try
		{

			SeleniumUtilities.findElement(
					Identifier.XPATH,
					String.format(ssoconfigpf.WE_XPATH_ECSDASHBOARD_USER, user)).click();
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object WE_XPATH_ECSDASHBOARD_USER mismatch");
			Assert.assertTrue("Page Object WE_XPATH_ECSDASHBOARD_USER mismatch",false);
		}

		catch (Exception e){
			LogHandler.warn( "SeleniumUtilities.findElement() Exception");
			Assert.assertTrue("SeleniumUtilities.findElement() Exception",false);
		}

	}

	/**
	 * Click_on_ecs_dashboard_ne.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>When "Enable" ECS Dashboard ne "ecname"</code>	
	 * </pre>
	 * @param action the action
	 * @param ne the ne
	 * @throws SQLException 
	 */
	@Given("^\"(.*?)\" ECS Dashboard ne \"(.*?)\"$")
	public void click_on_ecs_dashboard_ne(String action, String ne) throws SQLException {
		LogHandler.info("click_on_ecs_dashboard_tab(String ne)");
		//commonpf.excute_the_query("select NEID from NETWORK_ELEMENT_ACCESS where USERID='root'");
		DBconnection db;
		Boolean dboutput = null;
		List<Map<String,String>> queryresult = null;
		db= new DBconnection();
		queryresult =  db.dbselect("select NEID from NETWORK_ELEMENT_ACCESS where USERID='root'");	
		if (queryresult.toString() != null)
		{
			if (queryresult.toString().contains("marina"))
			{
				dboutput = true;
			}
			else
				dboutput = false;
		}
		else
			dboutput = false;

		try
		{

			if (((action.equals("Enable")) && (dboutput.equals(false))) || ((action.equals("Disable")) && (dboutput.equals(true))))
			{

				SeleniumUtilities.findElement(
						Identifier.XPATH,
						String.format(ssoconfigpf.WE_XPATH_ECSDASHBOARD_EC, ne)).click();

			}

		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object WE_XPATH_ECSDASHBOARD_EC mismatch");
			Assert.assertTrue("Page Object WE_XPATH_ECSDASHBOARD_EC mismatch",false);
		}
		catch (Exception e){
			LogHandler.warn( "SeleniumUtilities.findElement() Exception");
			Assert.assertTrue("SeleniumUtilities.findElement() Exception",false);
		}
	}

	/**
	 * Click_on_network_element_save.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>And Click on Network Element Save button</code>	
	 * </pre>
	 */
	@Given("^Click on Network Element Save button$")
	public void click_on_network_element_save() {
		LogHandler.info("click_on_network_element_save()");
		try
		{
			ssoconfigpf.WE_ID_NE_SAVE_BTN.click();
		}
		catch (WebDriverException | NullPointerException e)
		{			
			LogHandler.warn( "Page Object WE_ID_NE_SAVE_BTN mismatch");
			Assert.assertTrue("Page Object WE_ID_NE_SAVE_BTN mismatch",false);
		}
	}



	/**
	 * Verify_the_widget_error_message_status.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>When Verify the widget error message status</code>	
	 * </pre>
	 * @param arg1 the arg1
	 */
	@When("^Verify the widget error message status$")
	public void verify_the_widget_error_message_status(DataTable arg1) {
		ssoconfigpf.WE_ID_SERVER_HOST.sendKeys("d3333.");
		JavascriptExecutor js = (JavascriptExecutor) SeleniumUtilities.getDriver();
		Object s=js.executeScript("return document.getElementById(\"a\").validity.valid");
		System.out.println("bubble message"+s);
	}

	/**
	 * Clear_the_textbox.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>When Clear the text box "textbox"</code>	
	 * </pre>
	 * @param tag the tag
	 */
	@Given("^Clear the text box \"(.*?)\"$")
	public void clear_the_textbox(String tag)
	{
		LogHandler.info("clear_the_textbox(String tag)");
		try
		{
			if (tag.equals("Server Host"))
				ssoconfigpf.WE_ID_SERVER_HOST.clear();	
			else if (tag.equals("User Name"))
				ssoconfigpf.WE_ID_USER_NAME.clear();	
			else if (tag.equals("Password"))
				ssoconfigpf.WE_ID_PASSWORD.clear();	
			else if (tag.equals("Max Inactive"))
			{
				ssoconfigpf.WE_ID_MAX_INACT_TIME.clear();
				ssoconfigpf.WE_ID_MAX_INACT_TIME.click();
			}
			else if (tag.equals("Server Port"))
				ssoconfigpf.WE_ID_SERVER_PORT.clear();
			else if (tag.equals("Cache Interval"))
				ssoconfigpf.WE_ID_CACHE_INT.clear();
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object mismatch for"+ tag, false);
			LogHandler.warn("Mismatch in clearing WebElement" + tag);
		}


	}


	/**
	 * Click_the_web element.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>When Click the WebElement "WebElement"</code>	
	 * </pre>
	 * @param tag the tag
	 */
	@Given("^Click the WebElement \"(.*?)\"$")
	public void click_the_webElement(String tag)
	{
		LogHandler.info("click_the_webElement(String tag)");
		try
		{
			if (tag.equals("Server Host"))
				ssoconfigpf.WE_ID_SERVER_HOST.click();	
			else if (tag.equals("User Name"))
				ssoconfigpf.WE_ID_USER_NAME.sendKeys();	
			else if (tag.equals("Password"))
				ssoconfigpf.WE_ID_PASSWORD.click();	
			else if (tag.equals("Max Inactive"))
				ssoconfigpf.WE_ID_MAX_INACT_TIME.click();	
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object mismatch for"+ tag, false);
			LogHandler.warn("Mismatch in clicking WebElement" + tag);
		}

	}

	/**
	 * Gets the _values_from_ ui.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>When Get values from UI</code>	
	 * </pre>
	 * @return the _values_from_ ui
	 */
	@Given("^Get values from UI$")
	public void get_values_from_UI() {

		Map<String,String> map = new HashMap<String,String>();
		int cache, maxinactive;
		String cache2, maxinactive2;
		value.clear();
		try
		{
			map.put("SERVERHOST", ssoconfigpf.WE_ID_SERVER_HOST.getAttribute("value"));
			map.put("SERVERPORT", ssoconfigpf.WE_ID_SERVER_PORT.getAttribute("value"));
			map.put("USERNAME", ssoconfigpf.WE_ID_USER_NAME.getAttribute("value"));
			cache = Integer.valueOf(ssoconfigpf.WE_ID_CACHE_INT.getAttribute("value"));
			cache = cache * 60000;
			cache2 = String.valueOf(cache);
			map.put("CACHEINTERVAL", cache2);
			maxinactive = Integer.valueOf(ssoconfigpf.WE_ID_MAX_INACT_TIME.getAttribute("value"));
			maxinactive = maxinactive * 60000;
			maxinactive2 = String.valueOf(maxinactive);
			map.put("TOKENMAXINACTIVITYTIME", maxinactive2);
		}
		catch (WebDriverException e)
		{
			LogHandler.warn("Unable to get values from SSO WUI");
			Assert.assertTrue("Unable to get values from SSO WUI", false);
		}

		value.add(map);

	}


	/**
	 * Verify_ d b_match.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>When Verify DB match</code>	
	 * </pre>
	 */
	@Given("^Verify DB match$")
	public void verify_DB_match() {


		LogHandler.info("verify_DB_match()");
		commonpf.db.testdatacomparedbdata(value, commonpf.queryvalues1);


	}


	/**
	 * Satcook.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>When Frame a URL with "https" with below server details
	 * |server    |port        |context    |AUTHENTUCATEURL          |
	 * |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |</code>	
	 * </pre>
	 * @param ssoarray2 the ssoarray2
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void satcook(JSONObject ssoarray2) throws ClientProtocolException, IOException {

		HttpClient client;		 
		String url = "https://10.78.217.148:6605/vcsconsole/rs/ssoconfig/updateSsoConfig";
		client =getHttpClient("https",6605);
		Hooks.scenario.write("https://10.78.217.148:6605/vcsconsole/rs/ssoconfig/updateSsoConfig");	  
		HttpPost getRequest = new HttpPost(url);

		getRequest.addHeader("content-type", "application/json; charset=UTF-8");
		Hooks.scenario.write("content-type"+"application/json; charset=UTF-8");

		String value= SeleniumUtilities.getDriver().manage().getCookieNamed("CMC_SSO_COOKIE").toString();
		String CMC=value.substring(value.indexOf("=")+1,value.indexOf(";") );
		LogHandler.info("CMC_SSO_COOKIE"+":"+CMC);

		value= SeleniumUtilities.getDriver().manage().getCookieNamed("JSESSIONID").toString();
		String sess=value.substring(value.indexOf("=")+1,value.indexOf(";") );
		LogHandler.info("JSESSIONID"+":"+sess);

		Hooks.scenario.write("Cookie: "+ "slideMenuPinnedStr=false; JSESSIONID="+sess+"; username=root; SESSION_TIMEOUT=1800; ssoUserName=root; SSOActiveDomainId=8008; SSOAuthProvider=root; CMC_SSO_COOKIE="+CMC);

		getRequest.addHeader("Cookie", "slideMenuPinnedStr=false; JSESSIONID="+sess+"; username=root; SESSION_TIMEOUT=1800; ssoUserName=root; SSOActiveDomainId=8008; SSOAuthProvider=root; CMC_SSO_COOKIE="+CMC);
		getRequest.setEntity(new StringEntity(ssoarray2.toJSONString(), "UTF8"));		  
		HttpResponse response  = client.execute(getRequest);

		System.out.println(response.getStatusLine() );

		Hooks.scenario.write("SSO Config Content"+ssoarray);

		Header[] headers = response.getAllHeaders();
		for (Header header : headers) {
			System.out.println("Key : " + header.getName() 
					+ " ,Value : " + header.getValue());
		}

		//get header by 'key'
		String server = response.getFirstHeader("Server").getValue();;

		BufferedReader reader = new BufferedReader(new
				InputStreamReader(response.getEntity().getContent()));

		String line = reader.readLine();
		String actual = null;
		while (line != null)
		{
			System.out.println(line);
			actual=line;
			line = reader.readLine();
		}

		Assert.assertTrue("alert message"+actual,!"saved successfully but unable to publish configuration!!!".equals(line)); 

	}

	private void ssosave(JSONObject ssoarray2) throws ClientProtocolException, IOException {

		String url = "https://"+ Validate.readsystemvariable("vcs.ip")+ ":"+Validate.readsystemvariable("vcs.port")+ SSOconfigPagefactory.rsurl_ssoupdate;
		Hooks.scenario.write(url);	 
		
		String value= SeleniumUtilities.getDriver().manage().getCookieNamed("CMC_SSO_COOKIE").toString();
		String CMC=value.substring(value.indexOf("=")+1,value.indexOf(";") );
		LogHandler.info("CMC_SSO_COOKIE"+":"+CMC);

		value= SeleniumUtilities.getDriver().manage().getCookieNamed("JSESSIONID").toString();
		String sess=value.substring(value.indexOf("=")+1,value.indexOf(";") );
		LogHandler.info("JSESSIONID"+":"+sess);

	
		HashMap<String, String> contentType =  new  HashMap<String, String>();
		contentType.put("Cookie", "slideMenuPinnedStr=false; JSESSIONID="+sess+"; username=root; SESSION_TIMEOUT=1800; ssoUserName=root; SSOActiveDomainId=8008; SSOAuthProvider=root; CMC_SSO_COOKIE="+CMC);
		contentType.put("content-type", "application/json; charset=UTF-8");
		
		
		Hooks.scenario.write("SSO Config Content"+ssoarray2.toJSONString());
		
		RestfulIFCommonFunctions rs= new RestfulIFCommonFunctions();
		HttpResponse response=rs.getRESTAPIResponse(url, "POST", ssoarray2.toJSONString(), "https", contentType);
				
		LogHandler.info("Response"+response.getStatusLine().toString());

	
		String actual = rs.readContent(response).trim();
		LogHandler.info("Response Body: " + actual);
		Assert.assertTrue("Actual alert message: "+actual + " But Expected alert Message"+SSOconfigPagefactory.ssosave_sucess1,SSOconfigPagefactory.ssosave_sucess1.equals(actual)); 

	}


	/**
	 * Gets the http client.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>When Frame a URL with "https" with below server details
	 * |server    |port        |context    |AUTHENTUCATEURL          |
	 * |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |</code>	
	 * </pre>
	 * @param httpType the http type
	 * @param serverport the serverport
	 * @return the http client
	 */
	public static HttpClient getHttpClient(String httpType,int serverport) {

		HttpClient base = new DefaultHttpClient();
		ClientConnectionManager ccm = null;

		try {
			SSLContext ctx = SSLContext.getInstance("TLSv1.1");
			X509TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme(httpType,ssf, serverport));
		} catch (Exception e) {
			System.out.println("Issue with rest call ");

		}		
		return new DefaultHttpClient(ccm, base.getParams());

	}



}
