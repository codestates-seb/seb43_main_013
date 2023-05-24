/** 2023/05/24 - 게시글 작성에 대한 정보 - by 1-blue */
const Info = () => {
  return (
    <blockquote className="flex mx-4 mb-4 rounded-md py-3 px-4 bg-main-400 space-x-2 shadow-black/40 shadow-sm">
      <span className="text-lg">💡</span>
      <p className="text-lg text-white">
        "*"는 필수로 입력해야 합니다.
        <br />
        "#"을 접두사로 사용하면 해시태그처럼 생성이 됩니다. ( ex) #피드백 )
      </p>
    </blockquote>
  );
};

export default Info;
