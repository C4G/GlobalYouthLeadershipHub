import customFetcher from "@/services/api"
import { useQuery } from "@tanstack/react-query"

export const useGetUnverifiedUsers = () => {
    return useQuery(
        {
            queryKey: ["unverifiedUsers"],
            queryFn: ({ signal }) => customFetcher("/admin/users/pending", "GET", signal),
        }
    )
}