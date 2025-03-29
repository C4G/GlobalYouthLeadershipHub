import { API } from "@/constants/env";
import { getJWTToken } from "@/hooks/auth";

const customFetcher = async (path, method, signal = null, data) => {
    // TODO - might foresee some caching synchronization issue, will check when our get API is up
    const parsedUserJwtToken = getJWTToken()
    const bearerToken = parsedUserJwtToken?.token || ""

    const requiredAuth = !path.startsWith('/auth')
    if (requiredAuth && !bearerToken) {
        throw new Error("Authentication token is missing");
    }

    try {
        const options = {
            headers: {
                "Content-Type": "application/json",
                ...bearerToken ? { 'Authorization': `Bearer ${bearerToken}` } : {}
            },
            method: method,
            signal: signal,
        };

        if (["POST", "PUT", "PATCH"].includes(method) && data) {
            options.body = JSON.stringify(data)
        }

        const response = await fetch(`${API}${path}`, options)
        if (!response.ok) {
            const errorResponse = await response.json();
            /*
                errorResponse.error => handle errorResponse payload where key is error (i.e. { error: xxx })
                JSON.stringify(errorResponse) => handle errorResponse payload where key is NOT error, let your onError from tanstack handle
                "Something went wrong" => default error
            */
            throw new Error(errorResponse.error || JSON.stringify(errorResponse) || "Something went wrong");
        }
        return await response.json();
    } catch (error) {
        console.error(error)
        // throw error so that you react-query can handle callback for onError 
        throw error;
    }
}


export default customFetcher