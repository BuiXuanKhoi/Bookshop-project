import React from "react";
import "./NavigateBar.css"
import {BookFilled} from "@ant-design/icons";
import {Button, Dropdown, Menu} from "antd";
import {UserOutlined,ProfileOutlined,HourglassOutlined,LogoutOutlined} from "@ant-design/icons";
import Login from "../../Auth/login/Login";
import {Link} from "react-router-dom";
import {useCookies} from "react-cookie";
import {useNavigate} from "react-router";
import color from "color";
// const menu = (
//     <Menu
//         items={[
//             {
//                 key: '1',
//                 label: (
//                     <div>
//                         {/*<ProfileOutlined className={"icon-user"}/>*/}
//                         <a target="_blank" rel="noopener noreferrer" href="https://www.antgroup.com" className={"information"}>
//                             Personal's Information
//                         </a>
//                     </div>
//                 ),
//             },
//             {
//                 key: '2',
//                 label: (
//                     <div>
//                         <HourglassOutlined className={"icon-history"}/>
//                         <a target="_blank" rel="noopener noreferrer" href="https://www.aliyun.com" className={"history"}>
//                             Ordered history
//                         </a>
//                     </div>
//                 ),
//             },
//             {
//                 key: '3',
//                 label: (
//                     <div>
//                         <LogoutOutlined className={"icon-logout"}/>
//                         <a target="_blank" rel="noopener noreferrer" href="https://www.luohanacademy.com" className={"logout"}>
//                             Log out
//                         </a>
//                     </div>
//                 ),
//             },
//         ]}
//     />
// );

const NavMenu = (nav) => {

    const [cookies, setCookies, removeCookies] = useCookies(['book-token']);

    const handleClick = ({key}) => {
        console.log(key);
        if(key === 'logout'){
            logout();
        }
    }

    const logout = () => {
        removeCookies('book-token');
        window.location.reload();
    }
    return(
      <Menu onClick={handleClick}>
          {nav.map((item) =>
              <Menu.Item  key={item.path}>{item.title}</Menu.Item>
          )}

          <Menu.Item key={'logout'} >Log out</Menu.Item>
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