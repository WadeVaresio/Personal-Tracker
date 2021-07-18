import React from "react";
import { Container, Nav, Navbar, NavLink } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import AuthNav from "./AuthNav";


const ApplicationNavBar = () => {
    const styling = {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        marginBottom: "50px"
    };

    return (
        <div style={styling}>
            <Container>
                <Navbar bg={"dark"} variant={"dark"} fixed={"top"}>
                    <Nav className={"me-auto"}>
                        <LinkContainer to={"/"}>
                            <NavLink>Home</NavLink>
                        </LinkContainer>

                        <LinkContainer to={"/weather"}>
                            <NavLink>Weather</NavLink>
                        </LinkContainer>

                        <LinkContainer to={"/savedNotes"}>
                            <NavLink>Saved Notes</NavLink>
                        </LinkContainer>

                        <AuthNav/>
                    </Nav>
                </Navbar>
            </Container>
        </div>
    );
}

export default ApplicationNavBar;