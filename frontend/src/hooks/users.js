import customFetcher from "@/services/api"
import { useMutation, useQuery } from "@tanstack/react-query"

export const useGetUnverifiedUsers = () => {
    return useQuery(
        {
            queryKey: ["unverifiedUsers"],
            queryFn: ({ signal }) => customFetcher("/admin/users/pending", "GET", signal),
        }
    )
}

export const useVerifyPendingUser = () => {
    return useMutation({
        mutationFn: ({ email }) => customFetcher("/admin/users/verify", "POST", null, { email })
    })
}

export const useGetListOfResetUsers = () => {
    return useQuery({
        queryKey: ["resetUsers"],
        queryFn: ({ signal }) => customFetcher("/admin/users/reset-required", "GET", signal)
    })
}