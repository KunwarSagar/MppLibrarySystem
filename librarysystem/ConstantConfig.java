package librarysystem;

import java.awt.*;

public class ConstantConfig {

    public static final String APP_NAME = "LIBRARY SYSTEM";
    public static final int APP_WIDTH = 1000;
    public static final int APP_HEIGHT = 700;
    public static final int DIVIDER = 250;
    public static  final Font DEFUALT_FONT = new java.awt.Font("blue", 1, 13);


    public static final String[] ALL_MENU = {

            "Home",
            "Add member",
            "Add book",
            "Add book copy",
            "Checkout book",
            "OverDue",
            "Search book",
            "Search member",
            "Print Member Checkout",
            "Update/Delete member",
            "LogOut",
    };
    public static final String[] LIBRARIAN_MENU = {
            "Home",
            "Search member",
            "Search book",
            "Checkout book",
            "OverDue",
            "Search member checkouts",
            "LogOut",
    };

    public static final String[] ADMIN_MENU = {
            "Home",
            "Add member",
            "Add book",
            "Add book copy",
            "Search member",
            "Search book",
            "Update/Delete member",
            "LogOut",
    };





}
