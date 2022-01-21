package librarysystem.validation;

import librarysystem.guiElements.book.SearchBookPanel;
import librarysystem.guiElements.checkOut.OverDuePanel;
import exceptions.*;

import java.awt.*;

public class OverDueValidation implements Validation{

    private OverDuePanel overDuePanel;

    @Override
    public void checkValidation(Component ob) throws ValidationException {
        overDuePanel = (OverDuePanel) ob;
        checkEMptyFields();
    }

    private void checkEMptyFields() throws ValidationException {
        if(overDuePanel.getOverDueFields()[0].getText().trim().isEmpty())
            throw new ValidationException("Search field should be non-empty");
    }
}
