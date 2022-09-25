import React from "react";
import {Route, Routes} from "react-router-dom";
import Report from "../Component/admin/manage/report/Report";
import BlankPage from "../Component/BlankPage";

export default function RoutesDefine({routes}){

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