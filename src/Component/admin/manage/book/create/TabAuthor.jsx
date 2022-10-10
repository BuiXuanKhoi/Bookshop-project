import React, {useEffect, useRef, useState} from "react";
import {Form, Tabs} from "antd";
import Input from "antd/es/input/Input";
import TextArea from "antd/es/input/TextArea";
import axios from "axios";

export default function TabAuthor({formItemLayout, form, config}){

    const state = useRef(false);

    const [authorRegister, setAuthorRegister] = useState({
        authorName : ''
    })

    const handleCreateAuthor = (values) => {
        setAuthorRegister({
            authorName: values.authorName
        })
    }

    useEffect(() => {
        if(state.current){
            axios.post('https://ecommerce-web0903.herokuapp.com/api/authors', authorRegister, config)
                .then((res) => window.location.reload())
                .catch((err) => console.log(err))
        }else {
            state.current = true;
        }
    },[authorRegister])

    return(
        <Form
            {...formItemLayout}
            form={form}
            name="createAuthor"
            scrollToFirstError
            onFinish={handleCreateAuthor}
        >
            <Form.Item
                name='authorName'
                label='Name'
                rules={[
                    {
                        required: true,
                        message: 'Author name is required'
                    }
                ]}
            >
                <Input placeholder="Input author name here"/>
            </Form.Item>
        </Form>
    )

}