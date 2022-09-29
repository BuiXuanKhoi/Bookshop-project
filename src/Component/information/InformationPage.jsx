import React from "react";
import {Layout, Menu, Slider} from "antd";
import './InformationPage.css';


const { Header, Content, Footer, Sider } = Layout;

export default function InformationPage(){

    return(
        <Layout>
            <Sider
                breakpoint="lg"
                collapsedWidth="0"
            >
                <div className="logo"/>
                <Menu
                    theme="dark"
                    mode="inline"
                    defaultSelectedKeys="1"
                />

            </Sider>
        </Layout>
    );
}