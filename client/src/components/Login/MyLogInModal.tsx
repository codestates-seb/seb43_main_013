import { useState, useRef, useEffect } from "react";
import Link from "next/link";
import { twMerge } from "tailwind-merge";

// api
import { apiLogIn } from "@/apis";

// hook
import { useLoadingStore } from "@/store";
import useCustomToast from "@/hooks/useCustomToast";
import useLogInModal from "@/store/useLogInStore";
import { useMemberStore } from "@/store/useMemberStore";
import { useTokenStore } from "@/store/useTokenStore";

import Input from "@/components/AuthForm/Input";
import useSignUpStore from "@/store/useSignUpStore";

/** 2023/05/20 - 로그인 모달 - by 1-blue */
const MyLogInModal = () => {
  const toast = useCustomToast();
  const { loading } = useLoadingStore();
  const { setMember } = useMemberStore();
  const { setAccessToken, setRefreshToken } = useTokenStore();

  const [errors, setErrors] = useState({
    email: "",
    password: "",
  });

  const modalRef = useRef<HTMLFormElement>(null);
  const { closeLogInModal } = useLogInModal();
  const { openSignUpModal } = useSignUpStore();

  /** 2023/05/20 - 외부 클릭 시 모달 닫기 - by 1-blue */
  useEffect(() => {
    const modalCloseHandler = (e: MouseEvent) => {
      if (!(e.target instanceof HTMLElement)) return;
      if (e.target instanceof HTMLButtonElement) return;
      if (!modalRef.current) return;
      // 위쪽은 부가적인 부분이라 수정/삭제를 해도 되고 아래 부분이 핵심
      // 현재 클릭한 엘리먼트가 모달의 내부에 존재하는 엘리먼트인지 확인
      if (modalRef.current.contains(e.target)) return;

      if (!confirm("정말 로그인창을 닫으시겠습니까?")) return;

      closeLogInModal();
    };

    window.addEventListener("click", modalCloseHandler);
    return () => window.removeEventListener("click", modalCloseHandler);
  }, []);

  const onSubmit: React.FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    const values: string[] = [];
    const formData = new FormData(e.currentTarget);

    for (let value of formData.values()) {
      if (typeof value !== "string") continue;

      values.push(value);
    }

    const [email, password] = values;

    setErrors({ email: "", password: "" });

    if (!email.trim()) {
      setErrors((errors) => ({ ...errors, email: "** 이메일을 입력해주세요! **" }));
      toast({ title: "이메일을 입력해주세요!", status: "warning" });
      return;
    }
    if (!/[a-z0-9]+@[a-z]+\.[a-z]{2,3}/.test(email.trim())) {
      setErrors((errors) => ({ ...errors, email: "** 이메일 형식에 맞게 입력해주세요! **" }));
      toast({ title: "이메일 형식에 맞게 입력해주세요!", status: "warning" });
      return;
    }
    if (!password.trim()) {
      setErrors((errors) => ({ ...errors, password: "** 비밀번호를 입력해주세요! **" }));
      toast({ title: "비밀번호를 입력해주세요!", status: "warning" });
      return;
    }

    try {
      loading.start();

      const { authorization, refreshtoken, ...member } = await apiLogIn({ username: email, password });

      setMember(member);
      setAccessToken(authorization);
      setRefreshToken(refreshtoken);

      localStorage.setItem("accessToken", authorization);
      localStorage.setItem("refreshToken", refreshtoken);
      localStorage.setItem("member", JSON.stringify(member));

      toast({ title: "로그인에 성공했습니다.", status: "success" });
      closeLogInModal();
    } catch (error) {
      console.error(error);

      toast({ title: "로그인에 실패했습니다.", status: "error" });
    } finally {
      loading.end();
    }
  };

  return (
    <article className="fixed inset-0 backdrop-blur-sm z-10 animate-fade-up">
      <form
        onSubmit={onSubmit}
        className="py-6 px-8 bg-thick-bg shadow-[#232323] shadow-lg flex flex-col space-y-6 mx-auto min-w-[300px] max-w-[400px] w-[40vw] mt-[16vh] rounded-lg"
        ref={modalRef}
      >
        <h1 className="text-center text-4xl font-bold">CC - 로그인</h1>

        <div className="flex flex-col">
          <Input type="text" name="email" placeholder="ex) admin@gmail.com" error={errors.email} />
          <Input type="password" name="password" placeholder="ex) 123456789a!" error={errors.password} />

          <div className={twMerge("flex justify-between", errors.password && "mt-3")}>
            <Link
              href="/"
              className="text-xs underline-offset-4 transition-colors hover:text-main-400 focus:text-main-400 hover:underline focus:outline-none focus:underline"
            >
              비밀번호 수정 ( X )
            </Link>
            <button
              type="button"
              onClick={() => {
                closeLogInModal();
                openSignUpModal();
              }}
              className="text-xs underline-offset-4 transition-colors hover:text-main-400 focus:text-main-400 hover:underline focus:outline-none focus:underline"
            >
              회원가입
            </button>
          </div>
        </div>

        <button
          type="submit"
          className="border-2 border-fg text py-2 rounded-md text-xl transition-colors hover:border-main-500 hover:text-main-500 focus:outline-none focus:border-main-500 focus:text-main-500"
        >
          로그인
        </button>

        {/* FIXME: 각 로고 넣기 */}
        <div className="flex justify-evenly">
          <a
            href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=d7774b0de8bd81c958657f202701d306&redirect_uri=http://localhost:8080/auth/kakao/callback"
            className="w-14 h-14 border-2 rounded-full flex justify-center items-center"
          >
            <span>K</span>
          </a>
          <a
            href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=JAnr85GxwFcBiBMCvdpL&state=vninaeonfd&redirect_uri=http://localhost:8080/auth/naver/callback"
            className="w-14 h-14 border-2 rounded-full flex justify-center items-center"
          >
            <span>N</span>
          </a>
          <a
            href="http://localhost:8080/oauth2/authorization/google"
            className="w-14 h-14 border-2 rounded-full flex justify-center items-center"
          >
            <span>G</span>
          </a>
        </div>
      </form>
    </article>
  );
};

export default MyLogInModal;
