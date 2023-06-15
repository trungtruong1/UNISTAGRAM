
import { Container } from "react-bootstrap";
import { Col, Row } from 'react-bootstrap';
import NavBar from '../components/navbar';
import Profile from '../components/profile';
import Ranking from '../components/ranking';
import SubmissionList from "../components/submission_list";


function Watch() {
    return (
        <>
            <NavBar/>

            <br/>

            <Container>
                <Row>
                    <Col sm={1}></Col>
                    <Col sm={7}>
                        <center>
                            <h1>Your submissions</h1>
                        </center>
                        <SubmissionList problem="000 - SECRET OF THE UNIVERSE" verdict="Accepted" coins="3"/>
                        <SubmissionList problem="005 - A VERY HARD PROBLEM" verdict="Accepted" coins="300"/>
                        <SubmissionList problem="001 - THE MOST BASIC OPERATION" verdict="Accepted" coins="3"/>
                        <SubmissionList problem="003 - MULTIPLY!!!" verdict="Accepted" coins="3"/>
                        <SubmissionList problem="002 - THE SECOND MOST BASIC OPERATION" verdict="Accepted" coins="3"/>
                        <SubmissionList problem="004 - DIVIDE BUT NOT CONQUER" verdict="Accepted" coins="3"/>
                    </Col>
                    <Col sm={1}></Col>

                    <Col sm={3}>
                        <Profile/>
                        <Ranking/>
                    </Col>
                </Row>
                
            </Container>
        </>
    )
}

export default Watch;