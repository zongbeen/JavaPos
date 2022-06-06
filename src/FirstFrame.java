import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class FirstFrame extends JFrame {
    JButton button1 = new JButton("주문");
    JButton button2 = new JButton("매출관리");
    JButton button3 = new JButton("회원정보수정");
    JPanel butttonPanel = new JPanel();

    private Container c = getContentPane();

    FirstFrame() {
        setTitle("FirstFrame");
        setBackground(Color.WHITE);
        setSize(500,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        butttonPanel.setLayout(new GridLayout(1,3,3,3));
        butttonPanel.add(button1);
        butttonPanel.add(button2);
        butttonPanel.add(button3);
        butttonPanel.setSize(400,120);
        butttonPanel.setLocation(50,75);
        butttonPanel.setBackground(Color.WHITE);
        c.add(butttonPanel);
        c.setBackground(Color.WHITE);

        button1Listener b1 = new button1Listener(this);

        button1.addActionListener(b1);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SaleFrame();
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateMemberFrame();
            }
        });

        setVisible(true);
    }

    class button1Listener implements ActionListener{
        FirstFrame f;
        public button1Listener(FirstFrame fr){
            f=fr;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new OrderFrame(f);
        }
    }
}