
import { Col, Container, Row } from "react-bootstrap";
import NavBar from "../components/navbar"
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { checkLogin } from "../ultils/checkLogin";
import { useState } from "react";


async function registerUser(credentials) {
    const res = await fetch('http://localhost:8080/users', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials),
    });
    return res;
}


export default function SignUp() {

    const [username, setUserName] = useState();
    const [password, setPassword] = useState();
    const [repass, setRepass] = useState();

    const handleSubmit = async e => {
        e.preventDefault();

        const testToken = checkLogin();

        if(testToken) {
            alert("You are already signed in");
            return;
        }

        if(password !== repass) {
            alert("Passwords do not match!");
            return;
        }

        const res = await registerUser({
            username: username,
            password: password
        });
        
        if(!res.ok){
            alert(await res.text());
            return;
        }

        alert("Sign up successfully! You will be redirected to the sign in page");
        window.location.href = "/signin";
    }

    return (
        <>
            <NavBar/>
            <br/>
            <Container>
                <center>
                    <img src="/logo_full.png" style={{height: "30vh", width: "auto"}}/>
                    <h1>Sign Up</h1>
                </center>
                <Row>
                    <Col sm={3}></Col>
                    <Col sm={6}>
                        <Form onSubmit={handleSubmit}>
                            <Form.Group onChange={e => setUserName(e.target.value)} className="mb-3" controlId="formBasicUsername">
                                <Form.Label>Username</Form.Label>
                                <Form.Control type="text" placeholder="Enter Username" />
                            </Form.Group>

                            <Form.Group onChange={e => setPassword(e.target.value)} className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" placeholder="Password" />
                            </Form.Group>

                            <Form.Group onChange={e => setRepass(e.target.value)} className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Retype password</Form.Label>
                                <Form.Control type="password" placeholder="Enter your password again" />
                            </Form.Group>

                            <center>
                                <Button variant="primary" type="submit">
                                    Sign up
                                </Button>
                                <br></br>
                                <br></br>
                                <p className="text-center-lg-start">Already have an account? Click <a href="/signin">here</a> to sign in.</p>
                            </center>
                        </Form>
                    </Col>
                    <Col sm={3}></Col>
                </Row>
            </Container>
        </>
    )
}