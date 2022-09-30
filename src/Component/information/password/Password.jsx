import React from "react";
import {Button, Form, Input} from "antd";

export default function Password({isOpen}){

    const [form] = Form.useForm();


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
        <Form
            {...formItemLayout}
            form={form}
            name="changePass"
            scrollToFirstError
        >
            <Form.Item
                name="oldPass"
                label= "Old Password"
                rules={[
                    {
                        required : true,
                        message: "Old password is required"
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
            >
                <Input.Password/>
            </Form.Item>
        </Form>
    )

}