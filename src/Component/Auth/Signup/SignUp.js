import './Signup.css'
import React, {useState} from "react";
import 'antd/dist/antd.css';
import {
    AutoComplete,
    Button,
    Input,
    InputNumber,
    Select,
    Checkbox, Form, DatePicker,
} from "antd";

const {Option} = Select;


const SignUp = () =>{
    const [form] = Form.useForm();
    const onFinish = (values) => {
        console.log('Received values of form:',values)
    };

    const formItemLayout = {
        labelCol:{
            xs : {
                span: 22,
            },
            sm: {
                span: 8,
            },
        },
        wrapperCol:{
            xs: {
                span: 22,
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
    const email= <Form.Item name="email"
                             label="E-mail"
                             rules={[{
                                    type: 'email',
                                    message:'The input is not valid E-mail',
                             },
                                 {
                                     required: true,
                                     message: 'Please input your Email!',
                                 },
                             ]}
                            >
                        <Input />

                    </Form.Item>

    const userName = <Form.Item name="userName"
                                label="Username"
                                rules={[
                                {   required: true,
                                    message: 'Please input your username!',
                                },
                                    ]}
                                >
                            <Input />

                    </Form.Item>

    const firstName = <Form.Item  name="firstName"
                                    label="First name"
                                    rules={[
                                        {
                                            required :true,
                                            message: 'Please input your first name!',
                                        },

                                    ]}
                                    >
                            <Input />
                    </Form.Item>

    const lastName = <Form.Item  name="lastName"
                                  label="Last name"
                                  rules={[
                                      {
                                          required :true,
                                          message: 'Please input your last name!',
                                      },

                                  ]}
    >
        <Input />
    </Form.Item>

    const phone = <Form.Item name = "phoneNumber"
                                label ="Phone Number"
                            rules={[
                                {
                                    required: true,
                                    message:'Please insert your phone number'
                                },
                                {
                                    pattern:/^(?:\d*)$/,
                                    message: " Value should contain just number",
                                },
                                {
                                    max: 11,
                                    min: 10,
                                    message: " Value should be between 10 and 11 number ",
                                },
                            ]}
                >
            < Input />
    </Form.Item>

    const dateOfBirth = <Form.Item name = "dateOfBirth"
                                   label = "Date of Birth"
                                    rules = {[
                                        {
                                            required: true,
                                            message: ' DD/MM/YYYY',
                                        },
                                    ]}>
        <DatePicker format="DD/MM/YYYY"/>
    </Form.Item>

    const location = <Form.Item name="address"
                                label="Location"
                                rules={[
                                    {
                                        required:true,
                                        message:'Chose location!',
                                    },
                                ]}>
        <Select placeholder={"Select your Location"}>
            <Option value={"TP.HCM"}>TPHCM</Option>
            <Option value={"HN"}>HN</Option>
        </Select>
    </Form.Item>

    const checkAgreementBox = <Form.Item {...tailFormItemLayout} name="agreement"
                                            lable="checked"
                                        rules={[
                                            {
                                                validator:(_,value)=>
                                                    value?Promise.resolve():Promise.reject(new Error('Should agree first!'))
                                            },
                                        ]}

                                        >
        <Checkbox>
            I have read and agree with the < a href={""}> agreement</a>
        </Checkbox>
    </Form.Item>

    const button = <Form.Item {...tailFormItemLayout}>
                    <Button type="primary" htmlType="submit">
                        Register
                    </Button>

    </Form.Item>


    return (
        <>
            <div className={"first-box"}>

            </div>
            <div className={"second-box"}>
                <Form {...formItemLayout}
                      form={form}
                      name="register"
                      onFinish={onFinish}
                      >
                    {userName}
                    {dateOfBirth}

                    {lastName}
                    {firstName}

                    {location}
                    {email}
                    {phone}
                    {checkAgreementBox}
                    {button}
                </Form>
            </div>
        </>

    );
}
export default SignUp;