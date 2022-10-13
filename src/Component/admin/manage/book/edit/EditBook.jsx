import React, {useContext, useEffect, useRef, useState} from "react";
import {Button, Drawer, Form, Input, Modal, Select, Space} from "antd";
import FormItem from "antd/es/form/FormItem";
import {SecurityContext} from "../../../../../App";
import {Option} from "antd/es/mentions";
import TextArea from "antd/es/input/TextArea";
import axios from "axios";
export default function EditBook ({setOpenEditBook,open,closeEdit,bookId}) {
    const [form] = Form.useForm();
    const [security, setSecurity] = useContext(SecurityContext);
    const [bookDetail,setBookDetail] = useState({});
    const [bookUpdate, setBookUpdate] = useState({});
    const formItemLayout = {
        labelCol: {
            xs: {
                span: 24,
            },
            sm: {
                span: 6,
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

    const config = {
        headers: {Authorization: 'Bearer ' + security.token}
    };
    const getBookByID = () =>{
        axios.get("https://ecommerce-web0903.herokuapp.com/api/books/"+bookId)
            .then((res) =>{
                setBookDetail({
                    bookName: res.data.bookName,
                    bookPrice: res.data.bookPrice,
                    quantity: res.data.quantity,
                    description: res.data.description,
                    state:res.data.bookState,
                })
            })
            .catch((error) =>{
                console.log(error)
            })
    }
    useEffect(()=>{
        getBookByID();
    },[bookId])

    const handleSubmit = (values) => {
        setBookUpdate({
            bookName: values.bookName,
            bookPrice: values.bookPrice,
            state: values.bookState,
            quantity: values.quantity,
            description: values.description,
        })
    }

    useEffect(()=>{
            sendEditBookRequest();
    },[bookUpdate])

    const sendEditBookRequest = () => {
        axios.put("https://ecommerce-web0903.herokuapp.com/api/books/"+bookId,bookUpdate,config)
            .then((res)=>{
                handleSuccessUpdate();
                setOpenEditBook(true);
            })
            .catch((error) =>{
                console.log(error)
            })
    }
    const handleSuccessUpdate = () =>{
        Modal.success({
            content: " Updated Book information successfully !!!"
        })
    }
    return (
        <Drawer
            title = {"Edit Book"}
            placement = {"right"}
            open={open}
            width={"40%"}
            onClose={closeEdit}
            extra = {
                <Space>
                    <Button onClick={closeEdit}>Cancel</Button>
                </Space>
            }
        >
            <Form
                onFinish={handleSubmit}
                form={form}
                {...formItemLayout}
                name={"Edit Book"}
                scrollToFirstError
                fields={[
                    {
                        name: ["bookName"],
                        value: bookDetail.bookName,
                    },
                    {
                        name: ["bookPrice"],
                        value: bookDetail.bookPrice,
                    },
                    {
                        name: ["quantity"],
                        value: bookDetail.quantity,
                    },
                    {
                        name: ["description"],
                        value: bookDetail.description,
                    },
                    {
                        name: ["bookState"],
                        value: bookDetail.state,
                    }
                ]}
            >
                {/*----------------------------------------------------------------------------------------------------------------*/}
                <Form.Item
                    name={"bookName"}
                    label={"Book Name:"}
                    rules={[
                        {
                            required : true,
                            message: 'Book name is required !!!',
                        }
                    ]}

                >
                    <Input/>
                </Form.Item>
                {/*----------------------------------------------------------------------------------------------------------------*/}
                <Form.Item
                    name={"bookPrice"}
                    label={"Book Price:"}
                    rules={[
                        {
                            required: true,
                            message: 'Book Price is required !!!',
                        },
                        {
                            pattern: new RegExp("[+-]?([0-9]*[.])?[0-9]+"),
                            message: "Book price cannot contain the character !!!",
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>
                {/*----------------------------------------------------------------------------------------------------------------*/}
                <Form.Item
                    name={"quantity"}
                    label={"Book quantity:"}
                    rules={[
                        {
                            required: true,
                            message: " Book quantity is required !!!",
                        },
                        {
                            pattern: new RegExp("^[0-9]*$"),
                            message: "Book quantity cannot contain the character !!!",
                        },
                    ]}
                >
                    <Input />
                </Form.Item>
                {/*----------------------------------------------------------------------------------------------------------------*/}
                <Form.Item
                    name={"description"}
                    label={"Description:"}
                    rules={[
                        {
                            required: true,
                            message: " Description is required !!!",
                        },
                    ]}
                >
                    <TextArea />
                </Form.Item>
                {/*----------------------------------------------------------------------------------------------------------------*/}
                <Form.Item
                    name={"bookState"}
                    label={"State of Book:"}
                >
                    <Select >
                        <Option value={"AVAILABLE"}> AVAILABLE</Option>
                        <Option value={"UNAVAILABLE"}>UNAVAILABLE</Option>
                        <Option value={"OUT_OF_STOCK"}>STOCK OUT</Option>
                    </Select>
                </Form.Item>
                {/*----------------------------------------------------------------------------------------------------------------*/}
                <Form.Item>
                    <Button style={{marginLeft:"115%"}} htmlType={"submit"} type="primary">Submit</Button>
                </Form.Item>
            </Form>
        </Drawer>
    );
}