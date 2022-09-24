import React, {useEffect} from "react";

export default function Report(){

    useEffect(() =>{
        console.log("Report");
    },[]);
    return(
        <div>
            Report !!!
        </div>
    );
}