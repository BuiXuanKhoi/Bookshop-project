import React from "react";
import {Button, Result} from "antd";
import './shop/Shop.css'

export default function BlankPage(){

    return(
        <Result
            style={{paddingTop:'10rem'}}
            status="404"
            title="Error"
            subTitle="Sorry, the path you are trying to access is no longer available. "
            extra={<Button type="primary" href="/">Back Home</Button>}
         />
    );
}