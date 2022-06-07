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

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        HttpSession session = request.getSession();


        if (name != "" && surname != "" && email != "" && password != "") {

            AdminDao adminDao = new AdminDao();
            response.sendRedirect("http://localhost:8080/login");

        } else {
            String wrongPassOrEmail = "Podano nieprawidłowe dane. Spróbuj ponownie";
            request.setAttribute("wrongpass", wrongPassOrEmail);
            request.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
        }

    }
}