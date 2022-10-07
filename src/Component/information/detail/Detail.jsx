import React, {useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {Button, DatePicker, Form, Input, Modal, Select} from "antd";
import {Option} from "antd/es/mentions";
import {getCookie} from "react-use-cookie";
import axios from "axios";
import {useCookies} from "react-cookie";
import {useNavigate} from "react-router";
import 'react-moment';
import moment from "moment";


export default function Detail({config}){

    const [cookies, setCookies, removeCookies] = useCookies(['book-token']);
    const navigate = useNavigate();

    const [form] = Form.useForm();
    const [isOpenSession, setIsOpenSession] = useState(false);


    const [userDetail, setUserDetail] = useState({
        dateOfBirth : '',
        address : '',
        email : '',
        phoneNumber : '',
        firstName : '',
        lastName : '',
        createDate : '',
        updateDate : ''
    })

    const [modifyRequest, setModifyRequest] = useState({
        firstName : '',
        lastName : '',
        dateOfBirth : '',
        address : '',
        phoneNumber : '',
        email : ''
    })

    const closeSession = () => {
        removeCookies('book-token');
        navigate('/login');
    }


    const handleSubmitForm = (values) => {
        console.log(values);
        console.log(values.dateOfBirth.format('dd/mm/yyyy'));
        // setModifyRequest({
        //     firstName: values.firstName,
        //     lastName: values.lastName,
        //     email: values.email,
        //     dateOfBirth: values.dateOfBirth.format('dd/mm/yyyy'),
        //     address: values.address,
        //     phoneNumber: values.phoneNumber
        // })
    }

    const getInformationDetail = () => {
        axios.get('https://ecommerce-web0903.herokuapp.com/api/users/information', config)
            .then((res) => {
                setUserDetail(res.data);
                console.log(res.data);
            }).catch((err) => {

            if(err.response.data.statusCode === 401)
            {
                if(err.response.data.message === 'Expired JWT Token')
                {
                    setIsOpenSession(true);
                }else if (err.response.data.message === 'Cannot determine error'){
                    setIsOpenSession(true);
                }
            }

        })
    }

    useEffect(() => {
        getInformationDetail();
    },[])

    useEffect(() => {
        form.setFieldsValue({
            firstName: userDetail.firstName,
            lastName : userDetail.lastName,
            email: userDetail.email,
            phoneNumber : userDetail.phoneNumber,
            location : userDetail.address,
            dateOfBirth : moment(userDetail.dateOfBirth, 'YYYY-MM-DD')
        })
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
        <>
        <Modal
            title="Authentication Error"
            centered
            open={isOpenSession}
            onOk={() => {
                setIsOpenSession(false);
            }}
            onCancel={() => {
                setIsOpenSession(false);
            }}
            afterClose={closeSession}
            width={400}
        >
            <p>Your Session is expired. Please Login again !!!</p>
        </Modal>

        <Form
            {...formItemLayout}
            form={form}
            name="edit"
            onFinish={handleSubmitForm}
            scrollToFirstError
        >
            <Form.Item
                name="firstName"
                label="First Name"
                rules={[
                    {
                        required : true,
                        message: "Please input your first name here!!! "
                    }
                ]}
                style={{
                    width: '100%'
                }}
            >
                <Input/>
            </Form.Item>

            <Form.Item
                name="lastName"
                label="Last Name"
                rules={[
                    {
                        required: true,
                        message: "Please input your last name here!!! "
                    }
                ]}
                style={{
                    width: '100%'
                }}
            >
                <Input/>
            </Form.Item>

            <Form.Item
                name="email"
                label="E-mail"
                rules={[

                    {
                        type:"email",
                        message: "Your email is invalid"
                    },
                    {
                        required: true,
                        message: "Please input your email here!!! "
                    }
                ]}
                style={{
                    width: '100%'
                }}
            >
                <Input/>
            </Form.Item>
            <Form.Item
                name="phoneNumber"
                label="Phone Number"
                rules={[
                    {
                        max : 11,
                        message: "Phone number must have maximum is 11 characters!!! "
                    },
                    {
                        min: 10,
                        message: "Phone number must have at least 10 characters!!! "
                    },
                    {
                        required: true,
                        message: "Please input your phone number here!!! "
                    }
                ]}
                style={{
                    width: '100%'
                }}
            >
                <Input
                    addonBefore="+84"
                    style={{
                        width: "100%"
                    }}
                />
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
            >
                <Select>
                    <Option value="HN">HN</Option>
                    <Option value="SG">SG</Option>
                    <Option value="DN">DN</Option>
                </Select>
            </Form.Item>

            <Form.Item {...tailFormItemLayout}>
                <Button type="primary" htmlType="submit">
                    Modify
                </Button>
            </Form.Item>
        </Form>
        </>
    );

}