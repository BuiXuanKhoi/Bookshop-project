import React, {useEffect, useRef, useState} from "react";
import './UserTable.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBan, faPenToSquare, faSort, faTrash, faUnlock} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import {getCookie} from "react-use-cookie";
import {Button, Drawer, Form, Pagination} from "antd";
import Input from "antd/lib/input/Input";
import Modal from "antd/es/modal/Modal";


export default function UserTable({config, token}){

    const [page, setPage] = useState(0);

    const authorize = {
        headers: {Authorization: 'Bearer ' + JSON.parse(getCookie('book-token')).token}
    }

    const [totalElements, setTotalElements] = useState(0);
    const [isOpenDetail, setIsOpenDetail] = useState(false);

    const [users, setUsers] = useState([{
        userId: 0,
        userName : '',
        email: '',
        location : '',
        createDate : '',
        updateDate : '',
        firstName : '',
        lastName : '',
        phoneNumber : '',
        role : '',
        userState : ''
    }])

    const [detail, setDetail] = useState({
        userId: 0,
        userName : '',
        email: '',
        location : '',
        createDate : '',
        updateDate : '',
        firstName : '',
        lastName : '',
        phoneNumber : '',
        role : '',
        userState : ''
    })


    const getUserPage = () => {
        axios.get('https://ecommerce-web0903.herokuapp.com/api/users?page=' + page, authorize )
            .then((res) => {
                console.log(res)
                setTotalElements(res.data.totalElements);
                setUsers(res.data.content)
            })
            .catch((err) => console.log(err))
    }

    const blockUser = (userId) => {
        axios.put('https://ecommerce-web0903.herokuapp.com/api/users/' + userId, null, config)
            .then((res) => console.log(res))
            .catch((err) => console.log(err))
    }

    const openDetailModal = () => {
        setIsOpenDetail(true);
    }

    const closeDetailModal = () => {
        setIsOpenDetail(false);
    }

    const changePage = (number) => {
        setPage(number - 1);
    }

    useEffect(() => {
        getUserPage();
    },[page])

    useEffect(() =>{
        getUserPage();
    },[])

    const handleEdit = (id) => {
        console.log(id)
    }

    const handleDelete = (id) => {
        closeDetailModal();
        console.log(id)
    }

    const handleBlock = (id) => {
        blockUser(id);
        window.location.reload();
    }

    const handleUnlock = (id) => {
        console.log(id);
    }

    const handleFind = (user) => {
        setDetail(user);
        openDetailModal();
    }




    return(
        <div>
            <Modal
                title="Detail Information"
                open={isOpenDetail}
                closable={true}
                footer={null}
                onCancel={closeDetailModal}
            >
                <div>{detail.userName}</div>
                <div>{detail.firstName}  {detail.lastName}</div>
                <div>{detail.userId}</div>
                <div>{detail.userState}</div>
                <div>{detail.createDate}</div>
                <div>{detail.email}</div>
                <div>{detail.role}</div>

            </Modal>
            <Modal
                title="Are you sure ?"
            >
                <span>Are you sure to delete this user ?</span>
            </Modal>
            <table className="user-table-container">
                <thead className="user-table-column">
                    <th className="user-table-column-container">User name</th>
                    <th className="user-table-column-container">First name</th>
                    <th className="user-table-column-container">Last name</th>
                    <th className="user-table-column-container">E-mail</th>
                    <th className="user-table-column-container">Create Date</th>
                </thead>
                <tbody>
                {
                    users.map((user) => (
                        <tr className="user-table-row-container"  >
                            <td onClick={() => handleFind(user)}>{user.userName}</td>
                            <td onClick={() => handleFind(user)}>{user.firstName}</td>
                            <td onClick={() => handleFind(user)}>{user.lastName}</td>
                            <td onClick={() => handleFind(user)}>{user.email}</td>
                            <td onClick={() => handleFind(user)}>{user.createDate}</td>
                            <td>
                                <Button className="btn-style">
                                    <FontAwesomeIcon icon={faPenToSquare} />
                                </Button>
                            </td>
                            <td>
                                <Button className="btn-style">
                                    {
                                        user.userState === 'UNBLOCK' ? (
                                            <FontAwesomeIcon icon={faBan} onClick={() => handleBlock(user.userId)}/>
                                        ) :
                                            (
                                                <FontAwesomeIcon icon={faUnlock} onClick={() => handleUnlock(user.userId)}/>
                                            )
                                    }
                                </Button>
                            </td>
                        </tr>
                    ))
                }
                </tbody>
            </table>

            <Pagination className="user-table-page" defaultPageSize={10} defaultCurrent={1} total={totalElements} onChange={changePage}/>
        </div>
    )
}