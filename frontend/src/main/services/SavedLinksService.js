const saveNewLink = async (linkData) => {
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(linkData)
    };

    return await fetch('/api/savedLinks/new', requestOptions);
}

export {saveNewLink}