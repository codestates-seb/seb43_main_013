import { ChevronUpIcon, ChevronDownIcon } from "../HeaderIcon";
import NickModal from "./NickModal";

const NameButton = ({ nickModal }: { nickModal: boolean }) => {
  return (
    <div className="top-2 right-10 static flex justify-center">
      {nickModal ? (
        <>
          <NickModal />
          <ChevronUpIcon className="w-7 cursor-pointer" />
        </>
      ) : (
        <ChevronDownIcon className="w-7 cursor-pointer" />
      )}
    </div>
  );
};

export default NameButton;
