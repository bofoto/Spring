package com.board.paging;

public class PaginationInfo {
	private Criteria criteria;
	private int totalRecordCount;
	private int totalPageCount;
	private int firstPage;
	private int lastPage;
	private int firstRecordIndex;
	private int lastRecordIndex;
	private boolean hasPreviousPage;
	private boolean hasNextPage;
	
		public PaginationInfo(Criteria criteria) {
			if (criteria.getCurrentPageNo()<1) {
				criteria.setCurrentPageNo(1);
			}
			if (criteria.getRecordsPerPage()<1|| criteria.getRecordsPerPage()>100) {
				criteria.setRecordsPerPage(10);
			}
			if (criteria.getPageSize()< 5|| criteria.getPageSize()>20) {
				criteria.setPageSize(10);
			}
			
			this.criteria = criteria;
		}
		public void setTotalRecordCount(int totalRecordCount) {
			this.totalRecordCount = totalRecordCount;
			if(totalRecordCount > 0) {
				calculation();
			}
		}
		private void calculation() {
			/*전체 페이지 수 (현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장)*/
			totalPageCount = ((totalRecordCount -1)/ criteria.getRecordsPerPage())+1;
			if (criteria.getCurrentPageNo() > totalPageCount) {
				criteria.setCurrentPageNo(totalPageCount);
			}
			
			/* 페이지 리스트의 첫 페이지 번호*/
			firstPage = ((criteria.getCurrentPageNo()-1) / criteria.getPageSize()) * criteria.getPageSize() +1;
			
			/* 페이지 리스트의 마지막 페이지 번호 (마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지에 전체 페이지 수를 저장)*/
			lastPage = firstPage + criteria.getPageSize()-1;
			if (lastPage > totalPageCount) {
				lastPage = (int)Math.ceil((double)totalRecordCount/10);
				
			}
			
			/*SQL의 조걸절에 사용되는 첫 RNUM*/
			firstRecordIndex = (criteria.getCurrentPageNo() - 1)* criteria.getRecordsPerPage();
			
			/*SQL의 조걸절에 사용되는 마지막 RNUM*/
			lastRecordIndex = criteria.getCurrentPageNo()*criteria.getRecordsPerPage();
			
			/*이전 페이지 존재 여부*/
			hasPreviousPage = firstPage != 1;
			
			/*다음 페이지 존재 여부*/
			hasNextPage = (lastPage *criteria.getRecordsPerPage())<totalRecordCount; 
			
		}
		public Criteria getCriteria() {
			return criteria;
		}
		public void setCriteria(Criteria criteria) {
			this.criteria = criteria;
		}
		public int getTotalPageCount() {
			return totalPageCount;
		}
		public void setTotalPageCount(int totalPageCount) {
			this.totalPageCount = totalPageCount;
		}
		public int getFirstPage() {
			return firstPage;
		}
		public void setFirstPage(int firstPage) {
			this.firstPage = firstPage;
		}
		public int getLastPage() {
			return lastPage;
		}
		public void setLastPage(int lastPage) {
			this.lastPage = lastPage;
		}
		public int getFirstRecordIndex() {
			return firstRecordIndex;
		}
		public void setFirstRecordIndex(int firstRecordIndex) {
			this.firstRecordIndex = firstRecordIndex;
		}
		public int getLastRecordIndex() {
			return lastRecordIndex;
		}
		public void setLastRecordIndex(int lastRecordIndex) {
			this.lastRecordIndex = lastRecordIndex;
		}
		public boolean isHasPreviousPage() {
			return hasPreviousPage;
		}
		public void setHasPreviousPage(boolean hasPreviousPage) {
			this.hasPreviousPage = hasPreviousPage;
		}
		public boolean isHasNextPage() {
			return hasNextPage;
		}
		public void setHasNextPage(boolean hasNextPage) {
			this.hasNextPage = hasNextPage;
		}
		public int getTotalRecordCount() {
			return totalRecordCount;
		}
		
}
