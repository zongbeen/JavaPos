import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageFrame extends JFrame {
    JButton button1 = new JButton("메뉴 관리");
    JButton button2 = new JButton("계정 관리");
    JPanel butttonPanel = new JPanel();

    private Container c = getContentPane();

    ManageFrame() {
        setTitle("관리");
        setBackground(Color.WHITE);
        setSize(500,300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        butttonPanel.setLayout(new GridLayout(1,2,3,3));
        butttonPanel.add(button1);
        butttonPanel.add(button2);
        butttonPanel.setSize(400,120);
        butttonPanel.setLocation(50,75);
        butttonPanel.setBackground(Color.WHITE);
        c.add(butttonPanel);
        c.setBackground(Color.WHITE);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuListFrame();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MemberListFrame();
            }
        });

        setVisible(true);
    }

}