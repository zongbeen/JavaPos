//결제관리프로그램
//고칠점: 결제취소도 만들기
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PayCancle extends JFrame {
    private JLabel counting;
    private JLabel cResult;
    private JLabel count;
    private JDialog main;

    public PayCancle() {
        main=new JDialog(this,"결제취소중",true);
        main.setSize(300,200);
        main.setLayout(null);

        counting = new JLabel("결제 취소중입니다...",JLabel.CENTER);
        counting.setFont(new Font("GOTHIC",Font.PLAIN,19));
        counting.setBounds(70, 38, 150, 30);

        count=new JLabel("",JLabel.CENTER);
        count.setFont(new Font("GOTHIC",Font.BOLD,20));
        count.setBounds(70, 80, 20, 30);

        cResult=new JLabel("초 후에 완료됩니다.");
        cResult.setFont(new Font("GOTHIC",Font.BOLD,18));
        cResult.setBounds(90, 80, 150, 30);

        TimerThread th=new TimerThread(count,main);
        Thread td = new Thread(th);
        td.start();

        main.add(counting);
        main.add(count);
        main.add(cResult);
        main.setBackground(Color.WHITE);
        main.setLocationRelativeTo(null);
        main.setVisible(true);

    }

    class TimerThread implements Runnable{
        private JLabel timerLabel;
        private JDialog d;
        //private PayTest3 pt = new PayTest3();
        public TimerThread(JLabel timerLabel,JDialog d) {
            this.timerLabel=timerLabel;
            this.d=d;
        }

        @Override
        public void run() {
            int n=3;
            while(true) {
                timerLabel.setText(Integer.toString(n));
                n--;
                try {
                    Thread.sleep(1000);
                    if(n==0) {
                        d.dispose();
                    }
                }catch(InterruptedException e) {
                    return ;
                }
            }
        }
    }
}
