import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.DonGiaDAO;
import DAO.menu;

@WebServlet("/xemdg")
public class xemdg extends HttpServlet {
    private static final long serialVersionUID = 1L;
    DonGiaDAO donGiaDAO = new DonGiaDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Quản lý Đơn Giá</title>");
        out.println("</head>");
        out.println("<body>");
        
        menu menu = new menu();
        menu.htmenu(res, out);

        out.println("<h2>Tìm Kiếm Đơn Giá</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaDonGia'>Mã Đơn Giá:</label>");
        out.println("  <input type='text' id='searchMaDonGia' name='searchMaDonGia' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        out.println("<h2>Nhập Dữ Liệu Đơn Giá</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='MaDonGia'>Mã Đơn Giá:</label>");
        out.println("  <input type='text' id='MaDonGia' name='MaDonGia' required><br>");

        out.println("  <label for='SoTien'>Số Tiền:</label>");
        out.println("  <input type='text' id='SoTien' name='SoTien' required><br>");

        out.println("  <label for='TuKW'>Từ KW:</label>");
        out.println("  <input type='text' id='TuKW' name='TuKW' required><br>");

        out.println("  <label for='DenKW'>Đến KW:</label>");
        out.println("  <input type='text' id='DenKW' name='DenKW' required><br>");

        out.println("  <label for='GhiChu'>Ghi Chú:</label>");
        out.println("  <input type='text' id='GhiChu' name='GhiChu' required><br>");

        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        // Display Đơn Giá data
        donGiaDAO.displayData(out);

        out.println("</body>");
        out.println("</html>");

        String searchMaDonGia = req.getParameter("searchMaDonGia");
        if (searchMaDonGia != null && !searchMaDonGia.isEmpty()) {
            donGiaDAO.TK(req, res, out);
        }
        
        donGiaDAO.xoa(req, res, out);
        donGiaDAO.nhap(req, res);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Nhập Dữ Liệu</title>");
        out.println("</head>");
        out.println("<body>");
        String MaDonGia = request.getParameter("MaDonGia");
        out.println("<h2>Nhập Dữ Liệu Đơn Giá</h2>");
        out.println("<form method='post'>");

        out.println("  <input type='hidden' name='MaDonGia' value='" + MaDonGia + "'/>");

        out.println("  <label for='SoTien'>Số Tiền:</label>");
        out.println("  <input type='text' id='SoTien' name='SoTien' required><br>");

        out.println("  <label for='TuKW'>Từ KW:</label>");
        out.println("  <input type='text' id='TuKW' name='TuKW' required><br>");

        out.println("  <label for='DenKW'>Đến KW:</label>");
        out.println("  <input type='text' id='DenKW' name='DenKW' required><br>");

        out.println("  <label for='GhiChu'>Ghi Chú:</label>");
        out.println("  <input type='text' id='GhiChu' name='GhiChu' required><br>");

        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");

        String strSoTien = request.getParameter("SoTien");
        String strTuKW = request.getParameter("TuKW");
        String strDenKW = request.getParameter("DenKW");
        String GhiChu = request.getParameter("GhiChu");

        if (strSoTien != null && strTuKW != null && strDenKW != null) {
            double SoTien = Double.parseDouble(strSoTien);
            int TuKW = Integer.parseInt(strTuKW);
            int DenKW = Integer.parseInt(strDenKW);
            donGiaDAO.sua(request, response, out);
        }
    }
}

