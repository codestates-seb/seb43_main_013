"use input";
import useInput from "@/hooks/useInput";

const emailBind = useInput();
const pwBind = useInput();
const nameBind = useInput();
const nicknameBind = useInput();
const phoneBind = useInput();
const linkBind = useInput();
const introBind = useInput();
const imageBind = useInput();

const totalBind = { emailBind, pwBind, nameBind, nicknameBind, phoneBind, linkBind, introBind, imageBind };
export default totalBind;
