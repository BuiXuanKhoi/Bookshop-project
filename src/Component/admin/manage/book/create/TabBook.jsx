import React, {useRef, useState} from "react";
import {Form, Input, Select} from "antd";
import TextArea from "antd/es/input/TextArea";
import {Option} from "antd/es/mentions";

export default function TabBook({formItemLayout, form, config}){

    const state = useRef(false);


    const [bookRegister, setBookRegister] = useState({
        bookName : '',
        bookPrice : 0.0,
        description : '',
        quantity : 0,
        authorId : 0,
        state : '',
        imageLink : '',
        listCategory : []
    })

    const handleCreateBook = (values) => {
        console.log(values);
    }

    return(
        <Form
            {...formItemLayout}
            form={form}
            name="createBook"
            onFinish={handleCreateBook}
            scrollToFirstError
        >
            <Form.Item
                name="bookName"
                label="Book Name"
                rules={[
                    {
                        required: true,
                        message: 'Book Name is required'
                    }
                ]}
            >
                <Input placeholder="Input Book Name"/>
            </Form.Item>
            <Form.Item
                name="bookPrice"
                label="Price"
                rules={[
                    {
                        required: true,
                        message: "Book Price is required"
                    },
                    {
                        pattern: '[0-9]+',
                        message: 'Price must be numeric'
                    }
                ]}
            >
                <Input placeholder="Input Book Priace"/>
            </Form.Item>
            <Form.Item
                name="description"
                label="Book Description"
            >
                <TextArea rows={5} cols={10} placeholder="Write the book's description"/>
            </Form.Item>
            <Form.Item
                name="quantity"
                label="Book Quantity"
                rules={[
                    {
                        required: true,
                        message: "Quantity is required"
                    },
                    {
                        pattern: '[0-9]+',
                        message: "Quantity only number"
                    }
                ]}
            >
                <Input placeholder="Input your quantity"/>
            </Form.Item>
            <Form.Item
                name='bookState'
                label='State'
                rules={[
                    {
                        required: true,
                        message: 'Book State is required'
                    }
                ]}
            >
                <Select placeholder='select book state'>
                    <Option value='AVAILABLE'>AVAILABLE</Option>
                    <Option value='UNAVAILABLE'>UNAVAILABLE</Option>
                    <Option value='OUT_OF_STOCK'>OUT OF STOCK</Option>
                    <Option value='EXPIRED'>EXPIRED</Option>
                </Select>
            </Form.Item>
        </Form>
    )
}