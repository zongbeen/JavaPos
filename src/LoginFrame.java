import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class LoginFrame extends JFrame {
    JPanel MainPanel = new JPanel();
    JPanel InfoPanel = new JPanel();
    JPanel LoginPanel = new JPanel();
    JPanel LastPanel = new JPanel();

    JLabel idLabel = new JLabel("아이디");
    JLabel pwLabel = new JLabel("비밀번호");

    JTextField id = new JTextField();
    JPasswordField pw = new JPasswordField();

    JButton LoginBtn = new JButton("로그인");
    JButton JoinBtn = new JButton("회원가입");
    JButton ExitBtn = new JButton("종료");

    LoginFrame(){
        int x=85, y=70;

        setTitle("로그인");
        setBackground(Color.WHITE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        idLabel.setPreferredSize(new Dimension(50, 30));
        pwLabel.setPreferredSize(new Dimension(50, 30));

        id.setPreferredSize(new Dimension(170, 30));
        pw.setPreferredSize(new Dimension(170, 30));
        pw.addKeyListener(new LoginListener());
        pw.setEchoChar('*');

        LoginBtn.setPreferredSize(new Dimension(75, 68));
        JoinBtn.setPreferredSize(new Dimension(135, 30));
        ExitBtn.setPreferredSize(new Dimension(135, 30));

        setContentPane(MainPanel);
        MainPanel.setLayout(null);
        MainPanel.setBackground(Color.WHITE);

        InfoPanel.setBounds(x,y,250,70);
        LoginPanel.setBounds(x+260,y,75,70);
        LastPanel.setBounds(x+30,y+75,280,40);
        InfoPanel.setBackground(Color.WHITE);
        LoginPanel.setBackground(Color.WHITE);
        LastPanel.setBackground(Color.WHITE);


        InfoPanel.add(idLabel);
        InfoPanel.add(id);
        InfoPanel.add(pwLabel);
        InfoPanel.add(pw);
        LoginPanel.add(LoginBtn);
        LastPanel.add(ExitBtn);
        LastPanel.add(JoinBtn);

        MainPanel.add(InfoPanel);
        MainPanel.add(LoginPanel);
        MainPanel.add(LastPanel);
        MainPanel.setBackground(Color.WHITE);

        /* Button 이벤트 리스너 추가 */
        LoginButtonListener lb = new LoginButtonListener();

        LoginBtn.addActionListener(lb);

        setVisible(true);
        setResizable(false);

        ExitBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        JoinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JoinFrame();
            }
        });

    }
    class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id1 = id.getText();
            String pw1 = pw.getText();
            new PressLogin().PressLogins(id1, pw1);
        }
    }

    class LoginListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            String id1 = id.getText();
            String pw1 = pw.getText();
            if(e.getKeyCode()==KeyEvent.VK_ENTER)
                new PressLogin().PressLogins(id1, pw1);
        }
    }

    public class PressLogin {
        public void PressLogins(String id, String pw) {
            Database db;
            String uid = id;
            String upw = pw;
            try {
                db = new Database();
                String check = db.checkLogin(uid, upw);
                if (check.equals("0")) {
                    System.out.println("입력안함");
                    JOptionPane.showMessageDialog(null,"아이디, 비밀번호를 입력하세요", "Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(check.equals("1")) {
                    System.out.println("정상");
                    JOptionPane.showMessageDialog(null,"로그인 성공","Message", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new FirstFrame();
                }
                else {
                    System.out.println("입력한 아이디 비밀번호가 DB랑 안맞음");
                    JOptionPane.showMessageDialog(null,"아이디, 비밀번호를 확인하세요", "Error",JOptionPane.ERROR_MESSAGE);
                }


            }catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

}
