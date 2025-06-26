package org.yearup.data.mysql;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {
    // Constructor that receives a DataSource and passes it to the base DAO class.
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }


    //Retrieves all categories from the 'categories' table.
    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        // SQL query to fetch all rows from the categories table.
        String sql = "SELECT * FROM categories";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loop through each row in the ResultSet.
            while (rs.next()) {
                // Create a new Category object.
                Category category = new Category();

                // Set the categories ID from the "CategoryID" column.
                category.setCategory_id(rs.getInt("Category_id"));

                // Set the categories name from the "CategoryName" column.
                category.setName(rs.getString("name"));

                // Add the category object to our list.\
                categories.add(category);
            }
        } catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

        // Return the list categories
        return categories;
    }

    @Override
    public Category getById(int categoryId) {
        Category category = new Category();

        // SQL query to fetch a category by its ID using a parameterized query.
        String sql = "SELECT * FROM categories WHERE category_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, categoryId);

            ResultSet rs = stmt.executeQuery();

            // Loop through each row in the ResultSet.
            if (rs.next()) {
                // Set the categories ID from the "CategoryID" column.
                category.setCategory_id(rs.getInt("category_id"));
                // Set the categories name from the "CategoryName" column.
                category.setName(rs.getString("name"));


            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        } catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

        // Return the list of category objects.
        return category;
    }


    @Override
    public Category create(Category category) {
        // SQL query to insert a new category into the database.
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        // This is a "try-with-resources" block.
        // It ensures that the Connection and PreparedStatement are closed automatically after we are done.
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the first parameter (?) to the products name.
            stmt.setString(1, category.getName());
            // Set the second parameter (?) to the products name.
            stmt.setString(2, category.getDescription());
            // Execute the INSERT statement — this will add the row to the database.
            stmt.executeUpdate();

            // Retrieve the generated category_id
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    int newId = keys.getInt(1);
                    category.setCategory_id(newId);
                }
            }
        } catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public void update(int category_id, Category category) {
        String sql = """
                UPDATE
                    Categories 
                SET 
                    name = COALESCE(?, name) 
                WHERE 
                    category_id = ?
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setInt(2, category_id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void delete(int category_id) {
        String sql = "DELETE FROM Categories WHERE category_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, category_id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   //Helper method to convert a ResultSet row into a Category object.
    private Category mapRow(ResultSet row) throws SQLException {
        int category_id = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category= new Category() {
            {
                setCategory_id(category_id);
                setName(name);
                setDescription(description);
            }
        };

        return category;
    }
}
