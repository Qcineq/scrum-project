package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Plan", value = "/app/plan/list")
public class Plans extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        HttpSession session = request.getSession();
        AdminDao adminDao = new AdminDao();
        Admin loggedAdmin = adminDao.readAdminByEmail((String)session.getAttribute("email"));
        PlanDao planDao = new PlanDao();
        List<Plan> planList = planDao.readAllPlansByAdminId(loggedAdmin.getId());
        request.setAttribute("planList", planList);
    request.getServletContext().getRequestDispatcher("/app-schedules.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
