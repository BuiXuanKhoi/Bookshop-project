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
        path: 'information',
        component: <InformationPage/>,
        title: 'Information',
        className: 'information',
    },
    {
        path: '/cart',
        component: <Cart/>,
        title: 'Cart',
        className:'cart',
    },
    {
        path: '/order',
        component: <Order/>,
        title: 'Order'
    }
]
