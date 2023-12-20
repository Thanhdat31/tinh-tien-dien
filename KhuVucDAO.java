package DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KhuVucDAO {
    Connection con;
    Statement stmt;
    String MaKhuVuc;
    String TenKhuVuc;
    String QuanHuyen;

    public KhuVucDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendien?useUnicode=true&characterEncoding=UTF-8", "root", "Dat080808");
    }

    private KhuVucDAO Parameter(HttpServletRequest request) {
        KhuVucDAO khuVuc = new KhuVucDAO();
        khuVuc.MaKhuVuc = request.getParameter("MaKhuVuc");
        khuVuc.TenKhuVuc = request.getParameter("TenKhuVuc");
        khuVuc.QuanHuyen = request.getParameter("QuanHuyen");

        return khuVuc;
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from KhuVuc";
            ResultSet rs = stmt.executeQuery(sql);
out.println("<table border=1 width=50% height=50%>");
out.println("<tr><th>Mã Khu Vực</th><th>Tên Khu Vực</th><th>Quận Huyện</th><th>Chức Năng</th></tr>");

while (rs.next()) {
    String MaKhuVuc = rs.getString("MaKhuVuc");
    String TenKhuVuc = rs.getString("TenKhuVuc");
    String QuanHuyen = rs.getString("QuanHuyen");

    out.println("<tr>");
    out.println("<td>" + MaKhuVuc + "</td>");
    out.println("<td>" + TenKhuVuc + "</td>");
    out.println("<td>" + QuanHuyen + "</td>");
    
    // Chức Năng column
    out.println("<td>");
    
    // Xóa link
    out.println("<form style='display: inline;' method='post'>");
    out.println("<input type='hidden' name='MaKhuVuc' value='" + MaKhuVuc + "'>");
    out.println("<input type='submit' value='Xóa'>");
    out.println("</form>");
    
    // Sửa form
    out.println("<form style='display: inline;' method='post'>");
    out.println("<input type='hidden' name='MaKhuVuc' value='" + MaKhuVuc + "'>");
    out.println("<input type='submit' value='Sửa'>");
    out.println("</form>");
    
    out.println("</td>");
    out.println("</tr>");
}

out.println("</table>");

            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
    }

    public void nhap(HttpServletRequest request, HttpServletResponse response) {
        try {
            openConnection();
            KhuVucDAO k = Parameter(request);
            String sql = "INSERT INTO KhuVuc (MaKhuVuc, TenKhuVuc, QuanHuyen) VALUES (?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, k.MaKhuVuc);
                statement.setString(2, k.TenKhuVuc);
                statement.setString(3, k.QuanHuyen);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
                    System.out.println("Dữ liệu đã được chèn thành công!");
                } else {
                    PrintWriter out = response.getWriter();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void xoatubang(String tableName, String columnName, String value) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, value);
            statement.executeUpdate();
        }
    }

    public void xoa(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            openConnection();
            String maKhuVuc = request.getParameter("MaKhuVucXoa");
            if (maKhuVuc != null && !maKhuVuc.isEmpty()) {
                con.setAutoCommit(false);

                try {
                    xoatubang("KhuVuc", "MaKhuVuc", maKhuVuc);
                    xoatubang("DienKe", "MaKhuVuc", maKhuVuc);
                    con.commit();
                    out.println("Xóa khu vực thành công!");
                } catch (Exception e) {
                    con.rollback();
                    out.println("Lỗi: " + e.getMessage());
                } finally {
                    con.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        } finally {
            out.close();
        }
    }

    public void sua(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            openConnection();
            KhuVucDAO k = Parameter(request);
            String sql = "UPDATE KhuVuc SET TenKhuVuc=?, QuanHuyen=? WHERE MaKhuVuc=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, k.TenKhuVuc);
                statement.setString(2, k.QuanHuyen);
                statement.setString(3, k.MaKhuVuc);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out = response.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                } else {
                    out = response.getWriter();
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Mã Khu Vực tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void TK(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}