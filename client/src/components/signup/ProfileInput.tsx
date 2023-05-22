interface Profile {
  profile: string;
  setProfile: React.Dispatch<string>;
}

const ProfileInput = ({ profile, setProfile }: Profile) => {
  return (
    <>
      <div className="w-full h-24">
        <div className="flex text-xl mb-2">{"프로필 이미지"}</div>
        <input
          type="file"
          className="w-full h-12 text-lg"
          value={profile}
          onChange={(e) => setProfile(e.target.value)}
        />
      </div>

      <div className="w-full h-44 border-2 border-black flex justify-center items-center rounded-lg mb-3 text-2xl">
        사진
      </div>
    </>
  );
};

export default ProfileInput;
