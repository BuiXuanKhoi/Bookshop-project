import React, {useState} from "react";
import {Col, Row} from "antd";
import CartButton from "./CartButton";

export default function CartItem ({item,emptyList,config,setQuantityFlag}) {
    const [quantity,setQuantity] = useState(item.quantity);
    return (
        <Row key={item.cartItemsID}>
            {/*---------------------------Line--------------------------------*/}
            <Col span={24}>
                <Row>
                    <Col span={24} style={{borderStyle: "ridge", borderColor: "#F6F6F6"}}>
                    </Col>
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
                            <CartButton emptyList={emptyList} cartItemID={item.cartItemsID} quantity={item.quantity}
                                         config={config}  setQuantity={setQuantity} setQuantityFlag={setQuantityFlag}/>
                        </Col>
                    </Col>
                    {/*---------------------------Header--------------------------------*/}
                    <Col span={4}>
                        <p className={"positionForTitle2"}>
                            ${Math.round(quantity * item.price * 100) / 100}
                        </p>
                    </Col>
                </Row>
            </Col>
        </Row>
    );

}