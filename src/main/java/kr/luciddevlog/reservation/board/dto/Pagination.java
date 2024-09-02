package kr.luciddevlog.reservation.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class Pagination {
	private int pp;
	private int p;
	private int n;
	private int nn;

	private Long totalRecordCount;
	private int totalPageCount;
	private List<Integer> pageList;
}