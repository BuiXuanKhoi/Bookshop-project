import React from "react";
import Home from "../Component/home/Home";
import Shop from "../Component/shop/Shop";
import BookDetail from "../Component/books/BookDetail";
import Cart from "../Component/users/cart/Cart";
import Order from "../Component/users/order/Order";
import InformationPage from "../Component/information/InformationPage";
import Login from "../Component/Auth/login/Login";
import SignUp from "../Component/Auth/Signup/SignUp";

export const CustomerRoute = [
    {
        path: '/',
        component : <Home/>,
        title : 'Home Page'
    },
    {
        path: '/shop',
        component: <Shop/>,
        title: 'Shop Page'
    },
    {
        path: '/books/:id',
        component: <BookDetail/>,
        title: 'Book Detail'
    },
    {
        path: 'information',
        component: <InformationPage/>,
        title: 'Information Page'
    },
    {
        path: '/cart',
        component: <Cart/>,
        title: 'Cart Page'
    },
    {
        path: '/order',
        component: <Order/>,
        title: 'Order Page'
    },
    {
        path: '/login',
        component: <Login/>,
        title: 'Login Page'
    },
    {
        path: '/signup',
        component: <SignUp/>,
        title: 'Signup Page'
    }
]
