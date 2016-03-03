/*
 * 
 */
package com.sat.dbds.vcs.menu;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cisco.dbds.utils.cucumber.Hooks;
import com.cisco.dbds.utils.logging.LogHandler;
import com.cisco.dbds.utils.selenium.SeleniumUtilities;
import com.sat.dbds.vcs.menu.pagefactory.MenuPagefactory;

import cucumber.api.java.en.Given;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuStepDef.
 */
public class MenuStepDef {

	/** The menupf. */
	private MenuPagefactory menupf;

	/** The runtype. */
	public String runtype;
	/**
	 * Instantiates a new menu step def.
	 */
	public MenuStepDef() {

		runtype = System.getProperty("run.type");

		if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))
			menupf = PageFactory.initElements(SeleniumUtilities.getDriver(),MenuPagefactory.class);

	}



	/**
	 * Click_on_the_ toggle_menu_icon.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>Given Click on the Toggle menu icon </code>	
	 * </pre>
	 */
	@Given("^Click on the Toggle menu icon$")
	public void click_on_the_Toggle_menu_icon() {
		if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))
		{
			LogHandler.info("Click on toggle menu icon");
		//	try{
			//	SeleniumUtilities.waitForElement(menupf.WE_PRIME_TREE_MAIN_LINK_CSS_SEL);
			List<WebElement> found = SeleniumUtilities.getDriver().findElements(By.xpath(".//*[@class='toggleIcon icon-cisco-menu']"));
			if (found.size() > 0) 
			{
			    found.get(0).click();
			    LogHandler.info("Clicked toggle icon on home screen");
			} 
			else {
				Assert.assertTrue("Unable to login to VCS Console", false);
			}
				//menupf.WE_PRIME_TREE_MAIN_LINK_CSS_SEL.click();
		//	}
		
			/*catch (WebDriverException e)
			{			
				LogHandler.warn( "Page Object PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL  mismatch");
				Assert.assertTrue("Page Object PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL  mismatch",false);
			}
			catch (Exception e)
			{
				Assert.assertTrue("VCS console login not happening", false);
			}*/
		}
		else
			Hooks.scenario.write("Skipped for Rest");


	}

	/**
	 * Click_on_main_menu_option.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>And Click on "System Administration" main menu option </code>	
	 * </pre>
	 * @param mname the mname
	 */
	@Given("^Click on \"(.*?)\" main menu option$")
	public void Click_on_main_menu_option(String mname) {
		if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))
		{
			try{
				if (mname.equals("System Administration"))
					mname.replaceAll(mname, "Console Admin");

				menupf.getMAINMENU_SEL(mname).click();
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
		else
			Hooks.scenario.write("Skipped for Rest");

	}

	/**
	 * Click_on_sub_menu_option.
	 * <pre>
	 * <b>Gherkin</b>
	 *    <code>And Click on "SSO Configuration" sub menu option</code>	
	 * </pre>
	 * @param smname the smname
	 */
	@Given("^Click on \"(.*?)\" sub menu option$")
	public void Click_on_sub_menu_option(String smname) {
		LogHandler.warn( "Click_on_sub_menu_option(String smname)");
		if ("WUI".equalsIgnoreCase(runtype.toLowerCase()))
		{
			try{

				menupf.getSUBMENU_SEL(smname).click();
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
		else
			Hooks.scenario.write("Skipped for Rest");

	}

}
