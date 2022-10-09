import React, {useEffect, useState} from "react";
import './Cart.css';
import axios from "axios";
import {Button, Col, Row} from "antd";
import {MinusOutlined, PlusOutlined} from "@ant-design/icons";
import CartButton from "./CartButton";
import CartItem from "./CartItem";

export default function BookTable({emptyList,cartList,config,setQuantityFlag}){
    if (emptyList){
        return(
            <Col span={24}>
                {/*---------------------------Header--------------------------------*/}

                <Row>
                    <Col span={10} offset={1} >
                        <p className={"positionForChar2"} style={{marginLeft:"7%"}}>
                            Product
                        </p>
                    </Col>
                    <Col span={4} >
                        <p className={"positionForChar2"}>
                            Price
                        </p>
                    </Col>

                    <Col span={4} >
                        <p className={"positionForChar2"}>
                            Quantity
                        </p>
                    </Col>

                    <Col span={4} >
                        <p className={"positionForChar2"}>
                            Total
                        </p>
                    </Col>
                </Row>
                <Row>
                    <Col span={24} style={{borderStyle: "ridge", borderColor: "#F6F6F6"}}></Col>
                </Row>
                <Row>
                    <Col span={24} style={{justifyContent:"center",display:"flex",textAlign:"center"}}>
                        <p className={"positionForChar2"} >No items in your cart !!!</p>
                    </Col>
                </Row>
            </Col>
        );
    }
    else {
        return (
            <Col span={24}>
                {/*---------------------------Header--------------------------------*/}

                <Row>
                    <Col span={10} offset={1}>
                        <p className={"positionForChar2"} style={{marginLeft: "7%"}}>
                            Product
                        </p>
                    </Col>
                    <Col span={4}>
                        <p className={"positionForChar2"}>
                            Price
                        </p>
                    </Col>

                    <Col span={4}>
                        <p className={"positionForChar2"}>
                            Quantity
                        </p>
                    </Col>

                    <Col span={4}>
                        <p className={"positionForChar2"}>
                            Total
                        </p>
                    </Col>
                </Row>
                {/*---------------------------CartItem--------------------------------*/}
                {cartList.map((item) =>
                    <CartItem item={item} emptyList={emptyList} config={config} setQuantityFlag={setQuantityFlag}/>
                )}

            </Col>
        );
    }
}