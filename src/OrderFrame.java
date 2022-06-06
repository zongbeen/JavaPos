import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class OrderFrame extends JFrame {
    JButton[] coffeeButton = new JButton[12];
    JButton[] drinkButton = new JButton[12];
    JButton[] teaButton = new JButton[12];
    JButton[] ssButton = new JButton[12];

    JPanel category = new JPanel();
    JPanel tableBorder = new JPanel();
    JPanel menuBorder1 = new JPanel();
    JPanel menuBorder2 = new JPanel();
    JPanel menuBorder3 = new JPanel();
    JPanel menuBorder4 = new JPanel();


    JButton category1 = new JButton("커피");
    JButton category2 = new JButton("음료");
    JButton category3 = new JButton("스무디&쉐이크");
    JButton category4 = new JButton("차");

    JButton addBtn[] = new JButton[4];
    String AddStr[] = {"뒤로가기", "선택취소", "전체취소", "결제"};

    AddButton addButton = new AddButton();

    String ColName[] = {"메뉴","수량","금액"};
    String Data[][] ;
    DefaultTableModel model = new DefaultTableModel(Data,ColName);
    JTextField tf = new JTextField(30);
    JTable table = new JTable(model);
    int count = 0;
    int sumPrice = 0;
    FirstFrame fr;

    class Screen extends JPanel{
        Screen(){
            setBackground(Color.WHITE);
            DefaultTableModel m = (DefaultTableModel)table.getModel();
            table.setRowHeight(30);
            table.getTableHeader().setFont(new Font("GOTHIC", Font.BOLD, 15));
            add(new JScrollPane(table));
        }
    }
    class AddButton extends JPanel {
        AddButton() {
            setBackground(Color.WHITE);
            setLayout(new GridLayout(1, 4, 3, 3));

            for (int i = 0; i < AddStr.length; i++) {
                addBtn[i] = new JButton(AddStr[i]);
                add(addBtn[i]);
            }
        }
    }

    private Container c = getContentPane();

    public OrderFrame(FirstFrame fr) {
        this.fr =fr;
        setLayout(null);
        setTitle("TestFrame");
        setBackground(Color.WHITE);
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Screen sc = new Screen();
        tf.setSize(450, 70);
        tf.setLocation(50, 480);
        add(tf);

        addButton.setSize(450, 70);
        addButton.setLocation(530, 480);
        add(addButton);

        tableBorder.add(sc);
        tableBorder.setSize(500, 500);
        tableBorder.setLocation(25, 20);
        tableBorder.setBackground(Color.WHITE);

        category.setLayout(new GridLayout(1,4,3,3));
        category.add(category1);
        category.add(category2);
        category.add(category3);
        category.add(category4);
        category.setSize(450,70);
        category.setLocation(530,23);
        category.setBackground(Color.WHITE);

        menuBorder1.setLayout(new GridLayout(3, 4, 3, 3));
        menuBorder1.setSize(450, 300);
        menuBorder1.setLocation(530, 100);
        menuBorder1.setBackground(Color.WHITE);

        menuBorder2.setLayout(new GridLayout(3, 4, 3, 3));
        menuBorder2.setSize(450, 300);
        menuBorder2.setLocation(530, 100);
        menuBorder2.setBackground(Color.WHITE);

        menuBorder3.setLayout(new GridLayout(3, 4, 3, 3));
        menuBorder3.setSize(450, 300);
        menuBorder3.setLocation(530, 100);
        menuBorder3.setBackground(Color.WHITE);

        menuBorder4.setLayout(new GridLayout(3, 4, 3, 3));
        menuBorder4.setSize(450, 300);
        menuBorder4.setLocation(530, 100);
        menuBorder4.setBackground(Color.WHITE);

        for (int i = 0; i < coffeeButton.length; i++) {
            menuBorder1.add(coffeeButton[i] = new JButton());
            menuBorder2.add(drinkButton[i] = new JButton());
            menuBorder3.add(ssButton[i] = new JButton());
            menuBorder4.add(teaButton[i] = new JButton());
        }

        c.add(tableBorder);
        c.add(category);
        c.add(menuBorder1);
        c.add(menuBorder2);
        c.add(menuBorder3);
        c.add(menuBorder4);
        c.setBackground(Color.WHITE);
        reMove();

        buttonListener b1 = new buttonListener();

        category1.addActionListener(b1);
        category2.addActionListener(b1);
        category3.addActionListener(b1);
        category4.addActionListener(b1);


        for (int i = 0; i < AddStr.length; i++) {
            addBtn[i].addActionListener(new AddButtonListener());
        }
        setVisible(true);

        for (int i = 0; i < 15; i++) {
            coffeeButton[i].addActionListener(new menuButtonListener());
            drinkButton[i].addActionListener(new menuButtonListener());
            teaButton[i].addActionListener(new menuButtonListener());
            ssButton[i].addActionListener(new menuButtonListener());
        }
    }
    class buttonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String categoryButton = e.getActionCommand();

            Database db;

            if (categoryButton.equals("커피")) {
                try {
                    db = new Database();
                    String categoryBtn[] = db.insertCategory("커피");
                    for (int i = 0; i < categoryBtn.length - 1; i++) {
                        coffeeButton[i].setText(categoryBtn[i]);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                reMove();
                c.add(menuBorder1);
                menuBorder1.revalidate();
                menuBorder1.repaint();

            } else if (categoryButton.equals("음료")) {
                try {
                    db = new Database();
                    String categoryBtn[] = db.insertCategory("음료");
                    for (int i = 0; i < categoryBtn.length - 1; i++) {
                        drinkButton[i].setText(categoryBtn[i]);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                reMove();
                c.add(menuBorder2);
                menuBorder2.revalidate();
                menuBorder2.repaint();
            } else if (categoryButton.equals("스무디&쉐이크")) {
                try {
                    db = new Database();
                    String categoryBtn[] = db.insertCategory("스무디&쉐이크");
                    for (int i = 0; i < categoryBtn.length - 1; i++) {
                        ssButton[i].setText(categoryBtn[i]);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                reMove();
                c.add(menuBorder3);
                menuBorder3.revalidate();
                menuBorder3.repaint();
            } else if (categoryButton.equals("차")) {
                try {
                    db = new Database();
                    String categoryBtn[] = db.insertCategory("차");
                    for (int i = 0; i < categoryBtn.length - 1; i++) {
                        teaButton[i].setText(categoryBtn[i]);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                reMove();
                c.add(menuBorder4);
                menuBorder4.revalidate();
                menuBorder4.repaint();
            }
        }
    }
    class menuButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String mb = e.getActionCommand();
            int price = 0;
            int rowCont = table.getRowCount();
            int flag = 0;

            JButton coffeeButton = (JButton) e.getSource();
            DefaultTableModel m = (DefaultTableModel) table.getModel();

            Database db;
            try {
                db = new Database();
                price = db.menuPrice(mb);
                sumPrice += price;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i < rowCont; i++) {
                if (table.getValueAt(i, 0).equals(coffeeButton.getText())) {
                    flag = 1;
                    table.setValueAt((int) table.getValueAt(i, 1) + 1, i, 1);
                    table.setValueAt((int) table.getValueAt(i, 2) + price, i, 2);
                    break;
                }
            }

            if (flag == 0) {
                m.addRow(new Object[] { mb, count + 1, price });
            }
            tf.setText("총  :   " + Integer.toString(sumPrice) + "원");
            tf.setFont(new Font("GOTHIC", Font.BOLD, 25));
            tf.setHorizontalAlignment(JLabel.CENTER);
        }

    }
    public void reMove() {
        c.remove(menuBorder1);
        c.remove(menuBorder2);
        c.remove(menuBorder3);
        c.remove(menuBorder4);
    }
    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String addB = e.getActionCommand();
            int rowCont = table.getRowCount();
            int price = 0;

            Database db;
            try {
                db = new Database();
                price = db.menuPrice(addB);
                sumPrice += price;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if(addB.equals("뒤로가기")) {
                JButton addBtn = (JButton) e.getSource();
                fr.setVisible(true);
                setVisible(false);


            } else if (addB.equals("선택취소")) {
                JButton addBtn = (JButton) e.getSource();
                DefaultTableModel m = (DefaultTableModel) table.getModel();
                tf.setText(String.valueOf(""));
                int selectPrice = (int) table.getValueAt(table.getSelectedRow(), 2);
                sumPrice -= selectPrice;
                m.removeRow(table.getSelectedRow());
                tf.setText("총  :   " + Integer.toString(sumPrice) + "원");
                tf.setHorizontalAlignment(JLabel.CENTER);

            } else if (addB.equals("전체취소")) {
                JButton addBtn = (JButton) e.getSource();
                DefaultTableModel m = (DefaultTableModel) table.getModel();
                sumPrice = 0;
                m.setRowCount(0);
                tf.setText(String.valueOf(""));
            } else if (addB.equals("결제")) {
                String spendSumPrice;
                spendSumPrice = String.valueOf(sumPrice);
                String spendName;
                int spendCount = 0;
                int spendPrice = 0;
                int cou = table.getRowCount();
                int print = 0;

                new paying();

                DefaultTableModel m = (DefaultTableModel) table.getModel();
                System.out.println(cou);
                if (cou != 0) {
                    try {
                        db = new Database();
                        db.insertTotal(spendSumPrice);
                        for (int i = 0; i < cou; i++) {
                            spendName = (String) table.getValueAt(i, 0);
                            spendCount = (int) table.getValueAt(i, 1);
                            spendPrice = (int) table.getValueAt(i, 2);
                            print = db.insertBreakdown(spendName, String.valueOf(spendCount), String.valueOf(spendPrice));
                        }
                    } catch (SQLException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }
                }

                m.setRowCount(0);
                tf.setText(String.valueOf(""));
                fr.setVisible(true);
                setVisible(false);
            }
        }
    }
}
