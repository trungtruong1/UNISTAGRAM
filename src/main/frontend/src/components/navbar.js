import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import useToken from '../useTokens';


export default function NavBar() {
    const { token, setToken } = useToken();
    const SignOut = () => {
        setToken(null);
        window.location.href = "/";
    }
    return (
        <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
            <Container>
                <Navbar.Brand href="/">
                    <img src="/logo.png" width="40" height="auto"/>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/play">Play</Nav.Link>
                        <Nav.Link href="/watch">Watch</Nav.Link>
                        <Nav.Link href="/leaderboard">Leaderboard</Nav.Link>
                    </Nav>

                    <Nav>
                        {
                            (token)? 
                                <NavDropdown title={`Hello, ${token.username}`} id="collasible-nav-dropdown">
                                    <NavDropdown.Item>Match history</NavDropdown.Item>
                                    <hr style={{margin: 0}}></hr>
                                    <NavDropdown.Item onClick={SignOut}>Sign out</NavDropdown.Item>
                                </NavDropdown> 
                            : 
                                <><Nav.Link href="/signin">Sign In</Nav.Link>
                                <Nav.Link href="/signup">Sign Up</Nav.Link></>
                        }
                        
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}