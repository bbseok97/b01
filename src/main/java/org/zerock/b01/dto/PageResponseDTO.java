package org.zerock.b01.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {  //만들어진 값을 불러오기만 수행.

    private int page;
    private int size;
    private int total;

    //  시작페이지 번호
    private int start;
    //  끝 페이지 번호
    private int end;

    //  이전페이지 여부
    private boolean prev;
    //  다음 페이지 여부
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName = "withAll") //withAll 이라는 이름으로 호출
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {

        if(total == 0){ //  total == 0인 경우 실행하지 않고 종료.
            return;
        }
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();
        this.total = total;

        this.end = (int)(Math.ceil(this.page / 10.0)) * 10;     //화면에 표시할 페이지번호 갯수...
        this.start = this.end -9;   //화면에서 시작번호

        int last = (int)(Math.ceil(total / (double)size));      //데이터 개수로 계산한 마지막 페이지 번호
        this.end = end > last ? last : end;

        this.prev = start > 1;      //페이지 시작번호가 1보다 클 경우 이전 페이지 활성화
        this.next = total > this.end * this.size;

        this.dtoList = dtoList;
    }
}
