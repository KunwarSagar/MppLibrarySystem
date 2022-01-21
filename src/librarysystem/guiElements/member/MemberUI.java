package librarysystem.guiElements.member;

import business.*;
import controllers.*;
import exceptions.LibraryMemberException;
import librarysystem.*;
import librarysystem.guiElements.UtilGui;
import librarysystem.validation.Validation;
import exceptions.ValidationException;
import librarysystem.validation.ValidationFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class MemberUI extends JPanel{

    private final String[] memeberLabels ;
    private final JTextField[] memberFields ;
    private final ControllerInterface ci = new SystemController();
    public static MemberUI INSTANCE = new MemberUI();

    private JTable myTable;
    private JPanel addMemberPanel;

    private MemberUI() {
        memeberLabels = new String[] {"ID", "First Name", "Last Name", "Phone Number", "Street", "City", "State", "Zip"};
        memberFields = new JTextField[memeberLabels.length];
        addMemberForm();
        myTable = loadDataToTable();
        myTable.setShowGrid(true);
    }

    public JTextField[] getMemberFields() {
        return memberFields;
    }
    public JPanel getAddMemberPanel() {
        return this;
    }

    private void addMemberForm() {

        JLabel panelTitle = new JLabel(" Add Member");
        panelTitle.setFont(ConstantConfig.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
        titlePanel.add(panelTitle, BorderLayout.CENTER);
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);

        JPanel memberFormPanel = createMemberForm();

        // add add button
        JButton addBMemberBtn = new JButton("Add Member");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBMemberBtn.setPreferredSize(UtilGui.BTN_DIMENSION);
        addBMemberBtn.addActionListener(new addMemberListener());
        btnPanel.add(addBMemberBtn);

        JPanel container = new JPanel(new BorderLayout());
        container.setPreferredSize(UtilGui.PANEL_DIMENSION);

        // combine
        container.add(titlePanel, BorderLayout.NORTH);
        container.add(memberFormPanel, BorderLayout.CENTER);
        container.add(btnPanel, BorderLayout.SOUTH);


        this.add(container);
    }

    public JTable getMemberList(){
        return myTable;
    }

    private JTable loadDataToTable(){

        String[] column ={"MEMBER ID","FULL NAME","PHONE NO.", "Address"};
        HashMap<String , LibraryMember> memberHashMap = ci.getMembers();
        String[][] memberData = new String[memberHashMap.size()][column.length];
        List<String> memberID = ci.allMemberIds();

        for(int i = 0 ; i < memberHashMap.size(); i++){

            LibraryMember member = memberHashMap.get(memberID.get(i));
            memberData[i][0] = member.getMemberId();
            memberData[i][1] = member.getFirstName() + " " + member.getLastName();
            memberData[i][2] = member.getTelephone();
            memberData[i][3] = member.getAddress() != null ? member.getAddress().toString() : "";
        }

        DefaultTableModel model = new DefaultTableModel(memberData, column);

        return new JTable(model);

    }
    private JPanel createMemberForm() {

        JPanel memberFormPanel = new JPanel(new GridLayout(memberFields.length, 0));
        for (int i = 0; i < memberFields.length; i++) {
            memberFormPanel.add(getElementWithLabelMember(memeberLabels[i], i));
        }
        return memberFormPanel;
    }

    private JPanel getElementWithLabelMember(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.CENTER);

        memberFields[jtextFieldIndex] = new JTextField(20);
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(memberFields[jtextFieldIndex], BorderLayout.CENTER);


        JPanel nameForm = new JPanel(new BorderLayout());
        nameForm.add(labelPanel, BorderLayout.WEST);
        nameForm.add(formPanel, BorderLayout.EAST);

        return nameForm;
    }
    private class addMemberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                // Using Factory Validation
                Validation memberRule = ValidationFactory.getValidation(MemberUI.this);
                memberRule.checkValidation(MemberUI.this);

                // check if member already exists
                if(ci.checkMemberId(memberFields[0].getText().trim()))
                    throw new LibraryMemberException("Member with Member ID = " + memberFields[0].getText().trim() + " already exists");

                Address add = ci.addAddress(memberFields[4].getText(), memberFields[5].getText().trim(), memberFields[6].getText().trim(), memberFields[7].getText().trim());
                LibraryMember member = ci.addLibraryMember(memberFields[0].getText(), memberFields[1].getText(),
                        memberFields[2].getText(), memberFields[3].getText(), add);

                // Add New instance
                ci.saveLibraryMember(member);
                new MessageBox.InnerFrame().showMessageBox("Member added successfully", "Info");
                addRowToJTable(member);
                clearFormFields();

            } catch (LibraryMemberException | ValidationException ex) {
                new MessageBox.InnerFrame().showMessageBox(ex.getMessage(), "Error");
            }
        }
    }

    private void addRowToJTable(LibraryMember member){

        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        model.insertRow(0, new  Object[]{member.getMemberId() , member.getFirstName() +" " +member.getLastName(),
                member.getTelephone(), member.getAddress().toString()});
    }

    public void clearFormFields(){
        for(JTextField field : memberFields){
            field.setText("");
        }

    }

}
