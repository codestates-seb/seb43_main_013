"use client";
import { useState, useMemo } from "react";
import { ModalActionContext, ModalValueContext } from "./useModal";

/** 2023/05/10 - Modal 전역상태 Provider - by Kadesti */
const ModalProvider: React.FC<React.PropsWithChildren> = ({ children }) => {
  const [modalState, setModalState] = useState(false);
  const modalActions = useMemo(
    () => ({
      openModal() {
        // setModalState(true);
        console.log("open");
      },
      closeModal() {
        // setModalState(false);
        console.log("close");
      },
    }),
    [],
  );

  return (
    <ModalActionContext.Provider value={modalActions}>
      <ModalValueContext.Provider value={modalState}>{children}</ModalValueContext.Provider>
    </ModalActionContext.Provider>
  );
};

export default ModalProvider;
