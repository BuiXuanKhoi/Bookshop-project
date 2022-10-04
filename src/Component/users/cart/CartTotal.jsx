import React, {useEffect} from "react";
import './Cart.css';
import {Button, Col, Modal, Row} from "antd";
import axios from "axios";
import './Cart.css';


export default function CartTotal({emptyList,cartList,total,config}){
    if(emptyList){
        return (
            <>
                <Col span={24} style={{justifyContent:"center",textAlign:"center"}}>
                    <Row style={{background:"#F6F6F6"}}>
                        <Col span={20} offset={2}>
                            <p className="positionForChar2" style={{fontSize:"1.5vw"}}> Cart totals</p>
                        </Col>
                    </Row>
                    {/*--------------------------------------------------------------------------------*/}
                    <Row>
                        <Col span={24} style={{borderStyle:"ridge",borderColor:"#F6F6F6"}}>
                        </Col>
                    </Row>

                    {/*--------------------------------------------------------------------------------*/}
                    <Row style={{marginTop:"10%"}}>
                        <Col span={20} offset={2}>
                            <p style={{fontSize:"2.5vw",fontWeight:"bolder"}}>
                            </p>
                        </Col>
                    </Row>

                    {/*--------------------------------------------------------------------------------*/}
                    <Row style={{marginTop:"10%",marginBottom:"10%"}}>
                        <Col span={20} offset={2}>
                            <Button  style={{background:"#CFD2CF",paddingBottom:"10%",width:"100%",fontSize:"1.5vw",fontWeight:"bolder"}}>Place order</Button>
                        </Col>
                    </Row>
                </Col>
            </>
        );
    }
    else{
        const addToOrder = () => {
            axios.post("https://ecommerce-web0903.herokuapp.com/api/orders",null
                ,config)
                .then((res)=>{
                    window.location.reload();
                })
                .catch((error) =>{
                    window.location.reload();
                })
        }
        const handleAddSuccess = () => {
            Modal.success({
                content: 'Your order is on the way',
            });
        }
        return (
            <>
                <Col span={24} style={{justifyContent:"center",textAlign:"center"}}>
                    <Row style={{background:"#F6F6F6"}}>
                        <Col span={20} offset={2}>
                            <p className="positionForChar2" style={{fontSize:"1.5vw"}}> Cart totals</p>
                        </Col>
                    </Row>
                    {/*--------------------------------------------------------------------------------*/}
                    <Row>
                        <Col span={24} style={{borderStyle:"ridge",borderColor:"#F6F6F6"}}>
                        </Col>
                    </Row>

                    {/*--------------------------------------------------------------------------------*/}
                    <Row style={{marginTop:"10%"}}>
                        <Col span={20} offset={2}>
                            <p style={{fontSize:"2.5vw",fontWeight:"bolder"}}>
                                ${total}
                            </p>
                        </Col>
                    </Row>

                    {/*--------------------------------------------------------------------------------*/}
                    <Row style={{marginTop:"10%",marginBottom:"10%"}}>
                        <Col span={20} offset={2}>
                            <Button onClick={addToOrder} style={{background:"#CFD2CF",paddingBottom:"10%",width:"100%",fontSize:"1.5vw",fontWeight:"bolder"}}>Place order</Button>
                        </Col>
                    </Row>
                </Col>
            </>
        );
    }
}