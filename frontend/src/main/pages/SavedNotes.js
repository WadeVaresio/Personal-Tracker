import React from "react";
import {Button, Modal, Form} from "react-bootstrap";
import {useState} from "react";
import {saveNewNote} from "../services/NotesService";
import {ImageBackground} from "react-native";
import Goleta_Open_Space from "../images/Goleta_Open_Space.jpg";
import {Container} from "react-bootstrap";
import SavedNotesTable from "../components/SavedNotesTable";
import useSWR from "swr";
import {fetchWithToken} from "../services/fetch";
import {useAuth0} from "@auth0/auth0-react";

const SavedNotes = () => {
    const {getAccessTokenSilently: getToken} = useAuth0();
    const{ data: allNotes, mutate: mutateNotes } = useSWR(['/api/savedNotes/all', getToken], fetchWithToken);

    const styles = {
        image: {
            flex: 1,
            resizeMode: "cover",
            justifyContent: "center",
            minWidth: "101%",
            minHeight: "100%"
        }
    };

    const [showModal, setShowModal] = useState(false);
    const [newNote, setNewNote] = useState("");

    const handleShow = () => setShowModal(true);
    const handleClose = () => {
        setNewNote("");
        setShowModal(false);
    }

    const handleNewNote = (change) => {setNewNote(change.target.value)};

    const handleSubmit = () => {
        saveNewNote({note: newNote});
        mutateNotes();
        setShowModal(false);
    };

    return (
        <>
            <ImageBackground source={Goleta_Open_Space} style={styles.image}>
                <Container style={{justifyContent: 'center'}}>
                    <Button variant="primary" onClick={handleShow}>
                        New Note
                    </Button>
                    <hr/>
                    <SavedNotesTable notes={allNotes}/>
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