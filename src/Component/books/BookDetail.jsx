import React, {useEffect, useState} from "react";
import {useParams} from 'react-router-dom';
import axios from "axios";
import {Col, Row} from "antd";
import '../shop/Shop.css';
import './BookDetail.css'
import FeedbackTable from "./FeedbackTable";

export default function BookDetail(){

    const bookId = useParams();

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

    const getBookDetail = () => {
        axios.get('https://ecommerce-web0903.herokuapp.com/api/books/' + bookId.id)
            .then((res) => {
                setBookDetail(res.data);
                console.log(res.data);
                console.log(bookDetail);
            }).catch((err) => console.log(err))
    }

    useEffect(() => {
        getBookDetail();
    },[])

    return(
        <div className='book-detail'>
            <Col>
                <Row>
                    <Col span={16}>
                        <div className='book-detail-box'>
                            <Row>
                                <Col span={6}>
                                    <img src='https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_38237.jpg' width='200px' height='200px' className='book-image' alt='example'/>
                                    <div className='book-author'> By Author {bookDetail.authorName}</div>
                                </Col>
                                <Col span={18}>
                                    <h2 className='book-title'>{bookDetail.bookName}</h2>
                                    <span>Book Description</span>
                                    <p className='book-description'>
                                        {bookDetail.description}
                                    </p>
                                </Col>
                            </Row>
                        </div>
                    </Col>
                    <Col span={8}>
                        <div className='cart-box'>
                            Cart
                        </div>

                    </Col>
                </Row>
                <FeedbackTable/>
            </Col>

        </div>
    );
}