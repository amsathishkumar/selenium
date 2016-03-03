/*
 * 
 */
package com.sat.dbds.vcs.ssoconfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.junit.Assert;

import com.cisco.dbds.utils.cucumber.Hooks;
import com.cisco.dbds.utils.logging.LogHandler;
import com.cisco.dbds.utils.restapi.RestfulIFCommonFunctions;
import com.cisco.dbds.utils.selenium.SeleniumUtilities;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import com.cisco.dbds.utils.ssh.SSHManager;
// TODO: Auto-generated Javadoc
//import com.cisco.dbds.vcs.common.set;
//import com.cisco.dbds.vcs.common.update;
import com.sat.dbds.vcs.common.CommonStepDef;



// TODO: Auto-generated Javadoc
/**
 * The Class ssoconfigServiceStepDef.
 */
public class ssoconfigServiceStepDef {

	/** The request url. */
	String requestURL;

	/** The response. */
	HttpResponse response;

	/** The rs. */
	//VCSrest rs1;
	RestfulIFCommonFunctions rs;

	/**  SSH Manager *. */
	SSHManager sshconnect;

	/** System variables*. */
	String oracledbip;

	/** The oraclesshusername. */
	String oraclesshusername;

	/** The oraclesshpassword. */
	String oraclesshpassword;
	/**
	 * Framea_url_with_with_below_server_details.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>When Frame a URL with "https" with below server details
		 |server    |port        |context    |AUTHENTUCATEURL          |
		 |<<vcs.ip>>|<<vcs.port>>|/vcsconsole|/rs/ssosrvc/authenticate |</code>	
	 * </pre>
	 * @param urltype the urltype
	 * @param serverdetails the serverdetails
	 */
	@Given("^Frame a URL with \"(.*?)\" with below server details$")
	public void frame_a_URL_with_with_below_server_details(String urltype, DataTable serverdetails){
		LogHandler.info("frame_a_URL_with_with_below_server_details(String urltype, DataTable serverdetails)");
		requestURL=urltype+"://";
		List<Map<String, String>> serverdetailsvalues = serverdetails.asMaps(String.class,String.class);
		for(String svrvalue:serverdetails.topCells())
		{
			String val=serverdetailsvalues.get(0).get(svrvalue);
			if (val.startsWith("<<") && val.endsWith(">>") )
			{
				String var=val.substring(2,val.length()-2);
				val=System.getProperty(var);
				if (var.contains("vcs.port"))
					val=":"+val;

			}
			requestURL=requestURL+val;

		}
		Hooks.scenario.write(requestURL);
		LogHandler.info("ULR"+requestURL);
	}



	/**
	 * Add_the_below_parameter_values_to_ url.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Add the below parameter values to URL
	 *|token             |appid|
	 *|<<CMC_SSO_COOKIE>>|CMC  | </code>	
	 * </pre>
	 * @param paramdetails the paramdetails
	 */
	@Given("^Add the below parameter values to URL$")
	public void add_the_below_parameter_values_to_URL(DataTable paramdetails) {
		LogHandler.info("add_the_below_parameter_values_to_URL(DataTable paramdetails)");
		List<Map<String, String>> paramdetailsvalues = paramdetails.asMaps(String.class,String.class);
		int count=0;
		for(String prmvalue:paramdetails.topCells())
		{
			String val=paramdetailsvalues.get(0).get(prmvalue);
			if (val.startsWith("<<") && val.endsWith(">>") )
			{
				String var=val.substring(2,val.length()-2);
				val=System.getProperty(var);	
			}
			if (count==0)
				val="?"+prmvalue+"="+val;
			else
				val="&"+prmvalue+"="+val;
			count++;
			requestURL=requestURL+val;
		}
		Hooks.scenario.write(requestURL);
		LogHandler.info("ULR with Parameter"+requestURL);
	}


	/**
	 * Make_a_rest_call_with_basic_authentication.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>	And Make a rest call with basic authentication
	 *|Username|Password|
	 *|ssoadmin|dv3group|</code>	
	 * </pre>
	 * @param authDetails the auth details
	 */
	@Given("^Make a rest call with basic authentication$")
	public void make_a_rest_call_with_basic_authentication(DataTable authDetails) {
		LogHandler.info("make_a_rest_call_with_basic_authentication(DataTable authDetails)");
		List<Map<String, String>> authDetailsvalue = authDetails.asMaps(String.class,String.class);

		String url1=requestURL.substring(0, requestURL.indexOf(":"));
		String username =authDetailsvalue.get(0).get("Username");
		String password =authDetailsvalue.get(0).get("Password");				
		//rs= new VCSrest();
		rs= new RestfulIFCommonFunctions();
		HashMap<String, String> contentType =  new  HashMap<String, String>();
		contentType.put("Content-type","text/plain");
		contentType.put("Accept","text/plain");

		String AuthCred="basicusrpwd/"+username+"/"+password;
		response= rs.getRESTAPIResponse(requestURL, "GET", " ", url1, contentType,AuthCred);


		//response= rs.getRESTAPIResponse(requestURL, "GET", " ", url1, "text/plain",6605, username,password);


	}


	/**
	 * Verify_response_ status_code_as.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code> Then Verify response Status code as "200"</code>	
	 * </pre>
	 * @param expectedstatuscode the expectedstatuscode
	 */
	@When("^Verify response Status code as \"(.*?)\"$")
	public void verify_response_Status_code_as(String expectedstatuscode) {	
		LogHandler.info("verify_response_Status_code_as(String expectedstatuscode)");
		String actualstatuscode=Integer.toString(rs.readStatusCode(response));
		LogHandler.info("Actual Repsonse status code"+actualstatuscode+" Expected status code:"+expectedstatuscode);
		Assert.assertTrue("Actual Repsonse status code"+actualstatuscode+" Expected status code:"+expectedstatuscode,expectedstatuscode.equals(actualstatuscode));
	}



	/**
	 * Verify_response_ message_as.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code> And Verify response Message as "USER_ID=root"	</code>	
	 * </pre>
	 * @param expectedmessage the expectedmessage
	 */
	@When("^Verify response Message as \"(.*?)\"$")
	public void verify_response_Message_as(String expectedmessage){
		LogHandler.info("verify_response_Message_as(String expectedmessage)");
		String actualmessage = null;
		try {
			actualmessage = rs.readContent(response);
			if (expectedmessage.equals("ROLES=Root"))
				expectedmessage=CommonStepDef.frame_response_for_role(expectedmessage);
		} catch (IllegalStateException | IOException e) {
			LogHandler.info("No Actual Repsonse message");
			Assert.assertTrue("No Actual Repsonse message",false);
		}
		if (expectedmessage.equals("CMC_SSO_COOKIE"))
		{
			String cookie=System.getProperty("CMC_SSO_COOKIE");
			String value = "TOKEN_ID=";
			value = value.concat(cookie);
			LogHandler.info("Actual Repsonse message"+actualmessage+" Expected status code:"+value + value.equals(actualmessage));
			Assert.assertTrue("Actual Repsonse message"+actualmessage+" Expected status code:"+value,value.equals(actualmessage));
		}
		else
		{
			LogHandler.info("Actual Repsonse message"+actualmessage+" Expected status code:"+expectedmessage + expectedmessage.equals(actualmessage));
			Assert.assertTrue("Actual Repsonse message"+actualmessage+" Expected status code:"+expectedmessage,expectedmessage.equals(actualmessage));
		}
	}

	/**
	 * Read_the_cookie_variable.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Read the cookie variable "CMC_SSO_COOKIE"</code>	
	 * </pre>
	 * @param cookiename the cookiename
	 */
	@Given("^Read the cookie variable \"(.*?)\"$")
	public void read_the_cookie_variable(String cookiename) {
		LogHandler.info("read_the_cookie_variable(String cookiename)");
		//String value= SeleniumUtilities.getDriver().manage().getCookieNamed(cookiename).toString();
		String value= SeleniumUtilities.readBrowserCookies(cookiename);
		value=value.substring(value.indexOf("=")+1,value.indexOf(";") );
		LogHandler.info(cookiename+":"+value);
		System.setProperty(cookiename, value);
	}

	/**
	 * Stop_oracle_db.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Stop oracle db and verify message</code>	
	 * </pre>
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Given("^Stop oracle db and verify message$")
	public void stop_oracle_db() {
		LogHandler.info("stop oracle db");
		oracledbip = System.getProperty("oracle.db.ip");
		oraclesshusername = System.getProperty("oracle.ssh.username");
		oraclesshpassword = System.getProperty("oracle.ssh.password");

		sshconnect = new SSHManager();
		try
		{
			sshconnect.createSSHConnection("stopdb", oracledbip, oraclesshusername, oraclesshpassword, "bash");
			sshconnect.executeCommand("stopdb","su - oracle");
			sshconnect.executeCommand("stopdb","source oracleenv");
			sshconnect.executeCommand("stopdb","sqlplus / as sysdba");
			String message = sshconnect.executeCommand("stopdb","shutdown immediate", 10);
			if (!(message.contains("Database closed.")))
				Assert.assertTrue("Database failed to close", false);

			SSHManager.teardownConnection("stopdb");
		}
		catch (Exception e)
		{
			Assert.assertTrue("Exception in establishing ssh connection", false);
		}
	}

	/**
	 * Start_oracle_db.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Start oracle db and verify message</code>
	 * </pre>
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Given("^Start oracle db and verify message$")
	public void start_oracle_db()  {
		LogHandler.info("start oracle db");
		oracledbip = System.getProperty("oracle.db.ip");
		oraclesshusername = System.getProperty("oracle.ssh.username");
		oraclesshpassword = System.getProperty("oracle.ssh.password");

		sshconnect = new SSHManager();

		try
		{
			sshconnect.createSSHConnection("startdb", oracledbip, oraclesshusername, oraclesshpassword, "bash");
			sshconnect.executeCommand("startdb","su - oracle");
			sshconnect.executeCommand("startdb","source oracleenv");
			sshconnect.executeCommand("startdb","sqlplus / as sysdba");
			String message = sshconnect.executeCommand("startdb","startup", 10);
			if (!(message.contains("Database opened.")))
				Assert.assertTrue("Database failed to open", false);
			SSHManager.teardownConnection("startdb");
		}
		catch (Exception e)
		{
			Assert.assertTrue("Exception in establishing ssh connection", false);
		}



	}

	/**
	 * Restart_vcs_console.
	 * <pre>
	 *<b>Gherkin</b>
	 *    <code>And Restart vcsconsole</code>	
	 * </pre>
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Given("^Restart vcsconsole$")
	public void restart_vcs_console() {
		LogHandler.info("Restarting vcs console");
		String vcsip = System.getProperty("vcs.ip");
		String sshusername = System.getProperty("vcs.ssh.username");
		String sshpassword = System.getProperty("vcs.ssh.password");

		sshconnect = new SSHManager();

		try
		{
			sshconnect.createSSHConnection("restartvcs", vcsip, sshusername, sshpassword, "bash");
			String message = sshconnect.executeCommand("restartvcs","service vcsconsole stop",10);
		//	if (!(message.contains("Stopping vcsconsole: stopped")))
				if (!(message.contains("Stopping nds_vcsconsole: stopped")))
						Assert.assertTrue("VCS console failed to stop", false);

			String message1 = sshconnect.executeCommand("restartvcs","service vcsconsole start",10);
			if (!(message1.contains("Starting nds_vcsconsole: ")))
				Assert.assertTrue("VCS console failed to start", false);


			SSHManager.teardownConnection("restartvcs");
		}
		catch (Exception e)
		{
			Assert.assertTrue("Exception in establishing ssh connection", false);
		}

	}

	@Given("^test1234$")
	public void test123(DataTable tabs){
		List<Map<String, String>> widgetlst = tabs.asMaps(String.class,
				String.class);
		for (Map<String, String> widgetvalue : widgetlst)		
		{
			String tname=widgetvalue.get("UserRoleAAA_TABS");
			
			System.out.println(tname);
			System.out.println(widgetvalue.get("Status"));
			/*
			switch (tname.toLowerCase()) {
			case "my password":
				verify_mypassword_tabstatus(widgetvalue.get("Status"));				
				break;
			case "user and accounts":
				verify_userandaccounts_tabstatus(widgetvalue.get("Status"));
				break;
			default:
				LogHandler.warn( "No Tab named: "+tname);
				Assert.assertTrue("No Tab named: "+tname,false);
				break;*/
			}


		}
	//	try {
		/*
		System.out.println(cmessage);
		
		String ccmessage = cmessage;
		System.out.println("hi");
		System.out.println(ccmessage);
		String actualmessage = "The user password is not valid, Cause Message:The password cannot contain 'cisco' or 'ocsic', or any variant obtained by changing the capitalization of letters therein or by substituting '1', '|', or '!' for i, '0' for 'o', or '$' for 's'.The password must contain characters from at least three of the following classes:lowercase letters, uppercase letters, digits and special characters.";
				//	String actualmessage = commonpf.WE_ALERT_CRITICAL_MESSAGE.getText();
	//	String ccmessage = "Changing the task permissions for the SuperUsers UserGroup is not recommended.%Are you sure you wish to continue\\?";	
		String[] msgs = ccmessage.split("%");
		if (msgs.length == 1)
		{
			System.out.println("vantomla");
			if ((actualmessage.equals(ccmessage)))
				System.out.println("vanromla");
			actualmessage = actualmessage.replaceAll(ccmessage, "");
			actualmessage = actualmessage.trim();
		System.out.println(actualmessage+"vantom1");
		System.out.println(ccmessage+"vantom1");
		
		if (actualmessage.isEmpty())
			System.out.println("hi\n");
		}
		else
		{
			for (String msg :msgs)
			{
				System.out.println("msgaam"+msg);
				System.out.println(actualmessage+"vantom");
				if (msg.contains("|"))
				{
				System.out.print(actualmessage.indexOf("|"));
				System.out.print(msg.indexOf("|"));
					if (!(actualmessage.indexOf("|") == msg.indexOf("|")))
							{
							
							Assert.assertTrue("Actual message index of ? does not match with expected message index", false);
							}
					msg = msg.substring(0, msg.indexOf("|")) + msg.subSequence(msg.indexOf("|") + 1, msg.length());
					actualmessage = actualmessage.substring(0, actualmessage.indexOf("|")) + actualmessage.subSequence(actualmessage.indexOf("|") + 1, actualmessage.length());
					System.out.println("msgaaaam"+msg);
					System.out.println(actualmessage+"vantommm");
				}
				if (msg.contains("$"))
				{
				System.out.print(actualmessage.indexOf("$"));
				System.out.print(msg.indexOf("$"));
					if (!(actualmessage.indexOf("$") == msg.indexOf("$")))
							{
							
							Assert.assertTrue("Actual message index of ? does not match with expected message index", false);
							}
					msg = msg.substring(0, msg.indexOf("$")) + msg.subSequence(msg.indexOf("$") + 1, msg.length());
					actualmessage = actualmessage.substring(0, actualmessage.indexOf("$")) + actualmessage.subSequence(actualmessage.indexOf("$") + 1, actualmessage.length());
					System.out.println("msgaaaam"+msg);
					System.out.println(actualmessage+"vantommm");
				}
				
			actualmessage = actualmessage.replaceAll(msg, "");
				actualmessage = actualmessage.trim();
			System.out.println(actualmessage+"vantom1");
			}
			if (actualmessage.isEmpty())
				System.out.println("hi\n");
		}
			/*
		String dbip= System.getProperty("oracle.db.ip");
		String dbsid= System.getProperty("oracle.db.scid");
		String dbusr=System.getProperty("oracle.db.username");
		String dbpswd=System.getProperty("oracle.db.password");
		System.out.println(dbip+ dbsid+ dbusr+ dbpswd);*/
		
//	}
}

