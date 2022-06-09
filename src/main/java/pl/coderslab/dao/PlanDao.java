package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {
    // ZAPYTANIA SQL
    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name,description,created, admin_id) VALUES (?,?,?,?);";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan where id = ?;";
    private static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan;";
    private static final String READ_PLAN_QUERY = "SELECT * from plan where id = ?;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE	plan SET name = ? , description = ?, created = ?, admin_id = ? WHERE	id = ?;";
    private static final String READ_ALL_PLANS_FOR_ADMIN_ID = "SELECT * FROM plan WHERE admin_id = ?";
    private static final String REAL_LAST_ADDED_PLAN_FOR_ADMIN_ID = "SELECT * FROM plan WHERE admin_id = ? ORDER BY id DESC LIMIT 1;";


    /**
     * Get plan by id
     *
     * @param planId
     * @return
     */
    public Plan read(Integer planId) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getString("created"));
                    plan.setAdmin_id(resultSet.getInt("admin_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;
    }


    public List<Plan> readAllPlansByAdminId(Integer adminId) {
       List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ALL_PLANS_FOR_ADMIN_ID)
        ) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Plan planToAdd = new Plan();
                    planToAdd.setId(resultSet.getInt("id"));
                    planToAdd.setName(resultSet.getString("name"));
                    planToAdd.setDescription(resultSet.getString("description"));
                    planToAdd.setCreated(resultSet.getString("created"));
                    planToAdd.setAdmin_id(resultSet.getInt("admin_id"));
                    planList.add(planToAdd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planList;
    }


    /**
     * Return all plans
     *
     * @return
     */
    public List<Plan> findAll() {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLANS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getString("created"));
                planToAdd.setAdmin_id(resultSet.getInt("admin_id"));
                planList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;

    }

    /**
     * Create plan
     *
     * @param plan
     * @return
     */
    public Plan create(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, plan.getName());
            insertStm.setString(2, plan.getDescription());
            insertStm.setString(3, plan.getCreated());
            insertStm.setInt(4, plan.getAdmin_id());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Remove plan by id
     *
     * @param planId
     */
    public void delete(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Plan not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update plan
     *
     * @param plan
     */
    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setInt(5, plan.getId());
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());
            statement.setString(3, plan.getCreated());
            statement.setInt(4, plan.getAdmin_id());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Plan getLastPlanForAdminId(int admin_id){
        Plan plan = new Plan();
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(REAL_LAST_ADDED_PLAN_FOR_ADMIN_ID);
            statement.setInt(1, admin_id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                plan.setId(rs.getInt("id"));
                plan.setName(rs.getString("name"));
                plan.setDescription(rs.getString("description"));
                plan.setAdmin_id(rs.getInt("admin_id"));
                plan.setCreated(rs.getString("created"));
            }
            } catch (SQLException e ){
            e.printStackTrace();
        }
        return plan;
    }
}
