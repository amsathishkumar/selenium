/**
 * Copyright (c) 2015 by SAT Systems, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of SAT Systems,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with SAT Systems.
 *
 *
 * @Project: Utils
 * @Author: maparame
 * @Version: 
 * @Description:  
 * @Date created: Jun 14, 2015
 */
package com.cisco.dbds.utils.primewidgets;


import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;


import com.cisco.dbds.utils.cucumber.Hooks;
import com.cisco.dbds.utils.db.DatabaseManager;
import com.cisco.dbds.utils.logging.LogHandler;
import com.cisco.dbds.utils.primewidgets.pagefactory.primePageFactory;
import com.cisco.dbds.utils.primewidgets.pageobject.primePageConstants;
import com.cisco.dbds.utils.selenium.Autowait;
import com.cisco.dbds.utils.selenium.Identifier;
import com.cisco.dbds.utils.selenium.SeleniumUtilities;
import com.cisco.dbds.utils.validation.Validate;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


/**
 * @author maparame
 *
 */
public class primewidgetsStepDef implements primePageConstants{

	private primePageFactory primewuipf;
	public DatabaseManager dbm;

	public primewidgetsStepDef() {

		primewuipf = PageFactory.initElements(SeleniumUtilities.getDriver(),primePageFactory.class);
	}

	@Given("^Verify presence of success toaster notification$")
	public void verify_presence_of_success_toaster_notification(){
		LogHandler.info("verify_presence_of_success_toaster_notification()");
		try
		{
			Assert.assertTrue("Success toaster notification not found", primewuipf.SUCCESS_TOASTER_NOTIFICATION_WE_XPATH.isDisplayed());
			LogHandler.info("Verified presence of success toaster notification");
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object SUCCESS_TOASTER_NOTIFICATION_WE_XPATH snot found on VCS Console", false);
		}
	}

	@Given("^Verify success toaster notification title matches \"(.*?)\"$")
	public void verify_success_toaster_notification_title(String title){
		LogHandler.info("verify_success_toaster_notification_title(String title)");
		try
		{
			Assert.assertTrue("Success toaster title mismatch as actual toaster title " +primewuipf.SUCCESS_TOASTER_TITLE_WE_XPATH.getText().trim()+ "is not equal to expected toaster title "+title, primewuipf.SUCCESS_TOASTER_TITLE_WE_XPATH.getText().trim().equals(title));
			LogHandler.info("Verified " +title+ " as success toaster title");
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object SUCCESS_TOASTER_TITLE_WE_XPATH not found on VCS Console", false);
		}
	}

	@Given("^Verify success toaster notification content matches \"(.*?)\"$")
	public void verify_success_toaster_notification_content(String body){
		LogHandler.info("verify_success_toaster_notification_content(String body)");
		try
		{
			Assert.assertTrue("Success toaster title mismatch as actual toaster title " +primewuipf.SUCCESS_TOASTER_BODY_WE_XPATH.getText().trim()+ "is not equal to expected toaster body "+body, primewuipf.SUCCESS_TOASTER_BODY_WE_XPATH.getText().trim().equals(body));
			LogHandler.info("Verified " +body+ " as success toaster content");
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object SUCCESS_TOASTER_BODY_WE_XPATH not found on VCS Console", false);
		}
	}
	
	@Given("^Close any open toasters$")
	public void close_any_open_toastesr(){
		LogHandler.info("close_any_open_toastesr()");
		try
		{
			primewuipf.CLOSE_TOASTER_WE_XPATH.click();
		}
		catch (ElementNotFoundException e)
		{
			LogHandler.info("No open toasters found");
		}
		catch (Exception e)
		{
			Assert.assertTrue("Page Object CLOSE_TOASTER_WE_XPATH not found on VCS Console", false);
		}
	}

	@Given("^Verify presence of warning toaster notification$")
	public void verify_presence_of_warning_toaster_notification(){
		LogHandler.info("verify_presence_of_warning_toaster_notification()");
		try
		{
			Assert.assertTrue("Warning toaster notification not found", primewuipf.WARNING_TOASTER_NOTIFICATION_WE_XPATH.isDisplayed());
			LogHandler.info("Verified presence of warning toaster notification");
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object WARNING_TOASTER_NOTIFICATION_WE_XPATH not found on VCS Console", false);
		}
	}

	@Given("^Verify warning toaster notification title matches \"(.*?)\"$")
	public void verify_warning_toaster_notification_title(String title){
		LogHandler.info("verify_warning_toaster_notification_title(String title)");
		try
		{
			Assert.assertTrue("Warning toaster title mismatch as actual toaster title " +primewuipf.WARNING_TOASTER_TITLE_WE_XPATH.getText().trim()+ "is not equal to expected toaster title "+title, primewuipf.WARNING_TOASTER_TITLE_WE_XPATH.getText().trim().equals(title));
			LogHandler.info("Verified " +title+ " as warning toaster title");
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object WARNING_TOASTER_TITLE_WE_XPATH not found on VCS Console", false);
		}
	}

	@Given("^Verify warning toaster notification content matches \"(.*?)\"$")
	public void verify_warning_toaster_notification_content(String body){
		LogHandler.info("verify_warning_toaster_notification_content(String body)");
		try
		{
			Assert.assertTrue("Warning toaster title mismatch as actual toaster body " +primewuipf.WARNING_TOASTER_BODY_WE_XPATH.getText().trim()+ "is not equal to expected toaster body "+body, primewuipf.WARNING_TOASTER_BODY_WE_XPATH.getText().trim().equals(body));
			LogHandler.info("Verified " +body+ " as warning toaster content");
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object WARNING_TOASTER_BODY_WE_XPATH not found on VCS Console", false);
		}
	}

	@Given("^Verify presence of critical alert notification$")
	public void verify_critical_alert_notification_presence(){
		LogHandler.info("verify_critical_alert_notification_presence()");
		try
		{
			String actual_alert = primewuipf.CRITICAL_ALERT_NOTIFICATION_WE_XPATH.getText();
			actual_alert = actual_alert.replaceAll("\\r|\\n", "");
			actual_alert = actual_alert.trim();
			Assert.assertTrue("Critical Alert Notification not found on VCS Console page", primewuipf.CRITICAL_ALERT_NOTIFICATION_WE_XPATH.isDisplayed());
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object CRITICAL_ALERT_NOTIFICATION_WE_XPATH not found on VCS Console", false);

		}	

	}

	@Given("^Verify presence of warning alert notification$")
	public void verify_critical_warning_notification_presence(){
		LogHandler.info("verify_warning_alert_notification_presence()");
		try
		{
			Assert.assertTrue("Warning Alert Notification not found on VCS Console page", primewuipf.WARNING_ALERT_NOTIFICATION_WE_XPATH.isDisplayed());
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object WARNING_ALERT_NOTIFICATION_WE_XPATH not found on VCS Console", false);

		}
	}
	@Given("^Verify presence of info alert notification$")
	public void verify_info_alert_notification_presence(){
		LogHandler.info("verify_info_alert_notification_presence()");
		try
		{
			Assert.assertTrue("Info Alert Notification not found on VCS Console page", primewuipf.INFO_ALERT_NOTIFICATION_WE_XPATH.isDisplayed());
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object INFO_ALERT_NOTIFICATION_WE_XPATH not found on VCS Console", false);

		}
	}


	@Given("^Verify critical alert notification content matches \"(.*?)\"$")
	public void verify_critical_alert_notification_content(String cmessage){
		LogHandler.info("verify_critical_alert_notification_content(String cmessage)");
		try
		{
			String actual_alert = primewuipf.CRITICAL_ALERT_NOTIFICATION_CONTENT_WE_XPATH.getText();
			actual_alert = actual_alert.replaceAll("\\r|\\n", "");
			actual_alert = actual_alert.trim();
			Assert.assertTrue("Critical Alert Notification Message mismatch as actual message "+actual_alert+"does not match expected message "+cmessage, actual_alert.equals(cmessage));
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object CRITICAL_ALERT_NOTIFICATION_CONTENT_WE_XPATH not found on VCS Console", false);

		}	

	}

	@Given("^Verify warning alert notification content matches \"(.*?)\"$")
	public void verify_warning_alert_notification_content(String wmessage){
		LogHandler.info("verify_warning_alert_notification_content(String wmessage)");
		try
		{
			String actual_alert = primewuipf.WARNING_ALERT_NOTIFICATION_CONTENT_WE_XPATH.getText();
			actual_alert = actual_alert.replaceAll("\\r|\\n", "");
			actual_alert = actual_alert.trim();
			Assert.assertTrue("Warning Alert Notification Message mismatch as actual message "+actual_alert+"does not match expected message "+wmessage, actual_alert.equals(wmessage));
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object WARNING_ALERT_NOTIFICATION_CONTENT_WE_XPATH not found on VCS Console", false);

		}	

	}

	@Given("^Verify info alert notification content matches \"(.*?)\"$")
	public void verify_info_alert_notification_content(String imessage){
		LogHandler.info("verify_info_alert_notification_content(String imessage)");
		try
		{
			String actual_alert = primewuipf.INFO_ALERT_NOTIFICATION_CONTENT_WE_XPATH.getText();
			actual_alert = actual_alert.replaceAll("\\r|\\n", "");
			actual_alert = actual_alert.trim();
			Assert.assertTrue("Info Alert Notification Message mismatch as actual message "+actual_alert+"does not match expected message "+imessage, actual_alert.equals(imessage));
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object INFO_ALERT_NOTIFICATION_CONTENT_WE_XPATH not found on VCS Console", false);

		}	

	}

	@Given("^Verify the alert notification has the buttons \"(.*?)\"$")
	public void verify_alert_notification_has_buttons(List<String> buttons){
		LogHandler.info("verify_alert_notification_has_buttons(List<String> buttons)");
		for (String button: buttons)
		{
			try
			{

				Assert.assertTrue("Button " +button+ "not found in alert notification", SeleniumUtilities.findElement(Identifier.XPATH, String.format(ALERT_NOTIFICATION_BUTTON, button)).isDisplayed());
			}
			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Assert.assertTrue("Page Object ALERT_NOTIFICATION_BUTTON for button " +button.toString()+" not found on VCS Console", false);

			}
		}

	}

	@Given("^Click the button \"(.*?)\" on alert notification$")
	public void click_button_on_alert_notification(String button){
		LogHandler.info("click_button_on_alert_notification()");
		try
		{
			SeleniumUtilities.findElement(Identifier.XPATH, String.format(ALERT_NOTIFICATION_BUTTON, button)).click();
		}
		catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			Assert.assertTrue("Page Object ALERT_NOTIFICATION_BUTTON not found on VCS Console", false);

		}
	}

	@Given("^Hover over the tooltip icon and verify tooltip content$")
	public void hover_over_tooltip_icon_and_verify_content(DataTable table){
		for (String label : table.topCells())
		{
			try
			{
				List<Map<String, String>> map = table
						.asMaps(String.class, String.class);
				try{
					Actions action = new Actions(SeleniumUtilities.getDriver());
					action.moveToElement(SeleniumUtilities.findElement(Identifier.XPATH, String.format(IMAGE_ICON, label))).perform();
				}
				catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					Assert.assertTrue("Page Object not found on VCS Console" ,false);
				}
				SeleniumUtilities.wait(2);
				String actual_text =primewuipf.TOOLTIP_RIGHT_WE_XPATH.getText().trim();
				actual_text = actual_text.replaceAll("\\r|\\n", "");
				actual_text = actual_text.trim();
				//System.out.println(actual_text);
				Assert.assertTrue("Actual tooltip "+actual_text+"does not match with expected tooltip "+map.get(0).get(label), actual_text.equals(map.get(0).get(label)));
				SeleniumUtilities.wait(4);
			}
			catch (WebDriverException e)
			{
				Assert.assertTrue("Page Object TOOLTIP_RIGHT_WE_XPATH not found on VCS Console", false);
			}

		}
	}
	
	@Given("^Hover over the tooltip icon and verify tooltip content \"(.*?)\" for field \"(.*?)\"$")
	public void hover_over_tooltip_icon_and_verify_content(String tooltip, String label){
		//for (String label : table.topCells())
		//{
			try
			{
			//	List<Map<String, String>> map = table
			//			.asMaps(String.class, String.class);
				try{
					Actions action = new Actions(SeleniumUtilities.getDriver());
					action.moveToElement(SeleniumUtilities.findElement(Identifier.XPATH, String.format(IMAGE_ICON, label))).perform();
				}
				catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					Assert.assertTrue("Page Object not found on VCS Console" ,false);
				}
				SeleniumUtilities.wait(2);
				String actual_text =primewuipf.TOOLTIP_RIGHT_WE_XPATH.getText().trim();
				actual_text = actual_text.replaceAll("\\r|\\n", "");
				actual_text = actual_text.trim();
				//System.out.println(actual_text);
				Assert.assertTrue("Actual tooltip "+actual_text+"does not match with expected tooltip "+tooltip, actual_text.equals(tooltip));
				SeleniumUtilities.wait(4);
			}
			catch (WebDriverException e)
			{
				Assert.assertTrue("Page Object TOOLTIP_RIGHT_WE_XPATH not found on VCS Console", false);
			}

//		}
	}

	@Given("^Validate tooltip content \"(.*?)\" on entering invalid data$")
	public void validate_tooltip_content_on_entering_invalid_data(String exp_tooltip){
		LogHandler.info("validate_tooltip_content_on_entering_invalid_data(String exp_tooltip)");
		try
		{
			String actual_text =primewuipf.TOOLTIP_ABOVE_WE_XPATH.getText().trim();
			actual_text = actual_text.replaceAll("\\r|\\n", "");
			actual_text = actual_text.trim();	
			Assert.assertTrue("Actual tooltip "+actual_text+"does not match with expected tooltip "+exp_tooltip, actual_text.equals(exp_tooltip));
			SeleniumUtilities.wait(4);
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object TOOLTIP_ABOVE_WE_XPATH not found on VCS Console", false);
		}
	}



	@Given("^Hover over popover icon and verify PopOver title and content \"(.*?)\"$")
	public void hover_over_popover_icon_and_verify_popover_title_content(String exp_popovercontent, DataTable table){
		LogHandler.info("hover_over_popover_icon_and_verify_popover_title_content(DataTable table)");
		List<Map<String, String>> widgetlst = table.asMaps(String.class,
				String.class);
		String popover_content;
		for (Map<String, String> widgetvalue : widgetlst)		
		{
			try
			{
				try{
					Actions action = new Actions(SeleniumUtilities.getDriver());
					action.moveToElement(SeleniumUtilities.findElement(Identifier.XPATH, String.format(IMAGE_ICON, widgetvalue.get("Field")))).perform();
				}
				catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					Assert.assertTrue("Page Object not found on VCS Console" ,false);
				}

				Assert.assertTrue("Actual popover title " +primewuipf.POPOVER_TITLE_WE_XPATH.getText().trim()+ "does not match expected popover title "+widgetvalue.get("PopOverTitle"), primewuipf.POPOVER_TITLE_WE_XPATH.getText().trim().equals(widgetvalue.get("PopOverTitle")) );
				LogHandler.info("Verified popover title " +widgetvalue.get("PopOverTitle")+"for field" +widgetvalue.get("Field"));
				popover_content =primewuipf.POPOVER_CONTENT_WE_XPATH.getText().trim();
				popover_content = popover_content.replaceAll("\\r|\\n", "");
				popover_content = popover_content.trim();
				Assert.assertTrue("Actual popover content " +popover_content+ "does not match expected popover content "+exp_popovercontent, popover_content.equals(exp_popovercontent));

			}

			catch (WebDriverException e)
			{

				Assert.assertTrue("Page Object POPOVER_TITLE_WE_XPATH not found on VCS Console", false);
			}
		}
	}
	/*3 columns in table
	 * |Field|PopOverTitle|PopOverContent|
	 * |Old Password|Password Policy|Policy|
	 * */
	@Given("^Hover over popover icon and verify PopOver title and content$")
	public void hover_over_popover_icon_and_verify_popover_title_content(DataTable table){
		LogHandler.info("hover_over_popover_icon_and_verify_popover_title_content(DataTable table)");
		List<Map<String, String>> widgetlst = table.asMaps(String.class,
				String.class);
		String popover_content;
		for (Map<String, String> widgetvalue : widgetlst)		
		{
			try
			{
				try{
					Actions action = new Actions(SeleniumUtilities.getDriver());
					action.moveToElement(SeleniumUtilities.findElement(Identifier.XPATH, String.format(IMAGE_ICON, widgetvalue.get("Field")))).perform();
				}
				catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					Assert.assertTrue("Page Object not found on VCS Console" ,false);
				}

				Assert.assertTrue("Actual popover title " +primewuipf.POPOVER_TITLE_WE_XPATH.getText().trim()+ "does not match expected popover title "+widgetvalue.get("PopOverTitle"), primewuipf.POPOVER_TITLE_WE_XPATH.getText().trim().equals(widgetvalue.get("PopOverTitle")) );
				LogHandler.info("Verified popover title " +widgetvalue.get("PopOverTitle")+"for field" +widgetvalue.get("Field"));
				popover_content =primewuipf.POPOVER_CONTENT_WE_XPATH.getText().trim();
				popover_content = popover_content.replaceAll("\\r|\\n", "");
				popover_content = popover_content.trim();
				Assert.assertTrue("Actual popover content " +popover_content+ "does not match expected popover content "+widgetvalue.get("PopOverContent"), popover_content.equals(widgetvalue.get("PopOverContent")));
				LogHandler.info("Verified popover content " +widgetvalue.get("PopOverContent")+"for field" +widgetvalue.get("Field"));
			}

			catch (WebDriverException e)
			{

				Assert.assertTrue("Page Object POPOVER_TITLE_WE_XPATH not found on VCS Console", false);
			}
		}
	}

	@Given("^Click \"(.*?)\" button from Global Toolbar$")
	public void click_button_from_global_toolbar(String button){
		LogHandler.info("click_button_from_global_toolbar()");
		if (button.equals("Settings")) {
			primewuipf.SETTINGS_BUTTON_WE_XPATH.click();
		} else if (button.equals("Refresh")) {
			primewuipf.REFRESH_BUTTON_WE_XPATH.click();
		}
	}
	@Given("^Verify presence of \"(.*?)\" buttons from Global Toolbar$")
	public void verify_presence_of_buttons_from_global_toolbar(List<String> buttons){
		LogHandler.info("verify_button_presence_from_global_toolbar()");
		for(String button: buttons)
		{
		if (button.equals("Settings")) {
			Assert.assertTrue("Settings button presence could not be validated",primewuipf.SETTINGS_BUTTON_WE_XPATH.isDisplayed());
		} else if (button.equals("Refresh")) {
			Assert.assertTrue("Refresh button presence could not be validated",primewuipf.REFRESH_BUTTON_WE_XPATH.isDisplayed());
		}
		}
	}

	@Given("^Verify Settings dropdown has mentioned operations$")
	public void verify_settings_dropdown_has_mentioned_operations(DataTable table){
		LogHandler.info("verify_settings_dropdown_has_mentioned_columns(DataTable table");
		List<String> columns = table.asList(String.class);
		for (String colName : columns) {
			Assert.assertTrue("The listed column option is unavaiable: " + colName,
					SeleniumUtilities.isElementPresent(By.xpath(
							String.format(SETTINGS_OPTIONS,
									colName)
							)));
		}

	}
	@Given("^Select \"(.*?)\" operation from Settings button Dropdown$")
	public void select_option_from_settings_button_dropdown(String option){
		LogHandler.info("select_option_from_settings_button_dropdown(String option)");
		try
		{
			SeleniumUtilities.findElement(Identifier.XPATH,String.format(SETTINGS_OPTIONS,option)).click();
		}
		catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			Assert.assertTrue("Page Object SETTINGS_OPTIONS not found on VCS Console", false);
		}

	}

	@Given("^Click \"(.*?)\" button from the Settings Columns dropdown on Console$")
	public void click_widget_from_settings_columns_dropdown(String widget){
		LogHandler.info("click_widget_from_settings_columns_dropdown(String widget)");
		try
		{
			if (widget.equals("Close"))
				primewuipf.SETTINGS_CLOSE_BUTTON_WE_XPATH.click();
			if (widget.equals("Reset"))
				primewuipf.SETTINGS_RESET_BUTTON_WE_XPATH.click();
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page Object SETTINGS_CLOSE_BUTTON_WE_XPATH/SETTINGS_RESET_BUTTON_WE_XPATH not found on VCS Console", false);
		}
	}

	@Given("^Tick mentioned columns from Settings option$")
	public void filter_mentioned_columns_from_settings_dropdown(DataTable table){
		LogHandler.info("filter_options_from_settings_columns_dropdown(DataTable table)");
		List<String> columns = table.asList(String.class);
		for (String option : columns) {
			try
			{
				if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(SETTINGS_COLUMNS_OPTION_STATUS,option)).getAttribute("aria-checked").equals("false"))
				{
					SeleniumUtilities.findElement(
							Identifier.XPATH,
							String.format(
									SETTINGS_COLUMNS_OPTIONS,
									option,option)).click();
					if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(SETTINGS_COLUMNS_OPTION_STATUS,option)).getAttribute("aria-checked").equals("false"))
						Assert.assertTrue("Tick mark not present for selected column " +option, false); 
				}
			}

			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Assert.assertTrue("Page Object SETTINGS_COLUMNS_OPTIONS not found on VCS Console", false);
			}
		}

	}	

	@Given("^Untick mentioned columns from Settings option$")
	public void unfilter_mentioned_columns_from_settings_dropdown(DataTable table){
		LogHandler.info("unfilter_options_from_settings_columns_dropdown(DataTable table)");
		List<String> columns = table.asList(String.class);
		for (String option : columns) {
			try
			{
				if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(SETTINGS_COLUMNS_OPTION_STATUS,option)).getAttribute("aria-checked").equals("true"))
				{
					SeleniumUtilities.findElement(
							Identifier.XPATH,
							String.format(
									SETTINGS_COLUMNS_OPTIONS,
									option,option)).click();
					if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(SETTINGS_COLUMNS_OPTION_STATUS,option)).getAttribute("aria-checked").equals("true"))
						Assert.assertTrue("Tick mark still present against column name for untick operation"+ option, false);
				}
			}

			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Assert.assertTrue("Page Object SETTINGS_COLUMNS_OPTIONS not found on VCS Console", false);
			}
		}

	}

	@Then("^Select \"(.*?)\" from Fix Row Settings dropdown$")
	public void select_option_from_fix_row_settings(String option) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		LogHandler.info("select_option_from_fix_row_settings(String option)");
		try
		{
			SeleniumUtilities.findElement(Identifier.XPATH, String.format(FIX_TO_TOP_OR_BOTTOM, option)).click();
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page object mimsatch for FIX_TO_TOP_OR_BOTTOM " + option, false);
		}
	}

	@Then("^Verify row \"(.*?)\" is locked at \"(.*?)\" and same row present in Console grid$")
	public void verify_row_locked_at_bottom_or_top(List<String> rows, String position) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		LogHandler.info("verify_row_locked_at_bottom_or_top(List<String> rows, String position)");
		try
		{

			if(position.equals("bottom"))
			{
				int count = 0;
				for(String row:rows)
				{
					if (count == 0)
						((JavascriptExecutor) SeleniumUtilities.getDriver()).executeScript("arguments[0].scrollIntoView(true);", SeleniumUtilities.findElement(Identifier.XPATH, String.format(ROW_LOCKED_AT_BOTTOM, row)));
					if (!(SeleniumUtilities.findElement(Identifier.XPATH, String.format(ROW_LOCKED_AT_BOTTOM, row)).isDisplayed()))
						Assert.assertTrue("Row is not locked at bottom", false);
					if (!(SeleniumUtilities.findElement(Identifier.XPATH, String.format(ROW_DUPLICATE_LOCKED, row)).isDisplayed()))
						Assert.assertTrue("Duplicate row not found in grid", false);
					count++;
				}
			}
			else if (position.equals("top"))
			{
				for(String row:rows)
				{
					if (!SeleniumUtilities.findElement(Identifier.XPATH, String.format(ROW_LOCKED_AT_TOP, row)).isDisplayed())
						Assert.assertTrue("Row is not locked at top", false);
					if (!SeleniumUtilities.findElement(Identifier.XPATH, String.format(ROW_DUPLICATE_LOCKED, row)).isDisplayed())
						Assert.assertTrue("Duplicate row not found in grid", false);
				}
			}


		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page object mimsatch for ROW_LOCKED_XPATH at "+position, false);
		}

	}

	@Then("^Select checkboxes corresponding to locked row \"(.*?)\" at \"(.*?)\"$")
	public void click_checkboxes_for_locked_rows_at_top(List<String> rows,String position) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		LogHandler.info("click_checkboxes_for_locked_rows_at_top(List<String> rows,String position)");
		try
		{
			if (position.equals("top"))
			{				
				for(String row: rows)
				{
					List <WebElement> wes = new ArrayList<WebElement>();	     		
					wes = SeleniumUtilities.findElements(Identifier.XPATH, String.format(ROW_TOPLOCKED_CHECKBOX, row));
					for (WebElement we: wes)
					{
						System.out.println("hi");
						we.click();
					}
				}
			}	
			else
				if (position.equals("bottom"))
				{
					for (String row: rows){
						List <WebElement> wes = new ArrayList<WebElement>();		     			
						wes = SeleniumUtilities.findElements(Identifier.XPATH, String.format(ROW_BOTTOMLOCKED_CHECKBOX, row));
						for (WebElement we: wes)
						{
							System.out.println("hi");
							we.click();
						}
					}				
				}
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page object mimsatch for ROW_LOCKED_CHECKBOX at " +position, false);
		}

	}
	
	@Then("^Verify row \"(.*?)\" is unlocked on console$")
	public void verify_row_is_unlocked(List<String> rows) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		LogHandler.info("verify_row_is_unlocked(List<String> rows)");
		try
		{
		
				for(String row:rows)
				{
					if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(ROW_LOCKED_AT_BOTTOM, row)).isDisplayed())
						Assert.assertTrue("Row is not unlocked at bottom", false);
					if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(ROW_DUPLICATE_LOCKED, row)).isDisplayed())
						Assert.assertTrue("Duplicate row found in grid", false);
				}
		}
		catch (NoSuchElementException e)
		{
			LogHandler.info("Row is unlocked");
			//"Page object mimsatch for ROW_LOCKED_XPATH at ", false);
		}
		catch (Exception e)
		{
			Assert.assertTrue("Page object mimsatch for ROW_LOCKED_XPATH at ", false);
		}
		
	}

	@Given("^Verify column is displayed on console table$")
	public void verify_column_displayed_on_console_table(DataTable table){
		LogHandler.info("verify_column_displayed_on_console_table(DataTable table)");
		List <String> columns = table.asList(String.class);
		for (String column:columns)
		{
			try
			{
				Assert.assertTrue("Column "+column+"not found in table", SeleniumUtilities.findElement(Identifier.XPATH, String.format(COLUMN_NAME, column)).isDisplayed());
				LogHandler.info("Verified presence of column "+column);
			}
			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Assert.assertTrue("Page object mimsatch for COLUMN_NAME " +column, false);
			}
		}

	}



	@Given("^Verify column is not displayed on console table$")
	public void verify_column_not_displayed_on_console_table(DataTable table){
		LogHandler.info("verify_column_not_displayed_on_console_table(DataTable table)");
		List <String> columns = table.asList(String.class);
		for (String column:columns)
		{
			try
			{
				Assert.assertFalse("Column "+column+"not found in table", SeleniumUtilities.findElement(Identifier.XPATH, String.format(COLUMN_NAME, column)).isDisplayed());

			}
			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				LogHandler.info("Verified non-presence of column "+column);
			}
		}

	}
/*check*/
	@Given("^Verify panel titles \"(.*?)\" are displayed$")
	public void verify_panel_title(List<String> titles){
		LogHandler.info("verify_panel_title(String title)");
		//List<String> titles = table.asList(String.class);
		for (String title:titles)
		{
			try
			{
				Assert.assertTrue("Panel title "+title+"not found in page", SeleniumUtilities.findElement(Identifier.XPATH, String.format(PANEL_TITLE, title)).isDisplayed());
				LogHandler.info("Verified presence of title "+title);
			}
			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Assert.assertTrue("Page object mimsatch for PANEL_TITLE " +title, false);
			}
		}

	}
	
	@Given("^Verify column headers \"(.*?)\" are displayed$")
	public void verify_column_headers(List<String> headers){
		LogHandler.info("verify_column_headers(List<String> headers)");
		//List<String> titles = table.asList(String.class);
		for (String title:headers)
		{
			try
			{
				Assert.assertTrue("Column header "+title+"not found in table", SeleniumUtilities.findElement(Identifier.XPATH, String.format(COLUMN_NAME, title)).isDisplayed());
				LogHandler.info("Verified presence of column header "+title);
			}
			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Assert.assertTrue("Page object mimsatch for COLUMN_NAME " +title, false);
			}
		}

	}

	@Given("^Verify mentioned tabs \"(.*?)\" are displayed$")
	public void verify_mentioned_tabs_are_displayed(List<String> tabs){
		LogHandler.info("verify_mentioned_tabs_are_displayed(List<String> titles)");
		//List<String> titles = table.asList(String.class);
		for (String tab:tabs)
		{
			try
			{
				Assert.assertTrue("Tab "+tab+"not found in page", SeleniumUtilities.findElement(Identifier.XPATH, String.format(PAGE_TAB, tab)).isDisplayed());
				LogHandler.info("Verified presence of tab "+tab);
			}
			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Assert.assertTrue("Page object mimsatch for PAGE_TAB " +tab, false);
			}
		}

	}

	@Given("^Select the tab \"(.*?)\" on console$")
	public void select_the_tab_on_console(String tab){
		LogHandler.info("verify_current_active_tab(String tab)");
		try
		{
			SeleniumUtilities.findElement(Identifier.XPATH, String.format(TAB_SELECTION,tab)).click();
			LogHandler.info("Validated current active tab "+tab);
		}
		catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			Assert.assertTrue("Page object mimsatch for PAGE_CURRENT_TAB_WE_XPATH " +tab, false);
		}

	}
	
	@Given("^Verify currently active tab is \"(.*?)\"$")
	public void verify_current_active_tab(String tab){
		LogHandler.info("verify_current_active_tab(String tab)");
		try
		{
			Assert.assertTrue("Currently active tab is not "+tab, tab.equals(primewuipf.PAGE_CURRENT_TAB_WE_XPATH.isDisplayed()));
			LogHandler.info("Validated current active tab "+tab);
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("Page object mimsatch for PAGE_CURRENT_TAB_WE_XPATH " +tab, false);
		}

	}



	@Given("^Validate mandatory icons for corresponding fields \"(.*?)\"$")
	public void validate_mandatory_icon_for_field(List<String> fields){
		LogHandler.info("validate_invalid_icon_for_field");
		for (String field: fields){
			try
			{
				Assert.assertTrue("Mandatory icon not found for field "+field, SeleniumUtilities.findElement(Identifier.XPATH, String.format(MANDATORY_ICON, field)).isDisplayed());
				LogHandler.info("Mandatory icon found for field "+field);
			}
			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Assert.assertTrue("Page object mimsatch for MANDATORY_ICON  " +field, false);
			}
		}
	}

	@Given("^Validate invalid icons for corresponding fields \"(.*?)\"$")
	public void validate_invalid_icon_for_field(List<String> fields){
		LogHandler.info("validate_invalid_icon_for_field");
		for (String field: fields){
			try
			{
				Assert.assertTrue("Invalid icon not found for field "+field, SeleniumUtilities.findElement(Identifier.XPATH, String.format(INVALID_ICON, field)).isDisplayed());
				LogHandler.info("Invalid icon found for field "+field);
			}
			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Assert.assertTrue("Page object mimsatch for INVALID_ICON  " +field, false);
			}
		}
	}

	@Given("^Verify total table count in pane \"(.*?)\" matches with query \"(.*?)\"$")
	public void verify_total_count_matches_with_query(String title,String query){
		LogHandler.info("verify_total_count_matches_with_query()");

		List<Map<String,String>> queryvalues1 = null;
		DatabaseManager dbm;

		        dbm = new DatabaseManager();
		        try {
		            String dbip = System.getProperty("oracle.db.ip");
		            String dbsid = System.getProperty("oracle.db.scid");
		            String dbusr = System.getProperty("oracle.db.username");
		            String dbpswd = System.getProperty("oracle.db.password");
		            dbm.establishConnectionToOracleDb(dbip, dbsid, dbusr, dbpswd);
		        } catch (ClassNotFoundException | SQLException e) {
		            e.printStackTrace();
		        }
		try
		{
			queryvalues1=  dbselect(query);
			String count = queryvalues1.toString().substring(queryvalues1.toString().indexOf('=')+1, queryvalues1.toString().indexOf('}'));
			String ui_count = SeleniumUtilities.findElement(Identifier.XPATH, String.format(TOTAL_COUNT_XPATH,title)).getText().trim();
			Assert.assertTrue("Total count mismatch on console as db count is"+count+"and ui count is"+ui_count,ui_count.equals("Total "+count));
		}
		catch (WebDriverException | SQLException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			Assert.assertTrue("Page object WE_XPATH_TOTAL_COUNT mismatch", false);
		}
	}

    /**
     * Dbquery1.
     *
     * @param query the query
     * @return the list
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the SQL exception
     */
//	@SuppressWarnings("finally")
    public List<Map<String, String>> dbselect(String query) throws SQLException {
        List<Map<String, String>> s = null;
       
        ResultSet rs = dbm.execQuery(query);
        s = dbm.resultSetToListMap(rs);
        dbm.closeConnection();
        LogHandler.info("Check the query passed for dbupdate" + query);
        return s;
    }
	@Given("^Verify selected count in pane \"(.*?)\" matches \"(.*?)\"$")
	public void verify_selected_count_on_page(String title,String count){
		LogHandler.info("verify_selected_count_on_page(String count)");
		try
		{
			Assert.assertTrue("Selected count mismatch on console", SeleniumUtilities.findElement(Identifier.XPATH, String.format(SELECTED_COUNT_XPATH,title)).getText().trim().equals(count));
			LogHandler.info("Selected count matches on console");
		}
		catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			Assert.assertTrue("Page object WE_XPATH_SELECTED_COUNT mismatch", false);
		}
		try
		{
			Assert.assertTrue("Count separator mismatch on console", SeleniumUtilities.findElement(Identifier.XPATH, String.format(COUNT_SEPARATOR_XPATH,title)).isDisplayed());
			LogHandler.info("Count separator found on console");
		}		
		catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			Assert.assertTrue("Page object WE_XPATH_COUNT_SEPARATOR mismatch", false);
		}
	}

	@Given("Select checkboxes corresponding to column names \"(.*?)\"")
	public void select_checkbox_corresponding_to_columnnames(List<String> columns) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		LogHandler.info("select_checkbox_corresponding_to_columnnames(List<String> columns)");
		try
		{

			for(String column: columns)
			{

				List <WebElement> wes = new ArrayList<WebElement>();

				wes = SeleniumUtilities.findElements(Identifier.XPATH, String.format(CHECKBOXES_TO_SELECT, column));
				for (WebElement we: wes)
				{
					System.out.println("hi");
					we.click();
				}
			}
		}
		catch (WebDriverException e)
		{
			Assert.assertTrue("CHECKBOXES_TO_SELECT page object mismatch", false);
		}

	}
	
	@Given("^Navigate to VCS Console \"(.*?)\"$")
    public void navigate_to_VCS(String url) {
        LogHandler.info("void navigate_to_VCS(String url)" + url);
        if (url.startsWith("<<") && url.endsWith(">>")) {
            // url = commonpf.read_system_variable(url);
            url = Validate.readsystemvariable(url);

        }

        Hooks.scenario.write(url);
        LogHandler.info("SERVERHOST" + url);
        try {

            SeleniumUtilities.navigateToUrl(url + "/login.jsp");
            SeleniumUtilities.getDriver().navigate().refresh();

        } catch (TimeoutException e) {
            LogHandler.info("ULR:" + url + " is not reachable");
            try {

                Runtime.getRuntime().exec("taskkill /IM firefox.exe /t /F");

                Runtime.getRuntime().exec("taskkill /IM java.exe /t /F");


            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }
	  @Given("^Enter login user name \"(.*?)\"$")
	    public void enter_user_name(String username) {

	        if (username.startsWith("<<") && username.endsWith(">>")) {
	            //username = commonpf.read_system_variable(username);
	            username = Validate.readsystemvariable(username);

	        }


	        Hooks.scenario.write(username);
	        LogHandler.info("SERVERHOST" + username);
	        try {
	            LogHandler.info("void enter_user_name(String username)" + username);
	            SeleniumUtilities.type(primewuipf.WE_PRIME_USER_NAME_FIELD_ID, username);
	            //		loginpf.WE_PRIME_USER_NAME_FIELD_ID.clear();
	            //		loginpf.WE_PRIME_USER_NAME_FIELD_ID.sendKeys(username);
	        } catch (WebDriverException e) {
	            LogHandler.warn("Page Object PRIME_USER_NAME_FIELD_ID  mismatch");
	            Assert.assertTrue("Page Object PRIME_USER_NAME_FIELD_ID mismatch", false);
	        }

	    }


	    /**
	     * Enter_password.
	     * <pre>
	     * <b>Gherkin</b>
	     *    <code>And Enter password "password"</code>
	     * </pre>
	     *
	     * @param password the password
	     */
	    @Given("^Enter login password \"(.*?)\"$")
	    public void enter_password(String password) {
	        LogHandler.info("enter_password(String password)" + password);
	        if (password.startsWith("<<") && password.endsWith(">>")) {
	            //password = commonpf.read_system_variable(password);
	            password = Validate.readsystemvariable(password);

	        }


	        Hooks.scenario.write(password);
	        LogHandler.info("SERVERHOST" + password);
	        try {
	            //
	            //			loginpf.WE_PRIME_USER_PASSWORD_FIELD_ID.clear();
	            //			loginpf.WE_PRIME_USER_PASSWORD_FIELD_ID.sendKeys(password);
	            SeleniumUtilities.type(primewuipf.WE_PRIME_USER_PASSWORD_FIELD_ID, password);
	        } catch (WebDriverException e) {
	            LogHandler.warn("Page Object PRIME_USER_PASSWORD_FIELD_ID  mismatch");
	            Assert.assertTrue("Page Object PRIME_USER_PASSWORD_FIELD_ID mismatch", false);
	        }


	    }


	    @Given("^Click on \"(.*?)\" button on homepage$")
	    public void click_on_button(String arg1) {

	        try {
	            //			loginpf.WE_PRIME_SUBMIT_BUTTON_ID.click();
	            SeleniumUtilities.click(primewuipf.WE_PRIME_SUBMIT_BUTTON_ID);
	        } catch (WebDriverException e) {
	            LogHandler.warn("Page Object PRIME_SUBMIT_BUTTON_ID  mismatch");
	            Assert.assertTrue("Page Object PRIME_SUBMIT_BUTTON_ID  mismatch", false);
	        }

	    }
	    
	    @Given("^Click on the Toggle icon$")
		public void click_on_the_Toggle_menu_icon() {
		
				try{
					primewuipf.WE_PRIME_TREE_MAIN_LINK_CSS_SEL.click();
				}
				catch (WebDriverException e)
				{			
					LogHandler.warn( "Page Object PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL  mismatch");
					Assert.assertTrue("Page Object PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL  mismatch",false);
				}
		}

	    /**
		 * Click_on_main_menu_option.
		 * <pre>
		 * <b>Gherkin</b>
		 *    <code>And Click on "System Administration" main menu option </code>	
		 * </pre>
		 * @param mname the mname
		 */
		@Given("^Click on \"(.*?)\" mainmenu option$")
		public void Click_on_main_menu_option(String mname) {
				try{
					if (mname.equals("System Administration"))
						mname.replaceAll(mname, "Console Admin");

					primewuipf.getMAINMENU_SEL(mname).click();
				}
				catch (WebDriverException e)
				{			
					LogHandler.warn( "Page Object WPRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL  mismatch");
					Assert.assertTrue("Page Object PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL  mismatch",false);
				}	
				catch (NullPointerException e){
					LogHandler.warn( "Main menu name:"+mname);
					Assert.assertTrue("Main menu name:"+mname,false);
				}
			
		}
		/**
		 * Click_on_sub_menu_option.
		 * <pre>
		 * <b>Gherkin</b>
		 *    <code>And Click on "SSO Configuration" sub menu option</code>	
		 * </pre>
		 * @param smname the smname
		 */
		@Given("^Click on \"(.*?)\" submenu option$")
		public void Click_on_sub_menu_option(String smname) {
			LogHandler.warn( "Click_on_sub_menu_option(String smname)");				
			try{

					primewuipf.getSUBMENU_SEL(smname).click();
				}
				catch (WebDriverException e)
				{			
					LogHandler.warn( "Page Object PRIME_USERS_ROLES_AAA_LINK_CSS_SEL  mismatch");
					Assert.assertTrue("Page Object PRIME_USERS_ROLES_AAA_LINK_CSS_SEL  mismatch",false);
				}	
				catch (NullPointerException e){
					LogHandler.warn( "Sub menu name:"+smname);
					Assert.assertTrue("Sub menu name:"+smname,false);
				}
			

		}
		
		@Given("^Enter the following details in prime-textboxes$")
		public void enter_following_details_in_prime_textboxes(DataTable table){
			LogHandler.info("enter_following_details_in_prime_textboxes(DataTable table)");
			for (String label : table.topCells())
			{
				try
				{
					List<Map<String, String>> map = table
							.asMaps(String.class, String.class);
					SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_TEXTBOX, label)).clear();
					SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_TEXTBOX, label)).sendKeys(map.get(0).get(label));
					SeleniumUtilities.wait(1);
					/*
					String actual_text =primewuipf.TOOLTIP_RIGHT_WE_XPATH.getText().trim();
					actual_text = actual_text.replaceAll("\\r|\\n", "");
					actual_text = actual_text.trim();
					//System.out.println(actual_text);
					Assert.assertTrue("Actual tooltip "+actual_text+"does not match with expected tooltip "+map.get(0).get(label), actual_text.equals(map.get(0).get(label)));
					SeleniumUtilities.wait(4);*/
				}
				catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					Assert.assertTrue("Page Object PRIME_TEXTBOX not found on VCS Console" +label, false);
				}

			}
		}
		
		@Given("^Mark or unmark following prime-checkboxes$")
		public void mark_or_unmark_following_prime_checkboxes(DataTable table){
			LogHandler.info("mark_or_unmark_following_prime_checkboxes(DataTable table)");
			for (String label : table.topCells())
			{
				try
				{
					List<Map<String, String>> map = table
							.asMaps(String.class, String.class);
					if (map.get(0).get(label).equals("Tick"))
					{
					if ((SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).getAttribute("aria-checked"))  != null)
					{
						if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).getAttribute("aria-checked").equals("false"))
							SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).click();
					}
					else
					{
						if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).getAttribute("checked") == null)
							SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).click();
					}
					}
					//		SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).click();
					//else if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).getAttribute("aria-checked").equals("false"))
					//	SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).click();
					
					//}
					if (map.get(0).get(label).equals("Untick"))
					{
						if ((SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).getAttribute("aria-checked"))  != null)
						{
							if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).getAttribute("aria-checked").equals("true"))
								SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).click();
						}
						else
						{
							if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).getAttribute("checked") != null)
								SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).click();
						}
					}
					SeleniumUtilities.wait(1);
				}
				catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					Assert.assertTrue("Page Object PRIME_CHECKBOX not found on VCS Console", false);
				}

			}
		}
		
		@Given("^Select options from prime-dropdowns$")
		public void select_options_from_prime_dropdowns(DataTable table){
			LogHandler.info("select_options_from_prime_dropdowns(DataTable table)");
			for (String label : table.topCells())
			{
				try
				{
					List<Map<String, String>> map = table
							.asMaps(String.class, String.class);
					SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_DROPDOWN, label)).click();
					SeleniumUtilities.wait(1);
					SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_DROPDOWN_TEXT,map.get(0).get(label), map.get(0).get(label))).click();
				}
				catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					Assert.assertTrue("Page Object PRIME_DROPDOWN not found on VCS Console", false);
				}

			}
		}
		@Given("^Select the corresponding radio button for mentioned fields$")
		public void select_the_corresponding_radio_buttons(DataTable table){
			LogHandler.info("select_the_corresponding_radio_buttons(DataTable table)");
			for (String label : table.topCells())
			{
				try
				{
					List<Map<String, String>> map = table
							.asMaps(String.class, String.class);
				//	if (map.get(0).get(label).equals("Yes"))
				//	{
					SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_RADIOBUTTON, label,map.get(0).get(label))).click();
				
				//	}
				//	if (map.get(0).get(label).equals("Untick"))
				//	{
				//	if (SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).getAttribute("aria-checked").equals("true"))
				//		SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_CHECKBOX, label)).click();
				//	}
					SeleniumUtilities.wait(1);
				}
				catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					Assert.assertTrue("Page Object PRIME_RADIOBUTTON not found on VCS Console", false);
				}

			}
		}
		
		@Given("^Click the prime-button \"(.*?)\" present in panel \"(.*?)\"$")
		public void click_prime_button_present_in_panel(String button, String panel){
			LogHandler.info("click_prime_button_present_in_panel(String button, String panel)");
			try
			{
				SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_BUTTONS,panel,button)).click();
			}
			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Assert.assertTrue("Page Object PRIME_BUTTONS not found on VCS Console", false);
			}
		}
		
		@Given("^Enter invalid content \"(.*?)\" in prime-textbox \"(.*?)\" and verify invalid message \"(.*?)\"$")
		public void enter_invalid_content_in_prime_textbox_and_verify_invalid_message(String content, String textbox, String message){
			LogHandler.info("enter_invalid_content_in_prime_textbox_and_verify_invalid_message(String content, String textbox, String message)");
			try
			{
				SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_TEXTBOX, textbox)).clear();
				SeleniumUtilities.findElement(Identifier.XPATH, String.format(PRIME_TEXTBOX, textbox)).sendKeys(content);
				SeleniumUtilities.wait(1);
				Assert.assertTrue("Invalid icon not found for field "+textbox, SeleniumUtilities.findElement(Identifier.XPATH, String.format(INVALID_ICON, textbox)).isDisplayed());
				LogHandler.info("Invalid icon found for field "+textbox);
				String actual_text =primewuipf.TOOLTIP_ABOVE_WE_XPATH.getText().trim();
				actual_text = actual_text.replaceAll("\\r|\\n", "");
				actual_text = actual_text.trim();	
				Assert.assertTrue("Actual tooltip "+actual_text+"does not match with expected tooltip "+message, actual_text.equals(message));
				SeleniumUtilities.wait(4);
				
			}
			catch (WebDriverException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Assert.assertTrue("Page Object PRIME_RADIOBUTTON not found on VCS Console", false);
			}
		}
		
		@When("^UI wait for \"(.*?)\" seconds$")
		public void ui_wait_for_seconds(String argSec) {
			LogHandler.info("wui_wait_for_seconds(String argSec)" + argSec);
			try {
				int sec = Integer.parseInt(argSec);
				SeleniumUtilities.wait(sec, Autowait.vcswui);
			} catch (Exception e) {
				LogHandler.warn("In-valid WUI wait");
				Assert.assertTrue("In-valid WUI wait", false);
			}

		}
		
		@Given("^Scroll down or up$")
		public void scroll_down() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//	WebElement we = SeleniumUtilities.findElement(Identifier.XPATH, ".//label[normalize-space(text())='Users and Groups']");
		//	WebElement scroll = SeleniumUtilities.findElement(Identifier.XPATH, ".//label[contains(normalize-space(text()),'Users and Groups')]");
			WebElement scroll = primewuipf.SETTINGS_BUTTON_WE_XPATH;
			scroll.sendKeys(Keys.PAGE_UP);
			//	SeleniumUtilities.scroll(we, 100);
			//	WebElement element = driver.findElement(By.id("id_of_element"));
			//	JavascriptExecutor js = (JavascriptExecutor) (SeleniumUtilities.getDriver());
			((JavascriptExecutor) SeleniumUtilities.getDriver()).executeScript("arguments[0].scrollIntoView(true);", scroll);
		}
}

