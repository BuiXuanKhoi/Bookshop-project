import React, {useState} from "react";
import {Button} from "antd";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faMinus, faPlus} from "@fortawesome/free-solid-svg-icons";
import './QuantityButton.css';


export default function QuantityButton({handleAdd, handleLess, quantity}){



    return(
        <div className='change-quantity'>
            <Button icon={<FontAwesomeIcon icon={faPlus}/>}
                    size='large'
                    type='primary'
                    onClick={handleAdd}
            />
            <span className='quantity'>{quantity}</span>
            {
                quantity > 0 ? (
                <Button
                icon={<FontAwesomeIcon icon={faMinus}/>}
                size='large'
                type='primary'
                onClick={handleLess}
                />

                ) : (
                <Button
                icon={<FontAwesomeIcon icon={faMinus}/>}
                size='large'
                type='primary'
                onClick={handleLess}
                />
                )
            }
        </div>

    );
}