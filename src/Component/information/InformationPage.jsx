import React, {useContext, useState} from "react";
import {Col, Form, Layout, Menu, Modal, Row, Slider} from "antd";
import './InformationPage.css';
import Detail from "./detail/Detail";
import Password from "./password/Password";
import '@fortawesome/fontawesome-svg-core';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircleInfo, faLock} from "@fortawesome/free-solid-svg-icons";
import {getCookie} from "react-use-cookie";
import {SecurityContext} from "../../App";


export default function InformationPage(){

    const[isOpenChangePassword, setIsOpenChangePassword] = useState(false);
    const [loginData, setLoginData] = useContext(SecurityContext);
    const config = {
        headers: {Authorization: 'Bearer ' + JSON.parse(getCookie('book-token')).token}
    }


    const openChangePasswordModal = () => {
        setIsOpenChangePassword(true);
    }

    const closeChangePasswordModal = () => {
        setIsOpenChangePassword(false);
    }

    return(
        <div className="background-container">
            <Password config={config} isOpen={isOpenChangePassword} closeChangePasswordModal={closeChangePasswordModal}/>
            <div className="info-container">
                <Row>
                    <Col span={18} push={6}>
                        <h2>Account Information</h2>
                        <div className="detail-container">
                            <Detail config={config}/>
                        </div>
                    </Col>
                    <Col span={6} pull={18}>
                        <div className="info-select-column">
                            <div className="user-avatar-container">
                                <div className="user-avatar-placeholder">
                                    <img className="user-avatar" src="https://cf.shopee.vn/file/bf14a6d977125636cd97110271911699_tn" alt={"User Avatar"}/>
                                </div>
                                <div className="user-name-box">
                                    <div className="user-name"> {loginData.userName}'s Information</div>
                                </div>
                            </div>
                            <Menu mode="inline" className="menu">
                                <Menu.Item  icon={<FontAwesomeIcon style={{color:"black"}} icon={faCircleInfo}/>}>Detail</Menu.Item>
                                <Menu.Item onClick={openChangePasswordModal} icon={<FontAwesomeIcon style={{color:"black"}} icon={faLock}/>}>Password</Menu.Item>
                            </Menu>
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
    );
}