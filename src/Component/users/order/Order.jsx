import React, {useContext, useEffect, useState} from "react";
import {Row, Col, Steps, Button} from "antd";
import './Order.css';
import { LoadingOutlined, SmileOutlined, SolutionOutlined, UserOutlined,CheckOutlined, FolderOutlined } from '@ant-design/icons';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCreditCard} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import OrderItem from "./OrderItem";
import {SecurityContext} from "../../../App";
import {get} from "react-hook-form";
import OrderHistory from "./OrderHistory";

export default function Order() {
    const [loginData, setLoginData] = useContext(SecurityContext);
    const config = {
        headers: {Authorization: 'Bearer ' + loginData.token}
    }

    const [orderList, setOrderList] = useState([]);
    const [element,setElement] = useState([]);

    const getOrderList = () => {
        axios.get("https://ecommerce-web0903.herokuapp.com/api/orders/manage", config)
            .then((res) => {
                setOrderList(res.data);
            })
            .catch((error) => {
                console.log(error);
            })
    }
    useEffect(()=>{
        getOrderList();
    },[])

    return(
        <Row style={{background:"gray",paddingTop:"10%",borderStyle:"ridge"}}>
            <Col span={24} style={{borderStyle:"ridge"}}>
                {   orderList.map((element) =>
                    (<OrderHistory element={element}/>)
                )}
            </Col>
        </Row>
    );
}