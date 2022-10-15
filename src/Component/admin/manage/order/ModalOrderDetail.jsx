import React from "react";
import {Modal} from "antd";
import './ModalOrderDetail.css';

export default function ModalOrderDetail({isOpenModalDetail, closeModalDetail, orderItems}){


    return(
        <Modal
            title="Order Items Detail"
            open={isOpenModalDetail}
            closable={true}
            onCancel={closeModalDetail}
            footer={null}
        >
            <div className="order-detail-container">
                <div className="order-item-container">

                </div>
            </div>

        </Modal>
    )
}