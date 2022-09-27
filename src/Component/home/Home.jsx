import React, {useEffect, useRef, useState} from "react";
import './Home.css';
import RecommendTable from "./recommend/RecommendTable";
import {Button, Col, Row} from "antd";
import './recommend/RecommendTable.css'
import './Home.css'
import {CaretLeftOutlined, SearchOutlined} from "@ant-design/icons"
export default function Home(){

    const [changeStateButton, setChangeStateButton] = useState(false);

    const bookList = [{
        url:'https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0',
        bookName: 'The old man and the sea',
        authorName: 'Ernest Hemingway',
        price: 18.5,
    },
        {
            url:'https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0',
            bookName: 'The old man and the sea',
            authorName: 'Ernest Hemingway',
            price: 18.5,

        },
        {
            url:'https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0',
            bookName: 'The old man and the sea',
            authorName: 'Ernest Hemingway',
            price: 18.5,
        },
        {
            url:'https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0',
            bookName: 'The old man and the sea',
            authorName: 'Ernest Hemingway',
            price: 18.5,
        },

        {
            url:'https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0',
            bookName: 'The old man and the sea',
            authorName: 'Ernest Hemingway',
            price: 18.5,
        },

        {
            url:'https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0',
            bookName: 'The old man and the sea',
            authorName: 'Ernest Hemingway',
            price: 18.5,
        },

        {
            url:'https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0',
            bookName: 'The old man and the sea',
            authorName: 'Ernest Hemingway',
            price: 18.5,
        },

        {
            url:'https://th.bing.com/th/id/R.f17b9a7342277b1f5fb7986e114d89dc?rik=Glb%2bxt2j4opMtg&pid=ImgRaw&r=0',
            bookName: 'The old man and the sea',
            authorName: 'Ernest Hemingway',
            price: 18.5,
        },
    ]

    const changeStyleOfButton = () =>{
        if (changeStateButton){
            setChangeStateButton(false);
        }
        else{setChangeStateButton(true);}
    }


    return(

        <div >

            <RecommendTable/>
            <div className={"recommend-table"}>
                <Row className={"feature"}>
                    <Col span={23} style={{textAlign:"center"}}>
                        Feature Books
                    </Col>
                </Row>

                <Row>
                    <Col span={12} style={{display:"flex",justifyContent:"flex-end"}} >
                        {
                            changeStateButton ? <Button type="primary" onClick={changeStyleOfButton} className={"buttonChangeState"} style={{transition:0.1,background:"gray",color:"white"}}>Recommend</Button> :
                                <Button type="primary" onClick={changeStyleOfButton} className={"buttonChangeState"} >Recommend</Button>
                        }

                    </Col>
                    <Col span={12} >
                        {
                            changeStateButton ? <Button type="primary" onClick={changeStyleOfButton} className={"buttonChangeState"}>Popular</Button> :
                                <Button type="primary" onClick={changeStyleOfButton} className={"buttonChangeState"} style={{transition:0.1,background:"gray",color:"white"}}>Popular</Button>
                        }

                    </Col>
                </Row>

                <Row className={"onSaleCardList"} style={{height:"65rem"}}>

                        {
                            bookList.map(item => (
                                <Col span={6} >
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

                </Row>
            </div>
        </div>
    );
}