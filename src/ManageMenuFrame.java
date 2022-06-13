import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

class ManageMenuFrame extends JFrame {

    JLabel numLabel = new JLabel("  품번  ");
    JLabel nameLabel = new JLabel("  품명  ");
    JLabel priceLabel = new JLabel("  가격  ");
    JLabel category_numLabel = new JLabel("카테고리");

    JTextField numTf = new JTextField(10);
    JTextField nameTf = new JTextField(10);
    JTextField priceTf = new JTextField(10);
    JTextField category_numTf = new JTextField(10);

    JPanel numPanel = new JPanel();
    JPanel namePanel = new JPanel();
    JPanel pricePanel = new JPanel();
    JPanel c_idPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    JButton changeButton = new JButton("변경");
    JButton backButton = new JButton("뒤로가기");

    private Container c = getContentPane();

    ManageMenuFrame(String id, String name, String price, String cat_id) {
        int x=50, y=50;
        setLayout(null);
        setTitle("메뉴 수정");
        setBackground(Color.WHITE);
        setSize(400,500);
        setLocationRelativeTo(null);

        numTf.setText(id);
        nameTf.setText(name);
        priceTf.setText(price);
        category_numTf.setText(cat_id);

        numLabel.setPreferredSize(new Dimension(50, 30));
        nameLabel.setPreferredSize(new Dimension(50, 30));
        priceLabel.setPreferredSize(new Dimension(50, 30));
        category_numLabel.setPreferredSize(new Dimension(50, 30));

        numTf.setPreferredSize(new Dimension(170, 30));
        numTf.setEditable(false);
        numTf.setBackground(Color.LIGHT_GRAY);
        nameTf.setPreferredSize(new Dimension(170, 30));
        priceTf.setPreferredSize(new Dimension(170, 30));
        category_numTf.setPreferredSize(new Dimension(170, 30));
        category_numTf.setEditable(false);
        category_numTf.setBackground(Color.LIGHT_GRAY);

        changeButton.setPreferredSize(new Dimension(135, 30));
        setContentPane(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        numPanel.setBounds(x+15,y,250,40);
        namePanel.setBounds(x,y+75,280,40);
        pricePanel.setBounds(x,y+150,280,40);
        c_idPanel.setBounds(x,y+225,280,40);
        buttonPanel.setBounds(x,y+300,280,40);
        numPanel.setBackground(Color.WHITE);
        namePanel.setBackground(Color.WHITE);
        pricePanel.setBackground(Color.WHITE);
        c_idPanel.setBackground(Color.WHITE);
        buttonPanel.setBackground(Color.WHITE);

        numPanel.add(numLabel);
        numPanel.add(numTf);
        namePanel.add(nameLabel);
        namePanel.add(nameTf);
        pricePanel.add(priceLabel);
        pricePanel.add(priceTf);
        c_idPanel.add(category_numLabel);
        c_idPanel.add(category_numTf);
        buttonPanel.add(changeButton);
        buttonPanel.add(backButton);

        mainPanel.add(numPanel);
        mainPanel.add(namePanel);
        mainPanel.add(pricePanel);
        mainPanel.add(c_idPanel);
        mainPanel.add(buttonPanel);
        mainPanel.setBackground(Color.WHITE);

        changeButtonListener cb = new changeButtonListener();
        backButtonListener bb = new backButtonListener();
        changeButton.addActionListener(cb);
        backButton.addActionListener(bb);

        setVisible(true);
        setResizable(false);
    }

    class changeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = numTf.getText();
            String name = nameTf.getText();
            String price = priceTf.getText();
            String c_id = category_numTf.getText();
            Database db;
            try {
                db = new Database();
                if(id.equals("") || name.equals("") || price.equals("") || c_id.equals("")) {
                    JOptionPane.showMessageDialog(null,"메뉴 변경 실패", "Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    int updateMenu =db.updateMenu(new String[] {name, price, id});
                    if(updateMenu == 1){
                        JOptionPane.showMessageDialog(null,"메뉴 변경 성공", "Success",JOptionPane.INFORMATION_MESSAGE);
                        StaticMenu.productId = id;
                        StaticMenu.productName = name;
                        StaticMenu.productPrice = price;
                        StaticMenu.c_id = c_id;
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "메뉴 변경 오류", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    class  backButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}

