import './App.css';
import './Component/Auth/Signup/SignUp'
import SignUp from "./Component/Auth/Signup/SignUp";
import Login from "./Component/Auth/login/Login";
import Home from "./Component/home/Home";
import Shop from "./Component/shop/Shop";
import BookDetail from "./Component/books/BookDetail";
import {BrowserRouter, Switch ,Route, useParams} from 'react-router-dom';
import Cart from "./Component/users/cart/Cart";
import Order from "./Component/users/order/Order";
import Header from "./Component/Header";
import Footer from './Component/Footer';
import NavigateBar from "./Component/general/NavigateBar/NavigateBar";
import React, {createContext, useEffect, useState} from "react";
import {getCookie} from "react-use-cookie";
import {AdminRoute} from "./routes/AdminRoute";
import {CustomerRoute} from "./routes/CustomerRoute";
import RoutesDefine from "./routes/RoutesDefine";
import {BasicRoutes} from "./routes/BasicRoutes";
import {UserRoute} from "./routes/UserRoute";
import {Routes} from "react-router";
import ManageOrder from "./Component/admin/manage/order/ManageOrder";

export const SecurityContext = createContext();

function App()  {

    const [loginData, setLoginData] = useState({
        role : '',
        userName: '',
        token : 'dggdgdf',
        tokenType: 'Bearer',
        refreshToken : ''
    });


    useEffect(() =>{
        if(getCookie('book-token') !== ''){
            setLoginData(JSON.parse(getCookie('book-token')))
        }else{
            setLoginData({
                role : '',
                userName: '',
                token : 'dggdgdf',
                tokenType: 'Bearer',
                refreshToken : ''
            });
        }
    },[loginData.token])



  return (
    <BrowserRouter>
        <SecurityContext.Provider value={[loginData, setLoginData]}>
            {loginData.role === 'CUSTOMER' ? (
                <div>
                    <NavigateBar menu={CustomerRoute}/>
                    <RoutesDefine routes={BasicRoutes(CustomerRoute)}/>
                </div>
            ) : loginData.role === 'ADMIN' ? (
                <div>
                    <NavigateBar menu={AdminRoute}/>
                    <RoutesDefine routes={BasicRoutes(AdminRoute)}/>
                </div>
            ) : (
                <div>
                    <NavigateBar menu={null}/>
                    <RoutesDefine routes={UserRoute}/>
                </div>
            )}
        </SecurityContext.Provider>
    </BrowserRouter>

  );
}

export default App;
