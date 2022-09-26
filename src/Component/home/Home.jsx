import React from "react";
import './Home.css';
import RecommendTable from "./recommend/RecommendTable";
import {Button, Col, Row} from "antd";
import './recommend/RecommendTable.css'
import './Home.css'
import {SearchOutlined} from "@ant-design/icons"
export default function Home(){

    return(
        <div >
            <RecommendTable/>
            <div className={"recommend-table"}>
                <Row className={"feature"}>
                    <Col span={23} style={{textAlign:"center"}}>
                        Feature Books
                    </Col>
                </Row>

                <Row>
                    <Col span={3} offset={10}>
                        <Button type="primary" icon={<SearchOutlined />}>Recommend</Button>
                    </Col>
                    <Col span={3} pull={1}>
                        <Button type="primary" icon={<SearchOutlined />} style={{background:"gray"}}>Popular</Button>
                    </Col>
                </Row>

                <Row >
                    <Col>
                        <Row style={{borderStyle:"ridge",borderColor:"green"}}>
                            hello world
                        </Row>

                        <Row style={{borderStyle:"ridge",borderColor:"red"}}>
                            hi world
                        </Row>
                    </Col>
                </Row>
            </div>
        </div>
    );
}