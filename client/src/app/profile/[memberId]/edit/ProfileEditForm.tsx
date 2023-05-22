"use client";

import { useState } from "react";
import { notFound, useRouter } from "next/navigation";
import { XCircleIcon, CheckBadgeIcon } from "@heroicons/react/24/solid";
import { useQueryClient } from "@tanstack/react-query";

// api
import { apiConfirmPassword } from "@/apis/password";
import { apiLogOut, apiUpdateMember } from "@/apis";

import validate from "@/libs/validate";

// hook
import { useMemberStore } from "@/store/useMemberStore";
import useCustomToast from "@/hooks/useCustomToast";

// component
import Form from "@/components/Board/Form";

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

    const [pw, nickname, phone, link, introduction] = values;

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
    if (!validate.youtubeURL(link)) {
      return toast({ title: "유튜브 링크를 입력해주세요!", status: "warning" });
    }

    try {
      // FIXME: 프로필 이미지
      await apiUpdateMember({
        password: pw,
        nickname,
        phone,
        link,
        introduction,
        memberId,
        profileImageUrl: member.profileImageUrl,
      });
      await apiLogOut({});

      toast({ title: "정보를 수정했습니다. 다시 로그인해주세요!", status: "success" });

      queryClient.invalidateQueries(["member", memberId]);

      localStorage.clear();
      setMember(null);

      router.replace(`/login`);
    } catch (error) {
      console.error(error);

      toast({ title: "정보 수정에 실패했습니다.", status: "error" });
    }
  };

  if (!member) return notFound();

  return (
    <form
      onSubmit={onSumbit}
      className="flex-1 flex flex-col border-r px-8 pt-4 pb-8 space-y-2 bg-white shadow-2xl m-4 rounded-lg"
    >
      <Form.Photo
        setThumbnail={setThumbnail}
        className="h-[320px] mb-4"
        name="프로필 이미지"
        defaultPhoto={member.profileImageUrl}
      />
      {/* OAuth가 아니라면 비밀번호 확인 */}
      {member.oauth || (
        <div className="flex flex-col space-y-1 pb-4">
          <label htmlFor="password" className="font-bold cursor-pointer">
            {isConfirm ? "수정할 비밀번호 입력" : "현재 비밀번호 입력"}
          </label>
          <div className="flex">
            <input
              id="password"
              name="비밀번호"
              type="password"
              placeholder="ex) keyboard1cat-@"
              className="flex-1 px-3 py-1 bg-transparent rounded-sm text-lg border-2 border-main-300 focus:outline-none focus:border-main-500 placeholder:text-sm placeholder:font-bold"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <button type="button" className="ml-2">
              {isConfirm ? (
                <CheckBadgeIcon className="w-8 h-8 text-green-600" />
              ) : (
                <XCircleIcon
                  className="w-8 h-8 text-red-600 transition-colors hover:text-red-700"
                  onClick={onConfirmPassword}
                />
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
