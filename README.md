# 🛍️ EasyShop E-Commerce API

This is the backend RESTful API for EasyShop, an e-commerce website built using Spring Boot, Java, and MySQL.
It supports user registration, login, browsing products by category, adding items to a shopping cart, 
and checking out to place orders.

---

## 🚀 Features

- 🔐 User Registration & Login (JWT)
- 📦 View Products by Category
- 🔍 Product Search and Filtering (by category, price, color)
- 📁 Category Management (Admin Only)
- 🛠️ Product Management (Admin Only)
- 🌐 Fully testable with Postman

---

## 🖼️ Screenshots

### Screenshots for  Categories of the API tested in Postman.


### Registre
![image](https://github.com/user-attachments/assets/ca3eade8-e1f0-4faf-a700-4cc28bcaa4e4)

### Login
![image](https://github.com/user-attachments/assets/1d18365d-7d67-4231-8383-989e679a695d)

### Get http://localhost:8080/categories
![image](https://github.com/user-attachments/assets/66cf68d2-c962-4719-8d36-02ce379d1450)

### Get http://localhost:8080/categories/1
![image](https://github.com/user-attachments/assets/d30d0c32-547e-4992-8b59-c5dbeb9114e8)

### POST http://localhost:8080/categories
![image](https://github.com/user-attachments/assets/fb588d8a-dbfd-40b4-8b58-34b9063701c0)

![image](https://github.com/user-attachments/assets/a0fdec9c-4cff-416f-aa8a-051c947d1964)

### PUT http://localhost:8080/categorids/1
![image](https://github.com/user-attachments/assets/5db77876-969b-4e7a-a90b-b56c13c27905)

![image](https://github.com/user-attachments/assets/0f3e8618-6988-4eea-8ef9-cdbafc93d862)

### DELETE http://localhost:8080/categorids/1
![image](https://github.com/user-attachments/assets/a2553dea-6dd4-4a6f-8ec1-8d5a91150338)

![image](https://github.com/user-attachments/assets/0f9563c2-eeb3-4c27-836f-d0c5befa9062)

### Screenshots for Products of the API tested in Postman.



### Get http://localhost:8080/products 

![image](https://github.com/user-attachments/assets/67b90b39-74f7-45cb-b620-cd91b3bc1fa8)

### Get http://localhost:8080/products/1

![image](https://github.com/user-attachments/assets/dc816693-d430-481e-866f-a32ecbbcd448)

### Post http://localhost:8080/products
![image](https://github.com/user-attachments/assets/c2ad1a1f-4969-46c2-bb18-004cebac58c6)


### Put http://localhost:8080/products/1
![image](https://github.com/user-attachments/assets/bbccac8b-3ed3-4357-9591-38a580b9aea2)


### DELETE http://localhost:8080/products/1

![image](https://github.com/user-attachments/assets/c94b2d67-1902-4562-a0e0-cb0adb4172dc)

### Screenshot for Capstone-client-web-application 
![image](https://github.com/user-attachments/assets/4a3cb5ae-1e69-4680-8bfd-4d9ccf68d291)

![image](https://github.com/user-attachments/assets/d6e68f25-c4c6-4616-b07d-84bd3ce9e462)

![image](https://github.com/user-attachments/assets/290230c9-a7e4-4eb6-b6e1-998f6af8d7b3)

![image](https://github.com/user-attachments/assets/878312ce-5ff4-4a39-acd3-f750d182c7fa)




---

## 📦 Interesting Code Sample

One interesting part of this project is how I implemented the feature to get all products within a specific category.
This is important because it allows users to easily browse items based on their interests, like electronics or clothing.

```
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
 
```
