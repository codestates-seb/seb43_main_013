import { useContext, createContext } from "react";

const ModalValueContext = createContext(false);
const ModalActionContext = createContext({});

const useModalValue = () => {
  const value = useContext(ModalValueContext);
  if (value === undefined) {
    throw new Error("useModalValue should be used within CounterProvider");
  }
  return value;
};

const useModalActions = () => {
  const value = useContext(ModalActionContext);
  if (value === undefined) {
    throw new Error("useModalActions should be used within CounterProvider");
  }
  return value;
};

export { ModalActionContext, ModalValueContext, useModalValue, useModalActions };
