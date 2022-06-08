package project.manager;

import project.entities.enums.Roles;

import java.util.EnumSet;
import java.util.ResourceBundle;

public class Config {
    private static Config instance;
    private ResourceBundle resource;
    private static final String BUNDLE_NAME = "config";
    public static final String MAIN = "main";
    public static final String LOGIN = "login";
    public static final String RECEIPTS = "receipts";
    public static final String STORAGE = "storage";
    public static final String ADMIN = "admin";
    public static final String ERROR = "error";
    public static final String VIEWRECEIPT = "viewreceipt";
    public static final String VIEWCHECKPROD = "viewcheckprod";
    public static final String CHECKADDPROD = "checkaddprod";
    public static final String CREATEUSER = "createuser";
    public static final EnumSet<Roles> ACCESS_RECEIPTS = EnumSet.of(Roles.Cashier, Roles.Senior, Roles.Admin);
    public static final EnumSet<Roles> ACCESS_STORAGE = EnumSet.of(Roles.Merch, Roles.Admin);
    public static final EnumSet<Roles> ACCESS_ADMIN = EnumSet.of(Roles.Admin);
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
