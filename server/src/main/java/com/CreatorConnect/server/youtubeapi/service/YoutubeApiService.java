package com.CreatorConnect.server.youtubeapi.service;

import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.youtubeapi.dto.YoutubeApiDto;
import com.CreatorConnect.server.youtubeapi.entity.VideoEntity;
import com.CreatorConnect.server.youtubeapi.entity.VideoPK;
import com.CreatorConnect.server.youtubeapi.mapper.YoutubeApiMapper;
import com.CreatorConnect.server.youtubeapi.repository.YoutubeApiRepository;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class YoutubeApiService {
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;
    private final YoutubeApiRepository youtubeApiRepository;
    private final String apikey;
    private static YouTube youtube;
    private final YoutubeApiMapper mapper;

    public YoutubeApiService(YoutubeApiRepository youtubeApiRepository, @Value("${youtube.key}") String apikey, YoutubeApiMapper mapper) {
        this.youtubeApiRepository = youtubeApiRepository;
        this.apikey = apikey;
        this.mapper = mapper;
    }

    /**
     * <entity 저장 & dto변환 메서드>
     * 1. iterator 로 데이터 하나씩 꺼내기
     * 2. entity 저장 및 dto로 변환해서 dtoList에 추가
     */
    private void saveEntity(Iterator<Video> iteratorSearchResults, List<YoutubeApiDto.Details> responses, String categoryId) {
        // video id값 init
        Long id = 1L;

        // 다음 데이터가 있으면 루프
        while (iteratorSearchResults.hasNext()) {

            //데이터 반환
            Video singleVideo = iteratorSearchResults.next();

            if (singleVideo.getKind().equals("youtube#video")) {
                /** 썸네일 크기 지정 - medium
                 *             너비  *  높이
                 *  default  = 120  *  90
                 *  medium   = 320  *  180
                 *  high     = 480  *  360
                 *  standard = 640  *  480
                 *  maxres   = 1280 *  720
                */
                Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("maxres");

                // entity에 저장
                VideoPK videoPK = new VideoPK(categoryId,id);
                VideoEntity video = new VideoEntity();
                video.setVideoPK(videoPK);
                video.setYoutubeId(singleVideo.getId());
                video.setYoutubeUrl("https://youtu.be/" + singleVideo.getId());
                video.setThumbnailUrl(thumbnail.getUrl());
                // 타이틀 database에 넣을 때 이모지 제거
                video.setTitle(mysqlUtf8Safe(singleVideo.getSnippet().getTitle()));
                youtubeApiRepository.save(video);

                //entity - dto 변환
                YoutubeApiDto.Details youTubeDto = mapper.videoEntityToYoutubeApiDtoDetailsResponse(video);

                //dto List에 추가
                responses.add(youTubeDto);

                //video id값 1 증가
                id += 1;
            }
        }
    }

    /**
     * <youtube API 메서드>
     * 1. 서비스 가능한 categoryId값인지 체크
     * 2. entity 확인 메서드 실행
     * 3. youtube api 정보 지정 (인기 급상승 목록, 10개, 한국), (전체 or 카테고리별 조회)
     * 4. api 조회
     * 5. entity 저장 or 업데이트
     * 6. dto로 변환 후 출력
     */
    public YoutubeApiDto.Multi get(String categoryId) {

        // 카테고리 id 체크해서 불필요한 api 호출 차단
        if(!check(categoryId)){
            throw new BusinessLogicException(ExceptionCode.CATEGORY_ID_NOT_FOUND);
        }
        List<YoutubeApiDto.Details> responses = new ArrayList<>();
        VideoPK videoPK = new VideoPK(categoryId,10L);

        // entity 확인 메서드 실행
        if(entityExistCheck(videoPK)) {
            try {
                // youtube api 조회할 명령어 생성
                youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("youtube-video-duration-get").build();

                //원하는 정보 지정. youtube data API문서 참고
                YouTube.Videos.List videos = youtube.videos().list("snippet");
                // 인기 급상승 영상
                videos.setChart("mostPopular");
                // 조회 갯수
                videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
                // 지역
                videos.setRegionCode("kr");
                // 카테고리별 조회값 (카테고리 전체 조회시 추가 x)
                if(!Objects.equals(categoryId, "3")){
                    videos.setVideoCategoryId(categoryId);
                }
                // api 키 값
                videos.setKey(apikey);
                // youtube api 생성
                List<Video> videoList = videos.execute().getItems();

                // iterator로 리스트 순회하며 데이터 처리
                if (videoList != null) {
                    saveEntity(videoList.iterator(), responses, categoryId);
                }

            } catch (GoogleJsonResponseException e) {
                // 에러처리 더블체크 - 유튜브에서 제공하지 않는 카테고리ID 입력시
                throw new BusinessLogicException(ExceptionCode.CATEGORY_ID_NOT_FOUND);
            } catch (IOException e) {
                // 에러처리 - 할당량 초과
                throw new BusinessLogicException(ExceptionCode.LIMIT_EXCESS);
            } catch (Throwable t) {
                // 에러처리 - 복구할 수 없는 예외상황 (프로그램 오류로 종료되는 상황) 발생시 원인 출력
                t.printStackTrace();
            }
        } else {
            // 페이지 Request 생성
            PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("videoPK").ascending());
            // Page 생성
            Page<VideoEntity> VideoEntitiesPage = youtubeApiRepository.findByVideoCategoryId(categoryId, pageRequest);
            // 리스트로 가져오기
            responses = mapper.videoEntitiesToVideoEntitiesDetailsResponses(VideoEntitiesPage.getContent());
        }

        return new YoutubeApiDto.Multi(responses);
    }

    /**
     * <entity 확인 메서드>
     * 1. 데이터베이스에 존재하는지 확인 (없으면 생성 )
     * 2. 존재하면 entity 수정 시간과 현재 시간 비교 (1일 이상 차이나면 업데이트)
     */
    public boolean entityExistCheck(VideoPK videoPK) {
        Optional<VideoEntity> optionalVideoEntity = youtubeApiRepository.findById(videoPK);

        // 데이터베이스에 존재하지 않으면 true
        if (optionalVideoEntity.isEmpty()) {
            return true;
        } else {
            LocalDateTime date1 = optionalVideoEntity.orElseThrow(() -> new BusinessLogicException(ExceptionCode.VIDEO_NOT_FOUND)).getModifiedAt();
            LocalDateTime date2 = LocalDateTime.now();
            // DB에 엔티티 존재하면 수정된 시간 확인
            // 현재 시간과 1일 이상 차이나면 true 아니면 false
            return compareDay(date1, date2) >= 1;
        }
    }

    // 서비스 가능한 카테고리ID 인지 확인하는 메서드
    public boolean check(String categoryId) {
        return hasYoutubeCategoryId(categoryId, "1","2","3","10","15","17","20","22","23","24","25","26","28");
    }

    //여러 개의 문자열에 equals 비교하는 메서드
    public static boolean hasYoutubeCategoryId(String value, String... categoryIds) {
        for (String categoryId : categoryIds) {
            if (categoryId.equals(value)) {
                return true;
            }
        }
        return false;
    }

    // 시간 비교 메서드 - 일 단위
    public static int compareDay(LocalDateTime date1, LocalDateTime date2) {
        LocalDateTime dayDate1 = date1.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime dayDate2 = date2.truncatedTo(ChronoUnit.DAYS);

        int compareResult = dayDate1.compareTo(dayDate2);

        return compareResult;
    }

    /**
     * <4byte 문자 제거 메서드>
     * mysql 인코딩 설정이 utf8일때 DB에 이모지가 안들어가서..(에러남)
     */
    public static String mysqlUtf8Safe(String input) {
        if (input == null) return null;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (i < (input.length() - 1)) { // 이모지는 자바에서 두글자 길이로 처리됨 - "\uD83D\uDE80" 이런 형식
                if (Character.isSurrogatePair(input.charAt(i), input.charAt(i + 1))) {
                    i += 1; // 두번째 문자 스킵
                    continue;
                }
            }
            sb.append(input.charAt(i));
        }

        return sb.toString();
    }
}