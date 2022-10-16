import React, {useEffect, useRef, useState} from "react";
import {Button, DatePicker, Drawer, Form, Select, Space} from "antd";
import Input from "antd/lib/input/Input";
import {Option} from "antd/es/mentions";
import moment from "moment";
import axios from "axios";

export default function ({isOpen, close}){

    const [form] = Form.useForm();
    const state = useRef(false);

    const [userDetail, setUserDetail] = useState({
        userName : '',
        phoneNumber : '',
        address : '',
        dateOfBirth: '',
        firstName : '',
        lastName: '',
        email : '',
        role : 'ADMIN'
    })


    const handleFinish = (values) => {
        setUserDetail({
            userName: values.userName,
            phoneNumber: values.phoneNumber,
            address: values.location,
            dateOfBirth: values.dateOfBirth.format('DD/MM/YYYY'),
            firstName: values.firstName,
            lastName: values.lastName,
            email: values.email,
            role: 'ADMIN'
        })
    }



    const createUser = () => {
        axios.post('https://ecommerce-web0903.herokuapp.com/api/auth/signup', userDetail)
            .then((res) => console.log(res))
            .catch((err) => console.log(err))
    }

    useEffect(() => {
        if(state.current){
            createUser();
        }else{
            state.current = true;
        }
    },[userDetail])


    const formItemLayout = {
        labelCol: {
            xs: {
                span: 24,
            },
            sm: {
                span: 8,
            },
        },
        wrapperCol: {
            xs: {
                span: 24,
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
                offset: 0
            },
            sm: {
                span: 16,
                offset: 8
            }
        }
    };




    return(
        <Drawer
            title="Create new admin account"
            width={700}
            open={isOpen}
            onClose={close}
            extra={
                <Space>
                    <Button onClick={close}>Cancel</Button>
                    <Button onClick={form.submit} type="primary">Submit</Button>
                </Space>
            }
            bodyStyle={{ justifyContent:"center", display: "flex", marginTop: "30px"}}
        >
            <Form
                {...formItemLayout}
                form={form}
                name="register"
                onFinish={handleFinish}
                scrollToFirstError

            >
                <Form.Item
                    name="userName"
                    label="User name"
                    rules={[
                        {
                            required: true,
                            message: "Username is required"
                        }
                    ]}
                >
                    <Input placeholder="Please input username"/>
                </Form.Item>
                <Form.Item name="firstName" label="First Name"
                           rules={[
                               {
                                   required: true,
                                   message: "First name is required"
                               }
                           ]}
                >
                    <Input placeholder="Please input your first name"/>
                </Form.Item>
                <Form.Item name="lastName" label="Last Name"
                           rules={[
                               {
                                   required: true,
                                   message: "Last name is required"
                               }
                           ]}
                >
                    <Input placeholder="Please input your last name"/>
                </Form.Item>
                <Form.Item
                    name="email"
                    label="E-mail"
                    rules={[
                        {
                            required: true,
                            message: "E-mail is required"
                        }
                    ]}
                >
                    <Input placeholder="Please input your E-mail"/>
                </Form.Item>
                <Form.Item
                    name="phoneNumber"
                    label="Phone Number:"
                    rules={[
                        {
                            required: true,
                            message: "Phone number is required"
                        }
                    ]}
                >
                    <Input placeholder="Please input your phone number"/>
                </Form.Item>
                <Form.Item
                    name="dateOfBirth"
                    label="Date Of Birth"
                    style={{
                        width: '100%'
                    }}
                    rules={[
                        {
                            required: true,
                            message: "Date Of Birth is required"
                        }
                    ]}
                >
                    <DatePicker/>
                </Form.Item>
                <Form.Item
                    name="location"
                    label="Location"
                    style={{
                        width: '100%'
                    }}
                    rules={[
                        {
                            required: true,
                            message: "Location is required"
                        }
                    ]}
                >
                    <Select>
                        <Option value="HN">HN</Option>
                        <Option value="SG">SG</Option>
                        <Option value="DN">DN</Option>
                    </Select>
                </Form.Item>
            </Form>
        </Drawer>
    );
}