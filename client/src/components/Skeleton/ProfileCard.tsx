// component
import Skeleton from ".";

/** 2023/05/17 - 프로필 카드 스켈레톤 UI 컴포넌트 - by 1-blue */
const ProfileCard = () => {
  return (
    <div className="p-8 space-y-4 bg-white shadow-2xl m-4 rounded-lg flex flex-col items-center lg:w-[320px]">
      <Skeleton.Circle className="w-24 h-24 rounded-full shadow-md" />

      <div className="flex flex-col items-center space-y-1">
        <Skeleton.Square className="h-5" />
        <Skeleton.Square className="h-5 w-24" />
        <Skeleton.Square className="h-5 w-20" />
      </div>

      <div className="flex space-x-6">
        <Skeleton.Square className="px-2 py-1 rounded-md" />
        <Skeleton.Square className="px-2 py-1 rounded-md" />
      </div>

      {/* 자기소개 */}
      <div className="flex flex-col w-full space-y-1">
        <Skeleton.Text className="h-6" />
        <Skeleton.Text className="h-6" />
        <Skeleton.Text className="h-6" />
      </div>

      {/* 유튜브 링크 */}
      <div className="flex w-full items-center justify-between">
        <Skeleton.Square className="w-6 h-6" />
        <Skeleton.Square className="w-6 h-6" />
      </div>
    </div>
  );
};

export default ProfileCard;
