import customFetcher from "@/services/api"
import { useQueries, useQuery } from "@tanstack/react-query"
import { useEffect } from "react"

export const useGetAllPosts = (projectId) => {
    return useQuery({
        queryKey: ["projectPosts", projectId],
        queryFn: ({ signal }) => customFetcher(`/projects/${projectId}/posts`, "GET", signal),
        enabled: !!projectId
    })
}

export const useGetProjectPostImageById = (postImagePaths) => {
    const queries = useQueries({
        queries: postImagePaths.map((path) => ({
            queryKey: ["postImage", path],
            enabled: !!path,
            queryFn: async ({ signal }) => {
                const response = await customFetcher(path, "GET", signal, null, true);
                const blob = await response.blob();
                return URL.createObjectURL(blob);
            },
            retry: false,
            refetchOnWindowFocus: false,
        }))
    })

    // filter all the valid data that are not undefined or null
    const blobURLs = queries.map(q => q.data).filter(Boolean);

    useEffect(() => {
        // clean up the previous data, if there is a change in image from previous
        return (() => {
            blobURLs.forEach(q => {
                if (q.data) {
                    URL.revokeObjectURL(q.data)
                }
            })
        })
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [blobURLs.join()])

    return {
        isLoading: queries.some(q => q.isLoading),
        data: blobURLs
    }
}

export const useGetProjectPostById = (projectId, postId) => {
    return useQuery({
        queryKey: ["projectPost", postId],
        queryFn: ({ signal }) => customFetcher(`/api/projects/${projectId}/posts/${postId}`, "GET", signal),
        enabled: !!(projectId && postId)
    })
}