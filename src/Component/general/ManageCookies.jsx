import React from "react";

export const createToken =(loginData) =>{
    document.cookie = "username=" + loginData.userName + '; ' + 'expires=' + loginData.token;
    return "Hello";
}

export const getToken = () => {
    return null;
}

export const getUserName = () => {
    return null;
}

export const getRole = () => {
    return null;
}