import React from "react";
import '../home/recommend/RecommendTable.css'
import {useNavigate} from "react-router";

export default function CartBook ({item}){
    const navigate = useNavigate();
    const handleOnCLickBook = (bookID) =>{
        console.log(bookID)
        navigate("/books/"+bookID);
    }
    return (
        <div className="container" onClick={()=>handleOnCLickBook(item.bookId)}>
            <div className="card">
                <div className="card__header">
                    <img src={item.imageLink} alt="card__image"
                         className="card__image" style={{ width:"300"}}/>
                </div>
                <div className="card__body">
                    <h4>{item.bookName}</h4>
                    <p>{item.authorName}</p>
                </div>
                <div className="card footer" style={{background:"#D8D8D8"}}>
                    <div className="user">
                        <div className="user__info">
                            <h5>${item.bookPrice}</h5>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}