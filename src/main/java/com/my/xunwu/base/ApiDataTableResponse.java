package com.my.xunwu.base;

/**
 * Datatables插件响应结构(以下字段为Datatables插件的固定格式)
 * @author hcz
 * @date 2019.10.30
 */
public class ApiDataTableResponse extends ApiResponse{
	/**
	 * 验证结果
	 */
	private int draw;
	/**
	 * 总数
	 */
	private long recordsTotal;
	/**
	 * 分页
	 */
	private long recordsFiltered;
	
	public ApiDataTableResponse(ApiResponse.Status status) {
		this(status.getCode(),status.getStandardMessage(),null);
	}
	public ApiDataTableResponse(int code, String message, Object data) {
		super(code, message, data);
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public long getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public long getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	
}
