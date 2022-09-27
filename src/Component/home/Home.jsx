import React, {useState} from "react";
import './Home.css';
import RecommendTable from "./recommend/RecommendTable";
import {Button, Col, Row} from "antd";
import './recommend/RecommendTable.css'
import './Home.css'
import {CaretLeftOutlined, SearchOutlined} from "@ant-design/icons"
import axios from "axios";
import {useEffect} from "react";
export default function Home(){

    const [changeStateButton, setChangeStateButton] = useState(false);
    const [bookList, setBookList] = useState([])


    const changeStyleOfRecommendButton = (event) =>{
        if (changeStateButton){
            setChangeStateButton(false);
        }
        else{setChangeStateButton(true);}
        getRecommendBookList()
    }
    const getRecommendBookList = () => {
        axios.get("https://ecommerce-web0903.herokuapp.com/api/books/recommend")
            .then((res)=>{
                setBookList(res.data);
            })
    }

    const changeStyleOfPopularButton = (event) =>{
        if (changeStateButton){
            setChangeStateButton(false);
        }
        else{setChangeStateButton(true);}
        getPopularBookList()
    }
    const getPopularBookList = () => {
        axios.get("https://ecommerce-web0903.herokuapp.com/api/books/popular")
            .then((res) => {
                setBookList(res.data)
            })
    }

    useEffect(() =>{
        getPopularBookList();
    },[])
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
                            changeStateButton ? <Button type="primary" onClick={changeStyleOfRecommendButton} className={"buttonChangeState"} style={{transition:0.1,background:"gray",color:"white"}}>Recommend</Button> :
                                <Button type="primary" onClick={changeStyleOfRecommendButton} className={"buttonChangeState"} >Recommend</Button>
                        }

                    </Col>
                    <Col span={12} >
                        {
                            changeStateButton ? <Button type="primary" onClick={changeStyleOfPopularButton} className={"buttonChangeState"}>Popular</Button> :
                                <Button type="primary" onClick={changeStyleOfPopularButton} className={"buttonChangeState"} style={{transition:0.1,background:"gray",color:"white"}}>Popular</Button>
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

                </Row>
            </div>
        </div>
    );
}