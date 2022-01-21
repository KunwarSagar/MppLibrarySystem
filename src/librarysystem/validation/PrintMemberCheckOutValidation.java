package librarysystem.validation;

import librarysystem.guiElements.checkOut.OverDuePanel;
import librarysystem.guiElements.checkOut.PrintMemberCheckOut;
import exceptions.*;

import java.awt.*;

public class PrintMemberCheckOutValidation implements Validation{

    private PrintMemberCheckOut printMemberCheckOut;

    @Override
    public void checkValidation(Component ob) throws ValidationException {
        printMemberCheckOut = (PrintMemberCheckOut) ob;
        checkEmptyFields();
    }

    private void checkEmptyFields() throws ValidationException {
        if(printMemberCheckOut.getprintMemberFields()[0].getText().trim().isEmpty())
            throw new ValidationException("Search field should be non-empty");
    }
}
