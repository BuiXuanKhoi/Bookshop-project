import React, {useContext, useEffect, useRef, useState} from "react";
import './Cart.css'
import {Button, Col, Row} from "antd";
import {MinusOutlined, PlusOutlined} from "@ant-design/icons";
import axios from "axios";
import {useCookies} from "react-cookie";
import {getCookie} from "react-use-cookie";
import {SecurityContext} from "../../../App";


const bookTable = (props) => {
    const decreaseBookQuantity = (cartItemID,quantity) =>{
        quantity -=1;
        props.setQuatity(quantity);
        changeQuantity(cartItemID,quantity);

    }
    const increaseBookQuantity = (cartItemID,quantity) => {
        quantity +=1;
        props.setQuatity(quantity);
        changeQuantity(cartItemID,quantity);
    }
    const changeQuantity = (cartItemID,quantity) =>{
        axios.put("https://ecommerce-web0903.herokuapp.com/api/carts/"+cartItemID+"/change?quantity="+quantity,null,props.config)
            .then((res)=>{
                console.log(res);
            })
            .catch((error) =>{
                console.log(error);
            })
    }
    return (
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


export default function Cart(){
    const [loginData,setLoginData] = useContext(SecurityContext);
    const [cartId, setCartId] = useState(0);
    const [quantity,setQuantity] = useState(1);
    const [total,setTotal] = useState(0);
    const [saveCartList, setSaveCartList] = useState([]);
    const [cartList,setCartList] = useState([]);
    const savingFlag = useRef(true);
    const config = {
        headers: {Authorization:'Bearer ' + loginData.token}
    }
    const [cartList,setCartList] = useState([{}]);
    const getCartItem = () =>{
        axios.get("https://ecommerce-web0903.herokuapp.com/api/carts",config)
            .then((res)=>{
                console.log("Hello")
                setCartList(res.data);
                console.log(res.data)
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
