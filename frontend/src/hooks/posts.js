import { useMutation, useQuery } from "@tanstack/react-query"

export const useGetPosts = (projectId) => {
    return useQuery({
        queryKey: ["projectPosts", projectId],
        queryFn: ({ queryKey }) => {
            [, projectId] = queryKey
        }
    })
}

export const useGetPostById = (postId) => {
    return useQuery({
        queryKey: ["projectPost", postId],
        queryFn: ({ queryKey }) => {
            [, postId] = queryKey
        }
    })
}

export const useCreatePost = () => {
    return useMutation({
        mutationFn: ({ projectId }) => console.log('project', projectId)
    })
}