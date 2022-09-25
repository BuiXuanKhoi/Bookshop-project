import React from "react";
import {Divider, Row,Col} from "antd";
import Cart from "../../users/cart/Cart";
import {SettingOutlined, EditOutlined, EllipsisOutlined} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";
import { Avatar, Card } from 'antd';
import './RecommendTable.css'
export default function RecommendTable(){
    return(
        <div className={"recommend-table"} >
            <p>Hello world</p>
            <div className={"new"}>

                <p> Hi world</p>
            </div>
            {/*<Card*/}
            {/*    style={{*/}
            {/*        width: 300,*/}
            {/*    }}*/}
            {/*    cover={*/}
            {/*        <img*/}
            {/*            alt="example"*/}
            {/*            src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"*/}
            {/*        />*/}
            {/*    }*/}
            {/*    actions={[*/}
            {/*        <SettingOutlined key="setting" />,*/}
            {/*        <EditOutlined key="edit" />,*/}
            {/*        <EllipsisOutlined key="ellipsis" />,*/}
            {/*    ]}*/}
            {/*>*/}
            {/*    <Meta*/}
            {/*        avatar={<Avatar src="https://joeschmoe.io/api/v1/random" />}*/}
            {/*        title="Card title"*/}
            {/*        description="This is the description"*/}
            {/*    />*/}
            {/*</Card>*/}
        </div>

    );
}