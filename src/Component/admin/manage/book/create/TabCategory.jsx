import React, {useEffect, useRef, useState} from "react";
import {Form, Tabs} from "antd";
import Input from "antd/es/input/Input";
import TextArea from "antd/es/input/TextArea";
import axios from "axios";

export default function TabCategory({formItemLayout, form, config}){

    const state = useRef(false);


    const [categoryRegister, setCategoryRegister] = useState({
        categoryName : '',
        categoryDescription : ''
    })

    const handleCreateCategory = (values) => {
        setCategoryRegister({
            categoryName: values.categoryName,
            categoryDescription: values.categoryDescription
        })
    }

    useEffect(() => {
        if(state.current){
            axios.post('https://ecommerce-web0903.herokuapp.com/api/categories', categoryRegister, config)
                .then((res) => window.location.reload())
                .catch((err) => console.log(err))
        }else{
            state.current = true;
        }
    },[categoryRegister])

    return(
        <Form
            {...formItemLayout}
            form={form}
            name="createCategory"
            scrollToFirstError
            onFinish={handleCreateCategory}
        >
            <Form.Item
                name='categoryName'
                label='Name'
                rules={[
                    {
                        required: true,
                        message: 'Category name is required'
                    }
                ]}
            >
                <Input placeholder="Input category name here"/>
            </Form.Item>

            <Form.Item
                name='categoryDescription'
                label='Description'
            >
                <TextArea rows={5} cols={10} placeholder="Input Category Description here"/>
            </Form.Item>

        </Form>
    )

}