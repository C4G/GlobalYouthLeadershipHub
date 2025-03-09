import { createRoot } from "react-dom/client";
import { StrictMode } from 'react';
import App from "@/App.jsx";
import { QueryClientProvider } from "@tanstack/react-query";
import queryClient from "@/libs/queryClient.js";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { ENV } from "@/constants/env.js";

createRoot(document.getElementById("root")).render(
    <StrictMode>
        <QueryClientProvider client={queryClient}>
            <App />
            {ENV === 'local' && <ReactQueryDevtools initialIsOpen={true} />}
        </QueryClientProvider>
    </StrictMode>
);