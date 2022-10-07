import React, {useState} from "react";
import './Home.css';
import RecommendTable from "./recommend/RecommendTable";
import {Button, Col, Row} from "antd";
import './recommend/RecommendTable.css'
import './Home.css'
import {CaretLeftOutlined, SearchOutlined} from "@ant-design/icons"
import axios from "axios";
import {useEffect} from "react";
import MyCard from "../general/MyCard";
import '../general/MyCart.css'
import CartBook from "./CartBook";
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
                        <Col span={24} offset={2}>
                            <Row>
                            {
                                bookList.map(item => (
                                    <Col span={5} style={{marginTop:"5%"}}>
                                        <CartBook item={item}/>

                                    </Col>
                                ))
                            }
                            </Row>
                        </Col>

                </Row>
            </div>
        </div>
    );
}