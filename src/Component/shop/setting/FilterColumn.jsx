import React, {useEffect, useState} from "react";
import 'antd/dist/antd.min.css';
import {Tree} from 'antd';
import './SettingColumn.css';
import axios from "axios";
import GeneralFunction from "../../../GeneralFunction";
import Checkbox from "antd/es/checkbox/Checkbox";
import '../Shop.css';

export default function FilterColumn({changeAuthorFilter, changeCategoryFilter}){

    const [category, setCategory] = useState([{
        name: '',
        categoryId: 0
    }]);
    const [author, setAuthor] = useState([{
        authorName : '',
        authorID: 0
    }]);

    const updateListAuthor = () => {
        axios.get('https://ecommerce-web0903.herokuapp.com/api/authors')
            .then((res) =>{
                setAuthor(res.data)
            }).catch((err) => console.log(err))
    }

    const updateListCategory = () => {
        axios.get('https://ecommerce-web0903.herokuapp.com/api/categories')
            .then((res) => {
                setCategory(res.data)
            }).catch((err) => console.log(err))
    }
    const update = () =>{
            updateListAuthor();
            updateListCategory();
    }

    useEffect(() =>{
            update();
    },[])


    return(
        <div className={'display'}>
            <ul>
                <li className={'select-box'}>Category
                    <ul>
                        {category.map((item) =>
                            <li  key={item.categoryId} className="paddingcheck">
                                <Checkbox value={item.categoryId} onChange={changeCategoryFilter}>
                                    {item.name}
                                </Checkbox>

                            </li>
                        )}
                    </ul>
                </li>
                <li className={'paddingItem'}  >Author
                    <ul>
                        {author.map((item) =>
                            <li  key={item.authorID} className="paddingcheck">
                                <Checkbox value={item.authorID} onChange={changeAuthorFilter}>{item.authorName}</Checkbox>
                            </li>
                        )}
                    </ul>
                </li>
            </ul>
        </div>
    )

}

// <input value={item.authorID} type="checkbox" onChange={updateAuthor}/>
// {item.authorName}