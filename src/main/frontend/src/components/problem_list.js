import { Button, Card } from "react-bootstrap";

export default function ProblemList(props) {
    return (
        <Card style={{ width: '100%', margin: '10px'}}>
            <Card.Body>
                <Card.Title>{props.code} - {props.title}</Card.Title>
                <Card.Text>
                    {props.statement}
                </Card.Text>
                <Button variant="primary" href={`/problems/${props.code}`}>View</Button>
            </Card.Body>
        </Card>
    )
}