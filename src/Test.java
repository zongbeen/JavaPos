/*package yumyum;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class SalesCheck extends JFrame{

    private JPanel showMae;
    private JPanel selectM;
    private JPanel sp;
    Connection conn = null,conn2=null;
    PreparedStatement pstmt=null,pstmt2=null;
    ResultSet rs=null,rs2=null;
    String sql="",sql_1="";


    private JLabel title_l;
    private JLabel menu_l;
    private JLabel month_l,year_l,date_l; //일,월,년
    private JLabel chooseMenu; //메뉴선택
    private JButton previous;
    private JButton show_btn;
    private JButton total_btn;
    private JButton show_btn_m;
    private JButton total_btn_m;

    private JRadioButton oneday,monthly;
    JComboBox year_list,month_list, date_list,menu_list; //날짜 선택
    JComboBox meat_list,meal_list,drinks_list;

    Object ob[][]=new Object[0][3]; //데이터표시 열만 나오게 설정
    DefaultTableModel model; //데이터저장부분
    JTable menuTable;
    JScrollPane menuPane;
    String header[]= {"메뉴","수량","총 가격"}; //컬럼명


    String [] menu;
    String [] meatmenu; //고기메뉴 선택
    String [] mealmenu;
    String [] drinks;
    private JLabel meatCh;
    private JLabel mealCh;
    private JLabel drinkCh;

    public SalesCheck(Table_main tm) {
        setTitle("매출 확인");
        setSize(980, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(null); 	//자유롭게 위치 조정 가능
        getContentPane().setBackground(new Color(245,235,220));
        changeAllSwingComponentDefaultFont();
        setting(tm);
    }

    public void setting(Table_main tm) {
        title_l = new JLabel("매출 확인");
        title_l.setFont(new Font("나눔바른고딕", Font.PLAIN, 33));
        title_l.setBounds(420, 8, 187, 60);

        selectM = new JPanel();
        selectM.setLayout(null);
        selectM.setBounds(20,91,460,295);
        selectM.setBackground(Color.WHITE);

        showMae = new JPanel();
        showMae.setLayout(null);
        showMae.setBounds(500, 91, 460, 432);
        showMae.setBackground(Color.WHITE);

        previous = new JButton("돌아가기");

        previous.setBackground(new Color(214, 164, 149));
        previous.setBounds(20,12,96,56);
        previous.setBorderPainted(false);

        previous.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                setVisible(false);
                tm.setVisible(true);
            }
        });

        ButtonGroup g=new ButtonGroup();
        oneday = new JRadioButton("일일매출");
        oneday.setBounds(121, 17, 98, 27);
        oneday.setBackground(Color.WHITE);
        monthly = new JRadioButton("월별매출");
        monthly.setBounds(235, 17, 98, 27);
        monthly.setBackground(Color.WHITE);
        g.add(oneday); g.add(monthly);


        selectM.add(oneday);
        selectM.add(monthly);

        sp = new JPanel();
        sp.setBounds(14, 54, 432, 229);
        sp.setBackground(Color.WHITE);
        selectM.add(sp);
        sp.setLayout(null);

        //년
        String[] years=new String[]{"2020", "2021"};

        //달
        String [] months = new String[12];
        for (int i=0;i<months.length;i++) {
            months[i]=Integer.toString(i+1);
        }
        //일
        String [] dates = new String [31];
        for (int i=0;i<dates.length;i++) {
            dates[i] = Integer.toString(i+1);
        }

        year_list = new JComboBox(years);
        year_list.setVisible(false);
        sp.add(year_list);

        month_list=new JComboBox(months);
        month_list.setBounds(161, 12, 61, 24);
        month_list.setVisible(false);
        sp.add(month_list);

        date_list=new JComboBox(dates);
        date_list.setBounds(271, 12, 61, 24);
        date_list.setVisible(false);
        sp.add(date_list);

        year_l = new JLabel("년");
        year_l.setBounds(138, 12, 31, 30);
        year_l.setVisible(false);
        sp.add(year_l);

        month_l= new JLabel("월");
        sp.add(month_l);
        month_l.setVisible(false);

        date_l = new JLabel("일");
        sp.add(date_l);
        date_l.setVisible(false);


        //메뉴들
        meatCh = new JLabel("고기 선택: ",JLabel.CENTER);
        sp.add(meatCh);
        meatCh.setVisible(false);

        mealCh = new JLabel("식사류 선택 : ", JLabel.CENTER);
        sp.add(mealCh);
        mealCh.setVisible(false);

        drinkCh = new JLabel("음료 선택: ", JLabel.CENTER);
        sp.add(drinkCh);
        drinkCh.setVisible(false);

        //고기
        meatmenu=new String[16];
        meatmenu[0]="--";
        for(int i=1;i<meatmenu.length;i++) {
            setMeat(i);
        }

        //식사류
        mealmenu=new String[8];
        mealmenu[0]="--";
        for(int i=1;i<mealmenu.length;i++) {
            setMeal(i);
        }

        //음료
        drinks=new String[11];
        drinks[0]="--";
        for(int i=1;i<drinks.length;i++) {
            setDrink(i);
        }

        meat_list=new JComboBox(meatmenu);
        sp.add(meat_list);
        meat_list.setVisible(false);

        meal_list = new JComboBox(mealmenu);
        sp.add(meal_list);
        meal_list.setVisible(false);

        drinks_list = new JComboBox(drinks);
        sp.add(drinks_list);
        drinks_list.setVisible(false);

        show_btn = new JButton("매출보기");
        sp.add(show_btn);
        show_btn.setVisible(false);
        show_btn.setBackground(new Color(213, 218, 214));


        total_btn=new JButton("전체 매출");
        sp.add(total_btn);
        total_btn.setVisible(false);
        total_btn.setBackground(new Color(213, 218, 214));

        show_btn_m = new JButton("매출보기");
        show_btn_m.setVisible(false);
        show_btn_m.setBackground(new Color(213, 218, 214));
        sp.add(show_btn_m);
        total_btn_m=new JButton("전체 매출");
        total_btn_m.setVisible(false);
        total_btn_m.setBackground(new Color(213, 218, 214));
        sp.add(total_btn_m);


        // 디비에서 가져온 값을 출력해줘라
        model = new DefaultTableModel(header, 0) {
            public boolean isCellEditable(int i, int c) {//테이블 더블 클릭시 수정 불가
                return false;
            }
        };
        model.setColumnCount(3);
        menuTable=new JTable(model);
        menuTable.setRowHeight(20);
        menuPane=new JScrollPane(menuTable);
        menuPane.setBounds(5, 5, 450, 420);
        menuPane.getViewport().setBackground(Color.WHITE);
        showMae.add(menuPane);

        getContentPane().add(title_l);
        getContentPane().add(selectM);
        getContentPane().add(showMae);
        getContentPane().add(previous);

        sp.setVisible(true);
        setVisible(true);

        oneday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                year_list.setVisible(false);
                year_l.setVisible(false);
                show_btn_m.setVisible(false);
                total_btn_m.setVisible(false);

                year_list.setBounds(63, 12, 61, 24);
                year_list.setVisible(true);
                year_l.setBounds(138, 12, 31, 30);
                year_l.setVisible(true);

                month_list.setBounds(161, 12, 61, 24);
                month_list.setVisible(true);
                month_l.setBounds(236, 9, 31, 30);
                month_l.setVisible(true);

                date_list.setBounds(271, 12, 61, 24);
                date_list.setVisible(true);
                date_l.setBounds(335, 9, 31, 30);
                date_l.setVisible(true);

                meatCh.setBounds(48, 48, 115, 30);
                meatCh.setVisible(true);

                mealCh.setBounds(48, 90, 115, 30);
                mealCh.setVisible(true);

                drinkCh.setBounds(48, 136, 115, 30);
                drinkCh.setVisible(true);

                meat_list.setSelectedIndex(0);
                meat_list.setBounds(159, 48, 173, 30);
                meat_list.setVisible(true);

                meal_list.setSelectedIndex(0);
                meal_list.setBounds(159, 89, 173, 30);
                meal_list.setVisible(true);

                drinks_list.setSelectedIndex(0);
                drinks_list.setBounds(159, 134, 173, 30);
                drinks_list.setVisible(true);

                show_btn.setBounds(69, 180, 115, 37);
                show_btn.setVisible(true);

                total_btn.setBounds(224, 180, 145, 37);
                total_btn.setVisible(true);

                sp.add(year_list);
                sp.add(year_l);
                sp.add(month_list);
                sp.add(month_l);
                sp.add(meatCh);
                sp.add(meat_list);
                sp.add(mealCh);
                sp.add(meal_list);
                sp.add(drinkCh);
                sp.add(drinks_list);
                sp.add(show_btn);
                sp.add(total_btn);

                show_btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        model.setNumRows(0);
                        showM();
                    }
                });

                total_btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        model.setNumRows(0);
                        showTotal();
                    }
                });
            }
        });

        monthly.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                show_btn.setVisible(false);
                total_btn.setVisible(false);

                year_list.setBounds(117, 12, 61, 24);
                year_list.setVisible(true);

                year_l.setBounds(183, 12, 31, 30);
                year_l.setVisible(true);

                month_list.setBounds(228, 15, 61, 24);
                month_list.setVisible(true);
                month_l.setBounds(304, 12, 31, 30);
                month_l.setVisible(true);

                date_list.setVisible(false);
                date_l.setVisible(false);

                meatCh.setBounds(48, 48, 115, 30);
                meatCh.setVisible(true);
                mealCh.setBounds(48, 90, 115, 30);
                mealCh.setVisible(true);
                drinkCh.setBounds(48, 136, 115, 30);
                drinkCh.setVisible(true);

                meat_list.setSelectedIndex(0);
                meat_list.setBounds(159, 48, 173, 30);
                meat_list.setVisible(true);

                meal_list.setSelectedIndex(0);
                meal_list.setBounds(159, 89, 173, 30);
                meal_list.setVisible(true);

                drinks_list.setSelectedIndex(0);
                drinks_list.setBounds(159, 134, 173, 30);
                drinks_list.setVisible(true);


                show_btn_m.setBounds(69, 180, 115, 37);
                show_btn_m.setVisible(true);
                total_btn_m.setBounds(224, 180, 145, 37);
                total_btn_m.setVisible(true);

                sp.add(year_list);
                sp.add(year_l);
                sp.add(month_list);
                sp.add(month_l);
                sp.add(meatCh);
                sp.add(meat_list);
                sp.add(mealCh);
                sp.add(meal_list);
                sp.add(drinkCh);
                sp.add(drinks_list);
                sp.add(show_btn_m);
                sp.add(total_btn_m);

                show_btn_m.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        model.setNumRows(0);
                        showM_month();
                    }
                });

                total_btn_m.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        model.setNumRows(0);
                        showTotal_month();
                    }
                });
            }
        });
    }

    //전체폰트설정
    private static void changeAllSwingComponentDefaultFont() {
        try {
            UIDefaults swingComponentDefaultTable = UIManager.getDefaults();
            Enumeration allDefaultKey = swingComponentDefaultTable.keys();
            while(allDefaultKey.hasMoreElements()) {
                String defaultKey = allDefaultKey.nextElement().toString();
                if(defaultKey.indexOf("font") != -1) {
                    Font newDefaultFont = new Font("나눔바른고딕", Font.PLAIN, 14);
                    UIManager.put(defaultKey, newDefaultFont);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setMeat(int num) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost/yumyum1";
            conn=DriverManager.getConnection(url,"root","19970");
            sql = "select * from meatmenu where gid="+num;
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery(sql);
            while(rs.next()) {
                meatmenu[num]=rs.getString("meat_name");
            }
        }catch(ClassNotFoundException ce) {
            System.out.println("드라이버로딩실패");
        }catch(SQLException e) {
            System.out.println("에러: "+e);
        }finally {
            try{
                if(conn!=null && !conn.isClosed()) {
                    conn.close();
                }
                if(pstmt!=null) try {pstmt.close();} catch(SQLException se) {}
            }catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void setMeal(int num) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost/yumyum1";
            conn=DriverManager.getConnection(url,"root","19970");
            sql = "select * from mealmenu where mid="+num;
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery(sql);
            while(rs.next()) {
                mealmenu[num]=rs.getString("meal_name");
            }
        }catch(ClassNotFoundException ce) {
            System.out.println("드라이버로딩실패");
        }catch(SQLException e) {
            System.out.println("에러: "+e);
        }finally {
            try{
                if(conn!=null && !conn.isClosed()) {
                    conn.close();
                }
                if(pstmt!=null) try {pstmt.close();} catch(SQLException se) {}
            }catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void setDrink(int num) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost/yumyum1";
            conn=DriverManager.getConnection(url,"root","19970");
            sql = "select * from drinks where dr_id="+num;
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery(sql);
            while(rs.next()) {
                drinks[num]=rs.getString("dr_name");
            }
        }catch(ClassNotFoundException ce) {
            System.out.println("드라이버로딩실패");
        }catch(SQLException e) {
            System.out.println("에러: "+e);
        }finally {
            try{
                if(conn!=null && !conn.isClosed()) {
                    conn.close();
                }
                if(pstmt!=null) try {pstmt.close();} catch(SQLException se) {}
            }catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void showM() {
        String ty=year_list.getSelectedItem().toString();
        String tm=month_list.getSelectedItem().toString(); //선택된 달
        String td=date_list.getSelectedItem().toString(); //선택된 일

        //선택된 메뉴
        //고기
        String meat = meat_list.getSelectedItem().toString();
        //식사류
        String meal = meal_list.getSelectedItem().toString();
        //음료
        String drink = drinks_list.getSelectedItem().toString();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost/yumyum1";
            conn=DriverManager.getConnection(url,"root","19970");
            String menu_name="";
            int count=0;
            int total=0;

            //or로 묶어주기 해당되는 것들 모두 JTable에 띄워주기
            sql="select  product,sum(count) count,sum(price*count) total from maechul_1 where m_year="+Integer.parseInt(ty)+" and m_month="+Integer.parseInt(tm)
                    +" and m_date="+Integer.parseInt(td)+" group by product having product='"+meat+"' or product='"+meal+"' or product='"+drink+"';";
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery(sql);
            while(rs.next()) {
                menu_name=rs.getString(1);
                count=rs.getInt(2);
                total = rs.getInt(3);
                Object data[]= {menu_name,count,total};
                model.addRow(data);
            }
        }catch(ClassNotFoundException e) {
            System.out.println("드라이버로딩실패");
        }catch(SQLException e) {
            System.out.println("에러: "+e);
        }finally {
            try{
                if(conn!=null && !conn.isClosed()) {
                    conn.close();
                }
                if(pstmt!=null) try {pstmt.close();} catch(SQLException se) {}
            }catch(SQLException se) {
                se.printStackTrace();
            }
        }

    }

    //달별 매출
    public void showM_month() {
        String ty=year_list.getSelectedItem().toString();
        String tm=month_list.getSelectedItem().toString(); //선택된 달
        String td=date_list.getSelectedItem().toString(); //선택된 일

        //선택된 메뉴
        //고기
        String meat = meat_list.getSelectedItem().toString();
        //식사류
        String meal = meal_list.getSelectedItem().toString();
        //음료
        String drink = drinks_list.getSelectedItem().toString();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost/yumyum1";
            conn=DriverManager.getConnection(url,"root","19970");
            String menu_name="";
            int count=0;
            int total=0;

            //or로 묶어주기 해당되는 것들 모두 JTable에 띄워주기
            sql="select  product,sum(count) count,sum(price*count) total from maechul_1 where m_year="+Integer.parseInt(ty)+" and m_month="+Integer.parseInt(tm)
                    +" group by product having product='"+meat+"' or product='"+meal+"' or product='"+drink+"';";
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery(sql);
            while(rs.next()) {
                menu_name=rs.getString(1);
                count=rs.getInt(2);
                total = rs.getInt(3);
                Object data[]= {menu_name,count,total};
                model.addRow(data);
            }
        }catch(ClassNotFoundException e) {
            System.out.println("드라이버로딩실패");
        }catch(SQLException e) {
            System.out.println("에러: "+e);
        }finally {
            try{
                if(conn!=null && !conn.isClosed()) {
                    conn.close();
                }
                if(pstmt!=null) try {pstmt.close();} catch(SQLException se) {}
            }catch(SQLException se) {
                se.printStackTrace();
            }
        }

    }


    public void showTotal() {
        String ty=year_list.getSelectedItem().toString();
        String tm=month_list.getSelectedItem().toString(); //선택된 달
        String td=date_list.getSelectedItem().toString(); //선택된 일
        //total_btn이 눌렸을 때
        //선택된 메뉴
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost/yumyum1";
            conn=DriverManager.getConnection(url,"root","19970");
            String menu_name="";
            int count=0;
            int total=0;
            sql = "select  product,sum(count) count,sum(price*count) total "
                    + "from maechul_1 where m_year="+Integer.parseInt(ty)
                    +" and m_month="+Integer.parseInt(tm)+" and m_date="+Integer.parseInt(td)+" group by product;";
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery(sql);
            while(rs.next()) {
                menu_name=rs.getString(1);
                count=rs.getInt(2);
                total = rs.getInt(3);
                Object data[]= {menu_name,count,total};
                model.addRow(data);
            }
        }catch(ClassNotFoundException e) {
            System.out.println("드라이버로딩실패");
        }catch(SQLException e) {
            System.out.println("에러: "+e);
        }finally {
            try{
                if(conn!=null && !conn.isClosed()) {
                    conn.close();
                }
                if(pstmt!=null) try {pstmt.close();} catch(SQLException se) {}
            }catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    //월별
    public void showTotal_month() {
        String ty=year_list.getSelectedItem().toString();
        String tm=month_list.getSelectedItem().toString(); //선택된 달
        String td=date_list.getSelectedItem().toString(); //선택된 일
        //total_btn이 눌렸을 때
        //선택된 메뉴
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost/yumyum1";
            conn=DriverManager.getConnection(url,"root","19970");
            String menu_name="";
            int count=0;
            int total=0;
            sql = "select  product,sum(count) count,sum(price*count) total "
                    + "from maechul_1 where m_year="+Integer.parseInt(ty)
                    +" and m_month="+Integer.parseInt(tm)+" group by product;";
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery(sql);
            while(rs.next()) {
                menu_name=rs.getString(1);
                count=rs.getInt(2);
                total = rs.getInt(3);
                Object data[]= {menu_name,count,total};
                model.addRow(data);
            }
        }catch(ClassNotFoundException e) {
            System.out.println("드라이버로딩실패");
        }catch(SQLException e) {
            System.out.println("에러: "+e);
        }finally {
            try{
                if(conn!=null && !conn.isClosed()) {
                    conn.close();
                }
                if(pstmt!=null) try {pstmt.close();} catch(SQLException se) {}
            }catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

}*/