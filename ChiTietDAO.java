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

public class ChiTietDAO {
    Connection con;
    Statement stmt;
    String SoHoaDon;
    String MaDonGia;
    int SoLuongKW;

    public ChiTietDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendien?useUnicode=true&characterEncoding=UTF-8", "root", "Dat080808");
    }

    private ChiTietDAO Parameter(HttpServletRequest request) {
        ChiTietDAO chiTiet = new ChiTietDAO();
        chiTiet.SoHoaDon = request.getParameter("SoHoaDon");
        chiTiet.MaDonGia = request.getParameter("MaDonGia");
        chiTiet.SoLuongKW = Integer.parseInt(request.getParameter("SoLuongKW"));

        return chiTiet;
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM ChiTietHoaDon";
            ResultSet rs = stmt.executeQuery(sql);
            
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Số Hóa Đơn</th><th>Mã Đơn Giá</th><th>Số Lượng KW</th><th>Chức Năng</th></tr>");

            while (rs.next()) {
                String SoHoaDon = rs.getString("SoHoaDon");
                String MaDonGia = rs.getString("MaDonGia");
                int SoLuongKW = rs.getInt("SoLuongKW");

                out.println("<tr>");
                out.println("<td>" + SoHoaDon + "</td>");
                out.println("<td>" + MaDonGia + "</td>");
                out.println("<td>" + SoLuongKW + "</td>");

                // Chức Năng column
                out.println("<td>");

                // Xóa link
                out.println("<form style='display: inline;' method='post'>");
                out.println("<input type='hidden' name='SoHoaDon' value='" + SoHoaDon + "'>");
                out.println("<input type='submit' value='Xóa'>");
                out.println("</form>");

                // Sửa form
                out.println("<form style='display: inline;' method='post'>");
                out.println("<input type='hidden' name='SoHoaDon' value='" + SoHoaDon + "'>");
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

    public void nhap(HttpServletRequest request, HttpServletResponse response) {
        try {
            openConnection();
            ChiTietDAO chiTiet = Parameter(request);
            String sql = "INSERT INTO ChiTietHoaDon (SoHoaDon, MaDonGia, SoLuongKW) VALUES (?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, chiTiet.SoHoaDon);
                statement.setString(2, chiTiet.MaDonGia);
                statement.setInt(3, chiTiet.SoLuongKW);

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
            String soHoaDon = request.getParameter("SoHoaDonXoa");
            if (soHoaDon != null && !soHoaDon.isEmpty()) {
                con.setAutoCommit(false);

                try {
                    xoatubang("ChiTietHoaDon", "SoHoaDon", soHoaDon);
                    con.commit();
                    out.println("Xóa chi tiết thành công!");
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
            ChiTietDAO chiTiet = Parameter(request);
            String sql = "UPDATE ChiTietHoaDon SET MaDonGia=?, SoLuongKW=? WHERE SoHoaDon=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, chiTiet.MaDonGia);
                statement.setInt(2, chiTiet.SoLuongKW);
                statement.setString(3, chiTiet.SoHoaDon);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out = response.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                } else {
                    out = response.getWriter();
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Số Hóa Đơn tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void tk(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}

