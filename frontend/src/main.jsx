import { BrowserRouter } from "react-router-dom";
import { createRoot } from "react-dom/client";
import { ErrorBoundary } from "react-error-boundary";
import { StrictMode } from 'react';
import { QueryClientProvider } from "@tanstack/react-query";
import queryClient from "@/libs/queryClient.js";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { ENV } from "@/constants/env.js";
import App from "@/App.jsx";
import Fallback from "@/components/Fallback";

createRoot(document.getElementById("root")).render(
    <StrictMode>
        <QueryClientProvider client={queryClient}>
            <BrowserRouter>
                <ErrorBoundary FallbackComponent={Fallback}>
                    <App />
                </ErrorBoundary>
                {ENV === 'local' && <ReactQueryDevtools initialIsOpen={true} />}
            </BrowserRouter>
        </QueryClientProvider>
    </StrictMode>
);