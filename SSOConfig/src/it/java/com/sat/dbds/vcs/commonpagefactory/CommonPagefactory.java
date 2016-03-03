/*
 * 
 */
package com.sat.dbds.vcs.commonpagefactory;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.sat.dbds.vcs.commonpageobject.PageCommonConstants;


// TODO: Auto-generated Javadoc
/**
 * The Class CommonPagefactory.
 */
public class CommonPagefactory  implements PageCommonConstants {

	/** The we pagetitle css. */
	@FindBy(css = PAGETITLE_CSS)
	public WebElement WE_PAGETITLE_CSS;
	
	/** The we pagepanel css. */
	@FindBy(css = PAGEPANEL_CSS)
	public WebElement WE_PAGEPANEL_CSS;
	
	/** The We css view alert critical div. */
	@FindBy(css = ALERT_CRITICAL_DIV)
	public WebElement WE_CSS_View_ALERT_CRITICAL_DIV;
	
	@FindBy(xpath = TOOLTIP_TEXT)
	public WebElement WE_XPATH_TOOLTIP_TEXT;
	
	
	/** The we errormesssage css. */
	@FindBy(css = ERRORMESSSAGE_CSS)
	public WebElement WE_ERRORMESSSAGE_CSS;
	
	/** The we css alert critical div. */
	@FindBys({@FindBy(css = ALERT_CRITICAL_DIV)})
	private List<WebElement> WE_CSS_ALERT_CRITICAL_DIV;
	
	//#xwt_widget_notification_Alert_0 div.buttonPane
	

	/** The we prime alert selector. */
	@FindBy(css = PRIME_ALERT_SELECTOR)
	public WebElement WE_PRIME_ALERT_SELECTOR;
	
	/** The we alert critical message. */
	@FindBy(css = ALERT_CRITICAL_MESSAGE)
	public WebElement WE_ALERT_CRITICAL_MESSAGE;
	
	
	/** The we css alert critical div ok. */
	@FindBys({@FindBy(css = ALERT_CRITICAL_DIV_OK_BUTTON)})
	private List<WebElement> WE_CSS_ALERT_CRITICAL_DIV_OK;
	
	/** The mandatory field. */
	public final String MANDATORY_FIELD="//*[normalize-space(text())='%s']/sup";
	
	//public final String TOOL_TIP="//img[@title='%s']";
 
	/** The tool tip. */
//	public final String TOOL_TIP="//tr[td/label[contains(text(),'%s')]]/td[3]/img" ;
	
	public static final String TOOLTIPID_XPATH_WE=".//label[contains(normalize-space(text()),'%s')]/../..//img";
	//-- getattr id
	public static final String TOOLTIP_CONTENT_XPATH_WE=".//*[@connectid='%s'][@dojotype='dijit.Tooltip']//span";
	public static final String TOOLTIP_CONTENT1_XPATH_WE=".//*[@connectid='serverPortImg'][@dojotype='dijit.Tooltip']//span";
	
	//get text
		
	
			
	/** The we logout message. */
	@FindBy(xpath = LOGOUT_MESSAGE)
	public WebElement WE_LOGOUT_MESSAGE;
	
	/** The we invalid icon. */
	@FindBy(css = INVALID_ICON)
	public WebElement WE_INVALID_ICON;
	

	/** The we settings button. */
	@FindBy(css = SETTINGS_BUTTON)
	public WebElement WE_SETTINGS_BUTTON;
	
	@FindBy(id = SHOST)
	public WebElement WE_SHOST;
	
	
	/** The we logout button. */
	@FindBy(css = LOGOUT_BUTTON)
	public WebElement WE_LOGOUT_BUTTON;
	
	/**
	 * Gets the criticalalertmessage.
	 *
	 * @param cmessage the cmessage
	 * @return the criticalalertmessage
	 */
	public String getCRITICALALERTMESSAGE(String cmessage) {
		String str=null;	
		 for(WebElement elem:WE_CSS_ALERT_CRITICAL_DIV){
				String elem1text=elem.getText();
				if(elem1text.equalsIgnoreCase(cmessage)){
					str= elem1text;
				}
			}
		return str;	
	}
	
	/**
	 * Gets the criticalalertokbutton.
	 *
	 * @return the criticalalertokbutton
	 */
	public WebElement getCRITICALALERTOKBUTTON() {
		System.out.println("ok button start");
		WebElement web =null;	
		 for(WebElement elem:WE_CSS_ALERT_CRITICAL_DIV_OK){
				String elem1text=elem.getText();
				System.out.println(elem1text);
				if(elem1text.equalsIgnoreCase("OK")){
					web = elem;
				}
			}
		return web;	
	}
	
}
