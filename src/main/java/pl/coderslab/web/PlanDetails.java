package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "PlanDetails", value = "/app/plan/details")
public class PlanDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer planId = null;
        try {
            planId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.getWriter().append("Nie odnaleziono podanego planu");
            return;
        }
        if (planId != null) {
            PlanDao planDao = new PlanDao();
            Plan plan = planDao.read(planId);
            request.setAttribute("plan", plan);
            Map<String, List<RecipePlan>> recipePlanForEveryDay = RecipePlanDao.getPlanDetails(plan.getId());
            request.setAttribute("recipePlanForEveryDay", recipePlanForEveryDay);
            request.getServletContext().getRequestDispatcher("/app-details-schedules.jsp").forward(request, response);
        }
    }
}
