/*
 * 
 */
package com.sat.dbds.vcs.menu.pagefactory;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.sat.dbds.vcs.menu.pageobject.VCSPageConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuPagefactory.
 */
public class MenuPagefactory implements VCSPageConstants{
	
	/** The we prime tree main link css sel. */
//	@FindBy(css = PRIME_TREE_MAIN_LINK_CSS_SEL)
//	public WebElement WE_PRIME_TREE_MAIN_LINK_CSS_SEL;
	
	@FindBys({@FindBy(xpath = PRIME_TREE_MAIN_LINK_CSS_SEL)})
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
	
	/** The wes prime users roles aaa link css sel. */
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
}
