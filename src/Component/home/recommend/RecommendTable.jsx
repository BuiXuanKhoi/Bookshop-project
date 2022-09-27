import React, {useState} from "react";
import {Divider, Row, Col, Button, Menu, Dropdown} from "antd";
import Cart from "../../users/cart/Cart";
import {CaretLeftOutlined,SettingOutlined, EditOutlined, EllipsisOutlined,CaretRightOutlined} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";
import { Avatar, Card } from 'antd';
import './RecommendTable.css'
import Icon from "antd/es/icon";
import {Link} from "react-router-dom";




export default function RecommendTable(){
    const [bookCard ,setbookCard]= useState({
        url: '',
        authorName:'',
        bookName:'',
        price:'',
    });

    const bookCardList = (bookCard) => {
        return(

            <div className="container">
                <div className="card">
                    <div className="card__header">
                        <img src={bookCard.url} alt="card__image"
                             className="card__image" style={{ width:"300"}}/>
                    </div>
                    <div className="card__body">
                        <h4>{bookCard.bookName}</h4>
                        <p>{bookCard.authorName}</p>
                    </div>
                    <div className="card footer" style={{background:"#D8D8D8"}}>
                        <div className="user">
                            <div className="user__info">
                                <h5>$ {bookCard.price}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
    const bookList = [{
        url:'https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0',
        bookName: 'The old man and the sea',
        authorName: 'Hemingway',
        price: 18.5,
        },
        {
            url:'https://cdn0.fahasa.com/media/catalog/product/t/o/to_kill_a_mockingbird_1_2019_01_18_16_59_17.jpg',
            bookName:'To Kill A Mockingbird',
            authorName:'Harpen Lee',
            price: 6.30,

        },
        {
            url: 'https://images.thuvienpdf.com/dQtVi1.webp',
            bookName: 'The hollowing hill',
            authorName: 'Emily Bronte',
            price: 14.7,
        },
        {
            url: 'https://upload.wikimedia.org/wikipedia/commons/a/a0/The_Great_Gatsby_cover_1925_%281%29.jpg',
            bookName: 'The great Gatsby',
            authorName: 'Fitzergald',
            price: 21.9,
        }
    ]
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
                    <Button className={"buttonArrowDesign"} icon={<CaretLeftOutlined className={"arrowPointerInList"}/>}>
                    </Button>
                </Col>
                {
                    bookList.map(item =>(
                        <Col span={5} >
                            <div className="container">
                                <div className="card">
                                    <div className="card__header">
                                        <img src={item.url} alt="card__image"
                                             className="card__image" style={{ width:"300"}}/>
                                    </div>
                                    <div className="card__body">
                                        <h4>{item.bookName}</h4>
                                        <p>{item.authorName}</p>
                                    </div>
                                    <div className="card footer" style={{background:"#D8D8D8"}}>
                                        <div className="user">
                                            <div className="user__info">
                                                <h5>${item.price}</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Col>
                    ))
                }

                <Col span={2} className={"displayItemInColumn"}>
                    <Button className={"buttonArrowDesign"} icon={<CaretRightOutlined className={"arrowPointerInList"}/>}>
                    {/*<CaretRightOutlined  className={"arrowPointerInList"}/>*/}
                    </Button>
                </Col>

            </Row>

        </div>

    );
}