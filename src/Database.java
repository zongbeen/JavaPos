import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    Statement st;
    Connection conn;

    // 데이터베이스 연결
    public Database() throws SQLException {
        String user = "root";
        String password = "19970";
        String url = "jdbc:mysql://localhost:3306/JavaPos";
        conn = DriverManager.getConnection(url, user, password);
        st = conn.createStatement();
    }

    // 로그인
    public String checkLogin(String id, String pw) throws SQLException {
        String returnNum;
        String str = "Select count(*) from user where ID = '" + id + "' and PW = '" + pw + "';";

        staticMember(id,pw);
        ResultSet query = spendQuery(str);

        if (query.next()) {
            returnNum = query.getString(1);
        }
        return query.getString(1);
    }

    //정적 멤버
    public void staticMember(String id, String pw) throws SQLException {
        String[] staticMembers = {};
        String sql = "Select * from user where ID = '" + id + "' and PW = '" + pw + "';";
        ResultSet query1 = spendQuery(sql);

        while (query1.next()) {
            StaticMember.name = query1.getString(1);
            StaticMember.phone = query1.getString(2);
            StaticMember.ID = query1.getString(3);
            StaticMember.PW = query1.getString(4);
        }

        StaticMember.ID = id;
    }

    //정적 메뉴
    public void staticMenu(int categoryID) throws SQLException {
        String[] staticMenus = {};
        String sql = "Select * from menu where c_id = '" + categoryID + "';";
        ResultSet query1 = spendQuery(sql);

        while (query1.next()) {
            StaticMenu.productId = query1.getString(1);
            StaticMenu.productName = query1.getString(2);
            StaticMenu.productPrice = query1.getString(3);
            StaticMenu.c_id = query1.getString(4);
        }

        StaticMenu.c_id = String.valueOf(categoryID);
    }

    // 카테고리
    public String[] insertCategory(String categorName) throws SQLException {
        int a = 0;
        String sql = "";
        int i = 0;
        if (categorName.equals("커피")) {
            sql = "select productName from menu where c_id = 1";
            a = countMenu(1);
        } else if (categorName.equals("음료")) {
            sql = "select productName from menu where c_id = 2";
            a = countMenu(2);
        } else if (categorName.equals("스무디&쉐이크")) {
            sql = "select productName from menu where c_id = 3";
            a = countMenu(3);
        } else if (categorName.equals("차")) {
            sql = "select productname from menu where c_id = 4";
            a = countMenu(4);
        }

        ResultSet query = spendQuery(sql);

        String[] menuName = new String[a];

        if (query.next()) {
            for (i = 0; query.next(); i++) {
                menuName[i] = query.getString("productName");
            }
        }
        return menuName;
    }

    // 카테고리에 해당하는 메뉴의 개수
    public int countMenu(int a) throws SQLException {
        int returnNum = 0;
        String str = "Select count(*) from menu where c_id ='" + a + "';";
        ResultSet query = spendQuery(str);
        if (query.next()) {
            returnNum = query.getInt(1);
        }
        return returnNum;
    }

    // 회원가입
    public int insertSign(String[] infor) throws SQLException {
        String str = "insert into user values(?,?,?,?)";

        int query = spendUpdate(str, infor);

        return query;
    }

    //회원 정보 수정
    public int updateSign(String[] infor) throws SQLException {
        String str = "update user set name = ?,phone = ?, pw = ? where id = ?;";

        int query = spendUpdate(str, infor);

        return query;
    }

    //메뉴 목록 수정
    public int updateMenu(String[] infor) throws SQLException {
        String str = "update menu set productName = ?, productPrice = ? where productId = ?;";

        int query = spendUpdate(str, infor);

        return query;
    }

    private ResultSet spendQuerys(String str, String[] infor) {
        ResultSet query = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(str);
            for (int i = 1; i < infor.length + 1; i++) {
                pstmt.setString(i, infor[i - 1]);
            }
            query = pstmt.executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return query;
    }

    private int spendUpdate(String str, String[] infor) {
        int query = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(str);
            for (int i = 0; i < infor.length; i++) {
                pstmt.setString(i + 1, infor[i]);
            }
            query = pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return query;
    }

    private ResultSet spendQuery(String str) {
        ResultSet query = null;
        try {
            query = st.executeQuery(str);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(query);
        return query;
    }

    public int menuPrice(String productName) throws SQLException {
        int price = 0;
        String sql = "select productPrice from menu where productName = '" + productName + "';";
        ResultSet query = spendQuery(sql);
        if (query.next()) {
            price = query.getInt(1);
        }

        return price;
    }


    public String saleCode() throws SQLException {
        String code = null;
        String str = "select max(saleCode) from sales";
        ResultSet query = spendQuery(str);
        if (query.next()) {
            code = query.getString(1);
        }

        return code;
    }

    // 주문한 메뉴 디비에 삽입
    public int insertBreakdown(String menuName, String menuCount, String menuPrice) throws SQLException {
        String str = "insert into salesArray (saleCode, saleName, saleCount, salePrice) values(?,?,?,?)";

        String code1 = saleCode();
        String[] info = { code1, menuName, menuCount, menuPrice };
        int query = 0;
        query = spendUpdate(str, info);

        return query;

    }

    //주문한 메뉴 디비 삽입
    public int insertTotal(String totalPrice) throws SQLException {
      String str = "insert into sales (totalPrice) values(?)";
      String[] info = {totalPrice};

      int query = spendUpdate(str, info);
      return query;

   }

   //일매출 검색
    public String[][] dailySale(String year, String month, String day) throws SQLException {
        ArrayList<String[]> a =new ArrayList<>();
        String sql = "select saleName, sum(saleCount), sum(salePrice) from sales as s, salesArray as sa where s.saleCode = sa.saleCode " +
                " and date_format(saleTime,'%Y') = "+ year +
                " and date_format(saleTime,'%m') = "+ month +
                " and date_format(saleTime,'%d') = "+ day + " group by (saleName);";

        ResultSet query = spendQuery(sql);
        while (query.next()) {
            System.out.println(query.getString(1));
            a.add(new String[]{query.getString(1),query.getString(2),query.getString(3)});
        }

        return a.toArray(new String [a.size()][]);
    }
    //월매출 검색
    public String[][] monthlySale(String year, String month) throws SQLException {
        ArrayList<String[]>b  =new ArrayList<>();
        String sql = "select saleName, sum(saleCount), sum(salePrice) from sales as s, salesArray as sa where s.saleCode = sa.saleCode " +
                " and date_format(saleTime,'%Y') = "+ year +
                " and date_format(saleTime,'%m') = "+ month + " group by (saleName);";

        ResultSet query = spendQuery(sql);
        while (query.next()) {
            System.out.println(query.getString(1));
            b.add(new String[]{query.getString(1),query.getString(2),query.getString(3)});
        }

        return b.toArray(new String [b.size()][]);
    }

    //월매출 가격
    public int monthlyPrice(String year, String month) throws SQLException {
        int monthPrice = 0;
        String sql = "select * from sales where saleCode = saleCode " +
                " and date_format(saleTime,'%Y') = " + year +
                " and date_format(saleTime,'%m') = " + month + ";";

        ResultSet query = spendQuery(sql);
        while (query.next()) {
            monthPrice += query.getInt(3);
        }
        return monthPrice;
    }

    //일매출 가격
    public int dailyPrice(String year, String month, String day) throws SQLException {
        int dayPrice = 0;
        String sql = "select * from sales where saleCode = saleCode " +
                " and date_format(saleTime,'%Y') = " + year +
                " and date_format(saleTime,'%m') = " + month +
                " and date_format(saleTime,'%d') = "+ day + ";";

        ResultSet query = spendQuery(sql);
        while (query.next()) {
           dayPrice += query.getInt(3);
        }
        return dayPrice;
    }

    //
    public String[][] searchDel(String year, String month, String day) throws SQLException {
        ArrayList<String[]> sd =new ArrayList<>();
        String sql = "select * from sales where saleCode = saleCode" +
                " and date_format(saleTime,'%Y') = "+ year +
                " and date_format(saleTime,'%m') = "+ month +
                " and date_format(saleTime,'%d') = "+ day + " ;";

        ResultSet query = spendQuery(sql);
        while (query.next()) {
            System.out.println(query.getString(1));
            sd.add(new String[]{query.getString(1),query.getString(2),query.getString(3)});
        }

        return sd.toArray(new String [sd.size()][]);
    }

    public int deleteSale(String code) throws SQLException {
        String str = "delete from sales where saleCode = " + code + " ;";

        int query = spendUpdate(str, new String[]{});

        return query;
    }

    public String[][] allMenu() throws SQLException {
        ArrayList<String[]> am =new ArrayList<>();
        String sql = "select * from menu;";

        ResultSet query = spendQuery(sql);
        while (query.next()) {
            System.out.println(query.getString(1));
            am.add(new String[]{query.getString(1),query.getString(2),
                    query.getString(3),query.getString(4)});
        }

        return am.toArray(new String [am.size()][]);
    }

    public String[][] allMember() throws SQLException {
        ArrayList<String[]> am =new ArrayList<>();
        String sql = "select * from user;";

        ResultSet query = spendQuery(sql);
        while (query.next()) {
            System.out.println(query.getString(1));
            am.add(new String[]{query.getString(1),query.getString(2),
                    query.getString(3),query.getString(4)});
        }

        return am.toArray(new String [am.size()][]);
    }
}