import { useJWTToken } from "@/hooks/auth"
import { Navigate } from "react-router-dom"

// eslint-disable-next-line react/prop-types
export const ProtectedRoutes = ({ children }) => {
    const { data: jwtToken, isLoading } = useJWTToken()

    if (isLoading) {
        return <div>Loading...</div>
    }

    const isLoggedIn = Boolean(jwtToken?.token)

    if (!isLoggedIn) {
        return <Navigate to='/' replace />
    }

    return (
        children
    )
}


// eslint-disable-next-line react/prop-types
export const ProtectedAdminRoute = ({ children }) => {
    const { data: jwtToken, isLoading } = useJWTToken()

    if (isLoading) {
        return <div>Loading...</div>
    }

    const isLoggedIn = Boolean(jwtToken?.token)
    const isAdmin = jwtToken?.isAdmin

    if (!isLoggedIn & !isAdmin) {
        return <Navigate to='/' replace />
    }

    return (
        children
    )
}