import React from "react";
import {useParams} from 'react-router-dom';

export default function BookDetail(){

    let bookId = useParams();
    console.log(bookId);

    return(
        <div>
            Book Detail !!!

        </div>
    );
}