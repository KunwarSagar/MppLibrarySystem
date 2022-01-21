package librarysystem;

import controllers.*;
import librarysystem.guiElements.*;
import librarysystem.guiElements.member.EditOrDeleteMember;
import librarysystem.guiElements.book.AddBookCopyPanel;
import librarysystem.guiElements.book.BookGui;
import librarysystem.guiElements.book.SearchBookPanel;
import librarysystem.guiElements.member.MemberUI;
import librarysystem.guiElements.member.SearchMemberPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class AdministratorHomePage extends JFrame implements LibrarySystemWindow {

    private static final long serialVersionUID = 1L;

    public static final AdministratorHomePage INSTANCE = new AdministratorHomePage();
    ControllerInterface ci = new SystemController();

    private boolean isInitialized = false;

    JList<ListItem> linkList;
    JPanel cards;

    List<ListItem> itemList = new ArrayList<>();

    private  JPanel adminHomePagePanel;
    public JTable memberListJTable,bookListJTable;

    //Singleton class
    private AdministratorHomePage() {
        setSize(ConstantConfig.APP_WIDTH, ConstantConfig.APP_HEIGHT);
        UIController.INSTANCE.admin = this;
        memberListJTable = MemberUI.INSTANCE.getMemberList();
        bookListJTable = BookGui.INSTANCE.getBookList();
    }

    public void constructSideBarMenu(){

        for(String item : ConstantConfig.ADMIN_MENU){
            itemList.add(new ListItem(item, true));
        }
    }

    public void init() {

        // Construct sideBarMenu ListItems
        constructSideBarMenu();

        // Create sidebar
        createLinkLabels();

        // create main panels
        createMainPanels();

        // link my sidebar
        linkList.addListSelectionListener(event -> {
            String value = linkList.getSelectedValue().getItemName();
            boolean allowed = linkList.getSelectedValue().highlight();
            CardLayout cl = (CardLayout) (cards.getLayout());

            if (!allowed) {
                value = itemList.get(0).getItemName();
                linkList.setSelectedIndex(0);
            }
            cl.show(cards, value);
        });
        linkList.setBackground(new java.awt.Color(204, 204, 255));
        linkList.setVisibleRowCount(4);
        linkList.setFixedCellHeight(40);
        linkList.setSelectionForeground(Color.BLACK);


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
        splitPane.setDividerLocation(ConstantConfig.DIVIDER);

        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.add(splitPane);

        add(splitPane);
        isInitialized = true;
        // this.setResizable(false);
        centerFrameOnDesktop(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().width;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
    }

    public void createMainPanels() {

        // create admin panel
        setAdminDashboardPanel();

        // Assign crossponding panels to crsossponding Cards
        setCards();

    }

    public void setCards(){

        // book related panels
        JPanel searchBookPanel = SearchBookPanel.INSTANCE.getSearchBookPanel();
        JPanel addBookPanel = BookGui.INSTANCE.getAddBookPanel();
        JPanel addBookCopyPanel = AddBookCopyPanel.INSTANCE.getAddBookCopyPanel();

        // member related panels
        JPanel searchMemberPanel = SearchMemberPanel.INSTANCE.getsearchMemberPanel();
        JPanel addMemberPanel = MemberUI.INSTANCE.getAddMemberPanel();
        JPanel editOrDeletePanel = EditOrDeleteMember.INSTANCE.getAddMemberPanel();

        // logout panel
        JPanel logoutPanel = Logout.INSTANCE.getLogOutPanel();

        // Dashboard panel
        cards = new JPanel(new CardLayout());
        cards.add(adminHomePagePanel, itemList.get(0).getItemName());
        cards.add(addMemberPanel, itemList.get(1).getItemName());
        cards.add(addBookPanel, itemList.get(2).getItemName());
        cards.add(addBookCopyPanel, itemList.get(3).getItemName());
        //cards.add(searchMemberPanel, itemList.get(4).getItemName());
        //cards.add(searchBookPanel, itemList.get(5).getItemName());
        //cards.add(editOrDeletePanel, itemList.get(4).getItemName());
        cards.add(logoutPanel, itemList.get(4).getItemName());

    }



    public void setAdminDashboardPanel() {

        // create  panel
        adminHomePagePanel = new JPanel(new BorderLayout());
        adminHomePagePanel.add(new JLabel("Administrator Dashboard"), BorderLayout.NORTH);

        JTabbedPane tp=new JTabbedPane();
        tp.setPreferredSize(new Dimension(ConstantConfig.APP_WIDTH - ConstantConfig.DIVIDER, ConstantConfig.APP_HEIGHT));
        tp.add("Books",new JScrollPane(bookListJTable));
        tp.add("Members", new JScrollPane(memberListJTable));
        tp.setFont(ConstantConfig.DEFUALT_FONT);
        tp.setForeground(Util.LINK_AVAILABLE);
        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        adminHomePagePanel.add(tp , BorderLayout.CENTER);
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }

    @SuppressWarnings("serial")
    public void createLinkLabels() {

        DefaultListModel<ListItem> model = new DefaultListModel<>();

        for(ListItem item : itemList){
            model.addElement(item);
        }

        linkList = new JList<ListItem>(model);
        linkList.setCellRenderer(new DefaultListCellRenderer() {

            @SuppressWarnings("rawtypes")
            @Override
            public Component getListCellRendererComponent(JList list,
                                                          Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {

                Component c = super.getListCellRendererComponent(list,
                        value, index, isSelected, cellHasFocus);
                if (value instanceof ListItem) {
                    ListItem nextItem = (ListItem) value;
                    setText(nextItem.getItemName());
                    if (nextItem.highlight()) {
                        setForeground(Util.LINK_AVAILABLE);
                    } else {
                        setForeground(Util.LINK_NOT_AVAILABLE);
                    }
                    if (isSelected) {
                        setForeground(Color.BLACK);
                        setBackground(new Color(236,243,245));
                    }
                    setFont(ConstantConfig.DEFUALT_FONT);
                } else {
                    setText("illegal item");
                }
                return c;
            }

        });
    }


}
