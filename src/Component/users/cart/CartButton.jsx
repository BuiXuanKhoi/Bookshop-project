import React, {useEffect, useState} from "react";
import './Cart.css';
import {Button, Col, Row} from "antd";
import {MinusOutlined, PlusOutlined} from "@ant-design/icons";
import axios from "axios";

export default function CartButton({cartItemID, quantity, config, setQuantity}){

    const changeQuantity = () =>{
        axios.put("https://ecommerce-web0903.herokuapp.com/api/carts/"+cartItemID+"/change?quantity="+cartItemQuantity,null,config)
            .then((res)=>{
                console.log(res);
            })
            .catch((error) =>{
                console.log(error);
            })
    }

    const [cartItemQuantity, setCartItemQuantity] = useState(quantity);

    const decreaseBookQuantity = () => {
        setCartItemQuantity(cartItemQuantity - 1);
    }

    const increaseBookQuantity = () => {
        setCartItemQuantity(cartItemQuantity + 1);
    }

    useEffect(() => {
        setQuantity(cartItemQuantity)
        changeQuantity();
    },[cartItemQuantity])

    return(
        <Row style={{background:"#CFD2CF",marginTop:"87%"}}>
            <Col span={11} style={{borderColor:"#CFD2CF",alignItems:"center",display:"flex"}}>
                <Button onClick={decreaseBookQuantity}  style={{background:"#CFD2CF"}} icon={<MinusOutlined />}></Button>
            </Col>
            {/*--------------------------------------------------------------------------------*/}
            <Col span={2} style={{borderColor:"#CFD2CF",justifyContent:"center",display:"flex",alignItems:"center",textAlign:"center"}}>
                <p style={{marginTop:"5%",marginBottom:"5%",fontSize:"1.2vw"}}>{cartItemQuantity}</p>
            </Col>
            {/*--------------------------------------------------------------------------------*/}
            <Col span={11} style={{borderColor:"#CFD2CF",justifyContent:"right",alignItems:"center",display:"flex"}}>
                <Button onClick={increaseBookQuantity} style={{background:"#CFD2CF"}} icon={<PlusOutlined />}></Button>
            </Col>
        </Row>
    );
}