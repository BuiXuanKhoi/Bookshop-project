import React, {useContext, useEffect, useState} from "react";
import {useParams} from 'react-router-dom';
import axios from "axios";
import {Button, Col, Comment, Empty, Input, Modal, Rate, Row} from "antd";
import '../shop/Shop.css';
import './BookDetail.css'
import FeedbackTable from "./FeedbackTable";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser} from "@fortawesome/free-solid-svg-icons";
import QuantityButton from "../general/QuantityButton";
import {getCookie} from "react-use-cookie";
import {useNavigate} from "react-router";
import {useCookies} from "react-cookie";
import './FeedBackTable.css'
import TextArea from "antd/es/input/TextArea";
import {MinusOutlined,PlusOutlined} from "@ant-design/icons";
import ReviewSubmit from "./ReviewSubmit";

import {SecurityContext} from "../../App";
export default function BookDetail(){

    const [cookies, setCookies, removeCookies] = useCookies(['book-token']);
    let authorize = getCookie('book-token');

    const [session, setSession] = useState(false);
    const [loginData, setLoginData] = useContext(SecurityContext);
    const [isOpenSession, setIsOpenSession] = useState(false);
    const [isOpenSuccess, setIsOpenSuccess] = useState(false);
    const [isUpdateSuccess, setIsUpdateSuccess] = useState(false);
    const [isExistCart, setIsExistCart] = useState(false);
    const navigate = useNavigate();

    const [cartId, setCartId] = useState(0);

    const config = {
        headers: {Authorization: `Bearer ` + loginData.token}
    };

    const bookId = useParams();
    const [quantity,setQuantity] = useState(0);
    const [bookFeedback, setBookFeedback] = useState([{
        userName : '',
        comment: '',
        ratingPoint : 0.0,
        createDay: ''
    }]);

    const [bookDetail, setBookDetail] = useState({
        bookName: '',
        description: '',
        bookPrice: 0.0,
        quantity : 0,
        authorName: '',
        categoryName : [],
        ratingPoint : 0.0,
        review : '',
        imageLink: '',
        createDay : '',
        updateDay: '',
        bookState: ''
    })




    const closeSucessModal = () => {
        setQuantity(0);
        setIsOpenSuccess(false);
    }

    const openSuccessModal = () => {
        setIsOpenSuccess(true);
    }

    const openUpdateSuccessModal = () => {
        setIsUpdateSuccess(true);

    }

    const closeUpdateSuccessModal = () => {
        setIsUpdateSuccess(false);
    }

    const openWarningModal = () => {
        setIsExistCart(true);
    }

    const closeWarningModal = () => {
        setIsExistCart(false);
    }

    const handleAddQuantity = () => {
        setQuantity(quantity + 1);
    }

    const handleLessQuantity = () => {
        if(quantity > 0 ){
            setQuantity(quantity - 1);
        }
    }

    const [isFeedback, setIsFeedback] = useState(false);


    const getCarItem = () => {

        axios.get('https://ecommerce-web0903.herokuapp.com/api/books/' + bookId.id +  '/cart', config )
            .then((res) => {
                setCartId(res.data.cartItemsID) ;
                openWarningModal();
            }).catch((err) => {

                console.log(err)

                if(err.response.data.statusCode === 404){
                    addBookCart();
                }
                console.log(err);
        })

    }


    const updateCart = () => {
        console.log(cartId)
        axios.put('https://ecommerce-web0903.herokuapp.com/api/carts/' + cartId + '?quantity=' + quantity, null,
            config)
            .then((res) => {
                openUpdateSuccessModal();
            }).catch((err) =>

        {
            console.log(err)
            if(err.response.data.statusCode === 401)
            {
                if(err.response.data.message === 'Expired JWT Token')
                {
                    setIsOpenSession(true);
                }else if (err.response.data.message === 'Cannot determine error'){
                    navigate('/login');
                }
            }
        })
    }

    const addBookCart = () => {

        axios.post('https://ecommerce-web0903.herokuapp.com/api/carts',{
            quantity : quantity,
            bookId : bookId.id
        }, config)
            .then((res) => {
                openSuccessModal();
            }).catch((err) =>
        {
            console.log(err);
                if(err.response.data.statusCode === 401)
                {
                    if(err.response.data.message === 'Expired JWT Token')
                    {
                        setIsOpenSession(true);
                    }else if (err.response.data.message === 'Cannot determine error'){
                        navigate('/login');
                    }
                }
        })
    }

    const getFeedback = () => {
        axios.get('https://ecommerce-web0903.herokuapp.com/api/books/' + bookId.id + '/feedbacks')
            .then((res) => {
                setBookFeedback(res.data)
                console.log(bookFeedback);
            }).catch((err) => console.log(err))
    }

    const getBookDetail = () => {
        axios.get('https://ecommerce-web0903.herokuapp.com/api/books/' + bookId.id)
            .then((res) => {
                setBookDetail(res.data);
                console.log(res.data);
                console.log(bookDetail);
            }).catch((err) => setIsFeedback(false))
    }

    const closeSession = () => {
        removeCookies('book-token');
        navigate('/login');
    }


    useEffect(() => {
        getBookDetail();
        getFeedback();
    },[])

    const bookDescription = () =>{
        return(
            <Row className={"customerReviewList"} >
                <Col span={24}>
                    <Row>
                        <Col span ={8}>
                            <Row >
                                <img src={bookDetail.imageLink}/>
                            </Row>
                            <Row >
                                <Col span={24}>
                                    <div style={{display:"flex",justifyContent:"right"}}>
                                        <p className={"wordDesign"}> By (author)</p>
                                        <p style={{marginLeft:"2%",fontWeight:"bolder",color:"#576F72"}}> {bookDetail.authorName} </p>
                                    </div>
                                </Col>
                            </Row>
                        </Col>
                        {/*--------------------------------------------------------------------------------*/}
                        <Col span={14} offset={1} pull={0.5} style={{color:"#7F8487"}}>
                            <Row>
                                <p style={{fontSize:"2vw", fontWeight:"bolder",color:"black"}}> {bookDetail.bookName}</p>
                            </Row>
                            {/*--------------------------------------------------------------------------------*/}
                            <Row>
                                <div>
                                    <p className={"description"}>Book description</p>
                                    <p className={"positionForChar"} style={{marginTop:"5%"}}>{bookDetail.description} </p>
                                </div>
                            </Row>
                        </Col>
                    </Row>

                </Col>
            </Row>
        );
    }

    return(
    <>
        <Modal
            title="Update Success"
            centered
            open={isUpdateSuccess}
            onOk={() => closeUpdateSuccessModal()}
            onCancel={() => closeUpdateSuccessModal()}
            width={400}
        >
            <span>Update Cart Item Success !!!</span>
        </Modal>
        <Modal
            title="Warning"
            centered
            open={isExistCart}
            onOk={() => {
                closeWarningModal();
                updateCart()
            }}
            onCancel={() => closeWarningModal()}
            width={400}
        >
            <span>This Book Is Already In Your Cart. Wanna Buy More ?</span>
        </Modal>
        <Modal
                title="Authentication Error"
                centered
                open={isOpenSession}
                onOk={() => {
                    setIsOpenSession(false);
                }}
                onCancel={() => {
                    setIsOpenSession(false);
                }}
                afterClose={closeSession}
                width={400}
        >
                <p>Your Session is expired. Please Login again !!!</p>
        </Modal>
        <Modal
                title="Buying Success !!!"
                centered
                open={isOpenSuccess}
                onOk={() => closeSucessModal()}
                onCancel={() => closeSucessModal()}
                width={400}
        >
                <span>Add {bookDetail.bookName} To Cart Success. </span>
        </Modal>

            <Row style={{paddingTop:"10%",fontFamily:"Arial"}}>

                {bookDescription()}
                <Row className={"customerReviewPost"}>
                    <Col span={24}>
                        <Row style={{background:"#F6F6F6"}}>
                            <Col span={20} offset={2}>
                                <p className="positionForChar" style={{fontSize:"2vw",fontWeight:"bolder"}}> ${bookDetail.bookPrice }</p>
                            </Col>
                        </Row>
                        {/*--------------------------------------------------------------------------------*/}
                        <Row>
                            <Col span={24} style={{borderStyle:"ridge",borderColor:"#F6F6F6"}}>

                            </Col>
                        </Row>

                        {/*--------------------------------------------------------------------------------*/}
                        <Row style={{marginTop:"20%"}}>
                            <Col span={20} offset={2}>
                                <p className="positionForChar" style={{fontSize:"1.2vw"}}>Quanity</p>
                            </Col>
                        </Row>
                        {/*--------------------------------------------------------------------------------*/}
                        <Row>
                            <Col span={20} offset={2}>
                                <Row style={{background:"#CFD2CF"}}>
                                    <Col span={10} style={{borderColor:"#CFD2CF",alignItems:"center",display:"flex"}}>
                                        <Button style={{background:"#CFD2CF"}} onClick={handleLessQuantity} icon={<MinusOutlined />}></Button>
                                    </Col>
                                    {/*--------------------------------------------------------------------------------*/}
                                    <Col span={4} style={{borderColor:"#CFD2CF",justifyContent:"center",display:"flex",textAlign:"center"}}>
                                        <p style={{marginTop:"5%",marginBottom:"5%",fontWeight:"bolder",fontSize:"1.5vw"}}>{quantity}</p>
                                    </Col>
                                    {/*--------------------------------------------------------------------------------*/}
                                    <Col span={10} style={{borderColor:"#CFD2CF",justifyContent:"right",alignItems:"center",display:"flex"}}>
                                        <Button style={{background:"#CFD2CF"}} onClick={handleAddQuantity} icon={<PlusOutlined />}></Button>
                                    </Col>
                                </Row>

                            </Col>
                        </Row>
                        {/*--------------------------------------------------------------------------------*/}
                        <Row style={{marginTop:"10%",marginBottom:"15%"}}>
                            <Col span={20} offset={2}>
                                <Button style={{background:"#CFD2CF",paddingBottom:"10%",width:"100%"}} onClick={getCarItem}>
                                    <p className={"positionForChar"} style={{fontWeight:"bolder",fontSize:"1.5vw",marginBottom:"50%"}}>
                                        Add to cart
                                    </p>
                                </Button>
                            </Col>
                        </Row>
                    </Col>
                </Row>

            </Row>




            <Row style={{marginTop:"3%"}}>
                <FeedbackTable bookID={bookId.id} config={config}/>
                <ReviewSubmit bookID={bookId.id} config={config}/>
            </Row>
      </>
    );
}
