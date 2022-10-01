import React, {useEffect, useState} from "react";
import {useParams} from 'react-router-dom';
import axios from "axios";
import {Button, Col, Comment, Empty, Input, Modal, Rate, Row} from "antd";
import '../shop/Shop.css';
import './BookDetail.css'
import FeedbackTable from "./FeedbackTable";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser} from "@fortawesome/free-solid-svg-icons";
import QuantityButton from "../general/QuantityButton";
import {getCookie} from "react-use-cookie";
import {useNavigate} from "react-router";
import {useCookies} from "react-cookie";
import './FeedBackTable.css'
import TextArea from "antd/es/input/TextArea";
import {MinusOutlined,PlusOutlined} from "@ant-design/icons";

