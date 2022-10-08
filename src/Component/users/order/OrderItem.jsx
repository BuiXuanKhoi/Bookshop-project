import React, {useEffect, useState} from "react";

export default function OrderItem ({bookName,imageLink,price,quantity}) {
    const [sum,setSum] = useState(0);
    let save = 0
    const calculateCost = (quantity,price)=>{
         save = Math.round(quantity*price*10)/10;
         setSum(save);
    }
    useEffect(()=>{
        calculateCost(quantity,price)
    },[quantity,price])
    return (
        <>
            <div className={"flex-box"}>
                <div className={"image-wrapper"}>
                    <img style={{marginTop:"3%"}} src={imageLink} alt="Conan" />
                </div>
                <div style={{marginLeft:"1.5%"}}>
                    <p className={"font-wrapper"}> {bookName}</p>
                    <p className={"font-wrapper2"}> x {quantity}</p>
                </div>
                <div style={{marginLeft:"auto",alignItems:"center",display:"flex",fontSize:"1.5rem",fontWeight:"bolder"}}>
                    <p> ${sum}</p>
                </div>
            </div>
        </>
    );
}