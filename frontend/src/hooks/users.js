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