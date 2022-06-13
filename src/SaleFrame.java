import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaleFrame extends JFrame {
    JButton dailyButton = new JButton("일별 조회");
    JButton monthlyButton = new JButton("월별 조회");
    JButton cancleButton = new JButton("결제 취소");
    JPanel butttonPanel = new JPanel();


    private Container c = getContentPane();

    SaleFrame() {
        setTitle("매출관리");
        setBackground(Color.WHITE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        butttonPanel.setLayout(new GridLayout(1,3,3,3));
        butttonPanel.add(dailyButton);
        butttonPanel.add(monthlyButton);
        butttonPanel.add(cancleButton);
        butttonPanel.setSize(400,120);
        butttonPanel.setLocation(50,75);
        butttonPanel.setBackground(Color.WHITE);
        c.add(butttonPanel);
        c.setBackground(Color.WHITE);

        dailyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DailySaleFrame();
            }
        });
        monthlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MonthlySaleFrame();
            }
        });
        cancleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CancleFrame();
            }
        });

        setVisible(true);
    }
}