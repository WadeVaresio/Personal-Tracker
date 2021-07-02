const saveNewLink = async (linkData) => {
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(linkData)
    };

    return await fetch('/api/savedNotes/new', requestOptions);
}

export {saveNewLink}