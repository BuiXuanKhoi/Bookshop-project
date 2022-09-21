import './Signup.css'
import React, {useEffect, useState,useRef} from "react";
import 'antd/dist/antd.css';
import {AutoComplete, Button, Input, InputNumber, Select, Checkbox, Form, DatePicker,} from "antd";
import axios from "axios";
import userEvent from "@testing-library/user-event";
import {TwitterOutlined} from "@ant-design/icons";
import {FacebookFilled} from "@ant-design/icons"
import {LinkedinFilled} from "@ant-design/icons";
import Modal from "antd/es/modal/Modal";


const {Option} = Select;
const dateFormatList = 'DD/MM/YYYY';

const SignUp = () =>{
    const [form] = Form.useForm();
    const isInit = useRef(false);
    const onFinish = (values) => {
        console.log('Received values of form:',values)
    };
    let role = 'CUSTOMER';



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

    const errorPopUp = (mes,code) =>{
        Modal.error({
            title:"Signup Error: " + code,
            content: mes,
        })
    }

    const successPopUp = (mes) =>{
        Modal.success({
            title: " Signup Success: ",
            content: mes
        })
    }

    useEffect(()=>{
        if (isInit.current){
            PostAPI();
            console.log("yes");

        }
        else {
            console.log("No");
            isInit.current = true;}
    },[user])



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
        console.log(user);

    }

    const PostAPI = () =>{
        console.log("Second")
        console.log(user)
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
                successPopUp(response.data.message))
            .catch((error)=> {
                if (error.response.status === 400){
                    errorPopUp(error.response.message, error.response.data.message);
                }
            });
    }




    const formItemLayout = {
        labelCol:{
            xs : {
                span: 20,
            },
            sm: {
                span: 5,
            },
        },
        wrapperCol:{
            xs: {
                span: 20,
            },
            sm: {
                span: 15,
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
                span: 10,
                offset: 5,
            },
        },
    };


    return (
        <>
            <div className={"first-box"}>

                <div className="hex">
                    <div className="hex-background">
                        <img src={"https://images.unsplash.com/photo-1553729784-e91953dec042?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80\""}/>
                    </div>
                </div>

                <p>Sign in with another accounts</p>
                <Button type="primary" shape="circle" style={{marginLeft:"20%",marginTop:"80%",position:"relative"}} icon={<TwitterOutlined style={{fontSize:"25px"}}/>} size={"large"}/>
                <Button type="primary" shape="circle" style={{background:"#4267B2",marginLeft:"20%",marginTop:"80%",position:"relative"}} icon={<FacebookFilled style={{fontSize:"25px"}}/>} size={"large"}/>
                <Button type="primary" shape="circle" style={{background:"#0077B5",marginLeft:"20%",marginTop:"80%",position:"relative"}} icon={<LinkedinFilled style={{fontSize:"25px"}}/>} size={"large"}/>
            </div>


            <div className={"second-box"}>
                <header-position> Create your Book store Account</header-position>
                <position>
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
                                       pattern: new RegExp("^[a-zA-Z]+( [a-zA-Z]+)+$"),
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
                               label= {"First Name"}
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
                        <Button  type={"primary"} htmlType={"Submit"}>Signup</Button>
                    </Form.Item>
                </Form>
                </position>
            </div>
        </>

    );
}
export default SignUp;