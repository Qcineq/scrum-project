package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;

import javax.servlet.http.HttpSession;
import java.sql.*;

public class QuantityRecipe {

    private static String SQL = "select count(id) from recipe where admin_id = ?;";
    public static int getPlansQuantityForLoggedUser(HttpSession session) {
        Integer recipeQuantity = null;
        String email = (String) session.getAttribute("email");
        AdminDao adminDao = new AdminDao();
        Admin loggedAdmin = adminDao.readAdminByEmail(email);
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1,loggedAdmin.getId());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                recipeQuantity = rs.getInt("count(id)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (recipeQuantity == null){
                return 0;
            }
        }
        return recipeQuantity;
    }
}