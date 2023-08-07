import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Modal from 'react-bootstrap/Modal';
import { Col, ListGroup, ListGroupItem, Row, Form, Footer } from 'react-bootstrap';
import NavBar from '../components/navbar';
import useToken from '../useTokens';
import { useEffect, useRef, useState } from 'react';
import Board from '../components/chess_board';
import { CompatClient, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { checkLogin } from '../ultils/checkLogin';


function Matching() {
    const testToken = checkLogin();
    if(testToken === null) {
        window.location.href = "/signin";
    }

    const [showFindMatch, setshowFindMatch] = useState(false);

    const userToken = checkLogin();
  
    const stomp = useRef({});

    useEffect(() => {
        const socket = new SockJS("http://localhost:8080/ws");
        stomp.client = Stomp.over(socket);
        stomp.client.connect({}, (frame) => {
          console.log('Connected: ' + frame);
          stomp.client.subscribe('/sub/matching/user/' + userToken.id, function (greeting) {
            window.alert("Matched successfully! You will be redirected to chat app");
            window.location.href = "/chat";
          });
        });
    }, []);

    const handleFindingMatch = () => {
        stomp.client.send("/pub/matching/queue", {}, JSON.stringify({
            userId: userToken.id,
            cancel: false,
        }));
        setshowFindMatch(true)
    };

    const handleCancelFinding = () => {
        stomp.client.send("/pub/matching/queue", {}, JSON.stringify({
            userId: userToken.id,
            cancel: true,
        }));
        setshowFindMatch(false)
    };


    return (
        <>
            <Modal show={showFindMatch} onHide={handleCancelFinding}>
                <Modal.Header closeButton>
                    <Modal.Title>Matching...</Modal.Title>
                </Modal.Header>
                <Modal.Footer>
                    <Button variant="danger" onClick={handleCancelFinding}>
                        Cancel
                    </Button>
                </Modal.Footer>
            </Modal>

            <NavBar/>

            <br/>

            <Container>
                <Row style={{paddingTop: "150px"}}>
                    <center>
                        <h1>Press to match...!</h1>
                        <br/>
                        <br/>
                        <Card style={{ width: "40%", height: "5rem"}} onClick={handleFindingMatch}>
                            <Button variant="success" style={{height: "100%"}}><b>Random matching</b></Button>
                        </Card>
                    </center>
                </Row>
                
            </Container>
        </>
    );
}


export default Matching;