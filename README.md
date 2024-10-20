# ProdXpert - Online Order Management Application

![prodXpert-10-20-2024_02_24_PM](https://github.com/user-attachments/assets/a19ab163-4765-4466-8f48-3d4fe19e68e3)
![localhost-8080-prodXpert-product-admin-10-20-2024_02_25_PM](https://github.com/user-attachments/assets/886102a4-7978-4a5b-954a-1b8e5fe467cf)
![Product-Details-10-20-2024_02_25_PM](https://github.com/user-attachments/assets/90290c45-0da0-4aa2-9a84-226ba24069f1)


## Overview

ProdXpert is a powerful and secure web application designed to elevate your business's online order management. It provides a seamless experience for both customers and administrators, making the process of managing products, users, and orders more efficient and accessible. With a modern user interface powered by Thymeleaf and robust role-based authentication, ProdXpert ensures that businesses can handle their online operations with ease and confidence.

## Key Features

### 1. Secure User Authentication
ProdXpert guarantees the security of user data through session-based authentication. Users are categorized into two roles:
- **Admins**: Full control over the system's products, users, and order management features.
- **Clients**: Access to browse products, place orders, and manage their personal orders.

### 2. Dynamic and User-Friendly Interface
Built with **Thymeleaf**, ProdXpert offers a dynamic and responsive user interface. The platform replaces traditional technologies like JSP with a more modern approach to view rendering, ensuring a smoother and more intuitive navigation experience across devices.

### 3. Product Management for Admins
Administrators have exclusive access to manage the product catalog:
- Add, update, or remove products from the inventory.
- Search and filter products with pagination to make navigation efficient, even with large inventories.

### 4. Order Management for Admins and Clients
ProdXpert provides an extensive order management system:
- **For Clients**:
    - Place new orders quickly and easily.
    - Modify or cancel orders that are still in "Pending" or "Processing" status.
    - Track the status of orders and view order history.

- **For Admins**:
    - View, search, and update the status of all orders.
    - Oversee order histories and manage order-related tasks efficiently.

### 5. User Management for Admins
Administrators can manage both client and admin accounts:
- View and search all user accounts using pagination.
- Create, update, and delete user accounts as needed, maintaining full control over system access.

### 6. Layered Architecture for Maintainability
ProdXpert is designed with a **Model-View-Controller (MVC)** architecture. This clean separation between the data layer, business logic, and presentation ensures that the application is easy to maintain, scale, and extend as your business grows.

### 7. Comprehensive Testing
The application is thoroughly tested using **JUnit** and **Mockito**, ensuring the reliability of both the business logic and data access components. This focus on testing helps prevent bugs and guarantees a smooth experience for end users.

## Prerequisites

Before setting up OrderMaster, make sure you have the following:

- **Java 8+** installed on your machine.
- **Maven** for managing project dependencies.
- **Apache Tomcat** or any other servlet container for deploying the application.
- **PostgreSQL or MySQL** for the database.
- **JUnit 5** and **Mockito** for testing.
- A **web browser** to access the application interface.

Ensure that all required tools and frameworks are installed and properly configured before proceeding with the deployment of the application.
