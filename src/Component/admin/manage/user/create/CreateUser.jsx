import React, {useState} from "react";
import {Button, DatePicker, Drawer, Form, Select, Space} from "antd";
import Input from "antd/lib/input/Input";
import {Option} from "antd/es/mentions";
import moment from "moment";
import axios from "axios";

export default function ({isOpen, close}){

    const [form] = Form.useForm();


    const handleFinish = (values) => {
        console.log(values);
    }

    const createUser = () => {
        axios.post()
    }

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
            title="Create new account"
            width={700}
            open={isOpen}
            onClose={close}
            extra={
                <Space>
                    <Button onClick={close}>Cancel</Button>
                    <Button onClick={form.submit} type="primary">Submit</Button>
                </Space>
            }
        >
            <Form
                {...formItemLayout}
                form={form}
                name="register"
                onFinish={handleFinish}
                scrollToFirstError
                className="create-user-container"
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
                    label="Phone Number"
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