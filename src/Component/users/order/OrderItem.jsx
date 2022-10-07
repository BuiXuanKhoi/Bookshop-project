import React from "react";

export default function OrderItem ({bookName,imageLink,price,quantity}) {

    return (
        <>
            <div className={"flex-box"}>
                <div className={"image-wrapper"}>
                    <img style={{marginTop:"3%"}} src={imageLink} alt="Conan" />
                </div>
                <div style={{marginLeft:"1.5%",borderStyle:"ridge"}}>
                    <p className={"font-wrapper"}> {bookName}</p>
                    <p className={"font-wrapper2"}> x {quantity}</p>
                </div>
                <div style={{borderStyle:"ridge",marginLeft:"auto",alignItems:"center",display:"flex"}}>
                    <p> $ {Math.round(quantity*price*10)/10} </p>
                </div>
            </div>
        </>
    );
}