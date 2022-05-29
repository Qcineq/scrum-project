package pl.coderslab.dao;

import pl.coderslab.jbCrypt.BCrypt;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins(first_name,last_name,email,password) VALUES (?,?,?,?);";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins where id = ?;";
    private static final String FIND_ALL_ADMIN_QUERY = "SELECT * FROM admins;";
    private static final String READ_ADMIN_QUERY = "SELECT * from admins where id = ?;";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE admins SET first_name = ? , last_name = ?, email = ?, password = ? WHERE id = ?;";

    public Admin readAdmin(int id){
        Admin admin = new Admin();
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(READ_ADMIN_QUERY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                admin.setId(rs.getInt("id"));
                admin.setFirstName(rs.getString("first_name"));
                admin.setLastName(rs.getString("last_name"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return admin;
    }

    public List<Admin> readAllAdmins(){
        List<Admin> adminList = new ArrayList<>();
        try(Connection connection = DbUtil.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(FIND_ALL_ADMIN_QUERY);
            while(rs.next()){
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setFirstName(rs.getString("first_name"));
                admin.setLastName(rs.getString("last_name"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
                adminList.add(admin);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return adminList;
    }

    public Admin createAdmin(Admin newAdmin){
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(CREATE_ADMIN_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, newAdmin.getFirstName());
            statement.setString(2, newAdmin.getLastName());
            statement.setString(3, newAdmin.getEmail());
            statement.setString(4, BCrypt.hashpw(newAdmin.getPassword(), BCrypt.gensalt()));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                newAdmin.setId(rs.getInt(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return newAdmin;
    }

    public void deleteAdmin(int id){
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Admin editAdmin(Admin adminToEdit, Admin newAdmin){
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(UPDATE_ADMIN_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, newAdmin.getFirstName());
            statement.setString(2, newAdmin.getLastName());
            statement.setString(3, newAdmin.getEmail());
            statement.setString(4, BCrypt.hashpw(newAdmin.getPassword(), BCrypt.gensalt()));
            statement.setInt(5, adminToEdit.getId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                newAdmin.setPassword(rs.getString(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return newAdmin;
    }
}
