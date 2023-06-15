import { Button, Card, ListGroup } from "react-bootstrap";

export default function SubmissionList(props) {
    return (
            <Card style={{ width: '100%', margin: '10px'}}>
                <Button variant="none">
                    <ListGroup variant="flush" style={{flex: 1}}>
                        <ListGroup.Item style={{textAlign: 'left'}}>Problem: {props.problem}</ListGroup.Item>
                        <ListGroup.Item style={{textAlign: 'left'}}>Verdict: {props.verdict}</ListGroup.Item>
                        <ListGroup.Item style={{textAlign: 'left'}}>Coins earned: {props.coins}</ListGroup.Item>
                    </ListGroup>
                </Button>
            </Card>
    )
}