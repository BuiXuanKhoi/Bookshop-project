import React, {useEffect, useRef, useState} from "react";
import {Button, Col, Modal, Row} from "antd";
import {ArrowRightOutlined} from "@ant-design/icons";
import './ManageBookTable.css'
import axios from "axios";
export default function OrderListUser ({item,listOfOrderState,config}) {

    const [changeOrderState,setChangeOrderState] = useState(item.orderState);
    const flagChange = useRef(false);
    const flagDisable = useRef(true)

    const handleChangeOrderState = () =>{
        for (const element of listOfOrderState ){
            if(changeOrderState === element ){
                let i = listOfOrderState.indexOf(element);
                setChangeOrderState(listOfOrderState[i+1]);
            }
        }
    }

    useEffect(()=>{
        if(flagChange.current){
            sendAPI();
        }
        else{
            flagChange.current= true;
        }
    },[changeOrderState])

    const sendAPI = () =>{
        axios.put("https://ecommerce-web0903.herokuapp.com/api/orders/"+item.orderId,null,config)
            .then(()=>{

            })
            .catch((error) =>{
                console.log(error);
                handleFailUpdated(error);
            })
    }
    const handleFailUpdated = (error) => {
        Modal.error({
            title:"Error",
            content:error.response.data.message,
        })
    }
    return (
        <>
            <tr className="book-table-row-container"  >
                <td>{item.orderId}</td>
                <td>{item.userName}</td>
                <td>$ {item.totalPrice}</td>
                <td>
                    <Col span={24}>
                        {item.orderItems.map((element)=>
                            <Row className={"row-container"}>
                                <div className={"flex-box"} >
                                    <p className={"font-wrapper"} > {element.bookName}</p>
                                    <p className={"font-wrapper"} style={{marginLeft:"2%"}}> x {element.quantity}</p>
                                </div>
                            </Row>

                        )}
                    </Col>
                </td>
                <td>{item.updateDate}</td>
                <td>{item.updateBy}</td>
                <td>{changeOrderState}</td>
                <td>
                    <Button  onClick={handleChangeOrderState} className="btn-style" size={"large"} icon={<ArrowRightOutlined className={"button"}/>}>
                    </Button>
                </td>
            </tr>
        </>
    );
}