import React from "react";
import "./NavigateBar.css"
import {BookFilled} from "@ant-design/icons";
import {Link} from "react-router-dom";
export default function NavigateBar() {
    return(

        <div className={"nah"}>

            <BookFilled className={"book-icon"}/>
            <div className={"text-next-to-icon"}>
                <Link to="/home">Book shop</Link>
            </div>
            <div>

            </div>
        </div>

    );
}