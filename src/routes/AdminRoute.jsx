import React from "react";
import Home from "../Component/home/Home";
import Shop from "../Component/shop/Shop";
import InformationPage from "../Component/information/InformationPage";
import Login from "../Component/Auth/login/Login";
import SignUp from "../Component/Auth/Signup/SignUp";
import BookDetail from "../Component/books/BookDetail";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBook, faCartShopping, faFileCircleCheck, faUser, faUserCheck} from "@fortawesome/free-solid-svg-icons";
import ManageBook from "../Component/admin/manage/book/ManageBook";
import ManageCustomer from "../Component/admin/manage/user/ManageCustomer";
import ManageOrder from "../Component/admin/manage/book/table/ManageOrder";


export const AdminRoute = [
    {
        path: '/manage/book',
        component: <ManageBook/>,
        title: 'Manage Books',
        icon : <FontAwesomeIcon icon={faBook}/>
    },
    {
        path: '/manage/user',
        component: <ManageCustomer/>,
        title: 'Manage Users',
        icon: <FontAwesomeIcon icon={faUserCheck}/>
    },

    {
        path: '/information',
        component: <InformationPage/>,
        title: 'Information',
        icon: <FontAwesomeIcon icon={faUser}/>
    },
    {
        path: '/manage/orders',
        component: <ManageOrder/>,
        title: ' Manage Orders',
        icon: <FontAwesomeIcon icon={faCartShopping}/>
    },
]