// 배포버전 코드 (예상)
// const googleLink = "https://api.hard-coding.com/oauth2/authorization/google";
// const naverLink =
//   "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=JAnr85GxwFcBiBMCvdpL&state=vninaeonfd&redirect_uri=https://api.hard-coding.com/auth/naver/callback";
// const kakaoLink =
//   "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=d7774b0de8bd81c958657f202701d306&redirect_uri=https://api.hard-coding.com/auth/kakao/callback";

// 개발버전 코드
const googleLink = "http://localhost:8080/oauth2/authorization/google";
const naverLink =
  "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=JAnr85GxwFcBiBMCvdpL&state=vninaeonfd&redirect_uri=http://localhost:8080/auth/naver/callback";
const kakaoLink =
  "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=d7774b0de8bd81c958657f202701d306&redirect_uri=http://localhost:8080/auth/kakao/callback";

const linkArr = [googleLink, naverLink, kakaoLink];
const linkText = ["구글 로그인", "네이버 로그인", "카카오 로그인"];

export { linkArr, linkText };
