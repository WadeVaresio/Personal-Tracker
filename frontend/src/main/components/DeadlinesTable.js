import React from "react";
import {Button, Form, Modal} from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from 'react-bootstrap-table2-paginator';
import {useState} from "react";
import Datetime from "react-datetime";
import "react-datetime/css/react-datetime.css";
import {saveNewDeadline, deleteDeadline, saveEditedDeadline} from "../services/DeadlinesService";
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import {fetchWithToken} from "../services/fetch";


const createDateString = (dateString) => {
    let date = new Date(dateString);

    const dayWeek = date.toLocaleString('en-us', { weekday: 'long'}).substring(0, 3);
    const month = date.toLocaleString('default', {month: 'long'});
    const hour = date.getHours();
    const minutes = date.getMinutes();
    const timeDay = hour < 12 ? "AM" : "PM";

    return dayWeek + " " +
            month + " " +
            date.getDate() + " " +
            (hour > 12 ? hour - 12 : hour) + ":" +
            (minutes < 10 ? "0" + minutes : minutes) + " " +
            timeDay;
};


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

    const handleNewDeadlineClick = () => {
        setDeadlineDate(new Date());
        setShowModalNewDeadline(true);
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
        text: 'Date',
        sort: true,
        isDummyField: true,
        formatter: (_cell, row) => createDateString(row.date)
    },{
        dataField: 'description',
        text: 'Description',
        sort: true
    },{
        dataField: 'edit',
        text: 'Edit/Delete',
        isDummyField: true,
        formatter: (_cell, row) => getEditDeleteButtons(row)
    }];

    return(
        <>
            <Button onClick={handleNewDeadlineClick}>New Deadline</Button>

            <hr/>

            <Modal show={showModalNewDeadline} onHide={() => setShowModalNewDeadline(false)}>
                <Modal.Header>
                    <Modal.Title>New Deadline</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <Form onSubmit={handleFormSubmit}>
                        <Form.Label>Deadline Description</Form.Label>
                        <Form.Control type={"text"} placeholder={"Enter Deadline Description"} onChange={(change) => setDeadlineDesc(change.target.value)}/>

                        <hr/>

                        <Form.Label>Deadline</Form.Label>
                        <Datetime
                            value={deadlineDate}
                            onChange={setDeadlineDate}/>
                    </Form>
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

                        <hr/>

                        <Form.Label>Deadline</Form.Label>
                        <Datetime
                            value={editDeadlineNewDate}
                            onChange={setEditDeadlineNewDate}/>
                    </Form>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant={"secondary"} onClick={() => setShowModalEditDeadline(false)}>Discard Edit</Button>
                    <Button variant={"primary"} onClick={handleEditDeadlineSubmit}>Make Edit</Button>
                </Modal.Footer>
            </Modal>

            <BootstrapTable keyField={'id'}
                            data={deadlines || []}
                            columns={tableColumns}
                            pagination={paginationFactory()}
                            rowStyle={{ backgroundColor: 'rgba(0, 0, 0, 0.25)', color: '#ffffff'}}
                            bootstrap4={true}/>
        </>
    )
}

export default DeadlinesTable;