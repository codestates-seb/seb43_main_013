import Avatar from "@/components/Avatar";
import { useState } from "react";
import { XMarkIcon } from "@heroicons/react/24/solid";
import NameButton from "@/components/Header/NameButton";
import NavRoute from "@/components/Navbar/NavRoute";

interface MobileModal {
  profileSrc: string;
  nickName: string;
  setMobileOpen: React.Dispatch<boolean>;
}
const MobileModal: React.FC<MobileModal> = ({ profileSrc, nickName, setMobileOpen }) => {
  const nickState = useState(false);
  const [nickModal, setNickModal] = nickState;

  return (
    <div className="border-l-2 border-black bg-main-100 absolute w-52 right-0 top-0 md:hidden h-screen z-20">
      <div
        onClick={() => setNickModal(!nickModal)}
        className="w-full h-32 flex flex-col justify-center items-center mt-4 mb-16"
      >
        <Avatar src={profileSrc || ""} className="mr-2 mb-3 w-16 h-16" />
        <div className="w-32 relative flex justify-center">
          <span className="mr-2 text-4xl cursor-pointer relative">{nickName}</span>
          <NameButton nickState={nickState} />
        </div>
      </div>
      <NavRoute />
      <XMarkIcon className="absolute top-0 w-8 hover:bg-main-300" onClick={() => setMobileOpen(false)} />
    </div>
  );
};

export default MobileModal;
