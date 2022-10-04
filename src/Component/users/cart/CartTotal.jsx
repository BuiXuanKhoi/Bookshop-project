import React, {useEffect} from "react";
import './Cart.css';
import {Button, Col, Row} from "antd";

export default function CartTotal({cartList,total}){

    useEffect(() => {
        console.log(total)

    },[])

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
                            {total}
                        </p>
                    </Col>
                </Row>

                {/*--------------------------------------------------------------------------------*/}
                <Row style={{marginTop:"10%",marginBottom:"10%"}}>
                    <Col span={20} offset={2}>
                        <Button style={{background:"#CFD2CF",paddingBottom:"10%",width:"100%",fontSize:"1.5vw",fontWeight:"bolder"}}>Place order</Button>
                    </Col>
                </Row>
            </Col>
        </>
    );
}