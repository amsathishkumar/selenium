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
package com.cisco.dbds.utils.primewidgets.pagefactory;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.cisco.dbds.utils.primewidgets.pageobject.primePageConstants;

/**
 * @author maparame
 *
 */
public class primePageFactory implements primePageConstants{
	
	
	

	/*
	 * SUCCESS/WARNING TOASTERS
	 */
	@FindBy(xpath = SUCCESS_TOASTER_NOTIFICATION)
	public WebElement SUCCESS_TOASTER_NOTIFICATION_WE_XPATH;
	
	@FindBy(xpath = SUCCESS_TOASTER_TITLE)
	public WebElement SUCCESS_TOASTER_TITLE_WE_XPATH;
	
	@FindBy(xpath = SUCCESS_TOASTER_BODY)
	public WebElement SUCCESS_TOASTER_BODY_WE_XPATH;
	
	@FindBy(xpath = WARNING_TOASTER_NOTIFICATION)
	public WebElement WARNING_TOASTER_NOTIFICATION_WE_XPATH;
	
	@FindBy(xpath = WARNING_TOASTER_TITLE)
	public WebElement WARNING_TOASTER_TITLE_WE_XPATH;
	
	@FindBy(xpath = WARNING_TOASTER_BODY)
	public WebElement WARNING_TOASTER_BODY_WE_XPATH;
	
	@FindBy(xpath = CLOSE_TOASTER)
	public WebElement CLOSE_TOASTER_WE_XPATH;
	
	

	/*
	 * CRITICAL/WARNING/INFO ALERT NOTIFICATIONS
	 */
	
	@FindBy(xpath = CRITICAL_ALERT_NOTIFICATION)
	public WebElement CRITICAL_ALERT_NOTIFICATION_WE_XPATH;
	
	@FindBy(xpath = CRITICAL_ALERT_NOTIFICATION_CONTENT)
	public WebElement CRITICAL_ALERT_NOTIFICATION_CONTENT_WE_XPATH;
	
	@FindBy(xpath = WARNING_ALERT_NOTIFICATION)
	public WebElement WARNING_ALERT_NOTIFICATION_WE_XPATH;
	
	@FindBy(xpath = WARNING_ALERT_NOTIFICATION_CONTENT)
	public WebElement WARNING_ALERT_NOTIFICATION_CONTENT_WE_XPATH;	

	@FindBy(xpath = INFO_ALERT_NOTIFICATION)
	public WebElement INFO_ALERT_NOTIFICATION_WE_XPATH;
	
	@FindBy(xpath = INFO_ALERT_NOTIFICATION_CONTENT)
	public WebElement INFO_ALERT_NOTIFICATION_CONTENT_WE_XPATH;
	
	
	/*
	 * BUTTON in ALERT NOTIFICATION
	 */
	
	@FindBy(xpath = ALERT_NOTIFICATION_BUTTON)
	public WebElement ALERT_NOTIFICATION_BUTTON_WE_XPATH;
	
	/*
	 * TOOLTIPS
	 */
	
	@FindBy(xpath = TOOLTIP_RIGHT)
	public WebElement TOOLTIP_RIGHT_WE_XPATH;
	
	@FindBy(xpath = TOOLTIP_ABOVE)
	public WebElement TOOLTIP_ABOVE_WE_XPATH;
	
	/*
	 * TOOLTIPS
	 */
	
	@FindBy(xpath = POPOVER_TITLE)
	public WebElement POPOVER_TITLE_WE_XPATH;
	
	@FindBy(xpath = POPOVER_CONTENT)
	public WebElement POPOVER_CONTENT_WE_XPATH;
	
	/*
	 * GLOBAL_TOOLBAR_REFRESH_SETTINGS_OPERATIONS
	 */

	/*Refresh/Settings buttons*/
	@FindBy(xpath = REFRESH_BUTTON)
	public WebElement REFRESH_BUTTON_WE_XPATH;
	
	@FindBy(xpath = SETTINGS_BUTTON)
	public WebElement SETTINGS_BUTTON_WE_XPATH;
	
	/*Settings Columns Filtering*/
	
	@FindBy(xpath = SETTINGS_CLOSE_BUTTON)
	public WebElement SETTINGS_CLOSE_BUTTON_WE_XPATH;
	
	@FindBy(xpath = SETTINGS_RESET_BUTTON)
	public WebElement SETTINGS_RESET_BUTTON_WE_XPATH;
	
	/*Current Tab*/
	@FindBy(xpath = PAGE_CURRENT_TAB)
	public WebElement PAGE_CURRENT_TAB_WE_XPATH;
	
	/*Selected/Total Count*//*
    @FindBy(xpath = TOTAL_COUNT_XPATH)
  	public WebElement WE_XPATH_TOTAL_COUNT;
    
    @FindBy(xpath = SELECTED_COUNT_XPATH)
   	public WebElement WE_XPATH_SELECTED_COUNT;
     
     @FindBy(xpath = COUNT_SEPARATOR_XPATH)
   	public WebElement WE_XPATH_COUNT_SEPARATOR;
    */
	
	/*
	 * LOGIN
	 */
		
     @FindBy(id = PRIME_USER_NAME_FIELD_ID)
     public WebElement WE_PRIME_USER_NAME_FIELD_ID;

     @FindBy(id = PRIME_USER_PASSWORD_FIELD_ID)
     public WebElement WE_PRIME_USER_PASSWORD_FIELD_ID;

     @FindBy(id = PRIME_SUBMIT_BUTTON_ID)
     public WebElement WE_PRIME_SUBMIT_BUTTON_ID;
     
     /*
      * MENU
      */
	
     /** The we prime tree main link css sel. */
 	@FindBy(css = PRIME_TREE_MAIN_LINK_CSS_SEL)
 	public WebElement WE_PRIME_TREE_MAIN_LINK_CSS_SEL;
 	
	/** The wes prime system administration plane css sel. */
	@FindBys({@FindBy(css = PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL)})
	private List<WebElement> WES_PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL;
	
	/**
	 * Gets the mainmenu sel.
	 *
	 * @param mname the mname
	 * @return the mainmenu sel
	 */
	public WebElement getMAINMENU_SEL(String mname) {
		WebElement dupli=null;	
		 for(WebElement elem:WES_PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL){
				String attr=elem.getAttribute("title");
				if(attr.equals(mname)){
					dupli= elem;
				}
			}
		return dupli;	
	}
	@FindBys({@FindBy(css = PRIME_USERS_ROLES_AAA_LINK_CSS_SEL)})
	private List<WebElement> WES_PRIME_USERS_ROLES_AAA_LINK_CSS_SEL;

	/**
	 * Gets the submenu sel.
	 *
	 * @param smname the smname
	 * @return the submenu sel
	 */
	public WebElement getSUBMENU_SEL(String smname) {
		WebElement dupli=null;	
		 for(WebElement elem:WES_PRIME_USERS_ROLES_AAA_LINK_CSS_SEL){
				String attr=elem.getAttribute("title");
				if(attr.equals(smname)){
					dupli= elem;
				}
			}
		return dupli;	
	}
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

}

