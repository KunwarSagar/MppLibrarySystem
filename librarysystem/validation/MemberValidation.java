package librarysystem.validation;
import exceptions.*;

import librarysystem.Util;
import librarysystem.guiElements.member.MemberUI;

import javax.swing.*;
import java.awt.*;

public class MemberValidation implements Validation {

    private MemberUI memberGui;

    @Override
    public void checkValidation(Component ob) throws ValidationException {
         memberGui = (MemberUI) ob;
         checkEmptyFields();
         checkIdNumeric();
         checkZipNumeric();
         checkState();
         // idNotZipRule();
    }

    private void checkEmptyFields() throws ValidationException {

        for(JTextField field : memberGui.getMemberFields()){
            if(field.getText().isEmpty())
                throw new ValidationException("All fields must be non-empty");
        }

    }

    private void checkIdNumeric() throws ValidationException {
        String val = memberGui.getMemberFields()[3].getText();
        try {
            Integer.parseInt(val);
            //val is numeric
        } catch(NumberFormatException e) {
            throw new ValidationException("Phone Number should be numeric");
        }
    }

    private void checkZipNumeric() throws ValidationException {
        String val =  memberGui.getMemberFields()[memberGui.getMemberFields().length-1].getText().trim();
        try {
            Integer.parseInt(val);
        } catch(NumberFormatException e) {
            throw new ValidationException("Zipcode must be numeric");
        }
        if(val.length() != 5) throw new ValidationException("Zipcode must have 5 digits");
    }

    private void checkState() throws ValidationException {

        String state = memberGui.getMemberFields()[memberGui.getMemberFields().length-2].getText().trim();
        if(state.length() != 2) throw new ValidationException("State field must have two characters");
        if(!Util.isInRangeAtoZ(state.charAt(0))
                || !Util.isInRangeAtoZ(state.charAt(1))) {
            throw new ValidationException("Characters is state field must be in range A-Z");
        }
    }


}
