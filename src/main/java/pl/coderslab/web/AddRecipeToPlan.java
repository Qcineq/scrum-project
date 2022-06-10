package pl.coderslab.web;

import pl.coderslab.dao.*;
import pl.coderslab.model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddPlanToSchedule", value = "/app/recipe/plan/add")
public class AddRecipeToPlan extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");
        HttpSession session = request.getSession();
        AdminDao adminDao = new AdminDao();
        Admin loggedAdmin = adminDao.readAdminByEmail((String) session.getAttribute("email"));
        PlanDao planDao = new PlanDao();
        List<Plan> planList = planDao.readAllPlansByAdminId(loggedAdmin.getId());
        request.setAttribute("planList", planList);
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipeList = recipeDao.readAllRecipesByAdminId(loggedAdmin.getId());
        request.setAttribute("recipeList", recipeList);
        DayNameDao dayNameDao = new DayNameDao();
        List<DayName> days = dayNameDao.findAll();
        request.setAttribute("days", days);
        request.getServletContext().getRequestDispatcher("/app-schedules-meal-recipe.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");
        Integer plan_id = null;
        try {
            plan_id = Integer.parseInt(request.getParameter("planName"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String mealName = request.getParameter("mealName");
        Integer displayOrder = null;
        try {
            displayOrder = Integer.parseInt(request.getParameter("mealNumber"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Integer recipe_id = null;
        try {
            recipe_id = Integer.parseInt(request.getParameter("recipeName"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Integer day_name_id = null;
        try {
            day_name_id = Integer.parseInt(request.getParameter("dayName"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if(plan_id!=null && mealName!=null && !mealName.equals("") && displayOrder!=null && recipe_id !=null && day_name_id!=null){
            RecipePlanDao.putRecipeIntoPlan(recipe_id, mealName, displayOrder, day_name_id, plan_id);
            response.sendRedirect("/app/recipe/plan/add");
        } else {
            String wrongData = "Podano nieprawidłowe dane. Spróbuj ponownie";
            response.setContentType("text/html");
            response.setCharacterEncoding("utf8");
            response.getWriter()
                    .append(wrongData)
                    .append("<a href='http://localhost:8080/app/recipe/plan/add'> wróć </a>");
        }
    }
}
