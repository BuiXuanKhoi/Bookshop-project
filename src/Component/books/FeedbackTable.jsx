import React, {useEffect, useState} from "react";
import {Row, Col, Menu, Dropdown, Button, Input, Select, Rate, Pagination} from "antd";
import './FeedBackTable.css'
import {CaretDownOutlined} from "@ant-design/icons";
import {Option} from "antd/es/mentions";
import TextArea from "antd/es/input/TextArea";
import axios from "axios";

import ReviewSubmit from "./ReviewSubmit";

const customerReview = (props) =>{
    const fiveStar = props.feedbackRatingList.wonderful;
    const fourStar = props.feedbackRatingList.good;
    const threeStar = props.feedbackRatingList.normal;
    const twoStar =  props.feedbackRatingList.bad;
    const oneStar = props.feedbackRatingList.terrible;
    const totalFeedback = oneStar+twoStar+threeStar+fourStar+fiveStar;
    let a = document.getElementsByTagName("a");
    const handleOnClickSort = (values) =>{
        props.setMode(values.key)
    }
    const handleOnClickChangeNumber = (values) => {
        props.setSize(values.key);
        props.setDefaultPageSize(values.key);
        props.setShowNumber(values.key);
    }
    const displayOnClick = (values) =>{
        console.log(values.target)
    }
    const menu = (
        <Menu
            onClick={handleOnClickSort}
            items={[
                {
                    key: 'd',
                    label: (
                        <a target="_blank"  rel="noopener noreferrer" >
                            Sort by date: newest to oldest
                        </a>
                    ),
                },
                {
                    key: 'a',
                    label: (
                        <a target="_blank" rel="noopener noreferrer"    >
                            Sort by date: oldest to newest
                        </a>
                    ),
                },

            ]}
        />
    );

    const menuForNumber = (
        <Menu
            onClick={handleOnClickChangeNumber}
            items={[
                {
                    key: '10',
                    label: (
                        <a target="_blank" rel="noopener noreferrer" >
                            Show 10
                        </a>
                    ),
                },
                {
                    key: '20',
                    label: (
                        <a target="_blank" rel="noopener noreferrer" >
                            Show 20
                        </a>
                    ),
                },
                {
                    key: '30',
                    label: (
                        <a target="_blank" rel="noopener noreferrer">
                            Show 30
                        </a>
                    ),
                },
            ]}
        />
    );

    return (
            <Col span={24} >
                {/*--------------------------------------------------------------------------------*/}
                <Row>
                    <Col span={9} >
                        <p className="positionForChar" style={{marginLeft:"8%",fontSize:"2vw",fontWeight:"bolder"}}>Customer Reviews </p>
                    </Col>
                    <Col span={15}>
                        <p className="positionForChar" style={{color:"#A2B5BB",fontSize:"1.2vw",marginTop:"2.5%"}}>(Filtered by 5 star) </p>
                    </Col>

                </Row>
                {/*--------------------------------------------------------------------------------*/}
                <Row style={{fontSize:"3vw",fontWeight:"bolder"}}>
                    <Col span={3}>
                        <p className={"positionForChar"} style ={{marginLeft:"18%"}}>{Math.round(((fiveStar*5+fourStar*4+threeStar*3+twoStar*2+oneStar)/totalFeedback)*10)/10}</p>
                    </Col>
                    <Col span={21}>
                        <p className={"positionForChar"}>Star</p>
                    </Col>
                </Row>
                {/*--------------------------------------------------------------------------------*/}
                <Row style={{fontSize:"1vw"}}>
                    <Col span={24}>
                        <div style={{display:"flex"}}>
                            <p className={"positionForChar"} style={{marginLeft:"2.5%",fontWeight:"bolder",textDecoration:"underline"}}>
                                ({totalFeedback})
                            </p>
                            <div style={{display:"inline-block" ,marginLeft:"2%"}}>

                                <a onClick={displayOnClick} value={"5"}  className={"positionForChar"} style={{textDecoration:"underline"}}>
                                    5 star ({fiveStar}) |
                                </a>

                                <a onClick={displayOnClick} value={"4"} className={"positionForChar"} style={{textDecoration:"underline"}}>
                                    4 star ({fourStar})|
                                </a>

                                <a onClick={displayOnClick} value={"3"} className={"positionForChar"} style={{textDecoration:"underline"}}>
                                    3 star ({threeStar}) |
                                </a>

                                <a onClick={displayOnClick} value={"2"} className={"positionForChar"} style={{textDecoration:"underline"}}>
                                    2 star ({twoStar}) |
                                </a>

                                <a onClick={displayOnClick} value={"1"} className={"positionForChar"} style={{textDecoration:"underline"}}>
                                    1 star ({oneStar}) |
                                </a>
                            </div>
                        </div>
                    </Col>
                </Row>
                {/*--------------------------------------------------------------------------------*/}
                <Row style={{marginTop:"2%"}}>
                    <Col span={12} style={{display:"flex",alignItems:"center",justifyContent:"left"}}>
                        <p className={"positionForChar"} style={{fontSize:"1.2vw",marginLeft:"5%"}}>
                            Showing 1-{props.size} of number reviews
                        </p>
                    </Col>

                    <Col span={6}>
                        <Dropdown overlay={menu} placement={"bottomLeft"}>
                            <Button className={"editButton"} >
                                Sort by on sale <CaretDownOutlined/>
                            </Button>
                        </Dropdown>
                    </Col>
                    <Col span={6}>
                        <Dropdown overlay={menuForNumber} placement={"bottomLeft"} >
                            <Button className={"editButton"} >
                                Show {props.showNumber} <CaretDownOutlined />
                            </Button>
                        </Dropdown>
                    </Col>
                </Row>

            </Col>
    );
}

const reviewTitle = (ratingPoint, comment, title,userName, createdDay) => {
    return (
        <Col span={24}>

            {/*--------------------------------------------------------------------------------*/}
            <Row style={{paddingBlock:"0.25em",marginLeft:"2%",display:"flex",justifyContent:"left",textAlign:"center",alignContent:"center"}}>
                <div className={"container-for-title"}>
                    <p className={"positionForChar"} style={{padding:"1px",fontSize:"2vw",fontWeight:"bolder"}}>{title}</p>
                    <p className={"positionForChar"} style={{display:"flex",justifyContent:"left",textAlign:"left",padding:"1px"}}>| {ratingPoint}</p>
                </div>
            </Row>
            {/*--------------------------------------------------------------------------------*/}
            <Row style={{paddingBlock:"0.25em"}}>
                <Col span={23}>
                    <p className={"positionForChar"} style={{fontSize:"1.2vw",marginLeft:"2%"}}>
                        {comment}
                    </p>
                </Col>

            </Row>
            {/*--------------------------------------------------------------------------------*/}
            <Row style={{padding:"0.1em"}}>
                <div className={"container"} >
                    <p className={"positionForCharComment"} >{userName}</p>
                    <p className={"positionForCharComment"}> - </p>
                    <p className={"positionForCharComment"}> {createdDay} </p>
                </div>
            </Row>

            <Row style={{marginTop:"0"}}>
                <Col span={22}  style={{marginLeft:"2%",paddingBlock:"0.25em",borderBottomStyle:"ridge",borderColor:"gray"}}>
                </Col>
            </Row>
        </Col>
    );
}


export default function FeedbackTable ({bookID,config}) {
    const [page,setPage] = useState(0);
    const [size,setSize] = useState(10);
    const [mode, setMode] = useState('a');
    const [filter,setFilter] = useState(0);
    const [pageBook, setPageBook] = useState([]);
    const [currentPage,setCurrentPage] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [defaultPageSize,setDefaultPageSize] = useState(10);
    const [showNumber, setShowNumber] = useState(10);
    const [feedbackRatingList, setFeedbackRatingList] = useState([])
    const initReviewFeedback = () => {

        axios.get("https://ecommerce-web0903.herokuapp.com/api/books/"+bookID+"/feedbacks?page="+page+"&size="+size+"&mode="+mode+"&filter="+filter)
            .then((response)=>{
                console.log(response.data)
                setPageBook(response.data.content);
                setCurrentPage(response.data.number);
                setTotalElements(response.data.totalElements);
                setDefaultPageSize(response.data.pageable.pageSize)
            })
            .catch((error) =>{
                console.log(error);
            })
    }

    const handleOnChange = (m) =>{
        setPage(m-1);
    }
    const getFeebackQuantityList = () => {
        axios.get("https://ecommerce-web0903.herokuapp.com/api/books/"+bookID+"/feedbacks/rate")
            .then((res) =>{
                setFeedbackRatingList(res.data)
            })
            .catch((error) => {
                console.log(error);
            })
    }

    useEffect( ()=>{
        initReviewFeedback()
    },[page,mode,size]);

    useEffect(()=>{
        getFeebackQuantityList();

    },[])

    return (
            <>
                <Row className={"customerReviewList"} >
                    <Col span={24}>
                        {/*--------------------------------------------------------------------------------*/}
                        <Row style={{paddingTop:"5%"}}>
                            {customerReview({setMode,mode,size,setSize,defaultPageSize,setDefaultPageSize,setShowNumber,showNumber,feedbackRatingList,setFilter})}
                        </Row>
                        {/*--------------------------------------------------------------------------------*/}
                        { pageBook.map((feedback) =>
                            <Row style={{paddingBlock:"1.0em"}} key={bookID}>
                                {reviewTitle(feedback.ratingPoint, feedback.comment,feedback.title,feedback.userName,feedback.createDay)}
                            </Row>
                        )}
                        {/*--------------------------------------------------------------------------------*/}
                        <Row>
                            <Col span={24} style={{display:"flex",justifyContent:"center",alignItems:"center"}}>
                                <Pagination onChange={handleOnChange} defaultCurrent={currentPage} pageSize={defaultPageSize} style={{marginTop:"5%"}} total={totalElements}/>
                            </Col>
                        </Row>
                    </Col>
                </Row>
            </>
        )
}