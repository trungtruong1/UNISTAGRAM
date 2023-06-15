import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import './index.css';
import reportWebVitals from './reportWebVitals';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import App from './App';
import SignIn from './pages/signin';
import SignUp from './pages/signup';
import Play from './pages/play';
import Watch from './pages/watch';
import Leaderboard from './pages/leaderboard';
import useToken from './useTokens';

function Index() {
    const { token, setToken } = useToken();
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App />} />
                <Route path="play" element={<Play />} />
                <Route path="/watch/" element={<Watch />} />
                <Route path="/watch/:pcode" element={<Watch />} />
                <Route path="/leaderboard" element={<Leaderboard />} />
                <Route path="/signin" element={<SignIn setToken={setToken}/>} />
                <Route path="/signup" element={<SignUp />} />
            </Routes>
        </BrowserRouter>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    // <React.StrictMode>
        <Index />
    // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
