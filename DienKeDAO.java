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

public class DienKeDAO {
    Connection con;
    Statement stmt;
    String MaDienKe;
    String HieuDienThe;
    String MaKH;
    String MaKhuVuc;
    String NuocSanXuat;
    String GhiChu;

    public DienKeDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendien?useUnicode=true&characterEncoding=UTF-8", "root", "Dat080808");
    }

    private DienKeDAO Parameter(HttpServletRequest request) {
        DienKeDAO dienKe = new DienKeDAO();
        dienKe.MaDienKe = request.getParameter("MaDienKe");
        dienKe.HieuDienThe = request.getParameter("HieuDienThe");
        dienKe.MaKH = request.getParameter("MaKH");
        dienKe.MaKhuVuc = request.getParameter("MaKhuVuc");
        dienKe.NuocSanXuat = request.getParameter("NuocSanXuat");
        dienKe.GhiChu = request.getParameter("GhiChu");

        return dienKe;
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM DienKe";
            ResultSet rs = stmt.executeQuery(sql);
out.println("<table border=1 width=50% height=50%>");
out.println("<tr><th>Mã Điện Kế</th><th>Hiệu Điện Thế</th><th>Mã Khách Hàng</th><th>Mã Khu Vực</th><th>Nước Sản Xuất</th><th>Ghi Chú</th><th>Chức Năng</th></tr>");

while (rs.next()) {
    String MaDienKe = rs.getString("MaDienKe");
    String HieuDienThe = rs.getString("HieuDienThe");
    String MaKH = rs.getString("MaKH");
    String MaKhuVuc = rs.getString("MaKhuVuc");
    String NuocSanXuat = rs.getString("NuocSanXuat");
    String GhiChu = rs.getString("GhiChu");

    out.println("<tr>");
    out.println("<td>" + MaDienKe + "</td>");
    out.println("<td>" + HieuDienThe + "</td>");
    out.println("<td>" + MaKH + "</td>");
    out.println("<td>" + MaKhuVuc + "</td>");
    out.println("<td>" + NuocSanXuat + "</td>");
    out.println("<td>" + GhiChu + "</td>");

    // Chức Năng column with two small cells
    out.println("<td>");
    
    // Xóa link
    out.println("<form style='display: inline;' method='post' action='tiendien?MaDienKeXoa=" + MaDienKe + "'>");
    out.println("<input type='hidden' name='MaDienKe' value='" + MaDienKe + "'>");
    out.println("<input type='submit' value='Xóa'>");
    out.println("</form>");

    // Sửa form
    out.println("<form style='display: inline;' method='post' action=''>");
    out.println("<input type='hidden' name='MaDienKe' value='" + MaDienKe + "'>");
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
            DienKeDAO dk = Parameter(request);
            String sql = "INSERT INTO DienKe (MaDienKe, HieuDienThe, MaKH, MaKhuVuc, NuocSanXuat, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, dk.MaDienKe);
                statement.setString(2, dk.HieuDienThe);
                statement.setString(3, dk.MaKH);
                statement.setString(4, dk.MaKhuVuc);
                statement.setString(5, dk.NuocSanXuat);
                statement.setString(6, dk.GhiChu);

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

            String maDienKe = request.getParameter("MaDienKeXoa");
            if (maDienKe != null && !maDienKe.isEmpty()) {
                con.setAutoCommit(false);

                try {
                    xoatubang("DienKe", "MaDienKe", maDienKe);
                    con.commit();

                    out.println("Xóa điện kế thành công!");
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
        String searchMaDienKe = req.getParameter("searchMaDienKe");
        try {
            openConnection();
            String sql = "SELECT * FROM DienKe WHERE MaDienKe=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchMaDienKe);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Mã Điện Kế</th><th>Hiệu Điện Thế</th><th>Mã Khách Hàng</th><th>Mã Khu Vực</th><th>Nước Sản Xuất</th><th>Ghi Chú</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("MaDienKe") + "</td><td>" + resultSet.getString("HieuDienThe") + "</td><td>" + resultSet.getString("MaKH") + "</td><td>" + resultSet.getString("MaKhuVuc") + "</td><td>" + resultSet.getString("NuocSanXuat") + "</td><td>" + resultSet.getString("GhiChu") + "</td>"
                            + "<td><a href='DienKe?MaDienKeXoa=" + resultSet.getString("MaDienKe") + "'>Xóa</a></td>"
                            + "<td><form method='post'>" +
                            "<input type='hidden' name='MaDienKe' value='" + resultSet.getString("MaDienKe") + "'>" +
                            "<input type='submit' value='Sửa'>" +
                            "</form></td></tr>");
                } else {
                    // Handle when no records are found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void sua(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        res.setContentType("text/html");
        try {
            openConnection();
            DienKeDAO dk = Parameter(req);
            String sql = "UPDATE DienKe SET HieuDienThe=?, MaKH=?, MaKhuVuc=?, NuocSanXuat=?, GhiChu=? WHERE MaDienKe=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {

                statement.setString(1, dk.HieuDienThe);
                statement.setString(2, dk.MaKH);
                statement.setString(3, dk.MaKhuVuc);
                statement.setString(4, dk.NuocSanXuat);
                statement.setString(5, dk.GhiChu);
                statement.setString(6, dk.MaDienKe);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out = res.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                } else {
                    out = res.getWriter();
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Mã Điện Kế tồn tại.");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
