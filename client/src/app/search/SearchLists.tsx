"use client";

import Avatar from "@/components/Avatar";
import { useFetchSearch } from "@/hooks/query/useFetchSearch";
import { getTimeDiff } from "@/libs/time";
import type { ApiFetchSearchResponse, SearchBoard, SearchMember } from "@/types/api";
import Link from "next/link";
import {
  EyeIcon as OViewIcon,
  HandThumbUpIcon as OLikeIcon,
  ChatBubbleLeftRightIcon as OCommentsIcon,
} from "@heroicons/react/24/outline";
import InfiniteScrollContainer from "@/components/InfiniteScrollContainer";
import NotSearch from "@/components/Svg/NotSearch";

interface Props {
  keyword: string;
  initialData?: ApiFetchSearchResponse;
}

const table = {
  FREEBOARD: "free",
  FEEDBACKBOARD: "feedback",
  JOBBOARD: "job",
  PROMOTIONBOARD: "promotion",
};

/** 2023/05/22 - 검색된 리스트 컴포넌트 - by 1-blue */
const SearchLists: React.FC<Props> = ({ keyword, initialData }) => {
  const { data, fetchNextPage, hasNextPage } = useFetchSearch({ keyword, page: 1, size: 10, initialData });

  const boardPages = data?.pages.map((page) => page.data.filter((v): v is SearchBoard => v.boardType !== "MEMBER"));
  const memberPages = data?.pages.map((page) => page.data.filter((v): v is SearchMember => v.boardType === "MEMBER"));

  return (
    <article className="mb-12">
      <InfiniteScrollContainer fetchMore={fetchNextPage} hasMore={hasNextPage}>
        {/* 게시글들 */}
        <section className="mx-4 mt-12 space-y-4">
          <h3 className="text-2xl">
            📜 검색된 게시글들 📜
            <span className="text-base"> ( {boardPages?.reduce((p, c) => p + c.length, 0)} 개 )</span>
          </h3>
          {!!boardPages?.reduce((p, c) => p + c.length, 0) ? (
            <ul className="bg-white py-8 px-6 space-y-8 shadow-black/40 shadow-sm">
              {/* 게시글들 */}
              {boardPages?.map((boardPage) =>
                boardPage.map((board) => (
                  <li
                    key={board.memberId}
                    className="flex flex-col space-y-4 px-4 py-6 shadow-black/20 shadow-sm bg-sub-100 rounded-md transition-shadow hover:shadow-black/30 hover:shadow-lg"
                  >
                    <Link
                      href={`/search?keyword=${board.categoryName}`}
                      className="inline-block self-start text-xs font-bold px-2 py-1 border-2 border-main-500 rounded-md text-main-500 transition-colors hover:bg-main-500 hover:text-white"
                    >
                      {board.categoryName}
                    </Link>

                    <Link href={`/${table[board.boardType]}/${board.id}`} className="inline-block">
                      <h4 className="max-w-3/4 text-2xl truncate-1 decoration-2 underline-offset-8 transition-colors hover:text-main-500 hover:underline">
                        {board.title}
                      </h4>
                    </Link>

                    <p
                      className="truncate-3"
                      dangerouslySetInnerHTML={{ __html: board.content.replace(/<[^>]*>?/g, "") }}
                    />

                    <div className="flex justify-between">
                      <div className="flex items-center space-x-3">
                        <Avatar src={board.profileImageUrl} href={`/profile/${board.memberId}`} className="w-12 h-12" />
                        <div className="flex flex-col">
                          <span className="text-sub-700 font-bold">{board.nickname || board.name}</span>
                          <time className="text-sub-400 text-xs">{getTimeDiff(board.createdAt)}</time>
                        </div>
                      </div>

                      <div className="flex self-end space-x-4">
                        <div className="flex items-center space-x-1.5">
                          <OViewIcon className="w-6 h-6 text-sub-700" />
                          <span className="text-sub-500">{board.viewCount}</span>
                        </div>
                        <div className="flex items-center space-x-1.5">
                          <OLikeIcon className="w-6 h-6 text-sub-700" />
                          <span className="text-sub-500">{board.likeCount}</span>
                        </div>
                        <div className="flex items-center space-x-1.5">
                          <OCommentsIcon className="w-6 h-6 text-sub-700" />
                          <span className="text-sub-500">{board.commentCount}</span>
                        </div>
                      </div>
                    </div>
                  </li>
                )),
              )}
            </ul>
          ) : (
            <section className="bg-white py-8 px-6 shadow-black/40 shadow-sm space-y-8">
              <NotSearch />
              <h4 className="text-center text-3xl text-sub-600">검색된 게시글이 없습니다.</h4>
            </section>
          )}
        </section>

        {/* 유저들 */}
        <section className="mx-4 mt-12 space-y-4">
          <h3 className="text-2xl">
            🧑‍💻 검색된 유저들 🧑‍💻
            <span className="text-base"> ( {memberPages?.reduce((p, c) => p + c.length, 0)} 개 )</span>
          </h3>
          {!!memberPages?.reduce((p, c) => p + c.length, 0) ? (
            <ul className="bg-white py-8 px-6 shadow-black/40 shadow-sm grid grid-cols-1 xs:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4">
              {memberPages?.map((memberPage) =>
                memberPage.map((member) => (
                  <li
                    key={member.memberId}
                    className="p-8 space-y-4 bg-sub-50 shadow-black/40 shadow-sm transition-shadow hover:shadow-black/30 hover:shadow-lg m-4 rounded-lg flex flex-col items-center"
                  >
                    <Avatar
                      src={member.profileImageUrl}
                      alt="유저의 프로필 이미지"
                      className="w-16 h-16 rounded-full shadow-md"
                      href={`/profile/${member.memberId}`}
                    />

                    {/* 이름 */}
                    <Link
                      href={`/profile/${member.memberId}`}
                      className="flex flex-col items-center space-y-1 underline-offset-4 decoration-2 hover:text-main-400 hover:underline"
                    >
                      <h3 className="font-bold">{member.nickname}</h3>
                    </Link>

                    {/* 자기소개 */}
                    {member?.introduction && (
                      <p className="w-3/4 whitespace-pre-line text-center font-bold text-sm truncate-3">
                        {member.introduction}
                      </p>
                    )}
                    {/* 팔로잉 팔로워 카운터 */}
                    <div className="space-x-6 text-sm flex">
                      <div className="flex flex-col items-center text-sm transition-colors">
                        <span className="text-sm">팔로잉</span>
                        <span className="text-sm font-bold">{member.followings}명</span>
                      </div>
                      <div className="border border-sub-300" />
                      <div className="flex flex-col items-center text-sm transition-colors">
                        <span className="text-sm">팔로워</span>
                        <span className="text-sm font-bold">{member.followers}명</span>
                      </div>
                    </div>
                  </li>
                )),
              )}
            </ul>
          ) : (
            <section className="bg-white py-8 px-6 shadow-black/40 shadow-sm space-y-8">
              <NotSearch />
              <h4 className="text-center text-3xl text-sub-600">검색된 유저가 없습니다.</h4>
            </section>
          )}
        </section>
      </InfiniteScrollContainer>
    </article>
  );
};

export default SearchLists;
