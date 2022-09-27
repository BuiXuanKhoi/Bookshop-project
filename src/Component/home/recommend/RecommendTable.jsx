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

let number = 0;

export default function RecommendTable(){
    const [bookListOnSales ,setBookListOnSales]= useState([]);
    const [saveList, setSaveList] = useState([]);
    const [position,setPosition]=useState(0);
    useEffect(()=>{
        axios.get("https://ecommerce-web0903.herokuapp.com/api/books/onsale")
                .then((res)=>{
                    localStorage.setItem("bookListOnSales",JSON.stringify(res.data))
                })
    },[]);

    useEffect(()=>{
            const saved = localStorage.getItem("bookListOnSales");
            const initialValue = JSON.parse(saved);
            setBookListOnSales(initialValue.slice(0,4));
            setSaveList(initialValue)
    },[])
    useEffect(()=>{
        console.log("Yes")
        console.log(position)
        }
    ,[position])
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
                        <Col span={5} >
                            <div className="container">
                                <div className="card">
                                    <div className="card__header">
                                        <img src="https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0" alt="card__image"
                                             className="card__image" style={{ width:"300"}}/>
                                    </div>
                                    <div className="card__body">
                                        <h4>{item.bookName}</h4>
                                        <p>{item.authorName}</p>
                                    </div>
                                    <div className="card footer" style={{background:"#D8D8D8"}}>
                                        <div className="user">
                                            <div className="user__info">
                                                <h5>${item.bookPrice}</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Col>
                    ))
                }

                <Col span={2} className={"displayItemInColumn"}>
                    <Button className={"buttonArrowDesign"} onClick={handleOnClickOfRightButton} icon={<CaretRightOutlined className={"arrowPointerInList"}/>}>
                    {/*<CaretRightOutlined  className={"arrowPointerInList"}/>*/}
                    </Button>
                </Col>

            </Row>

        </div>

    );
}