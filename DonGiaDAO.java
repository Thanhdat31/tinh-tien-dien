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

public class DonGiaDAO {
    Connection con;
    Statement stmt;
    String MaDonGia;
    double SoTien;
    int TuKW;
    int DenKW;
    String GhiChu;

    public DonGiaDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendien?useUnicode=true&characterEncoding=UTF-8", "root", "Dat080808");
    }

    private DonGiaDAO Parameter(HttpServletRequest request) {
        DonGiaDAO donGia = new DonGiaDAO();
        donGia.MaDonGia = request.getParameter("MaDonGia");
        donGia.SoTien = Double.parseDouble(request.getParameter("SoTien"));
        donGia.TuKW = Integer.parseInt(request.getParameter("TuKW"));
        donGia.DenKW = Integer.parseInt(request.getParameter("DenKW"));
        donGia.GhiChu = request.getParameter("GhiChu");

        return donGia;
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM DonGia";
            ResultSet rs = stmt.executeQuery(sql);

            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Mã Đơn Giá</th><th>Số Tiền</th><th>Từ KW</th><th>Đến KW</th><th>Ghi Chú</th><th>Chức Năng</th></tr>");

            while (rs.next()) {
                String MaDonGia = rs.getString("MaDonGia");
                double SoTien = rs.getDouble("SoTien");
                int TuKW = rs.getInt("TuKW");
                int DenKW = rs.getInt("DenKW");
                String GhiChu = rs.getString("GhiChu");

                out.println("<tr>");
                out.println("<td>" + MaDonGia + "</td>");
                out.println("<td>" + SoTien + "</td>");
                out.println("<td>" + TuKW + "</td>");
                out.println("<td>" + DenKW + "</td>");
                out.println("<td>" + GhiChu + "</td>");

                // Chức Năng column
                out.println("<td>");

                // Xóa link
                out.println("<form style='display: inline;' method='post' action='DonGia?MaDonGiaXoa=" + MaDonGia + "'>");
                out.println("<input type='hidden' name='MaDonGia' value='" + MaDonGia + "'>");
                out.println("<input type='submit' value='Xóa'>");
                out.println("</form>");

                // Sửa form
                out.println("<form style='display: inline;' method='post' action=''>");
                out.println("<input type='hidden' name='MaDonGia' value='" + MaDonGia + "'>");
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
            DonGiaDAO d = Parameter(request);
            String sql = "INSERT INTO DonGia (MaDonGia, SoTien, TuKW, DenKW, GhiChu) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, d.MaDonGia);
                statement.setDouble(2, d.SoTien);
                statement.setInt(3, d.TuKW);
                statement.setInt(4, d.DenKW);
                statement.setString(5, d.GhiChu);

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

    public void xoatubang(String tableName, String columnName, String value) throws Exception {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, value);
            statement.executeUpdate();
        }
    }

    public void xoa(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            openConnection();
            String maDonGia = request.getParameter("MaDonGiaXoa");
            if (maDonGia != null && !maDonGia.isEmpty()) {
                con.setAutoCommit(false);

                try {
                    xoatubang("DonGia", "MaDonGia", maDonGia);
                    // Thêm các hoạt động xóa từ các bảng khác nếu cần

                    con.commit();
                    out.println("Xóa đơn giá thành công!");
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

    public void TK(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchMaDonGia = req.getParameter("searchMaDonGia");
    try {
        openConnection();
        String sql = "SELECT * FROM DonGia WHERE MaDonGia=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchMaDonGia);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body");
                out.println("table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table %>");
                out.println("<tr><th>Mã Đơn Giá</th><th>Số Tiền</th><th>Từ KW</th><th>Đến KW</th><th>Ghi Chú</th><th>Chức Năng</th></tr>");
                out.println("<tr>");
                out.println("<td>" + resultSet.getString("MaDonGia") + "</td>");
                out.println("<td>" + resultSet.getDouble("SoTien") + "</td>");
                out.println("<td>" + resultSet.getInt("TuKW") + "</td>");
                out.println("<td>" + resultSet.getInt("DenKW") + "</td>");
                out.println("<td>" + resultSet.getString("GhiChu") + "</td>");

                // Chức Năng column
                out.println("<td>");
                
                // Xóa link
                out.println("<form style='display: inline;' method='post' action='DonGia?MaDonGiaXoa=" + resultSet.getString("MaDonGia") + "'>");
                out.println("<input type='hidden' name='MaDonGia' value='" + resultSet.getString("MaDonGia") + "'>");
                out.println("<input type='submit' value='Xóa'>");
                out.println("</form>");

                // Sửa form
                out.println("<form style='display: inline;' method='post' action=''>");
                out.println("<input type='hidden' name='MaDonGia' value='" + resultSet.getString("MaDonGia") + "'>");
                out.println("<input type='submit' value='Sửa'>");
                out.println("</form>");

                out.println("</td>");
                out.println("</tr>");
            } else {
                out.println("<p>Không tìm thấy Đơn Giá có Mã: " + searchMaDonGia + "</p>");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}

    public void sua(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            openConnection();
            DonGiaDAO d = Parameter(request);
            String sql = "UPDATE DonGia SET SoTien=?, TuKW=?, DenKW=?, GhiChu=? WHERE MaDonGia=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setDouble(1, d.SoTien);
                statement.setInt(2, d.TuKW);
                statement.setInt(3, d.DenKW);
                statement.setString(4, d.GhiChu);
                statement.setString(5, d.MaDonGia);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out = response.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                } else {
                    out = response.getWriter();
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Mã Đơn Giá tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
}

