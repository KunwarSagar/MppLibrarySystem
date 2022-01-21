package librarysystem.guiElements;

import librarysystem.*;
import librarysystem.LibrarianHomePage;

import java.awt.*;

public class UtilGui {

    private static LibrarySystemWindow[] allWindows = {
            AdministratorHomePage.INSTANCE,
            LoginScreen.INSTANCE,
            LibrarianHomePage.INSTANCE,
    };

    public static void hideAllWindows() {
        for(LibrarySystemWindow frame: allWindows) {
            frame.setVisible(false);
        }
    }

    public static final Dimension BTN_DIMENSION = new Dimension(150, 30);
    public static final Dimension PANEL_DIMENSION =new Dimension(ConstantConfig.APP_WIDTH/2 + 100 , ConstantConfig.APP_HEIGHT/2);

}
