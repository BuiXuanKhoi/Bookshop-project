import React, {useState} from "react";
import './Home.css';
import RecommendTable from "./recommend/RecommendTable";
import {Button, Col, Row} from "antd";
import './recommend/RecommendTable.css'
import './Home.css'
import {SearchOutlined} from "@ant-design/icons"
export default function Home(){

    const [value, setValue] = useState(1);
    const [isChange, setIsChange] = useState(false);

    const handleClick = (event) => {
        if (value !== 1){
            value.style.background = 'white';
        }
        event.target.style.background = 'black';
        setValue(event.target);
        console.log(value);
    }

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
                        <Button type="primary" onClick={handleClick} style={{backgroundColor:'blue'}} icon={<SearchOutlined />}>Recommend</Button>
                    </Col>
                    <Col span={3} pull={1}>
                        <Button type="primary"  onClick={handleClick} icon={<SearchOutlined />} style={{background:"white"}}>Popular</Button>
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