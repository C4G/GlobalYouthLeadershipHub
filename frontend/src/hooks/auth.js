import queryClient from "@/libs/queryClient"
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query"
import { useEffect } from "react"

// TODO - might foresee some caching synchronization issue, will check when our get API is up
export const getJWTToken = () => {
    const storedJWTToken = localStorage.getItem('jwtToken')
    return storedJWTToken ? JSON.parse(storedJWTToken) : null
}

export const useJWTToken = () => {
    return useQuery({
        queryKey: ["jwtToken"],
        queryFn: getJWTToken,
        staleTime: Infinity,
        gcTime: Infinity
    })
}

export const useSetJWTToken = () => {
    return useMutation({
        mutationFn: (userJWTToken) => localStorage.setItem('jwtToken', JSON.stringify(userJWTToken)),
        onSuccess: (_, userJWTToken) => queryClient.setQueryData(["jwtToken"], userJWTToken)
    })
}

export const useRemoveJWTToken = () => {
    return useMutation({
        mutationFn: () => localStorage.removeItem('jwtToken'),
        onSuccess: () => queryClient.setQueryData(["jwtToken"], null)
    })
}

export const useSyncLocalStorage = () => {
    const qClient = useQueryClient()
    useEffect(() => {
        const handleStorageChange = () => {
            const token = localStorage.getItem("jwtToken")
            queryClient.setQueryData(["jwtToken"], token ? JSON.parse(token) : null)
        }
        window.addEventListener('storage', handleStorageChange)

        return () => window.removeEventListener('storage', handleStorageChange)
    }, [qClient])
}