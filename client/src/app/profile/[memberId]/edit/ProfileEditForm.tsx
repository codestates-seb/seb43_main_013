"use client";

import { useEffect, useRef, useState } from "react";
import { useRouter } from "next/navigation";
import { XCircleIcon, CheckBadgeIcon } from "@heroicons/react/24/solid";
import { useQueryClient } from "@tanstack/react-query";

// api
import { apiConfirmPassword } from "@/apis/password";
import { apiLogOut, apiSignOut, apiUpdateMember } from "@/apis";

// hook
import { useMemberStore } from "@/store/useMemberStore";
import useCustomToast from "@/hooks/useCustomToast";

// component
import Form from "@/components/Board/Form";
import { validateYoutubeURL } from "@/libs";
import { isAxiosError } from "axios";
import { twMerge } from "tailwind-merge";

// type
interface Props {
  memberId: number;
}

/** 2023/05/15 - 프로필 수정 폼 - by 1-blue */
const ProfileEditForm: React.FC<Props> = ({ memberId }) => {
  const router = useRouter();
  const { member, setMember } = useMemberStore();
  const toast = useCustomToast();

  const [password, setPassword] = useState("");
  const [isConfirm, setIsConfirm] = useState(false);

  const [thumbnail, setThumbnail] = useState<FileList | null>(null);

  const queryClient = useQueryClient();

  /** 2023/05/18 - 비밀번호 확인 - by 1-blue */
  const onConfirmPassword = async () => {
    if (isConfirm) return;
    if (password.trim().length === 0) {
      return toast({ title: "비밀번호를 입력해주세요!", status: "warning" });
    }

    try {
      await apiConfirmPassword({ memberId, password });
      setIsConfirm(true);
      toast({ title: "비밀번호 인증에 성공했습니다.", status: "success" });
    } catch (error) {
      console.error(error);
      toast({ title: "비밀번호 인증에 실패했습니다.", status: "error" });
    }
  };

  /** 2023/05/24 - passwordCheck input ref - by 1-blue */
  const passwordValidateRef = useRef<HTMLInputElement>(null);

  /** 2023/05/15 - 프로필 수정 요청 - by 1-blue */
  const onSumbit: React.FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    if (!member) return;

    const values: string[] = [];
    const formData = new FormData(e.currentTarget);

    for (let value of formData.values()) {
      if (typeof value !== "string") continue;

      values.push(value);
    }

    let pw = "";
    let nickname = "";
    let phone = "";
    let link = "";
    let introduction = "";

    // OAuth 로그인 유저인 경우
    if (member.oauth) {
      nickname = values[0];
      phone = values[1];
      link = values[2];
      introduction = values[3];

      if (nickname.trim().length === 0) {
        return toast({ title: "수정할 이름을 입력해주세요!", status: "warning" });
      }
      if (!phone) {
        return toast({ title: "휴대폰 번호를 입력해주세요!", status: "warning" });
      }
      if (!/^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/.test(phone)) {
        return toast({ title: "휴대폰 번호 형식에 맞게 입력해주세요!", status: "warning" });
      }
      if (link.trim().length !== 0) {
        if (!validateYoutubeURL(link)) {
          return toast({ title: "유튜브 링크의 형식에 맞게 입력해주세요!", status: "warning" });
        }
      }
    }
    // 일반 로그인 유저인 경우
    else {
      pw = values[0];
      nickname = values[1];
      phone = values[2];
      link = values[3];
      introduction = values[4];

      if (!isConfirm) {
        return toast({ title: "비밀번호를 먼저 확인해주세요!", status: "warning" });
      }
      if (pw.trim().length === 0) {
        return toast({ title: "비밀번호를 입력해주세요!", status: "warning" });
      }
      if (nickname.trim().length === 0) {
        return toast({ title: "수정할 이름을 입력해주세요!", status: "warning" });
      }
      if (!phone) {
        return toast({ title: "휴대폰 번호를 입력해주세요!", status: "warning" });
      }
      if (!/^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/.test(phone)) {
        return toast({ title: "휴대폰 번호 형식에 맞게 입력해주세요!", status: "warning" });
      }
      if (link.trim().length !== 0) {
        if (!validateYoutubeURL(link)) {
          return toast({ title: "유튜브 링크의 형식에 맞게 입력해주세요!", status: "warning" });
        }
      }
    }

    try {
      // FIXME: 프로필 이미지
      await apiUpdateMember({
        password: pw,
        nickname: member.nickname === nickname ? null : nickname,
        phone: member.phone === phone ? null : phone,
        link: member.link === link ? null : link,
        introduction: member.introduction === introduction ? null : introduction,
        memberId,
      });
      // TODO: 이놈 대체 왜
      apiLogOut({});

      toast({ title: "정보를 수정했습니다. 다시 로그인해주세요!", status: "success" });

      queryClient.invalidateQueries(["member", memberId]);

      localStorage.clear();
      setMember(null);
    } catch (error) {
      console.error(error);

      if (isAxiosError(error)) {
        toast({ title: error.response?.data.message || "정보 수정에 실패했습니다.", status: "error" });
      } else {
        toast({ title: "정보 수정에 실패했습니다.", status: "error" });
      }
    }
  };

  const confirmRef = useRef<HTMLButtonElement>(null);

  useEffect(() => {
    if (member) return;

    router.replace("/login");
  }, [member, router]);

  /** 2023/05/24 - 회원탈퇴 관련 - by 1-blue */
  const onClickSignOut = async () => {
    if (!member) return;

    // oauth인 경우
    if (member.oauth) {
      if (!confirm("정말 계정을 삭제하시겠습니까?")) return;

      await apiSignOut({ memberId: member.memberId });
      apiLogOut({});

      toast({ title: "회원탈퇴가 정상적으로 진행되었습니다.\n메인 페이지로 이동됩니다.", status: "info" });

      router.replace("/");
    }
    // 일반 로그인인 경우
    else {
      if (!isConfirm) {
        passwordValidateRef.current?.select();
        return toast({ title: "비밀번호를 먼저 확인하고 탈퇴를 진행해주세요!", status: "warning" });
      }
      if (!confirm("정말 계정을 삭제하시겠습니까?")) return;

      await apiSignOut({ memberId: member.memberId });
      apiLogOut({});

      toast({ title: "회원탈퇴가 정상적으로 진행되었습니다.\n메인 페이지로 이동됩니다.", status: "info" });

      router.replace("/");
    }
  };

  if (!member) return <></>;

  return (
    <form
      onSubmit={onSumbit}
      className="flex-1 flex flex-col border-r px-8 pt-4 pb-8 space-y-2 bg-white shadow-black/40 shadow-sm m-4 rounded-lg"
    >
      <div className="flex justify-between">
        <Form.Photo
          setThumbnail={setThumbnail}
          className="h-[320px] mb-4"
          name="프로필 이미지"
          defaultPhoto={member.profileImageUrl}
        />

        <button
          type="button"
          onClick={onClickSignOut}
          className="self-start text-red-400 transition-colors hover:text-red-500"
        >
          회원 탈퇴
        </button>
      </div>
      {/* OAuth가 아니라면 비밀번호 확인 */}
      {member.oauth || (
        <div className="flex flex-col space-y-1 pb-4">
          <label htmlFor="password" className="font-bold cursor-pointer">
            {isConfirm ? "수정할 비밀번호 입력" : "현재 비밀번호 입력"}
          </label>
          <div className="flex flex-col space-y-1 relative">
            <input
              id="password"
              name="비밀번호"
              type="password"
              placeholder="ex) keyboard1cat-@"
              ref={passwordValidateRef}
              className={twMerge(
                "flex-1 px-3 py-1 bg-transparent rounded-sm text-lg border-2 border-main-300 focus:outline-none focus:border-main-500 placeholder:text-sm placeholder:font-bold",
                !isConfirm && "border-red-500",
              )}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  confirmRef.current?.click();

                  e.preventDefault();
                }
              }}
            />
            {!isConfirm ? (
              <span className="text-red-500 text-sm">
                ** 비밀번호를 입력하고 엔터키나 우측 빨간 버튼을 눌러주세요! **
              </span>
            ) : (
              <span className="text-green-600 text-sm">** 인증되었습니다. 수정할 비밀번호를 입력해주세요! **</span>
            )}
            <button type="button" className="absolute top-0.5 right-2" ref={confirmRef} onClick={onConfirmPassword}>
              {isConfirm ? (
                <CheckBadgeIcon className="w-7 h-7 text-green-600" />
              ) : (
                <XCircleIcon className="w-7 h-7 text-red-600 transition-colors hover:text-red-700" />
              )}
            </button>
          </div>
        </div>
      )}
      <Form.Input name="닉네임" placeholder="ex) 1-blue" defaultValue={member.nickname} />
      <Form.Input name="휴대폰 번호" placeholder="ex) 010-1234-5678" defaultValue={member.phone} />
      <Form.Input
        name="유튜브 링크"
        placeholder="ex) https://www.youtube.com/@15ya.fullmoon"
        defaultValue={member.link}
      />
      <Form.Textarea
        name="자기소개"
        placeholder={
          "안녕하세요 새싹 유튜버 **입니다.\n많은 조언 해주시면 감사하겠습니다.\n유튜브 링크: https://www.youtube.com/@15ya.fullmoon"
        }
        className="min-h-[20vh] resize-none overflow-hidden"
        defaultValue={member.introduction}
      />
      <Form.Button>수정</Form.Button>
    </form>
  );
};

export default ProfileEditForm;
