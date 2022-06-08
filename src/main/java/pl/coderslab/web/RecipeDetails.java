package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RecipeDetails", value = "/app/recipe/details")
public class RecipeDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (id != null) {
            RecipeDao recipeDao = new RecipeDao();
            Recipe recipe = recipeDao.read(id);
            request.setAttribute("recipe", recipe);
            String[] ingredientsArr = recipe.getIngredients().split(",");
            request.setAttribute("ingredientsArr", ingredientsArr);
            request.getServletContext().getRequestDispatcher("/app-recipe-details.jsp").forward(request, response);
        } else {
            response.getWriter().append("nie znaleziono podanego przepisu");
            return;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
