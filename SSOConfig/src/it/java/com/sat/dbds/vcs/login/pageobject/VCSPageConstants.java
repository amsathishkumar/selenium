/*
 * 
 */
package com.sat.dbds.vcs.login.pageobject;

// TODO: Auto-generated Javadoc
/**
 * The Interface VCSPageConstants.
 */
public interface VCSPageConstants {

    /** IDs for CMC Login Page. */	
    public static final String PRIME_USER_NAME_FIELD_ID  = "loginPage_username";
    
    /** The Constant PRIME_USER_PASSWORD_FIELD_ID. */
    public static final String PRIME_USER_PASSWORD_FIELD_ID = "loginPage_password";
    
    /** The Constant PRIME_SUBMIT_BUTTON_ID. */
    public static final String PRIME_SUBMIT_BUTTON_ID = "loginPage_LoginButton";   
  
    /** IDs for Top level VCS Prime drop down menus. */
    public static final String PRIME_TREE_MAIN_LINK_CSS_SEL="div.headerNode div.headerLeft div.toggleNode div.icon-cisco-menu";
    
    /** The Constant PRIME_CONTROL_PLANE_CSS_SEL. */
    public static final String PRIME_CONTROL_PLANE_CSS_SEL="div.dijitTitlePaneTitle div.dijitTitlePaneTitleFocus";
    
    /** The Constant PRIME_ECS_DASHBOARD_LINK_CSS_SEL. */
    public static final String PRIME_ECS_DASHBOARD_LINK_CSS_SEL="div.hasChildren div.xwtSlideMenuMenuItemText";
    
    /** The Constant PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL. */
    public static final String PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL="div.dijitTitlePaneTitle  div.dijitTitlePaneTitleFocus";
    
    /** The Constant PRIME_USERS_ROLES_AAA_LINK_CSS_SEL. */
    public static final String PRIME_USERS_ROLES_AAA_LINK_CSS_SEL ="div.hasChildren div.xwtSlideMenuMenuItemText";


    //** Automation Added **/

    String Login_Settings_Dropdown_logout_Text = "logout_text";
    String Login_Settings_Dropdown_Help_text="Help_text";
    String Login_Settings_Help_Content="HelpContent_text";

    String Logged_User =".//*[contains(@id,'Logged in as')][contains(@id,'text')]";

    String Header=".//*[@id='xwt_widget_uishell_Header_0']";

    String Login_Header_UserName=".//div[@class='dijitInline settingsNode']//*[contains(@data-dojo-attach-point,'slidePopUpMenuLogin')]";
    String LoggedOut_Message=".//*[@id='xwt_widget__SimpleMessage_0']/span";
    String Main_Page_Validate=".//*[@id='loginPage']";
    String Login_Console_Title=".//*[@id='home_page_dashboard_pageId']//*[@class='containerNode']";

    String User_Name_Mask=".//*[@id='widget_loginPage_username']//*[@class='dijitPlaceHolder dijitInputField']";
    String Passowrd_Mask=".//*[@id='widget_loginPage_password']//*[@class='xwtValidateFlexField dijitReset dijitInputField dijitInputContainer']";
    String Wrong_Passowrd_Warning=".//*[@id='xwt_widget__SimpleMessage_0']/span";
    String Wrong_Passowrd_Locked=".//*[@id='xwt_widget__SimpleMessage_0']/span";
    //String Color_CSS=".xwtLoginErrorBox.xwtLoginMessages";
    String Color_CSS=".xwtLoginMessages.xwtLoginErrorBox";
    String Color_CSS_Warning=".xwtLoginMessages.xwtLoginWarningBox ";

    //String Color_Css1=".//*[@id='loginPage']/div/div[6]/div[@class='xwtLoginMessagesArea']/div[3]";
    String Color_Css1=".xwtLoginMessagesArea";

    String VCS_HOME_ICON_XPATH = "//div[@class = 'breadCrumbHomeIcon']";
    String VCS_SEPARATOR_ICON=".//*[@id='xwt_widget_uishell_GlobalBreadcrumb_0_breadcrumb']/span[@class='xwtBreadcrumbSeparator']";
    String HOME_TEXT=".//*[@id='xwt_widget_uishell_GlobalBreadcrumb_0_breadcrumb']/span[@class='xwtBreadcrumbText xwtBreadcrumbLast']";
    String FAVOURITE_ICON_TOGGLE=".//*[@id='xwt_widget_uishell_GlobalBreadcrumb_0_breadcrumb']/div[@class='xwtNavBreadcrumbFavoriteContainer']";

    String MAIN_MENU_XPATH=".//*[@id='xwt_widget_uishell_Header_0']/div";



    String VCS_BREADCRUMB_AREA_XPATH = "//div[@class = 'breadCrumbArea']";

    String VCS_HOME_CISCO_LOGO_XPATH="//*[@class = 'dijitInline appIconNode']";

    String Title_Console=".//*[@id='xwt_widget_uishell_Header_0']/div/div[1]/div[2]/div[2]/a";
    String Logo_CSS=".appIcon";
    String VCS_WELCOME_MESSAGE=".//*[@id='xwt_widget_uishell_Header_0']//*[@class='dijitInline appTitle']";

    String GLOBAL_SEARCH_ICON=".//*[@id='xwt_widget_uishell_Search_0']";
    //String LINK=".//a";
    //String LINK="html/body/h1/text()";
    //String LINK="//title/text()";
    String LINK="//html/body/table/tbody/tr/td/a[@id='btnidx']";
    
    
    String Toggle_Menu_Pane=".//*[@id='xwt_widget_navigation_SlideMenu_0_navMenu']";
   
    String Navigation=".//*[@id='xwt_widget_navigation_SlideMenu_0_navButton']";
    
    		
    String Index=".//span[@id='xwt_widget_navigation_SlideMenu_0_indexButton']";
    
    
    String Favourites=".//span[@id='xwt_widget_navigation_SlideMenu_0_favoritesButton']";
    
    String Home_Screen=".//*[@id='xwt_widget_navigation_SlideMenu_0_controlsPanel_searchField']/div/span/ancestor::div[@class='xwtSlideMenuMenuBar']/following-sibling::div[1][@class='xwtSlideMenuContainerNode']";
    
    //String Search_Text=".//*[@class='xwtSlideMenuInputInner']";
    //String Search_Text=".//div[@class='xwtSlideMenuInputContainer']";
    String Search_Text=".//*[@id='xwt_widget_navigation_SlideMenu_0_controlsPanel_searchField_searchField']";
    String SSO_Config =".//*[@class='xwtSlideMenuMenuItemText']/*[text()='%s']//ancestor::div[contains(@id,'xwt_widget_navigation__slidemenu_MenuItem')]//*[text()='%s']";
    String Logout_Position=".//*[contains(@id,'Logged in as')][contains(@id,'text')][normalize-space(text())='Logged in as']/ancestor::tr[contains(@id,'xwt_widget_shared_menu_SlideMenuPopup_0_Logged in as')]/following-sibling::tr[contains(@id,'logout')]";
    String Help_Content_Position=".//*[@id='Help_text']/ancestor::tr[@id='Help']/following::tr[@id='HelpContent']";
    String Console_Admin=".//div[@title='%s']/span/a";
    //String Pin=".//*[@id='xwt_widget_navigation_SlideMenu_0_controlsPanel']/div[@class='xwtSlideMenuMenuControlsPanelWrapper']/div[@class='xwtSliderMenuPinIcon icon-pin']";
    String Pin=".//*[@id='xwt_widget_navigation_SlideMenu_0_controlsPanel']/div[@class='xwtSlideMenuMenuControlsPanelWrapper']/div[@role='button']";
    //String Favorite_Validate=".//*[@id='xwt_widget_navigation_SlideMenu_0_favoritesMenu']//div[@class='xwtSlideMenuFavoritesContainerNodeContainer']";
    String Favorite_Validate=".//*[@id='xwt_widget_navigation_SlideMenu_0_favoritesMenu']//div[@class='xwtSlideMenuFavoritesContainerNodeContainer']//div/a[@href='%s']";
    String Favorite_icon=".//*[@id='xwt_widget_uishell_GlobalBreadcrumb_0_breadcrumb']//div[@title='%s']";
    
    String Favorite_Search=".//*[@id='xwt_widget_navigation_SlideMenu_0_controlsPanel_searchField']/div/span";
    String Favorite_Remove=".//*[@id='FavoritesMenuItem_0_xwt_widget_navigation_SlideMenu_0_favoritesMenu']//div[@title='Remove']";
    
    
    String Favorite_Validate_Remove=".//*[@id='xwt_widget_navigation_SlideMenu_0_favoritesMenu']//div[@class='xwtSlideMenuFavoritesContainerNodeContainer']//div/a[@href='%s']";
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    










}
