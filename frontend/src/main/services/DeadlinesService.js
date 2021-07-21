const saveNewDeadline = async (deadline, tokenFetcher) => {
    const authToken = await tokenFetcher();

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${authToken}`
        },
        body: JSON.stringify(deadline)
    };

    return await fetch('/api/private/deadlines/new', requestOptions);
};

const saveEditedDeadline = async(editedDeadline, tokenFetcher) => {
    const authToken = await tokenFetcher();

    const requestOptions = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${authToken}`
        },
        body: JSON.stringify(editedDeadline)
    };

    return await fetch(`/api/private/deadlines/put`, requestOptions);
}


const deleteDeadline = async (deadline, tokenFetcher) => {
    const authToken = await tokenFetcher();

    const requestOptions = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${authToken}`
        },
        body: JSON.stringify(deadline)
    };

    return await fetch('/api/private/deadlines/delete', requestOptions);
};

export {saveNewDeadline, saveEditedDeadline, deleteDeadline};