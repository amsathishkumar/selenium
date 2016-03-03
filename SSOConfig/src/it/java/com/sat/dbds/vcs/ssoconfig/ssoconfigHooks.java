/*
 * 
 */
package com.sat.dbds.vcs.ssoconfig;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.cisco.dbds.utils.configfilehandler.ConfigFileHandler;
import com.cisco.dbds.utils.logging.LogHandler;
import com.cisco.dbds.utils.selenium.SeleniumUtilities;
import com.cisco.dbds.utils.ssh.SSHManager;
import com.cisco.dbds.vcs.login.LoginStepDef;
import com.sat.dbds.vcs.common.CommonStepDef;
import com.sat.dbds.vcs.menu.MenuStepDef;
import com.sat.dbds.vcs.ssoconfig.ssoconfigServiceStepDef;
import com.sat.dbds.vcs.ssoconfig.ssoconfigStepDef;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

// TODO: Auto-generated Javadoc
/**
 * The Class ssoconfigHooks.
 */
public class ssoconfigHooks {

	/** The scenario. @Before(order = 1)*/
	public static Scenario scenario;

	/** The driver. */
	public static WebDriver driver;

	/** The ssoui. */
	ssoconfigStepDef ssoui;

	/** The menu. */
	MenuStepDef menu;

	/** The login. */
	LoginStepDef login;

	/** The common. */
	CommonStepDef common;

	/**
	 * Pre_hook_for_dbstop.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Before(value = "@Ttv9629542c", order = 2)
	public void pre_hook_for_dbstop() {
		// Browser Initializations
		LogHandler.info("pre_hook_for_dbstop..");
		ssoconfigServiceStepDef ssoservice = new ssoconfigServiceStepDef() ;
		ssoservice.stop_oracle_db();


	}

	/**
	 * Pre_hook_for_delete_from_db.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 * @throws InterruptedException the interrupted exception
	 */
	@Before(value = "@Ttv9629602c", order = 2)
	public void pre_hook_for_delete_from_db() {
		// Browser Initializations
		LogHandler.info("pre_hook_for_delete_from_db");
		common = new CommonStepDef();
		ssoconfigServiceStepDef ssoservice = new ssoconfigServiceStepDef();
		common.delete_entries_from_table("SSO_CONFIG");
		common.delete_entries_from_table("SSO_TOKEN");
		ssoservice.restart_vcs_console();
		common.wui_wait_for_seconds("60");

	}

	/**
	 * Post_hook_for_dbstart.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@After(value = "@Ttv9629586c,@Ttv9629606c,@Ttv9629607c", order = 2)
	public void post_hook_for_dbstart() {

		LogHandler.info("post_hook_for_dbstart..");
		ssoconfigServiceStepDef ssoservice = new ssoconfigServiceStepDef() ;
		ssoservice.start_oracle_db();

	}

	/**
	 * Post_hook_for_sso_enable.
	 *
	 * @throws Exception the exception
	 */
	@After(value = "@Ttv9629594c,@Ttv9629541c,@Ttv9629551c,@Ttv9629561c,@Ttv9629564c,@Ttv9629582c,@Ttv9629622c", order = 100)
	public void post_hook_for_sso_enable() throws Exception{

		LogHandler.info("post_hook_for_sso_enable order 100..");

		ssoui = new ssoconfigStepDef();
		menu = new MenuStepDef();
		common = new CommonStepDef();

		menu.click_on_the_Toggle_menu_icon();
		menu.Click_on_sub_menu_option("SSO Configuration");
		common.wui_wait_for_seconds("1");
		ssoui.enable_ssoconfig_enable();
		ssoui.click_on_ssoconfig_button("Save");
		common.wui_wait_for_seconds("2");

	}

	/**
	 * Post_hook_for_sso_enable_from_ne.
	 *
	 * @throws Exception the exception
	 */
	@After(value = "@Ttv9629585c", order = 100)
	public void post_hook_for_sso_enable_from_ne() throws Exception{

		LogHandler.info("post_hook_for_sso_enable_from_ne order 100..");

		ssoui = new ssoconfigStepDef();
		menu = new MenuStepDef();
		common = new CommonStepDef();

		menu.click_on_the_Toggle_menu_icon();
		menu.Click_on_main_menu_option("Console Admin");
		menu.Click_on_sub_menu_option("SSO Configuration");
		common.wui_wait_for_seconds("1");
		ssoui.enable_ssoconfig_enable();
		ssoui.click_on_ssoconfig_button("Save");
		common.wui_wait_for_seconds("2");

	}




}
