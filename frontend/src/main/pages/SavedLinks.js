import React from "react";
import {Button, Modal, Form, Jumbotron} from "react-bootstrap";
import {useState} from "react";
import {saveNewLink} from "../services/SavedLinksService";
import BootstrapTable from "react-bootstrap-table-next";
import useSWR from "swr";
import {fetchWithoutToken} from "../services/fetch";

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
        saveNewLink({link: newLink, note: newLinkNote});
        setShowModal(false)
    }

    const { data: allLinks } = useSWR("/api/savedLinks/all", fetchWithoutToken);

    const tableColumns = [{
        dataField: 'id',
        text: 'id'
    }, {
        dataField: 'link',
        text: 'Link'
    }, {
        dataField: 'note',
        text: 'Note'
    }]


    return (
        <>
            <Jumbotron>
                <Button variant="primary" onClick={handleShow}>
                    New Saved Link
                </Button>
                <BootstrapTable keyField={'id'} data={allLinks || []} columns={tableColumns}/>

            </Jumbotron>


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