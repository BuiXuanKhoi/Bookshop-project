import React, {useContext} from "react";
import {Route, Routes} from "react-router-dom";
import BlankPage from "../Component/BlankPage";
import {Space} from "antd";
import {SecurityContext} from "../App";
import PageNotPermission from "../Component/PageNotPermission";

export default function RoutesDefine({routes}){

    const [loginData, setLoginData] = useContext(SecurityContext);

    console.log(routes);

    return(
        <Routes>
            {routes.map((item) =>
                <Route key={item.path} path={item.path} element={item.component}/>
            )}
            <Route key={'*'} path={'*'} element={<BlankPage/>} />

        </Routes>
    )
}