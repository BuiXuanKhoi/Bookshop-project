import React, {useEffect, useState} from "react";
import {Col, Menu, Row, Table} from "antd";
import './ManageCustomer.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPeopleRoof, faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import UserTable from "./table/UserTable";
import {getCookie} from "react-use-cookie";
import {useCookies} from "react-cookie";
import CreateUser from "./create/CreateUser";


const column = [
    {
        title: 'User name',
        dataIndex: 'userName',
        sorter : true
    },
    {
        title : 'First Name',
        dataIndex: 'firstName',
        sorter: true
    },
    {
        title: 'Last Name',
        dataIndex: 'lastName',
        sorter : true
    },
    {
        title: 'E-mail',
        dataIndex: 'email',
        sorter : true
    },
    {
        title: 'Date Of Birth',
        dataIndex: 'dateOfBirth',
        sorter : true
    }
]


export default function ManageCustomer(){

    const [isOpen, setIsOpen] = useState(false);

    const openCreateAdminForm = () => {
        setIsOpen(true);
    }

    const closeCreateAdminForm = () => {
        setIsOpen(false);
    }





    const [sort, setSort] = useState('ea');

    const [cookies, setCookies, removeCookies] = useCookies(['book-token']);
    const [token, setToken] = useState('');
    const config = {
        headers: {Authorization: 'Bearer ' + token}
    };

    useEffect(() => {
        if(getCookie('book-token') !== ''){
            setToken(JSON.parse(getCookie('book-token')).token);
        }else{
            setToken('no-token');
        }
    })


    const onChange = (sorter) => {
        console.log(sorter);
    }

    return(
        <div className="usertable-container">
            <Row>
                <Col span={5} >
                    <div>
                        <div className="logo-placeholder">
                            <img className="logo-container" src="https://s.tmimgcdn.com/scr/1200x750/242600/book-shop-modern-logo-template_242604-original.jpg"/>
                        </div>
                        <div >
                            <Menu mode="inline" className="manage-user-menu">
                                <Menu.Item icon={<FontAwesomeIcon icon={faPeopleRoof}/>} >Manage</Menu.Item>
                                <Menu.Item onClick={openCreateAdminForm} icon={<FontAwesomeIcon icon={faSquarePlus}/>}>Create</Menu.Item>
                            </Menu>
                        </div>
                    </div>
                </Col>
                <Col span={19}>
                    <CreateUser isOpen={isOpen} close={closeCreateAdminForm}/>
                    <UserTable config={config} token={token}/>
                </Col>
            </Row>
        </div>
    );

}