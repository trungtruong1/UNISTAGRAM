
import { Col, Container, Row } from "react-bootstrap";
import NavBar from "../components/navbar"
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';


function SignUp() {
    return (
        <>
            <NavBar/>
            <br/>
            <Container>
                <center>
                    <h1>Sign Up</h1>
                </center>
                <Row>
                    <Col sm={3}></Col>
                    <Col sm={6}>
                        <Form>
                            <Form.Group className="mb-3" controlId="formBasicUsername">
                                <Form.Label>Username</Form.Label>
                                <Form.Control disabled="true" type="text" placeholder="Enter Username" />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control disabled="true" type="password" placeholder="Password" />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Retype password</Form.Label>
                                <Form.Control disabled="true" type="password" placeholder="Enter your password again" />
                            </Form.Group>

                            <center>
                                <Button disabled="true" variant="primary" type="submit">
                                    Sign up
                                </Button>
                            </center>
                        </Form>
                    </Col>
                    <Col sm={3}></Col>
                </Row>
            </Container>
        </>
    )
}

export default SignUp;