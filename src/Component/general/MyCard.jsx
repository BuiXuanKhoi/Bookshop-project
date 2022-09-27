import React from "react";
import '../home/recommend/RecommendTable.css'
import {Col} from "antd";
import {useNavigate} from "react-router";

export default function MyCard({item, url}){

    const navigate = useNavigate();

    const handleClickCard = (id) => {
        console.log(id)
        navigate('/books/' + id);
    }

    return(
        <div className="container" onClick={() => handleClickCard(item.bookId)}>
            <div className="card" >
                <div className="card__header">
                    <img src={url} alt="card_image"
                         className="card_image" style={{widtd: "300"}}
                    />
                </div>
                <div className="card__body">
                    <h4>{item.bookName}</h4>
                    <p>{item.authorName}</p>
                </div>
                <div className="card footer" style={{background: "#D8D8D8"}}>
                    <div className="user">
                        <div className="user__info">
                            <h5>{item.bookPrice}</h5>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

}