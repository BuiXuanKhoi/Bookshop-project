import React, {useEffect, useRef, useState} from "react";
import {Button, Col, Modal, Row} from "antd";
import {ArrowRightOutlined} from "@ant-design/icons";
import '../book/table/ManageBookTable.css'
import axios from "axios";

export default function OrderListUser ({item,listOfOrderState,config}) {

    const [changeOrderState,setChangeOrderState] = useState(item.orderState);
    const flagChange = useRef(false);
    const [disableButton, setDisableButton] = useState(false);

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
            if(changeOrderState == listOfOrderState[listOfOrderState.length-1]){
                setDisableButton(true)
            }
        }
    },[changeOrderState])

    const sendAPI = () =>{
        axios.put("https://ecommerce-web0903.herokuapp.com/api/orders/"+item.orderId,null,config)
            .then(()=>{
                if(changeOrderState == listOfOrderState[listOfOrderState.length-1]){
                    setDisableButton(true)
                }
                else{
                    setDisableButton(false)
                }
            })
            .catch((error) =>{
                console.log(error);
            })
    }

    return (
        <>
            <tr className="book-table-row-container" style={{borderTopStyle:"ridge"}}>
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
                    <Button  disabled={disableButton} onClick={handleChangeOrderState} className="btn-style" size={"large"} icon={<ArrowRightOutlined className={"button"}/>}>
                    </Button>
                </td>
            </tr>
        </>
    );
}