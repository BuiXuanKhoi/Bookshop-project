import React from "react";

export default function GeneralFunction(){

    const removeTheItem = ({array, removeItem}) => {
        if(array.include(removeItem))
        {
            array.filter(function (item)
            {
                return item !== removeItem;
            })
        }else{
            array.removeItem(removeItem)
        }
        return array;
    }
}