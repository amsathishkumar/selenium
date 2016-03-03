/*
 * 
 */
/**
* Copyright (c) ${year} by SAT Systems, Inc.
* All rights reserved.
*
* This software is the confidential and proprietary information
* of SAT Systems,  ("Confidential Information").  You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with SAT Systems.
*
*
* @Project: SSO-Config
* @Author: anganesa
* @Version:
* @Description:
* @@Date created: June 19, 2015
*/



package com.sat.dbds.vcs.login;

import com.cisco.dbds.utils.cucumber.Hooks;
import com.cisco.dbds.utils.logging.LogHandler;
import com.cisco.dbds.utils.selenium.Identifier;
import com.cisco.dbds.utils.selenium.SeleniumUtilities;
import com.cisco.dbds.utils.validation.Validate;
import com.sat.dbds.vcs.dbconnection.DBconnection;
import com.sat.dbds.vcs.login.pagefactory.LoginPagefactory;
import com.sat.dbds.vcs.login.pageobject.VCSPageConstants;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.openqa.selenium.By.xpath;

// TODO: Auto-generated Javadoc


/**
 * The Class LoginStepDef.
 */
public class LoginStepDef implements VCSPageConstants{

    /**
     * The loginpf.
     */
    public LoginPagefactory loginpf;
	


    /** The commonpf. */
    //public CommonStepDef commonpf;

    /**
     * Instantiates a new login step def.
     */
    public LoginStepDef() {

        /** The loginpf. */
        

        /** The commonpf. */
      

    	SeleniumUtilities.openBrowser();
        loginpf = PageFactory.initElements(SeleniumUtilities.getDriver(), LoginPagefactory.class);
        


        
    }

    /**
     * Loginbuttonstatus.
     *
     * @param status the status
     */
    public void Loginbuttonstatus(String status) {
        LogHandler.info("savebuttonstatus(String status)");
        try {
            if (SeleniumUtilities.isElementDisplayed(loginpf.WE_PRIME_SUBMIT_BUTTON_ID)) {
                boolean b = Boolean.parseBoolean(loginpf.WE_PRIME_SUBMIT_BUTTON_ID.getAttribute("aria-disabled"));
                if ("Enabled".equals(status)) {

                    Assert.assertTrue("Save button is not enabled" + loginpf.WE_PRIME_SUBMIT_BUTTON_ID.getAttribute("aria-disabled"), !b);
                }
                if ("Disabled".equals(status)) {
                    Assert.assertTrue("Save button is not disabled" + loginpf.WE_PRIME_SUBMIT_BUTTON_ID.getAttribute("aria-disabled"), b);
                }
            } else
                Assert.assertTrue("SAVE BUTTON is not visble ", false);

        } catch (WebDriverException | NullPointerException e) {
            LogHandler.warn("Page Object WE_ID_SAVE_BTN mismatch");
            Assert.assertTrue("Page Object WE_ID_SAVE_BTN mismatch", false);
        }
    }


    /**
     * Gets the color.
     *
     * @return the color
     */
    public void getColor() {
        //driver.findElement(By.cssSelector("Your css selector")).getAttribute("name")
        //String data = WE_Color_Css1.getAttribute("border");
        String data = loginpf.WE_Color_Css1.getCssValue("border-right-color");
       
        String[] hexValue = data.replace("rgba(", "").replace(")", "").split(",");

        int hexValue1 = Integer.parseInt(hexValue[0]);
        hexValue[1] = hexValue[1].trim();
        int hexValue2 = Integer.parseInt(hexValue[1]);
        hexValue[2] = hexValue[2].trim();
        int hexValue3 = Integer.parseInt(hexValue[2]);

        String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);

        //String data1 = WE_Color_Css1.getCssValue("border");
        //System.out.println(WE_Color_Css1.findElement(By.cssSelector("border")));
        System.out.println("value" + data);
        System.out.println("conversion" + actualColor);
        Assert.assertEquals("#ec2300", actualColor);

    }

    /**
     * Gets the color1.
     *
     * @return the color1
     */
    public void getColor1() {
        //driver.findElement(By.cssSelector("Your css selector")).getAttribute("name")
        //String data = WE_Color_Css1.getAttribute("border-right-color");
        String data = loginpf.WE_Color_CSS_Warning.getCssValue("border-right-color");
        String[] hexValue = data.replace("rgba(", "").replace(")", "").split(",");

        int hexValue1 = Integer.parseInt(hexValue[0]);
        hexValue[1] = hexValue[1].trim();
        int hexValue2 = Integer.parseInt(hexValue[1]);
        hexValue[2] = hexValue[2].trim();
        int hexValue3 = Integer.parseInt(hexValue[2]);

        String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);

        //String data1 = WE_Color_Css1.getCssValue("border");
        //System.out.println(WE_Color_Css1.findElement(By.cssSelector("border")));
        System.out.println("value" + data);
        System.out.println("conversion" + actualColor);
        Assert.assertEquals("#ffcd00", actualColor);

    }


    /**
     * Start scenario
     * <pre>
     * <b> Gherkin </b>
     *   <code> @Before(order = 1)</code>
     * </pre>.
     *
     * @param by the by
     * @param loc the loc
     * @throws IOException Signals that an I/O exception has occurred.
     */

    void DownloadImage(By by, String loc) throws IOException {

        WebElement Image = SeleniumUtilities.getDriver().findElement(by);
        File screen = ((TakesScreenshot) SeleniumUtilities.getDriver()).getScreenshotAs(OutputType.FILE);
        int width = Image.getSize().getWidth();
        int height = Image.getSize().getHeight();
        BufferedImage img = ImageIO.read(screen);
        BufferedImage dest = img.getSubimage(Image.getLocation().getX(), Image.getLocation().getY(), width, height);
        ImageIO.write(dest, "png", screen);
        File file = new File(loc);
        FileUtils.copyFile(screen, file);

    }

    /**
     * This method will try to match images and conclude if
     * the are identical.
     *
     * @param sourceImage the source image
     * @param targetImage the target image
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    boolean doImagesMatch(String sourceImage,
                          String targetImage) throws IOException {

        LogHandler.info("Source image is:" + sourceImage);
        LogHandler.info("Target image is:" + targetImage);

        File fileInput = new File(sourceImage);
        File fileOutPut = new File(targetImage);

        BufferedImage bufFileInput = ImageIO.read(fileInput);
        DataBuffer dataFileInput = bufFileInput.getData().getDataBuffer();
        int sizeFileInput = dataFileInput.getSize();

        BufferedImage bufFileOutPut = ImageIO.read(fileOutPut);
        DataBuffer dataFileOutPut = bufFileOutPut.getData().getDataBuffer();
        int sizeFileOutPut = dataFileOutPut.getSize();

        boolean matchFlag = true;
        if (sizeFileInput == sizeFileOutPut) {
            for (int j = 0; j < sizeFileInput; j++) {
                if (dataFileInput.getElem(j) != dataFileOutPut.getElem(j)) {
                    matchFlag = false;
                    break;
                }
            }
        } else
            matchFlag = false;

        return matchFlag;
    }


    /**
     * Navigate_to_ vcs.
     * <pre>
     * <b>Gherkin</b>
     *    <code>And Navigate to VCS "url"</code>
     * </pre>
     *
     * @param url the url
     */


    @Given("^Navigate to VCS \"(.*?)\"$")
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
            if (System.getProperty("setupissue.capture") != null)
            {
            	if (System.getProperty("setupissue.capture").equals("yes"))
            	  Assert.assertTrue("URL:" + url + " is not reachable", false);
            }
            else
            {
            try {

                Runtime.getRuntime().exec("taskkill /IM firefox.exe /t /F");
                //Runtime.getRuntime().exec("taskkill /IM java.exe /t /F");


            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            }

        }

    }

    /**
     * Enter_user_name.
     * <pre>
     * <b>Gherkin</b>
     *    <code>And Enter user name "username"</code>
     * </pre>
     *
     * @param username the username
     */
    @Given("^Enter user name \"(.*?)\"$")
    public void enter_user_name(String username) {

        if (username.startsWith("<<") && username.endsWith(">>")) {
            //username = commonpf.read_system_variable(username);
            username = Validate.readsystemvariable(username);

        }


        Hooks.scenario.write(username);
        LogHandler.info("SERVERHOST" + username);
        try {
            LogHandler.info("void enter_user_name(String username)" + username);
            SeleniumUtilities.type(loginpf.WE_PRIME_USER_NAME_FIELD_ID, username);
            
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
    @Given("^Enter password \"(.*?)\"$")
    public void enter_password(String password) {
        LogHandler.info("enter_password(String password)" + password);
        if (password.startsWith("<<") && password.endsWith(">>")) {
           
            password = Validate.readsystemvariable(password);

        }


        Hooks.scenario.write(password);
        LogHandler.info("SERVERHOST" + password);
        try {
           
            SeleniumUtilities.type(loginpf.WE_PRIME_USER_PASSWORD_FIELD_ID, password);
        } catch (WebDriverException e) {
            LogHandler.warn("Page Object PRIME_USER_PASSWORD_FIELD_ID  mismatch");
            Assert.assertTrue("Page Object PRIME_USER_PASSWORD_FIELD_ID mismatch", false);
        }


    }

    /**
     * Click_on_button.
     * <pre>
     * <b>Gherkin</b>
     *    <code>And Click on "login" button</code>
     * </pre>
     *
     * @param arg1 the arg1
     */
    @Given("^Click on \"(.*?)\" button$")
    public void click_on_button(String arg1) {

        try {
           
            SeleniumUtilities.click(loginpf.WE_PRIME_SUBMIT_BUTTON_ID);
            LogHandler.info("Clicked "+arg1+ "button");
        } 
        catch (TimeoutException e)
		{
			Assert.assertTrue("Page timed out as button " +arg1+" not found", false);
		}
        catch (WebDriverException e) {
            LogHandler.warn("Page Object PRIME_SUBMIT_BUTTON_ID  mismatch");
            Assert.assertTrue("Page Object PRIME_SUBMIT_BUTTON_ID  mismatch", false);
        }

    }

    /**
     * Validate_settings_dropdown.
     */
    @Given("^Validate VCS Settings Dropdown Values-Log Out$")
    public void validate_settings_dropdown() {

        try {
            Assert.assertTrue("Log out is unavailable as actual content is "+loginpf.WE_Login_Settings_Dropdown_logout_Text.getText(),
                    loginpf.WE_Login_Settings_Dropdown_logout_Text.getText().equals("Log out"));
            LogHandler.info("");
            System.out.println(" login validate");


        } catch (WebDriverException e) {
            LogHandler.warn("Text maimatch ");

        }
    }

    /**
     * Validate_settings_help.
     */
    @Given("^Validate VCS Settings Dropdown Value-Help$")
    public void validate_settings_help() {

        try {
            Assert.assertTrue("Help is unavailable",
                    loginpf.WE_Login_Settings_Help_Text.getText().equals("Help"));
            LogHandler.info("");
            


        } catch (WebDriverException e) {
            LogHandler.warn("Help Text maimatch ");

        }
    }

    /**
     * Validate_settings_help_content.
     */
    @Given("^Validate VCS Settings Dropdown-Help-Content$")
    public void validate_settings_help_content() {

        try {
            Assert.assertTrue("Help_Content is unavailable",
                    loginpf.WE_Login_Settings_Help_Content_text.getText().equals("Help Content"));
            LogHandler.info("");
            
            System.out.println(" Help Content  validate");


        } catch (WebDriverException e) {
            LogHandler.warn("Help Content Text maimatch ");

        }
    }


    /**
     * Validate_ vc s_ settings_ dropdown_ logged_in_as.
     *
     * @param username the username
     */
    @Given("^Validate VCS_Settings_Dropdown_Logged_in as \"(.*?)\"$")
    public void validate_VCS_Settings_Dropdown_Logged_in_as(String username) {
        String data = loginpf.WE_Logged_User.getText();
        String[] data1 = data.split("\\s+");
        String data2 = data1[3];
        System.out.println(" data to be compared " + data2);

        if (username.startsWith("<<") && username.endsWith(">>")) {

            username = Validate.readsystemvariable(username);

        }


        Assert.assertTrue("the two strings are equal ", username.equals(data2));


        LogHandler.warn("Help Content Text maimatch ");

    }


    /**
     * Excute_the_ database_query.
     *
     * @param query the query
     * @throws Throwable the throwable
     */
    @Given("^Excute the Database query \"(.*?)\"$")
    public void excute_the_Database_query(String query) throws Throwable {
        query = query.replace("<USERNAME>", System.getProperty("vcs.login.username"));
        DBconnection dBconnection = new DBconnection();

        for (HashMap<String, String> row : dBconnection.returnTableDump(dBconnection.dbm, query)) {
            System.out.println("\t" + row);
            Assert.assertEquals("The user name does not match!",
                    System.getProperty("vcs.login.username"),
                    row.get("USERNAME"));

        }

    }


    /**
     * Validate_the_ home_ screen_ header.
     */
    @Given("^Validate the Home Screen Header$")
    public void validate_the_Home_Screen_Header() {
       

        Assert.assertTrue(" header mismatch ", loginpf.WE_Header_ID.isDisplayed());


        LogHandler.warn("Header  maimatch " + loginpf.WE_Header_ID.isDisplayed());


    }

    /**
     * Validate_the_ logged_in_user_name.
     */
    @Given("^Validate the Logged in user name$")
    public void validate_the_Logged_in_user_name() {
        String data = loginpf.WE_Login_Header_UserName_text.getText();
        String data2 = System.getProperty("vcs.login.username");
        Assert.assertTrue("Login Names are different",
                loginpf.WE_Login_Header_UserName_text.getText().equals(data2));


    }

    /**
     * Click_on_ log_out.
     */
    @Given("^Click on Log-out$")
    public void click_on_Log_out() {


        SeleniumUtilities.click(loginpf.WE_Login_Settings_Dropdown_logout_Text);
    }
    
    /**
     * Click_on.
     *
     * @param arg1 the arg1
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws ClassNotFoundException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    @Given("^Click on \"(.*?)\"$")
    public void click_on(String arg1) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
    
    {
    	switch(arg1) {
    		case "Log-out":
    			SeleniumUtilities.click(loginpf.WE_Login_Settings_Dropdown_logout_Text);
    			break;
    		case "Pin":
    			
    			SeleniumUtilities.click(loginpf.Pin_WE_Text);
    			break;
    			
    		case "UnPin":
    			
    			SeleniumUtilities.click(loginpf.Pin_WE_Text);
    			break;
    		case "Favorite":
    			
    			SeleniumUtilities.findElement(Identifier.XPATH,String.format(Favorite_icon,"Favorite")).click();
    			break;
    		case "Remove Favorite":
    			
    			SeleniumUtilities.findElement(Identifier.XPATH,String.format(Favorite_icon,"Remove Favorite")).click();
    			break;
    		case "Favorite_Toggle":
    			
    			SeleniumUtilities.click(loginpf.Favourites_WE);
    			break;
    		
    		case "Default":
                LogHandler
                        .warn("Data Mismatch ");
                Assert.assertTrue(
                        "Data Mismatch",
                        false);
                break;
    	}
    
   			
        
    }


    /**
     * Validate_ log_out_ screen.
     */
 
    
    @Then("^Validate Log-out Screen Message as \"(.*?)\"$")
    public void validate_Log_out_Screen_Message_as(String arg1)  {
    	String data1= loginpf.WE_LoggedOut_Message.getText();
    	
    	
    	Assert.assertTrue("Login Names are different",
                loginpf.WE_LoggedOut_Message.getText().equals(arg1));
    	
    
    }

    /**
     * Validate_ log_out_ screen_ message.
     */
    @Given("^Validate Log-out Screen Message$")
    public void validate_Log_out_Screen_Message() {
    	String Message="You have been successfully logged out.";
    	
        
        Assert.assertTrue("Login Names are different",
                loginpf.WE_LoggedOut_Message.getText().equals(Message));


        
    }

    /**
     * Validate_ home_ page_is_displayed.
     */
    @Given("^Validate Home Page is displayed\\.$")
    public void validate_Home_Page_is_displayed() {
        // Home Page Header Validate
        Assert.assertTrue(" header mismatch ", loginpf.WE_Main_Page_Validate.isDisplayed());


        LogHandler.warn("Header  maimatch " + loginpf.WE_Main_Page_Validate.isDisplayed());

    }


    /**
     * Verify_the_below_widgets_details.
     * <pre>
     * <b>Gherkin</b>
     *    <code>And verify the below widgets details
     * |  Widget     | Status  |
     * |Save Button  | Enabled |
     * |Reset Button | Enabled |</code>
     * </pre>
     *
     * @param widget the widget
     */

    @Given("^Verify Button Status$")
    public void verify_Button_Status(DataTable widget) {
        List<Map<String, String>> widgetlst = widget.asMaps(String.class,
                String.class);
        for (Map<String, String> widgetvalue : widgetlst) {
            String wname = widgetvalue.get("Widget");
            System.out.println("inside map verification");
            Loginbuttonstatus(widgetvalue.get("Status"));
        }
    }


    /**
     * Clear_the_ username_ password_text_box.
     *
     * @param username the username
     */
    @Given("^Clear the Username/Password text box \"(.*?)\"$")
    public void clear_the_Username_Password_text_box(String username) {
       

        if (username.startsWith("<<") && username.endsWith(">>")) {
          
            username = Validate.readsystemvariable(username);

        }


        Hooks.scenario.write(username);
        LogHandler.info("SERVERHOST" + username);
        try {
            LogHandler.info("void enter_user_name(String username)" + username);
            
        } catch (WebDriverException e) {
            LogHandler.warn("Page Object PRIME_USER_NAME_FIELD_ID  mismatch");
            Assert.assertTrue("Page Object PRIME_USER_NAME_FIELD_ID mismatch", false);
        }


    }

    /**
     * Validate_the_ page_ title_as.
     *
     * @param arg1 the arg1
     */
    @Given("^Validate the Page Title as \"(.*?)\"$")
    public void validate_the_Page_Title_as(String arg1) {
       

        Assert.assertTrue(" header mismatch ", loginpf.WE_Login_Console_Title.isDisplayed());


        LogHandler.warn("Header  maimatch " + loginpf.WE_Login_Console_Title.isDisplayed());


    }

    /**
     * Validate_the_mask_of_ field.
     *
     * @param arg1 the arg1
     */
    @Given("^Validate the mask of \"(.*?)\" Field$")
    public void validate_the_mask_of_Field(String arg1) {

        System.out.println(arg1);
        switch (arg1) {
            case "Username":
                String data = loginpf.WE_User_Name_Mask_text.getText();
                Assert.assertTrue("Login Names are different",
                        loginpf.WE_User_Name_Mask_text.getText().equals(arg1));
                break;
            case " Passowrd":
               
                Assert.assertTrue("Login Names are different", loginpf.WE_Passowrd_Mask_text.getText().equals(arg1));
                break;
            case "Default":
                LogHandler
                        .warn("Data Mismatch ");
                Assert.assertTrue(
                        "Data Mismatch",
                        false);
                break;


        }


    }


    /**
     * Verify_ login_ message.
     *
     * @param value the value
     * @param message the message
     */
    @Given("^Verify Login \"(.*?)\" Message  \"(.*?)\"$")
    public void verify_Login_Message(String value, String message) {

        System.out.println(value);

        //getColor();


        switch (value) {
            case "Error":

                LogHandler.warn("Header  maimatch " + loginpf.WE_Wrong_Passowrd_Warning_text.getText());
                getColor1();
                Assert.assertTrue("Login Names are different", loginpf.WE_Wrong_Passowrd_Warning_text.getText().equals(message));


                break;
            case "Locked":
                LogHandler.warn("Header  maimatch " + loginpf.WE_Wrong_Passowrd_Warning_text.getText());
                getColor();
                Assert.assertTrue(" header mismatch ", loginpf.WE_Wrong_Passowrd_Locked_text.getText().equals(message));

                break;
            case "Default":
                Assert.assertTrue("Data Mismatch", false);
                break;


        }


    }
    
    /**
     * Verify_ login_ message.
     *
     * @param value the value
     */
    @Given("^Verify Login \"(.*?)\" Message\\.$")
    public void verify_Login_Message(String value) {
    	 switch (value) {
         case "Error":
        	 String error_message="Invalid Username or Password. Please try again. More than three consequent wrong attempts will lock the user account.";
             LogHandler.warn("Header  maimatch " + loginpf.WE_Wrong_Passowrd_Warning_text.getText());
             getColor1();
             Assert.assertTrue("Login Names are different", loginpf.WE_Wrong_Passowrd_Warning_text.getText().equals(error_message));


             break;
         case "Locked":
        	 String locked_message="User is locked due to three consequent wrong attempts. Please try back again in 10 minutes or contact system administrator";
             LogHandler.warn("Header  maimatch " + loginpf.WE_Wrong_Passowrd_Warning_text.getText());
             getColor();
             Assert.assertTrue(" header mismatch ", loginpf.WE_Wrong_Passowrd_Locked_text.getText().equals(locked_message));

             break;
         case "Default":
             Assert.assertTrue("Data Mismatch", false);
             break;


     }


       
       
    }



    /**
     * I_assert_if_the_breadcrumb_displayed_is_by_comparing_the_image.
     *
     * @param breadCrumb the bread crumb
     * @param imageName the image name
     * @throws Throwable the throwable
     */
    @And("^I assert if the breadcrumb displayed is \"(.*?)\" by comparing the image \"(.*?)\"$")
    public void i_assert_if_the_breadcrumb_displayed_is_by_comparing_the_image(String breadCrumb, String imageName) throws Throwable {
        
        if (imageName.equals("homeicon_original.png")) {
            imageName = "./src/it/resources/" + "homeicon_original.png";
        }
        DownloadImage(xpath(loginpf.VCS_HOME_ICON_XPATH), "./src/it/resources/homeicon_original1.png");


        doImagesMatch(imageName, "./src/it/resources/homeicon_original1.png");


      
        try {
            File file = new File("./src/it/resources/homeicon_original1.png");

            if (file.delete()) {
                LogHandler.info(file.getName() + " is deleted!");
            } else {
                LogHandler.info("Delete operation has failed!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to delete the temp file \"./src/it/resources/homeicon_original1.png\"");
        }
    }


    /**
     * I_verify_the_by_comparing_the_image.
     *
     * @param breadCrumb the bread crumb
     * @param imageName the image name
     * @throws Throwable the throwable
     */
    @And("^I verify the \"(.*?)\" by comparing the image \"(.*?)\"$")
    public void i_verify_the_by_comparing_the_image(String breadCrumb, String imageName) throws Throwable {

        if (imageName.equals("separatoricon_original.png")) {
            imageName = "./src/it/resources/" + "separatoricon_original.png";
        }
        DownloadImage(xpath(loginpf.VCS_SEPARATOR_ICON), "./src/it/resources/separatoricon_original1.png");


        doImagesMatch(imageName, "./src/it/resources/separatoricon_original1.png");





        /*Delete the temp file*/
        try {
            File file = new File("./src/it/resources/separatoricon_original1.png");

            if (file.delete()) {
                LogHandler.info(file.getName() + " is deleted!");
            } else {
                LogHandler.info("Delete operation has failed!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to delete the temp file \"./src/it/resources/separatoricon_original1.png\"");
        }

    }


    /**
     * I_verify_the_by_star_icon.
     *
     * @param breadCrumb the bread crumb
     * @param imageName the image name
     * @throws Throwable the throwable
     */
    @Then("^I verify the \"(.*?)\" by star icon \"(.*?)\"$")
    public void i_verify_the_by_star_icon(String breadCrumb, String imageName) throws Throwable {
        if (imageName.equals("favouriteicon_original.png")) {
            imageName = "./src/it/resources/" + "favouriteicon_original.png";
        }
        DownloadImage(xpath(loginpf.VCS_SEPARATOR_ICON), "./src/it/resources/favouriteicon_original1.png");


        doImagesMatch(imageName, "./src/it/resources/favouriteicon_original1.png");





        /*Delete the temp file*/
        try {
            File file = new File("./src/it/resources/favouriteicon_original1.png");
            
            if (file.delete()) {
                LogHandler.info(file.getName() + " is deleted!");
            } else {
                LogHandler.info("Delete operation has failed!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to delete the temp file \"./src/it/resources/favouriteicon_original1.png\"");
        }

    }


    /**
     * I_verify_the_text.
     *
     * @param arg1 the arg1
     * @throws Exception the exception
     */
    @And("^I verify the \"(.*?)\" text$")
    public void i_verify_the_text(String arg1) throws Exception {

        switch (arg1) {
            case "Home":

                Assert.assertTrue("Login Names are different",
                        loginpf.HOME_TEXT_WE_text.getText().equals(arg1));
                break;
            case "Videoscape Control Suite Console":
                
                Assert.assertTrue("Login Names are different",
                        loginpf.VCS_WELCOME_MESSAGE_WE_Text.getText().equals(arg1));
                break;
            case "Default":
                System.out.println("no matches");
                break;
           
        }




    }

    /**
     * I_check.
     *
     * @param arg1 the arg1
     * @throws Throwable the throwable
     */
    @Given("^I check \"(.*?)\"$")
    public void i_check(String arg1) throws Throwable {
        // Validate the CISCO Logo , Color and Position of the Logo
        String imageName = "./src/it/resources/" + "ciscologo.png";
        DownloadImage(xpath(loginpf.VCS_HOME_CISCO_LOGO_XPATH), "./src/it/resources/ciscologo1.png");
        doImagesMatch(imageName, "./src/it/resources/ciscologo1.png");

        /*Delete the temp file*/
        try {
            File file = new File("./src/it/resources/ciscologo1.png");

            if (file.delete()) {
                LogHandler.info(file.getName() + " is deleted!");
            } else {
                LogHandler.info("Delete operation has failed!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception("Unable to delete the temp file \"./src/it/resources/ciscologo1.png\"");
        }


    }


    /**
     * I_ verrify.
     *
     * @param arg1 the arg1
     * @throws Throwable the throwable
     */
    @Then("^I Verrify \"(.*?)\"$")
    public void i_Verrify(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // Validate the CISCO Logo , Color and Position of the Logo
        String imageName = "./src/it/resources/" + "MainMenu.png";
        DownloadImage(xpath(loginpf.MAIN_MENU_XPATH), "./src/it/resources/MainMenu1.png");

        doImagesMatch(imageName, "./src/it/resources/MainMenu1.png");

        /*Delete the temp file*/
        try {
            File file = new File("./src/it/resources/MainMenu1.png");

            if (file.delete()) {
                LogHandler.info(file.getName() + " is deleted!");
            } else {
                LogHandler.info("Delete operation has failed!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception("Unable to delete the temp file \"./src/it/resources/MainMenu1.png\"");
        }
    }


    /**
     * I_validate_in_main_menu.
     *
     * @param arg1 the arg1
     */
    @Then("^I validate \"(.*?)\" in main menu$")
    public void i_validate_in_main_menu(String arg1)  {
        // Global Search Validation

        Assert.assertTrue(" header mismatch ", loginpf.GLOBAL_SEARCH_ICON_WE.isDisplayed());


        LogHandler.warn("Header  maimatch " + loginpf.GLOBAL_SEARCH_ICON_WE.isDisplayed());


    }
    
    
    /**
     * Click_ vcs_ settings_ help_ content.
     */
    @And("^Click VCS Settings-Help-Content$")
    public void click_VCS_Settings_Help_Content()  {
    	
    	
    	try {
           
    		SeleniumUtilities.click(loginpf.WE_Login_Settings_Help_Content_text);
    		
        } catch (WebDriverException e) {
            LogHandler.warn("Help_Content_Button_Mismatch");
            Assert.assertTrue("Help_Content_Button_Mismatch", false);
        }
     
       
    }
    
    
    /**
     * Focus_ help_ window.
     *
     * @throws NoSuchMethodException the no such method exception
     * @throws SecurityException the security exception
     * @throws ClassNotFoundException the class not found exception
     * @throws IllegalAccessException the illegal access exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws InvocationTargetException the invocation target exception
     */
    @Given("^Focus Help Window$")
    public void focus_Help_Window() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
    	
    	
    	String parentHandle=SeleniumUtilities.getDriver().getWindowHandle();
    	SeleniumUtilities.click(loginpf.WE_Login_Settings_Help_Content_text);
    	SeleniumUtilities.wait(3);
    	
    	Set<String> handles =  SeleniumUtilities.getDriver().getWindowHandles();
    	
    	
    	System.out.println("getwindowhandles"+ handles);
    	   for(String windowHandle  : handles)
    	       { System.out.println("parenthandle"+ parentHandle);
    	       if(!windowHandle.equals(parentHandle))
    	          {
    	    	   System.out.println("windowhandle"+ windowHandle);
    	    	   SeleniumUtilities.getDriver().switchTo().window(windowHandle);
    	    	   
    	    	   
    	    	   SeleniumUtilities.click(loginpf.LINK_WE);
    	    	   SeleniumUtilities.waitForElement(SeleniumUtilities.findElement(Identifier.XPATH,String.format(LINK,"LINK")));
    	    	   
    	    	       	    	      	    	   
    	    	   
    	    	   String dtata1=SeleniumUtilities.findElement(Identifier.XPATH,String.format(LINK,"LINK")).getText();
    	    	   
    	    	   System.out.println("dtata1"+ dtata1);
    	    	   
    	    	   
    	    	   SeleniumUtilities.wait(3);
    	    	   
    	    	   
    	    	   
    	    	   SeleniumUtilities.getDriver().close(); //closing child window
    	    	   SeleniumUtilities.getDriver().switchTo().window(parentHandle); //cntrl to parent window
    	          }
    	       }
    }
    
    
    
    
    
    /**
     * Verify_position_of_ toggle_ menu_as.
     *
     * @param expected_position the expected_position
     */
    @Given("^Verify position of Toggle Menu as \"(.*?)\"$")
    public void verify_position_of_Toggle_Menu_as(String expected_position)  {

		LogHandler.info("verify_position_of_toggle_menu(String position)");
		String actual_position;
		try {
			actual_position = SeleniumUtilities.returnPositionDirection(loginpf.Toggle_Menu_Pane);
			if(!(actual_position.equals(expected_position)))
				Assert.assertTrue("Position mismatch for Password Policy title"+ actual_position +"not equals"+ expected_position,false);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue("Exception in finding WebElement Toggle Menu",false);
			
		}
    }
    
    
    /**
     * Verify_ navigation_ button.
     */
    @Given("^Verify Navigation Button$")
    public void verify_Navigation_Button()  {
    	/* Navigation button is disabled from click */
    	
    	String data=loginpf.Navigation_WE.getAttribute("aria-disabled");
        System.out.println(" the data pressed is "+ data);
        String data_before=loginpf.Index_WE.getAttribute("aria-disabled");
      	 
        System.out.println("the index is "+ data_before);
     
        
        SeleniumUtilities.click(loginpf.Search_Text_WE);
        loginpf.Search_Text_WE.sendKeys("user"); 
        SeleniumUtilities.wait(4);
        String data2=loginpf.Index_WE.getAttribute("aria-disabled");
   	 
        System.out.println("the index is "+ data2);
        SeleniumUtilities.wait(4);
        SeleniumUtilities.click(loginpf.Navigation_WE);
        
    
       
    }
    
    
    
    /**
     * Validate_for_ console_ admin_ menu.
     *
     * @throws NoSuchMethodException the no such method exception
     * @throws SecurityException the security exception
     * @throws ClassNotFoundException the class not found exception
     * @throws IllegalAccessException the illegal access exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws InvocationTargetException the invocation target exception
     */
    @Given("^Validate for Console Admin Menu$")
    public void validate_for_Console_Admin_Menu() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
       
       
    	SeleniumUtilities.findElement(Identifier.XPATH,String.format(Console_Admin,"Console Admin")).click();
    	
     
       
       SeleniumUtilities.findElement(Identifier.XPATH,String.format(SSO_Config,"Users, Roles & AAA","User Administration")).isDisplayed();
       SeleniumUtilities.findElement(Identifier.XPATH,String.format(SSO_Config,"SSO Configuration","User Administration")).isDisplayed();
       SeleniumUtilities.findElement(Identifier.XPATH,String.format(SSO_Config,"Audit List","Audit Log")).isDisplayed();
       SeleniumUtilities.findElement(Identifier.XPATH,String.format(SSO_Config,"Audit Setting","Audit Log")).isDisplayed();
       
       
       SeleniumUtilities.findElement(Identifier.XPATH,String.format(SSO_Config,"Configuration","Service Directory")).isDisplayed();
       SeleniumUtilities.findElement(Identifier.XPATH,String.format(SSO_Config,"Logging","System")).isDisplayed();
       SeleniumUtilities.findElement(Identifier.XPATH,String.format(SSO_Config,"Settings","System")).isDisplayed();
       
       
      
    }
    
    /**
     * Validate_ position.
     *
     * @param Data_Position the data_ position
     */
    @Given("^Validate \"(.*?)\" Position$")
    public void validate_Position(String Data_Position) {
    	 
    	 
    	switch (Data_Position) {
        case "Log out":
        	
            
            Assert.assertTrue("Login Names are different",
            		
                    loginpf.Logout_Position_WE.getText().equals(Data_Position));
            break;
        case "Help Content":
           
            Assert.assertTrue("Login Names are different", loginpf.Help_Content_Position_WE.getText().equals(Data_Position));
            break;
        case "Default":
            LogHandler.warn("Data Mismatch ");
            Assert.assertTrue( "Data Mismatch",false);
                  
            break;


    }
    	
    }
    
    /**
     * Validate_ toggle_ screen.
     */
    @Given("^Validate Toggle Screen$")
    public void validate_Toggle_Screen()  {
        
    }  
    
    
    /**
     * Validate_the_ pin_ function.
     *
     * @throws Throwable the throwable
     */
    @Given("^Validate the Pin Function$")
    public void validate_the_Pin_Function() throws Throwable {
    	
    	System.out.println(" Pin Content Validate " + loginpf.Pin_WE_Text.getAttribute("title"));
    	
    	 try {
             Assert.assertTrue("Pin is not disabled ",
            		 loginpf.Pin_WE_Text.getAttribute("title").equals("Pin"));
             LogHandler.info("");
             
             


         } catch (WebDriverException e) {
             LogHandler.warn("Pin is not disabled  ");

         }
    	
    	
     
    }
    
    
    /**
     * Validate_the_button.
     *
     * @param Text equals for Pin, UnPin
     */
    @Given("^Validate the \"(.*?)\" button$")
    public void validate_the_button(String arg1) 
    {
    	switch(arg1){
    	case "Pin":
    		 Assert.assertTrue("Pin is not disabled ",
            		 loginpf.Pin_WE_Text.getAttribute("title").equals("Pin"));
    		 break;
    	case "UnPin":
    		Assert.assertTrue("Pin is not disabled ",
           		 loginpf.Pin_WE_Text.getAttribute("title").equals("UnPin"));
   		 break;
   		 
    	case "Default":
    		 LogHandler
             .warn("Data Mismatch ");
     Assert.assertTrue(
             "Data Mismatch",
             false);
     break;
    		
    		
    	}
    		
   	
    }
    
    
    
    /**
     * Validate_ favorite_ screen.
     *
     * @param arg1 the arg1
     * @throws NoSuchMethodException the no such method exception
     * @throws SecurityException the security exception
     * @throws ClassNotFoundException the class not found exception
     * @throws IllegalAccessException the illegal access exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws InvocationTargetException the invocation target exception
     */
    @Given("^Validate Favorite Screen \"(.*?)\"$")
    public void validate_Favorite_Screen(String arg1) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
    	System.out.println("data from " + arg1);
    	
    	 try {
    		 SeleniumUtilities.findElement(Identifier.XPATH,String.format(Favorite_Validate,"#pageId=sso_config_page")).isDisplayed();
    		 SeleniumUtilities.isElementDisplayed(loginpf.Favorite_Remove_WE);
    		    
    	
    	 }
    	
    
    
    catch (WebDriverException e) {
        LogHandler.warn("Favorite is not working");
    } 
    
    	
        
    }
    
    		   
    	
}

    	
    	
    			
        
   
















