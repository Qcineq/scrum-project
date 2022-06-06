package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "AddRecipe", value = "/app/recipe/add")
public class AddRecipe extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");
        response.setContentType("text/html");
        request.getServletContext().getRequestDispatcher("/app-add-recipe.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf8");
        HttpSession session = request.getSession();

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
        String preparation = request.getParameter("preparation");
        String ingredients = request.getParameter("ingredients");

        Recipe newRecipe = new Recipe();
        newRecipe.setName(name);
        newRecipe.setDescription(description);
        newRecipe.setPreparationTime(preparationTime);
        newRecipe.setPreparation(preparation);
        newRecipe.setIngredients(ingredients);
        newRecipe.setCreated(String.valueOf(LocalDateTime.now()));

        AdminDao adminDao = new AdminDao();
        String email = (String) session.getAttribute("email");
        Admin admin = adminDao.readAdminByEmail(email);
        newRecipe.setAdmin_id(admin.getId());
        RecipeDao recipeDao = new RecipeDao();
        newRecipe = recipeDao.create(newRecipe);
        System.out.println(newRecipe);
        response.sendRedirect("/app/recipe/list");
    }
}
