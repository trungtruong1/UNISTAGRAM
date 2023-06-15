import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Modal from 'react-bootstrap/Modal';
import { Link, useParams } from "react-router-dom";
import { Col, ListGroup, ListGroupItem, Row, Form, Footer } from 'react-bootstrap';
import NavBar from '../components/navbar';
import Profile from '../components/profile';
import ProblemList from '../components/problem_list';
import Ranking from '../components/ranking';
import useToken from '../useTokens';
import { useEffect, useRef, useState } from 'react';
import Board from '../components/chess_board';


function ViewPlayOption() {

    const { token, setToken } = useToken();

    const [showCustom, setshowCustom] = useState(false);
    const [showFindMatch, setshowFindMatch] = useState(false);
    const [showBot, setshowBot] = useState(false);
    const [matchID, setMatchID] = useState(0);
    const socket = useRef({});

    const handleCustom = () => setshowCustom(true);
    const handleCancelCustom = () => setshowCustom(false);

    const handleFindingMatch = () => setshowFindMatch(true);
    const handleCancelFinding = () => setshowFindMatch(false);

    const handleBot = () => setshowBot(true);
    const handleCancelBot = () => setshowBot(false);

    const handleCreateRoom = async (e) => {
        e.preventDefault();
        const res = await fetch(`http://localhost:8000/api/match/add/`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: token.username
            })
        });
        const data = await res.json();

        if(data.status === "failed") {
            alert(data.reason);
            window.location.reload();
            return;
        }
        alert("Successful");
        window.location.reload();
    }

    const handleMatchIDChange = (e) => setMatchID(e.target.value);

    const handleJoinRoom = async (e) => {
        e.preventDefault();
        console.log(token);
        socket.join = new WebSocket(`ws://localhost:8000/ws/join/${matchID}/`);
        socket.join.onmessage = function(e) {
            const data = JSON.parse(e.data);
            if(data.player === token.username) return;
            window.location.reload();
        };
    
        socket.join.onclose = function(e) {
            console.error('Chat socket closed unexpectedly');
        };

        const res = await fetch(`http://localhost:8000/api/match/join/`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: token.username,
                password: token.password,
                match_id: matchID
            })
        });
        const data = await res.json();

        if(data.status === "failed") {
            alert(data.reason);
            window.location.reload();
            return;
        }

        socket.join.send(JSON.stringify({
            'player': token.username
        }));

        alert("Successful");
        window.location.reload();
    }

    return (
        <>
            <Modal show={showCustom} onHide={handleCancelCustom}>
                <Modal.Header closeButton>
                    <Modal.Title>Custom match</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Row>
                        <Col sm={6}>
                            <Form onSubmit={handleCreateRoom}>
                                <center>
                                    <Button style={{marginTop: "10%"}} variant="primary" type="submit">
                                        Create custom room
                                    </Button>
                                </center>
                            </Form>
                        </Col>

                        <Col sm={6}>
                            <Form onSubmit={handleJoinRoom}>
                                <Form.Group className="mb-3" controlId="formBasicUsername">
                                    <Form.Control onChange={handleMatchIDChange} type="number" placeholder="Enter ID" />
                                </Form.Group>
                                <center>
                                    <Button variant="primary" type="submit">
                                        Join
                                    </Button>
                                </center>
                            </Form>
                        </Col>
                        
                    </Row>
                </Modal.Body>
            </Modal>

            <Modal show={showFindMatch} onHide={handleCancelFinding}>
                <Modal.Header closeButton>
                    <Modal.Title>Finding a match...</Modal.Title>
                </Modal.Header>
                <Modal.Footer>
                    <Button variant="danger" onClick={handleCancelFinding}>
                        Cancel
                    </Button>
                </Modal.Footer>
            </Modal>

            <Modal show={showBot} onHide={handleCancelBot}>
                <Modal.Header closeButton>
                    <Modal.Title>Play with bot</Modal.Title>
                </Modal.Header>
                <Modal.Footer>
                    <Button variant="danger" onClick={handleCancelBot}>
                        Cancel
                    </Button>
                </Modal.Footer>
            </Modal>

            <NavBar/>

            <br/>

            <Container>
                <Row>
                    <Col sm={9}>
                        <center>
                            <h1>Play</h1>
                        </center>
                        <Row>
                            <Col sm={4}>
                                <center>
                                <Card style={{ width: "40%", height: "5rem" }} onClick={handleCustom}>
                                    <Button variant="success" style={{height: "100%"}}><b>Custom match</b></Button>
                                </Card>
                                </center>
                            </Col>
                            <Col sm={4}>
                                <center>
                                <Card style={{ width: "40%", height: "5rem" }} onClick={handleFindingMatch}>
                                    <Button variant="success" style={{height: "100%"}}><b>Random matching</b></Button>
                                </Card>
                                </center>
                            </Col>
                            <Col sm={4}>
                                <center>
                                <Card style={{ width: "40%", height: "5rem" }} onClick={handleBot}>
                                    <Button variant="success" style={{height: "100%"}}><b>Play with bots</b></Button>
                                </Card>
                                </center>
                            </Col>
                        </Row>
                    </Col>

                    <Col sm={3}>
                        <Profile/>
                        <Ranking/>
                    </Col>
                </Row>
                
            </Container>
        </>
    );
}

function ShowBoard({userInfo}) {
    
    const GACHA_PRICE = 7;
    const { token, setToken } = useToken();
    const [info, setInfo] = useState({});
    const [matchInfo, setMatchInfo] = useState({my_pocket: {}});
    const socket = useRef({});
    const randomPiece = useRef({});

    let selectPieceState = {};
    [selectPieceState.n, selectPieceState.setn] = useState({state: false, number: 0});
    [selectPieceState.b, selectPieceState.setb] = useState({state: false, number: 0});
    [selectPieceState.r, selectPieceState.setr] = useState({state: false, number: 0});
    [selectPieceState.q, selectPieceState.setq] = useState({state: false, number: 0});

    useEffect(() => {
        let ignore = false;
        async function fetchData() {
            if(!token) return;
            if(ignore) return;
            if(!userInfo.in_game) return;

            const match = await fetch(`http://localhost:8000/api/match/${userInfo.in_game}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const match_data = await match.json();
            const white_player = await (await fetch(match_data.white_player, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })).json();

            const black_player = (match_data.black_player)? await (await fetch(match_data.black_player, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })).json() : null;

            setMatchInfo({
                id: match_data.id,
                fen: match_data.fen,
                white_player: white_player.username,
                white_points: match_data.white_points,
                white_elo: white_player.elo,
                black_player: (black_player)? black_player.username : null,
                black_points: match_data.black_points,
                black_elo: (black_player)? black_player.elo : null,
                my_points: (token.username === white_player.username)? match_data.white_points : match_data.black_points,
                my_pocket: (token.username === white_player.username)? match_data.white_pocket : match_data.black_pocket,
            });

            const my_pocket = (token.username === white_player.username)? match_data.white_pocket : match_data.black_pocket;
            for(const key of Object.keys(my_pocket)) {
                selectPieceState[`set${key}`]({state: my_pocket[key] === 0, number: my_pocket[key]});
            }

            // Initialize WebSocket

            socket.resign = new WebSocket(`ws://localhost:8000/ws/resign/${match_data.id}/`);
            socket.join = new WebSocket(`ws://localhost:8000/ws/join/${match_data.id}/`);
            socket.roll = new WebSocket(`ws://localhost:8000/ws/roll/${match_data.id}/`);

            console.log(socket);

            // Player joins the match
            socket.join.onmessage = function(e) {
                const data = JSON.parse(e.data);
                if(data.player === token.username) return;
                window.location.reload();
            };
        
            socket.join.onclose = function(e) {
                console.error('Chat socket closed unexpectedly');
            };

            // Player resigns
            socket.resign.onmessage = function(e) {
                const data = JSON.parse(e.data);
                if(data.player === token.username) return;
                alert("Your opponent resigns");
                window.location.reload();
            };
        
            socket.resign.onclose = function(e) {
                console.error('Chat socket closed unexpectedly');
            };

            // Player rolls a random piece
            socket.roll.onmessage = function(e) {
                const data = JSON.parse(e.data);
                if(data.status === "failed") return;
                if(data.player === token.username) {
                    document.getElementById("my_points").textContent = `Your Points: ${data.my_points}`;
                    matchInfo.my_points = data.my_points;
                    
                    selectPieceState[`${data.piece}`].number = data.pocket;
                    selectPieceState[`${data.piece}`].state = false;
                    const new_state = {state: false, number: selectPieceState[`${data.piece}`].number};
                    console.log(new_state);
                    selectPieceState[`set${data.piece}`](new_state);

                    console.log(selectPieceState);

                } else {
                    console.log("Your opponent rolls a random piece");
                }
            };
        
            socket.roll.onclose = function(e) {
                console.error('Chat socket closed unexpectedly');
            };
            
        }

        fetchData();
        return () => { ignore = true; }        
    }, []);
    

    const resign = async (e) => {
        const res = await fetch(`http://localhost:8000/api/match/resign/`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: token.username,
                password: token.password,
                match_id: matchInfo.id
            })
        });
        const data = await res.json();

        if(data.status === "failed") {
            alert(data.reason);
            window.location.reload();
            return;
        }
        socket.resign.send(JSON.stringify({
            'player': token.username
        }));
        alert("Successful");
        window.location.reload();
    }

    const rollRandomPiece = (e) => {
        if(matchInfo.my_points < GACHA_PRICE) return;
        socket.roll.send(JSON.stringify({
            'player': token.username
        }));
    }

    const handle_pick_piece = (e) => {
        randomPiece.selected_piece = e.target.getAttribute("data-piece");
        document.getElementById("selected_piece").textContent = `You are picking ${randomPiece.selected_piece}`;
    }

    const handle_cancel_select = (e) => {
        randomPiece.selected_piece = null;
        document.getElementById("selected_piece").textContent = `You are not picking`;
    }

    return (
        <>
            <NavBar/>

            <br/>

            <Container>
                <Row>
                    <Col id="board_area" sm={8}>
                        {
                            (matchInfo.black_player)? 
                                <Board selectPieceState={selectPieceState} matchInfo={matchInfo} userInfo={userInfo} randomPiece={randomPiece}/>
                            :
                                <center>
                                    <h3>Waiting for another player...</h3>
                                </center>
                        }
                    </Col>

                    <Col sm={4}>
                        <Card style={{ width: "70%", margin: '10px' }} >
                            <Card.Header>
                                Match ID: {matchInfo.id}
                                <Button variant="danger" style={{ float: "right"}} onClick={resign}><b>Resign</b></Button>
                            </Card.Header>
                            <Card.Body>
                                <Card.Text> White player: {matchInfo.white_player} ({matchInfo.white_elo})</Card.Text>
                                <Card.Text> Black player: {matchInfo.black_player} ({matchInfo.black_elo})</Card.Text> 
                                <Card.Text id="status"> Status: white to move </Card.Text> 
                                <center>
                                    <Card.Text id="my_points"> Your Points: {matchInfo.my_points} </Card.Text>
                                    <Button id="roll_button" onClick={rollRandomPiece}>Roll</Button>
                                </center>
                                <br></br>
                                <Card.Text style={{ fontSize: "small" }}> Win (+102) / Lose (-13) / Draw (-1) </Card.Text> 
                            </Card.Body>
                        </Card>

                        <Card style={{ width: "70%", height: "20rem", margin: '10px' }} >
                            <Card.Header>
                                <Row>
                                <Col sm={8} id="selected_piece">
                                    You are not picking
                                </Col>
                                <Col sm={4}>
                                    <Button variant="danger" style={{ float: "right"}} onClick={handle_cancel_select}><b>Cancel</b></Button>
                                </Col>
                                </Row>
                                
                            </Card.Header>
                            <Card.Body style={{ overflow: "auto" }}>
                                {
                                    Object.keys(matchInfo.my_pocket).map((key, id) => {
                                        return (
                                            <Card.Text key={id}>
                                                <Button data-piece={key} data-number={selectPieceState[`${key}`].number} disabled={selectPieceState[`${key}`].state} onClick={handle_pick_piece}>
                                                    {key}: {selectPieceState[`${key}`].number}
                                                </Button>
                                            </Card.Text>
                                        )
                                    })
                                }
                                <Card.Text id="roll status"></Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
                
            </Container>
        </>
    )
}


function Play() {

    const { token, setToken } = useToken();
    const [info, setInfo] = useState({});
    const [matchInfo, setMatchInfo] = useState({});

    useEffect(() => {
        let ignore = false;

        async function fetchData() {
            if(!token) return;
            if(ignore) return;
            const user = await fetch(`http://localhost:8000/api/user/getinfo/?username=${token.username}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const user_data = await user.json();
            setInfo({
                username: user_data.username,
                in_game: user_data.in_game,
                number_of_matches: user_data.number_of_matches,
                elo: user_data.elo
            });
        }

        fetchData();
        return () => { ignore = true; }        
    }, []);

    return (
        <div>
            {
                (info.in_game)? 
                    <ShowBoard userInfo={info}/>
                :
                    <ViewPlayOption/>
            }
        </div>
    )
    
}

export default Play;