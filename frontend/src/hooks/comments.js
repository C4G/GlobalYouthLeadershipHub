import { useMutation, useQuery } from "@tanstack/react-query"

export const useGetCommentsByPostId = (postId) => {
    return useQuery({
        queryKey: ["comments", postId],
        queryFn: () => { }
    })
}

export const useCreateCommentByPost = () => {
    return useMutation({
        mutationFn: ({ postId }) => console.log(postId)
    })
}