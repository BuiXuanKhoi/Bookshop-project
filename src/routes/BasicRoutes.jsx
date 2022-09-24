import Login from "../Component/Auth/login/Login";
import SignUp from "../Component/Auth/Signup/SignUp";
import Home from "../Component/home/Home";
import Shop from "../Component/shop/Shop";

export const BasicRoutes = (routes) =>  [
    routes[0],
    routes[1],
    routes[2],
    {
        path: '/',
        component: <Home/>,
        title: 'Home'
    },
    {
        path: '/shop',
        component: <Shop/>,
        title: 'Shop'
    }
]