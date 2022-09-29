import React, {useState} from "react";
import {Row, Col, Menu, Dropdown, Button, Input, Select, Rate} from "antd";
import './FeedBackTable.css'
import {CaretDownOutlined} from "@ant-design/icons";
import {Option} from "antd/es/mentions";
import TextArea from "antd/es/input/TextArea";

const menu = (
    <Menu
        items={[
            {
                key: '1',
                label: (
                    <a target="_blank" rel="noopener noreferrer" href="https://www.antgroup.com">
                        Sort by date: newest to oldest
                    </a>
                ),
            },
            {
                key: '2',
                label: (
                    <a target="_blank" rel="noopener noreferrer" href="https://www.aliyun.com">
                        Sort by date: oldest to newest
                    </a>
                ),
            },

        ]}
    />
);

const customerReview = () =>{
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
                        <p className={"positionForChar"} style ={{marginLeft:"18%"}}>4.6</p>
                    </Col>
                    <Col span={21}>
                        <p className={"positionForChar"}>Star</p>
                    </Col>
                </Row>
                {/*--------------------------------------------------------------------------------*/}
                <Row style={{fontSize:"1vw"}}>
                    <Col  span={3} >
                        <p className={"positionForChar"} style={{marginLeft:"20%",fontWeight:"bolder",textDecoration:"underline"}}>
                            Point
                        </p>
                    </Col>

                    <Col span={2.5} >
                        <p className={"positionForChar"} style={{textDecoration:"underline"}}>
                            5 star (200) |
                        </p>
                    </Col>

                    <Col span={2.5} >
                        <p className={"positionForChar"} style={{textDecoration:"underline"}}>
                            4 star (100) |
                        </p>
                    </Col>

                    <Col span={2.5} >
                        <p className={"positionForChar"} style={{textDecoration:"underline"}}>
                            3 star (20) |
                        </p>
                    </Col>

                    <Col span={2.5} >
                        <p className={"positionForChar"} style={{textDecoration:"underline"}}>
                            2 star (10) |
                        </p>
                    </Col>

                    <Col span={11}>
                        <p className={"positionForChar"} style={{textDecoration:"underline"}}>
                            1 star (10) |
                        </p>
                    </Col>
                </Row>
                {/*--------------------------------------------------------------------------------*/}
                <Row style={{marginTop:"2%"}}>
                    <Col span={12} style={{display:"flex",alignItems:"center",justifyContent:"left"}}>
                        <p className={"positionForChar"} style={{fontSize:"1.2vw",marginLeft:"5%"}}>
                            Showing 1-12 of number reviews
                        </p>
                    </Col>

                    <Col span={6} >
                        <Dropdown overlay={menu} placement={"bottomLeft"}>
                            <Button className={"editButton"} >
                                Sort by on sale <CaretDownOutlined/>
                            </Button>
                        </Dropdown>
                    </Col>
                    <Col span={6} >
                        <Dropdown overlay={menu} placement={"bottomLeft"} >
                            <Button className={"editButton"} >
                                Show 20 <CaretDownOutlined />
                            </Button>
                        </Dropdown>
                    </Col>
                </Row>

            </Col>
    );
}

const reviewTitle = () => {
    return (
        <Col span={24}>
            {/*--------------------------------------------------------------------------------*/}
            <Row style={{paddingBlock:"0.25em"}}>
                <Col span={6} >
                    <p className={"positionForChar"} style={{marginLeft:"8%",fontSize:"2vw",fontWeight:"bolder"}}>Review Title</p>
                </Col>
                <Col span={18}>
                    <p className={"positionForChar"} style={{marginTop:"1.7%"}}>| 5 star</p>
                </Col>
            </Row>
            {/*--------------------------------------------------------------------------------*/}
            <Row style={{paddingBlock:"0.25em"}}>
                <Col span={23}>
                    <p className={"positionForChar"} style={{fontSize:"1.2vw",marginLeft:"2%"}}>
                        Such an incredibility complex story! I had to buy it because there was a waiting list of 30+ at the local library for this book.
                        Thrilled that i made the purchase.
                    </p>
                </Col>

            </Row>
            {/*--------------------------------------------------------------------------------*/}
            <Row style={{paddingBlock:"0.25em"}}>
                <p className={"positionForChar"} style={{fontSize:"1vw",marginLeft:"2%"}}>
                    April 12,2021
                </p>
            </Row>
            <Row>
                <Col span={22}  style={{marginLeft:"2%",paddingBlock:"0.25em",borderBottomStyle:"ridge",borderColor:"gray"}}>

                </Col>
            </Row>
        </Col>
    );
}

const customerReviewPost = (props) =>{

    return (

        <Col span={24}>
            <Row >
                <p className="positionForChar" style={{marginLeft:"2%",fontSize:"2vw",fontWeight:"bolder"}}> Write a Review</p>
            </Row>
            {/*--------------------------------------------------------------------------------*/}
            <Row>
                <Col span={24} style={{borderStyle:"ridge",borderColor:"#F6F6F6"}}>

                </Col>
            </Row>

            {/*--------------------------------------------------------------------------------*/}
            <Row style={{marginTop:"5%"}}>
                <p className="positionForChar" style={{marginLeft:"4%",fontSize:"1.2vw"}}>Add a title</p>
            </Row>
            {/*--------------------------------------------------------------------------------*/}
            <Row style={{paddingBlock:"0.25em"}}>
                <Col offset={1} span={22}>
                    <Input style={{height:"3vw"}}></Input>
                </Col>
            </Row>

            {/*--------------------------------------------------------------------------------*/}
            <Row style={{marginTop:"10%"}}>
                <p className="positionForChar" style={{marginLeft:"4%",fontSize:"1.2vw"}}>
                    Details please! Your review helps other shoppers
                </p>
            </Row>
            {/*--------------------------------------------------------------------------------*/}
            <Row style={{paddingBlock:"0.25em"}}>
                <Col offset={1} span={22}>
                    <TextArea cols={30} rows={5}></TextArea>
                </Col>
            </Row>

            {/*--------------------------------------------------------------------------------*/}
            <Row style={{marginTop:"10%"}}>
                <p className="positionForChar" style={{marginLeft:"4%",fontSize:"1.2vw"}}>
                    Select a rating star
                </p>
            </Row>
            {/*--------------------------------------------------------------------------------*/}
            <Row style={{paddingBlock:"0.25em"}}>
                <Col span={24}>
                    <span style={{marginLeft:"5%"}}>
                        <Rate tooltips={props.desc} onChange={props.setRatingPoint} value={props.ratingPoint}/>
                        {props.ratingPoint ? <span className="ant-rate-text">{props.desc[props.ratingPoint - 1]}</span>  : ''}
                    </span>
                </Col>
            </Row>

            {/*--------------------------------------------------------------------------------*/}
            <Row>
                <Col span={24} style={{borderStyle:"ridge",marginTop:"5%",marginBottom:"2%",borderColor:"#F6F6F6"}}>
                </Col>
            </Row>
            {/*--------------------------------------------------------------------------------*/}
            <Row style={{marginBottom:"2%"}}>
                <Col span={20} offset={2}>
                    <Button style={{background:"#CFD2CF",width:"100%",paddingBottom:"10%"}}>
                        <p className={"positionForChar"} style={{fontWeight:"bolder",fontSize:"1.5vw",marginBottom:"50%"}}>
                            Submit Review
                        </p>
                    </Button>
                </Col>
            </Row>
        </Col>
    );
}

export default function FeedbackTable () {
    const [ratingPoint, setRatingPoint] = useState(3);
    const desc = ['terrible', 'bad', 'normal', 'good', 'wonderful'];

    return (
        <Row style={{marginTop:"3%"}}>
            <Row className={"customerReviewList"} >
                <Col span={24}>
                    <Row style={{marginTop:"4%"}}>
                        {customerReview()}
                    </Row>

                    <Row style={{paddingBlock:"1.5em",}}>
                        {reviewTitle()}
                    </Row>

                    <Row style={{paddingBlock:"1.5em",}}>
                        {reviewTitle()}
                    </Row>

                    <Row style={{paddingBlock:"1.5em",}}>
                        {reviewTitle()}
                    </Row>

                    <Row style={{paddingBlock:"1.5em",}}>
                        {reviewTitle(ratingPoint)}
                    </Row>

                </Col>
            </Row>
            <Row className={"customerReviewPost"}>
                {customerReviewPost({ratingPoint,setRatingPoint,desc})}
            </Row>
        </Row>
        )
}