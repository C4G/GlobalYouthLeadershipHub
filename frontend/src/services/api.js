import { API } from "@/constants/env";
import { getJWTToken } from "@/hooks/auth";

const customFetcher = async (path, method, signal = null, data, raw = false) => {
    const parsedUserJwtToken = getJWTToken()
    const bearerToken = parsedUserJwtToken?.token || ""

    const noRequiredAuth = ['/auth'].some(publicPath => path.startsWith(publicPath))
    // path that is not part of whitelisted paths and requires token
    if (!noRequiredAuth && !bearerToken) {
        throw new Error("Authentication token is missing");
    }

    try {
        const options = {
            headers: {
                "Content-Type": "application/json",
                ...bearerToken ? { 'Authorization': `Bearer ${bearerToken}` } : {},
            },
            method: method,
            signal: signal,
        };

        if (["POST", "PUT", "PATCH"].includes(method) && data) {
            const isFormData = data instanceof FormData
            options.body = isFormData ? data : JSON.stringify(data);

            // Deleted "Content-Type" because it is defaulted to "application/json"
            if (isFormData && options.headers["Content-Type"]) {
                delete options.headers["Content-Type"];
            }
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

        return raw ? response : await response.json();
    } catch (error) {
        console.error(error)
        // throw error so that you react-query can handle callback for onError 
        throw error;
    }
}


export default customFetcher