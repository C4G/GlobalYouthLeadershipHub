import { API } from "@/constants/env";

const customFetcher = async (path, method, signal = null, data) => {
    const jwtToken = localStorage.getItem("jwtToken")
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

        const response = await fetch(`${API}${path}`, options)
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