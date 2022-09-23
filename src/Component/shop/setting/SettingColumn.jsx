import React, {useEffect, useState} from "react";
import 'antd/dist/antd.min.css';
import {Tree} from 'antd';
import './SettingColumn.css';
import axios from "axios";

const { TreeNode} = Tree;

export default function SettingColumn(){

    const [category, setCategory] = useState([{
        name: '',
        categoryId: 0
    }]);
    const [author, setAuthor] = useState([{
        authorName : '',
        authorID: 0
    }]);

    const update = () =>{
            axios.get('https://ecommerce-web0903.herokuapp.com/api/authors')
                .then((res) =>{
                    setAuthor([{
                        authorName : res.data.authorName,
                        authorID : res.data.authorID
                    }])
                    console.log(res)
                }).catch((err) => console.log(err))

            axios.get('https://ecommerce-web0903.herokuapp.com/api/categories')
                .then((res) => {
                    setCategory([{
                        name: res.data.name,
                        categoryId : res.data.categoryId
                    }])
                    console.log(res)
                }).catch((err) => console.log(err))
            console.log(author);
            console.log(category);
    }

    useEffect(() =>{
        if (category === null && author === null){
            update()
        }
    },[author])


    return(
        <div>
            <ul>
                <li>Category
                    <ul>
                        {category.map((item) =>
                            <li  key={item.categoryId} className="paddingcheck"><input type="checkbox"/>
                                {item.name}
                            </li>
                        )}
                    </ul>
                </li>
                <li>Author
                    <ul>
                        {author.map((item) =>
                            <li  key={item.authorID} className="paddingcheck"><input type="checkbox"/>
                                {item.authorName}
                            </li>
                        )}
                    </ul>
                </li>
            </ul>
        </div>
    )

}