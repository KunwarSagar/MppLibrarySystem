package librarysystem.validation;

import librarysystem.guiElements.checkOut.CheckOutBookPanel;
import librarysystem.guiElements.checkOut.OverDuePanel;
import exceptions.*;

import javax.swing.*;
import java.awt.*;

public class CheckOutValidation implements Validation{

    private CheckOutBookPanel checkOutGui;

    @Override
    public void checkValidation(Component ob) throws ValidationException {
        checkOutGui = (CheckOutBookPanel) ob;
        checkEmptyFields();
    }

    private void checkEmptyFields() throws ValidationException {

        for(JTextField jTextField : checkOutGui.getCheckOutFields()){
            if(jTextField.getText().isEmpty())
                throw new ValidationException("All fields must be non-empty");
        }
    }
}
