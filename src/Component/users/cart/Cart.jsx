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
    const [total,setTotal] = useState(0);
    const config = {
        headers: {Authorization:'Bearer ' + loginData.token}
    }

    const [quantityFlag, setQuantityFlag] = useState(0);
    // const copyCartList = useRef([]);
    const [emptyList,setEmptyList] = useState(false);
    const [cartList,setCartList] = useState([{}]);
    // const [cartListUse,setCartListUse] = useState()
    const flagForRegister = useRef(true);
    const getCartItem = () =>{
        axios.get("https://ecommerce-web0903.herokuapp.com/api/carts",config)
            .then((res)=>{
                setCartList(res.data);
                setEmptyList(false);
                // if(flagForRegister.current){
                //     copyCartList.current = res.data;
                //     flagForRegister.current= false;
                //     }
                }
            )
            .catch((error)=>{
                if(error.response.data.message=="List is Empty !!!"){
                    setEmptyList(true);
                }
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
    },[quantityFlag])

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
                            <BookTable  emptyList={emptyList} cartList={cartList} config={config} setQuantityFlag={setQuantityFlag}/>
                        </Row>
                        <Row className={"customerReviewPost"}>
                            <CartTotal emptyList={emptyList} cartList={cartList} total={total} config={config}/>
                        </Row>
                    </Row>
                </Col>
            </Row>
        </>
    );
}