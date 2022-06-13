import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CancleFrame extends JFrame {
    JPanel searchPanel = new JPanel();
    JPanel tablePanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    JButton searchButton = new JButton("조회");
    JButton cancleButton = new JButton("결제 취소");

    JLabel nianLabel = new JLabel("년");
    JLabel yueLabel = new JLabel("월");
    JLabel riLabel = new JLabel("일");

    JTextField sumTF = new JTextField(30);

    String ColNames[] = {"No","날짜","금액"};
    String Datas[][] ;
    DefaultTableModel saleModel = new DefaultTableModel(Datas,ColNames);
    JTable tables = new JTable(saleModel);

    String nian[] = {"2022"};

    String yue[] = {"1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "11", "12"};
    String ri[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18","19","20",
            "21", "22", "23", "24", "25", "26", "27", "28","29","30","31"};

    JComboBox<String> nianBox = new JComboBox<>(nian);
    JComboBox<String> yueBox = new JComboBox<>(yue);
    JComboBox<String> riBox = new JComboBox<>(ri);

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

    CancleFrame() {
        int x=130, y=120;
        setLayout(null);
        setTitle("결제 취소");
        setBackground(Color.WHITE);
        setSize(1000,600);
        setLocationRelativeTo(null);

        TableScreen ts = new TableScreen();

        tablePanel.add(ts);
        tablePanel.setSize(500, 500);
        tablePanel.setLocation(450, 20);
        tablePanel.setBackground(Color.WHITE);

        searchPanel.setLayout(new GridLayout(4,2,3,3));
        searchPanel.add(nianBox);
        searchPanel.add(nianLabel);
        searchPanel.add(yueBox);
        searchPanel.add(yueLabel);
        searchPanel.add(riBox);
        searchPanel.add(riLabel);
        searchPanel.setBounds(x,y,400,200);
        searchPanel.setBackground(Color.WHITE);

        buttonPanel.setLayout(new GridLayout(1,2,3,3));
        buttonPanel.add(searchButton);
        buttonPanel.add(cancleButton);
        buttonPanel.setBounds(x+10,y+200,200,70);
        buttonPanel.setBackground(Color.WHITE);

        c.add(tablePanel);
        c.add(searchPanel);
        c.add(buttonPanel);
        c.setBackground(Color.WHITE);

        searchButtonListener sl = new searchButtonListener();
        cancleButtonListener cl = new cancleButtonListener();
        searchButton.addActionListener(sl);
        cancleButton.addActionListener(cl);

        setVisible(true);
    }
    class searchButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String[][] searchDate=null;
            String year = nianBox.getSelectedItem().toString();
            String month = yueBox.getSelectedItem().toString();
            String day = riBox.getSelectedItem().toString();
            DefaultTableModel m = (DefaultTableModel) tables.getModel();
            m.setNumRows(0);

            Database db;
            try {
                db = new Database();
                searchDate = db.searchDel(year, month, day);
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
    class cancleButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String codeNum;
            Database db;
            new PayCancle();
            codeNum = (String)tables.getValueAt(tables.getSelectedRow(),0);
            System.out.println(codeNum);
            try {
                if(codeNum.equals("1")){
                    db = new Database();
                    int i = db.deleteSale(codeNum);
                    System.out.println(i);
                    DefaultTableModel m = (DefaultTableModel) tables.getModel();
                    m.removeRow(tables.getSelectedRow());
                    dispose();
                }
                else
                    JOptionPane.showMessageDialog(null,"선택된 리스트가 없습니다.", "Error",JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}