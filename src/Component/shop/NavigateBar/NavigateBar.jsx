import React from "react";
import "./NavigateBar.css"
import {BookFilled} from "@ant-design/icons";
import {Button, Dropdown, Menu} from "antd";
import {UserOutlined,ProfileOutlined,HourglassOutlined,LogoutOutlined} from "@ant-design/icons";

const menu = (
    <Menu
        items={[
            {
                key: '1',
                label: (
                    <div>
                        <ProfileOutlined className={"icon-user"}/>
                        <a target="_blank" rel="noopener noreferrer" href="https://www.antgroup.com" className={"information"}>
                            Personal's Information
                        </a>
                    </div>
                ),
            },
            {
                key: '2',
                label: (
                    <div>
                        <HourglassOutlined className={"icon-history"}/>
                        <a target="_blank" rel="noopener noreferrer" href="https://www.aliyun.com" className={"history"}>
                            Ordered history
                        </a>
                    </div>
                ),
            },
            {
                key: '3',
                label: (
                    <div>
                        <LogoutOutlined className={"icon-logout"}/>
                        <a target="_blank" rel="noopener noreferrer" href="https://www.luohanacademy.com" className={"logout"}>
                            Log out
                        </a>
                    </div>
                ),
            },
        ]}
    />
);
export default function NavigateBar() {
    return(
        <>
        <div className={"nah"}>
            <BookFilled className={"book-icon"}/>
            <p className={"text-next-to-icon"}>Book Shop</p>
            <ul>
                <li>Home</li>
                <li>Shop</li>
                <li style={{textDecoration:"underline",}}>About</li>
                <li>Cart</li>
                <li>Sign in/Sign up</li>
            </ul>

        </div>
        <div className={"personal"}>
            <Dropdown overlay={menu}
                      placement={"bottomRight"}
                      arrow={{
                          pointAtCenter:true,}
                      }
            >
                <Button className={"button-icon"}><UserOutlined className={"icon-user"}/></Button>
            </Dropdown>
        </div>
        </>
    );
}