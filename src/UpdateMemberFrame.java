import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateMemberFrame extends JFrame {
    JLabel nameLabel = new JLabel("  이름  ");
    JLabel phoneLabel = new JLabel("전화번호");
    JLabel idLabel = new JLabel(" 아이디 ");
    JLabel pwLabel = new JLabel("비밀번호");

    JTextField nameTF = new JTextField(StaticMember.name);
    JTextField phoneTF = new JTextField(StaticMember.phone);
    JTextField idTF = new JTextField(StaticMember.ID);
    JTextField pwTF = new JTextField(StaticMember.PW);

    JPanel namePanel = new JPanel();
    JPanel phonePanel = new JPanel();
    JPanel idPanel = new JPanel();
    JPanel pwPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    JButton backBtn = new JButton("뒤로가기");
    JButton updateBtn = new JButton("수정완료");

    UpdateMemberFrame(){
        int x=50, y=50;

        setTitle("회원수정");
        setBackground(Color.WHITE);
        setSize(400, 500);
        setLocationRelativeTo(null);


        nameLabel.setPreferredSize(new Dimension(50, 30));
        phoneLabel.setPreferredSize(new Dimension(50, 30));
        idLabel.setPreferredSize(new Dimension(50, 30));
        pwLabel.setPreferredSize(new Dimension(50, 30));

        nameTF.setPreferredSize(new Dimension(170, 30));
        phoneTF.setPreferredSize(new Dimension(170, 30));
        idTF.setPreferredSize(new Dimension(170, 30));
        idTF.setEditable(false);
        idTF.setBackground(Color.LIGHT_GRAY);
        pwTF.setPreferredSize(new Dimension(170, 30));


        updateBtn.setPreferredSize(new Dimension(135, 30));
        backBtn.setPreferredSize(new Dimension(135, 30));

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
        namePanel.add(nameTF);
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneTF);
        idPanel.add(idLabel);
        idPanel.add(idTF);
        pwPanel.add(pwLabel);
        pwPanel.add(pwTF);
        buttonPanel.add(backBtn);
        buttonPanel.add(updateBtn);

        mainPanel.add(namePanel);
        mainPanel.add(phonePanel);
        mainPanel.add(idPanel);
        mainPanel.add(pwPanel);
        mainPanel.add(buttonPanel);
        mainPanel.setBackground(Color.WHITE);


        updateButtonListener ub = new updateButtonListener();
        BackButtonListener bb = new BackButtonListener();
        updateBtn.addActionListener(ub);
        backBtn.addActionListener(bb);

        setVisible(true);
        setResizable(false);

    }

    class updateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameTF.getText();
            String phone = phoneTF.getText();
            String id = idTF.getText();
            String pw = pwTF.getText();
            Database db;
            try {
                db = new Database();
                if(name.equals("") || phone.equals("") || id.equals("") || pw.equals("")) {
                    JOptionPane.showMessageDialog(null,"회원수정 실패", "Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    int updateSign =db.updateSign(new String[] {name, phone, pw, id});
                    if(updateSign == 1){
                        JOptionPane.showMessageDialog(null,"회원수정 성공", "Success",JOptionPane.INFORMATION_MESSAGE);
                        StaticMember.name = name;
                        StaticMember.phone = phone;
                        StaticMember.PW = pw;
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "회원수정 실패", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
