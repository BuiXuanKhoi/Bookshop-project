import Home from "../Component/home/Home";
import Login from "../Component/Auth/login/Login";
import SignUp from "../Component/Auth/Signup/SignUp";
import Shop from "../Component/shop/Shop";
import BookDetail from "../Component/books/BookDetail";

export const UserRoute = [

    {
        path: '/shop',
        component: <Shop/>,
        title: 'Shop'
    },
    {
        path: '/login',
        component: <Login/>,
        title : 'Login'
    },
    {
        path: '/signup',
        component: <SignUp/>,
        title: 'Sign up'
    },
    {
        path: '/',
        component: <Home/>,
        title: 'Home'
    },
    {
        path: '/books/:id',
        component: <BookDetail/>,
        title: 'Book Detail'
    }
]