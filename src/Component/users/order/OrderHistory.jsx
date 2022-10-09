import React, {useEffect, useState} from "react";
import {Col, Row, Steps} from "antd";
import {CheckOutlined, FolderOutlined, ShoppingCartOutlined,LoadingOutlined, SmileOutlined, UserOutlined} from "@ant-design/icons";
import OrderItem from "./OrderItem";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCreditCard} from "@fortawesome/free-solid-svg-icons";

export default function OrderHistory ({element}) {
    const {Step} = Steps;
    const [totalCost, setTotalCost] = useState(0);
    const [stateOfOrder,setStateOfOrder] = useState([]);
    const [icon,setIcon] = useState([]);

    const handleCalculateTotalCost = () =>{
        let newList=element.orderItems.map(item=>item.quantity*item.price);
        setTotalCost(newList.reduce((a,b)=>a+b,0))
        setState(element.state);
    }

    const setState = (state) =>{
        if(state=="PREPARED"){
            setStateOfOrder(["process","wait","wait","wait","wait"]);
            setIcon([<LoadingOutlined/>,<FolderOutlined/>,<ShoppingCartOutlined/>,<SmileOutlined/>,<CheckOutlined/>]);
        }
        else if(state=="PACKAGED"){
            setStateOfOrder(["finish","process","wait","wait","wait"]);
            setIcon([<UserOutlined/>,<LoadingOutlined/>,<ShoppingCartOutlined/>,<SmileOutlined/>,<CheckOutlined/>])
        }
        else if(state=="DELIVERED"){
            setStateOfOrder(["finish","finish","process","wait","wait"])
            setIcon([<UserOutlined/>,<FolderOutlined/>,<LoadingOutlined/>,<SmileOutlined/>,<CheckOutlined/>])
        }
        else if(state=="RECEIVED"){
            setStateOfOrder(["finish","finish","finish","process","wait"])
            setIcon([<UserOutlined/>,<FolderOutlined/>,<ShoppingCartOutlined/>,<LoadingOutlined/>,<CheckOutlined/>])
        }
        else if(state=="COMPLETED"){
            setStateOfOrder(["finish","finish","finish","finish","finish"])
            setIcon([<UserOutlined/>,<FolderOutlined/>,<ShoppingCartOutlined/>,<SmileOutlined/>,<CheckOutlined/>])
        }
    }

    useEffect(()=>{
        handleCalculateTotalCost();
    },[])

    return (
        <Row key={element.orderId} style={{background: "white", display: "flex", justifyContent: "center", marginTop: "4%"}}>
            <Col span={23}>

                {/*--------------------------------------------------------------------------------*/}
                <Row className={"row-container"} style={{marginBottom:"0"}}>
                    <div style={{width: "100%", marginTop: "3%"}}>
                        <Steps>
                            <Step status={stateOfOrder[0]} title="Prepared" icon={icon[0]}/>
                            <Step status={stateOfOrder[1]} title="Packaged" icon={icon[1]}/>
                            <Step status={stateOfOrder[2]} title="Delivered" icon={icon[2]}/>
                            <Step status={stateOfOrder[3]} title="Received" icon={icon[3]}/>
                            <Step status={stateOfOrder[4]} title="Completed" icon={icon[4]}/>
                        </Steps>

                    </div>
                </Row>

                <Row>
                    <p className={"space-line"}/>
                </Row>
                {/*--------------------------------------------------------------------------------*/}
                {element.orderItems.map((item) =>
                    <div key={item.orderItemId}>

                        <Row className={"row-container"} style={{borderBlock: "1rem"}}>
                            <OrderItem bookName={item.bookName} imageLink={item.imageLink} price={item.price}
                                       quantity={item.quantity} />

                        </Row>
                        <Row>
                            <p className={"space-line"}/>
                        </Row>
                    </div>
                )}

                {/*--------------------------------------------------------------------------------*/}
                <Row style={{justifyContent: "right",marginBottom: "5%"}}>
                    <div style={{display: "flex",textAlign:"left"}}>
                        <p style={{padding: "1px",fontSize:"2rem",fontWeight:"bolder"}}>Total cost: ${Math.round(totalCost*100)/100}</p>
                    </div>
                </Row>
            </Col>
        </Row>
    );
}