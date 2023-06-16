
import { Container } from "react-bootstrap";
import { Col, Row } from 'react-bootstrap';
import NavBar from '../components/navbar';
import Profile from '../components/profile';
import Ranking from '../components/ranking';
import SubmissionList from "../components/submission_list";
import { ChatEngine } from 'react-chat-engine';
import '../App.css';
import SideBar from "../components/SideBar";
import Chat from "../components/Chat";


function ChatPage() {
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