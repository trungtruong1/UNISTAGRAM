
import { Container } from "react-bootstrap";
import { Col, Row } from 'react-bootstrap';
import NavBar from '../components/navbar';
import Profile from '../components/profile';
import Ranking from '../components/ranking';
import SubmissionList from "../components/submission_list";
import '../App.css';
import SideBar from "../components/SideBar";
import Chat from "../components/Chat";
import { checkLogin } from "../ultils/checkLogin";
import { useEffect } from "react";


function ChatPage() {
    const testToken = checkLogin();
    if(testToken === null) {
        window.location.href = "/signin";
    }

    return (
        <>
            <NavBar/>

            <div className='home'>
                <div className='containerChat'>
                    <SideBar/>
                    <Chat/>
                </div>
            </div>
            
        </>
    )
}

export default ChatPage;