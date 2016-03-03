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
package com.cisco.dbds.utils.primewidgets.pageobject;

/**
 * @author maparame
 *
 */
public interface primePageConstants {

	/*
	 * SUCCESS/WARNING TOASTERS
	 */
	String SUCCESS_TOASTER_NOTIFICATION = "//div[contains(@class,'xwtToaster')][contains(@style,'display: block;')]//*[@class='xwt-success-message']";
	String SUCCESS_TOASTER_TITLE = "//div[contains(@class,'xwtToaster')][contains(@style,'display: block;')]//*[@class='xwt-success-message']/div[@class='xwtTitle']";
	String SUCCESS_TOASTER_BODY = "//div[contains(@class,'xwtToaster')][contains(@style,'display: block;')]//*[@class='xwt-success-message']/div[@class='xwtBody']";

	String WARNING_TOASTER_NOTIFICATION = "//div[contains(@id,'xwt_widget_notification_Toaster')][contains(@style,'display: block;')]//*[@class='xwt-warning-message']";
	String WARNING_TOASTER_TITLE = "//div[contains(@id,'xwt_widget_notification_Toaster')][contains(@style,'display: block;')]//*[@class='xwt-warning-message']/div[@class='xwtTitle']";
	String WARNING_TOASTER_BODY = "//div[contains(@id,'xwt_widget_notification_Toaster')][contains(@style,'display: block;')]//*[@class='xwt-warning-message']/div[@class='xwtBody']";
	String CLOSE_TOASTER = "//div[contains(@id,'xwt_widget_notification_Toaster')][contains(@style,'display: block;')]//*[@class='xwtCloseIcon']";

	/*
	 * CRITICAL/WARNING/INFO ALERT NOTIFICATIONS
	 */

	String CRITICAL_ALERT_NOTIFICATION = ".//*[contains(@id,'xwt_widget_notification_Alert')]//*[@class='xwtAlert-critical']";
	String CRITICAL_ALERT_NOTIFICATION_CONTENT = ".//*[contains(@id,'xwt_widget_notification_Alert')]//*[@class='xwtAlert-critical']/div";

	String WARNING_ALERT_NOTIFICATION = ".//*[contains(@id,'xwt_widget_notification_Alert')]//*[@class='xwtAlert-warning']";
	String WARNING_ALERT_NOTIFICATION_CONTENT = ".//*[contains(@id,'xwt_widget_notification_Alert')]//*[@class='xwtAlert-warning']/div";

	String INFO_ALERT_NOTIFICATION = ".//*[contains(@id,'xwt_widget_notification_Alert')]//*[@class='xwtAlert-information']";
	String INFO_ALERT_NOTIFICATION_CONTENT = ".//*[contains(@id,'xwt_widget_notification_Alert')]//*[@class='xwtAlert-information']/div";


	/*
	 * BUTTON in ALERT NOTIFICATION
	 */

	String ALERT_NOTIFICATION_BUTTON = ".//span[@class='dijitReset dijitInline dijitButtonText'][text()='%s']";

	/*
	 * TOOLTIPS
	 */
	String TOOLTIP_RIGHT = ".//*[contains(@class,'dijitTooltip dijitTooltipRight')]//*[@class='dijitTooltipContainer dijitTooltipContents']";
	String IMAGE_ICON = ".//label[normalize-space(text())='%s']/../..//img";
	String TOOLTIP_ABOVE = ".//*[contains(@class,'dijitTooltip dijitTooltipAbove')]//*[@class='dijitTooltipContainer dijitTooltipContents']";
	String INVALID_ICON="//label[normalize-space(text())='%s']/../following-sibling::td[1]//*[contains(@class,'dijitValidationTextBoxError dijitError')]//*[@class='dijitReset dijitValidationIcon']";
	

	/*
	 * POP_OVERS
	 */
	String POPOVER_TITLE = "//*[@class='xwtPopover dijitTooltipDialog xwtQuickview xwtHoverPopover xwtActionPopover dijitTooltipABLeft dijitTooltipRight']//*[@class='xwtPopoverTitleTd']//span";
	String POPOVER_CONTENT = "//*[@class='xwtPopover dijitTooltipDialog xwtQuickview xwtHoverPopover xwtActionPopover dijitTooltipABLeft dijitTooltipRight']//*[@class='dijitTooltipContents dijitTooltipFocusNode']//*[@class='xwtPopoverContainer']";

	/*
	 * GLOBAL_TOOLBAR_REFRESH_SETTINGS_OPERATIONS
	 */
	String REFRESH_BUTTON = "//div[contains(@class,'dijitVisible')=1]//*[contains(@id,'xwtTableGlobalToolbar_refreshButton')][contains(@class,'dijitButtonContents')]";
	String SETTINGS_BUTTON = "//div[contains(@class,'dijitVisible')=1]//*[contains(@id,'xwtTableGlobalToolbar_settingsButton')][contains(@class,'dijitDownArrowButton')]";
	
	/*SETTINGS OPTIONS*/
	String SETTINGS_OPTIONS = "//table[contains(@id,'xwtTableGlobalToolbar_settingsMenu')]/tbody/tr/td[2][text() = '%s']";
	
	/*SETTINGS COLUMNS FILTERING*/
	String SETTINGS_COLUMNS_OPTION_STATUS="//*[@role='menuitemcheckbox'][contains(@aria-label,'%s')]";
	String SETTINGS_COLUMNS_OPTIONS = "//*[@role='menuitemcheckbox'][contains(@aria-label,'%s')]//*[text()='%s']";
	String SETTINGS_CLOSE_BUTTON=" .//*[contains(@id,'xwtTableGlobalToolbar_columnsMenu_close_label')]";
	String SETTINGS_RESET_BUTTON=".//*[contains(@id,'xwtTableGlobalToolbar_columnsMenu_reset_label')]";
	
	/*SETTINGS FIX TO TOP/BOTTOM*/
	String FIX_TO_TOP_OR_BOTTOM=".//*[contains(@id,'xwt_widget_menu_MenuItem')][text()='%s']";
	String ROW_LOCKED_AT_BOTTOM=".//*[contains(@class,'table-row-locked-container-bottom')]//*[text()='%s']";
	String ROW_LOCKED_AT_TOP=".//*[contains(@class,'table-row-locked-container-top')]//*[text()='%s']";
	String ROW_DUPLICATE_LOCKED=".//*[contains(@class,'row-locked no-index')][contains(@class,'row-even') or contains(@class,'row-odd')]//*[text()='%s']";
	String ROW_TOPLOCKED_CHECKBOX=".//*[contains(@class,'table-row-locked-container-top')]//*[text()='%s']/../../..//*[@type='checkbox']";
	String ROW_BOTTOMLOCKED_CHECKBOX=".//*[contains(@class,'table-row-locked-container-bottom')]//*[text()='%s']/../../..//*[@type='checkbox']";
	
	
	/*GENERAL_TAB/COLUMNS/PANELTITLE/MANDATORYICON*/
	String COLUMN_NAME = "//*[@role='columnheader']//div[@class='cellHeaderWrapper'][text()='%s']";
	String PANEL_TITLE = "//div[@class='xwtTitleNodeContainer']/div[@class='dijitInline xwtTitlePaneTextNode'][text()='%s']";
	String PAGE_TAB = "//*[@class='dijitTabContent'][@role='tab']/span[@class='tabLabel']";
	String PAGE_CURRENT_TAB = "//*[@class='dijitTabContent'][@role='tab'][@aria-selected='true']/span[@class='tabLabel']";
	String MANDATORY_ICON = "//label[normalize-space(text())='%s']/sup";
	String TAB_SELECTION = "//*[@class='dijitTabContent'][@role='tab']//span[text()='%s']";
	
	/*SELECTED/TOTAL COUNT*/
    public static final String SELECTED_COUNT_XPATH="//*[contains(@class,'xwtTitlePaneTextNode')][text()='%s']/ancestor::div[contains(@class,'xwtTitlePaneTitle')]/following-sibling::div[1]//*[@class='selectionCountLabel']";
    public static final String COUNT_SEPARATOR_XPATH="//*[contains(@class,'xwtTitlePaneTextNode')][text()='%s']/ancestor::div[contains(@class,'xwtTitlePaneTitle')]/following-sibling::div[1]//*[@class='countSeparatorBorder']";
    
//    public static final String TOTAL_COUNT_XPATH=".//*[@class='totalCountLabel']";
    public static final String TOTAL_COUNT_XPATH ="//*[contains(@class,'xwtTitlePaneTextNode')][text()='%s']/ancestor::div[contains(@class,'xwtTitlePaneTitle')]/following-sibling::div[1]//*[@class='totalCountLabel']";
	//String ROW_BOTTOMLOCKED_CHECKBOX_XPATH=".//*[contains(@class,'table-row-locked-container-bottom')]//*[text()='%s']/ancestor::*/preceding-sibling::td//*[@type='checkbox']";
	

    /*CHECKBOXES SELECTION*/
    String CHECKBOXES_TO_SELECT="//div[contains(@class,'dijitVisible')=1]//*[text()='%s']/ancestor::*/preceding-sibling::td//*[@type='checkbox']";
	
    /*LOGIN*/
    /** IDs for CMC Login Page. */	
    public static final String PRIME_USER_NAME_FIELD_ID  = "loginPage_username";
    
    /** The Constant PRIME_USER_PASSWORD_FIELD_ID. */
    public static final String PRIME_USER_PASSWORD_FIELD_ID = "loginPage_password";
    
    /** The Constant PRIME_SUBMIT_BUTTON_ID. */
    public static final String PRIME_SUBMIT_BUTTON_ID = "loginPage_LoginButton";   
    /*
     * MENU
     */
    /** IDs for Top level VCS Prime drop down menus. */
    public static final String PRIME_TREE_MAIN_LINK_CSS_SEL="div.headerNode div.headerLeft div.toggleNode div.icon-cisco-menu";
    /** The Constant PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL. */
    public static final String PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL="div.dijitTitlePaneTitle  div.dijitTitlePaneTitleFocus";
    /** The Constant PRIME_USERS_ROLES_AAA_LINK_CSS_SEL. */
    public static final String PRIME_USERS_ROLES_AAA_LINK_CSS_SEL ="div.hasChildren div.xwtSlideMenuMenuItemText";
    /*
     * BASIC_WIDGETS
     */
  //*[text()='Authorization Configuration']/../../..//*[@class='dijitReset dijitStretch dijitButtonContents'][@role='button']//*[text()='Save']

  String PRIME_TEXTBOX = "//*[normalize-space(text())='%s']/ancestor::tr[1]//input[contains(@class,'dijitInputInner') and contains(@class,'dijitReset')]";// -- textbox
 // String PRIME_CHECKBOX = "//*[normalize-space(text())='%s']/ancestor::tr[1]//input[contains(@class,'dijitCheckBoxInput') and contains(@class,'dijitReset')][@type='checkbox']";// -- checkbox
  String PRIME_CHECKBOX = "//*[normalize-space(text())='%s']/ancestor::tr[1]//div[contains(@class,'dijitCheckBox')]//input[@type='checkbox']";// -- checkbox
  String PRIME_BUTTONS = "//*[@class='dijitInline xwtTitlePaneTextNode'][text()='%s']/ancestor::*[@class='xwtTitlePane evenLevelTitlePane']//span[contains(@class,'dijitInline') and contains(@class,'dijitReset') and contains(@class,'dijitButtonText')][text()='%s']";// - buttons
  
 String PRIME_DROPDOWN = "//*[normalize-space(text())='%s']/ancestor::tr[1]//div[contains(@class,'dijitDownArrowButton')]";// -- dropdown button
 // String PRIME_DROPDOWN_TEXT ="//*[text()='%s']/ancestor-or-self::*[contains(@class,'dijitMenuItem')]//*[text()='%s']";// --text for dropdown
String PRIME_DROPDOWN_TEXT = "//*[text()='%s']/ancestor-or-self::*[contains(@style,'none')=0][contains(@class,'dijitComboBoxMenuPopup') or contains(@class,'dijitMenuPopup')]//*[text()='%s']"; //--text for dropdown
  String PRIME_RADIOBUTTON = "//*[normalize-space(text())='%s']/ancestor::tr[1]//div[contains(@class,'dijitRadio')]//input[contains(@id,'%s')]";
    /**
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

