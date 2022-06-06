package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;


import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

   public class QuantityPlan {

       private static String SQL = "select count(id) from plan where admin_id = ?;";
       public static int getPlansQuantityForLoggedUser(HttpSession session) {
           Integer plansQuantity = null;
           String email = (String) session.getAttribute("email");
           AdminDao adminDao = new AdminDao();
           Admin loggedAdmin = adminDao.readAdminByEmail(email);
           try (Connection connection = DbUtil.getConnection()) {
               PreparedStatement statement = connection.prepareStatement(SQL);
               statement.setInt(1,loggedAdmin.getId());
               ResultSet rs = statement.executeQuery();
               if(rs.next()){
                   plansQuantity = rs.getInt("count(id)");
               }
           } catch (SQLException e) {
               e.printStackTrace();
           } finally {
               if (plansQuantity == null){
                   return 0;

               }
           }
           return plansQuantity;
       }
   }