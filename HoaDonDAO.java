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

public class HoaDonDAO {
    Connection con;
    Statement stmt;
    String SoHoaDon;
    String Thang;
    String MaDienKe;
    double ThanhTien;

    public HoaDonDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendien?useUnicode=true&characterEncoding=UTF-8", "root", "Dat080808");
    }

    private HoaDonDAO Parameter(HttpServletRequest request) {
        HoaDonDAO hoaDon = new HoaDonDAO();
        hoaDon.SoHoaDon = request.getParameter("SoHoaDon");
        hoaDon.Thang = request.getParameter("Thang");
        hoaDon.MaDienKe = request.getParameter("MaDienKe");
        hoaDon.ThanhTien = Double.parseDouble(request.getParameter("ThanhTien"));

        return hoaDon;
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();

            // Display HoaDon data
            String sql = "select * from HoaDon";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<h2>HoaDon Table</h2>");
            displayHoaDonData(out, rs);

            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
    }

    private void displayHoaDonData(PrintWriter out, ResultSet rs) throws SQLException {
        // Display HoaDon data
        out.println("<table border=1 width=50% height=50%>");
        out.println("<tr><th>Số Hóa Đơn</th><th>Tháng</th><th>Mã Điện Kế</th><th>Thành Tiền</th><th>Chức Năng</th></tr>");

        while (rs.next()) {
            String SoHoaDon = rs.getString("SoHoaDon");
            String Thang = rs.getString("Thang");
            String MaDienKe = rs.getString("MaDienKe");
            double ThanhTien = rs.getDouble("ThanhTien");

            out.println("<tr>");
            out.println("<td>" + SoHoaDon + "</td>");
            out.println("<td>" + Thang + "</td>");
            out.println("<td>" + MaDienKe + "</td>");
            out.println("<td>" + ThanhTien + "</td>");

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
    }

    public void nhap(HttpServletRequest request, HttpServletResponse response) {
        try {
            openConnection();
            HoaDonDAO hoaDon = Parameter(request);
            String sql = "INSERT INTO HoaDon (SoHoaDon, Thang, MaDienKe, ThanhTien) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, hoaDon.SoHoaDon);
                statement.setString(2, hoaDon.Thang);
                statement.setString(3, hoaDon.MaDienKe);
                statement.setDouble(4, hoaDon.ThanhTien);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
                    System.out.println("Dữ liệu HoaDon đã được chèn thành công!");
                } else {
                    PrintWriter out = response.getWriter();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void xoatubang(String tableName, String columnName, String value) throws Exception {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, value);
            statement.executeUpdate();}
    }
    public void xoa(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            openConnection();
            String soHoaDon = request.getParameter("SoHoaDonXoa");
            if (soHoaDon != null && !soHoaDon.isEmpty()) {
                con.setAutoCommit(false);

                try {
                    xoatubang("HoaDon", "SoHoaDon", soHoaDon);
                    con.commit();
                    out.println("Xóa hóa đơn thành công!");
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
            HoaDonDAO hoaDon = Parameter(request);
            String sql = "UPDATE HoaDon SET Thang=?, MaDienKe=?, ThanhTien=? WHERE SoHoaDon=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, hoaDon.Thang);
                statement.setString(2, hoaDon.MaDienKe);
                statement.setDouble(3, hoaDon.ThanhTien);
                statement.setString(4, hoaDon.SoHoaDon);

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

    public void TK(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        // Implement search logic based on your requirements
        // You may use request parameters to get search criteria and perform a database query
        throw new UnsupportedOperationException("Not supported yet.");
    }

 
    }

   





