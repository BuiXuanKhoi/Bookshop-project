import React from "react";
// import '../home/recommend/RecommendTable.css'
import {Col} from "antd";
import './MyCart.css';
import {useNavigate} from "react-router";

export default function MyCard({item, url}){

    const navigate = useNavigate();

    const handleClickCard = (id) => {
        console.log(id)
        navigate('/books/' + id);
    }

    return(
        // <div className="container" onClick={() => handleClickCard(item.bookId)}>
        //     <div className="card" >
        //         <div className="card__header">
        //             <img src={url} alt="card_image"
        //                  className="card_image" style={{widtd: "300"}}
        //             />
        //         </div>
        //         <div className="card__body">
        //             <h4>{item.bookName}</h4>
        //             <p>{item.authorName}</p>
        //         </div>
        //         <div className="card footer" style={{background: "#D8D8D8"}}>
        //             <div className="user">
        //                 <div className="user__info">
        //                     <h5>{item.bookPrice}</h5>
        //                 </div>
        //             </div>
        //         </div>
        //     </div>
        // </div>

        <div className="card-container" onClick={() => handleClickCard(item.bookId)}>
            <div className="card-image-container">
                <img src={url} className="card-image"/>
            </div>

            <div className="card-detail-container">
                <h3 className="card-name">
                    <span>{item.bookName}</span>
                </h3>
                <div>
                    {item.authorName}
                </div>
                <div>
                    <span className="card-rating">{item.ratingPoint}</span>
                    <svg xmlns="http://www.w3.org/2000/svg" className="rating">
                        <path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z"></path>

                    </svg>
                </div>
                <div>

                </div>
                <div className="card-price">
                    {item.bookPrice}$
                </div>
            </div>

        </div>
    );

}