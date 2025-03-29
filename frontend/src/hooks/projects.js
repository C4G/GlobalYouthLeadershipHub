import { useQuery } from "@tanstack/react-query"

export const useGetProjects = () => {
    return useQuery({
        queryKey: ["projects"],
        queryFn: ({})
    })
}

export const useGetProjectById = (projectId) => {
    return useQuery({
        queryKey: ['project', projectId],
        queryFn: () => { }
    })
}

export const useGetProjectByUserId = (userId) => {
    return useQuery({
        enabled: Boolean(userId),
        queryKey: ['projectByUserId', userId],
        queryFn: () => { },
    })
}