package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

@WebServlet(name = "AddSchedules", value = "/app/plan/add")
public class AddSchedules extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");
        response.setContentType("text/html");
        request.getServletContext().getRequestDispatcher("/app-add-schedules.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf8");
        HttpSession session = request.getSession();

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        LocalDateTime localDateTime = LocalDateTime.now();

        Plan newPlan = new Plan();
        newPlan.setName(name);
        newPlan.setDescription(description);
        newPlan.setCreated(String.valueOf(localDateTime));

        AdminDao adminDao = new AdminDao();
        String email = (String) session.getAttribute("email");
        Admin admin = adminDao.readAdminByEmail(email);
        newPlan.setAdmin_id(admin.getId());
        PlanDao planDao = new PlanDao();
        newPlan = planDao.create(newPlan);
        System.out.println(newPlan);
        response.sendRedirect("/app/plan/list");

    }
}
