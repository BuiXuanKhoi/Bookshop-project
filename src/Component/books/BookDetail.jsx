import React, {useEffect, useState} from "react";
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

export default function BookDetail(){

    const [cookies, setCookies, removeCookies] = useCookies(['book-token']);
    let authorize = getCookie('book-token');

    const [session, setSession] = useState(false);
    const [isOpenSession, setIsOpenSession] = useState(false);
    const [isCloseSession, setIsCloseSession] = useState(false);
    const [isOpenSuccess, setIsOpenSuccess] = useState(false);
    const navigate = useNavigate();

    let config = '';

    const closeSucessModal = () => {
        setQuantity(0);
        setIsOpenSuccess(false);
    }

    const openSuccessModal = () => {
        setIsOpenSuccess(true);
    }

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


    const handleAddQuantity = () => {
        setQuantity(quantity + 1);
    }

    const handleLessQuantity = () => {
        if(quantity > 0 ){
            setQuantity(quantity - 1);
        }
    }



    const [isFeedback, setIsFeedback] = useState(false);

    const addBookCart = () => {

        if(authorize !== ''){
            config = {
                headers : {Authorization: `Bearer ` + JSON.parse(authorize).token }
            }
        }

        axios.post('https://ecommerce-web0903.herokuapp.com/api/carts',{
            quantity : quantity,
            bookId : bookId.id
        }, config)
            .then((res) => {
                openSuccessModal();
            }).catch((err) =>
        {
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

    const handleAddCart = () => {
        addBookCart();
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
                                <img src='https://www.lesmurray.org/wp-content/uploads/2020/12/war-and-peace.png'/>
                            </Row>
                            <Row >
                                <Col span={17}>
                                    <p className={"wordDesign"}> By (author)</p>
                                </Col>
                                <Col span={7}>
                                    <p style={{fontWeight:"bolder",color:"#576F72"}}> Leo Toystol </p>
                                </Col>
                            </Row>
                        </Col>
                        {/*--------------------------------------------------------------------------------*/}
                        <Col span={14} offset={1} pull={0.5} style={{color:"#7F8487"}}>
                            <Row>
                                <p style={{fontSize:"2vw", fontWeight:"bolder",color:"black"}}> Book title</p>
                            </Row>
                            {/*--------------------------------------------------------------------------------*/}
                            <Row>
                                <p className={"positionForChar"}>Book description</p>
                                <p className={"positionForChar"}>This was a VERY long book, but I would say it was mostly worth it. I Just want to put it out there that Tolstoy is simply brilliant! The chief reason this book is long is probably because of the careful detail he put into it- from every single character to the historical events.However,after reading literally hundreds of chapters and still not being done I began to feel like some of the detail was really not necessary(mostly the description of war strategies and general military things)but given the title of the book I'd say it was warranted. </p>
                            </Row>
                            {/*--------------------------------------------------------------------------------*/}
                            <Row style={{paddingBlock:"4%"}}>
                                <div>
                                    <p className={"positionForChar"}>"The multi-million copy bestseller"</p>
                                    <p className={"positionForChar"}>Soon to be a major film</p>
                                    <p className={"positionForChar"}>A Number One New York Times Bestseller</p>
                                </div>
                            </Row>
                            {/*--------------------------------------------------------------------------------*/}
                            <Row style={{paddingBlock:"4%"}}>
                                <div>
                                    <p className={"positionForChar"}>"The multi-million copy bestseller"</p>
                                    <p className={"positionForChar"}>Soon to be a major film</p>
                                    <p className={"positionForChar"}>A Number One New York Times Bestseller</p>
                                    <p className={"positionForChar"}> I have never read such a brilliant book. You learn very quickly that this man must have been a really clever man.</p>
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
                width={600}
            >
                <p>Your Session is expired. Please Login again !!!</p>
            </Modal>
            <Modal
                title="Buying Success !!!"
                centered
                open={isOpenSuccess}
                onOk={() => closeSucessModal()}
                onCancel={() => closeSucessModal()}
                width={600}
            >
                <span>Add {bookDetail.bookName} To Cart Success. </span>
            </Modal>

            <Row style={{paddingTop:"10%",fontFamily:"Arial"}}>

                {bookDescription()}
                <Row className={"customerReviewPost"}>
                    <Col span={24}>
                        <Row style={{background:"#F6F6F6"}}>
                            <Col span={20} offset={2}>
                                <p className="positionForChar" style={{fontSize:"2vw",fontWeight:"bolder"}}> $29.99</p>
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
                                        <Button style={{background:"#CFD2CF"}} icon={<MinusOutlined />}></Button>
                                    </Col>
                                    {/*--------------------------------------------------------------------------------*/}
                                    <Col span={4} style={{borderColor:"#CFD2CF",justifyContent:"center",display:"flex",textAlign:"center"}}>
                                        <p style={{marginTop:"5%",marginBottom:"5%",fontWeight:"bolder",fontSize:"1.5vw"}}>1</p>
                                    </Col>
                                    {/*--------------------------------------------------------------------------------*/}
                                    <Col span={10} style={{borderColor:"#CFD2CF",justifyContent:"right",alignItems:"center",display:"flex"}}>
                                        <Button  style={{background:"#CFD2CF"}} icon={<PlusOutlined />}></Button>
                                    </Col>
                                </Row>

                            </Col>
                        </Row>
                        {/*--------------------------------------------------------------------------------*/}
                        <Row style={{marginTop:"10%",marginBottom:"15%"}}>
                            <Col span={20} offset={2}>
                                <Button style={{background:"#CFD2CF",paddingBottom:"10%",width:"100%"}}>
                                    <p className={"positionForChar"} style={{fontWeight:"bolder",fontSize:"1.5vw",marginBottom:"50%"}}>
                                        Add to cart
                                    </p>
                                </Button>
                            </Col>
                        </Row>
                    </Col>
                </Row>

                {/*<Col span={22} offset={1} style={{borderStyle:"ridge"}}>*/}
                {/*    <Row>*/}
                {/*        <Col span={15} style={{borderStyle:"ridge"}}>*/}

                {/*            <Row>*/}
                {/*                <Col span={6}>*/}
                {/*                    <img src='https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_38237.jpg' width='200px' height='200px' className='book-image' alt='example'/>*/}
                {/*                    <div className='book-author'> By Author {bookDetail.authorName}</div>*/}
                {/*                </Col>*/}
                {/*                <Col span={15} >*/}
                {/*                    <h2 className='book-title'>{bookDetail.bookName}</h2>*/}
                {/*                    {bookDetail.categoryName.map((name) => (*/}
                {/*                        <span> {name},</span>*/}
                {/*                    ))}*/}
                {/*                    <p className='book-description'>*/}
                {/*                        {bookDetail.description}*/}
                {/*                    </p>*/}
                {/*                </Col>*/}
                {/*            </Row>*/}

                {/*        </Col>*/}
                {/*        <Col span={9} >*/}
                {/*            <div className='cart-box'>*/}
                {/*                <Row className='book-price-box'>*/}
                {/*                    <span className='book-price'>${bookDetail.bookPrice}</span>*/}
                {/*                </Row>*/}
                {/*                <QuantityButton*/}
                {/*                    handleAdd={handleAddQuantity}*/}
                {/*                    handleLess={handleLessQuantity}*/}
                {/*                    quantity={quantity}*/}
                {/*                />*/}
                {/*                <Button className='add-cart'*/}
                {/*                        type='primary'*/}
                {/*                        onClick={handleAddCart}*/}
                {/*                >*/}
                {/*                    Add To Cart*/}
                {/*                </Button>*/}
                {/*            </div>*/}

                {/*        </Col>*/}

                {/*    </Row>*/}
                {/*</Col>*/}
            </Row>

            <FeedbackTable/>
        </>
    );
}