import React from "react";
import {Button, Result} from "antd";

export default function PageNotPermission(){



    return(
        <Result
            style={{paddingTop:'10rem'}}
            status="401"
            title="Error"
            subTitle="Sorry, you cannot access to this page. "
            extra={<Button type="primary" href="/">Back Home</Button>}
        />
    );
}