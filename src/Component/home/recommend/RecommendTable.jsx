import React, {useState} from "react";
import {Divider, Row, Col, Button, Menu, Dropdown} from "antd";
import Cart from "../../users/cart/Cart";
import {CaretLeftOutlined,SettingOutlined, EditOutlined, EllipsisOutlined,CaretRightOutlined} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";
import { Avatar, Card } from 'antd';
import './RecommendTable.css'
import Icon from "antd/es/icon";
import MyCard from "../../general/MyCard";


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

const books = [
    {
        bookId: 9,
        url: 'https://cdn-amz.woka.io/images/I/71dUEXcxJzL.jpg',
        authorName : 'Hemingway',
        bookName : 'The Man And The Sea',
        bookPrice: 15.8
    },
    {
        bookId: 10,
        url:'https://static.8cache.com/cover/eJzLyTDWz8nJLtH1KvFKTk0pCzeMCjeJCHbyMggxLg7ziSqo8CqrDEoPKUnKTC_JzwoKNHHJMLQ0qQjwqzRKz_coTPQ1DPDxzsxzqUzMMPfOTzQtcM_wLrctNgAAYsoejA==/sherlock-holmes-toan-tap.jpg',
        authorName: 'Conan Doyle',
        bookName: 'Sherlock Holmes',
        bookPrice: 30.2
    },
    {
        bookId: 11,
        url: 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1466912744l/30752004._SY475_.jpg',
        authorName: 'Nguyen Nhat Anh',
        bookName: 'Mat Biec',
        bookPrice: 12.6
    },
    {
        bookId: 12,
        url:'https://upload.wikimedia.org/wikipedia/vi/b/b7/Doraemon1.jpg',
        authorName: 'Fujiko F Fujio',
        bookName:'Doraemon',
        bookPrice: 14.4
    }
]

export default function RecommendTable(){



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

                {
                    books.map(item =>(
                        <Col span={5}>
                            <MyCard item={item} url={item.url}/>
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