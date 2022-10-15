import React, {useContext, useEffect, useRef, useState} from "react";
import {useNavigate} from "react-router";
import {Row, Col, Form, Button, Input, Pagination} from "antd";
import '../book/table/ManageBookTable.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {ArrowRightOutlined} from "@ant-design/icons";
import axios from "axios";
import {SecurityContext} from "../../../../App";
import OrderListUser from "./OrderListUser";
import {getCookie} from "react-use-cookie";

export default function ManageOrder () {

    const [orderList,setOrderList] = useState([]);
    const config = {
        headers: {Authorization: 'Bearer ' + JSON.parse(getCookie('book-token')).token}
    }
    const [form]  = Form.useForm();

    const listOfOrderState = [
        "PREPARED","PACKAGED","DELIVERED","RECEIVED","COMPLETED"
    ]

    const flagChange = useRef(false);
    const [currentPage,setCurrentPage] = useState(0);
    const [pageSize, setPageSize] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [userName,setUserName] = useState('');

    const getOrderList = () =>{
        axios.get("https://ecommerce-web0903.herokuapp.com/api/orders/manage"+"?page="+currentPage+"&search="+userName,config)
            .then((res) =>{
                setOrderList(res.data.listManageOrder);
                setPageSize(res.data.pageSize);
                setCurrentPage(res.data.currentPage);
                setTotalElements(res.data.totalElements);
            })
            .catch((error) =>{
                console.log(error);
            })
    }

    useEffect(()=>{
        getOrderList();
    },[currentPage])

    useEffect(()=>{
        if(flagChange.current){
            getOrderList();
        }
        else{
            flagChange.current = true;
        }
    },[userName])

    const handleOnChangePages = (res) =>{
        setCurrentPage(res-1)
    }

    const handleSubmitSearch = (values) =>{
        setUserName(values.userName)
    }

    return (
        <div style={{paddingTop:"10%"}}>
            <Row>
                <Col span={23} offset={1}>
                    {/*--------------------------------------------------------------------------------*/}
                    <Row style={{fontStyle:"Palatino Linotype",fontSize:"2rem",fontWeight:"bolder"}}>
                        <p> Orders</p>
                    </Row>
                    {/*--------------------------------------------------------------------------------*/}
                    <Row >
                        <Col span={24}>
                            <Form
                                title={"Search"}
                                onFinish={handleSubmitSearch}
                                form={form}
                            >
                                <Row style={{justifyContent:"right"}}>
                                    <Col span={7}>
                                        <Form.Item name={"userName"}>
                                            <Input placeholder="Input username"/>
                                        </Form.Item>
                                    </Col>

                                    <Col style={{marginLeft:"1%",marginRight:"2%"}} span={2}>
                                        <Form.Item>
                                            <Button htmlType={"submit"} style={{background:"#D8D8D8"}}>
                                                    Search Orders
                                            </Button>
                                        </Form.Item>
                                    </Col>
                                </Row>

                            </Form>
                        </Col>
                    </Row>
                    {/*--------------------------------------------------------------------------------*/}
                    <Row>
                        <Col span={24} >
                            <div >
                                <table className={"book-table-container"} style={{marginTop:"0"}}>
                                    <thead className="book-table-column">
                                        <th className="book-table-column-container">Order ID</th>
                                        <th className="book-table-column-container">Username</th>
                                        <th className="book-table-column-container">Total Price</th>
                                        <th className="book-table-column-container">Order Items</th>
                                        <th className="book-table-column-container">Updated Date</th>
                                        <th className="book-table-column-container">Updated By</th>
                                        <th className="book-table-column-container">Order State</th>
                                    </thead>
                                    <tbody>
                                    {orderList.map((item)=>
                                        <OrderListUser item={item} listOfOrderState={listOfOrderState} config={config}/>
                                    )}
                                    </tbody>
                                </table>
                                <Pagination style={{marginTop:"3%",marginLeft:"40%"}} total={totalElements} current={currentPage+1} pageSize={pageSize} onChange={handleOnChangePages}/>
                            </div>
                        </Col>
                    </Row>
                </Col>
            </Row>
        </div>


    );

}