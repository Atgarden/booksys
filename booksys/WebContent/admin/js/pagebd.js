function pagebd(info) {
	
	$("#pageId").empty();
	
	var html = '<tr>';
	// 计算上一页
	html += '<td width="45"><input class="input-btn1" type="button" onclick="javascript:loadData(1)"></input></td>';
	var prePage = info.currentPage - 1;
	if (prePage <= 0) {
		html += '<td width="49"><input class="input-btn2" type="button" onclick="javascript:loadData(1)"></input></td>';
	} else {
		html += '<td width="49"><input class="input-btn2" type="button" onclick="javascript:loadData(' + prePage + ')"></input></td>';
	}
	// 计算下一页
	var nextPage = info.currentPage + 1;
	if (nextPage > info.totalPage) {
		html += '<td width="49"><input class="input-btn3" type="button" onclick="javascript:loadData(' + info.totalPage + ')"></input></td>';
	} else {
		html += '<td width="49"><input class="input-btn3" type="button" onclick="javascript:loadData(' + nextPage + ')"></input></td>';
	}
	html += '<td width="45"><input class="input-btn4" type="button" onclick="javascript:loadData(' + info.totalPage + ')"></input></td>';
	
	html += '<td width="37" class="STYLE22"><div align="center">转到</div></td>';
	html += '<td width="22"><div align="center">';
    html += '<input type="text" name="textfield" id="textfield" value="" style="width:20px; height:12px; font-size:12px; border:solid 1px #7aaebd;"/>';
    html += '</div></td>';
    html += '<td width="22" class="STYLE22"><div align="center">页</div></td>';
    html += '<td width="35"><input class="input-btn5" type="button" onclick="trans()"></input></td>';
    html += '</tr>';
	
	$("#pageId").append($(html));
}