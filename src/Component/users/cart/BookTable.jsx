import React, {useEffect} from "react";
import './Cart.css';
import axios from "axios";
import {Button, Col, Row} from "antd";
import {MinusOutlined, PlusOutlined} from "@ant-design/icons";
import CartButton from "./CartButton";

export default function BookTable({emptyList,cartList,cartId,setCartId,config,quantity,setQuantity}){
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
                    <Row key={item.cartItemsID}>
                        {/*---------------------------Line--------------------------------*/}
                        <Col span={24}>
                            <Row>
                                <Col span={24} style={{borderStyle: "ridge", borderColor: "#F6F6F6"}}></Col>
                            </Row>
                            {/*---------------------------Cart item--------------------------------*/}
                            <Row style={{paddingBlock: "4%"}}>
                                <Col span={10} offset={1}>
                                    <Row>
                                        {/*---------------------------Header--------------------------------*/}
                                        <Col span={12}>
                                            <div style={{padding: "1vw"}}>
                                                <img src={item.imageLink}/>
                                            </div>

                                        </Col>
                                        {/*---------------------------Header--------------------------------*/}
                                        <Col span={12}>
                                            <div>
                                                <p className={"positionForTitle"}>
                                                    {item.bookName}
                                                </p>
                                                <p className={"positionForTitle"}
                                                   style={{marginTop: "-20%", fontSize: "1vw", fontWeight: "normal"}}>

                                                </p>
                                            </div>
                                        </Col>
                                    </Row>
                                </Col>
                                {/*---------------------------Price--------------------------------*/}
                                <Col span={4} style={{justifyContent: "center"}}>
                                    <p className={"positionForTitle2"}>
                                        ${item.price}
                                    </p>
                                </Col>
                                {/*---------------------------Quantity--------------------------------*/}
                                <Col span={4}>
                                    <Col span={19}>
                                        <CartButton setQuantity={setQuantity} quantity={item.quantity}
                                                    cartItemID={item.cartItemsID} config={config}/>
                                    </Col>
                                </Col>
                                {/*---------------------------Header--------------------------------*/}
                                <Col span={4}>
                                    <p className={"positionForTitle2"}>
                                        ${Math.round(item.price * item.quantity * 10) / 10}
                                    </p>
                                </Col>
                            </Row>
                        </Col>
                    </Row>
                )}

            </Col>
        );
    }
}