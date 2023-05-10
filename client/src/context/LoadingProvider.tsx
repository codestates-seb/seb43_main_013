"use client";

import { createContext, useMemo, useState } from "react";

// component
import FullSpinner from "@/components/Spinner/FullSpinner";

const LoadingContext = createContext<null | {
  startLoading(): void;
  endLoading(): void;
}>(null);

const LoadingProvider: React.FC<React.PropsWithChildren> = ({ children }) => {
  const [isLoading, setIsLoading] = useState(false);
  const actions = useMemo(
    () => ({
      startLoading() {
        setIsLoading(true);
      },
      endLoading() {
        setIsLoading(false);
      },
    }),
    [],
  );

  const value = useMemo(() => actions, [actions]);

  return (
    <LoadingContext.Provider value={value}>
      {isLoading && <FullSpinner />}
      {children}
    </LoadingContext.Provider>
  );
};

export { LoadingContext, LoadingProvider };
