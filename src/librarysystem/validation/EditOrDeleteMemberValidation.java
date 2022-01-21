package librarysystem.validation;

import exceptions.LibraryMemberException;
import librarysystem.Util;
import librarysystem.guiElements.member.EditOrDeleteMember;
import librarysystem.guiElements.member.MemberUI;
import exceptions.*;

import javax.swing.*;
import java.awt.*;

public class EditOrDeleteMemberValidation implements Validation {

    private EditOrDeleteMember editOrDeleteMember;

    @Override
    public void checkValidation(Component ob) throws ValidationException {
        editOrDeleteMember = (EditOrDeleteMember) ob;
         checkEmptyFields();
        // idNumericRule();
         checkZipNumeric();
         checkState();
         // idNotZipRule();
    }


    public void nonEmptyMemberId() throws ValidationException {
        if(editOrDeleteMember.getMemberFields()[0].getText().isEmpty()) {
            throw new ValidationException("Enter Member ID and search for Member before Deleting/Editing");
        }

    }


    private void checkEmptyFields() throws ValidationException {

        for(JTextField field : editOrDeleteMember.getMemberFields()){
            if(field.getText().isEmpty())
                throw new ValidationException("All fields must be non-empty");
        }

    }

    private void idNumericRule() throws ValidationException {
        String val = editOrDeleteMember.getMemberFields()[3].getText();
        try {
            Integer.parseInt(val);
            //val is numeric
        } catch(NumberFormatException e) {
            throw new ValidationException("Phone Number should be numeric");
        }
    }

    private void checkZipNumeric() throws ValidationException {
        String val =  editOrDeleteMember.getMemberFields()[editOrDeleteMember.getMemberFields().length-1].getText().trim();
        try {
            Integer.parseInt(val);
        } catch(NumberFormatException e) {
            throw new ValidationException("Zipcode must be numeric");
        }
        if(val.length() != 5) throw new ValidationException("Zipcode must have 5 digits");
    }

    private void checkState() throws ValidationException {

        String state = editOrDeleteMember.getMemberFields()[editOrDeleteMember.getMemberFields().length-2].getText().trim();
        if(state.length() != 2) throw new ValidationException("State field must have two characters");
        if(!Util.isInRangeAtoZ(state.charAt(0))
                || !Util.isInRangeAtoZ(state.charAt(1))) {
            throw new ValidationException("Characters is state field must be in range A-Z");
        }
    }


}
