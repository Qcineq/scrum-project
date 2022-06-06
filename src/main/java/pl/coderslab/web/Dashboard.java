package pl.coderslab.web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Dashboard", value = "/app/dashboard")
public class Dashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int plansQuantity = QuantityPlan.getPlansQuantityForLoggedUser(session);
        int recipeQuantity = QuantityRecipe.getRecipeQuantityForLoggedUser(session);
        session.setAttribute("plansQuantity", plansQuantity);
        session.setAttribute("recipeQuantity", recipeQuantity);
        request.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
