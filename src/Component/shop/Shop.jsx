import React from "react";
import {Col, Row} from "antd";
import SettingColumn from "./setting/SettingColumn";

export default function Shop(){

    return(
        <div style={{paddingTop : 80}}>
            <h3>
                Books
            </h3>
            <Row>
                <Col span={18} push={6}>
                    Hello
                </Col>
                <Col span={6} pull={18}>
                    <SettingColumn/>
                </Col>
            </Row>

        </div>
    );
}