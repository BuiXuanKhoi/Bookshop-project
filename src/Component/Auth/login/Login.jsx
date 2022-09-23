import React, {useEffect, useRef, useState} from "react";
import 'antd/dist/antd.min.css';
import '../Signup/Signup.css';
import {Button, Form, Modal} from "antd";
import Input from "antd/es/input/Input";
import axios from "axios";
import "./Login.css"

import {useForm} from "react-hook-form";
import {getCookie} from 'react-use-cookie';
import {Link} from "react-router-dom";
import {createPath, useNavigate} from "react-router";
import {useCookies} from "react-cookie";
export default function Login(){


    const[loginData, setLoginData] = useState({
        userName: "",
        password: "",
    });

    const {register, handleSubmit} = useForm();
    const[cookies, setCookies] = useCookies(['book-token']);

    const isInit = useRef(false);


    const afterSubmit = (values) =>{

        setLoginData({
            userName: values.username,
            password: values.password,
        })
    }

    const errorPopup = (code, mess) =>{
        Modal.error({
            title: "Error Login",
            content: mess
        })
    }

    const successPopup = (mes) => {
        console.log(JSON.parse(getCookie('book-token')))
        Modal.success({
            title: "Login Success",
            content: mes
        })
    }




    const login = () =>{
        axios.post('https://ecommerce-web0903.herokuapp.com/api/auth/login',loginData)
            .then((res) =>{
                setCookies('book-token',{
                    userName : res.data.userName,
                    role : res.data.role,
                    token : res.data.token,
                    type : res.data.tokenType
                });
                successPopup("You can access shopping page now !!!");
            }).catch((err) => {
                errorPopup(err.response.status, err.response.data.message);
        })
    }

    useEffect(() =>{
        if(isInit.current) {
            login();
        }
        else
        {
            isInit.current = true;
        }
    },[loginData])


    return(
        <body className="align">

            <div className="grid">
                <h2>Sign In</h2>
                <form className="form login" onSubmit={handleSubmit(afterSubmit)}>

                    <div className="formfield">
                        <label htmlFor="login--username">
                            <svg className="icon">
                                <use href="#icon-user">

                                </use>
                            </svg>
                            <span className="hidden">Username</span></label>
                        <input {...register("username")} autoComplete="username" id="login--username" type="text"
                               className="forminput" placeholder="Username" required/>
                    </div>

                    <div className="formfield">
                        <label htmlFor="login password">
                            <svg className="icon">
                                <use href="#icon-lock">

                                </use>
                            </svg>
                            <span className="hidden">Password</span></label>
                        <input {...register("password")} id="login password" type="password"  className="form--input"
                               placeholder="Password" required/>
                    </div>

                    <div className="formfield">
                        <input type="submit" value="Sign In"/>
                    </div>

            </form>

            <p className="text--center">Not a member? <a href="/signup">Sign up now</a>
                <svg className="icon">
                    <use href="#icon-arrow-right">

                    </use>
                </svg>
            </p>

        </div>

        <svg xmlns="http://www.w3.org/2000/svg" className="icons">
            <symbol id="icon-arrow-right" viewBox="0 0 1792 1792">
                <path
                    d="M1600 960q0 54-37 91l-651 651q-39 37-91 37-51 0-90-37l-75-75q-38-38-38-91t38-91l293-293H245q-52 0-84.5-37.5T128 1024V896q0-53 32.5-90.5T245 768h704L656 474q-38-36-38-90t38-90l75-75q38-38 90-38 53 0 91 38l651 651q37 35 37 90z"/>
            </symbol>
            <symbol id="icon-lock" viewBox="0 0 1792 1792">
                <path
                    d="M640 768h512V576q0-106-75-181t-181-75-181 75-75 181v192zm832 96v576q0 40-28 68t-68 28H416q-40 0-68-28t-28-68V864q0-40 28-68t68-28h32V576q0-184 132-316t316-132 316 132 132 316v192h32q40 0 68 28t28 68z"/>
            </symbol>
            <symbol id="icon-user" viewBox="0 0 1792 1792">
                <path
                    d="M1600 1405q0 120-73 189.5t-194 69.5H459q-121 0-194-69.5T192 1405q0-53 3.5-103.5t14-109T236 1084t43-97.5 62-81 85.5-53.5T538 832q9 0 42 21.5t74.5 48 108 48T896 971t133.5-21.5 108-48 74.5-48 42-21.5q61 0 111.5 20t85.5 53.5 62 81 43 97.5 26.5 108.5 14 109 3.5 103.5zm-320-893q0 159-112.5 271.5T896 896 624.5 783.5 512 512t112.5-271.5T896 128t271.5 112.5T1280 512z"/>
            </symbol>
        </svg>

        </body>

    );
}