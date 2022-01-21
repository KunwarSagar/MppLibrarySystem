package librarysystem.validation;
import librarysystem.LoginScreen;
import exceptions.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginValidation implements Validation{

    private LoginScreen loginScreen;

    @Override
    public void checkValidation(Component ob) throws ValidationException {
        loginScreen = (LoginScreen) ob;
        checkEmptyFields();
    }

    private void checkEmptyFields() throws ValidationException {
        if(loginScreen.getPasswordField().getPassword().length ==0 || loginScreen.getUserNameField().getText().trim().isEmpty())
            throw new ValidationException("All fields must be non-empty");
    }
}
