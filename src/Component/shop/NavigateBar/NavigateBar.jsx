import React from "react";
import "./NavigateBar.css"
import {BookFilled} from "@ant-design/icons";
export default function NavigateBar() {
    return(

        <div className={"nah"}>

            <BookFilled className={"book-icon"}/>
            <p className={"text-next-to-icon"}>Bookshop</p>
        </div>

    );
}