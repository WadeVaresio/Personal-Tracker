import React from "react";
import { Button, Modal, Form } from "react-bootstrap";
import {useState} from "react";

const SavedLinks = () => {
    const [showModal, setShowModal] = useState(false);
    const [newLink, setNewLink] = useState("");
    const [newLinkNote, setNewLinkNote] = useState("");

    const handleShow = () => setShowModal(true);
    const handleClose = () => {
        setNewLink("");
        setNewLinkNote("");
        setShowModal(false);
    }

    const handleNewLink = (change) => {setNewLink(change.target.value)};
    const handleNewLinkNote = (change) => {setNewLinkNote(change.target.value)};

    const handleSubmit = () => {
        console.log(newLink);
        console.log(newLinkNote)
        setShowModal(false)
    }

    return (
        <>
            <Button variant="primary" onClick={handleShow}>
                New Saved Link
            </Button>

            <Modal show={showModal} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>New Link To Save</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Label>Link</Form.Label>
                        <Form.Control type={"url"} placeholder={"Link to save"} onChange={handleNewLink}/>
                        <Form.Label>Link Note</Form.Label>
                        <Form.Control type={"text"} placeholder={"Note regarding link to save"} onChange={handleNewLinkNote}/>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={handleSubmit}>
                        Save Link
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default SavedLinks;