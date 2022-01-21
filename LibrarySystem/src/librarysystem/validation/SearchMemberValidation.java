package librarysystem.validation;

import librarysystem.LoginScreen;
import librarysystem.guiElements.member.SearchMemberPanel;
import exceptions.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SearchMemberValidation implements Validation{

    private SearchMemberPanel searchMemberPanel;

    @Override
    public void checkValidation(Component ob) throws ValidationException {
        searchMemberPanel = (SearchMemberPanel) ob;
        checkEmptyFields();
    }

    private void checkEmptyFields() throws ValidationException {
        if(searchMemberPanel.getMemberFields()[0].getText().trim().isEmpty())
            throw new ValidationException("Search field should be non-empty");
    }
}
