package librarysystem.validation;

import librarysystem.LoginScreen;
import librarysystem.guiElements.book.SearchBookPanel;
import librarysystem.guiElements.member.SearchMemberPanel;
import exceptions.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SearchBookValidation implements Validation{

    private SearchBookPanel searchBookPanel;

    @Override
    public void checkValidation(Component ob) throws ValidationException {
        searchBookPanel = (SearchBookPanel) ob;
        checkEmptyFields();
    }

    private void checkEmptyFields() throws ValidationException {
        if(searchBookPanel.getBookFields()[0].getText().trim().isEmpty())
            throw new ValidationException("Search field should be non-empty");
    }
}
