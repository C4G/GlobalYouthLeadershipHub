import customFetcher from "@/services/api"
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query"

export const useGetCommentsByProjectAndPostId = (projectId, postId) => {
    return useQuery({
        queryKey: ["comments", projectId, postId],
        queryFn: ({ signal }) => customFetcher(`/projects/${projectId}/posts/${postId}/comments`, "GET", signal),
        enabled: !!(projectId && postId)
    })
}

export const useAddCommentByProjectAndPostId = (projectId, postId) => {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: (commentData) => customFetcher(`/projects/${projectId}/posts/${postId}/comments`, "POST", null, commentData),
        onSuccess: () => {
            queryClient.invalidateQueries(["comments", projectId, postId])
            queryClient.invalidateQueries(["projectPost", postId])
        }
    })
}