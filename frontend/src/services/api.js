const jwtToken = ""

const customFetcher = async (url, method, signal = null, data) => {
    try {
        const options = {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}`
            },
            method: method,
            signal: signal
        };

        if (["POST", "PUT", "PATCH"].includes(method) && data) {
            options.body = JSON.stringify(data)
        }

        const response = await fetch(url, options)
        if (!response.ok) {
            throw new Error(response.statusText)
        }

        return await response.json()

    } catch (error) {
        console.error(error)
        return
    }
}


export default customFetcher