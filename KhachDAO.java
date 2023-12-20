package DAO;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class KhachDAO {
        Connection con ;
        Statement stmt ;
        String MaKH ;
        String DiaChi ;
        String SoDienThoai ;

    public KhachDAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendien?useUnicode=true&characterEncoding=UTF-8", "root", "Dat080808");
        }
        private KhachDAO Parameter (HttpServletRequest request) {
        KhachDAO khach = new KhachDAO();
            khach.MaKH = (request.getParameter("MaKH"));
            khach.DiaChi = request.getParameter("DiaChi");
            khach.SoDienThoai = request.getParameter("SoDienThoai");
        try {
           
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return khach;
    }
        public void displayData(PrintWriter out) 
        {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from KhachHang";
            ResultSet rs = stmt.executeQuery(sql);
out.println("<table border=1 width=50% height=50%>");
out.println("<tr><th>Mã Khách Hàng</th><th>Địa Chỉ</th><th>Số Điện Thoại</th><th>Chức Năng</th></tr>");

while (rs.next()) {
    String MaKH = rs.getString("MaKH");
    String DiaChi = rs.getString("DiaChi");
    String SoDienThoai = rs.getString("SoDienThoai");

    out.println("<tr>");
    out.println("<td>" + MaKH + "</td>");
    out.println("<td>" + DiaChi + "</td>");
    out.println("<td>" + SoDienThoai + "</td>");

    // Chức Năng column with two small cells
    out.println("<td>");

    // Xóa link
    out.println("<form style='display: inline;' method='post' action='tiendien?MaKHXoa=" + MaKH + "'>");
    out.println("<input type='hidden' name='MaKH' value='" + MaKH + "'>");
    out.println("<input type='submit' value='Xóa'>");
    out.println("</form>");

    // Sửa form
    out.println("<form style='display: inline;' method='post' action=''>");
    out.println("<input type='hidden' name='MaKH' value='" + MaKH + "'>");
    out.println("<input type='submit' value='Sửa'>");
    out.println("</form>");

    out.println("</td>");
    out.println("</tr>");
}

out.println("</table>");


            con.close();
            } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
            }
        }
        public void nhap(HttpServletRequest request, HttpServletResponse response)
        {
        try {
           openConnection();
           KhachDAO k = Parameter(request);
           String sql = "INSERT INTO KhachHang (MaKH ,DiaChi ,SoDienThoai) VALUES (?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql))
            {   
                statement.setString(1, k.MaKH);
                statement.setString(2, k.DiaChi);
                statement.setString(3, k.SoDienThoai);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
         
                    System.out.println("Dữ liệu đã được chèn thành công!");
                } 
                else 
                {
                    PrintWriter out = response.getWriter();
                  
                }
            }
            } catch (Exception e) 
            {
                   e.printStackTrace();
                    
            }
        } 
       public void xoatubang(String tableName, String columnName, String value) throws Exception 
       {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) 
            {
            statement.setString(1, value);
            statement.executeUpdate();
            }
       }
        public void xoa(HttpServletRequest request, HttpServletResponse response ,PrintWriter out)
        {
           try {
            openConnection();

            
            String maKhach = request.getParameter("MaKHXoa");
            if (maKhach != null && !maKhach.isEmpty()) {
    

                con.setAutoCommit(false);

                try {
                    xoatubang("DienKe", "MaKH", maKhach);
                    xoatubang("KhachHang", "MaKH", maKhach);
                    
                    con.commit();

                    out.println("Xóa khách hàng thành công!");
                } catch (Exception e) {
                    // Rollback the transaction if an error occurs
                    con.rollback();
                    out.println("Lỗi: " + e.getMessage());
                } finally {
                    // Reset auto-commit to true
                    con.setAutoCommit(true);
                }
            } 
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        } finally {
            
            out.close();
        }
    }
        public void TK(HttpServletRequest req, HttpServletResponse res ,PrintWriter out)
        {
        String searchMaKH = req.getParameter("searchMaKH");
        try {
            openConnection();
            String sql = "SELECT * FROM KhachHang WHERE MaKH=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchMaKH);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Mã Khách Hàng</th><th>Địa Chỉ</th><th>Số Điện Thoại</th><th>Chức Năng</tr>");
                    out.println("<tr><td>" + resultSet.getString("MaKH") + "</td><td>" + resultSet.getString("DiaChi") + "</td><td>" + resultSet.getString("SoDienThoai") + "</td>"+"<td><a href='KhachHang?MaKHXoa=" + resultSet.getString("MaKH") + "'>Xóa</a></td>"
                    +"<td><form method='post'>" +
                    "<input type='hidden' name='MaKH' value='" + resultSet.getString("MaKH") + "'>" +
                    "<input type='submit' value='Sửa'>" +
                    "</form></td></tr>");
                } else {
                 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        }
        public void sua(HttpServletRequest req, HttpServletResponse res ,PrintWriter out)
        {
        res.setContentType("text/html");
        try {
            openConnection();
            KhachDAO k = Parameter(req);
            String sql = "UPDATE KhachHang SET DiaChi=?,SoDienThoai=? WHERE MaKH=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                
                statement.setString(1, k.DiaChi);
                statement.setString(2, k.SoDienThoai);
                statement.setString(3, k.MaKH);
                
               
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out = res.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                }else {
                    out = res.getWriter();
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Mã Khách tồn tại.");
                }
           
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        }
        
}