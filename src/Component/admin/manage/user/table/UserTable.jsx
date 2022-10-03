import React, {useEffect, useState} from "react";
import './UserTable.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSort} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import {CreateToken} from "../../../../general/ManageCookies";
import {getCookie} from "react-use-cookie";
import {Drawer, Form, Pagination} from "antd";
import Input from "antd/lib/input/Input";


export default function UserTable({config, token}){

    const [page, setPage] = useState(0);

    const authorize = {
        headers: {Authorization: 'Bearer ' + JSON.parse(getCookie('book-token')).token}
    }

    const [totalElements, setTotalElements] = useState(0);
    const[currentPage, setCurrentPage] = useState(0);

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


    const getUserPage = () => {
        axios.get('https://ecommerce-web0903.herokuapp.com/api/users?page=' + page, authorize )
            .then((res) => {
                console.log(res)
                setTotalElements(res.data.totalElements);
                setUsers(res.data.content)
            })
            .catch((err) => console.log(err))
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





    return(
        <div>
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
                        <tr  className="user-table-row-container" >
                            <td className="user-table-data">{user.userName}</td>
                            <td>{user.firstName}</td>
                            <td>{user.lastName}</td>
                            <td>{user.email}</td>
                            <td>{user.createDate}</td>
                        </tr>
                    ))
                }
                </tbody>
            </table>

            <Pagination className="user-table-page" defaultPageSize={10} defaultCurrent={1} total={totalElements} onChange={changePage}/>
        </div>
    )
}