package com.CreatorConnect.server.board.jobboard.mapper;

import com.CreatorConnect.server.board.jobboard.dto.JobBoardDto;
import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobBoardMapper {
    // JobBoardDto.Post -> JobBoard
    JobBoard jobBoardPostDtoToJobBoard(JobBoardDto.Post post);

    // JobBoard -> JobBoardDto.PostResponse
    JobBoardDto.PostResponse jobBoardToJobBoardPostResposneDto(JobBoard jobBoard);

    // JobBoard -> JobBoardDto.Response
    JobBoardDto.Response jobBoardToJobBoardResponseDto(JobBoard jobBoard);

    // JobBoardDto.Patch -> JobBoard
    JobBoard jobBoardPatchDtoToJobBoard(JobBoardDto.Patch patch);

    List<JobBoardDto.Response> jobBoardsToJobBoardResponseDtos(List<JobBoard> jobBoards);

}