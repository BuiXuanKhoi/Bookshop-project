import React from "react";
import './Cart.css'
import {Button, Col, Row} from "antd";
import {MinusOutlined, PlusOutlined} from "@ant-design/icons";

const cartItem = (url) => {
    return (
        <>
        {/*---------------------------Line--------------------------------*/}
        <Row>
            <Col span={24} style={{borderStyle:"ridge",borderColor:"#F6F6F6"}}></Col>
        </Row>
        {/*---------------------------Cart item--------------------------------*/}
        <Row style={{paddingBlock:"4%"}}>

            <Col span={10} offset={1}>
                <Row>
                    {/*---------------------------Header--------------------------------*/}
                    <Col span={12}>
                        <img  src={url}/>
                    </Col>
                    {/*---------------------------Header--------------------------------*/}
                    <Col span={12}>
                        <div>
                            <p className={"positionForTitle"}>
                                Book Title
                            </p>
                            <p className={"positionForTitle"} style={{marginTop:"-20%",fontSize:"1vw",fontWeight:"normal"}}>
                                Author Name
                            </p>
                        </div>
                    </Col>
                </Row>
            </Col>
            {/*---------------------------Price--------------------------------*/}
            <Col span={4} style={{justifyContent:"center"}}>
                <p className={"positionForTitle2"}>
                    $29.99
                </p>
            </Col>
            {/*---------------------------Quantity--------------------------------*/}
            <Col span={4}>
                <Col span={19}>
                    <Row style={{background:"#CFD2CF",marginTop:"87%"}}>
                        <Col span={11} style={{borderColor:"#CFD2CF",alignItems:"center",display:"flex"}}>
                            <Button style={{background:"#CFD2CF"}} icon={<MinusOutlined />}></Button>
                        </Col>
                        {/*--------------------------------------------------------------------------------*/}
                        <Col span={2} style={{borderColor:"#CFD2CF",justifyContent:"center",display:"flex",textAlign:"center"}}>
                            <p style={{marginTop:"5%",marginBottom:"5%",fontSize:"1.2vw"}}>1</p>
                        </Col>
                        {/*--------------------------------------------------------------------------------*/}
                        <Col span={11} style={{borderColor:"#CFD2CF",justifyContent:"right",alignItems:"center",display:"flex"}}>
                            <Button  style={{background:"#CFD2CF"}} icon={<PlusOutlined />}></Button>
                        </Col>
                    </Row>
                </Col>
            </Col>
            {/*---------------------------Header--------------------------------*/}
            <Col span={4}>
                <p className={"positionForTitle2"}>
                    $59.88
                </p>
            </Col>
        </Row>
        </>
    );
}
const bookTable = () => {
    return (
        <Col span={24}>
            {/*---------------------------Header--------------------------------*/}
            <Row>
                <Col span={10} offset={1} >
                    <p className={"positionForChar"}>
                        Product
                    </p>
                </Col>
                <Col span={4} >
                    <p className={"positionForChar"}>
                        Price
                    </p>
                </Col>
                <Col span={4} >
                    <p className={"positionForChar"}>
                        Quantity
                    </p>
                </Col>
                <Col span={4} >
                    <p className={"positionForChar"}>
                        Total
                    </p>
                </Col>
            </Row>
            {/*---------------------------CartItem--------------------------------*/}
            {cartItem("https://www.lesmurray.org/wp-content/uploads/2020/12/war-and-peace.png")};
            {cartItem("https://cdn-amz.woka.io/images/I/71Ui-NwYUmL.jpg")}
        </Col>
    );
}

const cartTotal = () => {
    return (
        <>
            <Col span={24} style={{justifyContent:"center",textAlign:"center"}}>
                <Row style={{background:"#F6F6F6"}}>
                    <Col span={20} offset={2}>
                        <p className="positionForChar" style={{fontSize:"1.5vw"}}> Cart totals</p>
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
                            {bookTable()}
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