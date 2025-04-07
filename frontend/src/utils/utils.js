export const truncateOwnerName = (name) => {
    if (name === "") {
        return "AS"
    }

    return name.slice(0, 3).toUpperCase()
}

export const dateStringToLocaleString = (dateString) => {
    const userTimeZone = new Intl.DateTimeFormat().resolvedOptions().timeZone;

    const date = new Date(dateString)

    return date.toLocaleString(navigator.language, { timeZone: userTimeZone, hour12: true })
}