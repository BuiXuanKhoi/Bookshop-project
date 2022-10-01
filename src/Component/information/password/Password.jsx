import React, {useEffect, useRef, useState} from "react";
import {Button, Form, Input, Modal} from "antd";
import axios from "axios";

export default function Password({isOpen, closeChangePasswordModal, config}){

    const [form] = Form.useForm();
    const [isSuccess, setIsSuccess] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [isFail, setIsFail] = useState(false);

    const [password, setPassword] = useState({
        oldPassword : '',
        newPassword : ''
    })

    const state = useRef(false);

    const openSuccessModal = () => {
        setIsSuccess(true);
    }

    const closeSuccessModal = () => {
        setIsSuccess(false);
    }

    const openFailModal = () => {
        closeChangePasswordModal();
        setIsFail(true);
    }

    const closeFailModal = () => {
        setIsFail(false);
    }

    const setupChangePassword = (values) => {
        setPassword({
            oldPassword: values.oldPass,
            newPassword: values.newPass
        })
    }

    const changePassword = () => {
        axios.put('https://ecommerce-web0903.herokuapp.com/api/users/password', password, config)
            .then((res) => {
                console.log(res.data);
                closeSuccessModal();
                openSuccessModal();
            }).catch((err) => {
                console.log(err)
                if(err.response.data.statusCode === 404){
                    setErrorMessage(err.response.data.message);
                }
        })
    }

    useEffect(() => {
        if(state.current){
            openFailModal();
        }else{
            state.current = true;
        }

    },[errorMessage])

    useEffect(() => {
            changePassword();
    },[password])




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
                closable={true}
                centered
                open={isFail}
                onOk={closeFailModal}
                onCancel={closeFailModal}
            >
                <span>{errorMessage}</span>
            </Modal>
            <Modal
                title="Success"
                centered
                open={isSuccess}
                onOk={closeSuccessModal}
                onCancel={closeSuccessModal}
            >
                <span>Change Password Successfully !!!</span>
            </Modal>
        <Modal
            title="Change Password"
            centered
            open={isOpen}
            onOk={form.submit}
            onCancel={closeChangePasswordModal}
        >
            <Form
                {...formItemLayout}
                form={form}
                name="changePass"
                scrollToFirstError
                onFinish={setupChangePassword}
            >
                <Form.Item
                    name="oldPass"
                    label= "Old Password"
                    rules={[
                        {
                            required : true,
                            message: "Old password is required"
                        },
                        {
                            min: 6,max : 15,
                            message: " Password must around 6 and 15 characters"
                        }
                    ]}
                >
                    <Input.Password/>
                </Form.Item>
                <Form.Item
                    name="newPass"
                    label="New Password"
                    rules={[
                        {
                            required:true,
                            message: "New Password is required"
                        },
                        {
                            min: 6,max : 15,
                            message: "New Password must around 6 and 15 characters"
                        }
                    ]}
                    hasFeedback
                >
                    <Input.Password/>
                </Form.Item>
                <Form.Item
                    name="retype"
                    label="Re-type New Password"
                    dependencies={["newPass"]}
                    rules={[
                        {
                            required: true,
                            message: "Please confirm password!!! "
                        },
                        ({ getFieldValue }) => ({
                            validator(_,value){
                                if(!value || getFieldValue('newPass') === value){
                                    return Promise.resolve();
                                }
                                return Promise.reject(new Error('Password not matches!!! '))
                            },
                        }),
                        {
                            min: 6,max : 15,
                            message: "New Password must around 6 and 15 characters"
                        }
                    ]}
                >
                    <Input.Password/>
                </Form.Item>
            </Form>
        </Modal>
        </>
    )

}