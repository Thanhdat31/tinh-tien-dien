package DAO;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class menu {
    public void htmenu(HttpServletResponse res,PrintWriter out)
    {   
        out.println("<div style='display: flex; justify-content: space-around; background-color: #f1f1f1; padding: 10px;'>");
        out.println("<a href='xemds' style='text-decoration: none; color: #333;'>Khách Hàng</a>");
        out.println("<a href='xemkv' style='text-decoration: none; color: #333;'>Khu Vực</a>");
        out.println("<a href='xemdk' style='text-decoration: none; color: #333;'>Điện Kế</a>");
        out.println("<a href='xemhd' style='text-decoration: none; color: #333;'>Hóa Đơn</a>");
        out.println("<a href='xemcthd' style='text-decoration: none; color: #333;'>Chi Tiết Hóa Đơn</a>");
        out.println("<a href='xemdg' style='text-decoration: none; color: #333;'>Đơn Giá</a>");
        out.println("<a href='index.jsp' style='text-decoration: none; color: #333;'>Đăng xuất </a>");
        out.println("</div>");   
    }
}