import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MonthlySaleFrame extends JFrame {
    JPanel searchPanel = new JPanel();
    JPanel tablePanel = new JPanel();

    JButton searchButton = new JButton("조회");

    JLabel nianLabel = new JLabel("년");
    JLabel yueLabel = new JLabel("월");

    String ColNames[] = {"메뉴","수량","금액"};
    String Datas[][] ;
    DefaultTableModel saleModel = new DefaultTableModel(Datas,ColNames);

    JTable tables = new JTable(saleModel);

    JTextField sumTF = new JTextField(30);

    String nian[] = {"2022"};

    String yue[] = {"1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "11", "12"};

    JComboBox<String> nianBox = new JComboBox<>(nian);
    JComboBox<String> yueBox = new JComboBox<>(yue);

    private Container c = getContentPane();

    class TableScreen extends JPanel{
        TableScreen(){
            setBackground(Color.WHITE);
            DefaultTableModel m = (DefaultTableModel)tables.getModel();
            tables.setRowHeight(30);
            tables.getTableHeader().setFont(new Font("GOTHIC", Font.BOLD, 15));
            add(new JScrollPane(tables));
        }
    }

    MonthlySaleFrame() {
        int x=130, y=120;
        setLayout(null);
        setTitle("월별 매출 조회");
        setBackground(Color.WHITE);
        setSize(1000,600);
        setLocationRelativeTo(null);

        TableScreen ts = new TableScreen();
        sumTF.setSize(350,70);
        sumTF.setLocation(x-60, 390);

        tablePanel.add(ts);
        tablePanel.setSize(500, 500);
        tablePanel.setLocation(450, 20);
        tablePanel.setBackground(Color.WHITE);

        searchPanel.setLayout(new GridLayout(4,2,3,3));
        searchPanel.add(nianBox);
        searchPanel.add(nianLabel);
        searchPanel.add(yueBox);
        searchPanel.add(yueLabel);
        searchPanel.add(searchButton);
        searchPanel.setBounds(x,y,400,200);
        searchPanel.setBackground(Color.WHITE);

        c.add(tablePanel);
        c.add(searchPanel);
        c.add(sumTF);
        c.setBackground(Color.WHITE);

        searchButtonListener sl = new searchButtonListener();
        searchButton.addActionListener(sl);

        setVisible(true);
    }
    class searchButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int price = 0;
            String[][] searchDate=null;
            String year = nianBox.getSelectedItem().toString();
            String month = yueBox.getSelectedItem().toString();
            DefaultTableModel m = (DefaultTableModel) tables.getModel();
            m.setNumRows(0);

            Database db;
            try {
                db = new Database();
                searchDate = db.monthlySale(year, month);
                price=db.monthlyPrice(year, month);
                sumTF.setText("");
                sumTF.repaint();
                sumTF.revalidate();
                sumTF.setText("월 매출 : " + Integer.toString(price) + "원");
                sumTF.setFont(new Font("GOTHIC", Font.BOLD, 25));
                sumTF.setHorizontalAlignment(JLabel.CENTER);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            for (int i=0; i<searchDate.length;i++){
                System.out.println(searchDate[i][0]);
                System.out.println(searchDate[i][1]);
                System.out.println(searchDate[i][2]);
                m.addRow(new Object[] { searchDate[i][0],searchDate[i][1],searchDate[i][2] });
            }
        }
    }
}