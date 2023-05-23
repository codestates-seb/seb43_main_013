package com.CreatorConnect.server.board.promotionboard.mapper;

import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardDto;
import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardResponseDto;
import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import com.CreatorConnect.server.board.tag.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromotionBoardMapper {

    PromotionBoard promotionBoardPostDtoToPromotionBoard(PromotionBoardDto.Post PromotionBoardPostDto);
    PromotionBoard promotionBoardPatchDtoToPromotionBoard(PromotionBoardDto.Potct PromotionBoardPatchDto);
    PromotionBoardResponseDto.Post promotionBoardToPromotionPostResponse(PromotionBoard promotionBoard);
    PromotionBoardResponseDto.Patch promotionBoardToPromotionPatchResponse(PromotionBoard promotionBoard);
    PromotionBoardResponseDto.Details prmotionBoardToPromotionBoardDetailsResponse(PromotionBoard promotionBoard);
    PromotionBoardResponseDto.Details promotionBoardToResponse(PromotionBoard promotionBoard, List<TagDto.TagInfo> tags);


}
