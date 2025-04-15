export const truncateOwnerName = (name) => {
    if (name === "") {
        return "AS"
    }

    const initials = name.split(" ").map(word => word[0]).join("")

    return initials.toUpperCase()
}

export const dateStringToLocaleString = (dateString) => {
    const userTimeZone = new Intl.DateTimeFormat().resolvedOptions().timeZone;

    const date = new Date(dateString)

    return date.toLocaleString(navigator.language, { timeZone: userTimeZone, hour12: true })
}