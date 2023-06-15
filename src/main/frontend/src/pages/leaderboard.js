
import { useEffect, useState } from "react";
import { Container, Spinner } from "react-bootstrap";
import { Col, Row, Table } from 'react-bootstrap';
import NavBar from '../components/navbar';
import Profile from '../components/profile';
import Ranking from '../components/ranking';

function Leaderboard() {

    const [scoreboard, setScoreboard] = useState([]);

    useEffect(() => {
        let ignore = false;

        async function fetchData() {
            if(ignore) return;
            const res = await fetch(`http://localhost:8000/api/user`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await res.json();
            console.log(data);
            data.sort();

            setScoreboard(data);
        }

        fetchData();
        return () => { ignore = true; }        
    }, []);

    // console.log(data);

    // if(!data.accounts) return false;

    // let i = 1;
    // let leaderboard = data.accounts.map((acc) => {
    //     i++;
    //     return (
    //         <tr>
    //             <td>{i}</td>
    //             <td>Admin</td>
    //             <td>3</td>
    //             <td>309</td>
    //         </tr>
    //     )
    // });

    return (
        <>
            <NavBar/>

            <br/>

            <Container>
            <Spinner/>
                <Row>
                    <Col sm={9}>
                        <center>
                            <h1>Leaderboard</h1>
                        </center>
                        <Table striped bordered hover>
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Username</th>
                                    <th>#Matches</th>
                                    <th>Elo</th>
                                </tr>
                            </thead>
                            <tbody>

                                {
                                    scoreboard.map((acc, id) => {
                                        return (
                                            <tr key={id}>
                                                <td>{id + 1}</td>
                                                <td>{acc.username}</td>
                                                <td>{acc.number_of_matches}</td>
                                                <td>{acc.elo}</td>
                                            </tr>
                                        );
                                    })
                                }
                                                                
                                
                            </tbody>
                        </Table>
                    </Col>

                    <Col sm={3}>
                        <Profile/>
                        <Ranking/>
                    </Col>
                </Row>
                
            </Container>
        </>
    )
}

export default Leaderboard;