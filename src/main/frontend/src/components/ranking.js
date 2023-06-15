import { useEffect, useState } from "react";
import { Table, Card } from "react-bootstrap";


export default function Ranking() {

    const [scoreboard, setScoreboard] = useState([]);

    useEffect(() => {
        let ignore = false;

        async function fetchData() {
            if(ignore) return;
            const res = await fetch(`http://localhost:8000/api/user/?top=5`, {
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


    return (
        <Card style={{ width: '15rem', margin: '10px' }}>
            <Card.Header>Top players</Card.Header>
            <Table>
                <thead>
                    <tr>
                    <th>#</th>
                    <th>Username</th>
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
                                    <td>{acc.elo}</td>
                                </tr>
                            );
                        })
                    }
                </tbody>
                </Table>
        </Card>
    )
}