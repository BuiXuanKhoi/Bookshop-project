import React, {useEffect, useRef, useState} from "react";
import 'antd/dist/antd.min.css';
import '../Signup/Signup.css';
import {Button, Form, Modal} from "antd";
import Input from "antd/es/input/Input";
import axios from "axios";
import {useCookies} from 'react-cookie';
export default function Login(){

    const[loginData, setLoginData] = useState({
        userName: '',
        password: ''
    });
    const[cookies, useCookies] = useCookies(['book-token']);


    const isInit = useRef(false);

    const [form] = Form.useForm();

    const errorPopup = (code, mess) =>{
        Modal.error({
            title: "Error Login",
            content: mess
        })
    }

    const successPopup = (mes) => {
        Modal.success({
            title: "Login Success",
            content: mes
        })
    }

    const setData = (values) =>{
        setLoginData({
            userName:values.userName,
            password: values.password
        })
    }

    const login = () =>{
        axios.post('http://localhost:8080/api/auth/login', loginData)
            .then((res) =>{
                successPopup("You can access shopping page now !!!");
            }).catch((err) => {
                errorPopup(err.response.status, err.response.data.message);
        })
    }

    useEffect(() =>{
        if(isInit.current)
        {
            login();
        }
        else
        {
            isInit.current = true;
        }
    },[loginData])

    const formItemLayout = {
        labelCol:{
            xs : {
                span: 20,
            },
            sm: {
                span: 8,
            },
        },
        wrapperCol:{
            xs: {
                span: 20,
            },
            sm: {
                span: 16,
            },
        },
    };

    const tailFormItemLayout = {
        wrapperCol: {
            xs: {
                span: 24,
                offset: 0,
            },
            sm: {
                span: 16,
                offset: 8,
            },
        },
    };


    return(
        <>
            <div className="first-box">

            </div>
            <div className="second-box">
                <Form
                    {...formItemLayout}
                    name="login"
                    form={form}
                    onFinish={setData}
                >
                    <Form.Item
                        name="userName"
                        label="User name"
                        rules={[
                            {
                                max:128,
                                message : 'Username must be less than 128 characters'
                            },
                            {
                                required:true,
                                message: 'Username is required'
                            }
                        ]}
                    >
                        <Input/>

                    </Form.Item>

                    <Form.Item
                        name="password"
                        label="Password"
                        rules={[
                            {
                                max: 128,
                                message: 'Password must be less thn 128 characters'
                            },
                            {
                                required:true,
                                message: 'Password is required'
                            }
                        ]}
                    >
                        <Input/>
                    </Form.Item>

                    <Form.Item {...tailFormItemLayout}>
                        <Button type={"primary"} htmlType={"submit"}>
                            Login
                        </Button>
                    </Form.Item>
                </Form>
            </div>
        </>
    );
}