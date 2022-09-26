import React, {useState} from "react";
import {Divider, Row, Col, Button, Menu, Dropdown} from "antd";
import Cart from "../../users/cart/Cart";
import {CaretLeftOutlined,SettingOutlined, EditOutlined, EllipsisOutlined,CaretRightOutlined} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";
import { Avatar, Card } from 'antd';
import './RecommendTable.css'
import Icon from "antd/es/icon";


const menu = (
    <Menu
        items={[
            {
                key: '1',
                label: (
                    <a target="_blank" rel="noopener noreferrer" href="https://www.antgroup.com">
                        1st menu item
                    </a>
                ),
            },
            {
                key: '2',
                label: (
                    <a target="_blank" rel="noopener noreferrer" href="https://www.aliyun.com">
                        2nd menu item
                    </a>
                ),
            },
            {
                key: '3',
                label: (
                    <a target="_blank" rel="noopener noreferrer" href="https://www.luohanacademy.com">
                        3rd menu item
                    </a>
                ),
            },
        ]}
    />
);

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

    return(

        <div className={"recommend-table"} >

            <Row>
                <Col span={19} push={2} style={{fontSize:"20px"}}>
                    On Sale
                </Col>
                <Col span={5} pull={1}>
                    <Dropdown overlay={menu} placement="bottomRight" arrow>
                        <Button className={"viewAllButton"}>View all <CaretRightOutlined />
                        </Button>
                    </Dropdown>
                </Col>
            </Row>

            <Row className={"onSaleCardList"} >
                <Col span={2} className={"displayItemInColumn"}>
                    <Button className={"buttonArrowDesign"} icon={<CaretLeftOutlined className={"arrowPointerInList"}/>}>
                    </Button>
                </Col>

                <Col span={5} >
                    <div className="container">
                        <div className="card">
                            <div className="card__header">
                                <img src="https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0" alt="card__image"
                                     className="card__image" style={{ width:"300"}}/>
                            </div>
                            <div className="card__body">
                                <h4>Book Name</h4>
                                <p>Author name</p>
                            </div>
                            <div className="card footer" style={{background:"#D8D8D8"}}>
                                <div className="user">
                                    <div className="user__info">
                                        <h5>$ Price</h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </Col>
                <Col span={5} >
                    <div className="container">
                        <div className="card">
                            <div className="card__header">
                                <img src="https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0" alt="card__image"
                                     className="card__image" style={{ width:"300"}}/>
                            </div>
                            <div className="card__body">
                                <h4>Book Name</h4>
                                <p>Author name</p>
                            </div>
                            <div className="card footer" style={{background:"#D8D8D8"}}>
                                <div className="user">
                                    <div className="user__info">
                                        <h5>$ Price</h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </Col>

                <Col span={5} >
                    <div className="container">
                        <div className="card">
                            <div className="card__header">
                                <img src="https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0" alt="card__image"
                                     className="card__image" style={{ width:"300"}}/>
                            </div>
                            <div className="card__body">
                                <h4>Book Name</h4>
                                <p>Author name</p>
                            </div>
                            <div className="card footer" style={{background:"#D8D8D8"}}>
                                <div className="user">
                                    <div className="user__info">
                                        <h5>$ Price</h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </Col>

                <Col span={5} >
                    <div className="container">
                        <div className="card">
                            <div className="card__header">
                                <img src="https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0" alt="card__image"
                                     className="card__image" style={{ width:"300"}}/>
                            </div>
                            <div className="card__body">
                                <h4>Book Name</h4>
                                <p>Author name</p>
                            </div>
                            <div className="card footer" style={{background:"#D8D8D8"}}>
                                <div className="user">
                                    <div className="user__info">
                                        <h5>$ Price</h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </Col>

                <Col span={2} className={"displayItemInColumn"}>
                    <Button className={"buttonArrowDesign"} icon={<CaretRightOutlined className={"arrowPointerInList"}/>}>
                    {/*<CaretRightOutlined  className={"arrowPointerInList"}/>*/}
                    </Button>
                </Col>

            </Row>

        </div>

    );
}