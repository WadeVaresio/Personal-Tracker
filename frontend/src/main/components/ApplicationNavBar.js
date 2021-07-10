import React from "react";
import { Container, Nav, Navbar, NavLink } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import LoginButton from "./LoginButton";
import useSWR from "swr";


const ApplicationNavBar = () => {
    const fetcher = url => fetch(url).then(res => res.json());
    const {data: loggedIn} = useSWR("/api/user/loggedIn", fetcher);

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

                        {loggedIn &&
                        <LinkContainer to={"/savedNotes"}>
                            <NavLink>Saved Notes</NavLink>
                        </LinkContainer>
                        }

                        <LoginButton/>
                    </Nav>
                </Navbar>
            </Container>
        </div>
    )
}

export default ApplicationNavBar;