"use client";

import { useEffect } from "react";
import { useSearchParams } from "next/navigation";

const OAuthLogin = () => {
  useEffect(() => {
    useGetOAuth(body, propsBind);
  }, []);
  const searchParams = useSearchParams();

  const access_token = searchParams.get("access_token");
  const refresh_token = searchParams.get("refresh_token");

  const body = { access_token, refresh_token };

  const { setAccessToken, setRefreshToken } = useTokenStore();
  const { setMember } = useMemberStore();
  const router = useRouter();
  const propsBind = { setAccessToken, setRefreshToken, setMember, router };

  return <></>;
};
export default OAuthLogin;

import axios from "axios";
import { useTokenStore } from "@/store/useTokenStore";
import { useMemberStore } from "@/store/useMemberStore";
import { useRouter } from "next/navigation";

const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;

interface getOAuth {
  (body: { access_token: string | null; refresh_token: string | null }, propsBind: any): Promise<void>;
}

const useGetOAuth: getOAuth = async (body, propsBind) => {
  const { access_token, refresh_token } = body;
  const { setAccessToken, setRefreshToken, setMember, router } = propsBind;

  const response = await axios.get(
    `${baseUrl}/api/login/oauth?access_token=${access_token}&refresh_token=${refresh_token}`,
  );
  //   //   axios.post(`/api/login/oauth?access_token=${access_token}&refresh_token=${refresh_token}`, body);

  const newMember = JSON.stringify(response.data);
  if (access_token) {
    setAccessToken(access_token);
    localStorage.setItem("accessToken", access_token);
  }
  if (refresh_token) {
    setRefreshToken(refresh_token);
    localStorage.setItem("refreshToken", refresh_token);
  }

  setMember(newMember);
  localStorage.setItem("member", newMember);

  router.push("/");
};
