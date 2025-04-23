import customFetcher from "@/services/api"
import { useQuery } from "@tanstack/react-query"
import { useEffect } from "react"

export const useGetProjects = () => {
    return useQuery({
        queryKey: ["projects"],
        queryFn: ({ signal }) => customFetcher('/projects', "GET", signal),
    })
}

export const useGetProjectImageById = (imagePath) => {
    const query = useQuery({
        queryKey: ["projectImage", imagePath],
        enabled: !!imagePath,
        // async...await is still needed for setting response.blob()
        queryFn: async ({ signal }) => {
            const response = await customFetcher(imagePath, "GET", signal, null, true)
            const blobImg = await response.blob();
            return URL.createObjectURL(blobImg)
        },
        retry: false,
        refetchOnWindowFocus: false
    })

    useEffect(() => {
        // clean up the previous data, if there is a change in image from previous
        return () => {
            if (query.data) {
                URL.revokeObjectURL(query.data)
            }
        }
    }, [query.data])

    return query
}

export const useGetProjectsByUser = () => {
    return useQuery({
        queryKey: ['userProjects'],
        queryFn: ({ signal }) => customFetcher('/projects/user', "GET", signal)
    })
}