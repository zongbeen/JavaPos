import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

class UpdateMemberFrame extends JFrame {

    JLabel nameLabel = new JLabel("  이름  ");
    JLabel phoneLabel = new JLabel("전화번호");
    JLabel idLabel = new JLabel(" 아이디 ");
    JLabel pwLabel = new JLabel("비밀번호");

    JTextField nameTf = new JTextField(10);
    JTextField phoneTf = new JTextField(10);
    JTextField idTf = new JTextField(10);
    JPasswordField pwTf = new JPasswordField(10);

    JPanel namePanel = new JPanel();
    JPanel phonePanel = new JPanel();
    JPanel idPanel = new JPanel();
    JPanel pwPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    JButton changeButton = new JButton("변경");
    JButton backButton = new JButton("뒤로가기");

    private Container c = getContentPane();

    UpdateMemberFrame(String name, String phone, String id, String pw) {
        int x=50, y=50;
        setLayout(null);
        setTitle("직원 수정");
        setBackground(Color.WHITE);
        setSize(400,500);
        setLocationRelativeTo(null);

        nameTf.setText(name);
        phoneTf.setText(phone);
        idTf.setText(id);
        pwTf.setText(pw);
        pwTf.setEchoChar('*');

        nameLabel.setPreferredSize(new Dimension(50, 30));
        phoneLabel.setPreferredSize(new Dimension(50, 30));
        idLabel.setPreferredSize(new Dimension(50, 30));
        pwLabel.setPreferredSize(new Dimension(50, 30));

        nameTf.setPreferredSize(new Dimension(170, 30));
        nameTf.setPreferredSize(new Dimension(170, 30));
        phoneTf.setPreferredSize(new Dimension(170, 30));
        idTf.setPreferredSize(new Dimension(170, 30));
        idTf.setEditable(false);
        idTf.setBackground(Color.LIGHT_GRAY);
        pwTf.setPreferredSize(new Dimension(170, 30));

        changeButton.setPreferredSize(new Dimension(135, 30));
        setContentPane(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        namePanel.setBounds(x+15,y,250,40);
        phonePanel.setBounds(x,y+75,280,40);
        idPanel.setBounds(x,y+150,280,40);
        pwPanel.setBounds(x,y+225,280,40);
        buttonPanel.setBounds(x,y+300,280,40);
        namePanel.setBackground(Color.WHITE);
        phonePanel.setBackground(Color.WHITE);
        idPanel.setBackground(Color.WHITE);
        pwPanel.setBackground(Color.WHITE);
        buttonPanel.setBackground(Color.WHITE);

        namePanel.add(nameLabel);
        namePanel.add(nameTf);
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneTf);
        idPanel.add(idLabel);
        idPanel.add(idTf);
        pwPanel.add(pwLabel);
        pwPanel.add(pwTf);
        buttonPanel.add(changeButton);
        buttonPanel.add(backButton);

        mainPanel.add(namePanel);
        mainPanel.add(phonePanel);
        mainPanel.add(idPanel);
        mainPanel.add(pwPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(backButton);
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
            String name = nameTf.getText();
            String phone = phoneTf.getText();
            String id = idTf.getText();
            String pw = pwTf.getText();
            Database db;
            try {
                db = new Database();
                if(name.equals("") || phone.equals("") || id.equals("") || pw.equals("")) {
                    JOptionPane.showMessageDialog(null,"계정 변경 실패", "Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    int updateMember =db.updateSign(new String[] {name, phone, pw, id});
                    if(updateMember == 1){
                        JOptionPane.showMessageDialog(null,"계정 변경 성공", "Success",JOptionPane.INFORMATION_MESSAGE);
                        StaticMember.name = name;
                        StaticMember.phone = phone;
                        StaticMember.ID = id;
                        StaticMember.PW = pw;
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "계정 변경 오류", "Error", JOptionPane.ERROR_MESSAGE);
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

