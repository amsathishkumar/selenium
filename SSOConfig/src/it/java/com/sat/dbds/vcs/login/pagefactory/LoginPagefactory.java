package com.sat.dbds.vcs.login.pagefactory;
//import javafx.scene.control.TextField;
import com.sat.dbds.vcs.login.pageobject.VCSPageConstants;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPagefactory implements VCSPageConstants {
    @FindBy(id = PRIME_USER_NAME_FIELD_ID)
    public WebElement WE_PRIME_USER_NAME_FIELD_ID;

    @FindBy(id = PRIME_USER_PASSWORD_FIELD_ID)
    public WebElement WE_PRIME_USER_PASSWORD_FIELD_ID;

    @FindBy(id = PRIME_SUBMIT_BUTTON_ID)
    public WebElement WE_PRIME_SUBMIT_BUTTON_ID;

    @FindBy(id = Login_Settings_Dropdown_logout_Text)
    public WebElement WE_Login_Settings_Dropdown_logout_Text;

    @FindBy(id = Login_Settings_Dropdown_Help_text)
    public WebElement WE_Login_Settings_Help_Text;

    @FindBy(id = Login_Settings_Help_Content)
    public WebElement WE_Login_Settings_Help_Content_text;

    @FindBy(xpath = Logged_User)
    public WebElement WE_Logged_User;

    @FindBy(xpath = Header)
    public WebElement WE_Header_ID;

    @FindBy(xpath = Login_Header_UserName)
    public WebElement WE_Login_Header_UserName_text;

    @FindBy(xpath = LoggedOut_Message)
    public WebElement WE_LoggedOut_Message_text;

    @FindBy(xpath = LoggedOut_Message)
    public WebElement WE_LoggedOut_Message;

    @FindBy(xpath = Main_Page_Validate)
    public WebElement WE_Main_Page_Validate;

    @FindBy(xpath = Login_Console_Title)
    public WebElement WE_Login_Console_Title;

    @FindBy(xpath = User_Name_Mask)
    public WebElement WE_User_Name_Mask_text;

    @FindBy(xpath = Passowrd_Mask)
    public WebElement WE_Passowrd_Mask_text;

    @FindBy(xpath = Wrong_Passowrd_Warning)
    public WebElement WE_Wrong_Passowrd_Warning_text;

    @FindBy(xpath = Wrong_Passowrd_Locked)
    public WebElement WE_Wrong_Passowrd_Locked_text;


    @FindBy(css = Color_CSS)
    public WebElement WE_Color_Css1;

    @FindBy(css=Color_CSS_Warning)
    public WebElement WE_Color_CSS_Warning;

    @FindBy(xpath = VCS_HOME_ICON_XPATH)
    public WebElement VCS_HOME_ICON_XPATH_WE;

    @FindBy(xpath = VCS_SEPARATOR_ICON)
    public WebElement VCS_SEPARATOR_ICON_WE;

    @FindBy(xpath = HOME_TEXT)
    public WebElement HOME_TEXT_WE_text;

    @FindBy(xpath=VCS_WELCOME_MESSAGE)
    public WebElement VCS_WELCOME_MESSAGE_WE_Text;

   



    @FindBy(xpath = VCS_BREADCRUMB_AREA_XPATH)
    private WebElement VCS_BREADCRUMB_AREA_XPATH_WE;


    @FindBy(css=Logo_CSS)
    public WebElement Logo_CSS_WE;


    @FindBy(xpath=Title_Console)
    public WebElement Title_Console_WE;


    @FindBy(xpath=MAIN_MENU_XPATH)
    public WebElement MAIN_MENU_XPATH_WE;

    @FindBy(xpath=GLOBAL_SEARCH_ICON)
    public WebElement GLOBAL_SEARCH_ICON_WE;
    
    @FindBy(xpath=LINK)
    public WebElement LINK_WE;
    
    @FindBy(xpath=Toggle_Menu_Pane)
    public WebElement Toggle_Menu_Pane_WE;
    @FindBy(xpath=Navigation)
    public WebElement Navigation_WE;
    @FindBy(xpath=Favourites)
    public WebElement Favourites_WE;
    @FindBy(xpath=Index)
    public WebElement Index_WE;
    
    @FindBy(xpath=Home_Screen)
    public WebElement Home_Screen_WE;
    
    @FindBy(xpath=Search_Text)
    public WebElement Search_Text_WE;
    
    @FindBy(xpath=SSO_Config)
    public WebElement SSO_Config_WE;
    
    @FindBy(xpath=Logout_Position)
    public WebElement Logout_Position_WE;
    
    @FindBy(xpath=Help_Content_Position)
    public WebElement Help_Content_Position_WE;
    
    @FindBy(xpath=Pin)
    public WebElement Pin_WE_Text;
    
    @FindBy(xpath=Favorite_Remove)
    public WebElement Favorite_Remove_WE;
    
    @FindBy(xpath=Favorite_Search)
    public WebElement Favorite_Search_WE;
    
    
    
    
    
    
    		
    
    
    
    
    
    

	





    /*public void getColor() {
        //driver.findElement(By.cssSelector("Your css selector")).getAttribute("name")
        //String data = WE_Color_Css1.getAttribute("border");
        String data=WE_Color_Css1.getCssValue("border-right-color");
        String[] hexValue = data.replace("rgba(", "").replace(")", "").split(",");

        int hexValue1=Integer.parseInt(hexValue[0]);
        hexValue[1] = hexValue[1].trim();
        int hexValue2=Integer.parseInt(hexValue[1]);
        hexValue[2] = hexValue[2].trim();
        int hexValue3=Integer.parseInt(hexValue[2]);

        String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);

        //String data1 = WE_Color_Css1.getCssValue("border");
        //System.out.println(WE_Color_Css1.findElement(By.cssSelector("border")));
        System.out.println("value" + data);
        System.out.println("conversion" + actualColor);

    }*/
}





















