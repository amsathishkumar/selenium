/*
 * 
 */
package com.sat.dbds.vcs.menu.pageobject;

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
  //  public static final String PRIME_TREE_MAIN_LINK_CSS_SEL="div.headerNode div.headerLeft div.toggleNode div.icon-cisco-menu";
    public static final String PRIME_TREE_MAIN_LINK_CSS_SEL=".//*[@class='toggleIcon icon-cisco-menu']";
     
    /** The Constant PRIME_CONTROL_PLANE_CSS_SEL. */
    public static final String PRIME_CONTROL_PLANE_CSS_SEL="div.dijitTitlePaneTitle div.dijitTitlePaneTitleFocus";
    
    /** The Constant PRIME_ECS_DASHBOARD_LINK_CSS_SEL. */
    public static final String PRIME_ECS_DASHBOARD_LINK_CSS_SEL="div.hasChildren div.xwtSlideMenuMenuItemText";
    
    /** The Constant PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL. */
    public static final String PRIME_SYSTEM_ADMINISTRATION_PLANE_CSS_SEL="div.dijitTitlePaneTitle  div.dijitTitlePaneTitleFocus";
    
    /** The Constant PRIME_USERS_ROLES_AAA_LINK_CSS_SEL. */
    public static final String PRIME_USERS_ROLES_AAA_LINK_CSS_SEL ="div.hasChildren div.xwtSlideMenuMenuItemText";
}
