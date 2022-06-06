package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Recipes", value = "/app/recipe/list")
public class Recipes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.readAdminByEmail((String)session.getAttribute("email"));
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipeList = recipeDao.readAllRecipesByAdminId(admin.getId());
        session.setAttribute("recipeList", recipeList);
    request.getServletContext().getRequestDispatcher("/app-recipes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
