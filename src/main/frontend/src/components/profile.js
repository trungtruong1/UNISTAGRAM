import { Button, Card } from "react-bootstrap";
import useToken from "../useTokens";
import { useEffect, useState } from "react";

export default function Profile() {

    const [info, setInfo] = useState({});

    useEffect(() => {
        let ignore = false;

        async function fetchData() {
            if(!token) return;
            if(ignore) return;
            const res = await fetch(`http://localhost:8000/api/user/getinfo/?username=${token.username}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await res.json();
            console.log(data);
            setInfo({
                username: data.username,
                number_of_matches: data.number_of_matches,
                elo: data.elo
            });
        }

        fetchData();
        return () => { ignore = true; }        
    }, []);


    const {token, setToken} = useToken();
    return (
        <Card style={{ width: '15rem', margin: '10px' }}>
            <Card.Header>Profile</Card.Header>
            <Card.Body>
                {token? 
                    <>
                        <Card.Text>
                            Username: {info.username}
                        </Card.Text>
                        <Card.Text>
                            #Matches: {info.number_of_matches}
                        </Card.Text>
                        <Card.Text>
                            Elo: {info.elo}
                        </Card.Text>
                    </>
                :
                    <>You are not signed in!</>
                }
            </Card.Body>
        </Card>
    );
}