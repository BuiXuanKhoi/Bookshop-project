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

export default function Order() {
    const [loginData, setLoginData] = useContext(SecurityContext);
    const config = {
        headers: {Authorization: 'Bearer ' + loginData.token}
    }
    const {Step} = Steps;
    const [orderList, setOrderList] = useState([]);
    const getOrderList = () => {
        axios.get("https://ecommerce-web0903.herokuapp.com/api/orders", config)
            .then((res) => {
                setOrderList(res.data);
            })
            .catch((error) => {
                console.log(error);
            })
    }

    const handleDisplay = () => {

        orderList.map((element) =>
           (
                <Row key={element.orderId} style={{background: "white", display: "flex", justifyContent: "center", marginTop: "4%"}}>
                    <Col span={23} style={{borderStyle: "ridge"}}>
                        {/*--------------------------------------------------------------------------------*/}
                        <Row className={"row-container"}>
                            <div style={{width: "100%", marginTop: "3%"}}>
                                <Steps>
                                    <Step status="finish" title="Prepared" icon={<UserOutlined/>}/>
                                    <Step status="finish" title="Packaged" icon={<FolderOutlined/>}/>
                                    <Step status="process" title="Delivered" icon={<LoadingOutlined/>}/>
                                    <Step status="wait" title="Received" icon={<SmileOutlined/>}/>
                                    <Step status="wait" title="Completed" icon={<CheckOutlined/>}/>
                                </Steps>

                            </div>
                        </Row>

                        <Row>
                            <p className={"space-line"}></p>
                        </Row>
                        {/*--------------------------------------------------------------------------------*/}

                        {element.orderItems.map((item) =>

                            <div key={item.orderItemId}>
                                <Row className={"row-container"} style={{borderBlock: "1rem"}}>
                                    <OrderItem bookName={item.bookName} imageLink={item.imageLink} price={item.price}
                                               quantity={item.quantity}/>
                                </Row>
                                <Row>
                                    <p className={"space-line"}></p>
                                </Row>
                            </div>
                        )}

                        {/*--------------------------------------------------------------------------------*/}
                        <Row style={{justifyContent: "right", marginBottom: "5%"}}>
                            <div style={{borderStyle: "ridge", display: "flex"}}>
                                <FontAwesomeIcon style={{fontSize: "4rem", marginRight: "4%"}} icon={faCreditCard}/>
                                <p style={{padding: "1px"}}> Total cost</p>
                                <p style={{padding: "1px"}}> $ 20</p>
                            </div>
                        </Row>
                    </Col>
                </Row>
           )
        )
    }

    useEffect(()=>{
        getOrderList();
    },[])

    return(
        <Row style={{background:"gray",paddingTop:"10%",borderStyle:"ridge"}}>
            <Col span={24} style={{borderStyle:"ridge"}}>
                {handleDisplay()}
            </Col>
        </Row>
    );
}