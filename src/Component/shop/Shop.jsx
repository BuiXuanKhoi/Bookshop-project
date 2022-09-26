import React, {useEffect, useRef, useState} from "react";
import {Avatar, Card, Col, Menu, Pagination, Row} from "antd";
import FilterColumn from "./setting/FilterColumn";
import DisplayBooks from "./display/DisplayBooks";
import axios from "axios";
import './Shop.css'
import ButtonArrow from "./ButtonArrow";

const {Meta} = Card;

export default function Shop(){

    const [authors, setAuthors] = useState([]);
    const [categories, setCategories] = useState([]);
    const [pageBooks, setPageBooks] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = useState(0);
    const [search, setSearch] = useState('');
    const [filter, setFilter] = useState([]);
    const [mode, setMode] = useState('na');
    const [id, setId] = useState();

    const displayBooks = () => {
        axios.get('https://ecommerce-web0903.herokuapp.com/api/books?page=' + page + '&mode=' + mode + '&searchCode=' + search + '&filter=' + categories)
            .then((res) => {
                setPageBooks(res.data.content)
                setCurrentPage(res.data.number)
                setTotalElements(res.data.totalElements)
            })
            .catch((err) => setPageBooks([]))
    }

    useEffect(() => displayBooks(), []);




    const updateAuthor = (e) => {
        if(e.target.checked)
        {
            authors.push(e.target.value);
        }else
        {
            authors.splice(authors.indexOf(e.target.value), 1);
        }
    }

    const updateCategories = (e) => {
        if(e.target.checked)
        {
            categories.push(e.target.value);
        }else
        {
            categories.splice(categories.indexOf(e.target.value), 1);
        }

        console.log(categories);
        displayBooks();
    }

    const menu = (
        <Menu

            items={[
                {
                    label : 'Sort By Price Low To High.',
                    key : 'pa'
                }
            ]}
        />
    )

    useEffect(() =>{
        console.log(id);
    },[id])






    return(
        <div className={'shop'}>
            <h1>
                Books
            </h1>
            <Row className='shop-except-header'>
                <Col span={18} push={6} className='display-column'>

                    <Row className='eachRow'>
                        {
                            pageBooks.map((book) =>

                                <Col span={6} key={book.bookId} className='eachRow'>
                                    <Card
                                        style={{width: 200,
                                            borderRadius : '10px'}}
                                        key={book.bookId}
                                        cover={
                                            <img
                                                alt="example"
                                                src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
                                            />
                                        }
                                        actions={<ButtonArrow/>}
                                        onClick={ (e) => {
                                            setId(book.bookId);
                                        }}
                                    >
                                        <Meta
                                            avatar={<Avatar src="https://joeschmoe.io/api/v1/random"/>}
                                            title={book.bookName}
                                            description={book.bookPrice}
                                       />
                                        <ButtonArrow/>
                                    </Card>
                                </Col>
                            )
                        }
                    </Row>

                    <Pagination className='page-navigate' defaultCurrent={currentPage} defaultPageSize={20} total={totalElements} />

                </Col>
                <Col span={6} pull={18} >
                    <FilterColumn changeAuthorFilter={updateAuthor} changeCategoryFilter={updateCategories}/>
                </Col>
            </Row>

        </div>
    );
}