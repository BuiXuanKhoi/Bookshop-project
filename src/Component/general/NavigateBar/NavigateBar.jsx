import React from "react";
import "./NavigateBar.css"
import {BookFilled} from "@ant-design/icons";
import {Button, Divider, Dropdown, Menu} from "antd";
import {UserOutlined,ProfileOutlined,HourglassOutlined,LogoutOutlined} from "@ant-design/icons";
import Login from "../../Auth/login/Login";
import {Link} from "react-router-dom";
import {useCookies} from "react-cookie";
import {useNavigate} from "react-router";
import '@fortawesome/fontawesome-svg-core';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBroom, faReply, faRightFromBracket, faUser, faUserCheck, faUserGear} from "@fortawesome/free-solid-svg-icons";
const NavMenu = (nav) => {

    const navigate = useNavigate();

    const [cookies, setCookies, removeCookies] = useCookies(['book-token']);

    const handleClick = ({key}) => {
        if(key === 'logout'){
            logout();
        }else{
            navigate(key);
        }
    }



    const logout = () => {
        removeCookies('book-token');
        console.log("Log Out")
        window.location.reload();
    }
    return(
        <Menu onClick={handleClick}>
            {(nav).map((item) =>
                    <Menu.Item icon={item.icon}  key={item.path}>{item.title}</Menu.Item>
            )}

            <Divider/>
            <Menu.Item icon={<FontAwesomeIcon icon={faRightFromBracket}/>} style={{color:'red'}}key={'logout'} >Log out</Menu.Item>
        </Menu>
    );
}


export default function NavigateBar({menu}) {


    return(
        <>
        <div className={"nah"}>
            <BookFilled className={"book-icon"}/>
            <Link className={"text-next-to-icon"} to={'/'} >Book Shop</Link>
            {menu === null ? (
                <div className={'icon-person'}>
                    <Link className={'Link'} to={'/login'}>Login</Link>
                    <span className={'Link'}> / </span>
                    <Link className={'Link'} to={'/signup'}>Sign up</Link>
                </div>
            ) :  (
                <div className={'icon-person'}>
                    <Dropdown overlay={NavMenu(menu)}
                              placement={"bottomRight"}
                              arrow={{
                                  pointAtCenter:true,}
                              }
                    >
                        <Button className={"button-icon"}><UserOutlined className={"icon-user"}/></Button>
                    </Dropdown>
                </div>
            )}
        </div>
        </>
    );
}