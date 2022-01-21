package librarysystem.validation;

import business.BookCopy;
import librarysystem.guiElements.book.AddBookCopyPanel;
import librarysystem.guiElements.book.BookGui;
import exceptions.*;

import javax.swing.*;
import java.awt.*;

public class BookCopyValidation implements Validation {

    private AddBookCopyPanel bookCopyPanel;

    @Override
    public void checkValidation(Component ob) throws ValidationException {

        bookCopyPanel = (AddBookCopyPanel) ob;
        checkEmptyFIeld();
        //maxDays();
    }

    private void checkEmptyFIeld() throws ValidationException {
        for(JTextField field : bookCopyPanel.getBookFields()){
            if(field.getText().isEmpty())
                throw new ValidationException("All fields must be non-empty");
        }
    }

//    private void maxDays() throws RuleException{
//
//        int maxBorrowDays = Integer.parseInt(bookCopyPanel.getBookFields()[1].getText());
//        if(maxBorrowDays <= 0)
//            throw new RuleException("Number of copies should be greater than one");
//
//    }
}
