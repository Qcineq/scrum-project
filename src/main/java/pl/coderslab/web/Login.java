package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;

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
    if(email!=null && password!=null){
        AdminDao adminDao = new AdminDao();
        if(adminDao.checkAdminPassword(email, password)){
            response.sendRedirect("http://localhost:8080/");
        } else {
            String wrongPassOrEmail = "Podano nieprawidłowe dane logowania. Spróbuj ponownie";
            request.setAttribute("wrongpass", wrongPassOrEmail);
            request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }
    }
}
