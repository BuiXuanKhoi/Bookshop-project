import React, {useContext, useEffect, useState} from "react";
import {Button, Drawer, Dropdown, Form, Input, Space, Tabs} from "antd";
import {useForm} from "react-hook-form";
import TextArea from "antd/es/input/TextArea";
import axios from "axios";
import TabBook from "./TabBook";
import TabCategory from "./TabCategory";
import TabAuthor from "./TabAuthor";
import {SecurityContext} from "../../../../../App";


const menuAuthor = (listAuthor) => {


}


export default function CreateBook({isOpen,closeCreateBookForm}){


    const [formBook] = Form.useForm();
    const [formAuthor] = Form.useForm();
    const [formCategory] = Form.useForm();
    const [tab, setTab] = useState('1');

    const [security, setSecurity] = useContext(SecurityContext);

    const config = {
        headers: {Authorization: 'Bearer ' + security.token}
    };

    const handleSwitchTab = (key) =>{
        setTab(key);
    }

    const handleSubmit = () => {
        if(tab === '1'){
            formBook.submit();
        }else if (tab === '2'){
            formCategory.submit();
        }else if ( tab === '3'){
            formAuthor.submit();
        }
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




    return(
        <Drawer
            title="Create"
            width={700}
            open={isOpen}
            onClose={closeCreateBookForm}
            extra={
                <Space>
                    <Button onClick={closeCreateBookForm}>Cancel</Button>
                    <Button onClick={handleSubmit} type="primary">Submit</Button>
                </Space>
            }
        >
            <Tabs defaultActiveKey="1" onTabClick={handleSwitchTab}>
                <Tabs.TabPane tab="Book" key="1" >
                    <TabBook formItemLayout={formItemLayout} form={formBook} config={config}/>
                </Tabs.TabPane>
                <Tabs.TabPane tab="Category" key="2">
                    <TabCategory formItemLayout={formItemLayout} form={formCategory} config={config}/>
                </Tabs.TabPane>
                <Tabs.TabPane tab="Author" key="3">
                    <TabAuthor formItemLayout={formItemLayout} form={formAuthor} config={config}/>
                </Tabs.TabPane>
            </Tabs>
        </Drawer>
    )
}