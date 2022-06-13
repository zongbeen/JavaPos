import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MemberListFrame extends JFrame {

    JPanel tablePanel = new JPanel();

    String ColNames[] = {"이름","전화번호","아이디","비밀번호"};
    String Datas[][] ;
    DefaultTableModel memberModel = new DefaultTableModel(Datas,ColNames);
    JTable tables = new JTable(memberModel) {
        public boolean isCellEditable(int i, int c){
            return false;
        }
    };

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

    MemberListFrame() {
        setLayout(null);
        setTitle("직원 목록");
        setBackground(Color.WHITE);
        setSize(500,500);
        setLocationRelativeTo(null);

        TableScreen ts = new TableScreen();
        tablePanel.add(ts);
        tablePanel.setSize(500, 500);
        tablePanel.setLocation(0, 0);
        tablePanel.setBackground(Color.WHITE);

        c.setBackground(Color.WHITE);
        c.add(tablePanel);

        String[][] searchMember=null;
        DefaultTableModel m = (DefaultTableModel) tables.getModel();
        Database db;
        try {
            db = new Database();
            searchMember = db.allMember();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        for (int i=0; i<searchMember.length;i++){
            System.out.println(searchMember[i][0]);
            System.out.println(searchMember[i][1]);
            System.out.println(searchMember[i][2]);
            m.addRow(new Object[] { searchMember[i][0],searchMember[i][1],searchMember[i][2],searchMember[i][3] });
        }
        tables.addMouseListener(new MyMouseListener());
        setVisible(true);
    }

    class MyMouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if (e.getClickCount() == 2) {
                int row = tables.getSelectedRow();
                new UpdateMemberFrame((String) tables.getValueAt(row,0), (String) tables.getValueAt(row,1),
                        (String) tables.getValueAt(row,2), (String) tables.getValueAt(row,3));
                dispose();
            }
        }
    }
}
