import React, {useContext, useEffect, useState} from "react";
import './Cart.css'
import {Button, Col, Row} from "antd";
import {MinusOutlined, PlusOutlined} from "@ant-design/icons";
import axios from "axios";
import {useCookies} from "react-cookie";
import {getCookie} from "react-use-cookie";
import {SecurityContext} from "../../../App";

const cartItem = (url,bookTitle,bookPrice,bookQuantity,cartItemID) => {
    const decreaseBookQuantity = (cartItemID) =>{
        console.log(cartItemID);
    }
    const increaseBookQuantity = () => {

    }
    return (
        <>
        {/*---------------------------Line--------------------------------*/}
            <Col span={24}>
                <Row>
                    <Col span={24} style={{borderStyle:"ridge",borderColor:"#F6F6F6"}}></Col>
                </Row>
                {/*---------------------------Cart item--------------------------------*/}
                <Row style={{paddingBlock:"4%"}}>
                    <Col span={10} offset={1}>
                        <Row>
                            {/*---------------------------Header--------------------------------*/}
                            <Col span={12}>
                                <div style={{padding: "1vw"}}>
                                    <img src={url}/>
                                </div>

                            </Col>
                            {/*---------------------------Header--------------------------------*/}
                            <Col span={12}>
                                <div>
                                    <p className={"positionForTitle"}>
                                        {bookTitle}
                                    </p>
                                    <p className={"positionForTitle"} style={{marginTop:"-20%",fontSize:"1vw",fontWeight:"normal"}}>
                                        Hello no
                                    </p>
                                </div>
                            </Col>
                        </Row>
                    </Col>
                    {/*---------------------------Price--------------------------------*/}
                    <Col span={4} style={{justifyContent:"center"}}>
                        <p className={"positionForTitle2"}>
                            ${bookPrice}
                        </p>
                    </Col>
                    {/*---------------------------Quantity--------------------------------*/}
                    <Col span={4}>
                        <Col span={19}>
                            <Row style={{background:"#CFD2CF",marginTop:"87%"}}>
                                <Col span={11} style={{borderColor:"#CFD2CF",alignItems:"center",display:"flex"}}>
                                    <Button onClick={()=>{decreaseBookQuantity(cartItemID)}}  style={{background:"#CFD2CF"}} icon={<MinusOutlined />}></Button>
                                </Col>
                                {/*--------------------------------------------------------------------------------*/}
                                <Col span={2} style={{borderColor:"#CFD2CF",justifyContent:"center",display:"flex",alignItems:"center",textAlign:"center"}}>
                                    <p style={{marginTop:"5%",marginBottom:"5%",fontSize:"1.2vw"}}>{bookQuantity}</p>
                                </Col>
                                {/*--------------------------------------------------------------------------------*/}
                                <Col span={11} style={{borderColor:"#CFD2CF",justifyContent:"right",alignItems:"center",display:"flex"}}>
                                    <Button onClick={increaseBookQuantity} style={{background:"#CFD2CF"}} icon={<PlusOutlined />}></Button>
                                </Col>
                            </Row>
                        </Col>
                    </Col>
                    {/*---------------------------Header--------------------------------*/}
                    <Col span={4}>
                        <p className={"positionForTitle2"}>
                            ${bookPrice*bookQuantity}
                        </p>
                    </Col>
                </Row>
            </Col>
        </>
    );
}
const bookTable = (props) => {
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

                <Col span={4} >
                    <p className={"positionForChar2"}>
                        Total
                    </p>
                </Col>
            </Row>
            {/*---------------------------CartItem--------------------------------*/}
            {props.cartList.map((item) =>
                <Row key={item.cartItemsID}>
                    {cartItem(item.imageLink,item.bookName,item.price,item.quantity,item.cartItemsID)}
                </Row>
            )}

        </Col>
    );
}

const cartTotal = () => {
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
                        <p style={{fontSize:"2.5vw",fontWeight:"bolder"}}>$99.97</p>
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
export default function Cart(){
    const [cookies,setCookies,removeCookie] = useCookies(['book-token']);
    const [loginData,setLoginData] = useContext(SecurityContext);
    const [quantity,setQuantity] = useState(1);
    const config = {
        headers: {Authorization:'Bearer ' + loginData.token}
    }
    const [cartList,setCartList] = useState([]);
    const getCartItem = () =>{
        axios.get("https://ecommerce-web0903.herokuapp.com/api/carts",config)
            .then((res)=>{
                console.log(res.data)
                setCartList(res.data);
            })
            .catch((error)=>{
                console.log("Error");
                console.log(error);
            })
    }
    useEffect(()=>{getCartItem();},[])
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
                            {bookTable({cartList,quantity,setQuantity})}
                        </Row>
                        <Row className={"customerReviewPost"}>
                            {cartTotal()}
                        </Row>
                    </Row>
                </Col>
            </Row>
        </>
    );
}