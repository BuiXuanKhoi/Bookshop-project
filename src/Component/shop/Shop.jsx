import React, {useEffect, useRef, useState} from "react";
import {Avatar, Button, Card, Col, Dropdown, Menu, Pagination, Row, Space} from "antd";
import FilterColumn from "./setting/FilterColumn";
import DisplayBooks from "./display/DisplayBooks";
import axios from "axios";
import './Shop.css'
import ButtonArrow from "./ButtonArrow";
import {DownOutlined} from "@ant-design/icons";
import {useNavigate} from "react-router";
import MyCard from "../general/MyCard";

const {Meta} = Card;

export default function Shop(){

    const [authors, setAuthors] = useState([]);
    const [categories, setCategories] = useState([]);
    const [pageBooks, setPageBooks] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = useState(0);
    const [search, setSearch] = useState('');
    const [mode, setMode] = useState('na');
    const [id, setId] = useState();
    const navigate = useNavigate();

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
        displayBooks();
    }

    useEffect(() => {
        displayBooks();
    },[mode])

    const handleChooseSort = (e) =>{
        setMode(e.key);
    }

    const menu = (
        <Menu

            onClick={handleChooseSort}

            items={[
                {
                    label : 'Sort By Price Low To High. ',
                    key : 'pa'
                },
                {
                    label: 'Sort By Price High To Low. ',
                    key: 'pd'
                },
                {
                    label : 'Sort By Rating Point Low To High. ',
                    key: 'ra'
                },
                {
                    label: 'Sort By Rating Point High To Low. ',
                    key: 'rd'
                },
                {
                    label: 'Sort By Book Name A-Z',
                    key: 'na'
                },
                {
                    label: 'Sort By Book Name Z-A',
                    key: 'nd'
                },
                {
                    label: 'Sort By Book Release Day Soon To Late. ',
                    key: 'ia'
                },
                {
                    label: 'Sort By Book Release Day Late To Soon',
                    key: 'id'
                }
            ]}
        />
    )

    const handleOnClick = (id) => {
        navigate('/books/' + id);
    }





    return(
        <div className={'shop'}>
            <h1 style={{position:'fixed'}}>
                Books
            </h1>
            <Row className='shop-except-header'>
                <Dropdown overlay={menu} className='dropdown-sort'>
                    <Button>
                        <Space>
                            Sort
                            <DownOutlined/>
                        </Space>
                    </Button>
                </Dropdown>
                <Col span={18} push={6} className='display-column'>
                    <Row className='eachRow'>

                        {
                            pageBooks.map((book) =>

                                <Col span={6} key={book.bookId} className='eachRow'>
                                    {/*<Card*/}
                                    {/*    style={{width: 200,*/}
                                    {/*        borderRadius : '10px'}}*/}
                                    {/*    key={book.bookId}*/}
                                    {/*    cover={*/}
                                    {/*        <img*/}
                                    {/*            alt="example"*/}
                                    {/*            src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"*/}
                                    {/*        />*/}
                                    {/*    }*/}
                                    {/*    actions={<ButtonArrow/>}*/}
                                    {/*    onClick={ (e) => {*/}
                                    {/*        handleOnClick(book.bookId);*/}
                                    {/*    }}*/}
                                    {/*>*/}
                                    {/*    <Meta*/}
                                    {/*        avatar={<Avatar src="https://joeschmoe.io/api/v1/random"/>}*/}
                                    {/*        title={book.bookName}*/}
                                    {/*        description={book.bookPrice}*/}
                                    {/*   />*/}
                                    {/*    <ButtonArrow/>*/}
                                    {/*</Card>*/}
                                    <MyCard item={book} url="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"/>
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