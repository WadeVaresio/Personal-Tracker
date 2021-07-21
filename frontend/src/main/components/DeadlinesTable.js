import React from "react";
import {Button, Form, Modal} from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from 'react-bootstrap-table2-paginator';
import {useState} from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {saveNewDeadline, deleteDeadline, saveEditedDeadline} from "../services/DeadlinesService";
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import {fetchWithToken} from "../services/fetch";

const DeadlinesTable = () => {
    const [showModalNewDeadline, setShowModalNewDeadline] = useState(false);
    const [showModalEditDeadline, setShowModalEditDeadline] = useState(false);
    const [editDeadline, setEditDeadline] = useState();
    const [editDeadlineNewDesc, setEditDeadlineNewDesc] = useState();
    const [editDeadlineNewDate, setEditDeadlineNewDate] = useState(new Date());
    const [deadlineDate, setDeadlineDate] = useState(new Date());
    const [deadlineDesc, setDeadlineDesc] = useState("");
    const {user, getAccessTokenSilently: getAuthToken} = useAuth0();
    const {data: deadlines, mutate:mutateDeadlines} = useSWR(['/api/private/deadlines/get', getAuthToken], fetchWithToken);

    const handleFormSubmit = (event) => {
        event.preventDefault();
        setShowModalNewDeadline(false);
        mutateDeadlines(saveNewDeadline({
            date: deadlineDate,
            description: deadlineDesc,
            userID: user.email
        }, getAuthToken));
    };

    const handleEditDeadlineSubmit = (event) => {
        event.preventDefault();
        editDeadline.description = editDeadlineNewDesc;
        editDeadline.date = editDeadlineNewDate;

        mutateDeadlines(saveEditedDeadline(editDeadline, getAuthToken));
        setShowModalEditDeadline(false);
    };

    const handleEditNoteClick = (row) => {
        setEditDeadline(row);
        setEditDeadlineNewDesc(row.description);
        let editDate = new Date(row.date);

        setEditDeadlineNewDate(editDate.setDate(editDate.getDate() + 1));
        setShowModalEditDeadline(true);
    };

    const getEditDeleteButtons = row => {
        return (
            <>
                <Button onClick={() => handleEditNoteClick(row)} style={{marginRight: "15px"}}>Edit</Button>
                <Button variant={"danger"} onClick={() => mutateDeadlines(deleteDeadline(row, getAuthToken))}>Delete</Button>
            </>
        );
    };

    const tableColumns = [{
        hidden: true,
        dataField: 'id',
        text: 'ID'
    }, {
        dataField: 'date',
        text: 'Date'
    },{
        dataField: 'description',
        text: 'Description'
    },{
        dataField: 'edit',
        text: 'Edit/Delete',
        isDummyField: true,
        formatter: (_cell, row) => getEditDeleteButtons(row)
    }];

    return(
        <>
            <Button onClick={() => setShowModalNewDeadline(true)}>New Deadline</Button>

            <hr/>

            <Modal show={showModalNewDeadline} onHide={() => setShowModalNewDeadline(false)}>
                <Modal.Header>
                    <Modal.Title>New Deadline</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <Form onSubmit={handleFormSubmit}>
                        <Form.Label>Deadline Description</Form.Label>
                        <Form.Control type={"text"} placeholder={"Enter Deadline Description"} onChange={(change) => setDeadlineDesc(change.target.value)}/>
                    </Form>

                    <hr/>

                    <DatePicker
                        selected={deadlineDate}
                        showTimeSelect
                        onChange={(date) => setDeadlineDate(date)}/>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant={"secondary"} onClick={() => setShowModalNewDeadline(false)}>Discard</Button>
                    <Button variant={"primary"} onClick={handleFormSubmit}>Submit</Button>
                </Modal.Footer>
            </Modal>

            <Modal show={showModalEditDeadline} onHide={() => setShowModalEditDeadline(false)}>
                <Modal.Header>
                    <Modal.Title>Edit Deadline</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <Form onSubmit={handleEditDeadlineSubmit}>
                        <Form.Label>Deadline Description</Form.Label>
                        <Form.Control type={"text"} defaultValue={editDeadlineNewDesc} onChange={(change) => setEditDeadlineNewDesc(change.target.value)}/>
                    </Form>

                    <hr/>

                    <DatePicker
                        selected={editDeadlineNewDate}
                        showTimeSelect
                        onChange={(date) => setEditDeadlineNewDate(date)}/>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant={"secondary"} onClick={() => setShowModalEditDeadline(false)}>Discard Edit</Button>
                    <Button variant={"primary"} onClick={handleEditDeadlineSubmit}>Make Edit</Button>
                </Modal.Footer>
            </Modal>

            <BootstrapTable keyField={'id'}
                            data={deadlines || []}
                            columns={tableColumns}
                            pagination={paginationFactory()}/>
        </>
    )
}

export default DeadlinesTable;