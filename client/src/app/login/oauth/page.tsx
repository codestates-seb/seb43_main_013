"use client";

import { useEffect } from "react";
import { useSearchParams } from "next/navigation";
import { serverInstance } from "@/apis";
import { useTokenStore } from "@/store/useTokenStore";
import { useMemberStore } from "@/store/useMemberStore";
import { useRouter } from "next/navigation";
import useCustomToast from "@/hooks/useCustomToast";
import FullSpinner from "@/components/Spinner/FullSpinner";

const Page = () => {
  const router = useRouter();
  const toast = useCustomToast();
  const { setAccessToken, setRefreshToken } = useTokenStore();
  const { setMember } = useMemberStore();

  const rename = useSearchParams();
  const accessToken = rename.get("access_token");
  const refreshToken = rename.get("refresh_token");

  useEffect(() => {
    if (!accessToken) return;
    if (!refreshToken) return;

    (async () => {
      const response = await serverInstance.get(process.env.NEXT_PUBLIC_BASE_URL + "/api/login/oauth", {
        params: { access_token: accessToken, refresh_token: refreshToken },
      });

      setMember(response.data);
      setAccessToken(accessToken);
      setRefreshToken(refreshToken);

      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("member", JSON.stringify(response.data));

      toast({ title: "로그인되었습니다.\n메인 페이지로 이동됩니다!", status: "success" });
      router.replace("/");
    })();
  }, [accessToken, refreshToken]);

  return <FullSpinner />;
};

export default Page;
