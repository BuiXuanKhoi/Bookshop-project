### The BE code place in the master branch and the FE code place in the Ecommerce-book-frontend branch

# Technologies used in project:


## Back-End : 
- Java +8.
- PostgreSQL.
- Spring Boot.
- Spring JPA/Hibernate.
- Spring Security with JWT.
- Lombok.
- Cloudinary for upload Image.
- Swagger for API Documents
## Front-End:
- ReactJS.
- React Hooks.
- Antd Design.
## Test : 
- Postman.
- JUnit for Unit Test.

# Web Routes Diagram : 

![alt text for screen readers](https://github.com/BuiXuanKhoi/Bookshop-project/blob/master/image/Bookshop%20Routes.jpg "Bookshop Web Routes Diagram")

# Feature
- Almost the data has been pagination to make optimize APIs.
- There are specific routes for each role beside basic routes. There is one general route and  2 routes for 3 different roles : User not signed, Admin, User signed.
User cannot access the route of other roles. If they try, it will lead to the Page Not Found page.
- In Security, we use Spring Security with JWT for authentication and authorize. 
## Basic Routes :
- Login.
- Signup (Only allow to create customer account. Only admin can create admin account. 
- The password will be auto generated follow the form : username@dateOfBirth. For Example : khoi@10112000 with khoi is username and 10/11/2000 is the date of birth) .
- View Home Page (include top cheap book, top popular and top recommend).
- View Shop Page (User can view shop page with pagination and user can filter by category and authors. User can also sort by some conditions and search for book name).
- View Book Detail (User can view specific book detail including book name, description, price, author, quantity, image and feedbacks of this book
. The book detail page also has add to cart table and submit review table but only available for user who signed).
- User who signed can view their information detail and edit the information. Besides, the menu also has an option for users to change their password if they don't want to use the present password.

## Admin Routes

- Manage User (Block/Unblock/Create/View List).
- Manage Books (Delete/Edit/Create/View List).
- Manage Orders(View List/Change State).

## Customer (Signed) Routes

- View Cart Page (User can view all books that they have added to their cart, they can also update the quantity of each cart item. 
There is an item to display the total price of the whole cart. Beside, there is an button to change the whole cart into new order).
- View Order (User can view their own order history. Each order has an progress bar that show their status. 

