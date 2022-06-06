package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    HttpSession session = request.getSession();
    if(email!=null && password!=null){
        AdminDao adminDao = new AdminDao();
        if(adminDao.checkAdminPassword(email, password)){
            session.setAttribute("email", email);
            session.setAttribute("password", password);
            Admin admin = adminDao.readAdminByEmail(email);
            session.setAttribute("name", admin.getFirstName());
            response.sendRedirect("http://localhost:8080/app/dashboard");
        } else {
            String wrongPassOrEmail = "Podano nieprawidłowe dane logowania. Spróbuj ponownie";
            request.setAttribute("wrongpass", wrongPassOrEmail);
            request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }
    }
}
