import React, {useEffect, useState} from "react";
import {Avatar, Card, Col, Pagination, Row} from "antd";
import axios from "axios";

const {Meta} = Card;

export default function DisplayBooks({authors}){

    // const [pageBooks, setPageBooks] = useState([]);
    // const [currentPage, setCurrentPage] = useState(0);
    // const [totalElements, setTotalElements] = useState(0);
    // const [page, setPage] = useState(0);
    // const [search, setSearch] = useState('');
    // const [filter, setFilter] = useState([]);
    // const [mode, setMode] = useState('na');
    //
    // const displayBooks = () => {
    //     console.log(authors)
    //     axios.get('https://ecommerce-web0903.herokuapp.com/api/books?page=' + page + '&mode=' + mode + '&searchCode=' + search + '&filter=' + authors)
    //         .then((res) => {
    //             console.log(res);
    //             setPageBooks(res.data.content)
    //             setCurrentPage(res.data.number)
    //             setTotalElements(res.data.totalElements)
    //             console.log(pageBooks);
    //         })
    //         .catch((err) => console.log(err))
    // }
    //
    // const handleClick = (e) => {
    //     console.log(e);
    // }
    //
    // useEffect(() =>{
    //     console.log(authors)
    //     displayBooks();
    //     console.log(pageBooks);
    // },[authors])
    return(

        <div>
            {/*    <Row>*/}
            {/*        {*/}
            {/*            pageBooks.map((book) => (*/}

            {/*                <Col span={6}>*/}
            {/*                    <Card*/}
            {/*                        style={{width: 250}}*/}
            {/*                        key={book.bookId}*/}
            {/*                        cover={*/}
            {/*                            <img*/}
            {/*                                alt="example"*/}
            {/*                                src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"*/}
            {/*                            />*/}
            {/*                        }*/}
            {/*                    >*/}
            {/*                        <Meta*/}
            {/*                            avatar={<Avatar src="https://joeschmoe.io/api/v1/random"/>}*/}
            {/*                            title={book.bookName}*/}
            {/*                            description={book.bookPrice}*/}
            {/*                        />*/}
            {/*                    </Card>*/}
            {/*                </Col>*/}

            {/*            ))*/}
            {/*        }*/}
            {/*    </Row>*/}

            {/*<Pagination className='page-navigate' defaultCurrent={currentPage} defaultPageSize={20} total={totalElements} />*/}


        </div>


    );

}