package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Register", value = "/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("utf8");
    response.setContentType("text/html");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        HttpSession session = request.getSession();


        if (name != "" && surname != "" && email != "" && password != "") {
            if (password.equals(repassword)) {
                AdminDao adminDao = new AdminDao();
                Admin newAdmin = new Admin();
                newAdmin.setFirstName(name);
                newAdmin.setLastName(surname);
                newAdmin.setEmail(email);
                newAdmin.setPassword(password);
                newAdmin = adminDao.createAdmin(newAdmin);
                response.sendRedirect("http://localhost:8080/login");
            } else {
                response.getWriter()
                        .append("podane hasła nie pokrywają się. <br>")
                        .append("<a href='http://localhost:8080/register'>wróć</a>");
                return;
            }
        } else {
            String wrongPassOrEmail = "Podano nieprawidłowe dane. Spróbuj ponownie";
            request.setAttribute("wrongpass", wrongPassOrEmail);
            request.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
        }

    }
}