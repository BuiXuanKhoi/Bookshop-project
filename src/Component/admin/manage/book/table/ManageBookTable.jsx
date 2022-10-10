 import React, {useEffect, useState} from "react";
import './ManageBookTable.css'
import Modal from "antd/es/modal/Modal";
import {Button, Pagination} from "antd";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBan, faPenToSquare, faTrash, faUnlock} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import {getCookie} from "react-use-cookie";
export default function ManageBookTable(){

    const authorize = {
        headers: {Authorization: 'Bearer ' + JSON.parse(getCookie('book-token')).token}
    }



    const [search, setSearch] = useState('');
    const [page, setPage] = useState(0);
    const [mode, setMode] = useState('na');
    const [totalElements, setTotalElements] = useState(0);
    const [isOpenDetail, setIsOpenDetail] = useState(false);

    const [books, setBooks] = useState([{
        bookId : 0,
        bookName: '',
        bookPrice : 0.0,
        ratingPoint : 0.0,
        imageLink : '',
        authorName : ''
    }])

    const [bookDetail, setBookDetail] = useState({
        bookId : 0,
        bookName: '',
        bookPrice : 0.0,
        ratingPoint : 0.0,
        imageLink : '',
        authorName : ''
    })

    const getBookPage = () => {
        axios.get('https://ecommerce-web0903.herokuapp.com/api/books?page=' + page + '&search=' + search + '&mode=' + mode, authorize)
            .then((res) => {
                console.log(res);
                setTotalElements(res.data.totalElements);
                setBooks(res.data.content);
            })
            .catch((err) => console.log(err))
    }

    useEffect(() => {
        getBookPage();
    },[])

    useEffect(() => {
        getBookPage();
    },[page])

    const handleFind = (book) => {
        setBookDetail(book);
        openBookDetailModal();
    }

    const handleEdit = (bookId) => {
        console.log(bookId);
    }

    const handleDelete = (bookId) => {
        console.log(bookId);
    }

    const changePage = (number) => {
        setPage(number - 1);
    }

    const openBookDetailModal = () => {
        setIsOpenDetail(true);
    }

    const closeBookDetailModal = () => {
        setIsOpenDetail(false);
    }


    return(
        <div>
            <Modal
                title="Book Information"
                open={isOpenDetail}
                closable={true}
                footer={null}
                onCancel={closeBookDetailModal}
            >
                <div>{bookDetail.bookName}</div>
                <div>{bookDetail.bookId}</div>
                <div>{bookDetail.bookPrice}</div>
                <div>{bookDetail.authorName}</div>
                <div>{bookDetail.ratingPoint}</div>
            </Modal>
            <Modal
                title="Are you sure ?"
            >
                <span>Are you sure to delete this user ?</span>
            </Modal>
            <table className="book-table-container">
                <thead className="book-table-column">
                <th className="book-table-column-container">Book Name</th>
                <th className="book-table-column-container">Book Price</th>
                <th className="book-table-column-container">Rating Point</th>
                <th className="book-table-column-container">Author</th>
                </thead>
                <tbody>
                {
                    books.map((book) => (
                        <tr className="book-table-row-container"  >
                            <td onClick={() => handleFind(book)}>{book.bookName}</td>
                            <td onClick={() => handleFind(book)}>{book.bookPrice}</td>
                            <td onClick={() => handleFind(book)}>{book.ratingPoint}</td>
                            <td onClick={() => handleFind(book)}>{book.authorName}</td>
                            <td>
                                <Button className="btn-style" onClick={() =>handleEdit(book.bookId)}>
                                    <FontAwesomeIcon icon={faPenToSquare} />
                                </Button>
                            </td>
                            <td>
                                <Button className="btn-style" onClick={() => handleDelete(book.bookId)}>
                                    <FontAwesomeIcon icon={faTrash}/>
                                </Button>
                            </td>
                        </tr>
                    ))
                }
                </tbody>
            </table>

            <Pagination className="book-table-page" defaultPageSize={20} defaultCurrent={1} total={totalElements} onChange={changePage}/>
        </div>
    );

}