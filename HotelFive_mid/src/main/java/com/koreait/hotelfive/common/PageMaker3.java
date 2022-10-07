package com.koreait.hotelfive.common;

public class PageMaker3 {
	
	public static String getPageView(String path, int page, int recordPerPage, int totalRecord, String query, String column) {
		
		StringBuffer sb = new StringBuffer();
		
		int totalPage = 0;
		int pagePerBlock = 5;
		int beginPageOfBlock = 0;
		int endPageOfBlock = 0;
		
		totalPage = (int)(totalRecord / recordPerPage);
		if ( totalRecord % recordPerPage != 0 ) {
			totalPage++;
		}
		
		if ( page > totalPage ) {
			totalPage = page;
		}
		
		beginPageOfBlock = (int)(((page - 1) / pagePerBlock) * pagePerBlock) + 1;
		endPageOfBlock = beginPageOfBlock + pagePerBlock - 1;
		
		if ( endPageOfBlock > totalPage ) {
			endPageOfBlock = totalPage;
		}
		
		// 이전 버튼 표시
		if ( beginPageOfBlock < pagePerBlock ) {
			sb.append("<span style='color: lightgray;'>◀</span>&nbsp;&nbsp;");
		} else {
			sb.append("<a href='" + path + "?page=" + (beginPageOfBlock - 1) + "&query=" + query + "&column=" + column + "'>◀</a>&nbsp;&nbsp;");
		}
		
		// 페이지 번호 표시
		if(endPageOfBlock < 5){
			sb.append("<span style='color: lightgray;'>▶</span>");
			} else {
			if (totalPage%5 == 1 && endPageOfBlock %5 == 1){
			sb.append("<span style='color: lightgray;'>▶</span>");
			} else {
			if (totalPage < endPageOfBlock){
			sb.append("<span style='color: lightgray;'>▶</span>");
			}else {
			if(totalPage%5 == 1 && endPageOfBlock%5 ==0){
				sb.append("<a href='" + path + "?page=" + (endPageOfBlock + 1) + "'>▶</a>&nbsp;&nbsp;");
			}else {
			sb.append("<a href='" + path + "?page=" + (endPageOfBlock + 1) + "'>▶</a>&nbsp;&nbsp;");
			}}}}
		
		return sb.toString();
		
	}

}
