/*
 * 
 */
package com.sat.dbds.vcs.ssoconfig.pagefactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sat.dbds.vcs.ssoconfig.pageobject.ConfiguratorViewPageConstants;


// TODO: Auto-generated Javadoc
/**
 * The Class SSOconfigPagefactory.
 */
public class SSOconfigPagefactory implements ConfiguratorViewPageConstants{
	
	/** The Constant TOASTER. */
	private static final String TOASTER = null;

	/** The we id server host. */
	@FindBy(id = SERVER_HOST)
	public WebElement WE_ID_SERVER_HOST;
	
	/** The we id server port. */
	@FindBy(id = SERVER_PORT)
	public WebElement WE_ID_SERVER_PORT;
	
	/** The we id user name. */
	@FindBy(id = USER_NAME)
	public WebElement WE_ID_USER_NAME;
	
	/** The we id password. */
	@FindBy(id = PASSWORD)
	public WebElement WE_ID_PASSWORD;
	
	
	/** The we id cache int. */
	@FindBy(id = CACHE_INT)
	public WebElement WE_ID_CACHE_INT;
	
	/** The we id max inact time. */
	@FindBy(id = MAX_INACT_TIME)
	public WebElement WE_ID_MAX_INACT_TIME;
	
	
	
	/** The we id is active. */
	@FindBy(id = IS_ACTIVE)
	public WebElement WE_ID_IS_ACTIVE;
	
	/** The we id save btn. */
	@FindBy(id = SAVE_BTN)
	public WebElement WE_ID_SAVE_BTN;
	
	/** The we id reset btn. */
	@FindBy(id = RESET_BTN)
	public WebElement WE_ID_RESET_BTN;	
	
	/** The we id toaster. */
	@FindBy(css = SUCCESS_TOASTER)
	public WebElement WE_CSS_TOASTER;
	
	/** The we id ne save btn. */
	@FindBy(id = NE_SAVE_BTN)
	public WebElement WE_ID_NE_SAVE_BTN;	
	
	
	/** The we xpath ecsdashboard tab. */
	public final String WE_XPATH_ECSDASHBOARD_TAB="//span[@class='tabLabel'][text()='%s']";
	
	/** The we xpath ecsdashboard dropdown. */
	public final String WE_XPATH_ECSDASHBOARD_DROPDOWN=".//*[@id='widget_userDropDown']/*[contains(@class, icon-rotate-90)]"; 
	
	/** The we xpath ecsdashboard user. */
	public final String WE_XPATH_ECSDASHBOARD_USER=".//*[contains(@id,'userDropDown_popup')]/*[text()='%s']";
	
	/** The we xpath ecsdashboard ec. */
	public final String WE_XPATH_ECSDASHBOARD_EC=".//*[@id='neListBox']/option[text()='%s']";
	

	/** The we xpath toaster body. */
	public final String WE_XPATH_TOASTER_BODY=".//*[@id='%s']//*[@class='xwtBody'][text()='%s']";
	
	/** The we xpath toaster title. */
	public final String WE_XPATH_TOASTER_TITLE=".//*[@id='%s']//*[@class='xwtTitle'][text()='%s']";
	
	public final static String rsurl_ssoupdate="/vcsconsole/rs/ssoconfig/updateSsoConfig";
	public final static String ssosave_sucess1="saved successfully but unable to publish configuration!!!";
	/*
	Network Element Management
	Network Element Access Saved Successfully!!!
	*/
}
