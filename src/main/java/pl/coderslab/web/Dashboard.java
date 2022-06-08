package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Dashboard", value = "/app/dashboard")
public class Dashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        AdminDao adminDao = new AdminDao();
        String email = (String)session.getAttribute("email");
        Admin loggedAdmin = adminDao.readAdminByEmail(email);
        Map<String, List<RecipePlan>> recipePlanForEveryDay = RecipePlanDao.getLastPlanDetails(loggedAdmin.getId());
        PlanDao planDao = new PlanDao();
        Plan lastPlan = planDao.getLastPlanForAdminId(loggedAdmin.getId());
        System.out.println(lastPlan);
        int plansQuantity = QuantityPlan.getPlansQuantityForLoggedUser(session);
        int recipeQuantity = QuantityRecipe.getRecipeQuantityForLoggedUser(session);
        session.setAttribute("plansQuantity", plansQuantity);
        session.setAttribute("recipeQuantity", recipeQuantity);
        session.setAttribute("lastPlan", lastPlan);
        session.setAttribute("recipePlanForEveryDay", recipePlanForEveryDay);
        request.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
