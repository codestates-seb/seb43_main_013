import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

/** 2023/05/04 - 로그인 후 접근 가능한 URL - by 1-blue */
const authURLs = [];
/** 2023/05/04 - 로그인 후 접근 불가능한 URL - by 1-blue */
const nonAuthURLs = [];

export const middleware = (req: NextRequest) => {
  return NextResponse.next();
};

/**
 * 아래 네 가지 경우는 미들웨어를 거치지 않음
 * 1. /api
 * 2. /_next/static
 * 3. /_next/image
 * 4. /favicon.ico
 */
export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico).*)"],
};
