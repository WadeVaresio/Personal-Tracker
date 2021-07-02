import React from "react";
import {Button, Modal, Form, Jumbotron} from "react-bootstrap";
import {useState} from "react";
import {saveNewLink} from "../services/NotesService";
import BootstrapTable from "react-bootstrap-table-next";
import useSWR from "swr";
import {fetchWithoutToken} from "../services/fetch";
import {ImageBackground} from "react-native";
import Goleta_Open_Space from "../images/Goleta_Open_Space.jpg";
import {useHistory} from "react-router-dom";
import {Container} from "react-bootstrap";

const SavedNotes = () => {
    const styles = {
        image: {
            flex: 1,
            resizeMode: "cover",
            justifyContent: "center",
            minWidth: "101%",
            minHeight: "100%"
        },
        table: {
            width: '5vw'
        }
    };

    const [showModal, setShowModal] = useState(false);
    const [newNote, setNewNote] = useState("");
    const history = useHistory();

    const handleShow = () => setShowModal(true);
    const handleClose = () => {
        setNewNote("");
        setShowModal(false);
    }

    const handleNewNote = (change) => {setNewNote(change.target.value)};

    const handleSubmit = () => {
        saveNewLink({note: newNote});
        setShowModal(false)
        history.push("/savedNotes")
    }

    const { data: allLinks } = useSWR("/api/savedNotes/all", fetchWithoutToken);

    const tableColumns = [{
        dataField: 'note',
        text: 'Note'
    }];

    return (
        <>
            <ImageBackground source={Goleta_Open_Space} style={styles.image}>
                <Container style={{justifyContent: 'center'}}>
                    <Button variant="primary" onClick={handleShow}>
                        New Note
                    </Button>
                    <BootstrapTable keyField={'id'} data={allLinks || []} columns={tableColumns} style={"width: auto;"} class={"table table-responsive"}/>

                </Container>
            </ImageBackground>

            <Modal show={showModal} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>New Link To Save</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Label>Note</Form.Label>
                        <Form.Control type={"text"} placeholder={"Note to save"} onChange={handleNewNote}/>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={handleSubmit}>
                        Save Note
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default SavedNotes;