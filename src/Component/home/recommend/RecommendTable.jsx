import React, {useEffect, useState} from "react";
import {Divider, Row, Col, Button, Menu, Dropdown} from "antd";
import Cart from "../../users/cart/Cart";
import {CaretLeftOutlined,SettingOutlined, EditOutlined, EllipsisOutlined,CaretRightOutlined} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";
import { Avatar, Card } from 'antd';
import './RecommendTable.css'
import Icon from "antd/es/icon";
import {Link} from "react-router-dom";
import axios from "axios";
import {set} from "react-hook-form";
import {useNavigate} from "react-router";
import CartBook from "../CartBook";

let number = 0;

export default function RecommendTable(){
    const [bookListOnSales ,setBookListOnSales]= useState([]);
    const [saveList, setSaveList] = useState([]);
    const [position,setPosition]=useState(0);
    const [isExistData, setIsExistData] = useState(false);

    useEffect(()=>{
        axios.get("https://ecommerce-web0903.herokuapp.com/api/books/onsale")
                .then((res)=>{
                    localStorage.setItem("bookListOnSales",JSON.stringify(res.data))
                    setIsExistData(true);
                })
            .catch((error) =>{
                console.log(error);
            })
    },[]);

    useEffect(()=>{
        const saved = localStorage.getItem("bookListOnSales");
        const initialValue = JSON.parse(saved);
        setBookListOnSales(initialValue.slice(0,4));
        setSaveList(initialValue)
    },[isExistData])

    const handleOnClickOfRightButton = () => {
        if(number<6){
            number+=1;
        }
        setPosition(number)
        setBookListOnSales(saveList.slice(number,number+4))
    }

    const handleOnClickOfLeftButton = () => {
        if(number>0){
            number -=1;
        }
        setPosition(number)
        setBookListOnSales(saveList.slice(number,number+4))
    }
    return(

        <div className={"recommend-table"} >

            <Row>
                <Col span={19} push={2} style={{fontSize:"20px"}}>
                    On Sale
                </Col>
                <Col span={5} pull={1}>

                    <Button className={"viewAllButton"}><a href={"/shop"}>View all <CaretRightOutlined /></a>
                    </Button>

                </Col>
            </Row>

            <Row className={"onSaleCardList"} >
                <Col span={2} className={"displayItemInColumn"}>
                    <Button onClick={handleOnClickOfLeftButton} className={"buttonArrowDesign"} icon={<CaretLeftOutlined className={"arrowPointerInList"}/>}>
                    </Button>
                </Col>

                {
                    bookListOnSales.map(item =>(
                        <Col style={{marginTop:"3%"}} span={5} >
                            <CartBook item={item}/>
                        </Col>
                    ))
                }

                <Col span={2} className={"displayItemInColumn"}>
                    <Button className={"buttonArrowDesign"} onClick={handleOnClickOfRightButton} icon={<CaretRightOutlined className={"arrowPointerInList"}/>}>
                    </Button>
                </Col>
            </Row>
        </div>

    );
}