import './Signup.css'
import React, {useEffect, useRef, useState} from "react";
import 'antd/dist/antd.min.css';
import {AutoComplete, Button, Input, InputNumber, Select, Checkbox, Form, DatePicker, message,Modal} from "antd";
import axios from "axios";
import userEvent from "@testing-library/user-event";

const {Option} = Select;
const dateFormatList = 'DD/MM/YYYY';

const SignUp = () =>{
    const [form] = Form.useForm();
    let role = 'CUSTOMER';
    const isInit = useRef(false);
    const [user, setUser] = useState({
        userName:'',
        dateOfBirth: '',
        firstName: '',
        lastName: '',
        address: '',
        phoneNumber: '',
        email: '',
        role: '',
    });

    const errorPopup = (mes, code) =>{
        Modal.error({
            title: "Signup Error: " + code,
            content : mes
        });
    };

    const successPopup = (mes) => {
        Modal.success({
            title: "Signup Success: ",
            content: mes
        })
    }

    const Send = (values) => {
        setUser(
            {
                userName: values.userName,
                dateOfBirth: values.dateOfBirth.format("DD/MM/YYYY"),
                firstName: values.firstName,
                lastName: values.lastName,
                address: values.location,
                phoneNumber: values.phoneNumber,
                email: values.email,
                role: role,
            }
        )
    }

    useEffect(() =>{
        if(isInit.current)
        {
            signup();
        }else
        {
            isInit.current = true;
        }
    },[user])

    const signup = () =>{
        axios
            .post("http://localhost:8080/api/auth/signup",{
                userName: user.userName,
                dateOfBirth: user.dateOfBirth,
                firstName: user.firstName.trim(),
                lastName: user.lastName,
                address: user.address,
                phoneNumber: user.phoneNumber,
                email: user.email,
                role: user.role,
            })
            .then((response)=>
                successPopup(response.data.message))
            .catch((error)=>{
                if(error.response.status === 400){
                    errorPopup(error.response.message, error.response.data.message);
                }
            });
    }





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


    return (
        <>
            <div className={"first-box"}>

            </div>
            <div className={"second-box"}>
                <Form {...formItemLayout}
                      form={form}
                      name="register"
                      onFinish={(values)=>Send(values)}

                      >
                    <Form.Item name={"userName"}
                               label={"Username"}
                               rules={[
                                   {
                                       max: 128, message: "Username must be less than 128 characters",
                                   },
                                   ()=>({
                                        validator(_,values){
                                            if((values).trim()===''){
                                                return Promise.reject("Please insert your username!");
                                            }
                                            return Promise.resolve();
                                        }
                                   })
                               ]}>
                        <Input />
                    </Form.Item>

                    <Form.Item name = {"dateOfBirth"}
                               label = {"Date of Birth"}
                               rules = {[
                                   {
                                       required: true, message: " Please insert your birthday !"
                                   },
                                   ()=>({
                                       validator(_,values){
                                           if((values)==='' || (values) === null){
                                               return Promise.reject("Please insert your birthday");
                                           }
                                           return Promise.resolve();
                                       }
                                   })
                               ]}>
                        <DatePicker format={dateFormatList}/>
                    </Form.Item>

                    <Form.Item name={"lastName"}
                               label={"Last Name"}
                               rules = {[
                                   {max:128, message: "Your last name must be less than 128 characters",},
                                   {
                                       // pattern: new RegExp("^[a-zA-Z]+( [a-zA-Z]+)+$"),
                                       pattern: new RegExp("^[A-Z]([a-z])+"),
                                       message: "Your last name cannot contain the number or special characters",
                                   },
                                   () =>({
                                       validator(_,values){
                                           if((values).trim()===null || (values).trim() === ''){
                                               return Promise.reject("Please input your last name !")
                                           }
                                           else{return Promise.resolve();}
                                       }
                                   })
                               ]}>
                        <Input/>
                    </Form.Item>

                    <Form.Item name = {"firstName"}
                               label = {"First Name"}
                               rules={[
                                   {max: 128, message: "Your first name must be less than 128 character",},
                                   {
                                       pattern: new RegExp("^[a-zA-Z'.]+$"),
                                       message: "Your first name cannot contain the number or special characters"
                                   },
                                   () =>({
                                       validator(_,values){
                                            if((values).trim()==='' || (values).trim()===null){
                                                return Promise.reject("Please insert your first name !");
                                            }
                                            else {return Promise.resolve();}
                                       }
                                   })
                               ]}>
                        <Input/>
                    </Form.Item>



                    <Form.Item name={"location"}
                               label={"Location"}
                               initialValue={"HCM"}
                                rules={[

                                ]}>
                        <Select >
                            <Option value={"HCM"}>HCM</Option>
                            <Option value={"HN"}>HN</Option>
                        </Select>
                    </Form.Item>

                    <Form.Item name={"phoneNumber"}
                               label={"Phone Number"}
                               rules={[
                                   {
                                       pattern: new RegExp("^0\\d{9,10}$"),
                                       message:"Your phone number cannot contain special characters or characters and have at least 10-11 numbers",
                                   },
                               ]}>
                        <Input />
                    </Form.Item>

                    <Form.Item name={"email"}
                               label={"Email"}
                               rules={[
                                   {
                                       type:"email",
                                       message:"Your email not existed !"
                                   },

                               ]}>
                        <Input />
                    </Form.Item>

                    <Form.Item {...tailFormItemLayout}>
                        <Button   type={"primary"} htmlType={"Submit"}>Register</Button>
                    </Form.Item>
                </Form>
            </div>
        </>

    );
}
export default SignUp;