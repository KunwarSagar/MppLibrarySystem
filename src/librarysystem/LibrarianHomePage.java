package librarysystem;

import controllers.*;
import librarysystem.guiElements.book.BookGui;
import librarysystem.guiElements.checkOut.CheckOutGui;
import librarysystem.guiElements.Logout;
import librarysystem.guiElements.member.MemberUI;
import librarysystem.guiElements.book.SearchBookPanel;
import librarysystem.guiElements.checkOut.CheckOutBookPanel;
import librarysystem.guiElements.checkOut.OverDuePanel;
import librarysystem.guiElements.checkOut.PrintMemberCheckOut;
import librarysystem.guiElements.member.SearchMemberPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class LibrarianHomePage extends JFrame implements LibrarySystemWindow {

    private static final long serialVersionUID = 1L;

    public static final LibrarianHomePage INSTANCE = new LibrarianHomePage();
    ControllerInterface ci = new SystemController();

    private boolean isInitialized = false;

    JList<ListItem> linkList;
    JPanel cards;
    List<ListItem> itemList = new ArrayList<>();

    private JPanel librarianHomePagePanel;
    public JTable memberListJTable,bookListJTable, checkOutList;

    //Singleton class
    private LibrarianHomePage() {
    	
        setSize(ConstantConfig.APP_WIDTH, ConstantConfig.APP_HEIGHT);
        memberListJTable = MemberUI.INSTANCE.getMemberList();
        bookListJTable = BookGui.INSTANCE.getBookList();
        checkOutList = CheckOutGui.INSTANCE.getCheckOutList();
        UIController.INSTANCE.librarian = this;
    }

    public void constructSideBarMenu(){

        for(String item : ConstantConfig.LIBRARIAN_MENU){
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
        setLibrarianHomePagePanel();

        // Assign crossponding panels to crsossponding Cards
        setCards();

    }

    public void setCards(){

        // Checkout
        // checkoutRelated Panels
        JPanel checkOutBookPanel = CheckOutBookPanel.INSTANCE.getCheckOutPanel();
        JPanel checkOutStatusPanel = OverDuePanel.INSTANCE.getSearchOverDuePanel();
        JPanel searchMemberCheckOutPanel = PrintMemberCheckOut.INSTANCE.getPrintMemberCheckOutPanel();

        // book related panels
        JPanel searchBookPanel = SearchBookPanel.INSTANCE.getSearchBookPanel();

        // member related panels
        JPanel searchMemberPanel = SearchMemberPanel.INSTANCE.getsearchMemberPanel();

        // logout panel
        JPanel logoutPanel = Logout.INSTANCE.getLogOutPanel();


        cards = new JPanel(new CardLayout());
        cards.add(librarianHomePagePanel, itemList.get(0).getItemName());
        cards.add(searchMemberPanel, itemList.get(1).getItemName());
        cards.add(searchBookPanel, itemList.get(2).getItemName());
        cards.add(checkOutBookPanel, itemList.get(3).getItemName());
        cards.add(checkOutStatusPanel, itemList.get(4).getItemName());
        cards.add(searchMemberCheckOutPanel, itemList.get(5).getItemName());
        cards.add(logoutPanel, itemList.get(6).getItemName());

    }


    public void setLibrarianHomePagePanel() {

        // create  panel
        librarianHomePagePanel = new JPanel(new BorderLayout());
        librarianHomePagePanel.add(new JLabel("Librarian Home Page"), BorderLayout.NORTH);


        JTabbedPane tp=new JTabbedPane();
        tp.setPreferredSize(new Dimension(ConstantConfig.APP_WIDTH - ConstantConfig.DIVIDER, ConstantConfig.APP_HEIGHT));
        tp.add("Checkouts", new JScrollPane(checkOutList));
        tp.add("Books",new JScrollPane(bookListJTable));
        tp.add("Members", new JScrollPane(memberListJTable));
        tp.setFont(ConstantConfig.DEFUALT_FONT);
        tp.setForeground(Util.LINK_AVAILABLE);
        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        librarianHomePagePanel.add(tp , BorderLayout.CENTER);
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
