import React, {useContext, useEffect, useRef, useState} from "react";
import './Cart.css'
import {Button, Col, Row} from "antd";
import {MinusOutlined, PlusOutlined} from "@ant-design/icons";
import axios from "axios";
import {useCookies} from "react-cookie";
import {getCookie} from "react-use-cookie";
import {SecurityContext} from "../../../App";
import BookTable from "./BookTable";
import CartTotal from "./CartTotal";


export default function Cart(){
    const [loginData,setLoginData] = useContext(SecurityContext);
    const [cartId, setCartId] = useState(0);
    const [quantity,setQuantity] = useState(1);
    const [total,setTotal] = useState(0);
    const config = {
        headers: {Authorization:'Bearer ' + loginData.token}
    }
    const [cartList,setCartList] = useState([{}]);
    const getCartItem = () =>{
        axios.get("https://ecommerce-web0903.herokuapp.com/api/carts",config)
            .then((res)=>{
                setCartList(res.data);
            })
            .catch((error)=>{
                console.log(error);
            })
    }
    const calculateTotal = () =>{
        let listCost = cartList.map(item=>item.price*item.quantity);
        setTotal(listCost.reduce((a,b)=>a+b,0));
    }
    useEffect(()=> {
        getCartItem();
        },[])

    useEffect(()=> {
        getCartItem();
    },[quantity])

    useEffect(() => {
        calculateTotal();
    },[cartList])
    return(
        <>
            <Row style={{paddingTop:"10%"}}>
                <Col span={24}>
                    <Row>
                        <Col>
                            <Row>

                            </Row>
                            <Row>
                                <Col span={24} style={{borderStyle:"ridge",borderColor:"#F6F6F6"}}> </Col>
                            </Row>
                        </Col>
                    </Row>
                    <Row>
                        <Row className={"customerReviewList"}>
                            <BookTable cartList={cartList} cartId={cartId} setCartId={setCartId} config={config} quantity={quantity} setQuantity={setQuantity} />
                        </Row>
                        <Row className={"customerReviewPost"}>
                            <CartTotal cartList={cartList} total={total} />
                        </Row>
                    </Row>
                </Col>
            </Row>
        </>
    );
}
