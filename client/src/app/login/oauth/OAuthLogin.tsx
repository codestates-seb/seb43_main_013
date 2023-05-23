"use client";
import { useEffect } from "react";
import { useSearchParams } from "next/navigation";
import { useTokenStore } from "@/store/useTokenStore";
import { useMemberStore } from "@/store/useMemberStore";
import { useRouter } from "next/navigation";
import useGetOAuth from "@/hooks/useGetOAuth";

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
