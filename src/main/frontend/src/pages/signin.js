
import { Col, Container, Row } from "react-bootstrap";
import NavBar from "../components/navbar"
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import React, { useState } from 'react';
import PropTypes from 'prop-types';

async function loginUser(credentials) {
    const res = await fetch('http://localhost:8000/api/user/authenticate/', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
    });
    return await res.json();
}

export default function SignIn({ setToken }) {

    const [username, setUserName] = useState();
    const [password, setPassword] = useState();

    const handleSubmit = async e => {
        e.preventDefault();

        const tokenString = sessionStorage.getItem('token');
        const testToken = JSON.parse(tokenString);

        if(testToken) {
            alert("You are already signed in");
            return;
        }

        const token = await loginUser({
            username,
            password
        });
        
        if(token.status === "failed"){
            alert(token.reason);
            return;
        }

        token.password = password

        setToken(token);
        window.location.href = "/";
    }

    return (
        <>
            <NavBar/>
            <br/>
            <Container>
                <center>
                    <h1>Sign In</h1>
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

                            <center>
                                <Button variant="primary" type="submit">
                                    Sign in
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

SignIn.propTypes = {
    setToken: PropTypes.func.isRequired
};