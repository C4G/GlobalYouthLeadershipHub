/* eslint-disable react/prop-types */
import { useJWTToken } from "@/hooks/auth"
import { Navigate } from "react-router-dom"


export const ProtectedRoutes = ({ children }) => {
    const { data: jwtToken, isLoading } = useJWTToken()

    if (isLoading) {
        return <div>Loading...</div>
    }

    const isLoggedIn = Boolean(jwtToken?.token)

    if (!isLoggedIn) {
        return <Navigate to='/' replace />
    }

    const isVerified = jwtToken?.isVerified === true
    if (!isVerified) {
        return <Navigate to='/unauthorized' replace />
    }

    return (
        children
    )
}


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