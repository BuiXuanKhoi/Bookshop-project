 import React, {useEffect, useState} from "react";
import './ManageBookTable.css'
import {Button, Drawer, Pagination,Modal} from "antd";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBan, faPenToSquare, faTrash, faUnlock} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import {getCookie} from "react-use-cookie";
 import EditBook from "../edit/EditBook";
 import {ExclamationCircleOutlined} from "@ant-design/icons";
 import {config} from "@fortawesome/fontawesome-svg-core";
 import confirm from "antd/es/modal/confirm";
export default function ManageBookTable(){

    const authorize = {
        headers: {Authorization: 'Bearer ' + JSON.parse(getCookie('book-token')).token}
    }

    const [search, setSearch] = useState('');
    const [page, setPage] = useState(0);
    const [mode, setMode] = useState('na');
    const [totalElements, setTotalElements] = useState(0);
    const [isOpenDetail, setIsOpenDetail] = useState(false);
    const [openEditBook, setOpenEditBook] = useState(false);
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
    const [bookID,setBookID] = useState(0);

    const handleEdit = (bookid) => {
        setOpenEditBook(true);
        setBookID(bookid);
    }
    const closeEdit = () =>{
        setOpenEditBook(false);
    }
    const handleDelete = (bookId) => {
        axios.delete("https://ecommerce-web0903.herokuapp.com/api/books/"+bookId,authorize)
            .then((res) => {
                Modal.success({
                    content: "Book has been deleted !!!"
                })
                window.location.reload();
            })
            .catch((error) =>{
                console.log(error)
            })
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
    const showDeleteConfirm = (bookId) => {
        confirm({
            title: 'Are you sure you want to delete this book ?',
            icon: <ExclamationCircleOutlined style={{color:"orange"}}/>,
            okText: 'Ok',
            okType: 'danger',
            cancelText: 'Cancel',
            onOk() {
                handleDelete(bookId)
            },
            onCancel() {
            },
        });
    };
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
                                <>
                                    <Button className="btn-style" onClick={()=> handleEdit(book.bookId)}>
                                        <FontAwesomeIcon icon={faPenToSquare} />
                                    </Button>
                                </>
                            </td>
                            <td>

                                <Button className="btn-style" onClick={() => showDeleteConfirm(book.bookId)}>
                                    <FontAwesomeIcon icon={faTrash}/>
                                </Button>
                            </td>
                        </tr>
                    ))
                }
                    <EditBook setOpenEditBook={setOpenEditBook} open={openEditBook} closeEdit = {closeEdit} bookId={bookID}/>
                </tbody>
            </table>

            <Pagination className="book-table-page" defaultPageSize={20} defaultCurrent={1} total={totalElements} onChange={changePage}/>
        </div>
    );

}