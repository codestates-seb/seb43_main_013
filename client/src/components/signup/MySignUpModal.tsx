import { useState, useRef, useEffect } from "react";
import Link from "next/link";

// api
import { apiLogIn, apiSignUp } from "@/apis";

// hook
import { useLoadingStore } from "@/store";
import useCustomToast from "@/hooks/useCustomToast";
import useSignUpStore from "@/store/useSignUpStore";

import Input from "../AuthForm/Input";
import validate from "@/libs/validate";
import useLogInModal from "@/store/useLogInStore";
import { isAxiosError } from "axios";

/** 2023/05/20 - 회원가입 모달 - by 1-blue */
const MySignUpModal = () => {
  const toast = useCustomToast();
  const { loading } = useLoadingStore();
  const { openLogInModal } = useLogInModal();

  const modalRef = useRef<HTMLFormElement>(null);
  const { closeSignUpModal } = useSignUpStore();

  /** 2023/05/20 - 외부 클릭 시 모달 닫기 - by 1-blue */
  useEffect(() => {
    const modalCloseHandler = (e: MouseEvent) => {
      if (!(e.target instanceof HTMLElement)) return;
      if (e.target instanceof HTMLButtonElement) return;
      if (!modalRef.current) return;
      if (modalRef.current.contains(e.target)) return;

      if (!confirm("정말 회원가입창을 닫으시겠습니까?")) return;

      closeSignUpModal();
    };

    window.addEventListener("click", modalCloseHandler);
    return () => window.removeEventListener("click", modalCloseHandler);
  }, []);

  const [errors, setErrors] = useState({
    email: "",
    password: "",
    name: "",
    nickname: "",
    phone: "",
    link: "",
    introduction: "",
  });

  const onSubmit: React.FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    const values: string[] = [];
    const formData = new FormData(e.currentTarget);

    for (let value of formData.values()) {
      if (typeof value !== "string") continue;

      values.push(value);
    }

    const [email, password, name, nickname, phone, link, introduction] = values;

    setErrors({ email: "", password: "", name: "", nickname: "", phone: "", link: "", introduction: "" });

    if (email.trim() === "admin@gmail.com") {
      setErrors((errors) => ({ ...errors, email: "** 생성할 수 없는 이메일입니다. **" }));
      toast({ title: "생성할 수 없는 이메일입니다.", status: "warning" });
      return;
    }
    if (!email.trim()) {
      setErrors((errors) => ({ ...errors, email: "** 이메일을 입력해주세요! **" }));
      toast({ title: "이메일을 입력해주세요!", status: "warning" });
      return;
    }
    if (!password.trim()) {
      setErrors((errors) => ({ ...errors, password: "** 비밀번호를 입력해주세요! **" }));
      toast({ title: "비밀번호를 입력해주세요!", status: "warning" });
      return;
    }
    if (!name.trim()) {
      setErrors((errors) => ({ ...errors, name: "** 이름을 입력해주세요! **" }));
      toast({ title: "이름을 입력해주세요!", status: "warning" });
      return;
    }
    if (!nickname.trim()) {
      setErrors((errors) => ({ ...errors, nickname: "** 별칭을 입력해주세요! **" }));
      toast({ title: "별칭을 입력해주세요!", status: "warning" });
      return;
    }
    if (!validate.phone(phone.trim())) {
      setErrors((errors) => ({ ...errors, phone: "** 휴대폰 번호를 입력해주세요! **" }));
      toast({ title: "휴대폰 번호를 입력해주세요!", status: "warning" });
      return;
    }
    if (link.trim()) {
      if (validate.youtubeURL(link.trim()))
        setErrors((errors) => ({ ...errors, link: "** 유튜브 링크를 제대로 입력해주세요! **" }));
      toast({ title: "유튜브 링크를 제대로 입력해주세요!", status: "warning" });
      return;
    }

    try {
      loading.start();

      await apiSignUp({ email, password, name, nickname, phone, link, introduction });

      toast({ title: "회원가입에 성공했습니다.\n로그인창으로 전환됩니다.", status: "success" });
      closeSignUpModal();
      openLogInModal();
    } catch (error) {
      console.error(error);

      if (isAxiosError(error)) {
        if (error.response?.data.message) toast({ title: error.response.data.message, status: "error" });
        else {
          toast({ title: "회원가입에 실패했습니다.", status: "error" });
        }
      } else {
        toast({ title: "회원가입에 실패했습니다.", status: "error" });
      }
    } finally {
      loading.end();
    }
  };

  return (
    <article className="fixed inset-0 backdrop-blur-sm z-10 flex justify-center items-center animate-fade-up">
      <form
        onSubmit={onSubmit}
        className="py-6 px-8 bg-thick-bg shadow-[#232323] shadow-lg flex flex-col space-y-5 mx-auto min-w-[300px] max-w-[400px] w-[40vw] rounded-lg"
        ref={modalRef}
      >
        <h1 className="text-center text-4xl font-bold">CC - 회원가입</h1>

        <div className="flex flex-col">
          <Input type="text" name="email" placeholder="ex) admin@gmail.com" error={errors.email} />
          <Input type="password" name="password" placeholder="ex) 123456789a!" error={errors.password} />
          <Input type="text" name="name" placeholder="ex) 한겨울" error={errors.name} />
          <Input type="text" name="nickname" placeholder="ex) pcc" error={errors.nickname} />
          <Input type="text" name="phone" placeholder="ex) 010-1234-4321" error={errors.phone} />
          <Input
            type="text"
            name="link"
            placeholder="ex) https://www.youtube.com/watch?v=ZfZnZmUlsuo"
            error={errors.link}
          />
          <Input type="text" name="introduction" placeholder="ex) " error={errors.introduction} />

          <div className="flex justify-between">
            <button
              type="button"
              onClick={() => {
                closeSignUpModal();
                openLogInModal();
              }}
              className="ml-auto text-xs underline-offset-4 transition-colors hover:text-main-400 focus:text-main-400 hover:underline focus:outline-none focus:underline"
            >
              로그인으로 이동
            </button>
          </div>
        </div>

        <button
          type="submit"
          className="border-2 border-fg text py-2 rounded-md text-xl transition-colors hover:border-main-500 hover:text-main-500 focus:outline-none focus:border-main-500 focus:text-main-500"
        >
          회원가입
        </button>
      </form>
    </article>
  );
};

export default MySignUpModal;
