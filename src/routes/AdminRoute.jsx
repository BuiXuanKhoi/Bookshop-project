import React from "react";
import Home from "../Component/home/Home";
import UserTable from "../Component/admin/manage/view/UserTable";
import Shop from "../Component/shop/Shop";
import InformationPage from "../Component/information/InformationPage";
import Login from "../Component/Auth/login/Login";
import SignUp from "../Component/Auth/Signup/SignUp";
import BookDetail from "../Component/books/BookDetail";

export const AdminRoute = [
    {
        path: '/',
        component : <Home/>,
        title : 'Home Page'
    },
    {
        path: '/users',
        component: <UserTable/>,
        title: 'Manage User'
    },
    {
        path: '/shop',
        component: <Shop/>,
        title: 'Shop Page'
    },
    {
        path: '/information',
        component: <InformationPage/>,
        title: 'Information Pae'
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
    },
    {
        path: '/books/:id',
        component: <BookDetail/>,
        title: 'Book Detail'
    }
]