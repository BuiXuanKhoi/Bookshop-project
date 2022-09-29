import React from "react";
import Home from "../Component/home/Home";
import UserTable from "../Component/admin/manage/view/UserTable";
import Shop from "../Component/shop/Shop";
import InformationPage from "../Component/information/InformationPage";
import Login from "../Component/Auth/login/Login";
import SignUp from "../Component/Auth/Signup/SignUp";
import BookDetail from "../Component/books/BookDetail";
import Report from "../Component/admin/manage/report/Report";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFileCircleCheck, faUser, faUserCheck} from "@fortawesome/free-solid-svg-icons";


export const AdminRoute = [

    {
        path: '/report',
        component: <Report/>,
        title: 'Report',
        icon : <FontAwesomeIcon icon={faFileCircleCheck}/>
    },
    {
        path: '/users',
        component: <UserTable/>,
        title: 'Manage User',
        icon: <FontAwesomeIcon icon={faUserCheck}/>
    },

    {
        path: '/information',
        component: <InformationPage/>,
        title: 'Information',
        icon: <FontAwesomeIcon icon={faUser}/>
    }
]