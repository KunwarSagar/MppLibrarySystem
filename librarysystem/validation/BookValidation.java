package librarysystem.validation;

import librarysystem.guiElements.book.BookGui;
import exceptions.*;

import javax.swing.*;
import java.awt.*;

public class BookValidation implements Validation {

    private BookGui bookGui;

    @Override
    public void checkValidation(Component ob) throws ValidationException {

        bookGui= (BookGui) ob;
        checkEmptyField();
        checkMaxDays();
    }

    private void checkEmptyField() throws ValidationException {
        for(JTextField field : bookGui.getBookFields()){
            if(field.getText().isEmpty())
                throw new ValidationException("All fields must be non-empty");
        }
    }

    private void checkMaxDays() throws ValidationException{

        int maxBorrowDays = Integer.parseInt(bookGui.getBookFields()[2].getText());
        if(maxBorrowDays != 7 && maxBorrowDays != 21)
            throw new ValidationException("Maximum checkout days it either 7 or 21 according to the university rules");

    }
}
