import React, {useEffect, useRef, useState} from "react";
import {Button, Col, Form, Input, Modal, Select} from "antd";
import TextArea from "antd/es/input/TextArea";
import {Option} from "antd/es/mentions";
import axios from "axios";
import './DropDownCustom.css'
import { default as ReactSelect } from "react-select";
import { components } from "react-select";



export default function TabBook({formItemLayout, form, config,isOpen,setIsOpen}){

    const state = useRef(false);
    const formData = new FormData();
    const [imageDetail, setImageDetail] = useState("");
    const [bookRegister, setBookRegister] = useState({});
    const [listCategory, setListCategory] = useState([]);
    const [optionList, setOptionList] = useState([]);
    let categoryChosenList=[]
    const { Option } = Select;
    let authorChosenList = []
    const [authorList,setAuthorList] = useState([]);
    const handleCreateBook = (values) => {
        setBookRegister({
            bookName: values.bookName,
            bookPrice: values.bookPrice,
            description: values.description,
            quantity: values.quantity,
            state: values.bookState,
            listCategory: categoryChosenList,
            imageLink: values.imageLink,
            authorId: values.authorName,
        })
    }

    const sendRequestCategoryList = () =>{
        axios.get("https://ecommerce-web0903.herokuapp.com/api/categories")
            .then((res) =>{
                setListCategory(res.data)
            })
            .catch((error) =>{
                console.log(error);
            })
    }
    const sendRequestAuthorList = () => {
        axios.get("https://ecommerce-web0903.herokuapp.com/api/authors")
            .then((res) =>{
                setAuthorList(res.data)
            })
            .catch((error) => {
                console.log(error)
            })
    }
    const handleCreateNewCategoryList = () => {
        listCategory.map((item) =>{
            let value = {value:"",label:""};
            value.value = item.categoryId;
            value.label = item.name;
            optionList.push(value)
        })
    }

    useEffect(()=>{
        sendRequestCategoryList();
        sendRequestAuthorList();
    },[isOpen])

    useEffect(()=>{
        handleCreateNewCategoryList();
    },[listCategory]);

    useEffect(()=>{
        if(state.current){
            formData.append("data", JSON.stringify(bookRegister))
            formData.append("image", imageDetail);
        }else {
            state.current = true;
        }
    },[bookRegister]);

    useEffect(() => {
        createBook();
    },[formData])

    const createBook = () => {
        console.log(formData);
        axios.post('https://ecommerce-web0903.herokuapp.com/api/books', formData, config)
            .then((res) => {
                handleSuccessAdd();
                setIsOpen(false);
            })
            .catch((err) => {
                console.log(err)
            })
    }
    const handleSuccessAdd = () => {
        Modal.success({
            content:"Book has been added to shop !!!"
        })
    }

    const handleChange = (values) =>{
        categoryChosenList=[]
        values.map((item)=>{
            let number = 0;
            number = item.value;
            categoryChosenList.push(number)
        })
    }

    const uploadImage = (event) => {
        setImageDetail(event.target.files[0]);
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
                <Input defaultValue={""} placeholder="Input Book Name"/>
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
                <Input defaultValue={""} placeholder="Input Book Price"/>
            </Form.Item>

            <Form.Item
                name="description"
                label="Book Description"
            >
                <TextArea defaultValue={""} rows={5} cols={10} placeholder="Write the book's description"/>
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
                <Input defaultValue={""} placeholder="Input your quantity"/>
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
                <Select defaultValue={""} placeholder='select book state'>
                    <Option value='AVAILABLE'>AVAILABLE</Option>
                    <Option value='UNAVAILABLE'>UNAVAILABLE</Option>
                    <Option value='OUT_OF_STOCK'>OUT OF STOCK</Option>
                </Select>
            </Form.Item>

            <Form.Item
                name = 'bookCategory'
                label={"Category:"}
                >
                <div className={"borderBox"}>
                        <ReactSelect
                            options={optionList}
                            isMulti
                            closeMenuOnSelect={false}
                            hideSelectedOptions={false}
                            allowSelectAll={false}
                            onChange={handleChange}
                        />
                </div>

            </Form.Item>

            <Form.Item
                name = "authorName"
                label={"Author Name:"}
            >
                <Select
                >
                    {authorList.map((item)=>
                        <Option value={item.authorID}>{item.authorName}</Option>
                    )}
                </Select>
            </Form.Item>
            <Form.Item
                name="image"
                label="Upload Image"
            >
                <input onChange={event => uploadImage(event)} type="file" placeholder="Input book image"/>
            </Form.Item>
        </Form>

    )
}